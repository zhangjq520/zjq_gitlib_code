package com.cn.zjq.mapper.read;

import com.zjq.common.domain.order.Question;

public interface QuestionReadMapper {

  Question selectByPrimaryKey(Integer id);

  int checkCodeUnique(Question question);

}
