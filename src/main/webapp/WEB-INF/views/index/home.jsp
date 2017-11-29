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
    <script src="js/index.js"></script>
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
                <li>logo</li>
                <li><a href="/index/home">儿童之家<i></i></a></li>
                <li><a href="/index/news">儿童资讯<i></i></a></li>
                <li><a href="/index/picture">儿童图片<i></i></a></li>
                <li><a href="/index/video">儿童视频<i></i></a></li>
                <li><a href="/index/msg">留言板</a></li>
            </ul>
        </div>
    </nav>
    <div class="pictureLoopContent">
        <div class="layui-carousel" id="pictureLoop">
            <div carousel-item>
                <c:forEach items="${loops}" var="loop">
                    <div class="loop-pic"><img src="${loop.imageurl}"></div>
                </c:forEach>
            </div>
        </div>
    </div>
</header>
<section>
    <aside class="home-aside-left">
        <ul>
            <li><span>热点资讯</span><a href="javascript:;">更多...</a></li>
            <li><a href="javascript:;">新闻1</a></li>
            <li><a href="javascript:;">新闻2</a></li>
            <li><a href="javascript:;">新闻3</a></li>
            <li><a href="javascript:;">新闻4</a></li>
            <li><a href="javascript:;">新闻5</a></li>
            <li><a href="javascript:;">新闻6</a></li>
            <li><a href="javascript:;">新闻7</a></li>
            <li><a href="javascript:;">新闻8</a></li>
            <li><a href="javascript:;">新闻9</a></li>
            <li><a href="javascript:;">新闻10</a></li>
        </ul>
    </aside>
    <aside class="home-aside-right">
        <ul>
            <li><span>热点图片</span><a href="javascript:;">更多...</a></li>
            <li><img src="img/icon/AI.png" alt=""></li>
            <li><img src="img/icon/AI.png" alt=""></li>
            <li><img src="img/icon/AI.png" alt=""></li>
            <li><img src="img/icon/AI.png" alt=""></li>
            <li><img src="img/icon/AI.png" alt=""></li>
            <li><img src="img/icon/AI.png" alt=""></li>
            <li><img src="img/icon/AI.png" alt=""></li>
        </ul>
    </aside>
    <div class="home-video">
        <ul>
            <li><span>热点视频</span><a href="javascript:;">更多...</a></li>
        </ul>
        <div class="video-list">
            <ol>
                <li><img src="img/icon/AVI.png" alt=""><span>1</span></li>
                <li><img src="img/icon/AVI.png" alt=""><span>2</span></li>
                <li><img src="img/icon/AVI.png" alt=""><span>3</span></li>
                <li><img src="img/icon/AVI.png" alt=""><span>5</span></li>
                <li><img src="img/icon/AVI.png" alt=""><span>6</span></li>
                <li><img src="img/icon/AVI.png" alt=""><span>7</span></li>
            </ol>
        </div>
    </div>
</section>
<footer>

</footer>
</body>
<script>
    layui.use('carousel', function () {
        var carousel = layui.carousel;
        //建造实例
        carousel.render({
            elem: '#pictureLoop'
            , width: '100%' //设置容器宽度
            , arrow: 'hover' //始终显示箭头
            , height: '380px'
            , anim: 'fade' //切换动画方式
        });
    });
</script>
</html>
