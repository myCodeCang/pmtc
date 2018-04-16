<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发布买交易</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		<li><a href="${ctx}/user/transcodeBuy/adminPushBuy">发布买交易</a></li>
		<%--<li class="active"><a href="${ctx}/user/transcodeBuy/form?id=${transcodeBuy.id}">交易<shiro:hasPermission name="user:transcodeBuy:edit">${not empty transcodeBuy.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="user:transcodeBuy:edit">查看</shiro:lacksPermission></a></li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="transcodeBuy" action="${ctx}/user/transcodeBuy/adminPushBuy" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户帐号：</label>
			<div class="controls">
				<sys:userinfoSelect id="userName"  title="用户选择" cssClass="required"  value="${userName}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">求购数量：</label>
			<div class="controls">
				<form:input path="sellNum" type="number" htmlEscape="false" class="input-xlarge required "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="user:transcodeBuy:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="发 布"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>