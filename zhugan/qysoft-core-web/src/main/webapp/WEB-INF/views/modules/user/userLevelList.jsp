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
		<li class="active"><a href="${ctx}/user/userLevel/">用户等级列表</a></li>

		<li>
			<sys:qyframe id="addLevel" url="${ctx}/user/userLevel/form" width="500" height="500" title="添加等级"></sys:qyframe>
		</li>
		<shiro:hasPermission name="user:userLevel:edit"><li><a href="${ctx}/user/userLevel/form">用户等级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userLevel" action="${ctx}/user/userLevel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>等级名称：</label>
				<form:input path="levelName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>等级代码：</label>
				<form:input path="levelCode" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>编号</th>
				<th>等级名称</th>
				<th>等级代码</th>
				<th>KPI系数</th>
				<%--<th>静态奖励</th>--%>
				<%--<th>量奖百分比</th>--%>
				<%--<th>量奖封顶</th>--%>
				<%--<th>静态奖励封顶</th>--%>
				<%--<th>静态奖励天数</th>--%>
				<%--<th>业绩量</th>--%>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="user:userLevel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userLevel">
			<tr>
			    <td>
					${userLevel.id}
				</td>
				<td><a href="${ctx}/user/userLevel/form?id=${userLevel.id}">
					${userLevel.levelName}
				</a></td>
				<td>
					${userLevel.levelCode}
				</td>
				<td>
					${userLevel.money}
				</td>
				<%--<td>--%>
					<%--${userLevel.staticMoney}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${userLevel.amountPer}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${userLevel.amountCap}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${userLevel.staticMoneyHigh}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${userLevel.staticMoneyDay}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userLevel.performance}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${userLevel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userLevel.remarks}
				</td>
				<shiro:hasPermission name="user:userLevel:edit"><td>
    				<a href="${ctx}/user/userLevel/form?id=${userLevel.id}" class="layui-btn layui-btn-mini layui-btn-normal">修改</a>
					<a href="${ctx}/user/userLevel/delete?id=${userLevel.id}" onclick="return confirmx('确认要删除该用户等级吗？', this.href)" class="layui-btn layui-btn-mini layui-btn-normal">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>