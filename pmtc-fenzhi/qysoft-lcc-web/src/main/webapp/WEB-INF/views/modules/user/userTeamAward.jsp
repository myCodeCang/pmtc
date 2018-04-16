<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团队奖励发放</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

            layui.use('layer', function() {
               var layer = layui.layer;
                if(${message!=null}){
                    layer.msg('${message}', {
                        time: 3000,
                    });
                }
            });

            $("#btnExport").click(function(){
                if($("#sumMoney").val()==""){
                    $("#tip").html("请输入奖励金额")
                    return;

                }                top.$.jBox.confirm("确认要发放奖励吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/user/userUserinfoExPmtc/teamAward");
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/user/userUserinfoExPmtc/findTeamUserList");
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
		});
        function amount(th){
            var regStrs = [
                ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
                ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
                ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
                ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
            ];
            for(var i=0; i<regStrs.length; i++){
                var reg = new RegExp(regStrs[i][0]);
                th.value = th.value.replace(reg, regStrs[i][1]);
            }
        }
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
		<li class="active"><a href="">团队奖励发放</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="userUserinfo" action="${ctx}/user/userUserinfoExPmtc/findTeamUserList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>团队等级: </label>
                <select name="userLevelId">
					<option value="-1">请选择等级</option>
					<c:forEach items="${userLevels}" var="userLevel">
                        <option value="${userLevel.levelCode}" ${userLevel.levelCode == userUserinfo.userLevelId? 'selected' : ''}>${userLevel.levelName}</option>
                    </c:forEach>
                </select>
            </li>
			<li><label>等级人数：</label>
				<input type="text" value="${peopleNum}" readonly="true" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>

			<%--<li><label>注册时间：</label>--%>
				<%--<input name="beginAddtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					   <%--value="<fmt:formatDate value="${userUserinfo.beginAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> ---%>
				<%--<input name="endAddtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					   <%--value="<fmt:formatDate value="${userUserinfo.endAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			<%--</li>--%>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><label>奖金总额：</label>
				<input id="sumMoney" name="sumMoney" onclick="$('#tip').html('')" onKeyUp="amount(this)" type="number">
			</li>
			<li>
				<span id="tip" style="color: red"></span>
			</li>
            <li class="btns">
				<input id="btnExport" class="btn btn-primary" type="button"   value="点击发放奖励"/><span style="color: red">*(奖励会平均发放到该等级下所有用户)</span>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<%--<sys:message content="${message}"/>--%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>账号</th>
				<th>手机号</th>
				<th>真实姓名</th>
				<th>推荐人</th>
				<th>昵称</th>
				<th>用户等级</th>
				<th>团队等级</th>
				<%--<th>用户类型</th>--%>
				<%--<th>归属机构</th>--%>
				<%--<th>机构编码</th>--%>
				<th>是否激活</th>
				<%--<th>团队等级</th>--%>
				<th>交易钱包</th>
				<th>矿机钱包</th>
				<th>动态钱包</th>
				<th>投资钱包</th>
				<th>业绩钱包</th>
				<%--<th>接点人</th>--%>
				<%--<th>报单中心</th>--%>

				<%--<th>主区业绩</th>--%>
				<%--<th>扩展区业绩</th>--%>
				<%--<th>左右区</th>--%>
				<th>创建时间</th>
				<%--<th>升级时间</th>--%>
				<%--<th>更新时间</th>--%>
				<%--<th>最后登录ip</th>--%>
				<%--<th>最后登录时间</th>--%>
				<%--<th>备注</th>--%>
				<%--<shiro:hasPermission name="user:userUserinfo:edit"><th style="padding-right:100px;">操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userUserinfo">
			<tr>
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
						${userUserinfo.walterName}
				</td>
				<td>
					${userUserinfo.userNicename}
				</td>
				<td>
						${userUserinfo.areaId}

				</td>
				<td>
					${userUserinfo.userLevel.levelName}
				</td>
				<%--<td>--%>
						<%--${fns:getDictLabel(userUserinfo.userType, 'qy_user_type', '')}--%>

				<%--</td>--%>
				<%--<td>--%>
						<%--${userUserinfo.office.name}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${userUserinfo.office.code}--%>
				<%--</td>--%>
				<td>
					${fns:getDictLabel(userUserinfo.activeStatus, 'yes_no', '')}

				</td>
				<%--<td>--%>
					<%--${userUserinfo.areaId}--%>
					<%----%>
				<%--</td>--%>
				<td>
					${userUserinfo.money}
				</td>
				<td>
					${userUserinfo.money2}
				</td>
				<td>
					${userUserinfo.money3}
				</td>
				<td>
						${userUserinfo.money4}
				</td>
				<td>
						${userUserinfo.money5}
				</td>
				<%--<td>--%>
					<%--${userUserinfo.parentName}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userUserinfo.serverName}--%>
				<%--</td>--%>

				<%--<td>--%>
					<%--${userUserinfo.mainPerformance}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${userUserinfo.extendPerformance}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(userUserinfo.positionSite, 'position_site', '')}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(userUserinfo.isUsercenter, 'yes_no', '')}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${userUserinfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--<fmt:formatDate value="${userUserinfo.activeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
				<%--</td>--%>
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
				<%--<shiro:hasPermission name="user:userUserinfo:edit"><td>--%>

    				<%--<a href="${ctx}/user/userUserinfo/form?id=${userUserinfo.id}" style="display:inline-block;float: left;" class="layui-btn layui-btn-mini layui-btn-normal">修改</a>--%>
					<%--<a href="${ctx}/user/userUserinfo/delete?id=${userUserinfo.id}" onclick="return confirmx('确认要删除该会员吗？', this.href)" style="display:inline-block;float: left;" class="layui-btn layui-btn-mini layui-btn-normal">删除</a>--%>
					<%--&lt;%&ndash;<a href="${ctx}/user/userAddress/list?userName=${userUserinfo.userName}" style="display:inline-block;float: left;" class="layui-btn layui-btn-mini layui-btn-normal">地址</a>&ndash;%&gt;--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>