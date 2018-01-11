package com.cto.edu.config.druid.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({DruidDataSource.class})
@ConditionalOnProperty(
        name = {"spring.datasource.type"},
        havingValue = "com.alibaba.druid.pool.DruidDataSource",
        matchIfMissing = true
)
@EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DruidConfiguration {
    @Bean
    public DruidDataSource dataSource(DataSourceProperties dataSourceProperties, DruidDataSourceProperties druidDataSourceProperties) {
        DruidDataSource druidDataSource = dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
        druidDataSource.configFromPropety(druidDataSourceProperties.toProperties());
        druidDataSource.setInitialSize(druidDataSourceProperties.getInitialSize());
        druidDataSource.setMinIdle(druidDataSourceProperties.getMinIdle());
        druidDataSource.setMaxActive(druidDataSourceProperties.getMaxActive());
        druidDataSource.setMaxWait(druidDataSourceProperties.getMaxWait());
        druidDataSource.setConnectProperties(druidDataSourceProperties.getConnectionProperties());
        return druidDataSource;
    }
   /* @Bean
    public ServletRegistrationBean druidStatViewServlet(DruidDataSourceProperties druidDataSourceProperties) {
        if (StringUtils.isEmpty(druidDataSourceProperties.getServletPath())) {
            druidDataSourceProperties.setServletPath("/druid/*");
        }
        return new ServletRegistrationBean(new StatViewServlet(), druidDataSourceProperties.getServletPath());
    }
    @Bean
    public FilterRegistrationBean druidWebStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
        return filterRegistrationBean;
    }*/
}
