package com.cn.zjq.mapper.write;

import com.zjq.common.domain.order.Order;

public interface OrderWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}