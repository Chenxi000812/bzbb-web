package com.chenxi.bzbb.service;

import com.chenxi.bzbb.domain.dao.*;
import com.chenxi.bzbb.domain.model.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppService {

    @Autowired
    MyGoodMapper myGoodMapper;

    @Autowired
    FastmailMapper fastmailMapper;

    @Autowired
    GoodMapper goodMapper;

    @Autowired
    GoodspecificationsMapper goodspecificationsMapper;

    @Autowired
    GoodtypeMapper goodtypeMapper;

    @Autowired
    GoodbrandMapper goodbrandMapper;

    @Autowired
    IplimitMapper iplimitMapper;

    public Object index() {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(0,5);
        List<Good> goodDisplays = myGoodMapper.queryGoodDisplays(null,null);
        map.put("recently",goodDisplays);
        return map;
    }

    public Object querySpByGoodId(Long gid) {
        GoodspecificationsExample goodspecificationsExample = new GoodspecificationsExample();
        GoodspecificationsExample.Criteria criteria = goodspecificationsExample.createCriteria();
        criteria.andGidEqualTo(gid);
        return goodspecificationsMapper.selectByExample(goodspecificationsExample);
    }

    public List<Goodtype> queryAllGoodType() {
        return goodtypeMapper.selectByExample(null);
    }

    public List<Goodbrand> queryAllGoodBrand() {
        return goodbrandMapper.selectByExample(null);
    }

    public List<Fastmail> queryAllFastMail() {
        return fastmailMapper.selectByExample(null);
    }

    public List<Good> queryGoodsByType(Integer type) {
        return myGoodMapper.queryGoodDisplays(type,null);
    }

    public List<String> queryAllipLimit() {
        List<Iplimit> iplimits = iplimitMapper.selectByExample(null);
        List<String> res = new ArrayList<>();
        for (Iplimit iplimit:iplimits){
            res.add(iplimit.getIp());
        }
        return res;
    }
}
