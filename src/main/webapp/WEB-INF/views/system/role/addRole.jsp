<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css">
    <script src="js/jquery.ztree.all.min.js"></script>
    <style>
        form {
            width: 97%;
        }
    </style>
</head>

<body oncontextmenu="return false" onselect="return false">
<form class="layui-form" id="roleForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div class="layui-form-item" style="margin-top: 25px;">
        <label class="layui-form-label"><span style="color: #f00;">*</span>角色名称：</label>
        <div class="layui-input-block">
            <input type="text" name="rolename" lay-verify="required" placeholder="请输入角色名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>描述：</label>
        <div class="layui-input-block">
            <input type="text" name="description" placeholder="请输入描述信息" lay-verify="required" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>是否可用</label>
        <div class="layui-input-block">
            <input type="radio" lay-verify="required" name="available" value="1" title="是" checked>
            <input type="radio" lay-verify="required" name="available" value="0" title="否">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">选择资源</label>
        <div style="width: 400px;float:left;">
            <ul id="resourceTree" class="ztree"></ul>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <shiro:hasPermission name="role:add">
                <button class="layui-btn" lay-submit lay-filter="addRole" id="roleSubmit">立即提交</button>
            </shiro:hasPermission>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
    <!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
</form>
</body>
<script>
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [
        <c:forEach var="r" items="${res}" varStatus="st">
        <c:if test="${st.index==res.size()-1}">
        {id:${r.id}, pId:${r.parentid}, name: '${r.name}', chkDisabled:${!r.available}, open:${!r.available}}
        </c:if>
        <c:if test="${st.index!=res.size()-1}">
        {id:${r.id}, pId:${r.parentid}, name: '${r.name}', chkDisabled:${!r.available}, open:${!r.available}},
        </c:if>
        </c:forEach>
    ];
    zTreeObj = $.fn.zTree.init($("#resourceTree"), setting, zNodes);


    var layer = null;
    layui.use(['form', 'layer'], function () {
        var form = layui.form;
        layer = layui.layer;
        form.on('submit(addRole)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#roleForm")[0]);
            var nodes = zTreeObj.getChangeCheckedNodes();
            var ids = "";
            for (var i = 0; i < nodes.length; i++) {
                if (i != nodes.length - 1) {
                    ids += nodes[i].id + ",";
                } else {
                    ids += nodes[i].id;
                }
            }
            fromData.append("ids", ids);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/addRole',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        $("#roleForm")[0].reset();
                        window.parent.reflashRoleOnSuccessTable();
                    }
                    layer.msg(result.msg);
                },
                error: function (data) {
                    layer.close(loadIndex);
                    alert("出现异常！");
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    });
</script>
</html>
