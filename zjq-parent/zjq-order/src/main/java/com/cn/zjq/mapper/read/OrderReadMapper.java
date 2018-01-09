package com.cn.zjq.mapper.read;

import com.zjq.common.domain.order.Order;

public interface OrderReadMapper {

    Order selectByPrimaryKey(Integer id);
}