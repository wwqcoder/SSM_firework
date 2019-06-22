package cn.wwq.mapper;

import cn.wwq.pojo.SysUser;

import java.util.List;

public interface UserMapper {
    /**
     *
     * @param username
     * @return
     */
    SysUser loadUserByUsername(String username);

    List<SysUser> findAll();

    void save(SysUser sysUser);

    SysUser checkUsername(String username);

    SysUser findUserById(Integer id);

    void deleteUserRoleRelation(Integer userId);

    void addRoleToUser(Integer userId, Integer roleId);
}
