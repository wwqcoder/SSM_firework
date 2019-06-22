package cn.wwq.mapper;

import cn.wwq.pojo.Role;

import java.util.List;

public interface RoleMapper {
    List<Role> findAll();

    void save(Role role);

    Role findById(Integer id);

    void deleteRolePermission(Integer roleId);

    void addRolePermission(Integer roleId, Integer id);
}
