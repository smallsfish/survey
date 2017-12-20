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
        .loop-pic {
            width: 100%;
            height: 100%;
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
                    <div class="loop-pic"><a
                            href="${(loop.url==null || loop.url.equals("")) ? 'javascript:;': loop.url}"><img
                            src="${loop.imageurl}"></a></div>
                </c:forEach>
            </div>
        </div>
    </div>
</header>
<section>
    <div class="questionnaire-content">
        <ul>
            <c:forEach var="q" items="${questionnaires}">
                <li>
                    <span>${q.name}</span>
                    <label>${q.time}</label>
                    <c:if test="${q.status==0}">
                        <a href="display/displayQuestionnaire?id=${q.id}" target="_blank">参与</a>
                    </c:if>
                    <c:if test="${q.status==1}">
                        <a href="javascript:;">未开始</a>
                    </c:if>
                    <c:if test="${q.status==2}">
                        <a href="javascript:;">已结束</a>
                    </c:if>
                </li>
            </c:forEach>
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
            , height: '600px'
            , anim: 'fade' //切换动画方式
        });
    });
</script>
</html>
