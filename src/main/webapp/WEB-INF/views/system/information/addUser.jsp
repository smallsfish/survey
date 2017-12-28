<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../base.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>test</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
    <style>
        form {
            width: 97%;
        }
    </style>
</head>

<body oncontextmenu="return false" onselect="return false">
<blockquote class="layui-elem-quote">注意：该用户的密码系统初始设置为123456，请让该用户到指定网页自行修改密码</blockquote>
<form class="layui-form" id="userForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>学校名称：</label>
        <div class="layui-input-block">
            <input type="text" name="account" lay-verify="required" placeholder="请输入学校名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>校长：</label>
        <div class="layui-input-block">
            <input type="text" name="headmaster" placeholder="请输入校长名称" lay-verify="required" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item user-address">
        <label class="layui-form-label"><span style="color: #f00;">*</span>地址：</label>
        <div class="layui-inline">
            <div class="layui-input-inline">
                <select name="province" lay-filter="province" lay-verify="required" lay-search>
                    <option value="">---请选择省份---</option>
                    <c:forEach var="p" items="${provinceList}">
                        <option value="${p.id}">${p.provincename}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <%--<div class="layui-inline">
            <div class="layui-input-inline">
                <select name="city" lay-verify="city" lay-search>
                    <option value="010">layer</option>
                    <option value="021">form</option>
                    <option value="0571" selected>layim</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline">
                <select name="city" lay-verify="county" lay-search>
                    <option value="010">layer</option>
                    <option value="021">form</option>
                    <option value="0571" selected>layim</option>
                </select>
            </div>
        </div>--%>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>详细地址：</label>
        <div class="layui-input-block">
            <input type="text" name="address" lay-verify="required" placeholder="请输入详细地址" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>留守儿童之家名称：</label>
        <div class="layui-input-block">
            <input type="text" name="playhousename" lay-verify="required" placeholder="请输入留守儿童之家名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: #f00;">*</span>图书数量：</label>
        <div class="layui-input-block">
            <input type="number" name="booknumber" lay-verify="required" placeholder="请输入图书数量" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <shiro:hasPermission name="user:add">
                <button class="layui-btn" lay-submit lay-filter="addUser" id="userSubmit">立即提交</button>
            </shiro:hasPermission>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
    <!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
</form>
</body>
<script>
    var layer = null;
    layui.use(['form', 'layer'], function () {
        var form = layui.form;
        layer = layui.layer;
        form.on('submit(addUser)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#userForm")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/addUser',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        $("#userForm")[0].reset();
                        window.parent.reflashUserOnSuccessTable();
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
        form.on('select(province)', function (data) {
            //user-address
            $.ajax({
                type: "GET",
                dataType: "json",
                url: 'system/getCityByProvinceId',
                data: {'provinceid':data.value},
                success: function (result) {
                    if (result.status == 0) {
                        var options="\n";
                        for(var i=0; i< result.data.length;i++){
                            options=options+"<option value=\""+result.data[i].id+"\">"+result.data[i].cityname+"</option>\n";
                        }
                        $('.user-address').append("<div class=\"layui-inline\">\n" +
                            "            <div class=\"layui-input-inline\">\n" +
                            "                <select name=\"city\" lay-filter=\"city\" lay-verify=\"required\" lay-search>\n" +
                            "                    <option value=\"\">---请选择城市---</option>\n" +
                            "                    \n" +
                             options +
                            "                    \n" +
                            "                </select>\n" +
                            "            </div>\n" +
                            "        </div>");
                        form.render();
                    }
                },
                error: function (data) {
                    alert("获取城市出现异常！");
                }
            });
        });

        form.on('select(city)', function (data) {
            //user-address
            $.ajax({
                type: "GET",
                dataType: "json",
                url: 'system/getCountyByCityId',
                data: {'cityid':data.value},
                success: function (result) {
                    if (result.status == 0) {
                        var options="\n";
                        for(var i=0; i< result.data.length;i++){
                            options=options+"<option value=\""+result.data[i].id+"\">"+result.data[i].countyname+"</option>\n";
                        }
                        $('.user-address').append("<div class=\"layui-inline\">\n" +
                            "            <div class=\"layui-input-inline\">\n" +
                            "                <select name=\"countyid\" lay-filter=\"county\" lay-verify=\"required\" lay-search>\n" +
                            "                    <option value=\"\">---请选择县---</option>\n" +
                            "                    \n" +
                            options +
                            "                    \n" +
                            "                </select>\n" +
                            "            </div>\n" +
                            "        </div>");
                        form.render();
                    }
                },
                error: function (data) {
                    alert("获取城市出现异常！");
                }
            });
        });
    });
</script>
</html>
