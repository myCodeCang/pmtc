<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信自定义菜单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
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
		<li><a href="${ctx}/wechat/sysWxMenu/">微信自定义菜单列表</a></li>
		<li class="active"><a href="${ctx}/wechat/sysWxMenu/form?id=${sysWxMenu.id}&parent.id=${sysWxMenuparent.id}">微信自定义菜单<shiro:hasPermission name="wechat:sysWxMenu:edit">${not empty sysWxMenu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wechat:sysWxMenu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysWxMenu" action="${ctx}/wechat/sysWxMenu/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">上级父级菜单编号:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${sysWxMenu.parent.id}" labelName="parent.name" labelValue="${sysWxMenu.parent.name}"
					title="父级菜单编号" url="/wechat/sysWxMenu/treeData" extId="${sysWxMenu.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">菜单名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">菜单类型：</label>
			<div class="controls">
				<%--<form:input path="typeId" htmlEscape="false" maxlength="11" class="input-xlarge required"/>--%>
					<form:select path="typeId" class="input-medium">
						<form:option value="0" label="URL跳转"/>
						<form:option value="1" label="点击事件"/>
					</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">菜单状态（0：隐藏  1：显示）：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="1" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">跳转url：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">菜单事件key：</label>
			<div class="controls">
				<form:input path="eventKey" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">扩展菜单名称：</label>
			<div class="controls">
				<form:input path="extend" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">菜单排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="3" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="user:sysWxMenu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>