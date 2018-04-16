<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行管理</title>
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
	<form:form id="searchForm" modelAttribute="userBankCommon" action="${ctx}/user/userBankCommon/selectBankCommen" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行名字：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="searchUserTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>银行编号</th>
				<th>银行代码</th>

				<th>银行名字</th>
				<th>是否开启</th>
				<th>修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userBankCommon">

			<tr onclick="selectUser(this)" data="${userBankCommon.bankCode}" ondblclick="doubleSelectUser(this)" style="cursor: pointer;" onmouseover="mouseOver(this)" onmouseout="mouseOut(this)" >

				<td>${userBankCommon.id}</td>
				<td>${userBankCommon.bankCode}</td>
				<td>
					${userBankCommon.name}
				</td>
				<td>
					${fns:getDictLabel(userBankCommon.status, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${userBankCommon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		function mouseOver(v){
			$(v).find("td").css("background-color","#CCCCCC");
		}
		
		function mouseOut(v){
			$(v).find("td").css("background-color","");
		}
		function selectUser(v){
			$("#searchUserTable").find("tr").removeClass("selected");
			$(v).addClass("selected");
		}
		
		function doubleSelectUser(v){
			$("#searchUserTable").find("tr").removeClass("selected");
			$(v).addClass("selected");
			
			top.$.jBox.getBox().find("button[value='ok']").trigger("click");
		}
		
		
	</script>
</body>
</html>