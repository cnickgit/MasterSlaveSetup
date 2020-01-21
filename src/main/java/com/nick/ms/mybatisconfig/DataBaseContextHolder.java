package com.nick.ms.mybatisconfig;

/**
 * @program: MasterSlaveSetup
 * @description:
 * @author: wsg
 * @create: 2020-01-21 13:52
 **/
public class DataBaseContextHolder {

    public enum DataBaseType{
        MASTER,SLAVE
    }

    private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<DataBaseType>();

    public static void setDataBaseType(DataBaseType dataBaseType){
        if(dataBaseType == null) {
            throw new NullPointerException();
        }
        contextHolder.set(dataBaseType);
    }

    public static DataBaseType getDataBaseType(){
        return contextHolder.get() == null ? DataBaseType.MASTER:DataBaseType.SLAVE;
    }

    public static void clearDataBaseType(){
        contextHolder.remove();
    }
}
