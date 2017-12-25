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
    <title>问卷修改</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
    <script>
        var questionnaire = ${displayQuestionnaireModel.questionnaire.questionnairejson};
    </script>
    <script src="js/questionnaire.js"></script>
    <script>
        var layer = null;
        layui.use('layer', function () {
            layer = layui.layer;
        });
    </script>
</head>
<body>
<div class="questionnaireadd">
    <div class="questionnaireadd-base-info">
        <blockquote class="layui-elem-quote">问卷基础信息</blockquote>
        <ul>
            <li><span><i style="color:red;">*</i>问卷名称：</span><input onkeyup="questionnaireNameChange()" type="text"
                                                                    required name="qname"
                                                                    value="${displayQuestionnaireModel.questionnaire.questionnairename}">
            </li>
            <li><span>问候语：</span><input onkeyup="questionnaireCompChange()" type="text" name="comp"
                                        value="${displayQuestionnaireModel.questionnaire.questionnairecomp}"></li>
            <li><span>问卷出处：</span><input onkeyup="questionnaireWhereChange()" type="text" name="qwhere"
                                         value="${displayQuestionnaireModel.questionnaire.questionnairefrom}"></li>
            <li><span>问卷有效时间：</span><input onblur="questionnaireDateRangeChange()" name="daterange" type="text"
                                           placeholder="yyyy-MM-dd HH:mm:ss ~ yyyy-MM-dd HH:mm:ss" id="dateselect"
                                           value='<c:if test="${displayQuestionnaireModel.questionnaire.questionnairebegintime!=null}"><fmt:formatDate value="${displayQuestionnaireModel.questionnaire.questionnairebegintime}" pattern="yyyy-MM-dd  HH:mm:ss"/> ~ <fmt:formatDate value="${displayQuestionnaireModel.questionnaire.questionnaireendtime}" pattern="yyyy-MM-dd  HH:mm:ss"/></c:if>'>
            </li>
            <li><span>问卷说明：</span><textarea onkeyup="questionnaireExplainChange()" name="explain" cols="14"
                                            rows="5">${displayQuestionnaireModel.questionnaire.questionnaireexplain}</textarea>
            </li>
        </ul>
    </div>
    <div class="questionnaireadd-base-qs">
        <blockquote class="layui-elem-quote">题型选择</blockquote>
        <ul class="layui-form">
            <li onclick="questionnaireAddTypes()"><img src="img/icon-white/icon-menu.png">添加分类</li>
            <li onclick="questionnaireAddSingleSelection()"><img src="img/icon-white/icon-single-selection.png">单选题</li>
            <li onclick="questionnaireAddMultiple()"><img src="img/icon-white/icon-multiple.png">多选题</li>
            <%--<li><img src="img/icon-white/icon-drop-down.png">下拉选择题</li>
            <li><img src="img/icon-white/icon-pack.png">单行填空题</li>
            <li><img src="img/icon-white/icon-packs.png">多行填空题</li>
            <li><img src="img/icon-white/icon-picture.png">图片单选题</li>
            <li><img src="img/icon-white/icon-pictures.png">图片多选题</li>
            <li><img src="img/icon-white/icon-describe.png">描述说明题</li>--%>
        </ul>
    </div>
    <div class="questionnaireadd-show">
        <div class="questionnaireadd-main">
            <div class="questionnaireadd-main-title">${displayQuestionnaireModel.questionnaire.questionnairename}</div>
            <div class="questionnaireadd-main-comp">${displayQuestionnaireModel.questionnaire.questionnairecomp}</div>
            <div class="questionnaireadd-main-explain">${displayQuestionnaireModel.questionnaire.questionnaireexplain}</div>
            <div class="questionnaireadd-main-from">${displayQuestionnaireModel.questionnaire.questionnairefrom}</div>
            <c:forEach var="qt" items="${displayQuestionnaireModel.displayQuestionTypeModels}">
                <div class="questionnaireadd-main-type">
                    <div class="questionnaireadd-main-type-title"
                         onclick="questionnaireAddMainTypeLife(this,event)">${qt.questionType.questionTypename}<img
                            src="img/icon/icon-delete.png" onclick="deleteItemTypes(this,event)" title="删除"></div>
                    <div class="questionnaireadd-main-type-content">
                        <ul>
                            <c:forEach var="q" items="${qt.displayQuestionModels}">
                                <li>
                                    <label onblur='changeItemTitle(this)'
                                           contenteditable="true"><span>*</span>${q.question.questionname}</label>
                                    <div class="questionnaireadd-main-type-content-option">
                                        <c:forEach var="o" items="${q.optionsList}">
                                            <input type="${q.question.questionstyle}"> <span
                                                onblur='changeItemOption(this)'
                                                contenteditable="true">${o.optionsname}</span><br>
                                        </c:forEach>
                                    </div>
                                    <div class="questionnaireadd-main-type-item-tool" data-type="radio">
                                        <img src="img/icon/icon-delete.png" onclick="deleteItemOption(this)" title="删除">
                                        <img src="img/icon/icon-more2.png" onclick="addItemOptions(this)" title="添加选项">
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="questionnaireadd-show-bottom">
            <shiro:hasPermission name="questionnaire:update">
                <button onclick="questionnaireEditorSubmit('${displayQuestionnaireModel.questionnaire.id}')"
                        class="layui-btn">修改
                </button>
            </shiro:hasPermission>
            <button onclick="if(questionnaireCheck()){layer.msg('问卷正常，可提交')}" class="layui-btn layui-btn-normal">检查
            </button>
        </div>
    </div>
</div>
</body>
<script>
    layui.use(['laydate'], function () {
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#dateselect',//指定元素
            type: 'datetime',
            range: '~',
            format: 'yyyy-MM-dd HH:mm:ss'
            , theme: 'molv'
        });
    });

</script>
</html>
