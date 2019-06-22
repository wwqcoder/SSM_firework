package cn.wwq.service;

import cn.wwq.pojo.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {
    /**
     * 通过分页查询角色列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Role> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 保存角色
     * @param role
     */
    void save(Role role);

    Role findById(Integer id);

    /**
     * 添加权限到角色
     * @param roleId
     * @param ids
     */
    void addPermissionToRole(Integer roleId, Integer[] ids);
}
