<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>${fns:getOption("system_trans","trans_name")}交易平台| ${fns:getOption("system_trans","trans_name")}</title>
    <%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>

    <link href="${ctxStatic}/themes/basic/css/page_setting.css" rel="stylesheet" media="screen">

</head>

<body style="height: 100%;width:100%">
<!-- 头部开始 -->
<div id="doc_head">
    <div class="head_masthead">
        <div class="section">

            <h1 class="head_logo"><a href="/" style="width:200px;height:50px"><img src="${fns:getOption('system_sys','site_logo')}" style="width:200px;" alt="交易平台"></a></h1>
            <h2 class="head_title">找回登录密码</h2>
            <div class="float_right head_user_option">
                <a class="option sub_show" href="${ctx}/indexl" style="font-size:14pt;">首页</a>
                <i class="seg sub_show"></i>
            </div>
        </div>
    </div>
</div>
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
            <div>

                <div id="findPwdStep1">
                    <form action="${ctx}/homeForgetPass" class="findPwdStep_form1" autocomplete="off" novalidate="novalidate">
                        <%--<div class="password_title p_t_30">--%>
                            <%--<ul>--%>
                                <%--<li class="hover"><i class="icon-password-tit">1</i>填写账户名</li>--%>
                                <%--<li><i class="icon-password-tit">2</i>设置登录密码</li>--%>
                                <%--<li><i class="icon-password-tit">3</i>成功</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                        <div class="doc_main_wrap m_auto">
                            <div class="mod mod_setting">
                                <div class="mod_bd p_all_30">
                                    <div class="group font_14">您正通过<span class="font_orange font_14">手机号码</span>找回登录密码。
                                    </div>
                                    <hr class="hr_s1">
                                    <div class="group">
                                        <div class="col_1 col_text  align_right">&nbsp;</div>
                                        <div class="col_2">
                                            <select class="input_text" id="countryCode" name="countryCode">
                                                <option value="86">中国大陆(+86)</option>
                                                <option value="00852">香港(+00852)</option>
                                                <option value="886">台灣(+886)</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="group">
                                        <div class="col_1 col_text  align_right">手机号码</div>
                                        <div class="col_2">
                                            <input name="mobile" id="mobile" placeholder="请输入手机号码" class="input_text" type="text">
                                            <div class="v_info"></div>
                                        </div>
                                    </div>

                                    <div class="group">
                                        <div class="col_1 col_text  align_right">验证码</div>
                                        <div class="col_2">
                                            <input class="input_text" name="validateCode" id="validateCode" autocomplete="off" style="width: 134px" type="text">
                                            <img <sys:validateWebCode name="validateCode" />
                                            </a></span>
                                            <div class="v_info"></div>
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
                                    <%--<div class="group">--%>
                                        <%--<div class="col_1 col_text  align_right">证件类型</div>--%>
                                        <%--<div class="col_2">--%>
                                            <%--<select name="idType" id="idType" class="input_select">--%>
                                                <%--<option value="01">身份证</option>--%>
                                                <%--<option value="02">港澳台证</option>--%>
                                                <%--<option value="03">护照</option>--%>
                                            <%--</select>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="group">--%>
                                        <%--<div class="col_1 col_text  align_right">证件号码</div>--%>
                                        <%--<div class="col_2">--%>
                                            <%--<input name="idNo" id="idNo" class="input_text" placeholder="如果账户未实名认证此项可不填写" type="text">--%>
                                            <%--<div class="v_info"></div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="group">
                                        <div class="col_1 col_text  align_right">密码</div>
                                        <div class="col_2">
                                            <input name="pwd" id="pwd" class="input_text" placeholder="请输入密码" type="password">
                                            <div class="v_info"></div>
                                        </div>
                                    </div>
                                    <div class="group">
                                        <div class="col_1 col_text  align_right">确认密码</div>
                                        <div class="col_2">
                                            <input name="checkPwd" id="checkPwd" class="input_text" placeholder="请确认密码" type="password">
                                            <div class="v_info"></div>
                                        </div>
                                    </div>
                                    <div class="group">
                                        <div class="col_1"></div>
                                        <div class="col_2">
                                            <button type="button" class="btn btn_orange small font_14" onclick="window.location.href='/f/login'">返回</button>
                                            <button id="checkFindPWDInfo1" type="submit" class="btn btn_orange small font_14">找回密码</button>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <%--<div id="findPwdStep2" style="display: none">--%>
                    <%--<form action="/api/webUser/updatePwdByMobile.jhtml" class="findPwdStep_form2" autocomplete="off" novalidate="novalidate">--%>
                        <%--<div class="password_title p_t_30">--%>
                            <%--<ul>--%>
                                <%--<li><i class="icon-password-tit">1</i>填写账户名</li>--%>
                                <%--<li class="hover"><i class="icon-password-tit">2</i>设置登录密码</li>--%>
                                <%--<li><i class="icon-password-tit">3</i>成功</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                        <%--<div class="doc_main_wrap m_auto">--%>
                            <%--<div class="mod mod_setting">--%>
                                <%--<div class="mod_bd p_all_30">--%>
                                    <%--<input name="pwdSecurityScore" value="9" type="hidden">--%>
                                    <%--<div class="group">--%>
                                        <%--<div class="col_1 col_text align_right">新登录密码</div>--%>

                                        <%--<div class="col_2">--%>
                                            <%--<input class="input_text bind_tips password" placeholder="新登录密码" name="password" id="password" type="password">--%>
                                            <%--<div class="v_info"></div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="group">--%>
                                        <%--<div class="col_1  col_ text  align_right">确认新密码</div>--%>
                                        <%--<div class="col_2"><input class="input_text" placeholder="确认新密码" name="confirm_password" id="confirm_password" type="password">--%>
                                            <%--<div class="v_info"></div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <%--<div class="group">--%>
                                        <%--<div class="col_1">--%>
                                            <%--<input type="hidden" id="token" name="token" value="">--%>
                                            <%--<input type="hidden" id="upmobile" name="mobile" value="">--%>
                                        <%--</div>--%>
                                        <%--<div class="col_2">--%>
                                            <%--<button type="button" class="btn btn_orange small font_14" onclick="window.location.href='/userweb/findPwdByMobile.jhtml'">上一步</button>--%>
                                            <%--<button id="checkFindPWDInfo" type="submit" class="btn btn_orange small font_14">下一步</button>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</form>--%>
                <%--</div>--%>
                <%--<div id="findPwdStep3" style="display: none">--%>
                    <%--<div class="password_title p_t_30">--%>
                        <%--<ul>--%>
                            <%--<li><i class="icon-password-tit">1</i>填写账户名</li>--%>
                            <%--<li><i class="icon-password-tit">2</i>设置登录密码</li>--%>
                            <%--<li class="hover"><i class="icon-password-tit">3</i>成功</li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                    <%--<div class="mod_dialog " style="margin:30px auto ">--%>
                        <%--<div class="mod_hd">--%>
                            <%--<span class="icon_wrap"><i class="icon"></i></span>--%>
                            <%--<span class="mod_title">找回密码成功，请重新登录</span>--%>
                        <%--</div>--%>
                        <%--<div class="mod_bd">--%>
                            <%--<a href="/userweb/login.jhtml" class="close">立刻去登录</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                <%--</div>--%>

            </div>
        </div>

    </div>
</div>
<script>
    $(function() {
        /****/
//        $("#countryCode").change(function(evt) {
//            $(".write_country_code").html("+" + this.value);
//            var areaCode = $("#countryCode").val();
//            var mobileCode = $("#mobileNo").val();
//            $("#mobile").val(areaCode + mobileCode);
//        });
//        $("#mobileNo").blur(function() {
//            var areaCode = $("#countryCode").val();
//            var mobileCode = $("#mobileNo").val();
//            $("#mobile").val(areaCode + mobileCode);
//        });

    })
</script>

<script type="text/javascript" src="${ctxStatic}/themes/basic/script/user_findPwdByMobile.js?v=1.3"></script>
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