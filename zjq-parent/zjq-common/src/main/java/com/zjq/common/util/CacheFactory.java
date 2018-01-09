package com.zjq.common.util;

public class CacheFactory {

  public static ICache getCacheImpl() {

    return SysCache.getInstance();

  }

}
