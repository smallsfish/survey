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
    <div class="news">
        <div class="news-tab">
            <ul>
                <li class="news-tab-focus">asdf</li>
                <li>asdf</li>
                <li>asdf</li>
                <li>asDF</li>
                <li>asdf</li>
            </ul>
        </div>
        <div class="news-content">
            <ul>
                <li>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                </li>
                <li>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                </li>
                <li>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                </li>
                <li>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                </li>
                <li>
                    <div class="n-news-list">
                        <span>奥斯卡劳动纠纷看拉升阶段分开了就阿斯利康的积分拉萨空间的发卢卡斯记得不刻录机阿斯达克浪费就阿斯科利大家分开拉上的纠纷快乐；啊睡觉的疯狂拉升地方</span>
                        <label>2017-10-27</label>
                        <img src="img/icon/EPS.png"/>
                    </div>
                </li>
            </ul>
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
