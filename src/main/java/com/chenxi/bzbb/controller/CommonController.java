package com.chenxi.bzbb.controller;

import com.aliyuncs.exceptions.ClientException;
import com.chenxi.bzbb.utils.AjaxResult;
import com.chenxi.bzbb.utils.AliyunSmsUtils;
import com.chenxi.bzbb.utils.MyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CommonController {
    @Value("${my.filepath}")
    private String path;

    @GetMapping(value = "/img/{s}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImgs(@PathVariable String s , HttpServletResponse response){
        try{
            File file = new File(path+s);
            if (file.isDirectory()){
                return;
            }
            InputStream stream =new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();
            byte buff[] =new byte[1024];
            int len = 0;
            while ((len = stream.read(buff))>0){
                outputStream.write(buff,0,len);
            }
            stream.close();
            outputStream.close();
            outputStream.flush();
        }catch (Exception e){
            response.setStatus(404);
        }
    }

    @GetMapping("/sms/getCode")
    public AjaxResult getCode(HttpSession session,String mobile,String code) throws ClientException {
        String imgCode = (String) session.getAttribute("imgCode");
        if (mobile == null || imgCode == null || !imgCode.equalsIgnoreCase(code)){
            return AjaxResult.me().setSuccess(false).setMsg("验证码错误或参数有误");
        }
        String newcode = AliyunSmsUtils.Newcode();
        AliyunSmsUtils.sendSms(mobile,newcode);
        Map<String, Object> map = new HashMap<>(16);
        map.put("mobile", mobile);
        map.put("code", newcode);
        session.setAttribute("smsCode",map);
        System.out.println(newcode);
        return new AjaxResult().setMsg("发送成功");
    }

    @GetMapping(value = "/sms/imgCode",produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImgCode(HttpSession session, HttpServletResponse response) throws IOException {
        MyUtils.createVerificationCode(session,response);
    }
}
