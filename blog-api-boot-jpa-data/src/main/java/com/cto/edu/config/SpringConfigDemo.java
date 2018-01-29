package com.cto.edu.config;

import com.cto.edu.common.repository.CustomRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

//@Configuration
//@ComponentScan(basePackages = "com.cto.edu")
//@PropertySource(value = {"classpath:jdbc.properties", "classpath:config.properties"})
//@ImportResource({"classpath:some-context.xml","classpath:other-context.xml"})
public class SpringConfigDemo {

    /*    @Value("${jdbc.url}")
        private String jdbcUrl;

        @Value("${jdbc.driverClassName}")
        private String driverClassName;


        @Bean(destroyMethod = "close", initMethod = "init")
        public DataSource dataSource() {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(driverClassName);
            return null;
        }*/
/*    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }*/
}
