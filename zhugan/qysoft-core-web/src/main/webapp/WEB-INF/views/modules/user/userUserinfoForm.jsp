<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
		<li><a href="${ctx}/user/userUserinfo/">会员列表</a></li>
		<li class="active"><a href="${ctx}/user/userUserinfo/form?id=${userUserinfo.id}">会员<shiro:hasPermission name="user:userUserinfo:edit">${not empty userUserinfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="user:userUserinfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userUserinfo" action="${ctx}/user/userUserinfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">用户头像：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="avatar" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>

		</div>
		<div class="control-group">
			<label class="control-label">用户编号：</label>
			<div class="controls">
				<form:label path="userName"  htmlEscape="false" maxlength="50" class="input-xlarge ">${userUserinfo.userName}</form:label>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">真实姓名：</label>
			<div class="controls">
				<form:input path="trueName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">昵称：</label>
			<div class="controls">
				<form:input path="userNicename" htmlEscape="false" maxlength="50" class="input-xlarge "  />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录密码：</label>
			<div class="controls">
				<input name="userPass" htmlEscape="false" maxlength="60" class="input-xlarge " type="password"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付密码：</label>
			<div class="controls">
				<input name="bankPassword" htmlEscape="false" maxlength="60" class="input-xlarge " type="password"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户等级:</label>
			<div class="controls">
				<form:select path="userLevelId" class="input-xlarge required">
					<%--<form:option value="" label="请选择"/>--%>
					<form:options items="${userLevels}" itemLabel="levelName" itemValue="levelCode" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idCard" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">性别：</label>
				<div class="controls">
					<form:select path="sex">
						<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>

			</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="userEmail" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>

		<%--<div class="control-group">--%>
			<%--<label class="control-label">是否激活：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select path="userStatus">--%>
					<%--<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">用户类型：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select path="userType">--%>
					<%--<form:options items="${fns:getDictList('qy_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>

		
		<%--<div class="control-group">--%>
			<%--<label class="control-label">接点人：</label>--%>

			<%--<div class="controls">--%>
			    <%--<sys:userinfoSelect id="parentName" title="用户选择" cssClass="required"   value="${userUserinfo.parentName }"/>--%>
			   	<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
			<%--<div class="control-group">--%>
				<%--<label class="control-label">报单中心：</label>--%>
				<%--<div class="controls">--%>
					<%--<sys:userinfoSelect id="serverName" title="用户选择" cssClass="required" value="${userUserinfo.serverName }" isUsercenter="true" disabled="${not empty userUserinfo.id?'disabled':''}"/>--%>
						<%--<span class="help-inline"><font color="red">*</font> </span>--%>
				<%--</div>--%>
			<%--</div>--%>


		<div class="control-group">
			<label class="control-label">推荐人：</label>
			<div class="controls">
				<sys:userinfoSelect id="walterName" title="用户选择" cssClass="" value="${userUserinfo.walterName }" disabled="${not empty userUserinfo.id?'disabled':''}"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属机构：</label>
			<div class="controls">
				<sys:treeselect   id="officeId" name="officeId" value="${userUserinfo.officeId}" labelName="office.name" labelValue="${userUserinfo.office.name}"
								  title="机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信号：</label>
			<div class="controls">
				<form:input path="weichat" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">QQ号：</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="user:userUserinfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>