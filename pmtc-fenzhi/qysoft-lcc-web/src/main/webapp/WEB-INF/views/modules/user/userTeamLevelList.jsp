<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户等级管理</title>
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
		<li class="active"><a href="${ctx}/user/userTeamLevel/">用户等级列表</a></li>
		<shiro:hasPermission name="user:userTeamLevel:edit"><li><a href="${ctx}/user/userTeamLevel/form">用户等级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userTeamLevel" action="${ctx}/user/userTeamLevel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>等级名称：</label>--%>
				<%--<form:input path="teamName" htmlEscape="false" maxlength="255" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>等级代码：</label>
				<form:input path="teamCode" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

					
				<%--<th>编号</th>--%>

				<th>等级代码</th>
				<th>等级名称</th>
				<th>用户持币量</th>
				<th>首次直推奖励</th>
				<th>每日复利</th>
				<th>代数奖</th>
				<th>领导奖直推人数条件</th>
				<th>领导奖代数</th>
				<th>领导奖比例</th>
				<shiro:hasPermission name="user:userTeamLevel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userTeamLevel">
			<tr>

				<%--<td><a href="${ctx}/user/userTeamLevel/form?id=${userTeamLevel.id}">--%>
					<%--${userTeamLevel.id}--%>
				<%--</a></td>--%>




				<td>
						${userTeamLevel.teamCode}
				</td>
				<td>
						${userTeamLevel.teamName}
				</td>
				<td>
						${userTeamLevel.conditionOne}
				</td>
				<td>
					${userTeamLevel.directEarnings}
				</td>
				<td>
					${userTeamLevel.everyEarnings}
				</td>
				<td>
					${userTeamLevel.indirectEarnings}
				</td>
				<td>
					${userTeamLevel.directPeopleNum}
				</td>
				<td>
					${userTeamLevel.indirectLevelno}
				</td>
				<td>
					${userTeamLevel.compoundInterest}
				</td>
				<shiro:hasPermission name="user:userTeamLevel:edit"><td>
    				<a href="${ctx}/user/userTeamLevel/form?id=${userTeamLevel.id}">修改</a>
					<a href="${ctx}/user/userTeamLevel/delete?id=${userTeamLevel.id}" onclick="return confirmx('确认要删除该用户等级吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>