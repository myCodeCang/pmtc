<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>k线走势管理</title>
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
		<li class="active"><a href="${ctx}/user/transcodePriceDaylog/">k线走势列表</a></li>
		<shiro:hasPermission name="user:transcodePriceDaylog:edit"><li><a href="${ctx}/user/transcodePriceDaylog/form">k线走势添加</a></li></shiro:hasPermission>
	</ul>
	<%--<form:form id="searchForm" modelAttribute="transcodePriceDaylog" action="${ctx}/user/transcodePriceDaylog/" method="post" class="breadcrumb form-search">--%>
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
		<%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<%--<ul class="ul-form">--%>
			<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
			<%--<li class="clearfix"></li>--%>
		<%--</ul>--%>
	<%--</form:form>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>开盘价</th>
				<th>现价</th>
				<th>交易量</th>
				<th>创建时间</th>
				<th>更新时间</th>

				<%--<th>备注</th>--%>
					
				<shiro:hasPermission name="user:transcodePriceDaylog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="transcodePriceDaylog">
			<tr>

				<td>
						${transcodePriceDaylog.startMoney}
				</td>
				<td>
						${transcodePriceDaylog.nowMoney}
				</td>
				<td>
						${transcodePriceDaylog.amount}
				</td>

				<td>
					<fmt:formatDate value="${transcodePriceDaylog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${transcodePriceDaylog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${transcodePriceDaylog.remarks}--%>
				<%--</td>--%>
				<shiro:hasPermission name="user:transcodePriceDaylog:edit"><td>
    				<a href="${ctx}/user/transcodePriceDaylog/form?id=${transcodePriceDaylog.id}">修改</a>
					<%--<a href="${ctx}/user/transcodePriceDaylog/delete?id=${transcodePriceDaylog.id}" onclick="return confirmx('确认要删除该k线走势吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>