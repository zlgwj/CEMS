package com.gwj.common.response;


import com.gwj.common.enums.ResultEnum;
import lombok.Data;

import java.util.Map;

//同一返回结果的类
@Data
public class R {

    private Boolean success = true;

    private Integer code;

    private String message;

    private Object data;

    private R(){}

    //成功的静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultEnum.SUCCESS.getCode());
        r.setMessage("成功");
        return r;
    }


    //失败的静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultEnum.ERROR.getCode());
        r.setMessage("失败");
        return r;
    }

    public static R error(Integer code) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(code);
        return r;
    }
    public static R error(String msg) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultEnum.ERROR.getCode());
        r.setMessage(msg);
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(Object value){
        this.data = value;
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
