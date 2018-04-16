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
<div id="doc_head">
    <div class="head_masthead">
        <div class="section">

            <h1 class="head_logo"><a href="/" style="width:200px;height:50px"><img src="${fns:getOption('system_sys','site_logo')}" style="width:200px;" alt="交易平台"></a></h1>
            <h2 class="head_title">欢迎注册 </h2>
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
        <div class="tab">
            <div class="Menu_box">
                <ul>
                    <li class="cur"><i class="icon_login_tel"></i>手机注册</li>
                </ul>
            </div>
            <div class="Content_box">
                <div class="box open" id="by_phone">
                    <form action="${ctx}/register" class="bind_validator" novalidate="novalidate">
                        <div class="group">
                            <select name="country" id="country" class="input_text size_full new_country_code">
                                <option value="0">中国大陆(+86)</option>
                                <%--<option value="0044">英国(+0044)</option>--%>
                                <option value="1">美国(+001)</option>
                                <%--<option value="00852">香港(+00852)</option>--%>
                                <%--<option value="886">台灣(+886)</option>--%>
                                <%--<option value="95">缅甸(+95)</option>--%>
                                <%--<option value="65">新加坡(+65)</option>--%>
                            </select>
                        </div>
                        <div class="group">
									<span class="input_phone">
                                  <b class="code write_country_code">+86</b>
                            <input type="text" placeholder="手机号" id="mobileNo" name="mobileNo" class="input_text size_full new_phone_code" autocomplete="off">
                            </span>
                        </div>
                        <div class="group" style="display:none">
                            <input type="text" placeholder="手机号" id="mobile" name="mobile" class="input_text size_full new_phone_code" autocomplete="off">
                        </div>
                        <div class="group">
                            <div class="input_captcha relative">
                                <input type="hidden" name="captcha_key" id="captcha_key" value="">
                                <input type="text" class="input_text size_full" placeholder="验证码" name="validateCode" autocomplete="off" id="captchaCode">
                                <span class="absolute"><a  class="captchaImg">

                                            <img <sys:validateWebCode name="validateCode" />
                                                   </a></span>
                            </div>
                        </div>
                        <div class="group">
                            <div class="input_sms input_sms_s2">
                                <button type="button" class="btn btn_link sms_sender">点击获取</button>
                                <input type="text" name="phoneCode" class="input_text" data-type="*" data-msg-null="请输入验证码" placeholder="短信验证码" autocomplete="off">
                                <input type="hidden" name="pwdSecurityScore" value="9">
                                <span class="resend_box">
                                        <button type="button" class="btn btn_link resend">点击重试</button>
                                    </span>
                            </div>
                        </div>
                        <div class="group">
                            <input type="text" name="idName" id="idName" class="input_text size_full" placeholder="真实姓名">
                        </div>
                        <div class="group">
                            <input type="text" class="input_text size_full" name="idNo" placeholder="身份号码">
                        </div>
                        <input type="hidden" name="type" value="phone">
                        <div class="form_tip"></div>
                        <p class="tips"><span>提示：</span>
                            <font color="red"><strong>默认登录密码为:123123123</strong></font>
                            <br>建议使用本人实名登记的手机号，如果不使用因此而产生的一切风险和损失，本平台不予承担。</p>
                        <p class="yue"><label><input type="checkbox" id="protocol" name="protocol" data-type="*" data-msg-null="请勾选" checked="">我已阅读并同意<a class="agreement" href="javascript:void(0);">《用户协议》</a></label>
                        </p>
                        <button type="submit" class="btn btn_orange sign_btn">注册</button>
                        <p class="help">
                            <a class="font_orange" href="${ctx}/login">直接登录</a>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="agree" style="display: none;">
    <div style="padding: 20px">
        ${fns:getOption("system_user_set","register_agreement")}
    </div>
</div>
<script>
    $(function() {
        $("#countryCode").change(function(evt) {
            $(".write_country_code").html("+" + this.value);
            var areaCode = $("#countryCode").val();
            var mobileCode = $("#mobileNo").val();
            $("#mobile").val(mobileCode);
        });
        $("#mobileNo").blur(function() {
            var areaCode = $("#countryCode").val();
            var mobileCode = $("#mobileNo").val();
            $("#mobile").val(mobileCode);
        });
        $(".agreement").click(function(evt) {
          var content=$('#agree').html();
            evt.preventDefault();
            layer.open({
                type: 1,
                title: false,
                area: ['900px', '550px'],
                shade: 0.8,
                fixed: true,
                closeBtn: 0,
                content: content,
                btn: ['我同意']
            });
        });
    })
</script>
<script type="text/javascript" src="${ctxStatic}/themes/basic/script/register.js?v=1.4"></script>

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