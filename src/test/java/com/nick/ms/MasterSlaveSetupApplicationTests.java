package com.nick.ms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class MasterSlaveSetupApplicationTests {

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveDataSource")
    private DataSource slaveDataSource;

    @Test
    void contextLoads() throws SQLException {
        Connection master = masterDataSource.getConnection("mysql","aa158747");
        System.out.println("master:"+master.getMetaData().getURL());
        Connection slave = slaveDataSource.getConnection("mysql", "aa158747");
        System.out.println("slave:"+slave.getMetaData().getURL());
    }

}
