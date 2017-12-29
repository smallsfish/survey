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
    <link rel="stylesheet" href="img/iconfont/iconfont.css">
    <script src="js/index.js"></script>
    <style>
        .loop-pic {
            width: 100%;
            height: 100%;
        }
        ::-webkit-scrollbar {
            width: 8px;
            height: 5px;
        }
        .loop-pic img {
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
                <li><img src="img/logo1.png" alt="logo"><%--<span>基础教育改革与发展协同创新中心</span>--%></li>
                <li><a href="index/home">儿童之家</a></li>
                <%--<li><a href="index/news">儿童资讯<i></i></a></li>--%>
                <%--<li><a href="index/picture">儿童图片<i></i></a></li>--%>
                <li><a href="index/questionnaire">儿童问卷</a></li>
                <li><a href="index/data">问卷分析</a></li>
                <li><a href="index/getNews">研究成果</a></li>
                <li><a href="index/msg">留言板</a></li>
            </ul>
        </div>
    </nav>
    <div class="pictureContent">
        <img src="img/bg.jpg" alt="关注留守儿童">
    </div>
</header>
<section>
    <div class="v-video-list">
        <ul>
            <c:forEach var="v" items="${vl}">
                <li onclick="playVideo('uploadvideo/${v.videourl}','${v.videotitle}')"><img
                        src="uploadimage/${v.imageurl}"/><i class="iconfont icon-play"></i><span>${v.videotitle}</span>
                </li>
            </c:forEach>
        </ul>
    </div>
</section>
<footer>
    <p>基础教育改革与发展协同创新中心 版权所有</p>
</footer>
</body>
<script>
    var layer = null;
    layui.use(['carousel', 'layer'], function () {
        var carousel = layui.carousel;
        layer = layui.layer;
        //建造实例
        carousel.render({
            elem: '#pictureLoop'
            , width: '100%' //设置容器宽度
            , arrow: 'always' //始终显示箭头
            , height: '600px'
            , anim: 'fade' //切换动画方式
        });
    });

    function playVideo(videoUrl, title) {
        layer.open({
            title: "正在播放   <<" + title + ">>",
            type: 1,
            anim: 1,
            area: ['800px', '530px'],
            content: $('.playVideo') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
        $(".playVideo video").attr("src", videoUrl);
    }
</script>
<div class="playVideo" style="width: 100%;height: 100%; display: none;">
    <video style="width: 100%;height: 100%;" autoplay controls></video>
</div>
</html>