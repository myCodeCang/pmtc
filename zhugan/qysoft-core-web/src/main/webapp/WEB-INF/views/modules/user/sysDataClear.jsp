<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>系统数据清除</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//$("#name").focus();
						$("[name='inputForm']")
								.each(
										function(i, v) {

											$(v)
													.validate(
															{
																submitHandler : function(
																		form) {
																	loading('正在提交，请稍等...');
																	form
																			.submit();
																},
																errorContainer : "#messageBox",
																errorPlacement : function(
																		error,
																		element) {
																	$(
																			"#messageBox")
																			.text(
																					"输入有误，请先更正。");
																	if (element
																			.is(":checkbox")
																			|| element
																					.is(":radio")
																			|| element
																					.parent()
																					.is(
																							".input-append")) {
																		error
																				.appendTo(element
																						.parent()
																						.parent());
																	} else {
																		error
																				.insertAfter(element);
																	}
																}
															});
										});

					});
</script>
<style type="text/css">

</style>
</head>
<body>
	<sys:message content="${message}" />
	<div class="layui-tab-item layui-show">
		<blockquote class="layui-elem-quote">平台基本数据操作</blockquote>
			
				
				<div class="control-group">
				
				<span style="padding-left:5em; padding-right:8.1em;">会员账变明细表：</span>
				        <input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=user_accountchange')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:10.1em;">会员汇款表：</span>
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=user_charge')"/>
				</div>
 				<div class="control-group">
					<span style="padding-left:5em; padding-right:6.1em;">会员充值后台记录表：</span>
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_charge_back_record')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:8em;">会员充值记录表：</span>
						
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_charge_log')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:8em;">会员升级明细表：</span>
						
						<input class="btn btn-primary" type="button" value="清除"  onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_level_log')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:10em;">邮件信息表：</span>
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_mailmessage')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:9em;">会员奖金报表：</span>
						
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_report')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:10em;">报单中心表：</span>
						
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_usercenter_log')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:8em;">会员基本信息表：</span>
						
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_userinfo')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:8em;">会员银行信息表：</span>
						
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_userinfo_bank')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:7.7em;">会员提现/充值表：</span>
						
						<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?','${ctx}/user/sysDataClear/singleTable?name=user_withdraw')"/>
				</div>
				<div class="control-group">
					<span style="padding-left:5em; padding-right:10em;">系统日志表：</span>
					
					<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=sys_log')"/>
				</div>
		<blockquote class="layui-elem-quote">平台交易数据操作</blockquote>

			<div class="control-group">
				<span style="padding-left:5em; padding-right:9.1em;">艺术品订货表：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_apply')"/> 
			</div>

			<div class="control-group">
				<span style="padding-left:5em; padding-right:7.1em;">艺术品订货限制表：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_apply_condition')"/>
			</div>

			<div class="control-group">
				<span style="padding-left:5em; padding-right:2.5em;">艺术品交易汇总表(交易大厅)：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_buy')"/>
			</div>	

			<div class="control-group">
				<span style="padding-left:5em; padding-right:7.1em;">艺术品历史价格表：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_buy_day_trend')"/>
			</div>	

			<div class="control-group">
				<span style="padding-left:5em; padding-right:7.1em;">艺术品成交记录表：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_buy_log')"/>
			</div>	

			<div class="control-group">
				<span style="padding-left:5em; padding-right:11.1em;">艺术品表：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_goods')"/>
			</div>

			<div class="control-group">
				<span style="padding-left:5em; padding-right:9em;">艺术品委托表：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_goods_group')"/>
			</div>

			<div class="control-group">
				<span style="padding-left:5em; padding-right:9em;">艺术品提货表：</span>
	        	<input class="btn btn-primary" type="button" value="清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/singleTable?name=trans_order')"/>
			</div>

		<blockquote class="layui-elem-quote">平台快捷数据操作</blockquote>
			<div class="layui-tab-item layui-show">
				<div class="control-group">
					<span style="padding-left:5em; padding-right:9em;">所有表中数据：</span>
						
						<input class="btn btn-primary" type="button" value="一键清除" onclick="return confirmx('确定要清除吗?', '${ctx}/user/sysDataClear/allTable')"/>
				</div>
			</div>
			
		</div>
	</div>
</div>
	<script type="text/javascript">
		layui.use('element', function() {
			var element = layui.element();
			element.tabChange('optionlayuitab', '${selOptLabel}'); //假设当前地址为：http://a.com#test1=222，那么选项卡会自动切换到“发送消息”这一项
		});
	</script>
</body>
</html>