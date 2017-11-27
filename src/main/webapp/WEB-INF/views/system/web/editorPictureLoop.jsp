<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
            margin-top: 30px;
        }
        .upload-loopimage{
            width: 200px;
            height:auto;
            margin-left: 45px;
            margin-top: 10px;
            margin-bottom: 15px;
            position: relative;
        }
        .upload-loopimage img{
            width: 100%;
        }
        .upload-loopimage button{
            width: 100%;
            height: 100%;
            position: absolute;
            background-color: rgba(0,0,0,0);
            z-index:2;
            top: 0;
            left: 0;
        }
    </style>
</head>

<body oncontextmenu="return false" onselect="return false">
<form class="layui-form" id="loopForm">
    <input name="id" value="${loop.id}" type="hidden">
    <div class="upload-loopimage" id="headimg">
        <img src="${loop.imageurl}" alt="轮播图片">
        <button type="button" class="layui-btn" id="uploadLoopImage">
            <i class="layui-icon">&#xe67c;</i>上传轮播图
        </button>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">超链接：</label>
        <div class="layui-input-block">
            <input type="text" name="url" placeholder="请输入超链接" autocomplete="off"
                   class="layui-input" value="${loop.url}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>是否显示</label>
        <div class="layui-input-block">
            <input type="radio" lay-verify="required" name="isshow" value="1" title="是" ${loop.isshow==true ? 'checked':''}>
            <input type="radio" lay-verify="required" name="isshow" value="0" title="否" ${loop.isshow==false ? 'checked':''}>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>排序：</label>
        <div class="layui-input-block">
            <input type="number" lay-verify="required" name="sort" placeholder="请输入编号" value="${loop.sort}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addLoop" id="loopSubmit">立即修改</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
</body>
<script>
    var layer = null;
    layui.use(['form', 'upload', 'layer'], function () {
        var form = layui.form;
        layer = layui.layer;
        var upload = layui.upload;
        //执行实例
        upload.render({
            elem: '#uploadLoopImage', //绑定元素
            auto:true,
            url:'system/updateLoopImage',
            size:2048,
            method:'post',
            data:{id:${loop.id},image:'${loop.imageurl}'},
            done:function (res,index,upload) {
                if(res.status==0){
                    window.parent.reflashLoopPictureTable();
                    $("#headimg img").attr("src",res.data);
                }
                layer.msg(res.msg);

            }
        });
        form.on('submit(addLoop)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#loopForm")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/loopEditor',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        window.parent.reflashLoopPictureTable();
                    }
                    layer.msg(result.msg);
                },
                error: function (data) {
                    layer.close(loadIndex);
                    alert("出现异常！");
                }
            });
            return false;
        });
    });
</script>
</html>
