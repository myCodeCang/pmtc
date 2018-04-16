<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>充值银行信息管理</title>
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
		<li class="active"><a href="${ctx}/user/userBankCharge/">充值银行信息列表</a></li>
		<shiro:hasPermission name="user:userBankCharge:edit"><li><a href="${ctx}/user/userBankCharge/form">充值银行信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userBankCharge" action="${ctx}/user/userBankCharge/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行代码：</label>
				<form:input path="bankCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>银行户名：</label>
				<form:input path="userBankName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>银行账号：</label>
				<form:input path="userBankNumber" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>银行编号</th>
				<th>银行代码</th>
				<th>银行户名</th>
				<th>银行卡号</th>
				<%--<th>最小收款金额</th>--%>
				<%--<th>最大收款金额</th>--%>
				<%--<th>外链地址</th>--%>
				<%--<th>是否充值外联</th>--%>
				<th>开户行</th>
				<th>说明</th>
				<th>状态是否激活</th>
				<shiro:hasPermission name="user:userBankCharge:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userBankCharge">
			<tr>
				<td>${userBankCharge.id}</td>
				<td><a href="${ctx}/user/userBankCharge/form?id=${userBankCharge.id}">
					${userBankCharge.bankCode}
				</a></td>
				<td>
					${userBankCharge.userBankName}
				</td>
				<td>
					${userBankCharge.userBankNumber}
				</td>
				<%--<td>--%>
					<%--${userBankCharge.minRechaege}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userBankCharge.maxRechaege}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userBankCharge.outAddress}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(userBankCharge.rechaegeOut, 'yes_no', '')}--%>
				<%--</td>--%>
				<td>
					${userBankCharge.commt}
				</td>
				<td>
						${userBankCharge.remarks}
				</td>
				<td>
					${fns:getDictLabel(userBankCharge.status, 'yes_no', '')}
				</td>
				<shiro:hasPermission name="user:userBankCharge:edit"><td>
    				<a href="${ctx}/user/userBankCharge/form?id=${userBankCharge.id}">修改</a>
					<a href="${ctx}/user/userBankCharge/delete?id=${userBankCharge.id}" onclick="return confirmx('确认要删除该充值银行信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>