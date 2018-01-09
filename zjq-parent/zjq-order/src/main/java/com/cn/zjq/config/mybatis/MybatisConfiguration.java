package com.cn.zjq.config.mybatis;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.cn.zjq.config.SqlSessionFactoryBeanLocal;
import com.cn.zjq.web.interceptor.ReadPageInterceptor;
import com.cn.zjq.web.interceptor.WritePageInterceptor;
import com.codingapi.tx.datasource.relational.LCNTransactionDataSource;

/**
 * Author: omer 
 * Date  : 28-10-2015
 */

@Configuration
@EnableTransactionManagement(order=2)
public class MybatisConfiguration implements EnvironmentAware{
    private static final Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

    private static final String ENV_DATASOURCE = "spring.datasource.";
    private static final String ENV_JNDI = "logic.jndi.";

    private static final String PROP_NAME_MASTER = "master.name";
    private static final String PROP_NAME_SLAVE= "slave.name";
    private static final String PROP_DRIVER_CLASS_NAME_MASTER = "master.driverClassName";
    private static final String PROP_DATABASEID_MASTER = "master.databaseId";
    private static final String PROP_DATABASEID_SLAVE = "slave.databaseId";

    private static final String PROP_URL = "url";
    private static final String PROP_USERNAME = "username";
    private static final String PROP_PASSWORD = "password";

    private static RelaxedPropertyResolver propertyResolverJNDI;
    private static RelaxedPropertyResolver propertyResolverDatasource;
    
    private DataSource writeDataSource;
    private LCNTransactionDataSource dataSourceProxy; 
    
    @Bean(destroyMethod = "")
    public DataSource readDataSource()
        throws IllegalArgumentException, NamingException
    {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/" + propertyResolverJNDI.getProperty(PROP_NAME_SLAVE, String.class, ""));
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.setExpectedType(DataSource.class);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }

    @Bean(destroyMethod = "")
    public DataSource writeDataSource()
        throws IllegalArgumentException, NamingException
    {
        if (writeDataSource != null)
        {
            return writeDataSource;
        }

        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/" + propertyResolverJNDI.getProperty(PROP_NAME_MASTER, String.class, ""));
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.setExpectedType(DataSource.class);
        bean.afterPropertiesSet();

        writeDataSource = (DataSource)bean.getObject();
        return writeDataSource;
    }
    
    @Bean
    public LCNTransactionDataSource getWriteLcnTransactionDataSource() throws IllegalArgumentException, NamingException{
    	if (dataSourceProxy==null) {
    		dataSourceProxy = new LCNTransactionDataSource();
		}
        dataSourceProxy.setDataSource(writeDataSource());
		dataSourceProxy.setMaxCount(10);
		
		return dataSourceProxy;
    }
    
    @Bean
    @Primary
    public DataSource dataSource() {

        DataSource dataSource = null;

        try{
            dataSource = readDataSource();
        }catch (Exception e){
            logger.error("dataSource error.", e);
        }
        return dataSource;
    }


    @Bean
    public DataSourceTransactionManager transactionManager()
            throws IllegalArgumentException, NamingException
        {

            return new DataSourceTransactionManager(getWriteLcnTransactionDataSource());
        }

    @Bean
    public ReadPageInterceptor readPaginationInterceptor(){
        return new ReadPageInterceptor();
    }

    @Bean
    public WritePageInterceptor writePaginationInterceptor(){
        return new WritePageInterceptor();
    }

    @Bean(name="readSqlSessionFactory")
    public SqlSessionFactory readSqlSessionFactory() throws Exception {

        logger.info("MyBatis readSqlSessionFactory");
        final SqlSessionFactoryBeanLocal readSqlSessionFactory =
                new SqlSessionFactoryBeanLocal(propertyResolverJNDI.getProperty(PROP_DATABASEID_SLAVE, String.class, null));
        readSqlSessionFactory.setDataSource(readDataSource());
        readSqlSessionFactory.setTypeAliasesPackage("com.cn.zjq.entity,com.zjq.common.domain.order");

        Interceptor[] interceptors = new Interceptor[1];
        interceptors[0] = readPaginationInterceptor();
        readSqlSessionFactory.setPlugins(interceptors);

        readSqlSessionFactory.setDatabaseIdProvider(databaseIdProvider());

        return readSqlSessionFactory.getObject();
    }

    @Bean(name="writeSqlSessionFactory")
    public SqlSessionFactory writeSqlSessionFactory() throws Exception {
        logger.info("MyBatis writeSqlSessionFactory");
        final SqlSessionFactoryBeanLocal writeSqlSessionFactory =
                new SqlSessionFactoryBeanLocal(propertyResolverJNDI.getProperty(PROP_DATABASEID_MASTER, String.class, null));
        writeSqlSessionFactory.setDataSource(getWriteLcnTransactionDataSource());
        writeSqlSessionFactory.setTypeAliasesPackage("com.cn.zjq.entity,com.zjq.common.domain.order");

        Interceptor[] interceptors = new Interceptor[1];
        interceptors[0] = writePaginationInterceptor();
        writeSqlSessionFactory.setPlugins(interceptors);

        writeSqlSessionFactory.setDatabaseIdProvider(databaseIdProvider());
        return writeSqlSessionFactory.getObject();
    }

    @Bean
    @ConditionalOnWebApplication
    public MapperScannerConfigurer readMapperScannerConfigurer(){
        logger.info("MyBatis readMapperScannerConfigurer");
        MapperScannerConfigurer readMapperScannerConfigurer = new MapperScannerConfigurer();
        readMapperScannerConfigurer.setBasePackage("com.cn.zjq.mapper.read");
        readMapperScannerConfigurer.setSqlSessionFactoryBeanName("readSqlSessionFactory");
        return readMapperScannerConfigurer;
    }

    @Bean
    @ConditionalOnWebApplication
    public MapperScannerConfigurer writeMapperScannerConfigurer() {
        logger.info("MyBatis writeMapperScannerConfigurer");
        MapperScannerConfigurer writeMapperScannerConfigurer = new MapperScannerConfigurer();
        writeMapperScannerConfigurer.setBasePackage("com.cn.zjq.mapper.write");
        writeMapperScannerConfigurer.setSqlSessionFactoryBeanName("writeSqlSessionFactory");
        return writeMapperScannerConfigurer;
    }

    @Bean
    public VendorDatabaseIdProvider databaseIdProvider(){
        VendorDatabaseIdProvider vendorDatabaseIdProvider = new VendorDatabaseIdProvider();
        try {
            vendorDatabaseIdProvider.setProperties(vendorProperties().getObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vendorDatabaseIdProvider;
    }

    @Bean
    public PropertiesFactoryBean vendorProperties(){
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();

        Properties props = new Properties();
        props.setProperty("Oracle", "oracle");
        props.setProperty("MySQL", "mysql");
        propertiesFactoryBean.setProperties(props);
        return propertiesFactoryBean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        logger.info("-------------------------------- MyBatisConfigration-----------------------");
        propertyResolverJNDI = new RelaxedPropertyResolver(environment, ENV_JNDI);
        propertyResolverDatasource = new RelaxedPropertyResolver(environment, ENV_DATASOURCE);
    }
    
}
