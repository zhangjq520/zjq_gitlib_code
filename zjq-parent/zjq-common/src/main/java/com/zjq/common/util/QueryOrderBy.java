package com.zjq.common.util;

public class QueryOrderBy {

  public static String ASC = "ASC";
  public static String DESC = "DESC";

  private String columnName;

  private String orderType;

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

}
