<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户账变明细管理</title>
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
		<li class="active"><a href="${ctx}/user/userAccountchange/">用户账变明细列表</a></li>
		<!-- <shiro:hasPermission name="user:userAccountchange:edit"><li><a href="${ctx}/user/userAccountchange/form">用户账变明细添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="userAccountchange" action="${ctx}/user/userAccountchange/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>账变类型</label>
				<form:select path="changeType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('change_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>消费类型</label>
				<form:select path="moneyType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('money_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>账变时间：</label>
				<input name="createDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userAccountchange.createDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userAccountchange.createDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>真实姓名</th>
				<th>账变类型</th>
				<th>消费类型</th>
				<th>账变金额</th>
				<th>账变前金额</th>
				<th>账变后金额</th>
				<th>创建时间</th>
				<th width="220">备注</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userAccountchange">
			<tr>
				<td>
					${userAccountchange.id}
				</td>
				<td>
					${userAccountchange.userName}
				</td>
				<td>
					${userAccountchange.userUserinfo.trueName}
				</td>
				<td>
						${fns:getDictLabel(userAccountchange.changeType, 'change_type', '')}

				</td>
				<td>
						${fns:getDictLabel(userAccountchange.moneyType, 'money_type', '')}

				</td>
				<td>
					${userAccountchange.changeMoney}
				</td>
				<td>
					${userAccountchange.beforeMoney}
				</td>
				<td>
					${userAccountchange.afterMoney}
				</td>
				<td>
					<fmt:formatDate value="${userAccountchange.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userAccountchange.commt}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>