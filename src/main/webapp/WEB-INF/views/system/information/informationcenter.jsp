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
    <style>
        ::-webkit-scrollbar {
            width: 5px;
            height: 5px;
        }
    </style>
</head>

<body oncontextmenu="return false" onselect="return false">
<div class="questionnaire-top">
    <div class="questionnaire-search" style="float:left; margin-left: 25px;">
        <input type="search" name="sname" placeholder="请输入学校名称">
        <div onclick="searchSuccessUser()" class="questionnaire-search-button"><img src="img/icon/icon-search.png"></div>
    </div>
    <div class="information-toolbar">
        <img onclick="addUser()" src="img/icon/icon-more2.png" alt="添加学校" title="添加">
        <img onclick="reflashUserOnSuccessTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
        <img src="img/icon/icon-delete.png" data-type="getCheckData" class="demoTable" alt="删除选中学校" title="删除">
    </div>
</div>
<div class="information-content">
    <table id="dynamic-table-info" lay-filter="infoTable"></table>
</div>
</body>
<script>
    var layui_tab_item_width = $('.information-content').width();
    var successUserTable;
    var loadIndex;
    layui.use(['element', 'table'], function () {
        var element = layui.element;
        var table = layui.table;
        //一些事件监听
        element.on('tab(demo)', function (data) {
        });
//        动态表格
        successUserTable=table.render({
            elem: '#dynamic-table-info', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'infoTable',
            url:'system/getInfoList',
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'aid', title: '序号', sort: true},
                {width: layui_tab_item_width * 0.08, field: 'schoolname', title: '学校名称'},
                {width: layui_tab_item_width * 0.08, field: 'headmaster', title: '校长'},
                {width: layui_tab_item_width * 0.06, field: 'address', title: '地址'},
                {width: layui_tab_item_width * 0.1, field: 'playhousename', title: '儿童之家名称'},
                {width: layui_tab_item_width * 0.06, field: 'booknumber', title: '图书数量'},
                {width: layui_tab_item_width * 0.06, field: 'childrennumber', title: '儿童数量'},
                {width: layui_tab_item_width * 0.06, field: 'withquestionnairenumber', title: '参与问卷'},
                {width: layui_tab_item_width * 0.1, field: 'lastlogintime', title: '最后一次登录时间'},
                {width: layui_tab_item_width * 0.14, field: 'remarks', title: '备注'},
                {width: layui_tab_item_width * 0.18,fixed: 'right', align:'center',toolbar:'#infoToolBar',title:'操作'}
            ]],
            size: 'lg',
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
                        $.each(checkStatus.data,function (index,obj) {
                            loadIndex=layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/userInfoDel',
                                data: {'id':obj.id},
                                success: function (result) {
                                    if(index==data.length-1){
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashUserOnSuccessTable();
                                    }
                                },
                                error: function(data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！"+JSON.stringify(data));
                                }
                            });
                        });
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
            if(layEvent === 'del'){ //删除
                layer.confirm('真的删除该用户吗？该操作无法恢复', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                    loadIndex=layer.load();
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/userInfoDel',
                        data: {'uid':data.id},
                        success: function (result) {
                            layer.close(loadIndex);
                            if(result.status==0){
                                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            }
                            layer.msg(result.msg);
                        },
                        error: function(data) {
                            layer.close(loadIndex);
                            layer.alert("出现异常！");
                        }
                    });
                });
            } else if(layEvent === 'edit'){ //编辑
                editorUser(data);
            } else if(layEvent==='studentManager'){
                if(data.childrennumber==="" || data.childrennumber==0){
                    layer.msg("该用户下没有学生");
                    return;
                }
                layer.open({
                    title:data.schoolname+'学生信息',
                    type:2,
                    area: ['80%', '70%'],
                    maxmin:true,
                    content:'system/getStudent?uid='+data.id,
                    skin:'layui-layer-molv'
                });
            }
        });
    });
    function reflashUserOnSuccessTable (){
        successUserTable.reload({
            url:'system/getInfoList'
        });
    }
    function searchSuccessUser(){
        var schoolName=$(":input[name='sname']").val();
        if(schoolName===""){
            layer.msg("请输入学校名称！",{icon:2,time:3000});
            return;
        }
        successUserTable.reload({
            url:'system/searchInfoList?schoolname='+schoolName
        });
    }



    function addUser() {
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                title:'添加普通用户',
                type:2,
                area: ['50%', '70%'],
                content:'system/getUserAdd',
                skin:'layui-layer-molv'
            });
        });
    }
    function editorUser(data) {
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                title: data.schoolname+' 用户编辑',
                type:2,
                area: ['50%', '70%'],
                content:'system/getEditorAdd?id='+data.id,
                skin:'layui-layer-molv'
            });
        });
    }
</script>
<script id="infoToolBar" type="text/html">
    <a class="layui-btn layui-btn-mini" lay-event="studentManager">学生管理</a>
    <a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>
<script id="headimg" type="text/html">
    <img src="{{d.headimg}}" style="width: 40px;height: 40px;">
</script>
</html>
