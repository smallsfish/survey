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
    <div class="questionnaire-search" style="float:left; margin-left: 25px;">
        <input type="search" name="qname" placeholder="请输入学生名称">
        <div class="questionnaire-search-button"><img src="img/icon/icon-search.png"></div>
    </div>
    <div class="information-toolbar">
        <img onclick="addSchoolUser()" src="img/icon/icon-more2.png" alt="添加管理员" title="添加">
        <img src="img/icon/icon-delete.png" data-type="getCheckData" class="demoTable" alt="删除选中管理员" title="删除">
        <img src="img/icon/icon-reflash2.png" alt="刷新当前页面" title="刷新">
    </div>
</div>
<div class="information-content">
    <table id="dynamic-table-student-info" lay-filter="infoTable"></table>
</div>
</body>
<script>
    var layui_tab_item_width = $('.information-content').width();
    layui.use(['element', 'table'], function () {
        var element = layui.element;
        var table = layui.table;
        //一些事件监听
        element.on('tab(demo)', function (data) {
            console.log(data);
        });
//        动态表格
        table.render({
            elem: '#dynamic-table-student-info', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'infoTable',
            height: 'full-110', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'id', title: '序号', sort: true},
                {width: layui_tab_item_width * 0.08, field: 'schoolName', title: '学校名称'},
                {width: layui_tab_item_width * 0.05, field: 'headMaster', title: '校长'},
                {width: layui_tab_item_width * 0.06, field: 'address', title: '地址'},
                {width: layui_tab_item_width * 0.07, field: 'familyName', title: '儿童之家名称'},
                {width: layui_tab_item_width * 0.06, field: 'bookNumber', title: '图书数量'},
                {width: layui_tab_item_width * 0.06, field: 'childrenNumber', title: '儿童数量',edit:'text'},
                {width: layui_tab_item_width * 0.06, field: 'teacherNumber', title: '老师数量'},
                {width: layui_tab_item_width * 0.06, field: 'withQuestionnaireNumber', title: '参与问卷'},
                {width: layui_tab_item_width * 0.1, field: 'lastLoginTime', title: '最后一次登录时间'},
                {width: layui_tab_item_width * 0.22, align:'center',templet:'#infoToolBar',title:'操作'}
            ]],
            size: 'lg',
            data: [{
                id:1,
                schoolName:'合肥一小',
                headMaster:'xxx',
                address:'合肥市xxxxx',
                familyName:'xxxx',
                bookNumber:90,
                childrenNumber:35,
                teacherNumber:123,
                withQuestionnaireNumber:234,
                lastLoginTime:'2017-06-14 14:25:45'
            }],
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });
        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('infoTable')
                    ,data = checkStatus.data;
                if(data.length==0){
                    layer.msg("请选择要删除的信息！",{icon:2})
                }else{
                    layer.confirm("确定删除所选行？",{icon:2,title:'系统提示'},function (index) {

                        layer.alert("删除成功"+JSON.stringify(checkStatus),{icon:1});
                    })
                }
            }
        };
        $('.usertools .demoTable').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        table.on('tool(infoTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                layer.msg("查看学校具体信息");
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if(layEvent === 'edit'){ //编辑
                layer.msg("编辑学校具体信息");
            }
        });
    });
</script>
<script id="infoToolBar" type="text/html">
    <a class="layui-btn " lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger" lay-event="del">删除</a>
</script>
</html>
