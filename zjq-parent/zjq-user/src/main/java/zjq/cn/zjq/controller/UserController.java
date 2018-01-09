package zjq.cn.zjq.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjq.common.exception.BusinessException;
import com.zjq.common.service.order.IOrderService;
import com.zjq.common.service.user.UserService;

import zjq.cn.zjq.entity.User;

@RequestMapping(value="/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value="/save")  
    public String save(User user) throws BusinessException{  
		userService.insert(user);  
          
        return "success";  
    }  
      
    @RequestMapping(value="/list")  
    public Map<String, Object> list(){  
        Map<String, Object> map = new HashMap<String, Object>();  
        map.put("data", userService.listAll());  
        return map;  
    }  
    
    @RequestMapping(value="/test")  
    public Map<String, Object> test(){  
    	Map<String, Object> map = new HashMap<String, Object>();
    	userService.test();
        return map;  
    }
}
