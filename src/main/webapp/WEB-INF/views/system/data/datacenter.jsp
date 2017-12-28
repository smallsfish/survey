<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
</head>

<body oncontextmenu="return false" onselect="return false">
<div class="questionnaire-top">
    <div class="questionnaire-search">
        <input type="search" name="qname" placeholder="请输入问卷名称">
        <div onclick="questionDataSearch()" class="questionnaire-search-button"><img src="img/icon/icon-search.png">
        </div>
    </div>
</div>
<div class="questionnaire-content">
    <c:forEach var="q" items="${qs}">
        <div class="questionnaire-item">
            <div class="questionnaire-one">
                <ul>
                    <li>${q.name}</li>
                    <li>开始时间:${q.b}</li>
                    <li>结束时间:${q.e}</li>
                    <li>参与学校：${q.schoolNumber}</li>
                    <li>参与城市：${q.cityNumber}</li>
                    <li>参与人数：${q.studentNumber}</li>
                </ul>
            </div>
            <div class="questionnaire-two">
                <div style="display: block;">
                    <shiro:hasPermission name="data:view">
                        <button onclick="window.parent.createTab({title:'${q.name}数据分析',isShowClose:true,url:'${q.url}'})"
                                class="layui-btn  layui-btn-radius">${q.button}
                        </button>
                        <br><br>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="data:view">
                        <a href="system/dataExcel?id=${q.id}"><button onclick="javascript:;"
                                class="layui-btn  layui-btn-radius">数据导出
                        </button></a>
                    </shiro:hasPermission>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div class="questionnaire-page">
    <div id="test1" style="margin-left: 20px; height:0;"></div>
</div>
</body>
<script>
    var count =${count};
    var loadIndex = null;
    var laypage = null, layer = null;
    layui.use(['laypage', 'layer'], function () {
        laypage = layui.laypage;
        layer = layui.layer;
        setDataPageValue('system/dataList', null);
    });

    function clearItem() {
        $(".questionnaire-item").remove();
    }

    function setDataPageValue(url, name) {
        //执行一个laypage实例
        laypage.render({
            elem: 'test1', //注意，这里的 test1 是 ID，不用加 # 号
            count: count, //数据总数，从服务端得到
            limit: 12,
            limits: [12, 24, 36, 48, 60],
            layout: ['prev', 'page', 'next', 'limit', 'skip', 'count'],
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                    loadIndex = layer.load();
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: url,
                        data: {'page': obj.curr, 'limit': obj.limit, 'name': name},
                        success: function (result) {
                            layer.msg(result.msg);
                            clearItem();
                            $(result.data).each(function (index, q) {
                                $(".questionnaire-content").append("<div class=\"questionnaire-item\">\n" +
                                    "        <div class=\"questionnaire-one\">\n" +
                                    "            <ul>\n" +
                                    "                <li>" + q.name + "</li>\n" +
                                    "                <li>开始时间:" + q.b + "</li>\n" +
                                    "                <li>结束时间:" + q.e + "</li>\n" +
                                    "                <li>参与学校：" + q.schoolNumber + "</li>\n" +
                                    "                <li>参与城市：" + q.cityNumber + "</li>\n" +
                                    "                <li>参与人数：" + q.studentNumber + "</li>\n" +
                                    "            </ul>\n" +
                                    "        </div>\n" +
                                    "        <div class=\"questionnaire-two\">\n<div style=\"display: block;\">" +
                                    "            <button onclick=\"window.parent.createTab({title:'" + q.name + "数据分析',isShowClose:true,url:'" + q.url + "'})\"\n" +
                                    "                    class=\"layui-btn  layui-btn-radius\">" + q.button + "\n" +
                                    "            </button>\n" +
                                    "<shiro:hasPermission name="data:view">" +
                                    "<br><br>\n<a href=\"system/dataExcel?id="+q.id+"\"><button onclick=\"javascript:;\"" +
                                    "   class=\"layui-btn  layui-btn-radius\">数据导出" +
                                    "</button></a>" +
                                    " </shiro:hasPermission></div>" +
                                    "        </div>\n" +
                                    "    </div>\n");
                            });
                            layer.close(loadIndex);
                        },
                        error: function () {
                            layer.close(loadIndex);
                            layer.msg("搜索失败", {icon: 2});
                        }
                    });
                }
            }
        });
    }

    function questionDataSearch() {
        var dataName = $(":input[name='qname']").val();
        dataName = encodeURI(encodeURI(dataName));
        if (dataName !== "") {
            loadIndex = layer.load();
            $.ajax({
                type: "GET",
                dataType: "json",
                url: 'system/dataSearch',
                data: {'name': dataName},
                success: function (result) {
                    layer.close(loadIndex);
                    layer.msg(result.msg);
                    clearItem();
                    $(result.data).each(function (index, q) {
                        $(".questionnaire-content").append("<div class=\"questionnaire-item\">\n" +
                            "        <div class=\"questionnaire-one\">\n" +
                            "            <ul>\n" +
                            "                <li>" + q.name + "</li>\n" +
                            "                <li>开始时间:" + q.b + "</li>\n" +
                            "                <li>结束时间:" + q.e + "</li>\n" +
                            "                <li>参与学校：" + q.schoolNumber + "</li>\n" +
                            "                <li>参与城市：" + q.cityNumber + "</li>\n" +
                            "                <li>参与人数：" + q.studentNumber + "</li>\n" +
                            "            </ul>\n" +
                            "        </div>\n" +
                            "        <div class=\"questionnaire-two\">\n <div style=\"display: block;\">" +
                            "            <button onclick=\"window.parent.createTab({title:'" + q.name + "数据分析',isShowClose:true,url:'" + q.url + "'})\"\n" +
                            "                    class=\"layui-btn  layui-btn-radius\">" + q.button + "\n" +
                            "            </button>\n" +
                            "<shiro:hasPermission name="data:view">" +
                            "<br><br>\\n<a href=\\\"system/dataExcel?id=\"+q.id+\"\\\"><button onclick=\"javascript:;\"" +
                            "   class=\"layui-btn  layui-btn-radius\">数据导出" +
                            "</button></a>" +
                            " </shiro:hasPermission></div>" +
                            "        </div>\n" +
                            "    </div>\n");
                    });
                    count = result.count;
                    setDataPageValue('system/dataSearch', dataName);
                },
                error: function () {
                    layer.close(loadIndex);
                    layer.msg("搜索失败", {icon: 2});
                }
            });
        } else {
            layer.msg("请输入问卷名称", {icon: 2});
        }
    }
</script>
</html>
