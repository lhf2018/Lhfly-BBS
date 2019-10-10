package aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志写入，路径在log4j.properties中有
 */
@Component
@Aspect
public class LogAspect {
    private final static Logger logger= Logger.getLogger(LogAspect.class);


    @Before("execution(* controller.*.*(..))")
    public void loginLogAspect(JoinPoint joinPoint) throws InterruptedException {
        String methodName=joinPoint.getSignature().toShortString();
        String args= Arrays.toString(joinPoint.getArgs());
        logger.info("---Before method "+methodName+" invoke, param:" +args+"---");
    }
}
