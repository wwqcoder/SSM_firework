package cn.wwq.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("/showUsername")
    public String showUsername(HttpServletRequest request){
        //通过SecurityContextHolder获得当前线程上绑定的SecurityContext对象
        SecurityContext context = SecurityContextHolder.getContext();
        //也可以通过session获得SecurityContext对象
        SecurityContext contextSession = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        //证明线程绑定的SecurityContext与session域中存储的SecurityContext是同一个
        System.out.println(context==contextSession);
        //获得Authentication认证对象
        Authentication authentication = contextSession.getAuthentication();
        //获取user对象
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }
}
