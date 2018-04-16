<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报单中心管理管理</title>
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
		<li class="active"><a href="${ctx}/user/userUsercenterVerifyLog/">报单中心管理列表</a></li>
		<!-- <shiro:hasPermission name="user:userUsercenterVerifyLog:edit"><li><a href="${ctx}/user/userUsercenterVerifyLog/form">报单中心管理添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="userUsercenterVerifyLog" action="${ctx}/user/userUsercenterVerifyLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>状态</th>
				<th>申请级别</th>
				<th>报单中心名字</th>
				<th>报单中心地址</th>
				<th>申请时间</th>
				<th>备注</th>
				<!-- <shiro:hasPermission name="user:userUsercenterVerifyLog:edit"><th>操作</th></shiro:hasPermission> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userUsercenterVerifyLog">
		<c:choose>
			<c:when test="${userUsercenterVerifyLog.status != 0}">
					<tr>
						<!-- <td><a href="${ctx}/user/userUsercenterVerifyLog/form?id=${userUsercenterVerifyLog.id}"> -->
						<td><a>
							${userUsercenterVerifyLog.userName}
						</a></td>
							
						<c:choose>
							<c:when test="${userUsercenterVerifyLog.status == 1}">
								<td>
									已同意
								</td>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${userUsercenterVerifyLog.status == 2}">
								<td>
									已驳回
								</td>
							</c:when>
						</c:choose>
						<!-- <td>
							${userUsercenterVerifyLog.status}
						</td> -->
						<td>
							${userUsercenterVerifyLog.level}
						</td>
						<td>
							${userUsercenterVerifyLog.centerName}
						</td>
						<td>
							${userUsercenterVerifyLog.centerAddress}
						</td>
						<td>
							<fmt:formatDate value="${userUsercenterVerifyLog.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${userUsercenterVerifyLog.commt}
						</td>
						<!-- <shiro:hasPermission name="user:userUsercenterVerifyLog:edit"><td>
		    				<a href="${ctx}/user/userUsercenterVerifyLog/form?id=${userUsercenterVerifyLog.id}">修改</a> -->
							<!-- <a href="${ctx}/user/userUsercenterVerifyLog/delete?id=${userUsercenterVerifyLog.id}" onclick="return confirmx('确认要删除该报单中心管理吗？', this.href)">删除</a> -->
						<!-- </td></shiro:hasPermission> -->
					</tr>
			</c:when>
		</c:choose>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>