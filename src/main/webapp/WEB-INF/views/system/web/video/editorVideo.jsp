<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    <style>
        form {
            width: 97%;
        }
        .layui-input-block dl{
            z-index: 1000 !important;
        }
    </style>

</head>

<body oncontextmenu="return false" onselect="return false">
<form class="layui-form" id="videoForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <input type="hidden" name="id" value="${video.id}">
    <div class="layui-form-item" style="margin-top: 20px;">
        <label class="layui-form-label"><span style="color: #f00;">*</span>视频标题：</label>
        <div class="layui-input-block">
            <input type="text" name="videotitle" lay-verify="required" placeholder="请输入视频标题" autocomplete="off"
                   class="layui-input" value="${video.videotitle}">
        </div>
    </div>
    <div class="upload-picture" id="headimg">
        <img src="uploadimage/${video.imageurl}" alt="资讯缩略图">
        <button type="button" class="layui-btn" id="uploadheadimg">
            <i class="layui-icon">&#xe67c;</i>上传缩略图
        </button>
    </div>
    <div class="upload-video" style="border-radius: 0;" id="videoFile">
        <video src="uploadvideo/${video.videourl}" controls></video>
        <button type="button" class="layui-btn" id="uploadVideoFile">
            <i class="layui-icon">&#xe67c;</i>上传视频
        </button>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>状态：</label>
        <div class="layui-input-block">
            <input type="radio" name="status" value="1" title="显示" ${video.status ? 'checked':''}>
            <input type="radio" name="status" value="0" title="隐藏" ${video.status ? '':'checked'}>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addVideo" id="videoSubmit">立即修改</button>
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
        var upload=layui.upload;
        var laydate = layui.laydate;

        upload.render({
            elem: '#uploadheadimg', //绑定元素
            auto:true,
            url:'system/updateVideoPicture',
            size:2*1024*1024,
            method:'post',
            data:{id:${video.id},image:'${video.imageurl}'},
            done:function (res,index,upload) {
                if(res.status==0){
                    window.parent.reflashVideoOnSuccessTable();
                    $("#headimg img").attr("src",res.data);
                }
                layer.msg(res.msg);
            }
        });


        upload.render({
            elem: '#videoFile', //绑定元素
            auto:true,
            url:'system/updateVideoFile',
            size:2*1024*1024,
            method:'post',
            exts: 'mp4',
            data:{id:${video.id},videoUrl:'${video.videourl}'},
            done:function (res,index,upload) {
                if(res.status==0){
                    window.parent.reflashVideoOnSuccessTable();
                    $("#videoFile video").attr("src",res.data);
                }
                layer.msg(res.msg);
            }
        });


        //执行一个laydate实例
        laydate.render({
            elem: '#comefrom' //指定元素
        });
        form.on('submit(addVideo)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#videoForm")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/updateVideo',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        window.parent.reflashVideoOnSuccessTable();
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
