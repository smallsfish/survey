<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../base.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, province-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
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
<form class="layui-form" id="provinceForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <input type="hidden" name="id" value="${county.id}">
    <div class="layui-form-item" style="margin-top: 25px;">
        <label class="layui-form-label"><span style="color: #f00;">*</span>城市：</label>
        <div class="layui-input-block">
            <select name="cityid" lay-verify="required">
                <c:forEach var="c" items="${cityList}">
                    <option value="${c.id}" ${c.id == county.cityid ? 'selected':''}>${c.cityname}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item" style="margin-top: 25px;">
        <label class="layui-form-label"><span style="color: #f00;">*</span>名称：</label>
        <div class="layui-input-block">
            <input type="text" name="countyname" placeholder="请输入县级名称" lay-verify="required" autocomplete="off"
                   class="layui-input" value="${county.countyname}">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addProvince" id="provinceSubmit">立即修改</button>
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
        form.on('submit(addProvince)', function (data) {
            var loadIndex = layer.load();
            var fromData = new FormData($("#provinceForm")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/editorCounty',
                data: fromData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.status == 0) {
                        $("#provinceForm")[0].reset();
                        window.parent.reflashCountyTable();
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
