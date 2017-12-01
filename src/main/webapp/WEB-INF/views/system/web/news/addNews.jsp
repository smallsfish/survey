<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../../base.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>test</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
    <!-- 配置文件 -->
    <script type="text/javascript" src="ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="ueditor/ueditor.all.js"></script>

    <style>
        form {
            width: 97%;
        }
    </style>

</head>

<body oncontextmenu="return false" onselect="return false">
<form class="layui-form" id="newsForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div class="layui-form-item" style="margin-top: 20px;">
        <label class="layui-form-label"><span style="color: #f00;">*</span>资讯标题：</label>
        <div class="layui-input-block">
            <input type="text" name="newstitle" lay-verify="required" placeholder="请输入资讯标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>资讯缩略图：</label>
        <div class="layui-input-block">
            <input type="file" name="file" lay-verify="required" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>资讯来源：</label>
        <div class="layui-input-block">
            <input type="text" name="comeform" placeholder="请输入资讯来源" lay-verify="required" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">发布时间：</label>
        <div class="layui-input-block">
            <input id="comefrom" type="text" name="createtime" placeholder="请输入发布时间(注意：如果不填写则使用当前时间   格式---》2001-01-05)" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label"><span style="color: #f00;">*</span>资讯类型：</label>
        <div class="layui-input-block">
            <select name="newstype" lay-verify="required">
                <option value="1">头条</option>
                <option value="2">家庭</option>
                <option value="3">谎言</option>
                <option value="4">恐吓</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>状态：</label>
        <div class="layui-input-block">
            <input type="radio" name="status" value="1" title="显示" checked>
            <input type="radio" name="status" value="0" title="隐藏">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>新闻内容：</label>
        <div class="layui-input-block">
            <textarea id="newsContent" lay-verify="required" name="newscontent" style="height: 500px;margin-bottom: 30px;"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addAdmin" id="newsSubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
    <!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
</form>
</body>
<script>
    var layer = null;
    layui.use(['form', 'upload', 'layer','laydate'], function () {
        var form = layui.form;
        layer = layui.layer;
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#comefrom' //指定元素
        });
        form.on('submit(addAdmin)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#newsForm")[0]);
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
                        $("#newsForm")[0].reset();
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
<script type="text/javascript">
    var ue = UE.getEditor("newsContent");
</script>
</html>
