<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>舆情分析管理</title>
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
		<li class="active"><a href="${ctx}/user/userMoneyReport/">舆情分析列表</a></li>
		<%--<shiro:hasPermission name="user:userMoneyReport:edit"><li><a href="${ctx}/user/userMoneyReport/form">舆情分析添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="userMoneyReport" action="${ctx}/user/userMoneyReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
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
					
				<th>产币数</th>
					
				<th>会员转账(入)</th>

				<th>会员转账(出)</th>
					
				<th>团队内转账</th>

				<th>跨团队转账</th>
					
				<%--<th>盈亏</th>--%>

				<%--<th>更新时间</th>--%>
					
				<%--<th>备注</th>--%>
					<%----%>
					<%----%>
				<%--<shiro:hasPermission name="user:userMoneyReport:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userMoneyReport">
			<tr>

				<td>
					${userMoneyReport.userName}
				</td>
				<td>
					${userMoneyReport.sumMoney2}
				</td>
				<td>
					${userMoneyReport.sumMoney3}
				</td>
				<td>
					${userMoneyReport.sumMoney4}
				</td>
				<td>
					${userMoneyReport.sumMoney7}
				</td>
				<td>
					${userMoneyReport.sumMoney8}
				</td>
				<%--<td>--%>
					<%--${userMoneyReport.sumMoney7}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<fmt:formatDate value="${userMoneyReport.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userMoneyReport.remarks}--%>
				<%--</td>--%>
				<%--<shiro:hasPermission name="user:userMoneyReport:edit"><td>--%>
    				<%--<a href="${ctx}/user/userMoneyReport/form?id=${userMoneyReport.id}">修改</a>--%>
					<%--<a href="${ctx}/user/userMoneyReport/delete?id=${userMoneyReport.id}" onclick="return confirmx('确认要删除该舆情分析吗？', this.href)">删除</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>