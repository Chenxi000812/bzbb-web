package com.chenxi.bzbb.utils;

public class AjaxResult {

    public static AjaxResult me(){
        return new AjaxResult();
    }

    private Boolean success = true;//默认操作成功
    private String msg = "操作成功";//返回前端操作的文字结果

    private Object object;//返回后台的对象

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        if (success){
            msg = "操作成功";
        }else {
            msg = "操作失败";
        }
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public AjaxResult setObject(Object object) {
        this.object = object;
        return this;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                '}';
    }
}