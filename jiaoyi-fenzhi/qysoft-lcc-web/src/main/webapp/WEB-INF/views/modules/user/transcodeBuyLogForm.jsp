<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>撮合成功记录管理</title>
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
		<li><a href="${ctx}/user/transcodeBuyLog/">撮合成功记录列表</a></li>
		<li class="active"><a href="${ctx}/user/transcodeBuyLog/form?id=${transcodeBuyLog.id}">撮合成功记录<shiro:hasPermission name="user:transcodeBuyLog:edit">查看</shiro:hasPermission><shiro:lacksPermission name="user:transcodeBuyLog:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="transcodeBuyLog" action="${ctx}/user/transcodeBuyLog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">买家：</label>
			<div class="controls">
				<form:input path="buyUserName" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卖家：</label>
			<div class="controls">
				<form:input path="sellUserName" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">买家订单号：</label>
			<div class="controls">
				<form:input path="buyId" readonly="true" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卖家订单号：</label>
			<div class="controls">
				<form:input path="sellId" htmlEscape="false" readonly="true" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易数量：</label>
			<div class="controls">
				<form:input path="num" htmlEscape="false" readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易单价：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<c:if test="${transcodeBuyLog.status>0}">
			<div class="control-group">
				<label class="control-label">凭证图片：</label>
				<div class="controls">
					<img src="${fns:getOption("system_trans","image_url")}${transcodeBuyLog.images}" style="width: 400px;height: 400px">
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">完成类型:</label>
			<div class="controls">
				<input type="text" readonly value="${fns:getDictLabel(transcodeBuyLog.type,'trans_log_status', '')}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
				<input type="text" readonly value="${fns:getDictLabel(transcodeBuyLog.status,'trans_log_status', '')}">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="addDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${transcodeBuyLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">备注：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<%--<shiro:hasPermission name="user:transcodeBuyLog:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>