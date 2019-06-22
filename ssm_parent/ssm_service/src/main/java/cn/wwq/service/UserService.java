package cn.wwq.service;

import cn.wwq.pojo.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    /**
     * 查询用户全部信息
     * @return
     */
    List<SysUser> findAll();

    /**
     * 根据分页查询用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<SysUser> findUserByPage(Integer pageNum, Integer pageSize);

    /**
     * 新增用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 用户名是否已经存在
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    SysUser findUserById(Integer id);

    /**
     * 添加角色到用户
     * @param userId
     * @param ids
     */
    void addRoleToUser(Integer userId, Integer[] ids);
}
