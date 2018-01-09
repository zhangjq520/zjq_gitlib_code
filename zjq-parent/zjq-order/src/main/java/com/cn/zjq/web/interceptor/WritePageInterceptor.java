package com.cn.zjq.web.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zjq.common.util.CacheFactory;
import com.zjq.common.util.ICache;
import com.zjq.common.util.PagingTool;
import com.zjq.common.util.QueryUtil;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Properties;

@Configuration
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class WritePageInterceptor implements Interceptor, EnvironmentAware {

  private static final Logger log = LoggerFactory.getLogger(ReadPageInterceptor.class);


  private static final String ENV_DATABASE = "logic.database.";
  private static final String PROP_WRITE_PREFIX = "write_prefix";


  private static RelaxedPropertyResolver propertyResolver;

  @Autowired
  HttpServletRequest request;

  /**
   * 1. Append FullWhereClause String 
   * 2. Query the record count. 
   * 3. Add paging string at the end of fullWhereClause String.
   */
  public Object intercept(Invocation invocation) throws Throwable {
    // 获取数据库前缀，如果config文件中没有，则使用context中指定的数据库
    ICache cache = CacheFactory.getCacheImpl();
    String databaseWritePrefix = propertyResolver.getProperty(PROP_WRITE_PREFIX, String.class, null);

    if (!StringUtils.isEmpty(databaseWritePrefix)) {
      setCatalog((Connection) invocation.getArgs()[0], databaseWritePrefix);
    }

    RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
    StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
    BoundSql boundSql = delegate.getBoundSql();
    Object obj = boundSql.getParameterObject();
    if (obj instanceof QueryUtil) {
      QueryUtil queryUtil = (QueryUtil) obj;
      String fullWhereClause = queryUtil.getFullWhereClause();
      if (fullWhereClause != null && !fullWhereClause.equals("")) {
        String sql = boundSql.getSql();
        StringBuffer sb = new StringBuffer(sql);
        sb.append(" ");
        sb.append(fullWhereClause);
        ReflectUtil.setFieldValue(boundSql, "sql", sb.toString());
      }

      PagingTool pagingTool = queryUtil.getPagingTool();

      if (pagingTool.getPageSize() != -1) {
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
        Connection connection = (Connection) invocation.getArgs()[0];
        String sql = boundSql.getSql();
        this.setTotalRecord(queryUtil, mappedStatement, connection, sql);
        String pageSql = this.getPageSql(pagingTool, sql);
        ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
      }
    }
    //log.info("[SQL :" + boundSql.getSql() + " ]");
    return invocation.proceed();
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties) {

  }

  private String getPageSql(PagingTool page, String sql) {
    StringBuffer sqlBuffer = new StringBuffer(sql);
    return getMysqlPageSql(page, sqlBuffer);
  }

  private String getMysqlPageSql(PagingTool page, StringBuffer sqlBuffer) {
    Integer start = page.getStart();
    Integer pageSize = page.getPageSize();
    sqlBuffer.append(" limit ").append(start).append(",").append(pageSize);
    return sqlBuffer.toString();
  }

  private void setTotalRecord(QueryUtil queryUtil, MappedStatement mappedStatement, Connection connection, String sql) {
    BoundSql boundSql = mappedStatement.getBoundSql(queryUtil);
    String countSql = this.getCountSql(sql);
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, queryUtil);
    ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, queryUtil, countBoundSql);
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = connection.prepareStatement(countSql);
      parameterHandler.setParameters(pstmt);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int totalRecord = rs.getInt(1);
        queryUtil.getPagingTool().setTotalNum(totalRecord);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (pstmt != null)
          pstmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private String getCountSql(String sql) {
    return "select count(1) from (" + sql + ") t";
  }
  
  private void setCatalog(Connection connection, String prefix) throws Throwable{
    String requestURL = request.getRequestURL().toString();

    requestURL = requestURL.substring(requestURL.indexOf("//") + 2);

    requestURL = requestURL.substring(0, requestURL.indexOf("/"));

    if (requestURL.indexOf(":") > -1) {
      requestURL = requestURL.substring(0, requestURL.indexOf(":"));
    }

    if (requestURL.indexOf(".") > -1) {
      requestURL = requestURL.substring(0, requestURL.indexOf("."));
    }

    String database = prefix + requestURL;

    DatabaseMetaData data = connection.getMetaData();
    if (data.getDriverName().toLowerCase().indexOf("mysql") > -1) {
      connection.setCatalog(database);
    }
    
  }

  @Override
  public void setEnvironment(Environment environment) {
    propertyResolver = new RelaxedPropertyResolver(environment, ENV_DATABASE);
  }


  private static class ReflectUtil {

    public static Object getFieldValue(Object obj, String fieldName) {
      Object result = null;
      Field field = ReflectUtil.getField(obj, fieldName);
      if (field != null) {
        field.setAccessible(true);
        try {
          result = field.get(obj);
        } catch (IllegalArgumentException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return result;
    }

    private static Field getField(Object obj, String fieldName) {
      Field field = null;
      for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
        try {
          field = clazz.getDeclaredField(fieldName);
          break;
        } catch (NoSuchFieldException e) {
        }
      }
      return field;
    }

    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
      Field field = ReflectUtil.getField(obj, fieldName);
      if (field != null) {
        try {
          field.setAccessible(true);
          field.set(obj, fieldValue);
        } catch (IllegalArgumentException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
}
