package zjq.cn.zjq.confiig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ProviderConfig;
import com.codingapi.tx.dubbo.interceptor.TxManagerInterceptor;

/**
 * Created by lorne on 2017/6/28.
 */
@Configuration
@ComponentScan(basePackages="com.codingapi.tx.*")
public class MyConfiguration {

    @Bean
    public TxManagerInterceptor requestInterceptor(){
        return new TxManagerInterceptor();
    }
}
