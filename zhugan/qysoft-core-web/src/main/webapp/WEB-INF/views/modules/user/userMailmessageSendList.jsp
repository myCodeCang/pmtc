<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>站内信管理</title>
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
		<li class="active"><a href="${ctx}/user/userMailmessage/send">发件箱列表</a></li>
		<shiro:hasPermission name="user:userMailmessage:edit"><li><a href="${ctx}/user/userMailmessage/sendform">写信</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userMailmessage" action="${ctx}/user/userMailmessage/send" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发送人：</label>
				<form:input path="fromMemberName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>消息标题：</label>
				<form:input path="messageTitle" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>添加时间：</label>
				<input name="beginAddtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userMailmessage.beginAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endAddtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userMailmessage.endAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/><input id="btnSubmit" class="btn btn-primary" type="reset" value="重置" onclick="window.location.href='${ctx}/user/userMailmessage/send';" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
					
					
				<th>发送人</th>
					
					
				<th>消息标题</th>
					
					
				<th>添加时间</th>
					
		
				<shiro:hasPermission name="user:userMailmessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userMailmessage">
			<tr>
				<td>
					${userMailmessage.id}
				</td>
				<td>
					${userMailmessage.fromMemberName}
				</td>
				<td>
					${userMailmessage.messageTitle}
				</td>
				<td>
					<fmt:formatDate value="${userMailmessage.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="user:userMailmessage:edit"><td>
    				<a href="${ctx}/user/userMailmessage/receform?id=${userMailmessage.id}">查看</a>
					<a href="${ctx}/user/userMailmessage/senddelete?id=${userMailmessage.id}" onclick="return confirmx('确认要删除该站内信吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>