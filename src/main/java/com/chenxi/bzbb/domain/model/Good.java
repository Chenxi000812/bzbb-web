package com.chenxi.bzbb.domain.model;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;

public class Good {
    private Long id;

    private String title;

    private String imgs;

    private Date createtime;

    private Integer type;

    private Integer brand;

    private Long sell;

    private BigDecimal maxprice;
    private BigDecimal minprice;

    public BigDecimal getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(BigDecimal maxprice) {
        this.maxprice = maxprice;
    }

    public BigDecimal getMinprice() {
        return minprice;
    }

    public void setMinprice(BigDecimal minprice) {
        this.minprice = minprice;
    }

    public Good(Long id, String title, String imgs, Date createtime, Integer type, Integer brand, Long sell) {
        this.id = id;
        this.title = title;
        this.imgs = imgs;
        this.createtime = createtime;
        this.type = type;
        this.brand = brand;
        this.sell = sell;
    }

    public Good() {
        super();
    }

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
        this.title = title == null ? null : title.trim();
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs == null ? null : imgs.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Long getSell() {
        return sell;
    }

    public void setSell(Long sell) {
        this.sell = sell;
    }

    public static void main(String[] args) {
        String s = new String(Base64.getDecoder().decode("5oKo55qE5a6J6KOF5YyF5LiN5a6M5pW077yM6K+35LiL6L295a6M5pW05YyF".getBytes()));
        System.out.println(s);
    }
}