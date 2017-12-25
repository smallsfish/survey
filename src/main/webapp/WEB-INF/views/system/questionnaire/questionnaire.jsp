<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../base.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>问卷中心</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
</head>
<body>
<div class="questionnaire-top">
    <shiro:hasPermission name="questionnaire:add">
        <div onclick="window.parent.createTab({title:'新建问卷',isShowClose:true,url:'system/questionnaireAdd'})" title="新建"
             class="questionnaire-add">添加问卷
        </div>
    </shiro:hasPermission>
    <div class="questionnaire-search">
        <input type="search" name="qname" placeholder="请输入问卷名称">
        <div onclick="questionnaireSearch()" class="questionnaire-search-button"><img src="img/icon/icon-search.png">
        </div>
    </div>
</div>
<div class="questionnaire-content">
    <c:if test="${qms.size()==0}">
        <div class="showEmpty" style="text-align: center;font-size: 22px; color: #ff5040;width: 100%;">暂无数据</div>
    </c:if>
    <c:forEach var="qm" items="${qms}">
        <div id="${qm.id}" class="questionnaire-item">
            <div class="questionnaire-one">
                <ul>
                    <li>${qm.questionnairename}</li>
                    <li style="margin-top: 13px;">创建时间:<fmt:formatDate value="${qm.questionnairecreatetime}"
                                                                       pattern="yyyy-MM-dd  HH:mm:ss"/></li>
                    <li>开始时间:<fmt:formatDate value="${qm.questionnairebegintime}" pattern="yyyy-MM-dd  HH:mm:ss"/><c:if test="${qm.questionnairebegintime==null || qm.questionnairebegintime.equals(\"\")}">未设</c:if></li>
                    <li>结束时间:<fmt:formatDate value="${qm.questionnaireendtime}" pattern="yyyy-MM-dd  HH:mm:ss"/><c:if test="${qm.questionnaireendtime==null || qm.questionnaireendtime.equals(\"\")}">未设</c:if></li>
                    <li>题目数量：${qm.questions}</li>
                    <li>制作者：${qm.author}</li>
                </ul>
            </div>
            <div class="questionnaire-two">
                <div style="display: block;">
                    <shiro:hasPermission name="questionnaire:view">
                        <button onclick="window.parent.createTab({title:'${qm.questionnairename}',isShowClose:true,url:'system/displayQuestionnaire?id=${qm.id}'})"
                                class="layui-btn  layui-btn-radius">预览
                        </button>
                        <br><br>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="questionnaire:share">
                        <button onclick="showDisplayURL('<%=baseUrl%>display/displayQuestionnaire?id=${qm.id}');"
                                class="layui-btn  layui-btn-radius">链接
                        </button>
                        <br><br>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="questionnaire:update">
                        <button onclick="window.parent.createTab({title:'${qm.questionnairename}编辑',isShowClose:true,url:'system/questionnaireEditor?id=${qm.id}'})"
                                class="layui-btn  layui-btn-radius">编辑
                        </button>
                        <br><br>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="questionnaire:delete">
                        <button onclick="deleteQuestionnaire('${qm.id}')" class="layui-btn  layui-btn-radius">删除
                        </button>
                    </shiro:hasPermission>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div class="questionnaire-page">
    <div id="test1" style="margin-left: 20px;height:0;"></div>
</div>
</body>
<script>
    /**
     * 时间对象的格式化;
     */
    Date.prototype.format = function (format) {
        /*
         * eg:format="YYYY-MM-dd hh:mm:ss";
         */
        var o =
            {
                "M+": this.getMonth() + 1, // month
                "d+": this.getDate(), // day
                "h+": this.getHours(), // hour
                "m+": this.getMinutes(), // minute
                "s+": this.getSeconds(), // second
                "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
                "S": this.getMilliseconds() // millisecond
            }
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }
</script>
<script>
    var count =${count};
    var laypage = null;
    var layer;
    layui.use(['laypage', 'layer'], function () {
        layer = layui.layer;
        laypage = layui.laypage;
        setPageValue('system/questionnairesList', null);
        var loadIndex = null;
        deleteQuestionnaire = function (id) {
            layer.confirm("确定删除当前问卷吗？此操作不可恢复！", function (index) {
                loadIndex = layer.load();
                layer.close(index);
                $.ajax({
                    type: "GET",
                    dataType: "json",
                    url: 'system/questionnaireDel',
                    data: {'id': id},
                    success: function (result) {
                        layer.close(loadIndex);
                        if (result.status == 0) {
                            $("#" + id).remove();
                            if ($(".questionnaire-item").length == 0) {
                                $(".questionnaire-content").append("<div class=\"showEmpty\" style=\"text-align: center;font-size: 22px; color: #ff5040;width: 100%;\">暂无数据</div>");
                            }

                        }
                        layer.msg(result.msg);
                    },
                    error: function (data) {
                        layer.close(loadIndex);
                        alert("出现异常！");
                    }
                });
            });
        }
    });

    function setPageValue(url, name) {
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
                            if (result.status == 0) {
                                $(".questionnaire-item").remove();
                                $(result.data).each(function (index, item) {
                                    var cDate = new Date(item.questionnairecreatetime).format("yyyy-MM-dd hh:mm:ss");
                                    var bDate = new Date(item.questionnairebegintime).format("yyyy-MM-dd hh:mm:ss");
                                    var eDate = new Date(item.questionnaireendtime).format("yyyy-MM-dd hh:mm:ss");
                                    if(item.questionnairebegintime==null || item.questionnairebegintime===""){
                                        bDate="未设";
                                    }
                                    if(item.questionnaireendtime==null || item.questionnaireendtime===""){
                                        eDate="未设";
                                    }
                                    $(".questionnaire-content").append("<div id=\"" + item.id + "\" class=\"questionnaire-item\">" +
                                        "            <div class=\"questionnaire-one\">" +
                                        "                <ul>\n" +
                                        "                    <li>" + item.questionnairename + "</li>" +
                                        "                    <li>创建时间:" + cDate +
                                        "                    <li>开始时间:" + bDate +
                                        "                    <li>结束时间:" + eDate + "</li>" +
                                        "                    <li>题目数量：" + item.questions + "</li>" +
                                        "                    <li>制作者：" + item.author + "</li>" +
                                        "                </ul>" +
                                        "            </div>" +
                                        "            <div class=\"questionnaire-two\">" +
                                        "                <div style=\"display: block;\">"<shiro:hasPermission name="questionnaire:view"> +
                                        "                    <button onclick=\"window.parent.createTab({title:'" + item.questionnairename + "',isShowClose:true,url:'display/displayQuestionnaire?id=" + item.id + "'})\"" +
                                        "                            class=\"layui-btn  layui-btn-radius\">预览" +
                                        "                    </button>" +
                                        "                    <br><br>"</shiro:hasPermission><shiro:hasPermission name="questionnaire:share"> +
                                        "                    <button onclick=\"showDisplayURL('<%=baseUrl%>display/displayQuestionnaire?id=" + item.id + "');\"" +
                                        "                            class=\"layui-btn  layui-btn-radius\">链接" +
                                        "                    </button>" +
                                        "                    <br><br>"</shiro:hasPermission> <shiro:hasPermission name="questionnaire:update"> +
                                        "                    <button onclick=\"window.parent.createTab({title:'" + item.questionnairename + "编辑',isShowClose:true,url:'system/questionnaireEditor?id=" + item.id + "'})\"" +
                                        "                            class=\"layui-btn  layui-btn-radius\">编辑" +
                                        "                    </button>" +
                                        "                    <br><br>"</shiro:hasPermission><shiro:hasPermission name="questionnaire:delete"> +
                                        "                    <button onclick=\"deleteQuestionnaire('" + item.id + "')\" class=\"layui-btn  layui-btn-radius\">删除</button>" </shiro:hasPermission> +
                                        "                </div>" +
                                        "            </div>" +
                                        "        </div>");
                                });
                            }
                            layer.close(loadIndex);
                        },
                        error: function (data) {
                            layer.close(loadIndex);
                            alert("出现异常！");
                        }
                    });
                }
            }
        });
    }

    function showDisplayURL(s) {
        $("#url").text(s);
        //layer.alert(s);
        layer.open({
                type: 1,
                content: $('#url'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                btn: ['确认']

            },
            function (index) {
                layer.close(index);
                $("#url").hide();
            });
    }
</script>
<script>
    function questionnaireSearch() {
        var questionnaireName = $(":input[name='qname']").val();
        questionnaireName=encodeURI(encodeURI(questionnaireName));
        if (questionnaireName !== "") {
            loadIndex = layer.load();
            $.ajax({
                type: "GET",
                dataType: "json",
                url: 'system/questionnaireSearch',
                data: {'name': questionnaireName},
                success: function (result) {
                    loadIndex = layer.load();
                    if (result.status == 0) {
                        $(".questionnaire-item").remove();
                        $(result.data).each(function (index, item) {
                            var cDate = new Date(item.questionnairecreatetime).format("yyyy-MM-dd hh:mm:ss");
                            var bDate = new Date(item.questionnairebegintime).format("yyyy-MM-dd hh:mm:ss");
                            var eDate = new Date(item.questionnaireendtime).format("yyyy-MM-dd hh:mm:ss");
                            if(item.questionnairebegintime==null || item.questionnairebegintime===""){
                                bDate="未设";
                            }
                            if(item.questionnaireendtime==null || item.questionnaireendtime===""){
                                eDate="未设";
                            }
                            $(".questionnaire-content").append("<div id=\"" + item.id + "\" class=\"questionnaire-item\">" +
                                "            <div class=\"questionnaire-one\">" +
                                "                <ul>\n" +
                                "                    <li>" + item.questionnairename + "</li>" +
                                "                    <li>创建时间:" + cDate +
                                "                    <li>开始时间:" + bDate +
                                "                    <li>结束时间:" + eDate + "</li>" +
                                "                    <li>题目数量：" + item.questions + "</li>" +
                                "                    <li>制作者：" + item.author + "</li>" +
                                "                </ul>" +
                                "            </div>" +
                                "            <div class=\"questionnaire-two\">" +
                                "                <div style=\"display: block;\">"<shiro:hasPermission name="questionnaire:view"> +
                                "                    <button onclick=\"window.parent.createTab({title:'" + item.questionnairename + "',isShowClose:true,url:'system/displayQuestionnaire?id=" + item.id + "'})\"" +
                                "                            class=\"layui-btn  layui-btn-radius\">预览" +
                                "                    </button>" +
                                "                    <br><br>"</shiro:hasPermission> <shiro:hasPermission name="questionnaire:share">+
                                "                    <button onclick=\"showDisplayURL('<%=baseUrl%>display/displayQuestionnaire?id=" + item.id + "');\"" +
                                "                            class=\"layui-btn  layui-btn-radius\">链接" +
                                "                   </button>" +
                                "                    <br><br>"</shiro:hasPermission> <shiro:hasPermission name="questionnaire:update">+
                                "                    <button onclick=\"window.parent.createTab({title:'" + item.questionnairename + "编辑',isShowClose:true,url:'system/questionnaireEditor?id=" + item.id + "'})\"" +
                                "                            class=\"layui-btn  layui-btn-radius\">编辑" +
                                "                    </button>" +
                                "                    <br><br>"</shiro:hasPermission> <shiro:hasPermission name="questionnaire:delete">+
                                "                    <button onclick=\"deleteQuestionnaire('" + item.id + "')\" class=\"layui-btn  layui-btn-radius\">删除</button>"</shiro:hasPermission> +
                                "                </div>" +
                                "            </div>" +
                                "        </div>");
                        });
                        count = result.count;
                        setPageValue('system/questionnaireSearch', questionnaireName);
                    }
                    layer.msg(result.msg);
                    layer.close(loadIndex);
                },
                error: function (data) {
                    layer.close(loadIndex);
                    alert("出现异常！");
                }
            });
        } else {
            layer.msg("请输入问卷名称！", {icon: 2, time: 3000});
        }
    }
</script>
<div id="url" contenteditable="true" style="padding: 30px 20px; display: none;"></div>
</html>
