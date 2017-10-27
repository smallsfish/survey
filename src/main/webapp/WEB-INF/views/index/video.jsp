<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
                <div>条目1</div>
                <div>条目2</div>
                <div>条目3</div>
                <div>条目4</div>
                <div>条目5</div>
            </div>
        </div>
    </div>
</header>
<section>
    <div class="v-video-list">
        <ul>
            <li><img src="img/icon/JPEG.png" /><i class="iconfont icon-play"></i><span>啦就是地方</span></li>
            <li><img src="img/icon/JPEG.png" /><i class="iconfont icon-play"></i><span>爱上了空间看浪费</span></li>
            <li><img src="img/icon/JPEG.png" /><i class="iconfont icon-play"></i><span>阿斯兰打开附件拉快速的减肥看拉升阶段分开了就阿斯科利的风景啊是开朗大方</span></li>
            <li><img src="img/icon/JPEG.png" /><i class="iconfont icon-play"></i><span>啊四个季节为日哦骄傲I微软硬件哦配件</span></li>
            <li><img src="img/icon/JPEG.png" /><i class="iconfont icon-play"></i><span>啊时间到了授课计划开朗大方噶</span></li>
        </ul>
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
            , arrow: 'always' //始终显示箭头
            , height: '380px'
            , anim: 'fade' //切换动画方式
        });
    });
</script>
</html>