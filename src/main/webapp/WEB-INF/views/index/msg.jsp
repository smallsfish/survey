<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>留守儿童之家</title>
    <%@ include file="../base.jsp" %>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="layui/css/layui.css">
    <style>
        #sendMsg{
            padding: 30px 0;
        }
    </style>
    <style>
        .loop-pic{
            width: 100%;
            height: 100%;
        }
        .loop-pic img{
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<header>
    <nav>
        <div class="header-nav">
            <ul>
                <li><img src="img/logo.png" alt="logo"><span>基础教育改革与发展协同创新中心</span></li>
                <li><a href="/index/home">留守儿童之家<i></i></a></li>
                <li><a href="/index/news">留守儿童资讯<i></i></a></li>
                <li><a href="/index/picture">留守儿童图片<i></i></a></li>
                <li><a href="/index/video">留守儿童视频<i></i></a></li>
                <li><a href="/index/questionnaire">留守儿童问卷<i></i></a></li>
                <li><a href="/index/data">问卷分析<i></i></a></li>
                <li><a href="/index/msg">留言板</a></li>
            </ul>
        </div>
    </nav>
    <div class="pictureLoopContent">
        <div class="layui-carousel" id="pictureLoop">
            <div carousel-item>
                <c:forEach items="${loops}" var="loop">
                    <div class="loop-pic"><a href="${(loop.url==null || loop.url.equals("")) ? 'javascript:;': loop.url}"><img src="${loop.imageurl}"></a></div>
                </c:forEach>
            </div>
        </div>
    </div>
</header>
<section>
    <form class="layui-form" id="sendMsg" action="javascript:;" method="POST">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名：</label>
            <div class="layui-input-block">
                <input type="text" name="name" lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系电话：</label>
            <div class="layui-input-block">
                <input type="text" name="telphone" lay-verify="required|phone|number" placeholder="请输入电话" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">留言</label>
            <div class="layui-input-block">
                <textarea name="msg" lay-verify="required" placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="sendMSG" id="adminUserSubmit">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</section>
<footer>

</footer>
</body>
<script>
    layui.use(['carousel','form'], function () {
        var carousel = layui.carousel;
        var form = layui.form;
        form.on('submit(sendMSG)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#sendMsg")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'index/submitMsg',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        $("#sendMsg")[0].reset();
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
        //建造实例
        carousel.render({
            elem: '#pictureLoop'
            , width: '100%' //设置容器宽度
            , arrow: 'always' //始终显示箭头
            , height: '600px'
            , anim: 'fade' //切换动画方式
        });
    });
</script>
</html>
