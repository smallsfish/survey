<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<%@ include file="../../base.jsp"%>
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
    <div class="da-item">
        <div id="da1" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da2" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da3" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da4" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da5" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da6" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da7" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da8" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da9" style="width: 100%;height: 100%;"></div>
    </div>
    <div class="da-item">
        <div id="da10" style="width: 100%;height: 100%;"></div>
    </div>
</div>
</body>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('da1'));
    var myChart2 = echarts.init(document.getElementById('da2'));
    var myChart3 = echarts.init(document.getElementById('da3'));
    var myChart4 = echarts.init(document.getElementById('da4'));
    var myChart5 = echarts.init(document.getElementById('da5'));
    var myChart6 = echarts.init(document.getElementById('da6'));
    var myChart7 = echarts.init(document.getElementById('da7'));
    var myChart8 = echarts.init(document.getElementById('da8'));
    var myChart9 = echarts.init(document.getElementById('da9'));
    var myChart10 = echarts.init(document.getElementById('da10'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option);
    myChart2.setOption(option);
    myChart3.setOption(option);
    myChart4.setOption(option);
    myChart5.setOption(option);
    myChart6.setOption(option);
    myChart7.setOption(option);
    myChart8.setOption(option);
    myChart9.setOption(option);
    myChart10.setOption(option);
</script>
</html>
