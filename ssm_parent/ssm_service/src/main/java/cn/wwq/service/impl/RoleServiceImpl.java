package cn.wwq.service.impl;

import cn.wwq.mapper.RoleMapper;
import cn.wwq.pojo.Role;
import cn.wwq.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public PageInfo<Role> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.findAll();
        PageInfo<Role> pageInfo = new PageInfo<>(roleList, 4);
        return pageInfo;
    }

    @Override
    public void save(Role role) {
        roleMapper.save(role);
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.findById(id);
    }

    @Override
    public void addPermissionToRole(Integer roleId, Integer[] ids) {
        //删除所有角色权限的关系
        roleMapper.deleteRolePermission(roleId);
        //为角色添加新的权限
        for (Integer id : ids) {
            roleMapper.addRolePermission(roleId,id);
        }


    }
}
