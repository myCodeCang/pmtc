<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<script type="text/javascript" src="${ctxStatic}/themes/basic/script/echarts.min.js"></script>

<head>
	<title>${fns:getOption("system_trans","trans_name")}交易平台| ${fns:getOption("system_trans","trans_name")}</title>
	<%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/themes/basic/css/layui.css">
	<link href="${ctxStatic}/themes/basic/css/page_center.css" rel="stylesheet" media="screen">

</head>

<script>
	function tabEchart(type) {
		window.location.href="/f/trans/pricedaylog?type="+type;
    }
</script>


<body>
<!-- 头部开始 -->
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/top.jsp" %>
<!-- 头部结束 -->

		<div id="doc_body">
			<div class="section head_notice" id="head_notice">
				<div class="notice_info" style="display: none;">
					<a href="javascript:void(0);" target="_blank">
						<i class="icon_notice_horn"></i> 公告：<span></span>
					</a>
				</div>
				<a class="close" href="#" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="section doc_section">

				<!-- 左侧开始 -->
				<%@ include file="/WEB-INF/views/website/themes/basic/layouts/transLeft.jsp" %>
				<!-- 左侧结束 -->

				<div class="doc_main_wrap">
					<input type="hidden" name="coin_type" value="cny_btc">
					<div class="mod mod_main">
						<div class="mod_hd">
							<ul class="mod_tabs" id="chart_tab">
								<li class="cur" data-sub="kline">
									<a href="javascript:void(0)">${fns:getOption("system_trans","trans_name")}行情图</a>
								</li>
							</ul>
						</div>
						<div class="mod_bd">
							<div class="now_box" id="market_ticker">
								<div class="now_part"><span>实时行情</span> <b id="now_price">￥${nowMoney}</b></div>
								<div class="now_part"><span>周成交量</span> <b id="now_low">￥${weekAmount}</b>
								</div>
								<div class="now_part"><span>月成交量</span> <b id="now_high">￥${monthAmount}</b>
								</div>
								<div class="now_part"><span>24H成交量</span> <b id="now_total">￥${dayAmount}</b></div>
							</div>
							<div style=" display: none" class="tab_sub tab_sub_chart" id="depth_chart">
								<div class="charts_options" style="left: 48%">
									委单深度:
									<a class="cur" data-len="10">10%</a>
								</div>
								<div id="chart_depth"></div>
							</div>
							<div class="tab_sub tab_sub_kline" id="kline_chart">
								<div class="tab_sub_tit">
									<ul class="kline_tabs" id="kline_tab">
										<li class="cur" data-sub="p">
											<a href="javascript:void(0)"><span>•</span>${fns:getOption("system_trans","trans_name")}行情图</a>
										</li>
									</ul>
									<div class="flash_trade">
									</div>
								</div>
								<%--<div class="layui-btn-group" style="width: 100% ;">--%>
									<%--<button class="layui-btn" style="background-color: #bd6b18" onclick="tabEchart('day')">日K</button>--%>
									<%--<button class="layui-btn" style="background-color: #bd6b18" onclick="tabEchart('week')">周K</button>--%>
									<%--<button class="layui-btn" style="background-color: #bd6b18" onclick="tabEchart('month')">月K</button>--%>
								<%--</div>--%>
								<div  class="kline_tab_sub" id="kline_wrap" style="width: 100%">
									<div  id="main" style="height: 850px; ">

									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script type="text/javascript" src="../script/trade.js"></script>
		<div id="doc_foot" class="doc_foot">

			<div class="foot_partner">
				<div class="section" style="display:none">
					<b>友情链接</b>
					<a href="https://www.huobi.com/" target="_blank">比特币</a>
					<a href="https://www.huobi.com/" target="_blank">莱特币</a>
					<a href="https://www.huobi.com/" target="_blank">以太坊</a>
					<a href="http://www.yuncaijing.com" target="_blank">今日股市行情</a>
					<a href="https://qianbao.qukuai.com/" target="_blank">快钱包</a>
					<a href="http://www.hao123.com/bitcoin" target="_blank">hao123比特币</a>

				</div>
			</div>
			<div class="section foot_copyright">
				<div class="float_left">
					<ul class="foot_record clear_fix">
						<li> </li>
						<li>&nbsp;</li>
					</ul>
					<div class="foot_end">
						<ul>
							<li>
								<a rel="nofollow" class="one" href="#" target="_blank"></a>
							</li>
							<li>
								<a rel="nofollow" class="two" href="#" target="_blank"></a>
							</li>
							<li>
								<a rel="nofollow" class="three" href="#" target="_blank"></a>
							</li>
						</ul>
					</div>
				</div>

				<div class="foot_lang" style="display:none">
					&nbsp;
				</div>
			</div>

		</div>
		<div>
			<div class="sweet-overlay" tabindex="-1"></div>
			<div class="sweet-alert" tabindex="-1">
				<div class="icon error"><span class="x-mark"><span class="line left"></span><span class="line right"></span></span>
				</div>
				<div class="icon warning"> <span class="body"></span> <span class="dot"></span> </div>
				<div class="icon info"></div>
				<div class="icon success"> <span class="line tip"></span> <span class="line long"></span>
					<div class="placeholder"></div>
					<div class="fix"></div>
				</div>
				<div class="icon custom"></div>
				<h2>Title</h2>
				<p>Text</p><button class="cancel" tabindex="2">Cancel</button><button class="confirm" tabindex="1">OK</button></div>
		</div>
	</body>

<script>
    var date = new Array();
    <c:forEach items="${priceList}" var="log">
    date.push(['<fmt:formatDate value="${log.createDate}" pattern="yyyy/MM/dd"/>',${log.startMoney},${log.nowMoney},${log.startMoney>log.nowMoney?log.nowMoney:log.startMoney},${log.startMoney>log.nowMoney?log.startMoney:log.nowMoney},${log.amount}]);
    </c:forEach>
    var myChart = echarts.init(document.getElementById('main'));
    // 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
    var data = splitData(date);


    console.log(data);
    //数组处理
    function splitData(rawData) {
        var datas = [];
        var times = [];
        var vols = [];
        var macds = []; var difs = []; var deas = [];
        for (var i = 0; i < rawData.length; i++) {
            datas.push(rawData[i]);
            times.push(rawData[i].splice(0, 1)[0]);
            vols.push(rawData[i][4]);

        }


        return {
            datas: datas,
            times: times,
            vols: vols,
            macds: macds,
            difs: difs,
            deas: deas
        };
    }

    //分段计算
    function fenduans(){
        var markLineData = [];
        var idx = 0; var tag = 0; var vols = 0;
        for (var i = 0; i < data.times.length; i++) {
            //初始化数据
            if(data.datas[i][5] != 0 && tag == 0){
                idx = i; vols = data.datas[i][4]; tag = 1;
            }
            if(tag == 1){ vols += data.datas[i][4]; }
            if(data.datas[i][5] != 0 && tag == 1){
                markLineData.push([{
                    xAxis: idx,
                    yAxis: data.datas[idx][1]>data.datas[idx][0]?(data.datas[idx][3]).toFixed(2):(data.datas[idx][2]).toFixed(2),
                    value: vols
                }, {
                    xAxis: i,
                    yAxis: data.datas[i][1]>data.datas[i][0]?(data.datas[i][3]).toFixed(2):(data.datas[i][2]).toFixed(2)
                }]);
                idx = i; vols = data.datas[i][4]; tag = 2;
            }

            //更替数据
            if(tag == 2){ vols += data.datas[i][4]; }
            if(data.datas[i][5] != 0 && tag == 2){
                markLineData.push([{
                    xAxis: idx,
                    yAxis: data.datas[idx][1]>data.datas[idx][0]?(data.datas[idx][3]).toFixed(2):(data.datas[idx][2]).toFixed(2),
                    value: (vols/(i-idx+1)).toFixed(2)+' M'
                }, {
                    xAxis: i,
                    yAxis: data.datas[i][1]>data.datas[i][0]?(data.datas[i][3]).toFixed(2):(data.datas[i][2]).toFixed(2)
                }]);
                idx = i; vols = data.datas[i][4];
            }
        }
        return markLineData;
    }

    //MA计算公式
    function calculateMA(dayCount) {
        var result = [];
        for (var i = 0, len = data.times.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += data.datas[i - j][1];
            }
            result.push((sum / dayCount).toFixed(2));
        }
        return result;
    }

    var option = {
        title: {
            text: '今日行情',
            left: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line'
            }
        },
        legend: {
            data: ['KLine', 'MA5','MA10','MA20']
        },
        grid: [           {
            left: '8%',
            right: '1%',
            height: '60%'
        },{
            left: '8%',
            right: '1%',
            top: '71%',
            height: '10%'
        }],
        xAxis: [{
            type: 'category',
            data: data.times,

            axisLine: { onZero: false },
            splitLine: { show: false },
            axisLine: { lineStyle: { color: '#777' } },
            axisLabel: {
                formatter: function (value) {
                    return echarts.format.formatTime('MM-dd', value);
                }
            },

            min: 'dataMin',
            max: 'dataMax',

        }, {
            type: 'category',
            gridIndex: 1,
            data: data.times,
            scale: true,

            splitLine: {show: false},
            axisLabel: {show: false},
            axisTick: {show: false},
            axisLine: { lineStyle: { color: '#777' } },
            splitNumber: 20,
            min: 'dataMin',
            max: 'dataMax',

        }],
        yAxis: [{
            scale: true,
            splitArea: {
                show: false
            }
        },{
            gridIndex: 1,
            splitNumber: 3,
            axisLine: {onZero: false},
            axisTick: {show: false},
            splitLine: {show: false},
            axisLabel: {show: true}
        }],
        series: [{
            name: '价格',
            type: 'candlestick',
            barWidth:15,
            data: data.datas,
            itemStyle: {
                normal: {
                    color: '#ef232a',
                    color0: '#14b143',
                    borderColor: '#ef232a',
                    borderColor0: '#14b143'
                }
            }
        }, {
            name: 'MA5',
            type: 'line',
            data: calculateMA(5),
            smooth: true,
            lineStyle: {
                normal: {
                    opacity: 0.5
                }
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
            },{
                name: '成交量',
                type: 'bar',
                xAxisIndex: 1,
                barWidth:15,
                yAxisIndex: 1,
                data: data.vols,
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var colorList;
                            if (data.datas[params.dataIndex][1]>=data.datas[params.dataIndex][0]) {
                                colorList = '#ef232a';
                            } else {
                                colorList = '#14b143';
                            }
                            return colorList;
                        },
                    }
                }
            }
        ]
    };
    myChart.setOption(option);


</script>


</html>