<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="base.jsp"%>
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


    <!-- 主面板开始 -->
    <div class="system-content">
        <!-- tab选项卡开始 -->

        <%--简洁风格--%>
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title" style="width: 250px;">
                <li onclick="sysUser()" class="layui-this" style="font-size: large">系统用户</li>
                <li onclick="" style="font-size: large">普通用户</li>
            </ul>
            <div class="layui-tab-content" style="height: 100px;">
                <div class="layui-tab-item layui-show">
                    weftrwertgewr
                </div>
                <div  class="layui-tab-item">
                    普通用户
                </div>
            </div>
        </div>
    </div>

<script>
    layui.use('element', function(){
        var $ = layui.jquery
            ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
    });

    function sysUser(){
        $.ajax({
            type:'get',
            url:'user',
            data:'name=wtf',
            /*dataType:'json',*/
            success:function(data){
                $(".layui-show:eq(0)").append(
                    "<table style='background-color:#afff98;' class='layui-table' lay-data='{height:332, url:\"\", page:true, id:\"idTest\"}' lay-filter='demo'>"+
                    "<thead>"+
                    "<tr>"+
                    "<th lay-data='{field:\"id\", width:80, sort: true, fixed: true}'>"+"序号"+"</th>"+
                    "<th lay-data='{field:\"username\", width:80}'>"+"账号"+"</th>"+
                    "<th lay-data='{field:\"sex\", width:80, sort: true}'>"+"头像"+"</th>"+
                    "<th lay-data='{field:\"city\", width:80}'>"+"昵称"+"</th>"+
                    "<th lay-data='{field:\"sign\", width:177}'>"+"角色"+"</th>"+
                    "<th lay-data='{field:\"experience\", width:80, sort: true}'>"+"性别"+"</th>"+
                    "<th lay-data='{field:\"score\", width:80, sort: true}'>"+"创建时间"+"</th>"+
                    "<th lay-data='{field:\"classify\", width:80}'>"+"最后一次登录时间"+"</th>"+
                    "<th lay-data='{field:\"wealth\", width:135, sort: true}'>"+"当前状态"+"</th>"+
                    "<th lay-data='{fixed: \"right\", width:160, align:\"center\", toolbar:\"#barDemo\"}'>"+"操作"+"</th>"+
                    "</tr>"+
                    "</thead>"+
                    "</table>");

            }
        });






    /*function ordUser(){
        $.ajax({
            type:'post',
            url:'',
            data:'{"name":"ps4","price":1420}',
            dataType:'json',
            success:function(data){
                alert(data);
                $("[class='layui-tab-item']").html(
                    "<p>"+hh+"</p>"
                );
            }
        });*/

    }
</script>
</body>
</html>
