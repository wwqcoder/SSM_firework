package cn.wwq.service;

import cn.wwq.pojo.Permission;

import java.util.List;

public interface PermissionService {
    /**
     * 查询权限列表
     * @return
     */
    List<Permission> findAll();

    /**
     * 新增权限
     * @param permission
     */
    void save(Permission permission);

    /**
     * 根据PID
     * @param pid
     * @return
     */
    List<Permission> findByPid(Integer pid);

    List<Permission> findNotParentList();
}
