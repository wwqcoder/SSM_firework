package cn.wwq.controller;

import cn.wwq.pojo.Permission;
import cn.wwq.pojo.Role;
import cn.wwq.service.PermissionService;
import cn.wwq.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @RequestMapping("/findAll")
    public String findAll(Model model,
        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
        @RequestParam(value = "pageSize",defaultValue = "2")Integer pageSize
    ){
        PageInfo<Role> pageInfo = roleService.findByPage(pageNum,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "role-list";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Role role){
        roleService.save(role);
        return "redirect:/role/findAll";
    }

    @RequestMapping("/addPermissionUI")
    public String addPermissionUI(Integer id,Model model){
        //获得全部的非父的权限列表
        List<Permission> permissionList = permissionService.findNotParentList();
        //获得当前角色的权限列表
        Role role = roleService.findById(id);
        List<Permission> rolePermissionList = role.getPermissionList();
        StringBuilder sb = new StringBuilder();
        for (Permission permission : rolePermissionList) {
            sb.append(permission.getId());
            sb.append(",");
        }
        model.addAttribute("permissionList",permissionList);
        model.addAttribute("role",role);
        model.addAttribute("permissionListStr",sb.toString());

        return "role-permission-add";
    }

    @RequestMapping("addPermissionToRole")
    public String addPermissionToRole(Integer roleId,Integer[] ids){
        roleService.addPermissionToRole(roleId,ids);
        return "redirect:/role/findAll";
    }


}
