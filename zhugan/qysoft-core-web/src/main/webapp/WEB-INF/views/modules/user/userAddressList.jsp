<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户地址管理</title>
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
		<li><a href="${ctx}/user/userUserinfo">会员管理</a></li>
		<li class="active"><a href="${ctx}/user/userAddress/list?userName=${userAddress.userName}">用户地址列表</a></li>
		<shiro:hasPermission name="user:userAddress:edit"><li><a href="${ctx}/user/userAddress/form?userName=${userAddress.userName}">用户地址添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

					
				<th>编号</th>
					
				<th>用户名</th>

				<th>手机号</th>

				<th>地址</th>
					
				<th>邮编</th>
					
				<th>收货人姓名</th>
					


					
				<shiro:hasPermission name="user:userAddress:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userAddress">
			<tr>

				<td><a href="${ctx}/user/userAddress/form?id=${userAddress.id}">
					${userAddress.id}
				</a></td>
				<td>
					${userAddress.userName}
				</td>
				<td>
						${userAddress.mobile}
				</td>
				<td>
					${userAddress.address}
				</td>
				<td>
					${userAddress.postcode}
				</td>
				<td>
					${userAddress.trueName}
				</td>


				<shiro:hasPermission name="user:userAddress:edit"><td>
    				<a href="${ctx}/user/userAddress/form?id=${userAddress.id}">修改</a>
					<a href="${ctx}/user/userAddress/delete?id=${userAddress.id}" onclick="return confirmx('确认要删除该用户地址吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>