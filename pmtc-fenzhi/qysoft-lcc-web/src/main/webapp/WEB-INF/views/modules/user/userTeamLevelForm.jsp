<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团队等级管理</title>
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
		<li><a href="${ctx}/user/userTeamLevel/">用户等级列表</a></li>
		<li class="active"><a href="${ctx}/user/userTeamLevel/form?id=${userTeamLevel.id}">用户等级<shiro:hasPermission name="user:userTeamLevel:edit">${not empty userTeamLevel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="user:userTeamLevel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userTeamLevel" action="${ctx}/user/userTeamLevel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		

		<%--<div class="control-group">--%>
			<%--<label class="control-label">团队分析人数条件：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="directPeopleNum" htmlEscape="false" maxlength="11" class="input-xlarge required"/>--%>
				<%--<span class="user-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">团队总人数条件：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="teamPeopleNum" htmlEscape="false" maxlength="11" class="input-xlarge required"/>--%>
				<%--<span class="user-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">等级代码：</label>
			<div class="controls">
				<form:input path="teamCode" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
		<label class="control-label">等级名称：</label>
		<div class="controls">
		<form:input path="teamName" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
		<span class="user-inline"><font color="red">*</font> </span>
		</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户持币量：</label>
			<div class="controls">
				<input name ="conditionOne"  value="${userTeamLevel.conditionOne}" type="number"  htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">首次直推奖励：</label>
			<div class="controls">
				<form:input path="directEarnings" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">每日复利奖励：</label>
			<div class="controls">
				<form:input path="everyEarnings" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代数奖奖励：</label>
			<div class="controls">
				<form:input path="indirectEarnings" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领导奖直推人数条件：</label>
			<div class="controls">
				<form:input path="directPeopleNum" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领导奖代数：</label>
			<div class="controls">
				<form:input path="indirectLevelno" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领导奖比例：</label>
			<div class="controls">
				<form:input path="compoundInterest" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="user-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">管理奖收益：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="indirectEarnings" htmlEscape="false" maxlength="255" class="input-xlarge required"/>--%>
				<%--<span class="user-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>

		<%--<div class="control-group">--%>
			<%--<label class="control-label">领导奖代数：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="indirectLevelno" htmlEscape="false" maxlength="11" class="input-xlarge required"/>--%>
				<%--<span class="user-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">备注：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="message" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">备注信息：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="user:userTeamLevel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>