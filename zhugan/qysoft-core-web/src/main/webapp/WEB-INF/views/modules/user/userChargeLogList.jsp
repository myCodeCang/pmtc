<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员充值管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/user/userChargeLog/userChargeLogExport");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
		});
		function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/user/userChargeLog");
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/user/userChargeLog/">会员充值列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userChargeLog" action="${ctx}/user/userChargeLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>类型</label>
				<form:select path="changeType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('money_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>添加时间：</label>
				<input name="createDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userChargeLog.createDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userChargeLog.createDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page()"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th width="200">记录编号</th>
				<th>充值金额</th>
				<th>之前金额</th>
				<th>之后金额</th>

				<th>充值来源</th>
				<th>备注</th>
				<th>充值类型 </th>
				<th>添加时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userChargeLog">
			<tr>
				<!-- <td><a href="${ctx}/user/userChargeLog/form?id=${userChargeLog.id}"> -->
				<td>
					${userChargeLog.userName}
				</td>
				<td>
					${userChargeLog.recordcode}
				</td>
				<td>
					${userChargeLog.changeMoney}
				</td>
				<td>
					${userChargeLog.beforeMoney}
				</td>
				<td>
						${userChargeLog.afterMoney}
				</td>

				<td>
					${userChargeLog.changeFrom}
					
				</td>
				<td>
					${userChargeLog.commt}
				</td>
				<td>
					${fns:getDictLabel(userChargeLog.changeType, 'money_type', '')}
					
				</td>
				<td>
					<fmt:formatDate value="${userChargeLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <shiro:hasPermission name="user:userChargeLog:edit"><td>
    				<a href="${ctx}/user/userChargeLog/form?id=${userChargeLog.id}">修改</a>
					<a href="${ctx}/user/userChargeLog/delete?id=${userChargeLog.id}" onclick="return confirmx('确认要删除该会员充值吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>