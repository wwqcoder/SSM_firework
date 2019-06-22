package cn.wwq.service.impl;

import cn.wwq.mapper.LogMapper;
import cn.wwq.pojo.SysLog;
import cn.wwq.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public void save(SysLog log) {
        logMapper.save(log);
    }
}
