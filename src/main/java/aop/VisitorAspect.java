package aop;

import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pojo.Visitor;
import service.VisitorService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 在进行topic的相关操作时向数据库添加访问操作
 */
@Component
@Aspect
public class VisitorAspect {
    @Autowired
    VisitorService visitorService;
    @Before("execution(* controller.TopicController.*(..))")
    public void addVisitorAspect(){
        Visitor visitor=new Visitor();
        HttpServletRequest request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        UserAgent userAgent=UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //获取用户浏览器名
        String userbrowser=userAgent.getBrowser().toString();
        visitor.setIP(ip);
        visitor.setDevice(userbrowser);
        visitor.setVisitTime(new Date());

        visitorService.insert(visitor);
    }
}
