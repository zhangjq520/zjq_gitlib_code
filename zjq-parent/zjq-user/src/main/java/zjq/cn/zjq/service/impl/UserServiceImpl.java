package zjq.cn.zjq.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zjq.cn.zjq.dao.UserDao;
import zjq.cn.zjq.entity.User;

import com.codingapi.tx.annotation.TxTransaction;
import com.zjq.common.domain.order.Order;
import com.zjq.common.service.order.IOrderService;
import com.zjq.common.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IOrderService orderService;
	
	@Override
	public int insert(Object object) {
		User user = (User) object;
		userDao.save(user);
		return user.getId();
	}

	@Override
	public List<Object> listAll() {
		List<Object> list = new ArrayList<Object>();
		List<User> users = (List<User>) userDao.findAll();
		for (User user : users) {
			list.add(user);
		}
		return list;
	}

	@Override
	public Object findById(Integer id) {
		return userDao.findOne(id);
	}

	@Override
	public void update(Object obj) {
		User user = (User) obj;
		userDao.save(user);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
    @TxTransaction
	public void test() {
		User user = new User();
		user.setUserName("test");
		user.setPassword("555");
		insert(user);
		
		Order order = new Order();
		order.setUserId(user.getId());
		order.setCreateTime(new Date());
		order.setAmount(new BigDecimal(555.00));
		order.setOrderNumber("555");
		orderService.save(order);
		
//		System.out.println(order.getId());
		
//		int a = 1/0;
	}

}
