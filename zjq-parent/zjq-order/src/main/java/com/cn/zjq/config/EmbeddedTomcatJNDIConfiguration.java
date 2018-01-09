package com.cn.zjq.config;

import javax.sql.DataSource;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * Author: omer 
 * Date  : 28-10-2015
 */
@Configuration
public class EmbeddedTomcatJNDIConfiguration implements EnvironmentAware {
    protected static final Logger log = LoggerFactory.getLogger(EmbeddedTomcatJNDIConfiguration.class);

    private static final String ENV_JNDI = "logic.jndi.";

    private static final String PROP_NAME_MASTER = "master.name";
    private static final String PROP_DRIVER_CLASS_NAME_MASTER = "master.driverClassName";
    private static final String PROP_URL_MASTER = "master.url";
    private static final String PROP_FACTORY_MASTER = "master.factory";
    private static final String PROP_MAX_ACTIVE_MASTER = "master.maxActive";
    private static final String PROP_MAX_IDLE_MASTER = "master.maxIdle";
    private static final String PROP_INITIAL_SIZE_MASTER = "master.initialSize";
    private static final String PROP_MAX_WAIT_MASTER = "master.maxWait";
    private static final String PROP_USERNAME_MASTER = "master.username";
    private static final String PROP_PASSWORD_MASTER = "master.password";
    private static final String PROP_TEST_ON_BORROW_MASTER = "master.test-on-borrow";
    private static final String PROP_VALIDATION_QUERY_MASTER = "master.validation-query";

    private static final String PROP_NAME_SLAVE= "slave.name";
    private static final String PROP_DRIVER_CLASS_NAME_SLAVE = "slave.driverClassName";
    private static final String PROP_URL_SLAVE = "slave.url";
    private static final String PROP_FACTORY_SLAVE = "slave.factory";
    private static final String PROP_MAX_ACTIVE_SLAVE = "slave.maxActive";
    private static final String PROP_MAX_IDLE_SLAVE = "slave.maxIdle";
    private static final String PROP_INITIAL_SIZE_SLAVE = "slave.initialSize";
    private static final String PROP_MAX_WAIT_SLAVE = "slave.maxWait";
    private static final String PROP_USERNAME_SLAVE = "slave.username";
    private static final String PROP_PASSWORD_SLAVE = "slave.password";
    private static final String PROP_TEST_ON_BORROW_SLAVE = "master.test-on-borrow";
    private static final String PROP_VALIDATION_QUERY_SLAVE = "master.validation-query";


    private static RelaxedPropertyResolver propertyResolver;

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

        	@Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.enableNaming();
                TomcatEmbeddedServletContainer container = 
                        super.getTomcatEmbeddedServletContainer(tomcat);
                for (Container child: container.getTomcat().getHost().findChildren()) {
                    if (child instanceof Context) {
                        ClassLoader contextClassLoader = 
                                ((Context)child).getLoader().getClassLoader();
                        Thread.currentThread().setContextClassLoader(contextClassLoader);
                        break;
                    }
                }
                return container;
            }

            @Override
            protected void postProcessContext(Context context) {
            	log.info("----------------slaveResource------------");
            	log.info("----------------masterResource------------");
                ContextResource masterResource = new ContextResource();
                masterResource.setName(propertyResolver.getProperty(PROP_NAME_MASTER, String.class, null));
                masterResource.setType(DataSource.class.getName());
                masterResource.setProperty("driverClassName", propertyResolver.getProperty(PROP_DRIVER_CLASS_NAME_MASTER, String.class, null));
                masterResource.setProperty("url", propertyResolver.getProperty(PROP_URL_MASTER, String.class, null));
                masterResource.setProperty("factory", propertyResolver.getProperty(PROP_FACTORY_MASTER, String.class, null));
                masterResource.setProperty("maxActive", propertyResolver.getProperty(PROP_MAX_ACTIVE_MASTER, String.class, null));
                masterResource.setProperty("maxIdle", propertyResolver.getProperty(PROP_MAX_IDLE_MASTER, String.class, null));
                masterResource.setProperty("initialSize", propertyResolver.getProperty(PROP_INITIAL_SIZE_MASTER, String.class, null));
                masterResource.setProperty("maxWait", propertyResolver.getProperty(PROP_MAX_WAIT_MASTER, String.class, null));
                masterResource.setProperty("username", propertyResolver.getProperty(PROP_USERNAME_MASTER, String.class, null));
//                masterResource.setProperty("testOnBorrow", propertyResolver.getProperty(PROP_TEST_ON_BORROW_MASTER, String.class, null));
//                masterResource.setProperty("validationQuery", propertyResolver.getProperty(PROP_VALIDATION_QUERY_MASTER, String.class, null));
                String password = propertyResolver.getProperty(PROP_PASSWORD_MASTER, String.class, null);
                if (password != null && !password.equalsIgnoreCase("")){
                    masterResource.setProperty("password", password);
                }

                ContextResource slaveResource = new ContextResource();
                slaveResource.setName(propertyResolver.getProperty(PROP_NAME_SLAVE, String.class, null));
                slaveResource.setType(DataSource.class.getName());
                slaveResource.setProperty("driverClassName", propertyResolver.getProperty(PROP_DRIVER_CLASS_NAME_SLAVE, String.class, null));
                slaveResource.setProperty("url", propertyResolver.getProperty(PROP_URL_SLAVE, String.class, null));
                slaveResource.setProperty("factory", propertyResolver.getProperty(PROP_FACTORY_SLAVE, String.class, null));
                slaveResource.setProperty("maxActive", propertyResolver.getProperty(PROP_MAX_ACTIVE_SLAVE, String.class, null));
                slaveResource.setProperty("maxIdle", propertyResolver.getProperty(PROP_MAX_IDLE_SLAVE, String.class, null));
                slaveResource.setProperty("initialSize", propertyResolver.getProperty(PROP_INITIAL_SIZE_SLAVE, String.class, null));
                slaveResource.setProperty("maxWait", propertyResolver.getProperty(PROP_MAX_WAIT_SLAVE, String.class, null));
                slaveResource.setProperty("username", propertyResolver.getProperty(PROP_USERNAME_SLAVE, String.class, null));
                password = propertyResolver.getProperty(PROP_PASSWORD_SLAVE, String.class, null);
//                masterResource.setProperty("testOnBorrow", propertyResolver.getProperty(PROP_TEST_ON_BORROW_SLAVE, String.class, null));
//                masterResource.setProperty("validationQuery", propertyResolver.getProperty(PROP_VALIDATION_QUERY_SLAVE, String.class, null));
                if (password != null && !password.equalsIgnoreCase("")){
                    slaveResource.setProperty("password", password);
                }

                context.getNamingResources().addResource(slaveResource);
                context.getNamingResources().addResource(masterResource);
            }
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        propertyResolver = new RelaxedPropertyResolver(environment, ENV_JNDI);
    }
}
