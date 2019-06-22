package cn.wwq.controller;

import cn.wwq.pojo.Permission;
import cn.wwq.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping("/findAll")
    public String findAll(Model model){

        List<Permission> permissionList = permissionService.findAll();
        model.addAttribute("permissionList",permissionList);
        return "permission-list";
    }

    @RequestMapping("/save")
    public String save(Permission permission){
        permissionService.save(permission);
        return "redirect:/permission/findAll";
    }

    @RequestMapping("/findByPid")
    public String findByPid(Integer pid,Model model){

        List<Permission> parentPermissionList = permissionService.findByPid(pid);
        model.addAttribute("parentPermissionList",parentPermissionList);
        return "permission-add";

    }
}
