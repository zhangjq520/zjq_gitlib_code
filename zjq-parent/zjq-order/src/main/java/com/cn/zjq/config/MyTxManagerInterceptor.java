package com.cn.zjq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.codingapi.tx.dubbo.interceptor.TxManagerInterceptor;

/**
 * Created by lorne on 2017/6/28.
 */
@Configuration
@ComponentScan(basePackages="com.codingapi.tx.*")
public class MyTxManagerInterceptor {


    @Bean
    public TxManagerInterceptor requestInterceptor(){
        return new TxManagerInterceptor();
    }
}
