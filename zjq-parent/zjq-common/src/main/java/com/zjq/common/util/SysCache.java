package com.zjq.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SysCache implements ICache {

  private Logger logger = LoggerFactory.getLogger(SysCache.class);

  private static final ConcurrentHashMap<String, Object> sessionCache = new ConcurrentHashMap<String, Object>(256);

  private static final ConcurrentHashMap<String, Object> generalCache = new ConcurrentHashMap<String, Object>(256);

  private static SysCache instance = new SysCache();

  private Map<String, Object> sysconfig;

  private SysCache() {

    logger.debug("====== new cache instance created ======");

  }

  public static SysCache getInstance() {
    return instance;
  }

  /**
   * ===================Session related===================
   */
  public void setSession(String key, Object value) {
    sessionCache.put(key, value);
  }

  public Object getSession(String token) {
    return sessionCache.get(token);
  }

  public void removeSession(String token) {
    sessionCache.remove(token);
  }

  public ConcurrentHashMap<String, Object> getAllSessions() {
    return sessionCache;
  }

  /**
   * ===================General related===================
   */
  @Override
  public void setCacheItem(String cacheKey, Object value) {
    generalCache.put(cacheKey, value);
  }

  @Override
  public Object getCacheItem(String cachekey) {
    return generalCache.get(cachekey);
  }

  @Override
  public void removeCacheItem(String cacheKey) {
    generalCache.remove(cacheKey);
  }

  @Override
  public ConcurrentHashMap<String, Object> getAllCachedItems() {
    return generalCache;
  }

@Override
public void removeChildCodes(String code) {
	// TODO Auto-generated method stub
	
}

@Override
public String getSysConfig(String key) {
	// TODO Auto-generated method stub
	return null;
}

}