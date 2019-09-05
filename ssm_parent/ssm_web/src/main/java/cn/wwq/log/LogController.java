package cn.wwq.log;

import cn.wwq.pojo.SysLog;
import cn.wwq.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Aspect   切面类
 */
@Component
//@Aspect
public class LogController {

    @Autowired
    LogService logService;

    @Autowired
    HttpServletRequest request;

    /**
     * 切入点
     */
    //@Pointcut("execution(* cn.wwq.controller.*.*(..))")
    public void pc(){}

    /**
     * 前置增强的织入
     */
    //@Before("pc()")
    public void before(JoinPoint joinPoint){
        //创建日志对象

        //访问时间
        SysLog log = new SysLog();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        log.setVisitTime(date);

        //访问者
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = (User) authentication.getPrincipal();

        String username = user.getUsername();
        log.setUsername(username);

        //访问者的IP
        String ip = request.getRemoteAddr();
        log.setIp(ip);
        //访问的功能  全类名.方法
        //全类名
        Class aClass = joinPoint.getTarget().getClass();
        String className = aClass.getName();
        //方法
        String methodName = joinPoint.getSignature().getName();
        log.setMethod(className+"#"+methodName);
        //保存日志
        logService.save(log);
    }
}
