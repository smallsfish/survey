<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../base.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, role-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
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
        <input type="search" name="rname" placeholder="请输入角色名称">
        <div onclick="searchSuccessRole()" class="questionnaire-search-button"><img src="img/icon/icon-search.png">
        </div>
    </div>
    <div class="information-toolbar">
        <shiro:hasPermission name="role:add">
            <img onclick="addRole()" src="img/icon/icon-more2.png" alt="添加角色" title="添加">
        </shiro:hasPermission>
        <img onclick="reflashRoleOnSuccessTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
        <shiro:hasPermission name="role:delete">
            <img src="img/icon/icon-delete.png" data-type="getCheckData" class="demoTable" alt="删除选中角色" title="删除">
        </shiro:hasPermission>
    </div>
</div>
<div class="information-content">
    <table id="dynamic-table-info" lay-filter="infoTable"></table>
</div>
</body>
<script>
    var layui_tab_item_width = $('.information-content').width();
    var successRoleTable;
    var loadIndex;
    layui.use(['element', 'table', 'form'], function () {
        var element = layui.element;
        var table = layui.table;
        var form = layui.form;
        form.on('switch(roleAvailable)', function (data) {
            var available = 0;
            if (data.elem.checked) {
                available = 1;
            }
            loadIndex = layer.load();
            $.ajax({
                type: "GET",
                dataType: "json",
                url: 'system/roleAvailableUpdate',
                data: {'id': data.value, 'available': available},
                success: function (result) {
                    layer.close(loadIndex);
                    layer.msg(result.msg);
                },
                error: function (data) {
                    layer.close(loadIndex);
                    layer.alert("出现异常！");
                }
            });
        });
        //一些事件监听
        element.on('tab(demo)', function (data) {
        });
//        动态表格
        successRoleTable = table.render({
            elem: '#dynamic-table-info', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'infoTable',
            url: 'system/roleList',
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'aid', title: '序号', sort: true},
                {width: layui_tab_item_width * 0.08, field: 'rolename', title: '角色名称'},
                {width: layui_tab_item_width * 0.4, field: 'resources', title: '资源'},
                {width: layui_tab_item_width * 0.165, field: 'description', title: '描述'},
                {width: layui_tab_item_width * 0.1, field: 'available', toolbar: '#available', title: '是否可用'},
                {
                    width: layui_tab_item_width * 0.18,
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#infoToolBar',
                    title: '操作'
                }
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });
        var $ = layui.$, active = {
            getCheckData: function () { //获取选中数据
                var checkStatus = table.checkStatus('infoTable')
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
                                url: 'system/roleDel',
                                data: {'id': obj.id},
                                success: function (result) {
                                    if (index == data.length - 1) {
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashRoleOnSuccessTable();
                                    }
                                },
                                error: function (data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！" + JSON.stringify(data));
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
        table.on('tool(infoTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if (layEvent === 'del') { //删除
                layer.confirm('真的删除该角色吗？该操作无法恢复', function (index) {
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                    loadIndex = layer.load();
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/roleDel',
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
                editorRole(data);
            }
        });
    });

    function reflashRoleOnSuccessTable() {
        successRoleTable.reload({
            url: 'system/roleList'
        });
    }

    function searchSuccessRole() {
        var name = $(":input[name='rname']").val();
        if (name === "") {
            layer.msg("请输入角色名称！", {icon: 2, time: 3000});
            return;
        }
        successRoleTable.reload({
            url: 'system/searchRole?name=' + name
        });
    }


    function addRole() {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '添加角色',
                type: 2,
                area: ['50%', '70%'],
                content: 'system/getAddRole',
                skin: 'layui-layer-molv'
            });
        });
    }

    function editorRole(data) {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: data.rolename + ' 角色编辑',
                type: 2,
                area: ['50%', '70%'],
                content: 'system/getRoleEditor?id=' + data.id,
                skin: 'layui-layer-molv'
            });
        });
    }
</script>
<script id="infoToolBar" type="text/html">
    <shiro:hasPermission name="role:update">
        <a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:delete">
        <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
    </shiro:hasPermission>
</script>
<script id="available" type="text/html">
    <form class="layui-form">
        <input type="checkbox" name="switch"
               {{# if(d.available==true){ }}
               checked
               {{# } }}
               value="{{d.id}}" lay-filter="roleAvailable" lay-skin="switch">
    </form>
</script>
</html>
