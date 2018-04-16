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
				<a class="close" href="javascript:void(0);" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="section doc_section">
				<div class="doc_main_wrap">
					<div class="mod mod_setting">
						<div class="mod_hd">
							<div class="crumb_nav">
								<a href="${ctx}/center">设置页面</a> &gt; 绑定银行卡
							</div>
						</div>
						<div class="mod_bd p_all_30">
							<div class="alert alert_orange m_b_30"> 您正在为账户<span class="font_orange">UID:(${userinfo.userName})${userinfo.trueName}</span> 绑定银行卡
							</div>
							<form action="${ctx}/user/myBankCardEdit" method="post" class="bind_validator" validator="true" novalidate="novalidate">
								<input name="pwdSecurityScore" value="9" type="hidden">
								<div class="group">
									<div class="col_1  col_text  align_right">持卡人姓名</div>
									<div class="col_2">
										<input class="input_text" id="bname" name="bname" type="text" value="${userbank.bankUserName? '':userinfo.trueName}" readonly="readonly">
										<input class="input_text"  name="bankId" type="hidden" value="${bankId}" readonly="readonly">
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1  col_text  align_right">开户银行</div>
									<div class="col_2">
										<select name="openBank" id="openBank" class="input_text">
											<c:forEach items="${bankList}" var="userBankWithdraw">

													<option value="${userBankWithdraw.bankCode}" ${userBankWithdraw.bankCode == userbank.bankCode?'selected':''}>${userBankWithdraw.bankName}</option>
												</c:forEach>
										</select>
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1  col_text  align_right">分支行名称</div>

									<div class="col_2">
										<input class="input_text" id="bbranch" name="bbranch" type="text" value="${userbank.bankUserAddress}">
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1  col_text  align_right">银行卡账号</div>

									<div class="col_2">
										<input class="input_text" id="bnum" name="bnum" type="text" value="${userbank.bankUserNum}">
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1  col_text  align_right">资金密码</div>

									<div class="col_2">
										<input class="input_text" id="advPassword" name="advPassword" type="password" value="">
										<div class="v_info"></div>
									</div>
								</div>
								<hr class="hr_s1">
								<div class="group">
									<div class="col_1"></div>
									<div class="col_2">
										<input type="hidden" name="accId" value="">
										<button type="submit" class="btn btn_orange small">确认</button>
										<!--
                    <p class="font_12 font_orange m_t_15">重置资金密码后24小时以内不允许提现、提币！该限制不予人工解除！</p>
                    -->
									</div>
									<br>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="doc_dialog doc_init_dialog  no_close">
					<div class="mod_dialog">
						<div class="mod_hd">
							<span class="icon_wrap"><i class="icon"></i></span>
							<span class="mod_title">
                  
            </span>
							<div class="mod_option">
								<a href="javascript:;" class="close">×</a>
							</div>
						</div>
						<div class="mod_bd">
							<!--
            <div class="p_all_10 ">
    <a class="p_r_10" href="/p/user/securityCenter">安全中心</a><a href="/p/user/security/strategy/index">安全策略</a>
</div>
-->

						</div>
						<div class="mod_ft">

						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		</script>
		<script type="text/javascript" src="${ctxStatic}/themes/basic/script/setting_bankInfo.js"></script>
		<!-- -->
		<div class="footer section">


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