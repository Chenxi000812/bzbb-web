package com.chenxi.bzbb.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderInfo implements Serializable {

    private Long id;
    private int count;
    private BigDecimal price;
    private Long gid;
    private String goodTitle;
    private String spName;
    private String type;
    private String spimg;
    private String brand;


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getGid() {
        return gid;
    }
    @JsonIgnore
    public void setGid(long gid) {
        this.gid = gid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBrand() {
        return brand;
    }

    @JsonIgnore
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @JsonIgnore
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getGoodTitle() {
        return goodTitle;
    }

    @JsonIgnore
    public void setGoodTitle(String goodTitle) {
        this.goodTitle = goodTitle;
    }

    public String getSpName() {
        return spName;
    }

    @JsonIgnore
    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getType() {
        return type;
    }

    @JsonIgnore
    public void setType(String type) {
        this.type = type;
    }

    public String getSpimg() {
        return spimg;
    }

    @JsonIgnore
    public void setSpimg(String spimg) {
        this.spimg = spimg;
    }

    public static OrderInfo createOrderInfo(Good good,Goodspecifications goodspecifications){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGid(good.getId());
        orderInfo.setGoodTitle(good.getTitle());
        orderInfo.setId(goodspecifications.getId());
        orderInfo.setPrice(goodspecifications.getPrice());
        orderInfo.setSpimg(goodspecifications.getImg());
        orderInfo.setSpName(goodspecifications.getName());
        return orderInfo;
    }
}
