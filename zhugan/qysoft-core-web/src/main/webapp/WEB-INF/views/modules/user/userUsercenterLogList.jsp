<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报单中心审核管理</title>
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
		<li class="active"><a href="${ctx}/user/userUsercenterLog/">报单中心审核列表</a></li>
		<!-- <shiro:hasPermission name="user:userUsercenterLog:edit"><li><a href="${ctx}/user/userUsercenterLog/form">报单中心审核添加</a></li></shiro:hasPermission>-->
	</ul> 
	<form:form id="searchForm" modelAttribute="userUsercenterLog" action="${ctx}/user/userUsercenterLog/" method="post" class="breadcrumb form-search">
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
				<th>编号</th>
				<th>用户名</th>
				<th>状态</th>
				<th>申请级别</th>
				<th>报单中心名字</th>
				<th>报单中心地址</th>
				<th>申请时间</th>
				<th>备注</th>
				<shiro:hasPermission name="user:userUsercenterLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userUsercenterLog">			
				<tr>
					<!-- <td><a href="${ctx}/user/userUsercenterLog/form?id=${userUsercenterLog.id}"> -->
					<td>
						${userUsercenterLog.id}
					</td>
					<td>
						${userUsercenterLog.userName}
					</td>
					<td>
						${fns:getDictLabel(userUsercenterLog.status, 'usercenter_type', '')}
					</td>
					<td>
						${userUsercenterLog.level}
					</td>
					<td>
						${userUsercenterLog.centerName}
					</td>
					<td>
						${userUsercenterLog.centerAddress}
					</td>
					<td>
						<fmt:formatDate value="${userUsercenterLog.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						${userUsercenterLog.commt}
					</td>
					<!-- <shiro:hasPermission name="user:userUsercenterLog:edit"> --><td>
					<c:if test="${userUsercenterLog.status==0}"> 
	    				<a href="${ctx}/user/userUsercenterLog/updetstatus?id=${userUsercenterLog.id}" onclick="layui.qyframe.openPromptMsg('报单中心审核','是否同意','${userUsercenterLog.userName}',[{'label':'是','value':'1'},{'label':'否','value':'0'}],'1',this.href);return  false;">处理</a>
	    			</c:if>
	    			<c:if test="${userUsercenterLog.status==1}"> 
	    				已同意
	    			</c:if>
	    			<c:if test="${userUsercenterLog.status==2}"> 
	    				已驳回
	    			</c:if>
	    			
					</td><!-- </shiro:hasPermission> -->
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	
	

	<script type="text/javascript">
	
	 layui.use('qyframe', function() {
		var qyframe = layui.qyframe;
	 }); 
	 
	</script>
</body>
</html>

