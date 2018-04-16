<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户升级明细表管理</title>
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
		<li class="active"><a href="${ctx}/user/userLevelLog/">用户升级明细表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userLevelLog" action="${ctx}/user/userLevelLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>真实姓名：</label>
				<form:input path="trueName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>添加时间：</label>
				<input name="createDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userLevelLog.createDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userLevelLog.createDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				
				<th>升级方式</th>
				
				<th>真实姓名</th>
				
				<th>之前等级</th>
					
				<th>现在等级</th>
					
				<th>备注</th>
							
				<th>创建日期</th>
						
				<th>更新日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userLevelLog">
			<tr>
				<td>
					${userLevelLog.id}
				</td>
				<td>
					${userLevelLog.userName}
				</td>
				<td>
					${fns:getDictLabel(userLevelLog.updateType, 'qy_update_type', '')}
				</td>
				<td>
					${userLevelLog.trueName}
				</td>
				<td>
					${userLevelLog.oldLevelName}
				</td>
				<td>
					${userLevelLog.newLevelName}
				</td>
				<td>
					${userLevelLog.commt}
				</td>
				
				<td>
					<fmt:formatDate value="${userLevelLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${userLevelLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>