package com.chenxi.bzbb.domain.model;

import java.util.Date;

public class Iplimit {
    private String ip;

    private Date createtime;

    public Iplimit(String ip, Date createtime) {
        this.ip = ip;
        this.createtime = createtime;
    }

    public Iplimit() {
        super();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}