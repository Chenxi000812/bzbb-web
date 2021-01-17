package com.chenxi.bzbb.domain.model;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.List;

public class GoodDisplay {
    private Long id;
    private String title;
    private List<String> imgs;
    private String type;
    private String brand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = JSON.parseArray(imgs,String.class);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
