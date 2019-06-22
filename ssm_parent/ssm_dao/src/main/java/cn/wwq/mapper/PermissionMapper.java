package cn.wwq.mapper;

import cn.wwq.pojo.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> findAll();

    void save(Permission permission);

    List<Permission> findByPid(Integer pid);

    List<Permission> findNotParentList();
}
