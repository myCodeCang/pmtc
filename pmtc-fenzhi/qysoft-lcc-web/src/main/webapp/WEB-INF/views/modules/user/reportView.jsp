<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台数据统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="##">平台数据统计</a></li>
</ul>
<%--<form:form id="searchForm" modelAttribute="userReport"--%>
		   <%--action="${ctx}/user/hclmReport/hclmReportView/" method="post"--%>
		   <%--class="breadcrumb form-search">--%>
	<%--&lt;%&ndash;<ul class="ul-form">&ndash;%&gt;--%>
		<%--&lt;%&ndash;<li><label>时间：</label>&ndash;%&gt;--%>
			<%--&lt;%&ndash;<input name="createDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"&ndash;%&gt;--%>
				   <%--&lt;%&ndash;value="<fmt:formatDate value="${createDateBegin}" pattern="yyyy-MM-dd"/>"&ndash;%&gt;--%>
				   <%--&lt;%&ndash;onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -&ndash;%&gt;--%>
			<%--&lt;%&ndash;<input name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"&ndash;%&gt;--%>
				   <%--&lt;%&ndash;value="<fmt:formatDate value="${createDateEnd}" pattern="yyyy-MM-dd"/>"&ndash;%&gt;--%>
				   <%--&lt;%&ndash;onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&ndash;%&gt;--%>
		<%--&lt;%&ndash;</li>&ndash;%&gt;--%>
		<%--&lt;%&ndash;<li class="btns"><input id="btnSubmit" class="btn btn-primary"&ndash;%&gt;--%>
								<%--&lt;%&ndash;type="submit" value="查询" /></li>&ndash;%&gt;--%>
		<%--&lt;%&ndash;<li class="clearfix"></li>&ndash;%&gt;--%>
	<%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
<%--</form:form>--%>
<sys:message content="${message}"/>
<style>
	.bochu-one{margin:20px 0;width:100%;}
	.bochu-one>div{padding:0 0.75rem;width:50%;box-sizing: border-box;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;-o-box-sizing: border-box;}
	.bochu-one>div>div{background-color:#0e90d2;border-radius:8px;}
	.bochu-one>div>div>p{margin: 0;padding:0 30px;color:#fff;padding-bottom:15px;}
	.bochu-one>div>div>div{padding: 15px 30px;padding-top:20px;}
	.bochu-one>div>div>div>span{font-size:24px;color:#fff;}

	.bochu{margin-top:20px;width:100%;margin-bottom:20px;}
	.bochu ul{width:100%;}
	.bochu li{margin-bottom:20px;padding:0 0.75rem;padding-right:0.75rem;width:50%;float: left;box-sizing: border-box;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;-o-box-sizing: border-box;}
	.bochu li>div{background-color:#0e90d2;border-radius:8px;}
	.bochu li>div>div{padding: 15px 30px;padding-top:20px;}
	.bochu li>div>p{margin: 0;padding:0 30px;color:#fff;padding-bottom:15px;}
	.bochu li>div>div>span{font-size:24px;color:#fff;}
</style>
<blockquote class="layui-elem-quote" style="height: 20px;">实时持币统计</blockquote>
<div class="bochu">
	<ul>
		<li>
			<div>
				<div><span>交易钱包总额:</span>&nbsp;<span>${hclmReport.dividendOne==null ? '--' : hclmReport.dividendOne}</span></div>
				<p>当前平台所有用户[交易钱包]余额</p>
			</div>
		</li>
		<li>
			<div>
				<div><span>矿机钱包总额:</span>&nbsp;<span>${hclmReport.dividendTwo==null ? '--' : hclmReport.dividendTwo}</span></div>
				<p>当前平台所有用户[矿机钱包]余额</p>
			</div>
		</li>
		<li>
			<div>
				<div><span>动态钱包总额:</span>&nbsp;<span>${hclmReport.dividendThree==null ? '--' : hclmReport.dividendThree}</span></div>
				<p>当前平台所有用户[动态钱包]余额</p>
			</div>
		</li>
		<%--<li>--%>
		<%--<div>--%>
		<%--<div><span>钱包余额:</span>&nbsp;<span>${hclmReport.dividendThree==null ? '--' : hclmReport.dividendThree}</span></div>--%>
		<%--<p>根据所选择的时间范围计算出的总首奖</p>--%>
		<%--</div>--%>
		<%--</li>--%>
		<li>
			<div>
				<div><span>投资钱包总额:</span>&nbsp;<span>${hclmReport.dividendFour==null ? '--' : hclmReport.dividendFour}</span></div>
				<p>当前平台所有用户[投资钱包]余额</p>
			</div>
		</li>
		<li>
			<div>
				<div><span>交易钱包+矿机钱包+动态钱包:</span>&nbsp;<span>${hclmReport.dividendFive==null ? '--' : hclmReport.dividendFive}</span></div>
				<p>当前平台所有用户[交易钱包][矿机钱包][动态钱包]余额总和</p>
			</div>
		</li>
		<li>
		<div>
		<div><span>后台充值总额(交易钱包):</span>&nbsp;<span>${hclmReport.dividendSix==null ? '--' : hclmReport.dividendSix}</span></div>
		<p>当前平台后台充入[交易钱包]总额</p>
		</div>
		</li>
		<li>
		<div>
		<div><span>后台充值总额(矿机钱包):</span>&nbsp;<span>${hclmReport.dividendSeven==null ? '--' : hclmReport.dividendSeven}</span></div>
		<p>当前平台后台充入[矿机钱包]总额</p>
		</div>
		</li>
		<li>
			<div>
				<div><span>后台充值总额(动态钱包):</span>&nbsp;<span>${hclmReport.dividendEight==null ? '--' : hclmReport.dividendEight}</span></div>
				<p>当前平台后台充入[动态钱包]总额</p>
			</div>
		</li>
		<%--<li>--%>
			<%--<div>--%>
				<%--<div><span>平台重消:</span>&nbsp;<span>${hclmReport.dividendSeven==null ? '--' : hclmReport.dividendSeven}</span></div>--%>
				<%--<p>根据所选择的时间范围计算出的平台重消奖励</p>--%>
			<%--</div>--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--<div>--%>
				<%--<div><span>手续费:</span>&nbsp;<span>${hclmReport.dividendTen==null ? '--' : hclmReport.dividendTen}</span></div>--%>
				<%--<p>根据所选择的时间范围计算出的总手续费</p>--%>
			<%--</div>--%>
		<%--</li>--%>

			<%--<li>--%>
			<%--<div>--%>
			<%--<div><span>手续费:</span>&nbsp;<span>${hclmReport.dividendTen==null ? '--' : hclmReport.dividendTen}</span></div>--%>
			<%--<p>根据所选择的时间范围计算出的总手续费统计</p>--%>
			<%--</div>--%>
			<%--</li>--%>
			<%--<li>--%>
			<%--<div>--%>
			<%--<div><span>慈孝基金:</span>&nbsp;<span>${hclmReport.dividendEleven==null ? '--' : hclmReport.dividendEleven}</span></div>--%>
			<%--<p>根据所选择的时间范围计算出的总慈孝基金</p>--%>
			<%--</div>--%>
			<%--</li>--%>
			<%--<li>--%>
			<%--<div>--%>
			<%--<div><span>复消奖励:</span>&nbsp;<span>${hclmReport.dividendTwelve==null ? '--' : hclmReport.dividendTwelve}</span></div>--%>
			<%--<p>根据所选择的时间范围计算出的总复消奖励</p>--%>
			<%--</div>--%>
			<%--</li>--%>
	</ul>
</div>
<%--<blockquote class="layui-elem-quote" style="height: 20px;margin-top: 500px;">余额统计</blockquote>--%>
<%--<div class="bochu-one">--%>
	<%--<div>--%>
		<%--<div>--%>
			<%--<div><span>钱包余额:</span>&nbsp;<span>${hclmReport.dividendNine==null ? '--' : hclmReport.dividendNine}</span></div>--%>
			<%--<p>所有用户我的钱包中的所有余额</p>--%>
		<%--</div>--%>
	<%--</div>--%>
<%--</div>--%>
</body>
</html>