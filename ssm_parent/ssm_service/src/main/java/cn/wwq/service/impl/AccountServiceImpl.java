package cn.wwq.service.impl;

import cn.wwq.mapper.AccountMapper;
import cn.wwq.pojo.Account;
import cn.wwq.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public List<Account> findAll() {
        return accountMapper.findAll();
    }
}
