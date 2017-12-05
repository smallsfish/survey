<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../../base.jsp" %>
<body oncontextmenu="return false" onselect="return false">
<div class="layui-tab layui-tab-brief" style="margin: 0; height: 99.9%;" lay-filter="docDemoTabBrief">
    <ul class="layui-tab-title">
        <li class="layui-this">图片列表</li>
        <li>图片分类</li>
    </ul>
    <div class="layui-tab-content" style="height: calc( 100% - 60px );">
        <div class="layui-tab-item layui-show" style="height: 94%;">

            <div class="picturestypetools">
                <div class="questionnaire-search" style="float:left; margin-top: -23px;">
                    <input type="search" style="border-right:1px gray solid;border-radius:5px;" name="qname"
                           placeholder="请输入图片标题">

                    <label for="picturestype" style="margin-left: 40px;">图片类型：</label>
                    <select id="picturestype"
                            style="margin-top: 20px; width: 80px; background-color: rgba(0,0,0,0); height:40px;border: 1px gray solid; border-radius: 5px;"
                            name="picturestype">
                        <option value="0">全部</option>
                        <c:forEach items="${pls}" var="type">
                            <option value="${type.id}">${type.name}</option>
                        </c:forEach>
                    </select>
                    <label for="status" style="margin-left: 40px;">状态：</label>
                    <select id="status"
                            style="margin-top: 20px; width: 80px; background-color: rgba(0,0,0,0); height:40px;border: 1px gray solid; border-radius: 5px;"
                            name="status">
                        <option value="2">全部</option>
                        <option value="1">显示</option>
                        <option value="0">隐藏</option>
                    </select>
                    <div onclick="picturesSearch()" class="pictures-search-button">搜索</div>
                </div>
                <img src="img/icon/icon-delete.png" data-type="checkPicturesData" class="demoTable" alt="删除选中管理员"
                     title="删除">
                <img onclick="reflashPicturesTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
                <img onclick="addPictures()" src="img/icon/icon-more2.png" alt="添加图片" title="添加">
            </div>
            <table id="dynamic-table-pictures" lay-filter="picturesTable"></table>
        </div>
        <div class="layui-tab-item" style="height: 93%">
            <div class="picturestypetools">
                <img src="img/icon/icon-delete.png" data-type="checkPicturestypeData" class="picturestypeTableDel"
                     alt="删除选中类别"
                     title="删除">
                <img onclick="reflashPicturestypeTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
                <img onclick="addPicturesType()" src="img/icon/icon-more2.png" alt="添加图片类别" title="添加">
            </div>
            <table id="dynamic-table-picturestype" lay-filter="picturestypeTable"></table>
        </div>
    </div>
</div>
</div>
</body>
<script>
    var layui_tab_item_width = $('.layui-tab-item').width();
    var table, picturesTable, layer, loadIndex, picturestypeTable;
    layui.use(['layer', 'table'], function () {
        layer = layui.layer;
    });
    layui.use(['element', 'table'], function () {
        var element = layui.element;
        table = layui.table;
        //一些事件监听---tab选项卡
        element.on('tab(demo)', function (data) {
        });
//        管理员动态表格
        picturesTable = table.render({
            elem: '#dynamic-table-pictures', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'picturesTable',
            url: 'system/getPicturesList',
            method: 'get',
            loading: true,
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'aid', title: '序号', sort: true},
                {width: layui_tab_item_width * 0.07, field: 'pictureurl', templet: '#headimg', title: '缩略图'},
                {width: layui_tab_item_width * 0.26, field: 'picturetitle', title: '图片标题'},
                {width: layui_tab_item_width * 0.23, field: 'picturetype', title: '图片类型'},
                {width: layui_tab_item_width * 0.091, field: 'operator', title: '操作人'},
                {width: layui_tab_item_width * 0.13, field: 'status', title: '状态'},
                {
                    width: layui_tab_item_width * 0.141,
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#picturesToolBar',
                    title: '操作'
                }
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });
        var $ = layui.$, active = {
            checkPicturesData: function () { //获取选中数据
                var checkStatus = table.checkStatus('picturesTable')
                    , data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择要删除的信息！", {icon: 2})
                } else {
                    layer.confirm("确定删除所选图片吗？", {icon: 2, title: '系统提示'}, function (index) {
                        $.each(checkStatus.data, function (index, obj) {
                            loadIndex = layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/picturesDel',
                                data: {'id': obj.id},
                                success: function (result) {
                                    if (index == data.length - 1) {
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashPicturesTable();
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
            checkPicturestypeData: function () {
                var checkStatus = table.checkStatus('picturestypeTable')
                    , data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择要删除的用户！", {icon: 2})
                } else {
                    layer.confirm("确定删除所选图片吗？", {icon: 2, title: '系统提示'}, function (index) {
                        $.each(checkStatus.data, function (index, obj) {
                            loadIndex = layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/picturestypeDel',
                                data: {'id': obj.id},
                                success: function (result) {
                                    if (index == data.length - 1) {
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashPicturestypeTable();
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
        $('.picturestypetools .demoTable').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.picturestypetools .picturestypeTableDel').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        table.on('tool(picturesTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') { //查看
                //do somehing
                layer.open({
                    title: '修改' + data.picturetitle + '图片',
                    type: 2,
                    area: ['60%', '50%'],
                    content: 'system/getEditorPictures?id=' + data.id,
                    skin: 'layui-layer-molv'
                });
            } else if (layEvent === 'del') { //删除
                layer.confirm('真的删除该图片吗？', function (index) {
                    loadIndex = layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/picturesDel',
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
        //        图片类型动态表格
        picturestypeTable = table.render({
            elem: '#dynamic-table-picturestype', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'picturestypeTable',
            url: 'system/getPicturesTypeList',
            method: 'get',
            loading: true,
            height: 'full-115', //容器高度
            cols: [[{width: layui_tab_item_width * 0.02, checkbox: true},
                {width: layui_tab_item_width * 0.04, title: '序号', field: 'aid'},
                {width: layui_tab_item_width * 0.825, title: '名称', field: 'name'},
                {
                    width: layui_tab_item_width * 0.1,
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#picturestypeToolBar',
                    title: '操作'
                }
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });

        table.on('tool(picturestypeTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') {
                layer.open({
                    title: '修改' + data.name + '图片类型',
                    type: 2,
                    area: ['50%', '40%'],
                    content: 'system/getEditorNewType?id=' + data.id,
                    skin: 'layui-layer-molv'
                });
            } else if (layEvent === 'del') { //删除
                layer.confirm('该操作会同步删除该类型下的所有图片！确认继续吗？', function (index) {
                    loadIndex = layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/picturestypeDel',
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

    function addPictures() {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '新增图片',
                type: 2,
                area: ['60%', '50%'],
                content: 'system/getAddPictures',
                skin: 'layui-layer-molv'
            });
        });
    }

    function addPicturesType() {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '新增图片类型',
                type: 2,
                area: ['50%', '40%'],
                content: 'system/getAddPicturesType',
                skin: 'layui-layer-molv'
            });
        });
    }

    function reflashPicturesTable() {
        picturesTable.reload({
            url: 'system/getPicturesList'
        });
    }

    function reflashPicturestypeTable() {
        picturestypeTable.reload({
            url: 'system/getPicturesTypeList'
        });
    }

    function picturesSearch() {
        var picturestitle = $(":input[name='qname']").val();
        var status = $("#status").val();
        var picturestype = $("#picturestype").val();
        picturesTable.reload({
            url: 'system/pictureSearch?picturestitle=' + picturestitle + '&status=' + status + '&picturestype=' + picturestype
        });
    }
</script>
<script id="headimg" type="text/html">
    <img src="uploadimage/{{d.pictureurl}}" style="height: 40px;">
</script>
<script id="picturesToolBar" type="text/html">
    <a id="a" class="layui-btn layui-btn-mini" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>
<script id="picturestypeToolBar" type="text/html">
    <a id="a" class="layui-btn layui-btn-mini" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>


<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, picturestype-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
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
        .picturestypetools {
            height: 4.5%;
        }

        .picturestypetools img {
            width: 23px;
            height: 23px;
            float: right;
            margin-right: 15px;
            cursor: pointer;
        }

        option {
            font-size: 16px;
        }

        /*用户管理样式结束*/
        .pictures-search-button {
            background-color: #ff5040;
            float: right;
            width: 60px;
            height: 40px;
            border-radius: 5px;
            margin-top: 20px;
            margin-left: 60px;
            border: 1px gray solid;
            font-size: 17px;
            color: #fff;
            text-align: center;
            line-height: 40px;
            cursor: pointer;
        }
    </style>
</head>
</html>
