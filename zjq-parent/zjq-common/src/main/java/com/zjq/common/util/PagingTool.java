package com.zjq.common.util;

public class PagingTool {

  private Integer currentPage; // Current page number.
  private Integer pageSize; // One page records numbers
  private Integer start; // Start index
  private Integer totalNum; // Total record numbers

  public void calculateStartIndex() {
    if (this.currentPage == null) {
      this.start = 0;
      this.currentPage = 1;
    } else if (this.currentPage < 1) {
      this.start = 0;
      this.currentPage = 1;
    } else {
      this.start = (this.currentPage - 1) * this.pageSize;
    }
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getTotalNum() {
    return totalNum;
  }

  public void setTotalNum(Integer totalNum) {
    this.totalNum = totalNum;
  }

}
