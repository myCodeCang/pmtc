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
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/user/userUserinfo/">会员列表</a></li>
		<shiro:hasPermission name="user:userUserinfo:edit"><li><a href="${ctx}/user/userUserinfo/form">会员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userUserinfo" action="${ctx}/user/userUserinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号：</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>真实姓名：</label>
				<form:input path="trueName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>归属机构：</label>
				<sys:treeselect   id="officeId" name="officeId" value="" labelName="office.name" labelValue=""
								  title="机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>推荐人账号：</label>
				<form:input path="parentName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>注册时间：</label>
				<input name="beginAddtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userUserinfo.beginAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endAddtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userUserinfo.endAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>账号</th>
				<th>手机号</th>
				<th>真实姓名</th>
				<th>昵称</th>
				<th>用户等级</th>
				<th>归属机构</th>
				<th>机构编码</th>
				<th>是否激活</th>
				<th>用户类型</th>
				<th>金币</th>
				<th>消费积分</th>
				<th>苏陕积分</th>
				<%--<th>接点人</th>--%>
				<%--<th>报单中心</th>--%>
				<th>推荐人</th>
				<%--<th>主区业绩</th>--%>
				<%--<th>扩展区业绩</th>--%>
				<%--<th>左右区</th>--%>
				<th>是否报单中心</th>
				<th>创建时间</th>
				<%--<th>更新时间</th>--%>
				<%--<th>最后登录ip</th>--%>
				<%--<th>最后登录时间</th>--%>
				<%--<th>备注</th>--%>
				<shiro:hasPermission name="user:userUserinfo:edit"><th style="padding-right:100px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userUserinfo">
			<tr>
				<td>
					${userUserinfo.id}
				</td>
				<td><a href="${ctx}/user/userUserinfo/form?id=${userUserinfo.id}">
					${userUserinfo.userName}
				</a></td>
				<td>
					${userUserinfo.mobile}
				</td>
				<td>
					${userUserinfo.trueName}
				</td>
				<td>
					${userUserinfo.userNicename}
				</td>
				<td>
					${userUserinfo.userLevel.levelName}
				
				</td>
				<td>
						${userUserinfo.office.name}
				</td>
				<td>
						${userUserinfo.office.code}
				</td>
				<td>
					${fns:getDictLabel(userUserinfo.userStatus, 'yes_no', '')}
					
				</td>
				<td>
					${fns:getDictLabel(userUserinfo.userType, 'qy_user_type', '')}
					
				</td>
				<td>
					${userUserinfo.money}
				</td>
				<td>
					${userUserinfo.score}
				</td>
				<td>
					${userUserinfo.money2}
				</td>
				<%--<td>--%>
					<%--${userUserinfo.parentName}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userUserinfo.serverName}--%>
				<%--</td>--%>
				<td>
					${userUserinfo.walterName}
				</td>
				<%--<td>--%>
					<%--${userUserinfo.mainPerformance}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userUserinfo.extendPerformance}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(userUserinfo.positionSite, 'position_site', '')}--%>
				<%--</td>--%>
				<td>
					${fns:getDictLabel(userUserinfo.isUsercenter, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${userUserinfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--<fmt:formatDate value="${userUserinfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${userUserinfo.lastLoginIp}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<fmt:formatDate value="${userUserinfo.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userUserinfo.remarks}--%>
				<%--</td>--%>
				<shiro:hasPermission name="user:userUserinfo:edit"><td>
    				<a href="${ctx}/user/userUserinfo/form?id=${userUserinfo.id}" style="display:inline-block;float: left;" class="layui-btn layui-btn-mini layui-btn-normal">修改</a>
					<a href="${ctx}/user/userUserinfo/delete?id=${userUserinfo.id}" onclick="return confirmx('确认要删除该会员吗？', this.href)" style="display:inline-block;float: left;" class="layui-btn layui-btn-mini layui-btn-normal">删除</a>
					<a href="${ctx}/user/userAddress/list?userName=${userUserinfo.userName}" style="display:inline-block;float: left;" class="layui-btn layui-btn-mini layui-btn-normal">地址</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>