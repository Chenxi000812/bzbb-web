package com.chenxi.bzbb.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chenxi.bzbb.domain.model.*;
import com.chenxi.bzbb.service.GoodService;
import com.chenxi.bzbb.service.OrderService;
import com.chenxi.bzbb.utils.AjaxResult;
import com.chenxi.bzbb.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/admin")
@RolesAllowed("/ADMIN")
public class AdminController {

    @Autowired
    GoodService goodService;

    @Autowired
    OrderService orderService;

    @PostMapping("/gm/uploadtype")
    public AjaxResult uploadType(Goodtype goodtype){
        if ((goodtype = goodService.uploadType(goodtype)).getId()!=null){
            return AjaxResult.me().setSuccess(true).setObject(goodtype);
        }
        return AjaxResult.me().setSuccess(false).setMsg("操作失败");
    }
    @PostMapping("/gm/uploadbrand")
    public AjaxResult uploadType(Goodbrand goodbrand){
        if ((goodbrand = goodService.uploadBrand(goodbrand)).getId()!=null){
            return AjaxResult.me().setSuccess(true).setObject(goodbrand);
        }
        return AjaxResult.me().setSuccess(false).setMsg("操作失败");
    }

    @PostMapping("/gm/uploadgood")
    public AjaxResult uploadGood(Good good, String[] guiges, @RequestParam("goodimgs") MultipartFile[] goodimgs,@RequestParam("guigeimgs") MultipartFile[] guigeimgs) throws IOException {

        try {
            goodService.uploadGood(goodimgs,guigeimgs,good,guiges);
            return AjaxResult.me().setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false);
        }
    }

    @PostMapping("/gm/sendOutOrder")
    public AjaxResult sendOutOrder(Order order){
        return AjaxResult.me().setSuccess(orderService.sendOutOrder(order));
    }
}
