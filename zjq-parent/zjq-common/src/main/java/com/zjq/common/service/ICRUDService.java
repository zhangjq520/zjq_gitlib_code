package com.zjq.common.service;


import java.util.List;

import com.zjq.common.exception.BusinessException;
import com.zjq.common.util.QueryUtil;

public interface ICRUDService {

  public int insert(Object obj) throws BusinessException;

  public Object get(Integer id) throws BusinessException;

  public void update(Object obj) throws BusinessException;

  public void delete(String ids) throws BusinessException;

  public List<Object> query(QueryUtil queryUtil);

  public Boolean validate(Object obj) throws BusinessException;

  public Boolean validateForInsert(Object obj) throws BusinessException;

  public Boolean validateForUpdate(Object obj) throws BusinessException;

  public Boolean validateForDelete(Object obj) throws BusinessException;

}