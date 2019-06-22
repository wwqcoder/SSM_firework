package cn.wwq.controller;


import cn.wwq.pojo.PageBean;
import cn.wwq.pojo.Product;
import cn.wwq.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("product")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 查询所有产品到页面渲染
     *
     * @return
     */
    @RequestMapping("/findAll2")
    public ModelAndView findAll2(){
        List<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productList",products);
        modelAndView.setViewName("product-list");
        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(Product product){
        productService.save(product);
        return "redirect:/product/findAll";
    }

    @RequestMapping("/editUI")
    public String editUI(Integer id, Model model){
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "product-update";
    }

    @PostMapping("/update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/product/findAll";
    }

    @RequestMapping("deleById")
    public String deleById(Integer id){
        productService.deleById(id);
        return "redirect:/product/findAll";
    }

    @RequestMapping("deleByIds")
    public String deleByIds(Integer[] ids){
        productService.deleByIds(ids);
        return "redirect:/product/findAll";
    }

    /**
     * 查询所有产品到页面渲染  手动分页
     * pageNum pageSize
     * @return
     */
    @RequestMapping("/findAll3")
    public ModelAndView findAll3(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize",required = false,defaultValue = "1")Integer pageSize){


        PageBean<Product> pageBean =  productService.findByPage(pageNum,pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageBean",pageBean);
        modelAndView.setViewName("product-list");
        return modelAndView;
    }

    /**
     * 查询所有产品到页面渲染  手动分页
     * pageNum pageSize
     * @return
     */
    @RequestMapping("/findAll")
    public ModelAndView findAll(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize",required = false,defaultValue = "1")Integer pageSize){


        PageInfo<Product> pageInfo =  productService.findByPageHelper(pageNum,pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("product-list");
        return modelAndView;
    }





}
