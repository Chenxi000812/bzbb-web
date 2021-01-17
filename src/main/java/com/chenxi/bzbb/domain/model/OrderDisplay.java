package com.chenxi.bzbb.domain.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDisplay {
    private String id;
    private String goods;
    private Integer couponId;
    private BigDecimal reduction;
    private String fastmailName;
    private String fastmailPrice;
    private BigDecimal total;
    private Long uid;
    private String userNickName;
    private String receiver;
    private String receivermobile;
    private String province;
    private String city;
    private String region;
    private String street;
    private String detailAddress;
    private Date createtime;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceivermobile() {
        return receivermobile;
    }

    public void setReceivermobile(String receivermobile) {
        this.receivermobile = receivermobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getReduction() {
        return reduction;
    }

    public void setReduction(BigDecimal reduction) {
        this.reduction = reduction;
    }

    public String getFastmailName() {
        return fastmailName;
    }

    public void setFastmailName(String fastmailName) {
        this.fastmailName = fastmailName;
    }

    public String getFastmailPrice() {
        return fastmailPrice;
    }

    public void setFastmailPrice(String fastmailPrice) {
        this.fastmailPrice = fastmailPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
