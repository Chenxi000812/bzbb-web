package com.chenxi.bzbb.controller;
import com.alipay.api.AlipayApiException;
import com.chenxi.bzbb.domain.model.*;
import com.chenxi.bzbb.domain.model.RequestModel.CreateOrderRequestModel;
import com.chenxi.bzbb.service.GoodService;
import com.chenxi.bzbb.service.OrderService;
import com.chenxi.bzbb.service.UserService;
import com.chenxi.bzbb.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usr")
@RolesAllowed("USER")
public class AppUserController {

    @Autowired
    UserService userService;

    @Autowired
    GoodService goodService;

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    public AjaxResult createOrder(Authentication authentication, @RequestBody CreateOrderRequestModel model) throws AlipayApiException {
        User user= (User) authentication.getPrincipal();
        if (user!=null){
            return orderService.createOrder(user, model);
        }
        return AjaxResult.me().setSuccess(false);
    }

    @GetMapping("/userAddress")
    public AjaxResult getUserAddress(Authentication authentication,boolean def){
        User user = (User) authentication.getPrincipal();
        List<Shippingaddress> shippingaddresses = orderService.queryUserAddress(user, def);
        if (shippingaddresses.isEmpty()){
            return AjaxResult.me().setSuccess(false).setMsg("空空如也");
        }
        return AjaxResult.me().setObject(shippingaddresses);
    }

    @PostMapping("/createAddress")
    public AjaxResult createUserAddress(Authentication authentication,@RequestBody Shippingaddress shippingaddress){
        System.out.println(shippingaddress);
        User user = (User) authentication.getPrincipal();
        if (user!=null&&orderService.createUserAddress(user,shippingaddress)){
            return AjaxResult.me().setObject(shippingaddress);
        }
        return AjaxResult.me().setSuccess(false).setMsg("不能超过10个地址");
    }

    @PostMapping("/updateAddress")
    public AjaxResult updateUserAddress(Authentication authentication,@RequestBody Shippingaddress shippingaddress){
        User user = (User) authentication.getPrincipal();
        if (user!=null&&orderService.updateUserAddress(user,shippingaddress)){
            return AjaxResult.me().setObject(shippingaddress);
        }
        return AjaxResult.me().setSuccess(false);
    }

    @GetMapping("/delAddress")
    public AjaxResult delUserAddress(Authentication authentication,Long id){
        User user = (User) authentication.getPrincipal();
        if (user!=null&&orderService.delUserAddress(user,id)){
            return AjaxResult.me();
        }
        return AjaxResult.me().setSuccess(false);
    }

    @GetMapping("/getWaitingForPayOrders")
    public AjaxResult getWaitingForPayOrders(Authentication authentication,boolean isStatus0){
        User user = (User) authentication.getPrincipal();
        if (user!=null){
            return AjaxResult.me().setObject(orderService.queryUserWaitingForPayOrders(user,isStatus0));
        }
        return AjaxResult.me().setSuccess(false);
    }


    @GetMapping("/paySuccess")
    public AjaxResult checkOrder(Authentication authentication,String outTradeNo){
        User user = (User) authentication.getPrincipal();
        if (user!=null){
            return AjaxResult.me().setSuccess(orderService.checkOrder(user,outTradeNo));
        }
        return AjaxResult.me().setSuccess(false);
    }

    @GetMapping("/myCoupons")
    public AjaxResult getUserCoupons(Authentication authentication,Integer status){
        User user = (User) authentication.getPrincipal();
        if (user!=null){
            return AjaxResult.me().setObject(orderService.queryUserCoupons(user,status));
        }
        return AjaxResult.me().setSuccess(false);
    }

    @GetMapping("/continueOrder")
    public AjaxResult continueUserOrder(Authentication authentication,String orderId) throws AlipayApiException {
        User user = (User) authentication.getPrincipal();
        if (user!=null){
            String s = orderService.continueOrder(user, orderId);
            return AjaxResult.me().setSuccess(s != null).setObject(s);
        }
        return AjaxResult.me().setSuccess(false);
    }

    @GetMapping("/cancelOrder")
    public AjaxResult cancelUserOrder(Authentication authentication,String orderId){
        User user = (User) authentication.getPrincipal();
        if (user!=null){
            return AjaxResult.me().setSuccess(orderService.cancelUserOrder(user,orderId));
        }
        return AjaxResult.me().setSuccess(false);
    }

    @GetMapping("/confirmOrder")
    public AjaxResult confirmUserOrder(Authentication authentication,String orderId){
        User user = (User) authentication.getPrincipal();
        if (user!=null){
            return AjaxResult.me().setSuccess(orderService.confirmUserOrder(user,orderId)).setMsg("确认成功,感谢惠顾");
        }
        return AjaxResult.me().setSuccess(false).setMsg("参数异常");
    }
}
