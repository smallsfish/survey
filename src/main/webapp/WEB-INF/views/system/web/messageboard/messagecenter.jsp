<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../../base.jsp" %>
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
        <input type="search" name="vname" placeholder="请输入姓名">
        <div onclick="searchSuccessMSG()" class="questionnaire-search-button"><img src="img/icon/icon-search.png">
        </div>
    </div>
    <div class="information-toolbar">
        <img onclick="reflashMSGOnSuccessTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
        <img src="img/icon/icon-delete.png" data-type="getCheckData" class="demoTable" alt="删除选中留言信息" title="删除">
    </div>
</div>
<div class="msgrmation-content">
    <table id="dynamic-table-msg" lay-filter="msgTable"></table>
</div>
</body>
<script>
    var layui_tab_item_width = $('.msgrmation-content').width();
    var successMSGTable;
    var loadIndex;
    layui.use(['element', 'table'], function () {
        var element = layui.element;
        var table = layui.table;
        //一些事件监听
        element.on('tab(demo)', function (data) {
        });
//        动态表格
        successMSGTable = table.render({
            elem: '#dynamic-table-msg', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'msgTable',
            url: 'system/getMSGList',
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'aid', title: '序号', sort: true},
                {width: layui_tab_item_width * 0.08, field: 'name', title: '真实姓名'},
                {width: layui_tab_item_width * 0.15, field: 'telphone', title: '联系电话'},
                {width: layui_tab_item_width * 0.274, field: 'msg', title: '内容'},
                {width: layui_tab_item_width * 0.08, field: 'isread', title: '状态'},
                {width: layui_tab_item_width * 0.16, field: 'createtime', title: '提交时间'},
                {
                    width: layui_tab_item_width * 0.18,
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#msgToolBar',
                    title: '操作'
                }
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });
        var $ = layui.$, active = {
            getCheckData: function () { //获取选中数据
                var checkStatus = table.checkStatus('msgTable')
                    , data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择要删除的信息！", {icon: 2})
                } else {
                    layer.confirm("确定删除所选行？", {icon: 2, title: '系统提示'}, function (index) {
                        $.each(checkStatus.data, function (index, obj) {
                            loadIndex = layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/msgDel',
                                data: { 'id': obj.id },
                                success: function (result) {
                                    if (index == data.length - 1) {
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashMSGOnSuccessTable();
                                    }
                                },
                                error: function (data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！");
                                }
                            });
                        });
                    })
                }
            }
        };
        $('.information-toolbar .demoTable').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        table.on('tool(msgTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                var data = obj.data; //获得当前行数据
                var layEvent = obj.event; //获得 lay-event 对应的值
                var tr = obj.tr; //获得当前行 tr 的DOM对象
                if (layEvent === 'del') { //删除
                    layer.confirm('真的删除该留言信息吗？该操作无法恢复', function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        //向服务端发送删除指令
                        loadIndex = layer.load();
                        $.ajax({
                            type: "GET",
                            dataType: "json",
                            url: 'system/msgDel',
                            data: {'id': data.id},
                            success: function (result) {
                                layer.close(loadIndex);
                                if (result.status == 0) {
                                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                                }
                                layer.msg(result.msg);
                            },
                            error: function (data) {
                                layer.close(loadIndex);
                                layer.alert("出现异常！");
                            }
                        });
                    });
                } else if (layEvent === 'edit') { //编辑
                    editorMSG(data);
                }
            }
        );
    });

    function reflashMSGOnSuccessTable() {
        successMSGTable.reload({
            url: 'system/getMSGList'
        });
    }

    function searchSuccessMSG() {
        var msgTitle = $(":input[name='vname']").val();
        if (msgTitle === "") {
            layer.msg("请输入留言信息标题！", {icon: 2, time: 3000});
            return;
        }
        successMSGTable.reload({
            url: 'system/searchMsg?name=' + msgTitle
        });
    }



    function editorMSG(data) {
        layui.use('layer', function () {
            var isread;
            if(data.isread==="已读"){
                isread=true;
            }else{
                isread=false;
            }
            var layer = layui.layer;
            layer.open({
                title: data.msgtitle + ' 留言信息编辑',
                type: 2,
                area: ['50%', '70%'],
                content: 'system/getEditorMsg?id=' + data.id+'&isRead='+isread,
                skin: 'layui-layer-molv'
            });
        });
    }
</script>
<script id="msgToolBar" type="text/html">
    <a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>
<script id="headimg" type="text/html">
    <img src="uploadimage/{{d.imageurl}}" style="height: 40px;">
</script>
</html>
