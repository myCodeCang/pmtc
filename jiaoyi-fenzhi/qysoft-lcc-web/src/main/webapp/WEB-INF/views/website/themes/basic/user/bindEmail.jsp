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

	<body style="height: 100%;width:100%">

	<%@ include file="/WEB-INF/views/website/themes/basic/layouts/settingTop.jsp" %>

		<div id="doc_body">
			<div class="section head_notice" id="head_notice">
				<div class="notice_info">&nbsp;
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
								<a href="${ctx}/center">设置页面</a>&gt; 绑定邮箱
							</div>
						</div>
						<div class="mod_bd p_all_30">
							<div class="alert alert_orange m_b_30"> 您正在为账户<span class="font_orange">UID:(${userinfo.userName})${userinfo.trueName}</span>绑定邮箱
							</div>
							<form action="${ctx}/user/bindEmail" method="post" class="bind_validator" validator="true" novalidate="novalidate">
								<div class="group">
									<div class="col_1 col_text align_right">邮箱地址</div>
									<div class="col_2">
										<input name="email" id="email" class="input_text"  type="text ">
                    <div class="group_help "></div>
                    <div class="v_info "></div>
                </div>
            </div>
<div class="group ">
    <div class="col_1 col_text align_right ">验证码</div>
    <div class="col_2 ">
        <input class="input_text " id="captchaCode " name="validateCode" data-msg-null="请填写验证码 " data-type="* " style="width: 134px " type="text ">
		<img <sys:validateWebCode name="validateCode" />
		</a></span>
     
        <div class="v_info "></div>
    </div>
</div>
            <hr class="hr_s1 ">
            <div class="group ">
                <div class="col_1 "></div>
                <div class="col_2 "><button type="submit " class="btn btn_orange small ">确认</button></div>
            </div>
        </form>
    </div>
</div>
        </div>
   </div>
</div>
<script type="text/javascript ">

</script>
<script type="text/javascript" src="${ctxStatic}/themes/basic/script/setting_bindEmail.js"></script>
<div class="footer section ">

   
</div>


<div><div class="sweet-overlay " tabindex="-1 "></div><div class="sweet-alert " tabindex="-1 "><div class="icon error "><span class="x-mark "><span class="line left "></span><span class="line right "></span></span></div><div class="icon warning "> <span class="body "></span> <span class="dot "></span> </div> <div class="icon info "></div> <div class="icon success "> <span class="line tip "></span> <span class="line long "></span> <div class="placeholder "></div> <div class="fix "></div> </div> <div class="icon custom "></div> <h2>Title</h2><p>Text</p><button class="cancel " tabindex="2 ">Cancel</button><button class="confirm " tabindex="1 ">OK</button></div></div></body></html>