<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../base.jsp"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>test</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
</head>

<body oncontextmenu="return false" onselect="return false">
<div class="questionnaire-top">
    <div class="questionnaire-search">
        <input type="search" name="qname" placeholder="请输入问卷名称">
        <div class="questionnaire-search-button"><img src="img/icon/icon-search.png"></div>
    </div>
</div>
<div class="questionnaire-content">
    <div class="questionnaire-item">
        <div class="questionnaire-one">
            <ul>
                <li>留守儿童问卷调查sssssssssss</li>
                <li>B:2017-09-15 15:46:58</li>
                <li>E:2017-09-18 15:46:58</li>
                <li>参与学校：56所</li>
                <li>参与城市：15座</li>
                <li>参与人数：5486人</li>
            </ul>
        </div>
        <div class="questionnaire-two">
            <button onclick="window.parent.createTab({title:'留守儿童问卷调查sssssssssss数据分析',isShowClose:true,url:'system/da'})" class="layui-btn  layui-btn-radius">数据分析</button>
        </div>
    </div>
</div>
<div class="questionnaire-page">
    <div id="test1" style="float:right; margin-right: 20px;height:0;"></div>
</div>
</body>
<script>
    layui.use('laypage', function(){
        var laypage = layui.laypage;
        //执行一个laypage实例
        laypage.render({
            elem: 'test1', //注意，这里的 test1 是 ID，不用加 # 号
            count: 500, //数据总数，从服务端得到
            limit:8,
            limits:[8,16,32,64],
            layout:['prev','page','next','limit','skip','count'],
            jump:function(obj,first){
                //首次不执行
                if(!first){
                    alert("跳转到第"+obj.curr);
                }
            }
        });
    });
</script>
</html>
