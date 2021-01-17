package com.chenxi.bzbb.domain.model;

import java.util.Date;

public class UserCoupon {
    private Long id;

    private Long uid;

    private Integer cid;

    private Integer status;

    private Date createtime;

    public UserCoupon(Long id, Long uid, Integer cid, Integer status, Date createtime) {
        this.id = id;
        this.uid = uid;
        this.cid = cid;
        this.status = status;
        this.createtime = createtime;
    }

    public UserCoupon() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}