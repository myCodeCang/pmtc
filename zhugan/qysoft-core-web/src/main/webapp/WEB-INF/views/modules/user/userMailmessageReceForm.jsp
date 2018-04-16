<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信件详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:history.go(-1);">返回列表</a></li>
		<li class="active"><a href="javascript:void(0);"><shiro:hasPermission name="user:userMailmessage:edit">查看</shiro:hasPermission><shiro:lacksPermission name="user:userMailmessage:edit">查看2</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userMailmessage" action="${ctx}/user/userMailmessage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">发送人：</label>
			<div class="controls">
				<form:input path="fromMemberName" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收人：</label>
			<div class="controls">
				<form:input path="toMemberName" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息标题：</label>
			<div class="controls">
				<form:input path="messageTitle" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
				
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">发送时间：</label>
			<div class="controls">
				<input readonly="true" name="addtime" type="text"  maxlength="10"  value="<fmt:formatDate value="${userMailmessage.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息内容：</label>
			<div class="controls">
				<form:textarea path="messageBody" htmlEscape="false" rows="25" class="input-xxlarge required" readonly="true"/>
				
			</div>
		</div>
	</form:form>
</body>
</html>