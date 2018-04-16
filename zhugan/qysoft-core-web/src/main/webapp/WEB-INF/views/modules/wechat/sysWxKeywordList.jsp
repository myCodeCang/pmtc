<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关键字回复管理</title>
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
		<li class="active"><a href="${ctx}/wechat/sysWxKeyword/">关键字回复列表</a></li>
		<shiro:hasPermission name="user:sysWxKeyword:edit"><li><a href="${ctx}/wechat/sysWxKeyword/form">关键字回复添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysWxKeyword" action="${ctx}/wechat/sysWxKeyword/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>关键字：</label>
				<form:input path="keyword" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>关键字</th>
				<th>回复内容</th>
				<shiro:hasPermission name="user:sysWxKeyword:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysWxKeyword">
			<tr>
				<td><a href="${ctx}/wechat/sysWxKeyword/form?id=${sysWxKeyword.id}">
						${sysWxKeyword.keyword}
				</a></td>
				<td>${sysWxKeyword.reply}</td>
				<shiro:hasPermission name="user:sysWxKeyword:edit"><td>
    				<a href="${ctx}/wechat/sysWxKeyword/form?id=${sysWxKeyword.id}">修改</a>
					<a href="${ctx}/wechat/sysWxKeyword/delete?id=${sysWxKeyword.id}" onclick="return confirmx('确认要删除该关键字回复吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>