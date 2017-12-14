<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh-CN" style="width: 100%;height: 100%;">
<%@ include file="../../base.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>test</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
    <style>
        form {
            display:-webkit-flex;
            display:-moz-flex;
            display:flex;
            display:-ms-flexbox;
            -webkit-box-orient: horizontal;
            -moz-box-orient: horizontal;
            /*垂直居中*/
            /*老版本语法*/
            -webkit-box-align: center;
            -moz-box-align: center;
            /*混合版本语法*/
            -ms-flex-align: center;
            /*新版本语法*/
            -webkit-align-items: center;
            align-items: center;
            /*水平居中*/
            /*老版本语法*/
            -webkit-box-pack: center;
            -moz-box-pack: center;
            /*混合版本语法*/
            -ms-flex-pack: center;
            /*新版本语法*/
            -webkit-justify-content: center;
            justify-content: center;
            position: absolute;
            width: 90%;
            height: 10%;
        }
    </style>
</head>

<body oncontextmenu="return false" onselect="return false" style="width: 100%;height: 100%;">
<form class="layui-form" id="userForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <input type="hidden" name="id" value="${id}">
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>新密码：</label>
        <div class="layui-input-block">
            <input type="text" name="account" lay-verify="required" placeholder="请输入新密码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateUserPassword" id="userSubmit">立即修改</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
    <!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
</form>
</body>
<script>
    var layer = null;
    layui.use(['form', 'layer'], function () {
        var form = layui.form;
        layer = layui.layer;
        form.on('submit(updateUserPassword)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#userForm")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'display/updateUserPassword',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        $("#userForm")[0].reset();
                        setTimeout(function () {
                            window.location.href="display/login";
                        },1000);
                    }
                    layer.msg(result.msg);
                },
                error: function (data) {
                    layer.close(loadIndex);
                    alert("出现异常！");
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    });
</script>
</html>
