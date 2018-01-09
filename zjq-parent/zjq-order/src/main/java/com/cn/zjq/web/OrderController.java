package com.cn.zjq.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjq.common.domain.order.Order;
import com.zjq.common.exception.BusinessException;
import com.zjq.common.service.order.IOrderService;
import com.zjq.common.service.user.UserService;

@RequestMapping(value="/order")
@RestController
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
//	@Autowired
//	private UserService userService;
	
	@RequestMapping(value="/all")  
    public Map<String, Object> all(){
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("data", orderService.findById(1));
        return map;
    }
	
	@RequestMapping(value="/save")  
    public Map<String, Object> save() throws BusinessException{  
		Map<String, Object> map= new HashMap<String, Object>();
		Order order = new Order();
		order.setUserId(250);
		order.setCreateTime(new Date());
		order.setAmount(new BigDecimal(2000.00));
		order.setOrderNumber("777");
		orderService.save(order);
		map.put("data", "");  
		map.put("status", "success"); 
        return map;  
    }
	
//	@RequestMapping(value="/test")  
//    public Map<String, Object> test(){  
//		Map<String, Object> map= new HashMap<String, Object>();
//		map.put("data", userService.listAll());  
//        return map;  
//    }
}
