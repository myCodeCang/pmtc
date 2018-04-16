<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>

<title>行情图表</title>
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/bootstrap-select/bootstrap-select.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/team/documents.css">

<style>
    .counter{
        display: flex;
        display: -webkit-flex;
        display: -moz-flex;
        display: -o-flex;
        align-items:center;
        -webkit-align-items:center;
        -moz-align-items:center;
        -o-align-items:center;
    }
    @media screen and (max-width: 480px){
        .panel-body{
             padding-right: 5px;
             padding-left:5px;
        }
    }
</style>

<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="widget-content padding-20">
                    <div class="tab-content">
                        <div class="ct-chart tab-pane active" id="scoreLineToDay"></div>
                        <div class="ct-chart tab-pane" id="scoreLineToWeek"></div>
                        <div class="ct-chart tab-pane" id="scoreLineToMonth"></div>
                    </div>
                    <div id="productOptionsData" class="text-center">
                        <div class="row no-space">

                            <div class="col-lg-3 col-sm-6 col-xs-12">
                                <div class="counter">
                                    <div class=" col-xs-1"></div>
                                    <div class="counter-label col-xs-5">实时行情</div>
                                    <div class="counter-number-group text-truncate col-xs-5">
                                        <span class="counter-number-related red-600">+</span>
                                        <span class="counter-number" style="color:#FF6347">${endMoney}</span>
                                    </div>
                                    <div class=" col-xs-1"></div>
                                </div>
                            </div>


                            <div class="col-lg-3 col-sm-6 col-xs-12">
                                <div class="counter">
                                    <div class=" col-xs-1"></div>
                                    <div class="counter-label col-xs-5">周成交量</div>
                                    <div class="counter-number-group text-truncate col-xs-5">
                                        <span class="counter-number-related green-600">-</span>
                                        <span class="counter-number">0</span>
                                    </div>
                                    <div class=" col-xs-1"></div>
                                </div>
                            </div>


                            <div class="col-lg-3 col-sm-6 col-xs-12">
                                <div class="counter">
                                    <div class=" col-xs-1"></div>
                                    <div class="counter-label col-xs-5">月成交量</div>
                                    <div class="counter-number-group text-truncate col-xs-5">
                                        <span class="counter-number-related green-600">-</span>
                                        <span class="counter-number">0</span>
                                    </div>
                                    <div class=" col-xs-1"></div>
                                </div>
                            </div>


                            <div class="col-lg-3 col-sm-6 col-xs-12">
                                <div class="counter">
                                    <div class=" col-xs-1"></div>
                                    <div class="counter-label col-xs-5">24H成交量</div>
                                    <div class="counter-number-group text-truncate col-xs-5">
                                        <span class="counter-number-related red-600">+</span>
                                        <span class="counter-number">0</span>
                                    </div>
                                    <div class=" col-xs-1"></div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <%--<ul class="nav nav-pills nav-pills-rounded chart-action">--%>
                    <%--<li class="active">--%>
                        <%--<a data-toggle="tab" href="#scoreLineToDay">本日</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a data-toggle="tab" href="#scoreLineToWeek">本周</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a data-toggle="tab" href="#scoreLineToMonth">本月</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
                <div class="col-ms-12 col-xs-12 col-md-12" id="ecommerceChartView" style="padding: 0;">
                    <div class="widget-header">
                        <ul class="nav nav-pills nav-pills-rounded product-filters margin-bottom-20" style="display: flex;display:-webkit-flex;justify-content:space-around;-webkit-justify-content: space-around;">
                            <li id="day">
                                <a href="" onclick="toFindData('day')" data-toggle="tab">日K</a>
                            </li>
                            <li id="week">
                                <a href="" onclick="toFindData('week')" data-toggle="tab">周K</a>
                            </li>
                            <li id="month">
                                <a href="" onclick="toFindData('month')" data-toggle="tab">月K</a>
                            </li>
                        </ul>
                        <div id="main" style="width: 100%;height: 400px">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function toFindData(type) {
        window.location.href="/f/user/echarts?type="+type;
    }
    if(${type=="day"}){
        $("#day").addClass("active");
    }
    if(${type=="week"}){
        $("#week").addClass("active");
    }
    if(${type=="month"}){
        $("#month").addClass("active");
    }

</script>
<script>
    var date = new Array();
    <c:forEach items="${priceList}" var="log">
        date.push(['<fmt:formatDate value="${log.createDate}" pattern="yyyy/MM/dd"/>',${log.startMoney},${log.endMoney},${log.lowMoney},${log.higMoney}]);
    </c:forEach>
    var myChart = echarts.init(document.getElementById('main'));

    // 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
    var data0 = splitData(date);

    function splitData(rawData) {
        var categoryData = [];
        var values = []
        for (var i = 0; i < rawData.length; i++) {
            categoryData.push(rawData[i].splice(0, 1)[0]);
            values.push(rawData[i])
        }
        return {
            categoryData: categoryData,
            values: values
        };
    }

    function calculateMA(dayCount) {
        var result = [];
        for (var i = 0, len = data0.values.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += data0.values[i - j][1];
            }
            result.push(sum / dayCount);
        }
        return result;
    }



    option = {
        title: {
            text: '实时行情',
            left: 0
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (data0) {
                var res = data0[0].seriesName + ' ' + data0[0].name;
                res += '<br/>  开盘 : ' + data0[0].data[1] + '  最高 : ' + data0[0].data[4];
                res += '<br/>  闭盘 : ' + data0[0].data[2] + '  最低 : ' + data0[0].data[3];
                return res;
            }
        },
        legend: {
            data: ['日K']
        },
        grid: {
            left: '10%',
            right: '10%',
            bottom: '15%'
        },
        xAxis: {
            type: 'category',
            data: data0.categoryData,
            scale: true,
            boundaryGap : false,
            axisLine: {onZero: false},
            splitLine: {show: false},
            splitNumber: 20,
            min: 'dataMin',
            max: 'dataMax'
        },
        yAxis: {
            scale: true,
            splitArea: {
                show: true
            }
        },
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series: [
            {
                name: '日期',
                type: 'candlestick',
                barWidth : 10,
                data: data0.values,
                markPoint: {
                    label: {
                        normal: {
                            formatter: function (param) {
                                return param != null ? Math.round(param.value) : '';
                            }
                        }
                    },
                    data: [
                        {
                            name: 'XX标点',
                            coord: ['2013/5/31', 2300],
                            value: 2300,
                            itemStyle: {
                                normal: {color: 'rgb(41,60,85)'}
                            }
                        }
                    ],
                    tooltip: {
                        formatter: function (param) {
                            return param.name + '<br>' + (param.data.coord || '');
                        }
                    }
                },
                markLine: {
                    symbol: ['none', 'none'],
                    data: [
                        // [
                        //     {
                        //         name: 'from lowest to highest',
                        //         type: 'min',
                        //         valueDim: 'lowest',
                        //         symbol: 'circle',
                        //         symbolSize: 10,
                        //         label: {
                        //             normal: {show: false},
                        //             emphasis: {show: false}
                        //         }
                        //     },
                        //     {
                        //         type: 'max',
                        //         valueDim: 'highest',
                        //         symbol: 'circle',
                        //         symbolSize: 10,
                        //         label: {
                        //             normal: {show: false},
                        //             emphasis: {show: false}
                        //         }
                        //     }
                        // ],
                        {
                            name: 'min line on close',
                            type: 'min',
                            valueDim: 'close'
                        },
                        {
                            name: 'max line on close',
                            type: 'max',
                            valueDim: 'close'
                        }
                    ]
                }
            },
            {
                name: 'MA5',
                type: 'line',
                data: calculateMA(5),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA10',
                type: 'line',
                data: calculateMA(10),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: calculateMA(20),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA30',
                type: 'line',
                data: calculateMA(30),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },

        ]
    };



    myChart.setOption(option);
</script>

<script src="${ctxStatic}/qysoftui/vendor/bootstrap-select/bootstrap-select.min.js"></script>


<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/qysoftui/js/examples/pages/team/documents.js" data-deps="app"></script>

