<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script>
        var questionnaire = {};
    </script>
    <script src="js/questionnaire.js"></script>
</head>
<body>
<div class="questionnaireadd">
    <div class="questionnaireadd-base-info">
        <blockquote class="layui-elem-quote">问卷基础信息</blockquote>
        <ul>
            <li><span><i style="color:red;">*</i>问卷名称：</span><input onkeyup="questionnaireNameChange()" type="text"
                                                                    required name="qname"></li>
            <li><span>问候语：</span><input onkeyup="questionnaireCompChange()" type="text" name="comp"></li>
            <li><span>问卷出处：</span><input onkeyup="questionnaireWhereChange()" type="text" name="qwhere"></li>
            <li><span>问卷有效时间：</span><input onblur="questionnaireDateRangeChange()" name="daterange" type="text"
                                           placeholder="yyyy-MM-dd HH:mm:ss ~ yyyy-MM-dd HH:mm:ss" id="dateselect"></li>
            <li><span>问卷说明：</span><textarea onkeyup="questionnaireExplainChange()" name="explain" cols="14"
                                            rows="5"></textarea></li>
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
            <div class="questionnaireadd-main-title"></div>
            <div class="questionnaireadd-main-comp"></div>
            <div class="questionnaireadd-main-explain"></div>
            <div class="questionnaireadd-main-from"></div>
        </div>
        <div class="questionnaireadd-show-bottom">
            <button onclick="questionnaireSubmit()" class="layui-btn">提交</button>
            <button onclick="if(questionnaireCheck()){layer.msg('问卷正常，可提交')}" class="layui-btn layui-btn-normal">检查</button>

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
