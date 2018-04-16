<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片管理</title>
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
		<li class="active"><a href="${ctx}/user/userPhoto/">图片列表</a></li>
		<shiro:hasPermission name="user:userPhoto:edit"><li><a href="${ctx}/user/userPhoto/form">图片添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userPhoto" action="${ctx}/user/userPhoto/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>关键字：</label>
				<form:input path="keyword" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>名称</th>
					
				<th>图片</th>
				<th>关键字</th>
				<th>链接</th>


				<th>备注</th>
					
				<shiro:hasPermission name="user:userPhoto:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userPhoto">
			<tr>

				<td>
						${userPhoto.id}
				</td>
				<td>
					${userPhoto.name}
				</td>
				<td>
					<img src="${userPhoto.photo}" style="height:120px;">
				</td>
				<td>
					${userPhoto.keyword}
				</td>
				<td>
					${userPhoto.url}
				</td>
				<td>
					${userPhoto.remarks}
				</td>
				<shiro:hasPermission name="user:userPhoto:edit"><td>
    				<a href="${ctx}/user/userPhoto/form?id=${userPhoto.id}">修改</a>
					<a href="${ctx}/user/userPhoto/delete?id=${userPhoto.id}" onclick="return confirmx('确认要删除该图片吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>