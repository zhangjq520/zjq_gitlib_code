package com.cn.zjq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zjq.mapper.read.OrderReadMapper;
import com.cn.zjq.mapper.write.OrderWriteMapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.zjq.common.domain.order.Order;
import com.zjq.common.service.order.IOrderService;

@Service
public class OrderService implements IOrderService{
	
	@Autowired
	private OrderReadMapper orderReadMapper;
	
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Override
	public Object findById(Integer id) {
		return orderReadMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Object obj) {
		Order order = (Order) obj;
		orderWriteMapper.updateByPrimaryKey(order);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(Order order){
		orderWriteMapper.insert(order);
		
		int a = 1/0;
	}

}
