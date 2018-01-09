package com.zjq.common.service.order;

import java.util.List;
import java.util.Map;

import com.zjq.common.util.QueryUtil;

public interface IQuestionService{

  /**
   * initialize form data
   * 
   * @return
   */
  public Map<String, Object> initFormData();

  /**
   * query reference item
   * 
   * @param queryUtil
   * @return
   */
  public List<Object> queryReferenceItem(QueryUtil queryUtil);

}
