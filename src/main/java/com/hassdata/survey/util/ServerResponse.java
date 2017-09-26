package com.hassdata.survey.util;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * JSON返回工具类
 * @param <T>
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //如果返回的json中null值时候就连key否不返回
public class ServerResponse<T> implements Serializable{
    private int status;
    private String msg;
    private long count;
    private int code;
    private T data; //可以指定泛型里面的内容，也可以不指定泛型里面的内容(String,map,list)

    //构造方法全部私有
    private ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status, T data){
        this.status = status;
        this.data = data;
    }
    private ServerResponse(int status, String msg){
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(int code,String msg,long count,T data){
        this.code=code;
        this.msg=msg;
        this.count=count;
        this.data=data;
    }
    private ServerResponse(int status, T data, String msg){
        this.status = status;
        this.data = data;
        this.msg = msg;
    }


    //确认响应是不是正确响应(true:成功)
    @JsonIgnore //序列化之后不会显示在json里面
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode(); //如果是0的话表示成功响应
    }


    //对外开放get方法
    public int getStatus() {
        return status;
    }

    public long getCount() {
        return count;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }



    //静态方法对外开放,返回一个成功的构造器
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());

    }
    //静态方法对外开放,返回一个成功的构造器,带msg信息
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);

    }
    //静态方法对外开放,返回一个成功的构造器,带data信息
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);

    }

    //静态方法对外开放,返回一个成功的构造器,带msg信息,data信息
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data,msg);

    }

    //静态方法对外开放,返回一个成功的构造器为layui的表格数据,带msg信息,data信息,count信息
    public static <T> ServerResponse<T> createBySuccessForLayuiTable(String msg,T data,long count){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,count,data);
    }

    //静态方法对外开放,返回一个error的构造器,带描述
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    //静态方法对外开放,返回一个error的构造器,带errMessage
    public static <T> ServerResponse<T> createByErrorMessage(String errMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errMessage);
    }

    //自定义错误返回构造器
    public static <T> ServerResponse<T> createByErrorCodeMessage(int code,String errMessage){
        return new ServerResponse<T>(code,errMessage);
    }

    //静态方法对外开放,没有登录，需要登录
    public static <T> ServerResponse<T> createByNeedLoginErrorMessage(String errMessage){
        return new ServerResponse<T>(ResponseCode.NEED_LOGIN.getCode(),errMessage);
    }

    //静态方法对外开放,没有权限，需要权限
    public static <T> ServerResponse<T> createByNeedPermissionErrorMessage(String errMessage){
        return new ServerResponse<T>(ResponseCode.NEED_PERMISSION.getCode(),errMessage);
    }



    //    public static void main(String[] args) {
//        ServerResponse sr1 = new ServerResponse(1,new Object());
//        ServerResponse sr2 = new ServerResponse(1,"abc");
//        System.out.println("console");
//
//    }

}