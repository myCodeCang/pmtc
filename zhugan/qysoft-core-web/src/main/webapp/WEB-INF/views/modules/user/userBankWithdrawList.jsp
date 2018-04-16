<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提款银行设置管理</title>
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
		<li class="active"><a href="${ctx}/user/userBankWithdraw/">提款银行设置列表</a></li>
		<shiro:hasPermission name="user:userBankWithdraw:edit"><li><a href="${ctx}/user/userBankWithdraw/form">提款银行设置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userBankWithdraw" action="${ctx}/user/userBankWithdraw/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行代码：</label>
				<form:input path="bankCode" htmlEscape="false" maxlength="50" class="input-medium"/>
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
				<%--<th>最小提现金额</th>--%>
				<%--<th>最大提现金额</th>--%>
				<th>是否参加活动</th>
				<th>说明</th>
				<th>状态是否激活</th>
				<shiro:hasPermission name="user:userBankWithdraw:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userBankWithdraw">
			<tr>
				<td>${userBankWithdraw.id}</td>
				<td><a href="${ctx}/user/userBankWithdraw/form?id=${userBankWithdraw.id}">
					${userBankWithdraw.bankCode}
				</a></td>
				<%--<td>--%>
					<%--${userBankWithdraw.minLength}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userBankWithdraw.maxLength}--%>
				<%--</td>--%>
				<td>
					${fns:getDictLabel(userBankWithdraw.joinActive, 'yes_no', '')}
				</td>
				<td>
					${userBankWithdraw.commt}
				</td>
				<td>
					${fns:getDictLabel(userBankWithdraw.status, 'yes_no', '')}
				</td>
				<shiro:hasPermission name="user:userBankWithdraw:edit"><td>
    				<a href="${ctx}/user/userBankWithdraw/form?id=${userBankWithdraw.id}">修改</a>
					<a href="${ctx}/user/userBankWithdraw/delete?id=${userBankWithdraw.id}" onclick="return confirmx('确认要删除该提款银行设置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>