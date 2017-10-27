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
    <div class="information-toolbar">
        <img onclick="addLoopPicture()" src="img/icon/icon-more2.png" alt="添加轮播图" title="添加">
        <img onclick="reflashLoopPictureTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
        <img src="img/icon/icon-delete.png" data-type="getCheckData" class="demoTable" alt="删除选中轮播图" title="删除">
    </div>
</div>
<div class="information-content">
    <table id="dynamic-table-loop" lay-filter="loopTable"></table>
</div>
</body>
<script>
    var layui_tab_item_width = $('.information-content').width();
    var loopTable,loadIndex;
    layui.use([ 'table','form'], function () {
        var table = layui.table;
        var form = layui.form;
        form.on('switch(loopShow)', function(data){
            var isshow=0;
            if(data.elem.checked){
                isshow=1;
            }
            loadIndex=layer.load();
            $.ajax({
                type: "GET",
                dataType: "json",
                url: 'system/alterLoopSortOrIsShow',
                data: {'id':data.value,'isshow':isshow},
                success: function (result) {
                    layer.close(loadIndex);
                    layer.msg(result.msg);
                },
                error: function(data) {
                    layer.close(loadIndex);
                    layer.alert("出现异常！");
                }
            });
        });
//        动态表格
        loopTable=table.render({
            elem: '#dynamic-table-loop', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'loopTable',
            url:'system/getLoopList',
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.2, field: 'url', title: '超链接'},
                {width: layui_tab_item_width * 0.3, field: 'imageurl', title: '图片地址'},
                {width: layui_tab_item_width * 0.14, field: 'imageurl', templet:'#loopimg', title: '缩略图'},
                {width: layui_tab_item_width * 0.14, field: 'isshow',templet:'#isshow', title: '是否显示'},
                {width: layui_tab_item_width * 0.12, field: 'sort',edit:'text', title: '排序',sort:true},
                {width: layui_tab_item_width * 0.22,fixed: 'right', align:'center',toolbar:'#loopToolBar',title:'操作'}
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });
        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('loopTable')
                    ,data = checkStatus.data;
                if(data.length==0){
                    layer.msg("请选择要删除的轮播图！",{icon:2})
                }else{
                    layer.confirm("确定删除所选轮播图？",{icon:2,title:'系统提示'},function (index) {
                        $.each(checkStatus.data,function (index,obj) {
                            loadIndex=layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/loopDel',
                                data: {'id':obj.id},
                                success: function (result) {
                                    if(index==data.length-1){
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashLoopPictureTable();
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
        $('.demoTable').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        table.on('tool(loopTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'del'){ //删除
                layer.confirm('真的删除该轮播图？该操作无法恢复', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                    loadIndex=layer.load();
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/loopDel',
                        data: {'id':data.id},
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
                layer.msg("编辑学校具体信息");
            }
        });
        table.on('edit(loopTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
//            console.log(obj.value); //得到修改后的值
//            console.log(obj.field); //当前编辑的字段名
//            console.log(obj.data); //所在行的所有相关数据
            loadIndex=layer.load();
            $.ajax({
                type: "GET",
                dataType: "json",
                url: 'system/alterLoopSortOrIsShow',
                data: {'id':obj.data.id,'sort':obj.value},
                success: function (result) {
                    layer.close(loadIndex);
                    layer.msg(result.msg);
                },
                error: function(data) {
                    layer.close(loadIndex);
                    layer.alert("出现异常！");
                }
            });
        });
    });
    function reflashLoopPictureTable (){
        loopTable.reload({
            url:'system/getLoopList'
        });
    }

    function addLoopPicture() {
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                title:'添加轮播图',
                type:2,
                area: ['50%', '70%'],
                content:'system/getLoopAdd',
                skin:'layui-layer-molv'
            });
        });
    }
</script>
<script id="loopToolBar" type="text/html">
    <a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>
<script id="loopimg" type="text/html">
    <img  src="{{d.imageurl}}" style="width: 40px;"/>
</script>
<script id="isshow" type="text/html">
    <form class="layui-form">
        <input type="checkbox" name="switch"
               {{# if(d.isshow==true){ }}
                checked
               {{# } }}
               value="{{d.id}}" lay-filter="loopShow" lay-skin="switch">
    </form>
</script>
</html>
