<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta name="description" content="${displayQuestionnaireModel.questionnaire.questionnairename}" />
	<meta name="keywords" content="留守儿童问卷调查,${displayQuestionnaireModel.questionnaire.questionnairename}" />
	<title>留守儿童问卷调查</title>
	<%@ include file="../../base.jsp"%>
	<style type="text/css">
		*{
			margin: 0;
			padding:0;
			text-decoration: none;
			list-style: none;
		}
		body{
			overflow-x: hidden;
		}
		h1{
			width: 100%;
			height: auto;
			text-align: center;
			font-size: 50px;
			font-weight: normal;
		}
		p{
			font-size: 20px;
			line-height: 35px;
			margin-top: 15px;
		}
		.bigtitle{
			width: 99.8%;
			background-color: #F1F1F1;
			padding-bottom: 15px;
			border-radius: 5px;
			margin-top: 15px;
		}
		.titlespan{
			display: block;
			width: 100%;
			height: 40px;
			background-color: #3280FC;
			text-align: center;
		}
		.question{
			width: 96%;
			margin-top: 15px;
			background-color: #FFF;
			padding-top:20px;
			padding-bottom: 20px;
			border-radius: 5px;
			border: 1px #DDD solid;
			margin-left: 1.8%;
			box-shadow: 0px 0px 15px #CCC;
		}
		.question input{
			width:20px;
			height: 20px;
		}
		#titlespan{
			display: block;
			width: 95%;
			border-bottom: 1px #AAA solid;
			padding-bottom: 15px;
		}
		
		.qtitle input{
			vertical-align: middle;
			margin-left: 25px;
		}
		.qtitle label{
			margin-left: 10px;
		}
		form{
			background-color: #F1F1F1;
			padding-bottom: 50px;
		}
		#bottom input:last-child{
			width: 90%;
			height: 50px;
			background-color: #44B549;
			border: 0px;
			font-size: 18px;
			color:#000;
			border-radius: 7px;
			color:#fff;
		}
		.qtitle{
			margin-left: 20px;
		}
		.qtitle span{
			font-weight: bold;
		}
		#md{
			position: fixed;
		}
		#md li{
			width: 50px;
			height: 80px;
			margin-top: 10px;
			text-align: center;
			line-height: 80px;
		}
		#md li a:hover{
			background-color: rgba(100,100,100,0.8);
		}
		#md li a{
			display: block;
			color:#fff;
			width: 100%;
			height: 100%;
			border-radius: 15px;
			background-color: rgba(100,100,100,0.4);

		}
		.person-info{
			width: 100%;
			height: 30px;
			text-align: center;
		}
		.person-info input{
			width: 200px;
			height: 30px;
			padding-left: 10px;
			margin-left: 50px;
			border: 0;
			border-bottom: 1px rgba(0,0,0,0.6) solid;
			background-color: #F1F1F1;

		}
	</style>
</head>
<body id="top">
	<h1>${displayQuestionnaireModel.questionnaire.questionnairename}</h1>
	<p style="margin-left: 20px;">${displayQuestionnaireModel.questionnaire.questionnairecomp}</p>
	<p style="text-indent: 2em;margin-left: 20px;">${displayQuestionnaireModel.questionnaire.questionnaireexplain}</p>
	<p style="text-indent: 2em; text-align: right;margin-right: 20px;">${displayQuestionnaireModel.questionnaire.questionnairefrom}</p>
	<form id="formQ" action="javascript:;" onsubmit="return volidateSubmit()">
		<div class="person-info">
			<input type="text" name="personname" placeholder="姓名" required>
			<input type="text" name="persongrade" placeholder="年级" required>
			<input type="text" name="personclass" placeholder="班级" required>
		</div>
		<c:forEach var="qt" items="${displayQuestionnaireModel.displayQuestionTypeModels}" varStatus="str">
			<div class="bigtitle" id="bigtitle${str.index+1}" style="border:1px solid #3280FC; box-shadow: 5px 0px 10px #3280FC;">
				<span class="titlespan" style="font-weight: bold; font-size: 18px; color:#fff; line-height: 40px;">${qt.questionType.questionTypename}</span>
				<c:forEach var="q" items="${qt.displayQuestionModels}">
					<div class="question">
						<div class="qtitle">
							<span>${q.question.questionname}</span>
							<c:forEach var="o" items="${q.optionsList}">
								<br>
								<br>
								<input id="${o.id}" type="${q.question.questionstyle}" name="${displayQuestionnaireModel.questionnaire.id}'_'${qt.questionType.id}'_'${q.question.id}" value="${o.optionsname}">
								<label for="${o.id}">${o.optionsname}</label>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
		<div id="bottom" style="width: 100%;margin-top: 30px; text-align: center;"><input type="submit" value="完成"></div>
	</form>
	<div id="md">
		<ul>
			<li ><a href="/system/displayQuestionnaire?id=${displayQuestionnaireModel.questionnaire.id}#top">头部</a></li>
			<c:forEach var="qt" items="${displayQuestionnaireModel.displayQuestionTypeModels}" varStatus="str">
				<li ><a href="/system/displayQuestionnaire?id=${displayQuestionnaireModel.questionnaire.id}#bigtitle${str.index+1}">${str.index+1}</a></li>
			</c:forEach>
			<li ><a href="/system/displayQuestionnaire?id=${displayQuestionnaireModel.questionnaire.id}#bottom">底部</a></li>
		</ul>
	</div>
</body>
<script type="text/javascript">
	var questionSpanLength=$(".question span").length;
	var windowHeight=$(window).height();
	var questionSpan=$(".question span");
	var question=$(".question");
	for (var i = 0; i <questionSpanLength ; i++) {
		var inputs=question[i].getElementsByTagName("input");
		$(questionSpan[i]).after("<input type='hidden' name='question"+$(inputs[0]).attr("name")+"' value='"+$(questionSpan[i]).html()+"'>");
	}

	for (var i = 0; i < question.length; i++) {
		var question_input=question[i].getElementsByTagName("input");
		var question_label=question[i].getElementsByTagName("label");
		for (var j = 1; j < question_input.length; j++) {
			if($(question_input[j]).attr("type")!="checkbox"){
				//$(question_input[j]).attr({"value":$(question_label[j-1]).html(),"required":"required"});
			}
			$(question_input[j]).attr("id",$(question_input[j]).attr("name")+j);
			$(question_label[j-1]).attr("for",$(question_input[j]).attr("name")+j);
		}
	}
	function volidateSubmit(){
		var isGo=false;
		for (var z = 0; z < question.length; z++) {
			$(question[z]).css("background-color","rgba(204,204,204,0.8)");
		}

		for (var i = 0; i < question.length; i++) {
			var question_input=question[i].getElementsByTagName("input");
			var log=0;
			for (var j = 1; j < question_input.length; j++) {
				if(question_input[j].checked){
					log++;
				}
			}
			if(log<=0){
				break;
			}
		}
		if(i>=question.length) isGo=true;
		else{
			isGo=false;
			
			$(window).scrollTop($(question[i]).offset().top-300);
			for (var j = 0; j < question.length; j++) {
				var question_input=question[j].getElementsByTagName("input");
				var log=0;
				for (var x = 1; x < question_input.length; x++) {
					if(question_input[x].checked){
						log++;
					}
				}
				if(log<=0){
					$(question[j]).css("background-color","rgba(250,0,0,0.3)");
				}
			}
			
			alert("请补充当前未做题目(红色背景)!");
		}
		return isGo;
	}
	$("#formQ").submit(function () {
		alert(JSON.stringify($("#formQ").serializeArray()));
    });
	$("#md").css({"right":10,"top":(windowHeight-$("#md").height())/2});

</script>
</html>