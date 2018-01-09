package com.zjq.common.service.user;

import java.util.List;

public interface UserService{
	int insert(Object object);
	
	List<Object> listAll();
	
	Object findById(Integer id);
	
	void update(Object obj);
	
	void test();
}
