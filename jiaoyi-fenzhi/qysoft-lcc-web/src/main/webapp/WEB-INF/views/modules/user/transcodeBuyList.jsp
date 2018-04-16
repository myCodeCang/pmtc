<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易管理</title>
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
		<li class="active"><a href="${ctx}/user/transcodeBuy/?status=${transcodeBuy.status}&statusBegin=${transcodeBuy.statusBegin}&type=${transcodeBuy.type}">交易列表</a></li>
		<%--<shiro:hasPermission name="user:transcodeBuy:edit"><li><a href="${ctx}/user/transcodeBuy/form">交易添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="transcodeBuy" action="${ctx}/user/transcodeBuy/?status=${transcodeBuy.status}&statusBegin=${transcodeBuy.statusBegin}&type=${transcodeBuy.type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
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
				<th>订单编号</th>

				<th>用户编号</th>

				<th>价格</th>

				<th>出售数量</th>

				<th>剩余数量</th>

				<th>下架数量</th>

				<th>状态</th>

				<th>类型</th>

				<th>发布时间</th>
					
				<%--<th>备注</th>--%>
					
				<%--<shiro:hasPermission name="user:transcodeBuy:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="transcodeBuy">
			<tr>
				<td>
						${transcodeBuy.id}
				</td>
				<td>
						${transcodeBuy.userName}
				</td>
				<td>
						${transcodeBuy.money}
				</td>
				<td>
						${transcodeBuy.sellNum}
				</td>
				<td>
						${transcodeBuy.nowNum}
				</td>
				<td>
						${transcodeBuy.downNum}
				</td>
				<td>
						${fns:getDictLabel(transcodeBuy.status, 'md_status', '')}

				</td>
				<td>
						${fns:getDictLabel(transcodeBuy.type, 'md_type', '')}

				</td>

				<td>
					<fmt:formatDate value="${transcodeBuy.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${transcodeBuy.remarks}--%>
				<%--</td>--%>
				<%--<shiro:hasPermission name="user:transcodeBuy:edit"><td>--%>
    				<%--<a href="${ctx}/user/transcodeBuy/form?id=${transcodeBuy.id}">修改</a>--%>
					<%--<a href="${ctx}/user/transcodeBuy/delete?id=${transcodeBuy.id}" onclick="return confirmx('确认要删除该交易吗？', this.href)">删除</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>