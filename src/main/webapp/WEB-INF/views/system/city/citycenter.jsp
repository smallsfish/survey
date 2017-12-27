<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../base.jsp" %>
<body oncontextmenu="return false" onselect="return false">
<div class="layui-tab layui-tab-brief" style="margin: 0; height: 99.9%;" lay-filter="docDemoTabBrief">
    <ul class="layui-tab-title">
        <shiro:hasPermission name="city:view">
            <li class="layui-this">县</li>
            <li>市</li>
            <li>省</li>
        </shiro:hasPermission>
    </ul>
    <div class="layui-tab-content" style="height: calc( 100% - 60px );">
        <div class="layui-tab-item layui-show" style="height: 94%;">
            <div class="usertools">
                <shiro:hasPermission name="city:delete">
                    <img src="img/icon/icon-delete.png" data-type="checkCountyData" class="demoTable" alt="删除选中县"
                         title="删除">
                </shiro:hasPermission>
                <img onclick="reflashCountyTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
                <shiro:hasPermission name="city:add">
                    <img onclick="addCounty()" src="img/icon/icon-more2.png" alt="添加县" title="添加">
                </shiro:hasPermission>
            </div>
            <table id="dynamic-table-county" lay-filter="countyTable"></table>
        </div>
        <div class="layui-tab-item" style="height: 94%;">
            <div class="usertools">
                <shiro:hasPermission name="city:delete">
                    <img src="img/icon/icon-delete.png" data-type="checkCityData" class="demoTable" alt="删除选中市"
                         title="删除">
                </shiro:hasPermission>
                <img onclick="reflashCityTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
                <shiro:hasPermission name="city:add">
                    <img onclick="addCity()" src="img/icon/icon-more2.png" alt="添加市" title="添加">
                </shiro:hasPermission>
            </div>
            <table id="dynamic-table-city" lay-filter="cityTable"></table>
        </div>
        <div class="layui-tab-item" style="height: 94%;">
            <div class="usertools">
                <shiro:hasPermission name="city:delete">
                    <img src="img/icon/icon-delete.png" data-type="checkProvinceData" class="demoTable" alt="删除选中省份"
                         title="删除">
                </shiro:hasPermission>
                <img onclick="reflashProvicneTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
                <shiro:hasPermission name="city:add">
                    <img onclick="addProvince()" src="img/icon/icon-more2.png" alt="添加省份" title="添加">
                </shiro:hasPermission>
            </div>
            <table id="dynamic-table-province" lay-filter="provinceTable"></table>
        </div>
    </div>
</div>
</body>
<script>
    var layui_tab_item_width = $('.layui-tab-item').width();
    var table, countyTable, layer, loadIndex, cityTable, provinceTable;
    layui.use(['element', 'table','layer'], function () {
        var element = layui.element;
        table = layui.table;
        layer = layui.layer;
//        县动态表格
        countyTable = table.render({
            elem: '#dynamic-table-county', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'countyTable',
            url: 'system/getCountyList',
            method: 'get',
            loading: true,
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.05, field: 'id', title: 'ID', sort: true},
                {width: layui_tab_item_width * 0.26, field: 'provincename', title: '所属省'},
                {width: layui_tab_item_width * 0.26, field: 'cityname', title: '所属市'},
                {width: layui_tab_item_width * 0.253, field: 'countyname', title: '县名'},
                {
                    width: layui_tab_item_width * 0.141,
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#countyToolBar',
                    title: '操作'
                }
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });
        var $ = layui.$, active = {
            checkCountyData: function () { //获取选中数据
                var checkStatus = table.checkStatus('countyTable')
                    , data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择要删除的信息！", {icon: 2})
                } else {
                    layer.confirm("确定删除所选用户吗？", {icon: 2, title: '系统提示'}, function (index) {
                        $.each(checkStatus.data, function (index, obj) {
                            loadIndex = layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/countyDel',
                                data: {'id': obj.id},
                                success: function (result) {
                                    if (index == data.length - 1) {
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashCountyTable();
                                    }
                                },
                                error: function (data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！" + JSON.stringify(data));
                                }
                            });
                        });
                    });
                }
            },
            checkCityData: function () { //获取选中数据
                var checkStatus = table.checkStatus('cityTable')
                    , data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择要删除的信息！", {icon: 2})
                } else {
                    layer.confirm("确定删除所选用户吗？", {icon: 2, title: '系统提示'}, function (index) {
                        $.each(checkStatus.data, function (index, obj) {
                            loadIndex = layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/cityDel',
                                data: {'id': obj.id},
                                success: function (result) {
                                    if (index == data.length - 1) {
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashCityTable();
                                    }
                                },
                                error: function (data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！" + JSON.stringify(data));
                                }
                            });
                        });
                    });
                }
            },
            checkProvinceData: function () { //获取选中数据
                var checkStatus = table.checkStatus('provinceTable')
                    , data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择要删除的信息！", {icon: 2})
                } else {
                    layer.confirm("确定删除所选用户吗？", {icon: 2, title: '系统提示'}, function (index) {
                        $.each(checkStatus.data, function (index, obj) {
                            loadIndex = layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/provinceDel',
                                data: {'id': obj.id},
                                success: function (result) {
                                    if (index == data.length - 1) {
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashProvicneTable();
                                    }
                                },
                                error: function (data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！" + JSON.stringify(data));
                                }
                            });
                        });
                    });
                }
            }
        };
        $('.usertools .demoTable').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        table.on('tool(countyTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') { //查看
                //do somehing
                layer.open({
                    title: '修改' + data.countyname,
                    type: 2,
                    area: ['50%', '70%'],
                    content: 'system/getEditorCounty?id=' + data.id,
                    skin: 'layui-layer-molv'
                });
            } else if (layEvent === 'del') { //删除
                layer.confirm('真的删除该用户吗？', function (index) {
                    loadIndex = layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/countyDel',
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
                            layer.alert("出现异常！" + JSON.stringify(data));
                        }
                    });
                });
            }
        });





        provinceTable = table.render({
            elem: '#dynamic-table-province', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'provinceTable',
            url: 'system/getProvinceList',
            method: 'get',
            loading: true,
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'id', title: 'ID', sort: true},
                {width: layui_tab_item_width * 0.785, field: 'provincename', title: '省名'},
                {
                    width: layui_tab_item_width * 0.141,
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#provinceToolBar',
                    title: '操作'
                }
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });


        table.on('tool(provinceTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') { //查看
                //do somehing
                layer.open({
                    title: '修改' + data.provincename,
                    type: 2,
                    area: ['50%', '40%'],
                    content: 'system/getEditorProvince?id=' + data.id,
                    skin: 'layui-layer-molv'
                });
            } else if (layEvent === 'del') { //删除
                layer.confirm('真的删除该用户吗？', function (index) {
                    loadIndex = layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/provinceDel',
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
                            layer.alert("出现异常！" + JSON.stringify(data));
                        }
                    });
                });
            }
        });


        cityTable = table.render({
            elem: '#dynamic-table-city', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'countyTable',
            url: 'system/getCityList',
            method: 'get',
            loading: true,
            height: 'full-115',//容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'id', title: 'ID', sort: true},
                {width: layui_tab_item_width * 0.4, field: 'provincename', title: '所属省'},
                {width: layui_tab_item_width * 0.383, field: 'cityname', title: '市名'},
                {
                    width: layui_tab_item_width * 0.141,
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#cityToolBar',
                    title: '操作'
                }
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });



        table.on('tool(cityTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') { //查看
                //do somehing
                layer.open({
                    title: '修改' + data.cityname,
                    type: 2,
                    area: ['50%', '40%'],
                    content: 'system/getEditorCity?id=' + data.id,
                    skin: 'layui-layer-molv'
                });
            } else if (layEvent === 'del') { //删除
                layer.confirm('真的删除该用户吗？', function (index) {
                    loadIndex = layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/cityDel',
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
                            layer.alert("出现异常！" + JSON.stringify(data));
                        }
                    });
                });
            }
        });




    });

    function addCounty() {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '添加县',
                type: 2,
                area: ['50%', '70%'],
                content: 'system/getCountyAdd',
                skin: 'layui-layer-molv'
            });
        });
    }

    function reflashCountyTable() {
        countyTable.reload({
            url: 'system/getCountyList'
        });
    }

    function addCity() {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '添加市',
                type: 2,
                area: ['50%', '70%'],
                content: 'system/getAddCity',
                skin: 'layui-layer-molv'
            });
        });
    }

    function reflashCityTable() {
        cityTable.reload({
            url: 'system/getCityList'
        });
    }


    function addProvince() {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '添加省',
                type: 2,
                area: ['50%', '70%'],
                content: 'system/getAddProvince',
                skin: 'layui-layer-molv'
            });
        });
    }

    function reflashProvicneTable() {
        provinceTable.reload({
            url: 'system/getProvinceList'
        });
    }





</script>

<script id="provinceToolBar" type="text/html">
    <shiro:hasPermission name="city:update">
        <a id="a" class="layui-btn layui-btn-mini" lay-event="detail">修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="city:delete">
        <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
    </shiro:hasPermission>
</script>

<script id="cityToolBar" type="text/html">
    <shiro:hasPermission name="city:update">
        <a id="a" class="layui-btn layui-btn-mini" lay-event="detail">修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="city:delete">
        <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
    </shiro:hasPermission>
</script>

<script id="countyToolBar" type="text/html">
    <shiro:hasPermission name="city:update">
        <a id="a" class="layui-btn layui-btn-mini" lay-event="detail">修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="city:delete">
        <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
    </shiro:hasPermission>
</script>

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
            height: 8px;
        }

        /*用户管理样式开始*/
        .usertools {
            height: 4.5%;
        }

        .usertools img {
            width: 23px;
            height: 23px;
            float: right;
            margin-right: 15px;
            cursor: pointer;
        }
        /*用户管理样式结束*/
    </style>
</head>
</html>
