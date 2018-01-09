package com.cn.zjq.mapper.write;

import java.util.Map;

import com.zjq.common.domain.order.Question;

public interface QuestionWriteMapper {

  int deleteByPrimaryKey(Question record);

  int insertSelective(Question record);

  int updateByPrimaryKeySelective(Question record);

  void updateCorrectResponseById(Map<String, Object> map);

  int deleteOrgQuestions(Integer questionId);

}
