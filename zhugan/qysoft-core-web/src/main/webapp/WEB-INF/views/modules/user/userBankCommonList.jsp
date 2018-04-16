<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行管理</title>
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
		<li class="active"><a href="${ctx}/user/userBankCommon/">银行列表</a></li>
		<shiro:hasPermission name="user:userBankCommon:edit"><li><a href="${ctx}/user/userBankCommon/form">银行添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userBankCommon" action="${ctx}/user/userBankCommon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
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
				<th>银行名称</th>
				<th>是否开启</th>
				<th>修改时间</th>
				<shiro:hasPermission name="user:userBankCommon:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userBankCommon">
			<tr>
				<td>${userBankCommon.id}</td>
				<td>${userBankCommon.bankCode}</td>
				<td><a href="${ctx}/user/userBankCommon/form?id=${userBankCommon.id}">
					${userBankCommon.name}
				</a></td>
				<td>
					${fns:getDictLabel(userBankCommon.status, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${userBankCommon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="user:userBankCommon:edit"><td>
    				<a href="${ctx}/user/userBankCommon/form?id=${userBankCommon.id}">修改</a>
					<a href="${ctx}/user/userBankCommon/delete?id=${userBankCommon.id}" onclick="return confirmx('确认要删除该银行吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>