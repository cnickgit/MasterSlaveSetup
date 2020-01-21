package com.nick.ms.mybatisconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @program: MasterSlaveSetup
 * @description:
 * @author: wsg
 * @create: 2020-01-21 14:05
 **/
public class ReadWriteSplitRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataBaseContextHolder.getDataBaseType();
    }
}
