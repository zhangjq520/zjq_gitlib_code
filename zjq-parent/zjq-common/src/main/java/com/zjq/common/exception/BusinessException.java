package com.zjq.common.exception;

import com.zjq.common.ErrorMessageEnum;

public class BusinessException extends Exception {

  private static final long serialVersionUID = 3483946438317534602L;

  protected String errorCode;

  protected Object[] param;

  public BusinessException() {
    super();
  }

  public BusinessException(String msg) {
    super(msg);
  }

  public BusinessException(ErrorMessageEnum errorMessageEnum, Object... param) {
    super(errorMessageEnum.getDesc());
    this.errorCode = errorMessageEnum.getCode();
    this.param = param;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public Object[] getParam() {
    return param;
  }

}
