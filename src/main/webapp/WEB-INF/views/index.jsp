<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>问卷调查管理系统</title>
    <%@ include file="base.jsp"%>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
</head>
<body oncontextmenu="return false" onselect="return false">
<!-- 主页面div-->
<div class="system-admin-main">
    <!-- 头部开始 -->
    <div class="header">
        <!-- 头部导航开始 -->
        <div class="system-nav">
            <!-- 头部logo -->
            <div class="system-logo">
                <img src="img/icon/icon-headimg.png">问卷调查管理系统
            </div>
            <!-- 导航右侧菜单开始 -->
            <div class="system-layout-right">
                <ul>
                    <!-- 导航菜单项 -->
                    <%--<li class="system-layout-item">
                        <a href="javascript:;">
                            <span class="item-hint-info">99</span>
                            <img src="img/icon/icon-remind.png" alt="" class="system-layout-item-nav-img"> 消息</a>
                    </li>--%>
                    <%--<li class="system-layout-item"><a href="javascript:;"><span class="item-hint-point"></span>皮肤</a>
                    </li>--%>
                    <li class="system-layout-item">
                        <a href="javascript:;"><img src="uploadimage/${sessionScope.CurrentAdminUser.headimage=="" || sessionScope.CurrentAdminUser.headimage==null ? 'nohead.jpg' : sessionScope.CurrentAdminUser.headimage}" alt="" class="system-layout-item-nav-headimg"> ${sessionScope.CurrentAdminUser.account}<span class="square-icon"></span></a>
                        <!-- 导航子菜单，可以在每一个菜单项的a标签后面添加-->
                        <ul class="system-layout-item-child">
                            <li class="system-layout-item-child-li"><a href="javascript:;">个人中心</a></li>
                            <li class="system-layout-item-child-li"><a href="javascript:;">修改密码</a></li>
                        </ul>
                    </li>
                    <li class="system-layout-item">
                        <a onclick="systemExit()" href="javascript:;"><img src="img/icon/icon-exit.png" alt="" class="system-layout-item-nav-img"> 退出</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 导航结束 -->
    </div>
    <!-- header结束 -->
    <!-- 左侧边栏导航开始 -->
    <div class="system-side">
        <!-- 导航菜单开关按钮 -->
        <div class="system-side-menu-switch">
            <img src="img/icon/icon-more.png">
        </div>
        <!-- 导航菜单项开始 -->
        <div class="side-menu-items">
            <!-- 带文字的导航菜单项 -->
            <div class="side-menu-item">
                <div class="side-menu-item-title"><span class="side-menu-square"></span>用户管理</div>
                <ul>
                    <li><a onclick="createTab({title:'用户中心',isShowClose:true,url:'system/userCenter'})" href="javascript:;"><img src="img/icon/icon-user.png" class="side-menu-a-img">用户中心</a></li>
                    <li><a href="javascript:;"><img src="img/icon/icon-role-mgr.png" class="side-menu-a-img">角色管理</a></li>
                    <li><a href="javascript:;"><img src="img/icon/icon-limits-mgr.png" class="side-menu-a-img">权限管理</a></li>
                </ul>
            </div>
            <%--<div class="side-menu-item">
                <div class="side-menu-item-title"><span class="side-menu-square"></span>题库管理</div>
                <ul>
                    <li><a href="javascript:;"><img src="img/icon/icon-question-bank.png" class="side-menu-a-img">题库中心</a></li>
                </ul>
            </div>--%>
            <div class="side-menu-item">
                <div class="side-menu-item-title"><span class="side-menu-square"></span>数据管理</div>
                <ul>
                    <li><a onclick="createTab({title:'数据中心',isShowClose:true,url:'system/data'})" href="javascript:;"><img src="img/icon/icon-data.png" class="side-menu-a-img">数据中心</a></li>
                </ul>
            </div>
            <div class="side-menu-item">
                <div class="side-menu-item-title"><span class="side-menu-square"></span>问卷管理</div>
                <ul>
                    <li><a onclick="createTab({title:'问卷中心',isShowClose:true,url:'system/questionnaires'})" href="javascript:;"><img src="img/icon/icon-questionnaire.png" class="side-menu-a-img">问卷中心</a></li>
                </ul>
            </div>
            <div class="side-menu-item">
                <div class="side-menu-item-title"><span class="side-menu-square"></span>信息管理</div>
                <ul>
                    <li><a onclick="createTab({title:'信息中心',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-info-center.png" class="side-menu-a-img">信息中心</a></li>
                </ul>
            </div>
            <div class="side-menu-item">
                <div class="side-menu-item-title"><span class="side-menu-square"></span>儿童之家网站</div>
                <ul>
                    <li><a onclick="createTab({title:'轮播图',isShowClose:true,url:'system/loop'})" href="javascript:;"><img src="img/icon/icon-loop.png" class="side-menu-a-img">轮播图</a></li>
                    <li><a onclick="createTab({title:'儿童资讯',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-news.png" class="side-menu-a-img">儿童资讯</a></li>
                    <li><a onclick="createTab({title:'儿童图片',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-picture.png" class="side-menu-a-img">儿童图片</a></li>
                    <li><a onclick="createTab({title:'儿童视频',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-video.png" class="side-menu-a-img">儿童视频</a></li>
                    <li><a onclick="createTab({title:'留言板',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-panel.png" class="side-menu-a-img">留言板</a></li>
                </ul>
            </div>
        </div>

        <!-- 带文字的导航菜单项结束 -->
        <!-- 关闭后小图标菜单项开始 -->
        <div class="side-menu-small-items">
            <!-- 小图标菜单项 -->
            <div class="side-menu-small-item">
                <div data-toast="用户管理" class="side-menu-small-item-icon"><span class="side-menu-small-square" ></span></div>
                <ul>
                    <li data-toast="用户中心"><a onclick="createTab({title:'用户中心',isShowClose:true,url:'system/userCenter'})" href="javascript:;"><img src="img/icon/icon-user.png" class="side-menu-small-a-img"></a></li>
                    <li data-toast="角色管理"><a href="javascript:;"><img src="img/icon/icon-role-mgr.png" class="side-menu-small-a-img"></a></li>
                    <li data-toast="权限管理"><a href="javascript:;"><img src="img/icon/icon-limits-mgr.png" class="side-menu-small-a-img"></a></li>
                </ul>
            </div>
            <%--<div class="side-menu-small-item">
                <div data-toast="题库管理" class="side-menu-small-item-icon"><span class="side-menu-small-square" ></span></div>
                <ul>
                    <li data-toast="题库中心"><a href="javascript:;"><img src="img/icon/icon-question-bank.png" class="side-menu-small-a-img"></a></li>
                </ul>
            </div>--%>
            <div class="side-menu-small-item">
                <div data-toast="数据管理" class="side-menu-small-item-icon"><span class="side-menu-small-square" ></span></div>
                <ul>
                    <li data-toast="数据中心"><a onclick="createTab({title:'数据中心',isShowClose:true,url:'system/data'})" href="javascript:;"><img src="img/icon/icon-data.png" class="side-menu-small-a-img"></a></li>
                </ul>
            </div>
            <div class="side-menu-small-item">
                <div data-toast="问卷管理" class="side-menu-small-item-icon"><span class="side-menu-small-square" ></span></div>
                <ul>
                    <li data-toast="问卷中心"><a onclick="createTab({title:'问卷中心',isShowClose:true,url:'system/questionnaires'})" href="javascript:;"><img src="img/icon/icon-questionnaire.png" class="side-menu-small-a-img"></a></li>
                </ul>
            </div>
            <div class="side-menu-small-item">
                <div data-toast="信息管理" class="side-menu-small-item-icon"><span class="side-menu-small-square" ></span></div>
                <ul>
                    <li data-toast="信息中心"><a onclick="createTab({title:'信息中心',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-info-center.png" class="side-menu-small-a-img"></a></li>
                </ul>
            </div>
            <div class="side-menu-small-item">
                <div data-toast="儿童之家网站" class="side-menu-small-item-icon"><span class="side-menu-small-square" ></span></div>
                <ul>
                    <li data-toast="轮播图"><a onclick="createTab({title:'轮播图',isShowClose:true,url:'system/loop'})" href="javascript:;"><img src="img/icon/icon-loop.png" class="side-menu-small-a-img"></a></li>
                    <li data-toast="儿童资讯"><a onclick="createTab({title:'儿童资讯',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-news.png" class="side-menu-small-a-img"></a></li>
                    <li data-toast="儿童图片"><a onclick="createTab({title:'儿童图片',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-picture.png" class="side-menu-small-a-img"></a></li>
                    <li data-toast="儿童视频"><a onclick="createTab({title:'儿童视频',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-video.png" class="side-menu-small-a-img"></a></li>
                    <li data-toast="留言板"><a onclick="createTab({title:'留言板',isShowClose:true,url:'system/info'})" href="javascript:;"><img src="img/icon/icon-panel.png" class="side-menu-small-a-img"></a></li>
                </ul>
            </div>
        </div>
        <!-- 小图标导航菜单项结束 -->
    </div>
    <!-- 侧边栏导航结束 -->
    <!-- 主面板开始 -->
    <div class="system-content">
        <!-- tab选项卡开始 -->

        <div class="system-tab">
            <!-- tab选项卡导航开始 -->
            <div class="system-tab-nav">
                <ul>
                    <!-- 导航项 -->
                </ul>
            </div>
            <!-- 导航项具体内容 -->
            <div class="system-content-iframe">

            </div>
        </div>
    </div>
    <!-- footer开始 -->
    <div class="system-footer">
        <span>版权所有©合肥海思数据分析有限公司</span>
    </div>
    <!-- footer结束 -->
</div>


<script>
    var layer;
    layui.use(['layer'],function () {
        layer=layui.layer;

    });
    function systemExit(){

        layer.confirm("确定退出系统吗？",function (index) {
            var loadIndex=layer.load(0);
            layer.close(index);
            $.get( 'system/exit',function () {
                layer.close(loadIndex);
                window.location.href="system/login";
            });
        },function () {
            layer.close(loadIndex);
        });
    }
</script>
</body>
</html>
