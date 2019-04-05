package com.datasource.provider.domain;


import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.datasource.provider.utils.MessageUtils;

/**
 * 服务返回对象
 * 注意:该对象的属性没有set方法 不能缓存
 * @param <T>
 *     @author
 */

public class ServiceResult<T> implements Serializable{


    /**
     * 请求失败标识
     */
//    @Getter
    private Integer error;
    /**
     * 请求返回编码
     */
//    @Getter
    private String code;
    /**
     * 请求返回信息
     */
//    @Getter
    private String message;
    /**
     * 请求返回数据
     */
//    @Getter
    private T data;

    public Integer getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @JSONField(serialize=false)
    public boolean isError(){
        return 1 == error;
    }

    public void setCode(String code) {
        this.code = code;
        this.message = MessageUtils.getString(code);
    }

    private void setCode(String code, Object... s) {
        this.code = code;
        this.message = String.format(MessageUtils.getString(code), s);
    }

    public <M> ServiceResult<M> convert() {
        ServiceResult<M> result = new ServiceResult<M>();
        result.code = this.code;
        result.message = this.message;
        result.error = this.error;
        return result;
    }

    public static <T> ServiceResult<T> success(){
        ServiceResult<T> result = new ServiceResult<T>();
        result.error = 0;
        return result;
    }

    public static <T> ServiceResult<T> success(T data){
        ServiceResult<T> result = new ServiceResult<T>();
        result.error = 0;
        result.data = data;
        return result;
    }

    public static <T> ServiceResult<T> failure(String code){
        ServiceResult<T> result = new ServiceResult<T>();
        result.error = 1;
        result.setCode(code);
        return result;
    }

    public static <T> ServiceResult<T> failure(String code, Object... s){
        ServiceResult<T> result = new ServiceResult<T>();
        result.error = 1;
        result.setCode(code,s);
        return result;
    }

    public static <T> ServiceResult<T> exception(String code,String message) {
        ServiceResult<T> result = new ServiceResult<T>();
        result.error = 1;
        result.code = code;
        result.message = message;
        return result;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

}
