package com.chenxi.bzbb.service;

import com.alibaba.fastjson.JSON;
import com.chenxi.bzbb.domain.dao.*;
import com.chenxi.bzbb.domain.model.*;
import com.chenxi.bzbb.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class GoodService {

    @Value("${my.filepath}")
    private String path;


    @Autowired
    GoodtypeMapper goodtypeMapper;
    @Autowired
    GoodbrandMapper goodbrandMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    GoodspecificationsMapper goodspecificationsMapper;
    @Autowired
    GoodDisplayMapper goodDisplayMapper;

    @Transactional
    public Goodtype uploadType(Goodtype goodtype) {
        goodtypeMapper.insert(goodtype);
        return goodtype;
    }

    @Transactional
    public List<Goodtype> queryAllType() {
        return goodtypeMapper.selectByExample(null);
    }

    @Transactional
    public Goodbrand uploadBrand(Goodbrand goodbrand){
        goodbrandMapper.insert(goodbrand);
        return goodbrand;
    }

    @Transactional
    public List<Goodbrand> queryAllBrand() {
        return goodbrandMapper.selectByExample(null);
    }


    @Transactional
    public void uploadGoodSpecifications(Goodspecifications goodspecifications) {
        goodspecifications.setCreatetime(new Date());
        goodspecificationsMapper.insert(goodspecifications);
    }

    @Transactional
    public List<GoodDisplay> queryAllgoods() {
        return goodDisplayMapper.selectGoodDislay();
    }

    @Transactional
    public List<Goodspecifications> queryGoodspecificationsByIds(List<Long> longs){
        GoodspecificationsExample goodspecificationsExample = new GoodspecificationsExample();
        GoodspecificationsExample.Criteria criteria = goodspecificationsExample.createCriteria();
        criteria.andIdIn(longs);
        return goodspecificationsMapper.selectByExample(goodspecificationsExample);
    }

    @Transactional(rollbackFor = Exception.class)
    public void uploadGood(MultipartFile[] goodimgs, MultipartFile[] guigeimgs, Good good,String[] guiges)throws Exception {
        List<String> imgs = new ArrayList<>();
        try{
            for (MultipartFile multipartFile : goodimgs){
                imgs.add(MyUtils.saveFile(multipartFile,path));
            }
            good.setImgs(JSON.toJSONString(imgs));
            good.setCreatetime(new Date());
            goodMapper.insert(good);
            if (guiges!=null) {
                List<Goodspecifications> goodspecifications = JSON.parseArray(Arrays.toString(guiges), Goodspecifications.class);
                for (int i = 0; i < goodspecifications.size(); i++) {
                    Goodspecifications g = goodspecifications.get(i);
                    g.setGid(good.getId());
                    String s = MyUtils.saveFile(guigeimgs[i], path);
                    imgs.add(s);
                    g.setImg(s);
                    uploadGoodSpecifications(goodspecifications.get(i));
                }
            }
        }catch (Exception e){
            for (String s: imgs){
                new File(path+s).delete();
            }
            throw e;
        }

    }
}
