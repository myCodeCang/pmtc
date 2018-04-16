<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
	<title>提现管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/user/userWithdraw/withdrawExport");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/user/userWithdraw/">提现审核</a></li>
</ul>
<form:form id="searchForm" modelAttribute="userWithdraw" action="${ctx}/user/userWithdraw/" method="post"
		   class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>用户账户：</label>
			<form:input path="userName" htmlEscape="false" maxlength="100" class="input-medium"/>
		</li>
		<li><label>记录编号：</label>
			<form:input path="recordcode" htmlEscape="false" maxlength="255" class="input-medium"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th>
		<th>记录编号</th>
		<th>用户账户</th>
		<th>用户姓名</th>

		<th>提现金额</th>
		<th>实际金额</th>
		<th>银行名称</th>
		<th>银行卡号</th>

		<th>银行卡开户名</th>
		<th>开户行地址</th>
		<th>状态</th>
		<th>申请时间</th>
		<th>处理时间</th>
		<th>备注</th>
		<!-- <th>备注</th> -->
		<shiro:hasPermission name="user:userWithdraw:edit">
			<th>操作</th>
		</shiro:hasPermission>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="userWithdraw">
		<tr>
			<td>
					${userWithdraw.id}
			</td>
			<td>
					${userWithdraw.recordcode}
			</td>
			<td>
					${userWithdraw.userName}
			</td>

			<td>
					${userWithdraw.trueName}
			</td>

			<td>
					${userWithdraw.changeMoney}
			</td>
			<td>
					<fmt:formatNumber type="number" value="${userWithdraw.changeMoney - (userWithdraw.changeMoney * fns:getOption('system_user_set','poundage')<2?2:userWithdraw.changeMoney * fns:getOption('system_user_set','poundage'))}
" pattern="0.00" maxFractionDigits="2"/>
			<td>
					${userWithdraw.bankCodeName}
			</td>
			<td>
					${userWithdraw.userBankNum}
			</td>
			<td>
					${userWithdraw.userBankName}
			</td>
			<td>
					${userWithdraw.userBankAddress}
			</td>
			<td>
					${fns:getDictLabel(userWithdraw.status, 'usercenter_type', '')}
			</td>
			<td>
				<fmt:formatDate value="${userWithdraw.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>


			<td>
				<fmt:formatDate value="${userWithdraw.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>

			<td>
					${userWithdraw.commt}
			</td>

			<td>
				<c:if test="${userWithdraw.status==0}">
					<a class="layui-btn layui-btn-mini layui-btn-normal" href="${ctx}/user/userWithdraw/check?id=${userWithdraw.id}"
					   onclick="layui.qyframe.openPromptMsg('提现审核','是否同意','${userWithdraw.userName}',[{'label':'是','value':'1'},{'label':'否','value':'0'}],'1',this.href);return  false;">处理</a>
				</c:if>
				<c:if test="${userWithdraw.status==1}">
					已同意
				</c:if>
				<c:if test="${userWithdraw.status==2}">
					已驳回
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>


<script type="text/javascript">

    layui.use('qyframe', function() {
        var qyframe = layui.qyframe;
    });

</script>
</body>
</html>