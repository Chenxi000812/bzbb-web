package com.chenxi.bzbb.controller;

import com.chenxi.bzbb.service.GoodService;
import com.chenxi.bzbb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;

@Controller
@RolesAllowed("ADMIN")
public class IndexController {

    @Autowired
    GoodService goodService;
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/loginpage",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @GetMapping(value="/index")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/123")
    public String test(){return "123";}

    @GetMapping("/GoodManage")
    public String goodmanage(Model model){
        model.addAttribute("goodtypes",goodService.queryAllType());
        model.addAttribute("goodbrands",goodService.queryAllBrand());
        model.addAttribute("goods",goodService.queryAllgoods());
        return "GoodManage";
    }

    @GetMapping("/OrderManage")
    public String ordermanage(Model model,Integer status){
        model.addAttribute("orders",orderService.queryAllOrders(status));
        return "OrderManage";
    }
}
