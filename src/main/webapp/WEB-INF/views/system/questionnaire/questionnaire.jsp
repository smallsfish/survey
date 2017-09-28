<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<body style="background-color: #def7f0;">
<div class="questionnaire-top">
    <div onclick="window.parent.createTab({title:'新建问卷',isShowClose:true,url:'system/questionnaireAdd'})" title="新建"
         class="questionnaire-add"><img src="img/icon/icon-more2.png" alt="添加问卷"></div>
    <div class="questionnaire-search">
        <input type="search" name="qname" placeholder="请输入问卷名称">
        <div class="questionnaire-search-button"><img src="img/icon/icon-search.png"></div>
    </div>
</div>
<div class="questionnaire-content">
    <c:if test="${qms.size()==0}"><div class="showEmpty" style="text-align: center;font-size: 22px; color: #ff5040;width: 100%;">暂无数据</div></c:if>
    <c:forEach var="qm" items="${qms}">
        <div class="questionnaire-item">
            <div class="questionnaire-one">
                <ul>
                    <li>${qm.questionnairename}</li>
                    <li>C:<fmt:formatDate value="${qm.questionnairecreatetime}" pattern="yyyy-MM-dd  HH:mm:ss"/></li>
                    <li>B:<fmt:formatDate value="${qm.questionnairebegintime}" pattern="yyyy-MM-dd  HH:mm:ss"/></li>
                    <li>E:<fmt:formatDate value="${qm.questionnaireendtime}" pattern="yyyy-MM-dd  HH:mm:ss"/></li>
                    <li>状态：已完成</li>
                    <li>制作者：${qm.author}</li>
                </ul>
            </div>
            <div class="questionnaire-two">
                <div style="display: block;">
                    <button onclick="window.parent.createTab({title:'留守儿童问卷调查预览',isShowClose:true,url:'system/questionnairePreview'})"
                            class="layui-btn  layui-btn-radius">预览
                    </button>
                    <br><br>
                    <button onclick="window.parent.createTab({title:'留守儿童问卷调查',isShowClose:true,url:'system/questionnaireEditor'})"
                            class="layui-btn  layui-btn-radius">编辑
                    </button>
                    <br><br>
                    <button onclick="deleteQuestionnaire('${qm.id}')" class="layui-btn  layui-btn-radius">删除</button>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div class="questionnaire-page">
    <div id="test1" style="float:right; margin-right: 20px;height:0;"></div>
</div>
</body>
<script>
    var count=${count};
    layui.use(['laypage','layer'], function () {
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'test1', //注意，这里的 test1 是 ID，不用加 # 号
            count: count, //数据总数，从服务端得到
            limit: 8,
            limits: [8, 16, 32, 64],
            layout: ['prev', 'page', 'next', 'limit', 'skip', 'count'],
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                    alert("跳转到第" + obj.curr);
                }
            }
        });
        deleteQuestionnaire = function (id) {
            layer.confirm("确定删除当前问卷吗？此操作不可恢复！", function (index) {

                layer.close(index);
            })
        }
    });
</script>
</html>
