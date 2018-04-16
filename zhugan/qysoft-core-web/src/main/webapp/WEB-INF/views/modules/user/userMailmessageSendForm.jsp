<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>站内信添加</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#messageBody").val("${messageBody}");
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:history.go(-1);">返回列表</a></li>
		<li class="active"><a href="javascript:void(0);"><shiro:hasPermission name="user:userMailmessage:edit">写信</shiro:hasPermission><shiro:lacksPermission name="user:userMailmessage:edit">写信2</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userMailmessage" action="${ctx}/user/userMailmessage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group" style="display: none;">
			<label class="control-label">父消息：</label>
			<div class="controls">
				<form:input path="messageParentId" htmlEscape="false" maxlength="11" class="input-xlarge " value="1"/>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">发件人：</label>
			<div class="controls">
				<form:input path="fromMemberName" htmlEscape="false" maxlength="255" class="input-xlarge required" value="admin"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收人：</label>
			<div class="controls">
				<form:input path="toMemberName" htmlEscape="false" maxlength="255" class="input-xlarge required" value="${toMemberName}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息标题：</label>
			<div class="controls">
				<form:input path="messageTitle" htmlEscape="false" maxlength="255" class="input-xlarge required" value="${title}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息内容：</label>
			<div class="controls">
				<form:textarea id="messageBody" path="messageBody" htmlEscape="false" rows="15" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">发件箱状态：1-&gt;正常，0-&gt;已删除：</label>
			<div class="controls">
				<form:input path="sendboxStatus" htmlEscape="false" maxlength="1" class="input-xlarge " value="1"/>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">收件箱状态：1-&gt;正常，0-&gt;已删除：</label>
			<div class="controls">
				<form:input path="receiveboxStatus" htmlEscape="false" maxlength="1" class="input-xlarge " value="1"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="user:userMailmessage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="发 送"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>