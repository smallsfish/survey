<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <li><img src="img/logo.png" alt="logo"><span>基础教育改革与发展协同创新中心</span></li>
                <li><a href="index/home">留守儿童之家<i></i></a></li>
                <%--<li><a href="index/news">留守儿童资讯<i></i></a></li>--%>
                <%--<li><a href="index/picture">留守儿童图片<i></i></a></li>--%>
                <%--<li><a href="index/video">留守儿童视频<i></i></a></li>--%>
                <li><a href="index/questionnaire">留守儿童问卷<i></i></a></li>
                <li><a href="index/data">问卷分析<i></i></a></li>
                <li><a href="index/msg">留言板</a></li>
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
    <aside class="home-aside-left">
        <ul>
            <li><span>热点资讯</span><a href="index/news">更多...</a></li>
            <c:forEach var="n" items="${news}">
                <li><a href="index/newsDetail?id=${n.id}"><span>${n.newstitle}</span> <label><fmt:formatDate value="${n.createtime}" pattern="yyyy-MM-dd"/></label></a></li>
            </c:forEach>
        </ul>
    </aside>
    <aside class="home-aside-right">
        <ul>
            <li><span>热点图片</span><a href="index/picture">更多...</a></li>
            <c:forEach var="p" items="${pcitures}" varStatus="st">
                <li class="pic${st.index}"><img id="p${st.index}" src="uploadimage/${p.pictureurl}" alt="${p.picturetitle}"></li>
            </c:forEach>
        </ul>
    </aside>
    <div class="home-video">
        <ul>
            <li><span>热点视频</span><a href="index/video">更多...</a></li>
        </ul>
        <div class="video-list">
            <ol>
                <c:forEach var="v" items="${videos}">
                    <li onclick="playVideo('uploadvideo/${v.videourl}','${v.videotitle}')">
                        <img src="uploadimage/${v.imageurl}" alt="${v.videotitle}"></li>
                </c:forEach>
            </ol>
        </div>
    </div>
</section>
<footer>

</footer>
</body>
<script>
    var layer;
    layui.use(['carousel','layer'], function () {
        layer=layui.layer;
        var carousel = layui.carousel;
        //建造实例
        carousel.render({
            elem: '#pictureLoop'
            , width: '100%' //设置容器宽度
            , arrow: 'hover' //鼠标进入显示箭头
            , height: '600px'
            , anim: 'fade' //切换动画方式
        });
    });
    //加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
    function loadimg1(){
        var imgheight=$("#p0").height();
        var imgwidth=$("#p0").width();
        var imgbl=imgheight/imgwidth;//图片高于宽的比例
        windowHeight=$('.pic0').height();
        windowWidth=$('.pic0').width();
        setWH(imgheight, imgwidth, imgbl,"#p0");
    }
    //加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
    function loadimg2(){
        var imgheight=$("#p1").height();
        var imgwidth=$("#p1").width();
        var imgbl=imgheight/imgwidth;//图片高于宽的比例
        windowHeight=$('.pic1').height();
        windowWidth=$('.pic1').width();
        setWH(imgheight, imgwidth, imgbl,'#p1');
    }
    //加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
    function loadimg3(){
        var imgheight=$("#p2").height();
        var imgwidth=$("#p2").width();
        var imgbl=imgheight/imgwidth;//图片高于宽的比例
        windowHeight=$('.pic2').height();
        windowWidth=$('.pic2').width();
        setWH(imgheight, imgwidth, imgbl,'#p2');
    }
    //加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
    function loadimg4(){
        var imgheight=$("#p3").height();
        var imgwidth=$("#p3").width();
        var imgbl=imgheight/imgwidth;//图片高于宽的比例
        windowHeight=$('.pic3').height();
        windowWidth=$('.pic3').width();
        setWH(imgheight, imgwidth, imgbl,'#p3');
    }
    //加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
    function loadimg5(){
        var imgheight=$("#p4").height();
        var imgwidth=$("#p4").width();
        var imgbl=imgheight/imgwidth;//图片高于宽的比例
        windowHeight=$('.pic4').height();
        windowWidth=$('.pic4').width();
        setWH(imgheight, imgwidth, imgbl,'#p4');
    }
    //加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
    function loadimg6(){
        var imgheight=$("#p5").height();
        var imgwidth=$("#p5").width();
        var imgbl=imgheight/imgwidth;//图片高于宽的比例
        windowHeight=$('.pic5').height();
        windowWidth=$('.pic5').width();
        setWH(imgheight, imgwidth, imgbl,'#p5');
    }
    //加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
    function loadimg7(){
        var imgheight=$("#p6").height();
        var imgwidth=$("#p6").width();
        var imgbl=imgheight/imgwidth;//图片高于宽的比例
        windowHeight=$('.pic6').height();
        windowWidth=$('.pic6').width();
        setWH(imgheight, imgwidth, imgbl,'#p6');
    }


    loadimg1();
    loadimg2();
    loadimg3();
    loadimg4();
    loadimg5();
    loadimg6();
    loadimg7();


    function setWH(imgheight, imgwidth, imgbl,id) {
        var width = 0, height = 0, top = 0, left = 0;
        //如果图片的高度和宽度都大于屏幕的宽高
        if (imgheight > windowHeight && imgwidth > windowWidth) {
            if (imgwidth < imgheight) {
                //图片宽小于高
                width = windowWidth;
                height = width * imgbl;
            } else {
                //图片宽大于高
                height = windowHeight;
                width = height * (1 / imgbl);
            }
        } else if (imgheight > windowHeight && imgwidth < windowWidth) {
            //如果图片的高大于屏幕的高但是图片的宽小于屏幕的宽

        } else if (imgheight < windowHeight && imgwidth > windowWidth) {
            //如果图片的高小于屏幕的高但是图片的宽大于屏幕的宽

        } else {
            //如果图片的宽高都小于屏幕的宽高
        }
        //如果按比例缩放后还出现图片的高度或宽度小于屏幕的宽高，就再次进行等比例缩放
        if (width < windowWidth) {
            width = windowWidth;
            height = width * imgbl;
        }
        if (height < windowHeight) {
            height = windowHeight;
            width = height * (1 / imgbl);
        }
        if (width > windowWidth) {
            left = -(width - windowWidth) / 2;
        } else if (height > windowHeight) {
            top = -(height - windowHeight) / 2;
        }
        $(id).css({"width": width, "height": height, "top": top, "left": left});
    }


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
