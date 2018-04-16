<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇款审核管理</title>
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
		<li class="active"><a href="${ctx}/user/userCharge/">汇款审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userCharge" action="${ctx}/user/userCharge/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户编号：</label>
				<form:input path="userName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>状态</label>
				<form:select path="isDispose" class="input-medium">
					<form:option value="-1" label="全部"/>
					<form:option value="0" label="未处理"/>
					<form:option value="1" label="已处理"/>
				</form:select>
				<%--<form:select path="status" class="input-medium">--%>
					<%--<form:option value="" label="全部"/>--%>
					<%--<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

					
				<th>序号</th>
				<th>记录编号</th>
				<th>用户编号</th>

				<th>用户姓名</th>

				<th>充值金额</th>

				<%--<th>充值类型</th>--%>
					
				<th>银行名称</th>
					
				<th>银行号码</th>
					
				<th>打款人姓名</th>

				<th>打款凭证</th>

				<th>添加时间</th>

				<th>状态</th>

				<th>备注</th>
				<shiro:hasPermission name="user:userCharge:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userCharge">
			<tr>

				<td><a href="${ctx}/user/userCharge/form?id=${userCharge.id}">
					${userCharge.id}
				</a></td>
				<td>
						${userCharge.recordcode}
				</td>
				<td>
					${userCharge.userName}
				</td>
				<td>
					${userCharge.trueName}
				</td>

				<td>
					${userCharge.changeMoney}
				</td>

				<%--<td>--%>
						<%--${fns:getDictLabel(userCharge.changeType, 'money_type', '')}--%>
				<%--</td>--%>
				<td>
					${userCharge.bankName}
				</td>

				<td>
					${userCharge.bankNum}
				</td>
				<td>
					${userCharge.bankUser}
				</td>
				<td>
					<img src="${userCharge.bankImage}" style="height:120px;" onclick="imgShow(${userCharge.id})">
				</td>
				<td>
					<fmt:formatDate value="${userCharge.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>

				<td>
						${fns:getDictLabel(userCharge.status, 'usercenter_type', '')}
				</td>
				<td>
						${userCharge.commt}
				</td>

				<td>

					<c:if test="${userCharge.status==0}">
						<a class="layui-btn layui-btn-mini layui-btn-normal" href="${ctx}/user/userCharge/updetstatus?id=${userCharge.id}" onclick="layui.qyframe.openPromptMsg('汇款审核','是否同意','${userCharge.userName}',[{'label':'是','value':'1'},{'label':'否','value':'0'}],'1',this.href);return  false;">处理</a>
					</c:if>
					<c:if test="${userCharge.status==1}">
						已处理
					</c:if>
					<c:if test="${userCharge.status==2}">
						已处理
					</c:if>
				</td>
				<style>
					.box{width:100%;height:100%;position: fixed;z-index:999;top:0;left:0;display:flex;display:-webkit-flex;justify-content: center;-webkit-justify-content: center;align-items: center;-webkit-align-items: center;background:rgba(0,0,0,0.3);display:none;}
					.box .box-one{width:800px;height:600px;}
					.box-one img{width:100%;}
				</style>
				<div class="box" id="box-img_${userCharge.id}" onclick="imgHide(${userCharge.id})">
					<div class="box-one">
						<img src="${userCharge.bankImage}" />
					</div>
				</div>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		function imgHide(id){
		    $("#box-img_"+id).hide();
		}
        function imgShow(id){
            $("#box-img_"+id).css("display","flex");
        }

        layui.use('qyframe', function() {
            var qyframe = layui.qyframe;
        });

	</script>

</body>
</html>