<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="zh-CN">
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
            width: 97%;
        }

        select {
            width: 85%;

        }

        option {
            font-size: 16px;
            padding: 3px 5px;
        }
    </style>
</head>

<body oncontextmenu="return false" onselect="return false">
<blockquote class="layui-elem-quote">注意：该用户的密码系统初始设置为000000，请让该用户登录后自行修改密码</blockquote>
<form class="layui-form" id="adminUserForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <%--<div class="upload-headimg" id="headimg">
        <button type="button" class="layui-btn" id="uploadheadimg">
            <i class="layui-icon">&#xe67c;</i>上传头像
        </button>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>头像：</label>
        <div class="layui-input-block">
            <input type="file" name="file" lay-verify="required" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>账号：</label>
        <div class="layui-input-block">
            <input type="text" name="account" lay-verify="required" placeholder="请输入账号" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>Email：</label>
        <div class="layui-input-block">
            <input type="email" name="email" placeholder="请输入Email" lay-verify="required|email" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>角色：</label>
        <div class="layui-input-block">
            <select name="roles" multiple lay-ignore>
                <c:forEach var="r" items="${roles}">
                    <option value="${r.id}" ${!r.available ? 'disabled':''}>${r.description}</option>
                </c:forEach>
            </select>
        </div>
        <span style=" display:block;float:right;">按住Shift多选</span>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">编号：</label>
        <div class="layui-input-block">
            <input type="text" name="identifier" placeholder="请输入编号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <shiro:hasPermission name="admin:add">
                <button class="layui-btn" lay-submit lay-filter="addAdmin" id="adminUserSubmit">立即提交</button>
            </shiro:hasPermission>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
    <!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
</form>
</body>
<script>
    var layer = null;
    layui.use(['form', 'upload', 'layer'], function () {
        var form = layui.form;
        layer = layui.layer;
        form.on('submit(addAdmin)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#adminUserForm")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/addAdminUser',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        $("#adminUserForm")[0].reset();
                        window.parent.reflashAdminTable();
                    }
                    layer.msg(result.msg);
                },
                error: function (data) {
                    layer.close(loadIndex);
                    alert("出现异常！" + JSON.stringify(data));
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    });
</script>
</html>
