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
    <script src="js/echarts.js"></script>
</head>

<body oncontextmenu="return false" onselect="return false">
<div class="da-items">
    <div class="questions">
        <ul>
            <c:forEach var="q" items="${questionList}" varStatus="st">
                <li data-qid="${q.id}" ${st.index==0 ? 'class="qc-show"':''}>${q.questionname}</li>
            </c:forEach>
        </ul>
    </div>

    <div class="da-item">
        <div class="da-tools">
            <ul>
                <li><img src="img/icon/bingzhuang.png" alt="饼状图"></li>
                <li><img src="img/icon/zhexian.png" alt="折线图"></li>
                <li class="da-tool-show"><img src="img/icon/zhuzhuang.png" alt="柱状图"></li>
            </ul>
        </div>
        <div class="charts">
            <div id="dabing"></div>
            <div id="dazhe"></div>
            <div id="dazhu" class="charts-show"></div>
        </div>
    </div>
    <div class="county">
        <ul>
            <li class="qc-show">全部</li>
            <c:forEach var="c" items="${countyList}">
                <li data-cid="${c.id}">${c.countyname}</li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
<script type="text/javascript">
    var name = "asdfasdf";
    var currentQuestionId = '${questionList.get(0).id}';
    var currentCountyId = 0;
    var ydata = [];
    var xdata = [];

    $(".da-tools ul").on('click', 'li', function (ev) {
        var liid = $('.da-tools ul li').index($(this));
        clearDaToolStyle();
        if (liid == 0) {
            $("#dabing").attr("class", "charts-show");
        } else if (liid == 1) {
            $("#dazhe").attr("class", "charts-show");
        } else {
            $("#dazhu").attr("class", "charts-show");
        }
        $('.da-tools ul li:eq(' + liid + ')').attr("class", "da-tool-show");
    });

    function clearDaToolStyle() {
        $('.da-tools ul li').attr("class", "");
        $(".charts-show").attr("class", "");
    }

    // 基于准备好的dom，初始化echarts实例
    myChartZhu = echarts.init(document.getElementById('dazhu'));
    myChartZhe = echarts.init(document.getElementById('dazhe'));
    myChartBing = echarts.init(document.getElementById('dabing'));

    optionZhu = {
        title: {
            text: name,
            left: '8%'
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '直接访问',
                type: 'bar',
                barWidth: '60%',
                data: [10, 52, 200, 334, 390, 330, 220]
            }
        ]
    };

    optionZhe = {
        title: {
            text: name
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data: ['选择情况']
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '选择情况',
                type: 'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data: [120, 132, 101, 134, 90, 230, 210]
            }
        ]
    };
    optionBing = {
        title: {
            text: name,
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
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
    myChartZhu.setOption(optionZhu);
    myChartZhe.setOption(optionZhe);
    myChartBing.setOption(optionBing);

    function clearQuesitonStyle() {
        $(".questions ul li").attr("class", "");
    }

    $(".questions ul").on("click", "li", function (ev) {
        clearQuesitonStyle();
        $(this).attr("class", "qc-show");
        currentQuestionId = $(this).data("qid");
    });

    function clearCountyStyle() {
        $(".county ul li").attr("class", "");
    }

    $(".county ul").on("click", "li", function (ev) {
        clearCountyStyle();
        $(this).attr("class", "qc-show");
        currentCountyId = $(this).data("cid");
    });

    function ajaxGetData() {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: 'system/getDataByQuestionIdOrCountyId',
            data: {'questionid': currentQuestionId, 'countyid': currentCountyId},
            success: function (result) {
                alert(result.msg);
            },
            error: function (data) {
                layer.alert("出现异常！");
            }
        });
    }
    ajaxGetData();
</script>
</html>
