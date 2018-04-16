<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易品管理</title>
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
	<form:form id="searchForm" modelAttribute="transGoodsGroup" action="${ctx}/trans/transGoodsGroup/selectTrans" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>名称</th>
				<th>创建日期</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="transGoodsGroup">

			<tr onclick="selectUser(this)" data2="${transGoodsGroup.name}" data="${transGoodsGroup.id}" ondblclick="doubleSelectUser(this)" style="cursor: pointer;" onmouseover="mouseOver(this)" onmouseout="mouseOut(this)" >
			
				<td>
					${transGoodsGroup.id}
				</td>
				<td>				
					${transGoodsGroup.name}
				</td>
				<td>
					<fmt:formatDate value="${transGoodsGroup.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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