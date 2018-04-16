<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台统计管理</title>
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
		<li class="active"><a href="${ctx}/user/systemReport/">平台统计列表</a></li>
		<%--<shiro:hasPermission name="user:systemReport:edit"><li><a href="${ctx}/user/systemReport/form">平台统计添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="systemReport" action="${ctx}/user/systemReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${systemReport.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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

				<th>交易充值</th>

				<th>矿机充值</th>

				<th>动态充值</th>
					
					
				<th>交易总量</th>
					
				<th>矿机总量</th>
					
				<th>动态总量</th>
					
				<th>投资总量</th>
					
				<th>动态生成</th>
					
				<th>静态生成</th>
					
				<th>投资生成</th>
					
				<th>手续费</th>
					
				<th>外部转入</th>
					
				<th>外部转出</th>
					
					
					
					
					
					
					
					
					
				<th>创建时间</th>
					
					
				<%--<th>update_date</th>--%>
					<%----%>
					<%----%>
				<%--<shiro:hasPermission name="user:systemReport:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="systemReport">
			<tr>

				<td>
					${systemReport.id}
				</td>
				<td>
						${systemReport.dividendEleven}
				</td>
				<td>
						${systemReport.dividendTwelve}
				</td>
				<td>
						${systemReport.dividendThirteen}
				</td>
				<td>
					${systemReport.dividendOne}
				</td>
				<td>
					${systemReport.dividendTwo}
				</td>
				<td>
					${systemReport.dividendThree}
				</td>
				<td>
					${systemReport.dividendFour}
				</td>
				<td>
					${systemReport.dividendFive}
				</td>
				<td>
					${systemReport.dividendSix}
				</td>
				<td>
					${systemReport.dividendSeven}
				</td>
				<td>
					${systemReport.dividendEight}
				</td>
				<td>
					${systemReport.dividendNine}
				</td>
				<td>
					${systemReport.dividendTen}
				</td>
				<td>
					<fmt:formatDate value="${systemReport.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<%--<td>--%>
					<%--<fmt:formatDate value="${systemReport.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
				<%--</td>--%>
				<%--<shiro:hasPermission name="user:systemReport:edit"><td>--%>
    				<%--<a href="${ctx}/user/systemReport/form?id=${systemReport.id}">修改</a>--%>
					<%--<a href="${ctx}/user/systemReport/delete?id=${systemReport.id}" onclick="return confirmx('确认要删除该平台统计吗？', this.href)">删除</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>