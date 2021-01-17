package com.chenxi.bzbb.controller;
import com.chenxi.bzbb.domain.model.Fastmail;
import com.chenxi.bzbb.domain.model.Goodbrand;
import com.chenxi.bzbb.domain.model.Goodtype;
import com.chenxi.bzbb.service.AppService;
import com.chenxi.bzbb.utils.AjaxResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/app")
public class AppController {

    @Value("${my.filepath}")
    private String path;

    @Autowired
    AppService appService;

    @GetMapping(value = "/download")
    public void downlaod(HttpServletResponse response){

        String fileName = "bzbb.apk"; //下载的文件名
        // 如果文件名不为空，则进行下载
        //设置文件路径
        String realPath = path+"/apk";
        File file = new File(realPath, fileName);

        // 如果文件名存在，则进行下载
        if (file.exists()) {

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Length",""+file.length());
            // 实现文件下载
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);
                OutputStream os = response.getOutputStream();
                IOUtils.copy(fis,os);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    @GetMapping("/index")
    public AjaxResult index(){
        return AjaxResult.me().setSuccess(true).setObject(appService.index());
    }

    @GetMapping("/getGoodsByType")
    public AjaxResult goods(Integer type){
        return AjaxResult.me().setObject(appService.queryGoodsByType(type));
    }


    @GetMapping("/querygoodsp")
    public AjaxResult goodSp(Long gid){
        return AjaxResult.me().setSuccess(true).setObject(appService.querySpByGoodId(gid));
    }

    @GetMapping("/getGoodtype")
    public List<Goodtype> goodtypes(){
        return appService.queryAllGoodType();
    }

    @GetMapping("/getGoodbrand")
    public List<Goodbrand> goodbrands(){
        return appService.queryAllGoodBrand();
    }
    @GetMapping("/getFastMail")
    public List<Fastmail> fastmails(){
        return appService.queryAllFastMail();
    }

}
