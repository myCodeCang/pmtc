<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
	
	<style type="text/css">
		
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
	</ul>
	<form:form id="searchForm" modelAttribute="userUserinfo" action="${ctx}/user/userUserinfo/selectUserTagList?pageSize=8&isUsercenter=${userUserinfo.isUsercenter}&userLevelId=${userLevelId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号：</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="searchUserTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>账号</th>
				<th>手机号</th>
				<th>真实姓名</th>
				<th>昵称</th>
				<%--<th>用户等级</th>--%>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userUserinfo">
		 
			<tr onclick="selectUser(this)" dataId="${userUserinfo.id}" data="${userUserinfo.userName}" ondblclick="doubleSelectUser(this)" style="cursor: pointer;" onmouseover="mouseOver(this)" onmouseout="mouseOut(this)" >
			
				<td>
					${userUserinfo.id}
				</td>
				<td>				
					${userUserinfo.userName}
				</td>
				<td>
					${userUserinfo.mobile}
				</td>
				<td>
					${userUserinfo.trueName}
				</td>
				<td>
					${userUserinfo.userNicename}
				</td>
				<%--<td>--%>
					<%--${userUserinfo.userLevel.levelName}--%>
				<%----%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${userUserinfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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