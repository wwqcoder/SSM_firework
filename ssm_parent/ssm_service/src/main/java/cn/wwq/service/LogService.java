package cn.wwq.service;

import cn.wwq.pojo.SysLog;

public interface LogService {
    /**
     * 保存日志
     * @param log
     */
    void save(SysLog log);
}
