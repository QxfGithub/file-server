package com.qxf.fileserver.aspect;

import com.qxf.fileserver.annotation.LOG;
import com.qxf.fileserver.dao.OperateLogDAO;
import com.qxf.fileserver.dao.domain.OperateLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProcessLog {

    static Logger Logger = LoggerFactory.getLogger(ProcessLog.class);

    @Autowired
    private OperateLogDAO OperateLogDAO;

    @Pointcut("@annotation(com.qxf.fileserver.annotation.LOG)")
    private void myPointCut() {
    }

    @Around("myPointCut()&&@annotation(log)")
    public Object go(ProceedingJoinPoint pjp, LOG log) {
        Object proceed = null;

        //执行的方法名
        String methodName = pjp.getSignature().getName();
        Logger.info("将要执行：" + methodName + "方法；");

        try{
            proceed = pjp.proceed();
            Logger.info("===开始保存日志===");

            //保存日志
            OperateLog operateLog = new OperateLog();
            Object[] args = pjp.getArgs();
            for (Object obj : args) {
                BeanUtils.copyProperties(obj,operateLog);
            }
            operateLog.setIsDeleted(0);
            OperateLogDAO.save(operateLog);

            Logger.info("===结束保存日志===");

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return proceed;
    }

}
