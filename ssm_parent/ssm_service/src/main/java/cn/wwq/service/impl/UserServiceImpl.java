package cn.wwq.service.impl;

import cn.wwq.mapper.UserMapper;
import cn.wwq.pojo.Role;
import cn.wwq.pojo.SysUser;
import cn.wwq.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserMapper userMapper;

    /**
     * 通过用户名载入用户对象
     *
     * 在提交表单时提交时输入的username
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.loadUserByUsername(username);
        if (sysUser != null){
            //创建角色列表对象
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            //获得用户的角色列表
            List<Role> roleList = sysUser.getRoleList();
            for (Role role : roleList) {
                //创建角色对象
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getRoleName());
                authorities.add(simpleGrantedAuthority);
            }
            /**
             * 参数1  用户名
             * 参数2 密码
             * 参数3 角色列表
             */
            User user = new User(sysUser.getUsername(),sysUser.getPassword(), authorities);
            return user;
        }
        return null;
    }

    @Override
    public List<SysUser> findAll() {
        return userMapper.findAll();
    }

    @Override
    public PageInfo<SysUser> findUserByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> userList = userMapper.findAll();
        PageInfo<SysUser> pageInfo = new PageInfo<>(userList, 5);
        return pageInfo;
    }

    @Override
    public void save(SysUser sysUser) {
        userMapper.save(sysUser);
    }

    @Override
    public boolean checkUsername(String username) {

        SysUser sysUser = userMapper.checkUsername(username);
        return sysUser == null;

    }

    @Override
    public SysUser findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public void addRoleToUser(Integer userId, Integer[] ids) {

        //删除当前用户的所有关系
        userMapper.deleteUserRoleRelation(userId);

        for (Integer roleId : ids) {
            userMapper.addRoleToUser(userId,roleId);
        }


    }
}
