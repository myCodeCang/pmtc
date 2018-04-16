<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>PMTC</title>
    <%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>
    <!-- 自定义 -->
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/css/login.css">
    <!-- 插件 -->
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/animsition/animsition.css">
    <!-- 图标 -->
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/fonts/web-icons/web-icons.css">
    <!-- 插件 -->
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.css">

    <!--login-->
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/css/logincss/component.css">
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/css/logincss/normalize.css">

    <!-- 插件 CSS -->
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/animsition/animsition.css">
    <link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/toastr/toastr.css">
    <style>
        body {
            padding-top: 0;
        }
    </style>
</head>

<body>
<%--<div class="language-change">--%>
<%--<select id="lang" onchange="uploadlang()">--%>
<%--<option value=""><spring:message code="xuanzeyuyan"/></option>--%>
<%--<option value="en_US"><spring:message code="yingwen"/></option>--%>
<%--<option value="zh_CN"><spring:message code="zhongwen"/></option>--%>
<%--</select>--%>
<%--</div>--%>
<% request.setAttribute("language", request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));%>
<div class="container demo-1" style="padding:0;width:100%;">
    <div class="content">
        <div id="large-header" class="large-header" style="overflow-y: scroll;">
            <canvas id="demo-canvas"></canvas>
            <div class="login-detail" id="loginDetail">
                <h3>PMTC</h3>
                <form class="form-horizontal" id="loginForm" method="post">
                    <div class="login-detail-one">
                        <div style="margin:0;"><input id="userName" class="form-control" name="userName" type="text"
                                                      placeholder="会员编号"/></div>
                        <p style="color:#ccc;font-size:12px;">会员编号由7-10位小写字母加数字组成</p>
                        <div><input id="userPass" class="form-control" name="userPass" type="password"
                                    placeholder="登录密码"/></div>
                        <div><input id="userPass2" class="form-control" name="userPass2"
                                                      type="password" placeholder="重复密码"/></div>
                        <div>
                            <input type="text" class="form-control" name="validCode" placeholder="请输入验证码"
                                   style="width:55%;float: left;">
                            <div class="btn btn-block" id="getVerifyCode"
                                 style="width:40%;border:1px solid #ddd;height:41px;line-height:41px;padding:0;font-size:13px;color:#fff;-webkit-border-radius:3px;-moz-border-radius: 3px;border-radius: 3px;float: right;">
                                获取验证码
                            </div>
                        </div>
                        <button type="submit" class="register">确认修改</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<style>
    .login-detail {
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -195px;
        margin-left: -200px;
        background: rgba(0, 0, 0, 0.6);
        border-radius: 6px;
        width: 400px;
        height: 390px;
    }

    @media screen and (min-width: 640px) {
        .login-detail {
            width: 400px;
            margin-left: -200px;
            margin-top: -195px;
            height: 390px;
        }
    }

    @media screen and (min-width: 324px) and (max-width: 639px) {
        .login-detail {
            width: 400px;
            margin-left: -200px;
            margin-top: -195px;
            height: 390px;
        }
    }

    .login-detail h3{height:40px;line-height:40px;text-align: center;color:#fff;font-size:24px;}
    .login-detail .login-detail-one {
        width: 290px;
        margin: 0 auto;
        margin-top: 15px;
    }

    .login-detail .login-detail-one div {
        height: 45px;
        line-height: 45px;
        width: 100%;
        margin-bottom: 10px;
    }

    /*.login-detail .login-detail-one div span{display:inline-block;width:70px;height:100%;color:#fff;text-align: center;font-size:17px;}*/
    .login-detail .login-detail-one div input {
        width: 100%;
        height: 41px;
        border: 1px solid #fff;
        border-radius: 3px;
        outline: none;
        color: #fff;
        font-size: 16px;
        padding-left: 10px;
        background: none;
    }

    .login-detail .login-detail-one .yanzheng input {
        width: 175px;
        margin-right: 20px;
    }

    .login-detail .login-detail-one .yanzheng img {
        width: 95px;
        height: 41px;
        border: 1px solid #fff;
        border-radius: 3px;
    }

    .login-detail .login-detail-one div input::-webkit-input-placeholder {
        color: #ddd;
        font-size: 15px;
    }

    .login-detail .login-detail-one .register {
        width: 100%;
        height: 41px;
        border: 1px solid #fff;
        background: none;
        border-radius: 6px;
        display: block;
        margin: 0 auto;
        text-align: center;
        line-height: 40px;
        color: #fff;
        font-size: 18px;
        float: right;
    }

    .language-change {
        position: fixed;
        top: 15px;
        left: 15px;
        z-index: 99999;
        width: 100px;
        height: 30px;
    }

    .language-change select {
        width: 100%;
        height: 100%;
        padding-left: 5px;
        background: none;
        border: 1px solid #fff;
        color: #fff;
    }

    .language-change select option {
        width: 100%;
        height: 35px;
        background: none;
        color: #000;
    }
</style>
<%--<div class="zhezhao">--%>
<%--<div>--%>
<%--<p>测试版本，请勿商用</p>--%>
<%--<div class="btn btn-primary" onclick="zhezhaoHide()">确定</div>--%>
<%--</div>--%>
<%--</div>--%>

<!-- JS -->
<script src="${ctxStatic}/qysoftui/vendor/jquery/jquery.min.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/bootstrap/bootstrap.min.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js"
        data-deps="formValidation"></script>
<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/qysoftui/vendor/toastr/toastr.min.js"></script>

<script src="${ctxStatic}/qysoftui/js/loginjs/TweenLite.min.js"></script>
<script src="${ctxStatic}/qysoftui/js/loginjs/EasePack.min.js"></script>
<script src="${ctxStatic}/qysoftui/js/loginjs/rAF.js"></script>
<script src="${ctxStatic}/qysoftui/js/loginjs/demo-1.js"></script>


<script type="text/javascript" data-deps="formValidation">
    var nums = 60;
    var clock = '';

    function RndNum(n) {
        var rnd = "";
        for (var i = 0; i < n; i++)
            rnd += Math.floor(Math.random() * 10);
        return rnd;
    }

    $('#getVerifyCode').on('click', function () {
        getVerify();
    });

    (function (document, window, $) {
        if (${language != "zh_CN"}) {
            toastr.info("检测到当前为亚太地区,自动切换为中文");
        }
        $('#loginForm').formValidation().on('success.form.fv', function (e) {
            e.preventDefault();
            var $form = $(e.target),
                fv = $(e.target).data('formValidation');
            var userPass = $("#userPass").val();
            var userPass2 = $("#userPass2").val();
            var userName = $("#userName").val();
            if (userName == "") {
                toastr.info("请输入账户");
                return;
            }
            if (userPass != userPass2) {
                toastr.info("两次输入密码不一致");
                return;
            }
            //$.app.ajax('${ctx}/getVerificationCode', {validateCode: document.getElementById('validateCode').value}, function (data) {
            $.app.ajax('/f/user/updatePass', $form.serialize(), function (data) {
                toastr.info(data.info);
                setTimeout(function () {
                    window.location.href = "${ctx}";
                }, 2000);
            }, function () {
                fv.disableSubmitButtons(false);
            });
            // }, function () {
            //     fv.disableSubmitButtons(false);
            // });
        });


        $('#admui-pageContent').on('click', '.reload-vify', function () { //验证码刷新
            var $img = $(this).children('img'),
                URL = $img.prop('src');
            $img.prop('src', URL + '?tm=' + Math.random());
        });

    })(document, window, jQuery);

    function getVerify() {
        var userName = $("#userName").val();
        if (userName == "") {
            toastr.info("请输入账户");
            return;
        }
        var param = {
            userName: userName
        };
        
        $.app.ajax('/f/user/sendCode', param, function (data) {
			$('#getVerifyCode').off('click');
            $("#getVerifyCode").attr("disabled", true);
            $("#getVerifyCode").text(nums + '秒重新获取');
            //一秒执行一次
            clock = setInterval(doLoop, 1000);
            toastr.info("验证码已发送到注册手机,请查收");
        }, function () {

        });
    }


    function doLoop() {
        nums--;
        if (nums > 0) {
            $("#getVerifyCode").attr("disabled", true);
            $("#getVerifyCode").text(nums + '秒重新获取');
        } else {
            clearInterval(clock);
            //清除js定时器
            $("#getVerifyCode").removeAttr("disabled");
            $("#getVerifyCode").text('获取验证码');
            nums = 60;
            //重置时间

            $('#getVerifyCode').on('click', function () {
                getVerify();
            });
        }
    }
</script>
<script>
    function zhezhaoHide() {
        $(".zhezhao").eq(0).hide();
    }

    function uploadlang() {
        window.location.href = "/f/language?type=" + $("#lang").val();
    }

    // $('input').on('focus',function(){
    //     $('#loginDetail').css({'margin-top':-100+'px'});
    // }).on('blur',function(){
    //     $('#loginDetail').css({'margin-top':-285+'px'});
    // })
    /*android软键盘弹出和收起的事件处理*/
    var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
    $(window).on('resize', function () {
        var nowClientHeight = document.documentElement.clientHeight || document.body.clientHeight;
        if (clientHeight > nowClientHeight) {
            $('#loginDetail').css({'margin-top': -100 + 'px'});
        }
        else {
            $('#loginDetail').css({'margin-top': -285 + 'px'});
        }
    });
    /*ios软键盘弹出和收起的事件处理*/
    // $(document).on('focusin', function () {
    //     $('#loginDetail').css({'margin-top':-100+'px'});
    // });
    //
    // $(document).on('focusout', function () {
    //     $('#loginDetail').css({'margin-top':-285+'px'});
    // });
</script>

</body>

</html>
