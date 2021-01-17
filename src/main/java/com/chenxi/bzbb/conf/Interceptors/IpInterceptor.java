package com.chenxi.bzbb.conf.Interceptors;

import com.chenxi.bzbb.utils.MyUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IpInterceptor implements HandlerInterceptor {
    private static List<String> allowIps = new ArrayList<>();

    public IpInterceptor(){
        allowIps.add("0:0:0:0:0:0:0:1");
    }

    public static void addAllowIps(List<String> allowIps){
        IpInterceptor.allowIps.addAll(allowIps);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (allowIps.contains(MyUtils.getIpByRequest(request))){
            return true;
        }
        response.sendRedirect("/app/download");
        return false;
    }
}
