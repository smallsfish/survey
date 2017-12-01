<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../../base.jsp" %>
<body oncontextmenu="return false" onselect="return false">
<div class="layui-tab layui-tab-brief" style="margin: 0; height: 99.9%;" lay-filter="docDemoTabBrief">
    <ul class="layui-tab-title">
        <li class="layui-this">资讯列表</li>
        <li>资讯分类</li>
    </ul>
    <div class="layui-tab-content" style="height: calc( 100% - 60px );">
        <div class="layui-tab-item layui-show" style="height: 94%;">

            <div class="usertools">
                <div class="questionnaire-search" style="float:left; margin-top: -23px;">
                    <input type="search" style="border-right:1px gray solid;border-radius:5px;" name="qname" placeholder="请输入新闻标题">

                    <label for="newstype" style="margin-left: 40px;">资讯类型：</label>
                    <select id="newstype" style="margin-top: 20px; width: 80px; background-color: rgba(0,0,0,0); height:40px;border: 1px gray solid; border-radius: 5px;" name="newstype">
                        <option value="0">全部</option>
                        <option value="1">头条</option>
                        <option value="2">家庭</option>
                        <option value="3">谎言</option>
                        <option value="4">恐吓</option>
                    </select>
                    <label for="status" style="margin-left: 40px;">状态：</label>
                    <select id="status" style="margin-top: 20px; width: 80px; background-color: rgba(0,0,0,0); height:40px;border: 1px gray solid; border-radius: 5px;" name="newstype">
                        <option value="0">全部</option>
                        <option value="1">显示</option>
                        <option value="2">隐藏</option>
                    </select>
                    <div onclick="adminUserSearch()" class="news-search-button">搜索</div>
                </div>
                <img src="img/icon/icon-delete.png" data-type="checkAdminData" class="demoTable" alt="删除选中管理员" title="删除">
                <img onclick="reflashAdminTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
                <img onclick="addAdminUser()" src="img/icon/icon-more2.png" alt="添加管理员" title="添加">
            </div>
            <table id="dynamic-table-admin" lay-filter="adminTable"></table>
        </div>
        <div class="layui-tab-item" style="height: 93%">
            <div class="usertools">
                <img src="img/icon/icon-delete.png" data-type="checkUserData" class="userTableDel" alt="删除选中普通用户" title="删除">
                <img onclick="reflashUserTable()" src="img/icon/icon-reflash.png" alt="刷新" title="刷新">
                <img onclick="addAdminUser()" src="img/icon/icon-more2.png" alt="添加管理员" title="添加">
            </div>
            <table id="dynamic-table-user" lay-filter="userTable"></table>
        </div>
    </div>
</div>
</div>
</body>
<script>
    var layui_tab_item_width = $('.layui-tab-item').width();
    var table,adminTable,layer,loadIndex,userTable;
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
        adminTable=table.render({
            elem: '#dynamic-table-admin', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'adminTable',
            url:'system/getAdminUser',
            method:'get',
            loading:true,
            height: 'full-115', //容器高度
            cols: [[{checkbox: true, width: layui_tab_item_width * 0.02},
                {width: layui_tab_item_width * 0.04, field: 'aid', title: '序号',sort:true},
                {width: layui_tab_item_width * 0.07, field: 'headimage', templet:'#headimg', title: '缩略图'},
                {width: layui_tab_item_width * 0.22, field: 'account', title: '资讯标题'},
                {width: layui_tab_item_width * 0.12, field: 'email', title: '资讯来源'},
                {width: layui_tab_item_width * 0.11, field: 'role', title: '发布时间'},
                {width: layui_tab_item_width * 0.13, field: 'identifier', title: '资讯类型'},
                {width: layui_tab_item_width * 0.13, field: 'remarks', title: '状态'},
                {width: layui_tab_item_width * 0.141,fixed:'right', align:'center',toolbar:'#adminToolBar',title:'操作'}
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });
        var $ = layui.$, active = {
            checkAdminData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('adminTable')
                    ,data = checkStatus.data;
                if(data.length==0){
                    layer.msg("请选择要删除的信息！",{icon:2})
                }else{
                    layer.confirm("确定删除所选用户吗？",{icon:2,title:'系统提示'},function (index) {
                        $.each(checkStatus.data,function (index,obj) {
                            loadIndex=layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/adminUserDel',
                                data: {'id':obj.id},
                                success: function (result) {
                                    if(index==data.length-1){
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashAdminTable();
                                    }
                                },
                                error: function(data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！"+JSON.stringify(data));
                                }
                            });
                        });
                    });
                }
            },
            checkUserData:function () {
                var checkStatus = table.checkStatus('userTable')
                    ,data = checkStatus.data;
                if(data.length==0){
                    layer.msg("请选择要删除的用户！",{icon:2})
                }else{
                    layer.confirm("确定删除所选用户吗？",{icon:2,title:'系统提示'},function (index) {
                        $.each(checkStatus.data,function (index,obj) {
                            loadIndex=layer.load();
                            $.ajax({
                                type: "GET",
                                dataType: "json",
                                url: 'system/userDel',
                                data: {'id':obj.id},
                                success: function (result) {
                                    if(index==data.length-1){
                                        layer.close(loadIndex);
                                        layer.msg(result.msg);
                                        reflashUserTable();
                                    }
                                },
                                error: function(data) {
                                    layer.close(loadIndex);
                                    layer.alert("出现异常！"+JSON.stringify(data));
                                }
                            });
                        });
                    });
                }
            }
        };
        $('.usertools .demoTable').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.usertools .userTableDel').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        table.on('tool(adminTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'detail'){ //查看
                //do somehing
                layer.open({
                    title:'修改'+data.account+'管理员',
                    type:2,
                    area: ['50%', '70%'],
                    content:'system/getEditorAdminUser?id='+data.id,
                    skin:'layui-layer-molv'
                });
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除该用户吗？', function(index){
                    loadIndex=layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/adminUserDel',
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
                            layer.alert("出现异常！"+JSON.stringify(data));
                        }
                    });
                });
            }
        });



        //        普通用户动态表格
        userTable=table.render({
            elem: '#dynamic-table-user', //指定原始表格元素选择器（推荐id选择器）
            page: true,
            id: 'userTable',
            url:'system/getUser',
            method:'get',
            loading:true,
            height: 'full-115', //容器高度
            cols:[[{width: layui_tab_item_width * 0.02,checkbox: true},
                {width: layui_tab_item_width * 0.04,title:'序号',field:'aid'},
                {width: layui_tab_item_width * 0.04,title:'ID',field:'id'},
                {width: layui_tab_item_width * 0.07,title:'学校名称',field:'schoolname'},
                {width: layui_tab_item_width * 0.07,title:'校长',field:'headmaster'},
                {width: layui_tab_item_width * 0.13,title:'地址',field:'address'},
                {width: layui_tab_item_width * 0.1,title:'留守儿童之家名称',field:'playhousename'},
                {width: layui_tab_item_width * 0.06,title:'操作人',field:'operationuser'},
                {width: layui_tab_item_width * 0.1,title:'状态',field:'status'},
                {width: layui_tab_item_width * 0.21,title:'备注',field:'remarks'},
                {width: layui_tab_item_width * 0.14,fixed:'right', align:'center',toolbar:'#userToolBar',title:'操作'}
            ]],
            size: 'lg',
            limits: [30, 60, 90, 150, 300],
            limit: 30 //默认采用30
        });

        table.on('tool(userTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'detail'){ //查看
                //do somehing
                layer.confirm('该用户通过审核？', function(index){
                    loadIndex=layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/userAuditing',
                        data: {'id':data.id},
                        success: function (result) {
                            layer.close(loadIndex);
                            layer.msg(result.msg);
                            reflashUserTable();
                        },
                        error: function(data) {
                            layer.close(loadIndex);
                            layer.alert("出现异常！");
                        }
                    });
                });
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除该用户吗？', function(index){
                    loadIndex=layer.load();
                    layer.close(index);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: 'system/userDel',
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
                            layer.alert("出现异常！"+JSON.stringify(data));
                        }
                    });
                });
            }
        });



    });
    function addAdminUser() {
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                title:'新增资讯',
                type:2,
                area: ['100%', '100%'],
                content:'system/getAddNews',
                skin:'layui-layer-molv'
            });
        });
    }
    function reflashAdminTable(){
        adminTable.reload({
            url:'system/getAdminUser'
        });
    }

    function reflashUserTable(){
        userTable.reload({
            url:'system/getUser'
        });
    }
    function adminUserSearch() {
        var account=$(":input[name='qname']").val();
        if(account===""){
            layer.msg("请输入账号！",{icon:2,time:3000});
        }else{
            adminTable.reload({
                url:'system/adminUserSearch?account='+account
            });
        }
    }

    function userSearch() {
        var account=$(":input[name='uname']").val();
        if(account===""){
            layer.msg("请输入普通用户账号！",{icon:2,time:3000});
        }else{
            userTable.reload({
                url:'system/userSearch?account='+account
            });
        }
    }
</script>
<script id="headimg" type="text/html">
    <img src="uploadimage/{{d.headimage}}" style="width: 40px;height: 40px;">
</script>
<script id="adminToolBar" type="text/html">
    <a id="a" class="layui-btn layui-btn-mini" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>
<script id="userToolBar" type="text/html">
    <a id="u" class="layui-btn layui-btn-mini" lay-event="detail">审核</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
        option{
            font-size: 16px;
        }
        /*用户管理样式结束*/
        .news-search-button{
            background-color: #ff5040;
            float: right;
            width: 60px;
            height: 40px;
            border-radius: 5px;
            margin-top: 20px;
            margin-left: 60px;
            border: 1px gray solid;
            font-size: 17px;
            color:#fff;
            text-align: center;
            line-height: 40px;
            cursor: pointer;
        }
    </style>
</head>
</html>