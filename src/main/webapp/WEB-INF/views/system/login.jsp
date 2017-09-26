<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../base.jsp"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/style.css">
    <title>留守儿童问卷调查后台系统登录</title>
</head>
<body>
    <div class="system-login">
        <div class="system-login-top" style="position: relative;">
            <div class="top-en">留守儿童问卷调查后台系统</div>
            <div class="top-en">LIUSHOUERTONGWENJUANDIAOCHAXITONG</div>
        </div>
        <div class="system-login-center">
            <div class="system-login-center-left">

            </div>
            <div class="system-login-center-right">
                <div class="system-login-box">
                    <%--<div class="system-login-center-right-info">还没有账号？赶紧<a href="javascript:;" onclick="alert('正在施工中。。。')">注册一个</a>吧！</div>--%>
                    <form id="doSystemLogin" action="javascript:;" method="post">
                        <div class="system-login-form-title">登录</div>
                        <input type="text" name="account" placeholder="输入账号" style="width: 78%;padding-left: 2%;height: 30px;margin-top:15%; "><br>
                        <input type="password" name="password" placeholder="输入密码" style="width: 78%;padding-left: 2%;height: 30px;margin-top:15px;"><br>
                        <%--<div style="width: 80%; margin-left: 10%; display: block; margin-top: 15px;">--%>
                            <%--<input id="remind" name="remind" value="1" type="checkbox" style="float:left;margin-top: 5px;"><label for="remind" style="font-size: 14px;margin-left: 5px; display: block;float:left;">记住账号</label>--%>
                            <%--<a href="javascript:;" onclick="alert('正在施工中...')" style="font-size: 14px;float:right;">忘记密码？</a>--%>
                        <%--</div>--%>
                        <input type="submit" style="width: 60%;height: 35px; border: 0;margin-top: 60px; background-color:#44B549; color: #fff; font-size: 18px;" value="登录">
                    </form>
                </div>
            </div>
        </div>
        <div class="system-login-bottom">
            Copyright © 合肥海思数据分析有限公司
        </div>
    </div>
</body>
<script>
    layui.use(['layer'],function () {
        $("#doSystemLogin").submit(function () {
            var account=$(":input[name='account']").val();
            var password=$(":input[name='password']").val();
            if(account===""){
                layer.msg("请输入账号");
                $(":input[name='account']").css({'border':'2px red solid'});
                $(":input[name='account']").focus();
                return;
            }
            if(password===""){
                layer.msg("请输入密码");
                $(":input[name='password']").css({'border':'2px red solid'});
                $(":input[name='password']").focus();
                return;
            }
            var loadIndex=layer.load(0);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/login',
                data: $('#doSystemLogin').serialize(),
                success: function (result) {
                    layer.close(loadIndex);
                    if(result.status==0){
                        layer.msg(result.msg);
                        setTimeout(function () {
                            window.location.href="system/index";
                        },1000);
                    }else{
                        layer.msg(result.msg,{icon:2,time:3000});
                    }
                },
                error: function(data) {
                    layer.close(loadIndex);
                    alert("error:"+data);
                }
            });

        });
    });
</script>
</html>
