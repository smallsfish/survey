<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../base.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>数据查看</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="layui/css/layui.css">
    <script src="js/echarts.js"></script>
</head>

<body oncontextmenu="return false" onselect="return false">
<div class="da-items">
    <c:forEach var="c" items="${ch}" varStatus="st">
        <div class="da-item">
            <div id="da${st.index}" style="width: 50%;height: 90%; float:left;"></div>
            <div id="ba${st.index}" style="width: 50%;height: 90%;float:left;"></div>
        </div>
    </c:forEach>
</div>
</body>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myCharts = new Array();
    var bCharts = new Array();
    var options = new Array();
    var bOptions = new Array();
    <c:forEach var="c" items="${ch}" varStatus="st">
    myCharts[${st.index}] = echarts.init(document.getElementById('da${st.index}'));
    bCharts[${st.index}] = echarts.init(document.getElementById('ba${st.index}'));
    </c:forEach>

    // 指定图表的配置项和数据
    <c:forEach var="c" items="${ch}" varStatus="st1">
    options[${st1.index}] = {
        title: {
            text: '${c.name}',
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['选择数量'],
            right: '6%'
        },
        grid: {
            left: '1%',
            right: '1.3%',
            bottom: '1%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 1]
        },
        yAxis: {
            type: 'category',
            data: [<c:forEach var="o" items="${c.optionInfos}" varStatus="st2">
                <c:if test="${st2.index!=c.optionInfos.size()-1}">
                "${o.name}",
                </c:if>
                <c:if test="${st2.index==c.optionInfos.size()-1}">
                "${o.name}"
                </c:if>
                </c:forEach>]
        },
        series: [
            {
                name: '选择数量',
                type: 'bar',
                data: [<c:forEach var="o" items="${c.optionInfos}" varStatus="st3">
                    <c:if test="${st3.index!=c.optionInfos.size()-1}">
                    "${o.count}",
                    </c:if>
                    <c:if test="${st3.index==c.optionInfos.size()-1}">
                    "${o.count}"
                    </c:if>
                    </c:forEach>]
            }
        ]
    };

    bOptions[${st1.index}] = {
        title: {
            text: '饼状图分析',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a}<br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: '1%',
            top: '1%',
            data: [<c:forEach var="o" items="${c.optionInfos}" varStatus="st2">
                <c:if test="${st2.index!=c.optionInfos.size()-1}">
                '${o.name}',
                </c:if>
                <c:if test="${st2.index==c.optionInfos.size()-1}">
                '${o.name}'
                </c:if>
                </c:forEach>]
        },
        series: [
            {
                name: '选择情况',
                type: 'pie',
                radius: '55%',
                center: ['65%', '50%'],
                data: [
                    //{value:335, name:'直接访问'},
                    <c:forEach var="o" items="${c.optionInfos}" varStatus="st2">
                    <c:if test="${st2.index!=c.optionInfos.size()-1}">
                    {value:${o.count},name:'${o.name}'},
                    </c:if>
                    <c:if test="${st2.index==c.optionInfos.size()-1}">
                    {value:${o.count},name:'${o.name}'}
                    </c:if>
                    </c:forEach>
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    </c:forEach>
    // 使用刚指定的配置项和数据显示图表。
    <c:forEach var="c" items="${ch}" varStatus="st">
    myCharts[${st.index}].setOption(options[${st.index}]);
    bCharts[${st.index}].setOption(bOptions[${st.index}]);
    </c:forEach>

</script>
</html>
