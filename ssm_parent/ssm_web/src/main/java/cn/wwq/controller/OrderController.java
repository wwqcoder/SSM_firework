package cn.wwq.controller;

import cn.wwq.pojo.Order;
import cn.wwq.service.ProductService;
import cn.wwq.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<Order> orders = orderService.findAll();
        model.addAttribute("orderList",orders);
        return "order-list";
    }

    @RequestMapping("/ProductUI")
    public String ProductUI(Model model){
        model.addAttribute("plist", productService.findAll());
        return "order-add";
    }

    @PostMapping("/save")
    public String save(Order order){
        orderService.save(order);
        return "redirect:/order/findAll";
    }

    @RequestMapping("/editUI")
    public String editUI(Integer id,Model model){
        Order order = orderService.findOrderById(id);
        model.addAttribute("orders",order);
        return "order-update";
    }

    @PostMapping("/update")
    public String update(Order order){
        orderService.update(order);
        return "redirect:/order/findAll";
    }

    @RequestMapping("/dele")
    public String dele(Integer id){
        orderService.dele(id);
        return "redirect:/order/findAll";
    }

    @PostMapping("/deleByIds")
    public String deleByIds(Integer[] ids){
        orderService.deleByIds(ids);
        return "redirect:/order/findAll";
    }

}
