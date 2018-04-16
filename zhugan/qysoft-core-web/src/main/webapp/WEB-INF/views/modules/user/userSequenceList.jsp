<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>序列管理管理</title>
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
		<li class="active"><a href="${ctx}/user/userSequence/">序列管理列表</a></li>
		<shiro:hasPermission name="user:userSequence:edit"><li><a href="${ctx}/user/userSequence/form">序列管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userSequence" action="${ctx}/user/userSequence/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>序列名称：</label>
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
				<th>序列名称</th>
					
				<th>序列值</th>
					
				<th>步长</th>

				<th>备注</th>
				<shiro:hasPermission name="user:userSequence:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userSequence">
			<tr>
				<td><a href="${ctx}/user/userSequence/form?id=${userSequence.id}">
					${userSequence.name}
				</a></td>
				<td>
					${userSequence.currentValue}
				</td>
				<td>
					${userSequence.increment}
				</td>
				<td>
					${userSequence.remarks}
				</td>
				<shiro:hasPermission name="user:userSequence:edit"><td>
    				<a href="${ctx}/user/userSequence/form?id=${userSequence.id}">修改</a>
					<a href="${ctx}/user/userSequence/delete?id=${userSequence.id}" onclick="return confirmx('确认要删除该序列管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>