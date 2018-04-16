<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>会员奖金明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {});
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
		<li class="active"><a href="##">机构资产报表</a></li>
		<!-- <shiro:hasPermission name="user:userReport:edit"><li><a href="${ctx}/user/userReport/form">会员奖金明细添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="userReport"
		action="${ctx}/user/userReport/organList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>机构名称：</label><sys:treeselect   id="office" name="officeArray" value="${Organ.userUserinfo.office.name}" labelName="office.name" labelValue="${user.office.name}"
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-long" allowClear="true" notAllowSelectParent="true" checked="true"/></li>
			<li><label>月份: </label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userReport.createDate}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<form:form id="inputForm" modelAttribute="organ" action="" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">统计人数：</label>
			<div class="controls">
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true" />
				                     
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值金额统计：</label>
			<div class="controls">
				<form:input path="dividendOne" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true" />
				                     
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额统计：</label>
			<div class="controls">
				<form:input path="dividendTwo" htmlEscape="false" class="input-xlarge" readonly="true" />
				                     
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">买入金额统计：</label>
			<div class="controls">
				<form:input path="dividendThree" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />
				                     
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卖出金额统计：</label>
			<div class="controls">
				<form:input path="dividendFour" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />
				                     
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">盈亏金额统计：</label>
			<div class="controls">
				<form:input path="dividendFive" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />
				                     
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订货金额统计：</label>
			<div class="controls">
				<form:input path="dividendSix" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />
				                     
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费统计：</label>
			<div class="controls">
				<form:input path="dividendSeven" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">艺术品资格购买统计：</label>
			<div class="controls">
				<form:input path="dividendEight" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">积分商城统计：</label>
			<div class="controls">
				<form:input path="dividendNine" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">苏陕商城统计：</label>
			<div class="controls">
				<form:input path="dividendTen" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>
		<blockquote class="layui-elem-quote">奖励提成统计</blockquote>
		<div class="control-group">
			<label class="control-label">购买字画提成：</label>
			<div class="controls">
				<form:input path="buyBonus" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">认购字画提成：</label>
			<div class="controls">
				<form:input path="applyBonus" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">手续费提成：</label>
			<div class="controls">
				<form:input path="procedureBonus" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商城消费提成：</label>
			<div class="controls">
				<form:input path="shopBonus" htmlEscape="false" maxlength="8" class="input-xlarge" readonly="true" />

			</div>
		</div>
	</form:form>

</body>
</html>