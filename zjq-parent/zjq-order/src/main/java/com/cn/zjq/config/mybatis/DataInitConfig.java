package com.cn.zjq.config.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zjq.common.domain.order.Order;
import com.zjq.common.service.order.IOrderService;

@Component
public class DataInitConfig implements CommandLineRunner{
	
	@Autowired
	private IOrderService orderService;

	@Override
	public void run(String... args) throws Exception {
		Order obj = (Order) orderService.findById(1);
		
		orderService.update(obj);
		
	}

}
