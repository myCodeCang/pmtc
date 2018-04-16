<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>会员奖金明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/user/userReport/">用户资产报表</a></li>
		<!-- <shiro:hasPermission name="user:userReport:edit"><li><a href="${ctx}/user/userReport/form">会员奖金明细添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="userReport"
		action="${ctx}/user/userReport/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>用户名：</label> <form:input path="userName"
					htmlEscape="false" maxlength="100" class="input-medium" />
			</li>
			<li><label>月份: </label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userReport.createDate}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>用户名</th>
				<th>真实姓名</th>
				<th>充值金额统计</th>
				<th>提现金额统计</th>
				<th>买入金额统计</th>
				<th>卖出金额统计</th>
				<th>盈亏金额统计</th>
				<th>订货金额统计</th>
				<th>手续费统计</th>
				<th>艺术品资格购买统计</th>
				<th>积分商城统计</th>
				<th>苏陕商城统计</th>
				<th>添加时间</th>
				<!-- <th>备注</th> -->
				<!-- <shiro:hasPermission name="user:userReport:edit"><th>操作</th></shiro:hasPermission> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="userReport">
				<tr>
					<!-- <td><a href="${ctx}/user/userReport/form?id=${userReport.id}"> -->
					<td>${userReport.id}</td>
					<td>${userReport.userName}</td>
					<td>${userReport.userUserinfo.trueName}</td>
					<td>${userReport.dividendOne}</td>
					<td>${userReport.dividendTwo}</td>
					<td>${userReport.dividendThree}</td>
					<td>${userReport.dividendFour}</td>
					<td>${userReport.dividendFive}</td>
					<td>${userReport.dividendSix}</td>
					<td>${userReport.dividendSeven}</td>
					<td>${userReport.dividendEight}</td>
					<td>${userReport.dividendNine}</td>
					<td>${userReport.dividendTen}</td>
					<td><fmt:formatDate value="${userReport.createDate}"
							pattern="yyyy-MM" /></td>
					<!-- <td>${userReport.remarks}</td> -->
					<!-- <shiro:hasPermission name="user:userReport:edit"><td>
    				<a href="${ctx}/user/userReport/form?id=${userReport.id}">修改</a>
					<a href="${ctx}/user/userReport/delete?id=${userReport.id}" onclick="return confirmx('确认要删除该会员奖金明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission> -->
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>