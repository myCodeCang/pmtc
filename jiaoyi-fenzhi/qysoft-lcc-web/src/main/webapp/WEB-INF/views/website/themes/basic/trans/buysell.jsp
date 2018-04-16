<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
	<title>${fns:getOption("system_trans","trans_name")}交易平台| ${fns:getOption("system_trans","trans_name")}</title>
	<%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>
	<script src="${ctxStatic}/themes/basic/script/buysell.js?v=2.4"></script>
	<link href="${ctxStatic}/themes/basic/css/page_center.css" rel="stylesheet" media="screen">
</head>


<body>
<!-- 头部开始 -->
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/top.jsp" %>
<!-- 头部结束 -->
		<style>
			<!-- .xalert {
				padding: 20px;
				background-color: #FDEDDB;
			}
			
			.xalert p {
				line-height: 30px;
			}
			
			-->
		</style>
		<div id="doc_body">
			<div class="section head_notice" id="head_notice">
				<div class="notice_info" style="display: none;">&nbsp;
				</div>
				<a class="close" href="#" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="etc_activity_01 etc_activity_02 etc_activity" id="etc_activity">
				<div class="section">
					<a href="/etc" target="_blank"></a>
					<i id="etc_activity_close"></i>
				</div>
			</div>

			<div class="section doc_section">
				<!-- 左侧开始 -->
				<%@ include file="/WEB-INF/views/website/themes/basic/layouts/transLeft.jsp" %>
				<!-- 左侧结束 -->

				<div class="doc_main_wrap">
					<div class="panel_wrap" id="panel_wrap">
						<div class="panel_wrap_head">
							<div class="wrap_head_left">
								<div class="wrap_tabs">
									<a href="javascript:void(0);" class="cur">买入/卖出</a>
								</div>
								<div class="wrap_option">
								</div>
							</div>
							<div class="wrap_head_right">
								<div class="wrap_tabs" style="width: 5px">
									<a>&nbsp;</a>
								</div>
								<div class="wrap_option">
									&nbsp;
								</div>
							</div>
						</div>

						<div class="trade_panel">
							<form action="/api/webTradeOff/doBuy.jhtml" class="form_trade" data-trade-type="buy" method="post" autocomplete="off">
								<input type="hidden" id="guaranty" value="${guaranty}">
								<input type="hidden" id="transMultiple" value="${transMultiple}">

								<div class="panel_head">
									<div class="available_bar">
										当前价格 <span class="available font_buy" id="cny_cny_a_panel">￥${nowMoney}</span> <input type="hidden" id="currPrice" name="currPrice" value="${nowMoney}"><span class="font_buy">CNY</span>

									</div>
								</div>
								<div class="panel_body">

									<div class="group lp_show">
										<label class="input_unit size_full align_right">
                    <input type="text" class="input_text " id="buyQty" name="buyQty" placeholder="" maxlength="13" autocomplete="off">
                    <span class="unit">买入量 ${fns:getOption("system_trans","trans_name")}</span>
                </label>
									</div>
									<div class="group mp_show">
										<label class="input_unit size_full align_right">
                    <input type="text" class="input_text" name="market_transaction_amount" placeholder="" maxlength="12" autocomplete="off">
                    <span class="unit">交易额 CNY</span>
                </label>
									</div>
									<div class="group lp_show">
										<label class="input_unit size_full align_left">
                    <input class="input_text " id="buyCaptchaCode" name="buyValidateCode" placeholder="验证码" maxlength="13" autocomplete="off" type="text" style="text-align:left;">
                     <span class="unit" >
                                                <sys:validateWebCode name="validateCode" />
						 </a></span>
                </label>
									</div>
									<div class="group trade_pwd_group" style=" display:none; ">
										<input type="password" class="input_text size_full" name="trade_pwd" placeholder="资金密码" autocomplete="off" data-type="*" data-msg-null="请输入资金密码">
									</div>

									<div class="trade_amount">
										<div class="lp_show">
											<span class="label">交易额</span>
											<span>CNY</span>
											<span class="transaction_amount" id="totalBuy">0.00</span>
										</div>

									</div>
									<div class="trade_msg" data-msg-submit=""></div>
									<div class="trade_button">
										<input type="hidden" value="" name="_form_uniq_id">
										<button type="button" id="btnBuy" class="btn btn_buy size_full">买入${fns:getOption("system_trans","trans_name")}</button>
									</div>

								</div>
							</form>

							<!--加充值入口-->
							<div class="panel_deposit p_t_5">
							</div>
						</div>

						<div class="trade_panel">
							<form action="/api/webTradeOff/doSell.jhtml" class="form_trade" data-trade-type="sell" method="post" autocomplete="off">
								<div class="panel_head">
									<div class="available_bar ">
										当前${fns:getOption("system_trans","trans_name")}量 <span class="available font_sell" id="cny_btc_a_panel">${userinfo.money}</span> <span class="font_sell">${fns:getOption("system_trans","trans_name")}</span>

									</div>
								</div>
								<div class="panel_body">

									<div class="group lp_show">
										<label class="input_unit size_full align_right">
                    <input type="text" class="input_text " id="saleQty" name="saleQty" placeholder="" maxlength="13" autocomplete="off">
                    <span class="unit">卖出量 ${fns:getOption("system_trans","trans_name")}</span>
                    <span class="max_amount">
                        最大卖出量 <b>0.00</b>
                    </span>
                </label>
									</div>
									<div class="group lp_show" style="display:none">
										<label class="input_unit size_full">
                    <select class="input_text" name="bankId" id="bankId">
                    </select>
                </label>
										<div class="align_right font_grayDDD relative">
											没有收款帐户？
											<a href="${ctx}/user/myBankCard">点此设置</a>
										</div>

									</div>
									<div class="group lp_show">
										<label class="input_unit size_full align_left">
                    <input class="input_text " id="saleCaptchaCode" name="saleCaptchaCode" placeholder="验证码" maxlength="13" autocomplete="off" type="text" style="text-align:left;">
                     <span class="unit" >
                                                <img <sys:validateWebCode name="validateCode2"  imageCssStyle='width:60;height:20'/>
						 </a></span>
                </label>
									</div>

									<div class="group mp_show">
										<label class="input_unit size_full align_right">
                    <input type="text" class="input_text " name="market_amount" placeholder="" maxlength="13" autocomplete="off">
                    <span class="unit">卖出量 ${fns:getOption("system_trans","trans_name")}</span>
                </label>
									</div>
									<div class="trade_amount">
										<div class="lp_show">
											<span class="label">交易额</span>
											<span>CNY</span>
											<span class="transaction_amount" id="totalSale">0.00</span>
										</div>
									</div>
									<div class="trade_msg" data-msg-submit=""></div>
									<div class="trade_button">
										<input type="hidden" value="" name="_form_uniq_id">
										<button type="button" id="btnSale" class="btn btn_sell size_full">卖出${fns:getOption("system_trans","trans_name")}</button>
									</div>
									<div class="panel_foot">
										<div class="align_right font_grayDDD relative">
											没有收款帐户？
											<a href="${ctx}/user/myBankCard">点此设置</a>
										</div>
									</div>
								</div>

							</form>
						</div>

						<div class="orders" id="market">
							<div class="order_head">
								<div class="float_left">
									买卖盘
									<span id="tmp_overview">

               </span>
								</div>
								<div class="float_right">

								</div>
							</div>
							<div class="order_body">
								<ul class="buy_5" id="tmp_depth">
									<li class="">
										<span class="cell_1  ">买</span>
										<span class="cell_2  ">${nowMoney}</span>
										<span class="cell_3  ">${buyNowNum}</span>
									</li>
									<li class="">
										<span class="cell_1  ">卖</span>
										<span class="cell_2  ">${nowMoney}</span>
										<span class="cell_3  ">${sellNowNum}</span>
									</li>
								</ul>

							</div>

						</div>

					</div>
					<div class="mod mod_fold" id="mod_order">
						<div class="xalert">
							${fns:getOption("system_trans","trans_rule")}

						</div>

					</div>

				</div>
			</div>
		</div>

		<script type="text/javascript">
			window.COIN = 'LCC';
		</script>
		<!--<script type="text/javascript" src="../script/buysell.js?v=1.2"></script>-->
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