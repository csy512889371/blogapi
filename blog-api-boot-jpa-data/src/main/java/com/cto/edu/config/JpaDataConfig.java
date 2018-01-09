package com.cto.edu.config;

import com.cto.edu.common.repository.CustomRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

@SpringBootConfiguration
@EntityScan("com.cto.**.entity")
@EnableJpaRepositories(basePackages = {"com.cto"},
        repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class
)
public class JpaDataConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public MethodInvokingFactoryBean repositoryHelperFactoryBean() {
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("com.cto.edu.common.repository.RepositoryHelper.setEntityManagerFactory");
        factoryBean.setArguments(entityManagerFactory);
        return factoryBean;
    }

}
