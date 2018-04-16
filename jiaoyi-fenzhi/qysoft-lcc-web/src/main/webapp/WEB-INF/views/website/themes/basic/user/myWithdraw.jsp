<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
	<title>${fns:getOption("system_trans","trans_name")}交易平台| ${fns:getOption("system_trans","trans_name")}</title>
	<%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>

	<link href="${ctxStatic}/themes/basic/css/page_center.css" rel="stylesheet" media="screen">

</head>


<body>
<!-- 头部开始 -->
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/top.jsp" %>
<!-- 头部结束 -->

<div id="doc_body">
			<div class="section head_notice" id="head_notice">
				<div class="notice_info">
					&nbsp;
				</div>
				<a class="close" href="#" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="section doc_section">

				<div class="doc_left_bar">

					<dl class="doc_left_menu" id="doc_left_menu">

						<dt data-name="finance" class=" cur "><i class="icon_nav_finance"></i>资产信息 <i class="icon_gray_arrows"></i></dt>
						<dd class=" open ">
							<p>
								<a href="${ctx}/user/myBankCard">我的银行卡</a><i class="icon_gray_arrows_2"></i></p>
							<p >
								<a href="${ctx}/user/myBankCard">我的钱包</a><i class="icon_gray_arrows_2"></i></p>
						</dd>

						<dt data-name="deposit" id="allow_currency" data-allow_cny="1" data-allow_usd="" class=" cur  "><i class="icon_nav_deposit"></i>${fns:getOption('system_trans','trans_name')}转内网 <i class="icon_gray_arrows"></i></dt>
						<dd class=" open ">

							<p class="cur">
								<a href="${ctx}/user/myWithdraw">${fns:getOption("system_trans","trans_name")}转内网</a><i class="icon_gray_arrows_2"></i></p>
							<p class="">
								<a href="${ctx}/user/myWithdrawLog">转内网记录</a><i class="icon_gray_arrows_2"></i></p>
						</dd>
					</dl>

				</div>

				<div class="doc_main_wrap">

					<div class="mod mod_main">
						<div class="mod_hd">
							<ul class="mod_tabs">
								<li class="cur">
									<a href="withdraw_LCC.jhtml">${fns:getOption("system_trans","trans_name")}转内网</a>
								</li>
							</ul>
						</div>
						<div class="mod_bd">
							<div class="alert alert_orange m_t_20">
								<b class="font_orange">温馨提示:</b> 如需将${fns:getOption("system_trans","trans_name")}转内网操作，为了保证能够快速到账，请您正确核对内网的钱包地址。
								<br>
							</div>
							<form action="${ctx}/user/myWithdraw" class="bind_validator" novalidate="novalidate">
								<div class="group">
									&nbsp;
								</div>
								<input name="card_type" value="0" type="hidden">
								<!-- -->
								<div class="group">
									<div class="col_1 align_right">当前余额</div>
									<div class="col_2">
										${userinfo.money}</div>
								</div>
								<!-- -->
								<div class="group">
									<div class="col_1 align_right col_text">可转内网余额</div>
									<div class="col_2">
										${userinfo.money}</div>
								</div>
								<div class="group">
									<div class="col_1 col_text align_right">转内网数量</div>
									<div class="col_2">
										<input name="amount" id="amount" class="input_text" placeholder="转内网数量" value="" type="text">
										<div class="v_info"></div>
									</div>
								</div>
								<%--<div class="group">--%>
									<%--<div class="col_1 col_text align_right">内网钱包地址</div>--%>
									<%--<div class="col_2">--%>
										<%--<input name="caddress" value="${userinfo.usercenterAddress}" class="input_text" placeholder="内网钱包地址" value="" type="text" readonly>--%>
										<%--<div class="v_info"></div>--%>
									<%--</div>--%>
								<%--</div>--%>
								<div class="group">
									<div class="col_1 col_text align_right">内网钱包地址</div>
										<div class="col_2">
											${userinfo.usercenterAddress eq '0'?'请先绑定钱包地址':userinfo.usercenterAddress}
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group" >
									<div class="col_1  align_right">手机号码</div>
									<div class="col_2">
										${userinfo.mobile}<input type="hidden" id="mobile" name="mobile" value="${userinfo.mobile}">
									</div>
								</div>
								<div class="group">
									<div class="col_1 align_right">短信验证码</div>
									<div class="col_2">
										<div class="input_sms sms_resend">
											<input name="verfiyCode" id="verfiyCode" class="input_text" style="width: 100px" data-type="*" data-msg-null="请输入验证码" type="text">

											<span class="resend_box">
												<button type="button" id="sendCode" class="btn btn_link resend">点击获取</button>
											</span>

										</div>
										<div class="group_help" style="display:none">验证码已经发送至您的手机，30分钟内输入有效</div>
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1 col_text align_right">资金密码</div>
									<div class="col_2">
										<input name="advPassword"  id="advPassword" class="input_text" placeholder="资金密码" value="" type="password">
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1 col_text align_right">备注</div>
									<div class="col_2">
										<textarea id="memo" name="memo" class="form-control input-sm" rows="6" cols="50"></textarea>
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1 col_text align_right"></div>
									<div class="col_2">
										<button type="submit" class="btn btn_orange small">提交</button>
									</div>
								</div>
							</form>

						</div>
					</div>

				</div>

			</div>
		</div>
		<script type="text/javascript">
		</script>
<script type="text/javascript" src="${ctxStatic}/themes/basic/script/finance_withdrawCoin.js?v=2.3"></script>
		<!-- -->
		<div id="doc_foot" class="doc_foot">

			<div class="foot_partner">
				<div class="section" style="display:none">
					<b>友情链接</b>
					<a href="https://www.huobi.com/" target="_blank">比特币</a>
					<a href="https://www.huobi.com/" target="_blank">莱特币</a>
					<a href="https://www.huobi.com/" target="_blank">以太坊</a>
					<a href="http://www.yuncaijing.com" target="_blank">今日股市行情</a>
					<a href="https://qianbao.qukuai.com/" target="_blank">快钱包</a>
					<a href="http://www.hao123.com/bitcoin" target="_blank">hao123比特币</a>

				</div>
			</div>
			<div class="section foot_copyright">
				<div class="float_left">
					<ul class="foot_record clear_fix">
						<li> </li>
						<li>&nbsp;</li>
					</ul>
					<div class="foot_end">
						<ul>
							<li>
								<a rel="nofollow" class="one" href="#" target="_blank"></a>
							</li>
							<li>
								<a rel="nofollow" class="two" href="#" target="_blank"></a>
							</li>
							<li>
								<a rel="nofollow" class="three" href="#" target="_blank"></a>
							</li>
						</ul>
					</div>
				</div>

				<div class="foot_lang" style="display:none">
					&nbsp;
				</div>
			</div>

		</div>
		<div>
			<div class="sweet-overlay" tabindex="-1"></div>
			<div class="sweet-alert" tabindex="-1">
				<div class="icon error"><span class="x-mark"><span class="line left"></span><span class="line right"></span></span>
				</div>
				<div class="icon warning"> <span class="body"></span> <span class="dot"></span> </div>
				<div class="icon info"></div>
				<div class="icon success"> <span class="line tip"></span> <span class="line long"></span>
					<div class="placeholder"></div>
					<div class="fix"></div>
				</div>
				<div class="icon custom"></div>
				<h2>Title</h2>
				<p>Text</p><button class="cancel" tabindex="2">Cancel</button><button class="confirm" tabindex="1">OK</button></div>
		</div>
	</body>

</html>