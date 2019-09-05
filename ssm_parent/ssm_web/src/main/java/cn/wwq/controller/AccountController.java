package cn.wwq.controller;

import cn.wwq.pojo.Account;
import cn.wwq.pojo.Order;
import cn.wwq.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Account> findAll(){
       return accountService.findAll();
    }

    /**
     * 每隔5秒刷新一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduled(){
        logger.info("定时任务一已执行!");
    }
}
