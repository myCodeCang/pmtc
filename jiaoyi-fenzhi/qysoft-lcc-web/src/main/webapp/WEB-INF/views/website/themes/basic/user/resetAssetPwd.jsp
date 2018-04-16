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
                        <a href="${ctx}/center">设置页面</a> &gt; 重置资金密码
                    </div>
                </div>
                <div class="mod_bd p_all_30">
                    <div class="alert alert_orange m_b_30"> 您正在为账户<span
                            class="font_orange">UID:(${userinfo.userName})${userinfo.trueName}</span>重置资金密码
                    </div>

                    <form action="${ctx}/user/resetAssetPwd" method="post" class="bind_validator" validator="true"
                          novalidate="novalidate">
                        <input name="pwdSecurityScore" value="9" type="hidden">
                        <div class="group">
                            <div class="col_1  col_text  align_right">新资金密码</div>

                            <div class="col_2">
                                <input class="input_text bind_tips password" id="npasswd" name="npasswd"
                                       type="password">
                                <div class="v_info"></div>
                            </div>
                        </div>
                        <div class="group">
                            <div class="col_1  col_text  align_right">确认资金密码</div>
                            <div class="col_2"><input class="input_text" id="rpasswd" name="rpasswd" placeholder=""
                                                      data-msg-error="两次密码输入不一致，请重新输入" data-type="compare"
                                                      data-compare="password" data-msg-null="请输入资金密码" type="password">
                                <div class="v_info"></div>
                            </div>
                        </div>
                        <hr class="hr_s1">
                        <div class="group">
                            <div class="col_1  align_right">手机号码</div>
                            <div class="col_2">
                                ${userinfo.mobile}<input type="hidden" id="mobile" name="mobile"
                                                         value="${userinfo.mobile}">
                            </div>
                        </div>
                        <div class="group">
                            <div class="col_1 align_right">手机验证码</div>
                            <div class="col_2">
                                <div class="input_m_code" id="input_m_code">
                                    <input name="mobileCode" id="mobileCode" class="input_text" style="width: 100px" data-type="*" data-msg-null="请输入验证码" type="text">
                                    <button type="button" class="btn mini btn_link sms_sender">获取验证码
                                    </button>
                                </div>
                                <div class="v_info"></div>
                            </div>
                        </div>

                        <div class="group">
                            <div class="col_1"></div>
                            <div class="col_2">
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

<script type="text/javascript" src="${ctxStatic}/themes/basic/script/setting_resetAssetPwd.js?v=1.1
"></script>
<!-- -->
<div class="footer section">


</div>

<div>
    <div class="sweet-overlay" tabindex="-1"></div>
    <div class="sweet-alert" tabindex="-1">
        <div class="icon error"><span class="x-mark"><span class="line left"></span><span
                class="line right"></span></span>
        </div>
        <div class="icon warning"><span class="body"></span> <span class="dot"></span></div>
        <div class="icon info"></div>
        <div class="icon success"><span class="line tip"></span> <span class="line long"></span>
            <div class="placeholder"></div>
            <div class="fix"></div>
        </div>
        <div class="icon custom"></div>
        <h2>Title</h2>
        <p>Text</p>
        <button class="cancel" tabindex="2">Cancel</button>
        <button class="confirm" tabindex="1">OK</button>
    </div>
</div>
</body>

</html>