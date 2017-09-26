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
        <div id="da1" style="width: 80%;height: 80%;"></div>
    </div>
    <div class="da-item">
        <div id="da2" style="width: 80%;height: 80%;"></div>
    </div>
    <div class="da-item">
        <div id="da3" style="width: 80%;height: 80%;"></div>
    </div>
    <div class="da-item">
        <div id="da4" style="width: 80%;height: 80%;"></div>
    </div>
</div>
</body>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('da1'));
    var myChart2 = echarts.init(document.getElementById('da2'));
    var myChart3 = echarts.init(document.getElementById('da3'));
    var myChart4 = echarts.init(document.getElementById('da4'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '学校学生信息'
        },
        tooltip: {},
        legend: {
            data:['人数']
        },
        xAxis: {
            data: ["合肥一中","合肥二中","合肥三中","合肥四中","合肥五中","合肥六中"]
        },
        yAxis: {},
        series: [{
            name: '人数',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart2.setOption({
        title: {
            text: '学校学生信息'
        },
        roseType: 'angle',
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                data:[
                    {value:235, name:'视频广告'},
                    {value:274, name:'联盟广告'},
                    {value:310, name:'邮件营销'},
                    {value:335, name:'直接访问'},
                    {value:400, name:'搜索引擎'}
                ]
            }
        ]
    });
    myChart3.setOption({
        title: {
            text: '学校学生信息'
        },
        roseType: 'angle',
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                data:[
                    {value:235, name:'视频广告'},
                    {value:274, name:'联盟广告'},
                    {value:310, name:'邮件营销'},
                    {value:335, name:'直接访问'},
                    {value:400, name:'搜索引擎'}
                ]
            }
        ]
    });
    myChart4.setOption(option);
</script>
</html>
