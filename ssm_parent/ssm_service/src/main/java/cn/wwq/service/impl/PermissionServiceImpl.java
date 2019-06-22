package cn.wwq.service.impl;

import cn.wwq.mapper.PermissionMapper;
import cn.wwq.pojo.Permission;
import cn.wwq.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }

    @Override
    public void save(Permission permission) {
        permissionMapper.save(permission);
    }

    @Override
    public List<Permission> findByPid(Integer pid) {
        return permissionMapper.findByPid(pid);
    }

    @Override
    public List<Permission> findNotParentList() {
        return permissionMapper.findNotParentList();
    }
}
