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
            <li><a href="http://www.scxxb.com.cn/html/2017/hdxw_1122/402149.html" target="_blank">重庆永川：关爱留守儿童在行动</a></li>
            <li><a href="http://news.xwh.cn/2017/1126/384825.shtml" target="_blank">爱心义卖情暖村小留守儿童</a></li>
            <li><a href="http://news.xxrb.com.cn/html/2017/ycxx_1127/210187.html" target="_blank">“暖流计划”温暖我市留守儿童</a></li>
            <li><a href="http://yb.newssc.org/system/20171122/002312692.html">关爱留守儿童 屏山公安构建警民和谐</a></li>
            <li><a href="http://e.hznews.com/paper/hzrb/20171115/A6/5/" target="_blank">情暖留守儿童</a></li>
            <li><a href="http://www.ahwang.cn/city/hn/fouce/20171128/1707963.shtml" target="_blank">淮南小橘灯爱心社志愿者带百名留守儿童游学</a></li>
            <li><a href="http://www.gx211.com/news/20171128/n15118461902981.html" target="_blank">铜仁幼儿师范高等专科学校志愿者协会开展关爱留守儿童活动</a></li>
            <li><a href="http://www.nmgcb.com.cn/chengshi/tongliao/2017/1121/146922.html">左中400名留守儿童 将收到爱心包裹</a></li>
            <li><a href="http://www.gs.chinanews.com/news/2017/11-27/296757.shtml" target="_blank">庄浪县举行关爱贫困留守儿童“温暖包”发放仪式</a></li>
            <li><a href="http://www.mxrb.cn/lyxws/content/2017-11/13/content_1657701.htm">永定区培丰镇关爱留守儿童</a></li>
        </ul>
    </aside>
    <aside class="home-aside-right">
        <ul>
            <li><span>热点图片</span><a href="javascript:;">更多...</a></li>
            <li><img src="img/l1.jpg" alt=""></li>
            <li><img src="img/l2.jpg" alt=""></li>
            <li><img src="img/l3.jpg" alt=""></li>
            <li><img src="img/l4.jpg" alt=""></li>
            <li><img src="img/l5.jpg" alt=""></li>
            <li><img src="img/l6.jpg" alt=""></li>
            <li><img src="img/l7.jpg" alt=""></li>
        </ul>
    </aside>
    <div class="home-video">
        <ul>
            <li><span>热点视频</span><a href="javascript:;">更多...</a></li>
        </ul>
        <div class="video-list">
            <ol>
                <li><a href="http://www.iqiyi.com/w_19rtilr855.html?share_sTime=0-share_eTime=55" target="_blank"> <img src="img/1.png" alt=""></a></li>
                <li><a href="http://www.iqiyi.com/w_19rsjcw5x1.html?share_sTime=0-share_eTime=193" target="_blank"> <img src="img/2.png" alt=""></a></li>
                <li><a href="http://www.iqiyi.com/w_19rscf57hx.html?share_sTime=0-share_eTime=27" target="_blank"> <img src="img/3.png" alt=""></a></li>
                <li><a href="http://www.iqiyi.com/w_19rtilr855.html?share_sTime=0-share_eTime=55" target="_blank"> <img src="img/4.png" alt=""></a></li>
                <li><a href="http://www.iqiyi.com/w_19rqzkddyt.html" target="_blank"> <img src="img/5.png" alt=""></a></li>
                <li><a href="http://www.iqiyi.com/w_19rtic67r1.html?share_sTime=0-share_eTime=86" target="_blank"> <img src="img/6.png" alt=""></a></li>
                <li><a href="" target="_blank"> <img src="img/7.png" alt=""></a></li>
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
            , arrow: 'hover' //鼠标进入显示箭头
            , height: '380px'
            , anim: 'fade' //切换动画方式
        });
    });
</script>
</html>
