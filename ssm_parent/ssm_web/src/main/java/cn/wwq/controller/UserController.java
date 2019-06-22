package cn.wwq.controller;

import cn.wwq.mapper.RoleMapper;
import cn.wwq.pojo.Role;
import cn.wwq.pojo.SysUser;
import cn.wwq.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("user")
@Secured("ROLE_ADMIN")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("findAll2")
    public String findAll2(Model model){
        List<SysUser> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "user-list";
    }

    @RequestMapping("/findAll")
    public String findAll(Model model,
                           @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize",defaultValue = "2")Integer pageSize){
        PageInfo<SysUser> pageInfo = userService.findUserByPage(pageNum,pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "user-list";
    }

    @PostMapping("/checkUsername")
    @ResponseBody  //以流的格式返回
    public String checkUsername(String username){
        /**
         * true 可用  不存在
         *false
         */
        boolean isUnique = userService.checkUsername(username);
        return String.valueOf(isUnique);
    }


    @PostMapping("/save")
    public String save(SysUser sysUser){
        String encodePassword = bCryptPasswordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encodePassword);
        userService.save(sysUser);
        return "redirect:/user/findAll";
    }

    @RequestMapping("/findUserById")
    public String findUserById(Integer id,Model model){
        SysUser user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-show";
    }

    @RequestMapping("/addUserRoleUI")
    public String addUserRoleUI(Integer id,Model model){
        //查询所有角色列表
        List<Role> roleList = roleMapper.findAll();
        //查询当前用户的角色列表
        SysUser user = userService.findUserById(id);
        List<Role> userRoleList = user.getRoleList();

        StringBuilder sb = new StringBuilder();
        for (Role role : userRoleList) {
             sb.append(role.getId());
             sb.append(",");
        }

        model.addAttribute("roleList",roleList);
        model.addAttribute("userId",user.getId());
        model.addAttribute("userRoleStr",sb.toString());

        return "user-role-add";
    }

    @RequestMapping("/addRoleToUser")
    public String addRoleToUser(Integer userId,Integer[] ids){
        userService.addRoleToUser(userId,ids);
        return "redirect:/user/findAll";
    }
}
