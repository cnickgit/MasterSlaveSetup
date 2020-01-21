package com.nick.ms.mybatisconfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @program: MasterSlaveSetup
 * @description:
 * @author: wsg
 * @create: 2020-01-21 14:25
 **/
@EnableAspectJAutoProxy
@Component
public class SlaveInterceptor implements Ordered {

    public static final Logger logger = LoggerFactory.getLogger(SlaveInterceptor.class);


    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Slave slave){
        try {
            logger.info("------set database connection read only------");
            DataBaseContextHolder.setDataBaseType(DataBaseContextHolder.DataBaseType.SLAVE);
            Object result = proceedingJoinPoint.proceed();
            return result;
        } catch (Throwable throwable) {
            logger.info("从库配置出错error:------"+throwable.getMessage());
            throwable.printStackTrace();
        } finally {
            DataBaseContextHolder.clearDataBaseType();
            logger.info("------clear database connection------");
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
