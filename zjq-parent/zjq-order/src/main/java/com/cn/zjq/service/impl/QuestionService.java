package com.cn.zjq.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zjq.mapper.read.QuestionReadMapper;
import com.cn.zjq.mapper.write.QuestionWriteMapper;
import com.zjq.common.service.order.IQuestionService;
import com.zjq.common.util.QueryUtil;

@Service
public class QuestionService implements IQuestionService {

	@Autowired
	QuestionReadMapper questionReadMapper;

	@Autowired
	QuestionWriteMapper questionWriteMapper;

	@Override
	public Map<String, Object> initFormData() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", questionReadMapper.selectByPrimaryKey(1));
		return map;
	}

	@Override
	public List<Object> queryReferenceItem(QueryUtil queryUtil) {
		// TODO Auto-generated method stub
		return null;
	}

}
