package com.zjq.common.service.order;

import com.zjq.common.domain.order.Order;
import com.zjq.common.exception.BusinessException;

public interface IOrderService {

	Object findById(Integer id);
	
	void update(Object obj);
	
	void save(Order order);
}
