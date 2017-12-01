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
                    <div class="loop-pic"><a href="${loop.url}"><img src="${loop.imageurl}"></a></div>
                </c:forEach>
            </div>
        </div>
    </div>
</header>
<section>
    <div class="pictures">
        <div class="pictures-tab">
            <ul>
                <li>分类：</li>
                <li>1</li>
                <li>2</li>
                <li>3</li>
                <li>4</li>
                <li>5</li>
            </ul>
        </div>

    </div>
    <div class="pictures-content">
        <ul>
            <li>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
                <div class="p-picture-list">
                    <img src="img/icon/HTML.png" />
                    <span>阿克苏的发手拉动房价啦设法就拉丝的肌肤</span>
                </div>
            </li>
            <li>2</li>
            <li>3</li>
            <li>4</li>
            <li>5</li>
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
            , arrow: 'hover' //始终显示箭头
            , height: '380px'
            , anim: 'fade' //切换动画方式
        });
    });
</script>
</html>
