
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
		<li class="active"><a href="${ctx}/user/userUserinfo/list?isUsercenter=1">报单中心管理列表</a></li>
		<shiro:hasPermission name="user:userCenter:edit"><li><a href="${ctx}/user/userUserinfo/userCenterform">报单中心管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userUserinfo" action="${ctx}/user/userUserinfo/list?isUsercenter=1" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号：</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<!-- <li><label>等级：</label>
				<form:input path="usercenterLevel" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>账号</th>
				<th>手机号</th>
				<th>真实姓名</th>
				<th>昵称</th>
				<th>报单中心</th>
				<th>是否报单中心</th>
				<th>报单中心等级</th>
				<th>报单中心地址</th>
				<th>成为报单中心时间</th>
				<th>备注</th>
				<shiro:hasPermission name="user:userCenter:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userUserinfo">
			<tr>
				<td>
					${userUserinfo.id}
				</td>
				<td>
					${userUserinfo.userName}
				</td>
				<td>
					${userUserinfo.mobile}
				</td>
				<td>
					${userUserinfo.trueName}
				</td>
				<td>
					${userUserinfo.userNicename}
				</td>
				<td>
					${userUserinfo.serverName}
				</td>
				<td>
					${fns:getDictLabel(userUserinfo.isUsercenter, 'yes_no', '')}
				</td>
				<td>
					${userUserinfo.usercenterLevel}
				</td>
				<td>
					${userUserinfo.usercenterAddress}
				</td>
				<td>
					<fmt:formatDate value="${userUserinfo.usercenterAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userUserinfo.remarks}
				</td>
				<shiro:hasPermission name="user:userCenter:edit"><td>
    				
					<c:if test="${userUserinfo.isUsercenter==1}">
					<a href="${ctx}/user/userUserinfo/savecenter?id=${userUserinfo.id}&isUsercenter=0" onclick="return confirmx('确认要关闭该报单中心管理吗？', this.href)">关闭</a>
					<!-- <a href="${ctx}/user/userUserinfo/updateCenterLevel?id=${userUserinfo.id}" onclick="layui.qyframe.openPromptMsg('报单中心升级','升级级别','${userUserinfo.trueName}',[{'label':'一级报单中心','value':'1','select':'1'},{'label':'二级报单中心','value':'2'},{'label':'三级报单中心','value':'3'}],'${userUserinfo.usercenterLevel}',this.href);return  false;">升级</a> -->
					</c:if>
				</td></shiro:hasPermission>
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