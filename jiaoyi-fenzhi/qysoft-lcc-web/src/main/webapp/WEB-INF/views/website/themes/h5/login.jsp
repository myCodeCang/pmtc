<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>${fns:getOption("system_trans","trans_name")}交易平台| ${fns:getOption("system_trans","trans_name")}</title>
    <%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>

    <link href="${ctxStatic}/themes/basic/css/login.css" rel="stylesheet" media="screen">

</head>

<body style="height: 100%;width:100%">


<!-- 头部开始 -->
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>
<div id="doc_head">
    <div class="head_masthead">
        <div class="section">

            <h1 class="head_logo"><a href="/" style="width:200px;height:50px"><img src="${fns:getOption('system_sys','site_logo')}" style="width:200px;" alt="交易平台"></a></h1>
            <h2 class="head_title">欢迎登录</h2>
            <div class="float_right head_user_option">
                <a class="option sub_show" href="${ctx}/indexl" style="font-size:14pt;">首页</a>
                <i class="seg sub_show"></i>
            </div>
        </div>
    </div>
</div>
<!-- 头部结束 -->

<div id="doc_login">
    <div class="section">
        <!--登录-->
        <form class="form_login bind_validator" id="bind_validator" method="post" action="${ctx}/login" novalidate="novalidate">
            <div class="tab tab01" id="pwdlogin">
                <h4 class="title">登录</h4>
                <div class="group">
                    <input type="hidden" id="userType" name="userType" value="website">
                    <select name="countryCode" id="countryCode" class="input_text size_full new_country_code">
                        <option value="86">中国大陆(+86)</option>
                        <option value="0044">英国(+0044)</option>
                        <option value="001">美国(+001)</option>
                        <option value="00852">香港(+00852)</option>
                        <option value="886">台灣(+886)</option>
                        <option value="95">缅甸(+95)</option>
                        <option value="65">新加坡(+65)</option>
                    </select>
                </div>
                <div class="group" style="display:none">
							<span class="input_phone">
						  <b class="code write_country_code">+86</b>
					<input type="text" placeholder="请输入手机号码" id="mobileNo" name="mobileNo" class="input_text size_full new_phone_code" autocomplete="off">
					</span>
                </div>
                <p>
                    <input type="text" id="userCode" name="username" class="input_login mail_complete" value="" placeholder="请输入邮箱/手机号码/会员ID" autocomplete="off">
                </p>
                <div class="relative">
                    <div class="mail_complete_list absolute"></div>
                </div>
                <p>
                    <input type="password" name="password" class="input_login" data-type="*" data-msg-null="请输入密码" placeholder="请输入密码">
                </p>
                <p class="relative" id="vcode_p">

                    <input type="text" class="input_login" data-type="*" name="validateCode" data-msg-null="请输入验证码" placeholder="验证码"> <input type="hidden" name="captcha_key" id="captcha_key" value="">
                    <span class="absolute"><a  class="captchaImg">

                    <img <sys:validateWebCode name="validateCode" />
					       </a></span>
                </p>

                <div class="form_tip"></div>
                <p>
                    <button class="btn btn_orange sign_btn" type="submit">登录</button>
                </p>
                <p class="help">
                    <a href="${ctx}/homeForgetPass" >忘记密码？</a>
                    <a class="float_right font_orange" href="${ctx}/register" >免费注册</a>
                </p>
            </div>
        </form>

    </div>
</div>
<!--登录注册相关底部-->
<div id="doc_adv">
    <div class="section">
        <ul>
            <li><b>安全</b>
                <p>银行级SSL安全连接，分布式离线钱包机</p>
            </li>
            <li class="mid"><b>快捷</b>
                <p>不涉及第三方的P2P交易平台</p>
            </li>
            <li class="end"><b>稳定</b>
                <p>三种交易规则，保证${fns:getOption("system_trans","trans_name")}及时稳定到帐</p>
            </li>
        </ul>
    </div>
</div>

<script>
    $(function() {
        /**
         $("#countryCode").change(function(evt){
					$(".write_country_code").html("+"+this.value);
					var areaCode = $("#countryCode").val();
					var mobileCode = $("#mobileNo").val();
					$("#userCode").val(areaCode+ mobileCode);
				});
         $("#mobileNo").blur(function(){
					var areaCode = $("#countryCode").val();
					var mobileCode = $("#mobileNo").val();
					$("#userCode").val(areaCode+ mobileCode);
				});
         **/
    })
</script>
<script type="text/javascript" src="${ctxStatic}/themes/basic/script/login.js?v=1.1"></script>
<!-- -->
<div class="footer section">
    网络技术有限公司 © 2013-2017

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