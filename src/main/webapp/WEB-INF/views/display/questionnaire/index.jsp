<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta name="description" content="。。。" />
	<meta name="keywords" content="留守儿童问卷调查" />
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
		form input:last-child{
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
	</style>
</head>
<body id="top">
	<h1>${displayQuestionnaireModel.questionnaire.questionnairename}</h1>
	<p style="margin-left: 20px;">${displayQuestionnaireModel.questionnaire.questionnairecomp}</p>
	<p style="text-indent: 2em;margin-left: 20px;">${displayQuestionnaireModel.questionnaire.questionnaireexplain}</p>
	<p style="text-indent: 2em; text-align: right;margin-right: 20px;">${displayQuestionnaireModel.questionnaire.questionnairefrom}</p>
	<form id="formQ" action="javascript:;" method="POST" onsubmit="return volidateSubmit()">
		<c:forEach var="qt" items="${displayQuestionnaireModel.displayQuestionTypeModels}">
			<div class="bigtitle" id="bigtitle1" style="border:1px solid #3280FC; box-shadow: 5px 0px 10px #3280FC;">
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
							<%--<br>
							<br>
							<input id="sex2" type="radio" name="sex">
							<label for="sex2">女</label>--%>
						</div>
					</div>
				</c:forEach>
				<%--<div class="question">
					<div class="qtitle">
						<span>2. 你今年就读( )年级</span>
						<br>
						<br>
						<input id="grade1" type="radio" name="grade">
						<label for="grade1">一年级</label><br>
						<br>
						<input id="grade2" type="radio" name="grade">
						<label for="grade2">二年级</label>
						<br>
						<br>
						<input id="grade3" type="radio" name="grade">
						<label for="grade3">三年级</label>
						<br>
						<br>
						<input id="grade4" type="radio" name="grade">
						<label for="grade4">四年级</label>
						<br>
						<br>
						<input id="grade5" type="radio" name="grade">
						<label for="grade5">五年级</label>
						<br>
						<br>
						<input id="grade6" type="radio" name="grade">
						<label for="grade6">六年级</label>
						<br>
						<br>
						<input id="grade7" type="radio" name="grade">
						<label for="grade7">七年级</label>
						<br>
						<br>
						<input id="grade8" type="radio" name="grade">
						<label for="grade8">八年级</label>
						<br>
						<br>
						<input id="grade9" type="radio" name="grade">
						<label for="grade9">九年级</label>
					</div>
				</div>--%>
			</div>
		</c:forEach>
		<%--<div class="bigtitle" id="bigtitle2" style="border:1px solid #FFDCBC; ">
			<span class="titlespan" style="font-weight: bold; font-size: 18px;color:#F1A325 ; background-color: #FFF0D5; line-height: 40px;">二、你的学习成绩</span>
			<div class="question">
			<div class="qtitle">
				<span>1. 你的学习成绩在班里属于哪种水平？</span>
				<br>
				<br>
				<input id="score1" type="radio" name="score">
				<label for="score1">优秀</label>
				<br><br>
				<input id="score2" type="radio" name="score">
				<label for="score2">良好</label>
				<br><br>
				<input id="score3" type="radio" name="score">
				<label for="score3">中等</label>
				<br><br>
				<input id="score4" type="radio" name="score">
				<label for="score4">较差</label>
				<br><br>
				<input id="score5" type="radio" name="score">
				<label for="score5">很差</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>2.学习遇到困难时老师会辅导你吗？</span>
				<br>
				<br>
				<input id="helpt1" type="radio" name="helpt">
				<label for="helpt1">经常</label>
				<br><br>
				<input id="helpt2" type="radio" name="helpt">
				<label for="helpt2">有时</label>
				<br><br>
				<input id="helpt3" type="radio" name="helpt">
				<label for="helpt3">很少</label>
				<br><br>
				<input id="helpt4" type="radio" name="helpt">
				<label for="helpt4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>3.下课后，老师经常同你谈心吗？</span>
				<br>
				<br>
				<input id="speakt1" type="radio" name="speakt">
				<label for="speakt1">经常</label>
				<br><br>
				<input id="speakt2" type="radio" name="speakt">
				<label for="speakt2">有时</label>
				<br><br>
				<input id="speakt3" type="radio" name="speakt">
				<label for="speakt3">很少</label>
				<br><br>
				<input id="speakt4" type="radio" name="speakt">
				<label for="speakt4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>4.如果回答不出老师的问题，老师会鼓励你思考吗？</span>
				<br>
				<br>
				<input id="arouse1" type="radio" name="arouse">
				<label for="arouse1">经常</label>
				<br><br>
				<input id="arouse2" type="radio" name="arouse">
				<label for="arouse2">有时</label>
				<br><br>
				<input id="arouse3" type="radio" name="arouse">
				<label for="arouse3">很少</label>
				<br><br>
				<input id="arouse4" type="radio" name="arouse">
				<label for="arouse4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>5.作业上出错的地方，老师会指出并帮助你改正吗？</span>
				<br>
				<br>
				<input id="error1" type="radio" name="error">
				<label for="error1">经常</label>
				<br><br>
				<input id="error2" type="radio" name="error">
				<label for="error2">有时</label>
				<br><br>
				<input id="error3" type="radio" name="error">
				<label for="error3">很少</label>
				<br><br>
				<input id="error4" type="radio" name="error">
				<label for="error4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>6.课堂上，老师经常给你发言的机会吗？</span>
				<br>
				<br>
				<input id="speakwithclass1" type="radio" name="speakwithclass">
				<label for="speakwithclass1">经常</label>
				<br><br>
				<input id="speakwithclass2" type="radio" name="speakwithclass">
				<label for="speakwithclass2">有时</label>
				<br><br>
				<input id="speakwithclass3" type="radio" name="speakwithclass">
				<label for="speakwithclass3">很少</label>
				<br><br>
				<input id="speakwithclass4" type="radio" name="speakwithclass">
				<label for="speakwithclass4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>7.你每天都能按时完成作业吗？</span>
				<br>
				<br>
				<input id="finishwork1" type="radio" name="finishwork">
				<label for="finishwork1">经常</label>
				<br><br>
				<input id="finishwork2" type="radio" name="finishwork">
				<label for="finishwork2">有时</label>
				<br><br>
				<input id="finishwork3" type="radio" name="finishwork">
				<label for="finishwork3">很少</label>
				<br><br>
				<input id="finishwork4" type="radio" name="finishwork">
				<label for="finishwork4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>8.在学习过程中，在你身上发生过下列情况吗？（最多可以选3项）</span>
				<br>
				<br>
				<input id="listquestion1" type="checkbox" name="listquestion">
				<label for="listquestion1">学习基础不好</label>
				<br><br>
				<input id="listquestion2" type="checkbox" name="listquestion">
				<label for="listquestion2">上课没有认真听讲</label>
				<br><br>
				<input id="listquestion3" type="checkbox" name="listquestion">
				<label for="listquestion3">自己不够聪明</label>
				<br><br>
				<input id="listquestion4" type="checkbox" name="listquestion">
				<label for="listquestion4">学习方法不对</label>
				<br><br>
				<input id="listquestion5" type="checkbox" name="listquestion">
				<label for="listquestion5">太胆小，不敢问老师或者找同学帮助解决问题</label>
				<br><br>
				<input id="listquestion6" type="checkbox" name="listquestion">
				<label for="listquestion6">已上情况都没有</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>9.在学习过程中，在你身上发生过下列情况吗?（最多可以选3项）</span>
				<br>
				<br>
				<input id="selflistquestion1" type="checkbox" name="selflistquestion">
				<label for="selflistquestion1">父母工作太忙，没有时间辅导我的学习</label>
				<br><br>
				<input id="selflistquestion2" type="checkbox" name="selflistquestion">
				<label for="selflistquestion2">父母学历低，没有能力辅导我的学习</label>
				<br><br>
				<input id="selflistquestion3" type="checkbox" name="selflistquestion">
				<label for="selflistquestion3">父母不关心我的学习</label>
				<br><br>
				<input id="selflistquestion4" type="checkbox" name="selflistquestion">
				<label for="selflistquestion4">家庭经济困难，不能提供足够的学习材料</label>
				<br><br>
				<input id="selflistquestion5" type="checkbox" name="selflistquestion">
				<label for="selflistquestion5">家庭环境太差，影响了我的学习</label>
				<br><br>
				<input id="selflistquestion6" type="checkbox" name="selflistquestion">
				<label for="selflistquestion6">已上情况都没有</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>10. 你在学习中遇到困难最愿意找谁帮助解决？（最多可以选3项）</span>
				<br>
				<br>
				<input id="questionwithwho1" type="checkbox" name="questionwithwho">
				<label for="questionwithwho1">老师</label>
				<br><br>
				<input id="questionwithwho2" type="checkbox" name="questionwithwho">
				<label for="questionwithwho2">同学</label>
				<br><br>
				<input id="questionwithwho3" type="checkbox" name="questionwithwho">
				<label for="questionwithwho3">家人</label>
				<br><br>
				<input id="questionwithwho4" type="checkbox" name="questionwithwho">
				<label for="questionwithwho4">朋友</label>
				<br><br>
				<input id="questionwithwho5" type="checkbox" name="questionwithwho">
				<label for="questionwithwho5">其他人</label>
				<br><br>
				<input id="questionwithwho6" type="checkbox" name="questionwithwho">
				<label for="questionwithwho6">靠自己，不愿意找别人帮助</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>11. 最近一年内，你们换过老师吗？</span>
				<br>
				<br>
				<input id="swapteacher1" type="radio" name="swapteacher">
				<label for="swapteacher1">经常</label>
				<br><br>
				<input id="swapteacher2" type="radio" name="swapteacher">
				<label for="swapteacher2">有时</label>
				<br><br>
				<input id="swapteacher3" type="radio" name="swapteacher">
				<label for="swapteacher3">很少</label>
				<br><br>
				<input id="swapteacher4" type="radio" name="swapteacher">
				<label for="swapteacher4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>12. 你到学校（留守儿童之家）图书室读书吗？</span>
				<br>
				<br>
				<input id="tubook1" type="radio" name="tubook">
				<label for="tubook1">经常</label>
				<br><br>
				<input id="tubook2" type="radio" name="tubook">
				<label for="tubook2">有时</label>
				<br><br>
				<input id="tubook3" type="radio" name="tubook">
				<label for="tubook3">很少</label>
				<br><br>
				<input id="tubook4" type="radio" name="tubook">
				<label for="tubook4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>13. 你上学迟到吗？</span>
				<br>
				<br>
				<input id="late1" type="radio" name="late">
				<label for="late1">经常</label>
				<br><br>
				<input id="late2" type="radio" name="late">
				<label for="late2">有时</label>
				<br><br>
				<input id="late3" type="radio" name="late">
				<label for="late3">很少</label>
				<br><br>
				<input id="late4" type="radio" name="late">
				<label for="late4">从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>14. 在平时的学校生活中，你有什么样的烦恼？</span>
				<br>
				<input id="schoolannoyance1" type="checkbox" name="schoolannoyance">
				<label for="schoolannoyance1">学校不够好</label>
				<br><br>
				<input id="schoolannoyance2" type="checkbox" name="schoolannoyance">
				<label for="schoolannoyance2">作业太多</label>
				<br><br>
				<input id="schoolannoyance3" type="checkbox" name="schoolannoyance">
				<label for="schoolannoyance3">与老师关系不好</label>
				<br><br>
				<input id="schoolannoyance4" type="checkbox" name="schoolannoyance">
				<label for="schoolannoyance4">朋友很少</label>
				<br><br>
				<input id="schoolannoyance5" type="checkbox" name="schoolannoyance">
				<label for="schoolannoyance5">感觉不安全</label>
				<br><br>
				<input id="schoolannoyance6" type="checkbox" name="schoolannoyance">
				<label for="schoolannoyance6">心理上不适应</label>
				<br><br>
				<input id="schoolannoyance7" type="checkbox" name="schoolannoyance">
				<label for="schoolannoyance7">没有烦恼</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>15. 你认为你的学校有哪些地方需要改善？（最多可以选3项）</span>
				<br>
				<br>
				<input type="checkbox" name="schoolperfect">
				<label>活动场地大一点</label>
				<br><br>
				<input type="checkbox" name="schoolperfect">
				<label>体育器材多一些</label>
				<br><br>
				<input type="checkbox" name="schoolperfect">
				<label>增加点课外活动</label>
				<br><br>
				<input type="checkbox" name="schoolperfect">
				<label>校园环境好一些</label>
				<br><br>
				<input type="checkbox" name="schoolperfect">
				<label>教师宽敞一些</label>
				<br><br>
				<input type="checkbox" name="schoolperfect">
				<label>学校在干净一些</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>16. 你每天做作业花多长时间？</span>
				<br>
				<br>
				<input type="radio" name="learntime">
				<label>不到0.5小时</label>
				<br><br>
				<input type="radio" name="learntime">
				<label>0.5~1小时</label>
				<br><br>
				<input type="radio" name="learntime">
				<label>1~1.5小时</label>
				<br><br>
				<input type="radio" name="learntime">
				<label>1.5小时以上</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>17. 在平时的学习和生活中，你觉得老师对你怎么样？</span>
				<br>
				<br>
				<input type="radio" name="teachereval">
				<label>老师经常批评我</label>
				<br><br>
				<input type="radio" name="teachereval">
				<label>老师从来不关注我</label>
				<br><br>
				<input type="radio" name="teachereval">
				<label>老师对我不公平</label>
				<br><br>
				<input type="radio" name="teachereval">
				<label>老师不喜欢我</label>
				<br><br>
				<input type="radio" name="teachereval">
				<label>老师对我很好</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>18. 在与同学交往的过程中，你是否遇到以下情况？</span>
				<br>
				<br>
				<input type="checkbox" name="schoolmatecontact">
				<label>自己不主动和同学说话</label>
				<br><br>
				<input type="checkbox" name="schoolmatecontact">
				<label>同学不理我</label>
				<br><br>
				<input type="checkbox" name="schoolmatecontact">
				<label>不喜欢其他同学</label>
				<br><br>
				<input type="checkbox" name="schoolmatecontact">
				<label>感觉自己和他们不一样</label>
				<br><br>
				<input type="checkbox" name="schoolmatecontact">
				<label>没有遇到以上这些情况</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>19. 你在学校里看到有什么不安全的情况吗？</span>
				<br>
				<br>
				<input type="checkbox" name="security">
				<label>同学们经常打架</label>
				<br><br>
				<input type="checkbox" name="security">
				<label>校园里总有陌生人出入</label>
				<br><br>
				<input type="checkbox" name="security">
				<label>学校周围的环境混乱</label>
				<br><br>
				<input type="checkbox" name="security">
				<label>有人勒索、欺负同学</label>
				<br><br>
				<input type="checkbox" name="security">
				<label>以上情况都没有</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>20. 你对学校生活有什么不适应的地方吗？</span>
				<br>
				<br>
				<input type="checkbox" name="schoolife">
				<label>很多同学都看不起我</label>
				<br><br>
				<input type="checkbox" name="schoolife">
				<label>经常被人误会或错怪</label>
				<br><br>
				<input type="checkbox" name="schoolife">
				<label>经常与同学发生矛盾</label>
				<br><br>
				<input type="checkbox" name="schoolife">
				<label>以上情况都没有</label>
			</div>
		</div>
		</div>

		<div class="bigtitle" id="bigtitle3" style="border:1px #BAE8B6 solid;">
			<span class="titlespan" style="font-weight: bold; color:#38B03F; font-size: 18px; background-color: #DDF4DF; line-height: 40px;">三、你的家庭情况</span>
			<div class="question">
			<div class="qtitle">
				<span>1. 你回家是否有安静的环境做作业？</span>
				<br>
				<br>
				<input type="radio" name="learnenv">
				<label>有</label>
				<br><br>
				<input type="radio" name="learnenv">
				<label>无</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>2. 你认为每天和父母在一起的时间多吗？</span>
				<br>
				<br>
				<input type="radio" name="withparentime">
				<label>很多</label>
				<br><br>
				<input type="radio" name="withparentime">
				<label>较多</label>
				<br><br>
				<input type="radio" name="withparentime">
				<label>还可以</label>
				<br><br>
				<input type="radio" name="withparentime">
				<label>较少</label>
				<br><br>
				<input type="radio" name="withparentime">
				<label>很少</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>3. 你的父母经常和你谈心吗？</span>
				<br>
				<br>
				<input type="radio" name="parentspe">
				<label>经常</label>
				<br><br>
				<input type="radio" name="parentspe">
				<label>有时</label>
				<br><br>
				<input type="radio" name="parentspe">
				<label>很少</label>
				<br><br>
				<input type="radio" name="parentspe">
				<label>从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>4. 你的父母通常最关心你哪些方面的问题？</span>
				<br>
				<br>
				<input type="radio" name="parentcare">
				<label>学习成绩</label>
				<br><br>
				<input type="radio" name="parentcare">
				<label>身体状况</label>
				<br><br>
				<input type="radio" name="parentcare">
				<label>生活情况</label>
				<br><br>
				<input type="radio" name="parentcare">
				<label>心里的烦恼</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>5. 你的父母在家辅导你学习吗？</span>
				<br>
				<br>
				<input type="radio" name="parenthelp">
				<label>经常辅导</label>
				<br><br>
				<input type="radio" name="parenthelp">
				<label>有时辅导</label>
				<br><br>
				<input type="radio" name="parenthelp">
				<label>很少辅导</label>
				<br><br>
				<input type="radio" name="parenthelp">
				<label>从不辅导</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>6. 当你正在学习时父母会叫你去做与学习无关的事情吗？</span>
				<br>
				<br>
				<input type="radio" name="parentnohelp">
				<label>经常会</label>
				<br><br>
				<input type="radio" name="parentnohelp">
				<label>有时会</label>
				<br><br>
				<input type="radio" name="parentnohelp">
				<label>很少会</label>
				<br><br>
				<input type="radio" name="parentnohelp">
				<label>从不会</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>7. 当你的作业、考试成绩退步时，你的父母怎样对待你？</span>
				<br>
				<br>
				<input type="radio" name="scoredown">
				<label>帮你分析原因，鼓励你进步</label>
				<br><br>
				<input type="radio" name="scoredown">
				<label>严厉批评</label>
				<br><br>
				<input type="radio" name="scoredown">
				<label>不理睬</label>
				<br><br>
				<input type="radio" name="scoredown">
				<label>打骂</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>8. 你认为你现在的家庭生活怎么样？</span>
				<br>
				<br>
				<input type="radio" name="homelife">
				<label>很好</label>
				<br><br>
				<input type="radio" name="homelife">
				<label>比较好</label>
				<br><br>
				<input type="radio" name="homelife">
				<label>一般</label>
				<br><br>
				<input type="radio" name="homelife">
				<label>比较差</label>
				<br><br>
				<input type="radio" name="homelife">
				<label>很差</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>9. 家里人经常带你去能培养你兴趣的场所吗？（如图书馆、书店、科技馆等）</span>
				<br>
				<br>
				<input type="radio" name="parentplay">
				<label>经常</label>
				<br><br>
				<input type="radio" name="parentplay">
				<label>有时</label>
				<br><br>
				<input type="radio" name="parentplay">
				<label>很少</label>
				<br><br>
				<input type="radio" name="parentplay">
				<label>从来不</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>10. 你在家庭生活里有什么样的烦恼？</span>
				<br>
				<br>
				<input type="radio" name="homeannoyance">
				<label>父母教育方式粗暴（如经常打骂等）</label>
				<br><br>
				<input type="radio" name="homeannoyance">
				<label>缺少父母关心</label>
				<br><br>
				<input type="radio" name="homeannoyance">
				<label>家里没有钱</label>
				<br><br>
				<input type="radio" name="homeannoyance">
				<label>自己或家人身体不好</label>
				<br><br>
				<input type="radio" name="homeannoyance">
				<label>被人欺负</label>
				<br><br>
				<input type="radio" name="homeannoyance">
				<label>父母经常吵架</label>
				<br><br>
				<input type="radio" name="homeannoyance">
				<label>没有烦恼</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>11. 当你心里有烦恼时，一般会告诉谁？（最多可以选3项）</span>
				<br>
				<br>
				<input type="radio" name="homeannoyancetowho">
				<label>家人</label>
				<br><br>
				<input type="radio" name="homeannoyancetowho">
				<label>同学</label>
				<br><br>
				<input type="radio" name="homeannoyancetowho">
				<label>朋友</label>
				<br><br>
				<input type="radio" name="homeannoyancetowho">
				<label>老师</label>
				<br><br>
				<input type="radio" name="homeannoyancetowho">
				<label>亲戚</label>
				<br><br>
				<input type="radio" name="homeannoyancetowho">
				<label>憋在心里或写日记</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>12. 你现在和谁一起生活？</span>
				<br>
				<br>
				<input type="radio" name="withlife">
				<label>和父亲一起生活</label>
				<br><br>
				<input type="radio" name="withlife">
				<label>和母亲一起生活</label>
				<br><br>
				<input type="radio" name="withlife">
				<label>和爷爷奶奶一起生活</label>
				<br><br>
				<input type="radio" name="withlife">
				<label>和其他亲戚一起生活</label>
			</div>
		</div>
		<div class="question">
			<div class="qtitle">
				<span>13. 放学后，你通常较多的时间是在做什么？</span>
				<br>
				<br>
				<input type="radio" name="classedtime">
				<label>参加体育运动</label>
				<br><br>
				<input type="radio" name="classedtime">
				<label>与同学、朋友聊天</label>
				<br><br>
				<input type="radio" name="classedtime">
				<label>帮父母干活</label>
				<br><br>
				<input type="radio" name="classedtime">
				<label>学习、做作业</label>
				<br><br>
				<input type="radio" name="classedtime">
				<label>上网玩游戏</label>
				<br><br>
				<input type="radio" name="classedtime">
				<label>看电视、电影</label>
				<br><br>
				<input type="radio" name="classedtime">
				<label>看课外书</label>
				<br><br>
				<input type="radio" name="classedtime">
				<label>其它活动</label>
			</div>
		</div>
		</div>--%>
		
		<div id="bottom" style="width: 100%;margin-top: 30px; text-align: center;"><input type="submit" value="完成"></div>
	</form>
	<div id="md">
		<ul>
			<li ><a href="/display/displayQuestionnaire#top">头部</a></li>
			<li ><a href="/display/displayQuestionnaire#bigtitle1">一</a></li>
			<li ><a href="/display/displayQuestionnaire#bigtitle2">二</a></li>
			<li ><a href="/display/displayQuestionnaire#bigtitle3">三</a></li>
			<li ><a href="/display/displayQuestionnaire#bottom">底部</a></li>
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