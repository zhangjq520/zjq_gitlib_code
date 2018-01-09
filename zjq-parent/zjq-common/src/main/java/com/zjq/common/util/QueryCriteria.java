package com.zjq.common.util;

public class QueryCriteria {

  public static final String AND = "AND";
  public static final String OR = "OR";
  public static final String EQUAL = "=";
  public static final String MORE_THAN = ">";
  public static final String LESS_THAN = "<";
  public static final String MORE_THAN_AND_EQUAL = ">=";
  public static final String LESS_THAN_AND_EQUAL = "<=";
  public static final String NOT_EQUAL = "!=";
  public static final String LIKE = "like";
  public static final String IN = "in";

  private String connection; // AND / OR

  private String key; // Column Name

  private String condition; // =, >, <, >=, <=, !=, like

  private String value;

  private Boolean isValueADigital = false;

  public String getConnection() {
    return connection;
  }

  public void setConnection(String connection) {
    this.connection = connection;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public Boolean getIsValueADigital() {
    return isValueADigital;
  }

  public void setIsValueADigital(Boolean isValueADigital) {
    this.isValueADigital = isValueADigital;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
