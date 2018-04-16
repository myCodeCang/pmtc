<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>今日行情配置</title>
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
		<li class="active"><a href="${ctx}/user/transPriceDaylog/">行情列表</a></li>
		<shiro:hasPermission name="user:transPriceDaylog:edit"><li><a href="${ctx}/user/transPriceDaylog/form">今日行情添加</a></li></shiro:hasPermission>
	</ul>
	<%--<form:form id="searchForm" modelAttribute="transPriceDaylog" action="${ctx}/user/transPriceDaylog/" method="post" class="breadcrumb form-search">--%>
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
		<%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<%--<ul class="ul-form">--%>
			<%--<li><label>当前价：</label>--%>
				<%--<form:input path="nowMoney" htmlEscape="false" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>开盘价：</label>--%>
				<%--<form:input path="startMoney" htmlEscape="false" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>收盘价：</label>--%>
				<%--<form:input path="endMoney" htmlEscape="false" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>最高价：</label>--%>
				<%--<form:input path="higMoney" htmlEscape="false" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>最低价：</label>--%>
				<%--<form:input path="lowMoney" htmlEscape="false" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>交易量：</label>--%>
				<%--<form:input path="amount" htmlEscape="false" maxlength="11" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>创建时间：</label>--%>
				<%--<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${transPriceDaylog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			<%--</li>--%>
			<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
			<%--<li class="clearfix"></li>--%>
		<%--</ul>--%>
	<%--</form:form>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

					
				<th>id</th>
					
				<%--<th>交易品组编号</th>--%>
					
				<th>当前价</th>
					
				<th>开盘价</th>
					
				<th>收盘价</th>
					
				<th>最高价</th>
					
				<th>最低价</th>
					
				<th>交易量</th>

					
				<th>创建时间</th>
					
				<%--<th>备注</th>--%>
					
				<%--<shiro:hasPermission name="user:transPriceDaylog:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="transPriceDaylog">
			<tr>

				<td>
					${transPriceDaylog.id}
				</td>
				<%--<td>--%>
					<%--${transPriceDaylog.groupId}--%>
				<%--</td>--%>
				<td>
					${transPriceDaylog.nowMoney}
				</td>
				<td>
					${transPriceDaylog.startMoney}
				</td>
				<td>
					${transPriceDaylog.endMoney}
				</td>
				<td>
					${transPriceDaylog.higMoney}
				</td>
				<td>
					${transPriceDaylog.lowMoney}
				</td>
				<td>
					${transPriceDaylog.amount}
				</td>
				<td>
					<fmt:formatDate value="${transPriceDaylog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${transPriceDaylog.remarks}--%>
				<%--</td>--%>
				<%--<shiro:hasPermission name="user:transPriceDaylog:edit"><td>--%>
    				<%--&lt;%&ndash;<a href="${ctx}/user/transPriceDaylog/form?id=${transPriceDaylog.id}">修改</a>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<a href="${ctx}/user/transPriceDaylog/delete?id=${transPriceDaylog.id}" onclick="return confirmx('确认要删除该物品价格记录表吗？', this.href)">删除</a>&ndash;%&gt;--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>