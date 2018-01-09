package com.cn.zjq.config;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Author: omer 
 * Date  : 30-10-2015
 */
@Configuration
@Component
public class ApplicationConfiguration {
    private final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        log.info("----------------creating CommonsMultipartResolver bean------------");
        return new CommonsMultipartResolver();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        log.info("----------------creating multipartConfigElement bean---------------");
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("128KB");
        factory.setMaxRequestSize("128KB");


        return factory.createMultipartConfig();
    }

}
