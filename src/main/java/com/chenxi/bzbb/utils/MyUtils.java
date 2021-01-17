package com.chenxi.bzbb.utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import java.util.UUID;

public class MyUtils {

    private static DefaultKaptcha defaultKaptcha;
    static {
        defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 设置边框，合法值：yes , no
        properties.setProperty("kaptcha.border", "yes");
        // 设置边框颜色，合法值： r,g,b (and optional alpha) 或者 white,
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 设置字体颜色， r,g,b 或者 white,black,blue.
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 设置图片宽度
        properties.setProperty("kaptcha.image.width", "200");
        // 设置图片高度
        properties.setProperty("kaptcha.image.height", "100");
        // 设置字体尺寸
        properties.setProperty("kaptcha.textproducer.font.size", "80");
        // 设置验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 设置字体
        properties.setProperty("kaptcha.textproducer.font.names", "楷体");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
    }

    public static String saveFile(MultipartFile f,String path) throws IOException {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        f.transferTo(new File(path+uuid));
        return uuid;
    }

    public static String getIpByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void download(String filename, HttpServletResponse res) throws IOException {
        // 发送给客户端的数据
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取filename
        bis = new BufferedInputStream(new FileInputStream(new File(filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }


    public static void createVerificationCode(HttpSession session, HttpServletResponse response) throws IOException {
        String createCode = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(createCode);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"jpeg",outputStream);
        session.setAttribute("imgCode",createCode);
    }

}
