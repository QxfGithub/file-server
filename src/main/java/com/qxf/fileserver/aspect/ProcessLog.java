package com.qxf.fileserver.aspect;

import com.qxf.fileserver.annotation.LOG;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProcessLog {

    @Pointcut("@annotation(com.qxf.fileserver.annotation.LOG)")
    private void myPointCut() {
    }

    @Before("myPointCut() &&  @annotation(log)")
    public void myAfter(JoinPoint joinPoint, LOG log) throws NoSuchMethodException, SecurityException {
        //saveLog(log);
        System.out.println("====================================================");
        System.out.println("operateType:" + log.operateType() + " ,logStatus:" + log.logStatus() + " ,operator:" + log.operator());
        System.out.println("====================================================");

    }

}
