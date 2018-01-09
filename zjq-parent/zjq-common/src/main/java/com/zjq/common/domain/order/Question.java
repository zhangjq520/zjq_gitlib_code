package com.zjq.common.domain.order;

import com.zjq.common.domain.BasePojo;

public class Question extends BasePojo {

  private static final long serialVersionUID = -3834353144361803689L;

  private String code;

  private String type;

  private String subjectDesc;

  private String inputDesc;

  private String correctResponse;

  private Double correctNumberStart;

  private Double correctNumberEnd;

  private String inputForException;

  private String parentAnswerCode;

  private String viewName;

  private String columnForDisplay;

  private String columnForValue;

  private String whereClause;

  private Double point;

  private String subject;

  private String status;
  
  private String other1;
  
  private String other2;
  
  private String other3;

  private String other4;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type == null ? null : type.trim();
  }

  public String getSubjectDesc() {
    return subjectDesc;
  }

  public void setSubjectDesc(String subjectDesc) {
    this.subjectDesc = subjectDesc == null ? null : subjectDesc.trim();
  }

  public String getInputDesc() {
    return inputDesc;
  }

  public void setInputDesc(String inputDesc) {
    this.inputDesc = inputDesc == null ? null : inputDesc.trim();
  }

  public String getCorrectResponse() {
    return correctResponse;
  }

  public void setCorrectResponse(String correctResponse) {
    this.correctResponse = correctResponse == null ? null : correctResponse.trim();
  }

  public Double getCorrectNumberStart() {
    return correctNumberStart;
  }

  public void setCorrectNumberStart(Double correctNumberStart) {
    this.correctNumberStart = correctNumberStart;
  }

  public Double getCorrectNumberEnd() {
    return correctNumberEnd;
  }

  public void setCorrectNumberEnd(Double correctNumberEnd) {
    this.correctNumberEnd = correctNumberEnd;
  }

  public String getInputForException() {
    return inputForException;
  }

  public void setInputForException(String inputForException) {
    this.inputForException = inputForException == null ? null : inputForException.trim();
  }

  public String getParentAnswerCode() {
    return parentAnswerCode;
  }

  public void setParentAnswerCode(String parentAnswerCode) {
    this.parentAnswerCode = parentAnswerCode == null ? null : parentAnswerCode.trim();
  }

  public String getViewName() {
    return viewName;
  }

  public void setViewName(String viewName) {
    this.viewName = viewName == null ? null : viewName.trim();
  }

  public String getColumnForDisplay() {
    return columnForDisplay;
  }

  public void setColumnForDisplay(String columnForDisplay) {
    this.columnForDisplay = columnForDisplay == null ? null : columnForDisplay.trim();
  }

  public String getColumnForValue() {
    return columnForValue;
  }

  public void setColumnForValue(String columnForValue) {
    this.columnForValue = columnForValue == null ? null : columnForValue.trim();
  }

  public String getWhereClause() {
    return whereClause;
  }

  public void setWhereClause(String whereClause) {
    this.whereClause = whereClause == null ? null : whereClause.trim();
  }

  public Double getPoint() {
    return point;
  }

  public void setPoint(Double point) {
    this.point = point;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject == null ? null : subject.trim();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getOther1() {
	return other1;
  }

  public void setOther1(String other1) {
	this.other1 = other1;
  }

  public String getOther2() {
	return other2;
  }

  public void setOther2(String other2) {
	this.other2 = other2;
  }

  public String getOther3() {
	return other3;
  }

  public void setOther3(String other3) {
	this.other3 = other3;
  }

  public String getOther4() {
    return other4;
  }

  public void setOther4(String other4) {
    this.other4 = other4;
  }
}
