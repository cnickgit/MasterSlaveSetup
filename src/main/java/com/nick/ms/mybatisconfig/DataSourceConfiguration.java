package com.nick.ms.mybatisconfig;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;


/**
 * @program: MasterSlaveSetup
 * @description:
 * @author: wsg
 * @create: 2020-01-21 12:55
 **/
@Configuration
//开启事务
@EnableTransactionManagement
public class DataSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Value("${durid.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "durid.master")
    public DataSource masterDataSource(){
        //注入master数据源
        DataSource masterDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        logger.info("-----注入master数据源-----:",masterDataSource);
        return masterDataSource;
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "durid.slave")
    public DataSource slaveDataSource(){
        //注入master数据源
        DataSource slaveDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        logger.info("-----注入slave数据源-----:",slaveDataSource);
        return slaveDataSource;
    }

    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow","localhost");
        reg.addInitParameter("deny","/deny");
        logger.info("druid console manager is init......",reg);
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        logger.info("druid filter register................",filterRegistrationBean);
        return filterRegistrationBean;
    }
}
