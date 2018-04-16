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
		<li class="active"><a href="${ctx}/user/systemReport/">用户资产报表</a></li>
		<!-- <shiro:hasPermission name="user:systemReport:edit"><li><a href="${ctx}/user/systemReport/form">会员奖金明细添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="systemReport"
		action="${ctx}/user/systemReport/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<%--<li><label>用户名：</label> <form:input path="userName"--%>
					<%--htmlEscape="false" maxlength="100" class="input-medium" />--%>
			<%--</li>--%>
			<li><label>时间: </label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${systemReport.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
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
				<th>会员钱包总量</th>
				<th>转账(入)</th>
				<th>转账(出)</th>
				<th>保证金</th>
				<th>手续费</th>
				<th>创建时间</th>
				<!-- <th>备注</th> -->
				<!-- <shiro:hasPermission name="user:systemReport:edit"><th>操作</th></shiro:hasPermission> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="systemReport">
				<tr>
					<!-- <td><a href="${ctx}/user/systemReport/form?id=${systemReport.id}"> -->
					<td>${systemReport.id}</td>
					<td>${systemReport.dividendOne }</td>
					<td>${systemReport.dividendTwo}</td>
					<td>${systemReport.dividendThree * -1}</td>
					<td>${systemReport.dividendFour * -1}</td>
					<td>${systemReport.dividendFive * -1}</td>
					<td><fmt:formatDate value="${systemReport.createDate}"
							pattern="yyyy-MM-dd" /></td>
					<!-- <shiro:hasPermission name="user:systemReport:edit"><td>
    				<a href="${ctx}/user/systemReport/form?id=${systemReport.id}">修改</a>
					<a href="${ctx}/user/systemReport/delete?id=${systemReport.id}" onclick="return confirmx('确认要删除该会员奖金明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission> -->
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>