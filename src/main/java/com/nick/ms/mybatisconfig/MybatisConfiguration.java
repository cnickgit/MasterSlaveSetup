package com.nick.ms.mybatisconfig;


import com.sun.deploy.security.MozillaJSSDSASignature;
import org.apache.ibatis.javassist.scopedpool.SoftValueHashMap;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.management.loading.ClassLoaderRepository;
import javax.sql.DataSource;
import java.util.List;


/**
 * @program: MasterSlaveSetup
 * @description:
 * @author: wsg
 * @create: 2020-01-21 13:59
 **/
@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MybatisConfiguration extends MybatisAutoConfiguration{

    public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }

    @Resource(name="masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveDataSource")
    private DataSource slaveDataSource;

    public AbstractRoutingDataSource roundRobinDataSourceProxy(){
        ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
        SoftValueHashMap targetDataSource = new SoftValueHashMap();
        targetDataSource.put(DataBaseContextHolder.DataBaseType.MASTER,masterDataSource);
        targetDataSource.put(DataBaseContextHolder.DataBaseType.SLAVE,slaveDataSource);
        //默认数据源
        proxy.setDefaultTargetDataSource(masterDataSource);
        return proxy;
    }
}
