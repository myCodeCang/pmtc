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
				<div class="notice_info" style="">&nbsp;
				</div>
				<a class="close" href="#" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="section doc_section">

				<div class="doc_left_bar">
					<dl class="doc_left_menu" id="doc_left_menu">
						<dt data-name="safe" class="cur"><i class="icon_nav_safe"></i>安全中心<i class="icon_gray_arrows"></i></dt>
						<dd class="open">
							<p>
								<a href="${ctx}/center">安全设置</a><i class="icon_gray_arrows_2"></i></p>
							<%--<p>--%>
								<%--<a href="${ctx}/user/loginHistory">安全记录</a><i class="icon_gray_arrows_2"></i></p>--%>
							<p class="cur">
								<a href="${ctx}/user/account">基本信息</a><i class="icon_gray_arrows_2"></i></p>
							<p>
								<a href="${ctx}/user/realnameAuth">身份信息</a><i class="icon_gray_arrows_2"></i></p>
						</dd>
					</dl>
				</div>

				<div class="doc_main_wrap">

					<div class="basic_info">
						<h4><i class="top_icons"></i> ${userinfo.trueName}</h4>
						<ul>
							<li class="one clear_fix">
								<div class="float_left m_r_15">

									<i class="icon-basic-acc"></i>
									<span class="basic-acc"></span>
									<c:if test="${empty userinfo.userEmail}">
										<a target="_blank" href="${ctx}/user/bindEmail">绑定邮箱</a>
									</c:if>
									${userinfo.userEmail}
								</div>
								<div class="float_left">
									<i class="icon-basic-tel"></i>
									<span class="basic-acc">${userinfo.mobile}</span>

								</div>
							</li>

							<li><span class="basic-col">UID</span><span>${userinfo.userName}</span></li>
							<li><span class="basic-col">登录密码</span>
								<a target="_blank" href="${ctx}/user/changeLoginPwd">重置</a>
							</li>

							<li><span class="basic-col">资金密码</span>

								<a target="_blank" href="${ctx}/user/resetAssetPwd">重置</a>
							</li>
							<li class="font_12"> &nbsp; <span class="font_14"> &nbsp;</span> </li>
						</ul>
					</div>

				</div>

			</div>
		</div>

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