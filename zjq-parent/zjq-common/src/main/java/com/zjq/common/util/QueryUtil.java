package com.zjq.common.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUtil {

  private PagingTool pagingTool;
  private List<QueryCriteria> queryCriterias;
  private List<QueryOrderBy> queryOrderBies;
  private Map<String, Object> params = new HashMap<String, Object>();

  public PagingTool getPagingTool() {
    return pagingTool;
  }

  public void setPagingTool(PagingTool pagingTool) {
    this.pagingTool = pagingTool;
  }

  public List<QueryCriteria> getQueryCriterias() {
    return queryCriterias;
  }

  public void setQueryCriterias(List<QueryCriteria> queryCriterias) {
    this.queryCriterias = queryCriterias;
  }

  public List<QueryOrderBy> getQueryOrderBies() {
    return queryOrderBies;
  }

  public void setQueryOrderBies(List<QueryOrderBy> queryOrderBies) {
    this.queryOrderBies = queryOrderBies;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  /**
   * The sample return string is like: col1 = '1' and col2 > 2 and col2 <=5 or col3 >='3' and col4 in ('B41', 'B42')
   * order by col1 ASC
   * 
   * @return
   */
  public String getFullWhereClause() {
    StringBuffer sb = new StringBuffer();

    List<QueryCriteria> queryCriterias = getQueryCriterias();
    int size;

    if (queryCriterias != null) {

      size = getQueryCriterias().size();

      // Loop queryCriteria get first section of fullQueryCriteria
      for (int i = 0; i < size; i++) {

        if (i > 0) {
          sb.append(" ");
        }

        QueryCriteria q = getQueryCriterias().get(i);

        // BEGIN Updated by OMER (2015-12-07)
        
        if (i == 0 && q.getConnection().indexOf("(", 0) > 0) {
			sb.append("(");
		}
        
        if (i != 0){
          sb.append(q.getConnection());
        }
        sb.append(" ").append("UPPER("+q.getKey()+")").append(" ").append(q.getCondition()).append(" ");
        // END Updated by OMER (2015-12-07)

        if (QueryCriteria.LIKE.equals(q.getCondition()) && q.getIsValueADigital().booleanValue() == false) {
          sb.append("'");
        }

        if ((QueryCriteria.EQUAL.equals(q.getCondition()) ||
                QueryCriteria.MORE_THAN.equals(q.getCondition()) ||
                QueryCriteria.LESS_THAN.equals(q.getCondition()) ||
                QueryCriteria.MORE_THAN_AND_EQUAL.equals(q.getCondition()) ||
                QueryCriteria.LESS_THAN_AND_EQUAL.equals(q.getCondition()) ||
                QueryCriteria.NOT_EQUAL.equals(q.getCondition())) &&
                q.getIsValueADigital().booleanValue() == false) {
          sb.append("'");
        }

        if (QueryCriteria.LIKE.equals(q.getCondition())) {
          sb.append("%");
        }

        if (QueryCriteria.IN.equals(q.getCondition())) {
          sb.append("(");
        }


        if (QueryCriteria.IN.equals(q.getCondition()) && q.getIsValueADigital().booleanValue() == false) {
          String[] splurt = q.getValue().toUpperCase().split(",");
          boolean first = true;
          for (String each : splurt){
            if (q.getIsValueADigital().booleanValue() == false) {
              if (!first){
                sb.append(",");
              }else{
                first = false;
              }
              sb.append("'");
              sb.append(each.trim());
              sb.append("'");
            }else{
              if (!first){
                sb.append(",");
              }else{
                first = false;
              }
              sb.append(each.trim());
            }
          }
        }

        if (!QueryCriteria.IN.equals(q.getCondition())){
          if (isDate(q.getValue().toUpperCase())){
            sb.append("TO_DATE ( SUBSTR ( '")
                    .append(q.getValue().toUpperCase())
                    .append("', 1, 19), 'YYYY-MM-DD\"T\"HH24:MI:SS')");
          }else{
            sb.append(q.getValue().toUpperCase());
          }

        }


        if (QueryCriteria.LIKE.equals(q.getCondition())) {
          sb.append("%");
        }

        if (QueryCriteria.IN.equals(q.getCondition())) {
          sb.append(")");
        }

        if (QueryCriteria.LIKE.equals(q.getCondition()) && q.getIsValueADigital().booleanValue() == false) {
          sb.append("'");
        }

        if ((QueryCriteria.EQUAL.equals(q.getCondition()) ||
                QueryCriteria.MORE_THAN.equals(q.getCondition()) ||
                QueryCriteria.LESS_THAN.equals(q.getCondition()) ||
                QueryCriteria.MORE_THAN_AND_EQUAL.equals(q.getCondition()) ||
                QueryCriteria.LESS_THAN_AND_EQUAL.equals(q.getCondition()) ||
                QueryCriteria.NOT_EQUAL.equals(q.getCondition())) &&
                q.getIsValueADigital().booleanValue() == false) {
          sb.append("'");
        }

      }

    }

    if (sb.toString().isEmpty()){
      sb.append(" 1=1 ");
    }

    List<QueryOrderBy> queryOrderBies = getQueryOrderBies();
    if (queryOrderBies != null) {
      // Add order by clause
      size = getQueryOrderBies().size();
      if (size > 0) {
        sb.append(" ORDER BY");
      }

      for (int i = 0; i < size; i++) {
        QueryOrderBy q = queryOrderBies.get(i);

        if (i > 0) {
          sb.append(",");
        }

        sb.append(" ").append(q.getColumnName()).append(" ").append(q.getOrderType());
      }
    }

    // Please reference PageInterceptor for how to add fullWhereClause and page limit.



    return sb.toString();
  }

  public String getRootOrderClause(){
    StringBuffer sb = new StringBuffer();
    List<QueryOrderBy> queryOrderBies = getQueryOrderBies();
    if (queryOrderBies != null) {
      // Add order by clause
      int size = getQueryOrderBies().size();
      for (int i = 0; i < size; i++) {
        QueryOrderBy q = queryOrderBies.get(i);

        if (i > 0) {
          sb.append(",");
        }

        sb.append(" a.").append(q.getColumnName()).append(" ").append(q.getOrderType());
      }
    }

    return sb.toString();
  }

  private boolean isDate(String input){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    try {
      sdf.parse(input);
    } catch (ParseException e) {
      return false;
    }

    return true;

  }

}
