<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>PMTC-1</title>
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
        body{
            padding-top: 0;
        }
    </style>
</head>

<body>


<style>
    .login-detail{position: absolute;top:50%;left:50%;margin-top:-190px;margin-left:-175px;background:rgba(0,0,0,0.6);border-radius:6px;width:350px;height:380px;}
    .login-detail h3{height:40px;line-height:40px;text-align: center;color:#fff;font-size:24px;}
    .login-detail .login-detail-one{padding:10px 30px;width:100%;height:330px;}
    .login-detail .login-detail-one div{height:45px;line-height: 45px;width:100%;margin-bottom:20px;}
    /*.login-detail .login-detail-one div span{display:inline-block;width:70px;height:100%;color:#fff;text-align: center;font-size:17px;}*/
    .login-detail .login-detail-one div input{width:100%;height:41px;border:1px solid #fff;border-radius:3px;outline: none;color:#fff;font-size:16px;padding-left:10px;background:none;}
    .login-detail .login-detail-one .yanzheng input{width:175px;margin-right:20px;}
    .login-detail .login-detail-one .yanzheng img{width:95px;height:41px;border:1px solid #fff;border-radius:3px;}
    .login-detail .login-detail-one div input::-webkit-input-placeholder{color:#ddd;font-size:15px;}
    .login-detail .login-detail-one .denglu{width:100%;height:41px;border:1px solid #fff;background:none; border-radius:6px;display:block;margin:0 auto;text-align: center;line-height:40px;color:#fff;font-size:18px;float:left;}
    .login-detail .login-detail-one .register{line-height:30px;color:#fff;font-size:18px;float: left;}
    .login-detail .login-detail-one .registerone{line-height:30px;color:#fff;font-size:18px;float: right;}
    .language-change{position:fixed;top:15px;left:15px;z-index:99999;width:150px;height:30px;}
    .language-change select{width:100%;height:100%;padding-left:5px;background:none;border:1px solid #fff;color:#fff;}
    .language-change select option{width:100%;height:35px;background:none;color:#000;}
</style>
<div id="selectYuyAN">
<div class="language-change">
    <select id="lang"  onchange="uploadlang()">
        <option value=""><spring:message code="xuanzeyuyan"/></option>
        <option value="en_US"><spring:message code="yingwen"/></option>
        <option value="zh_CN"><spring:message code="zhongwen"/></option>
    </select>
</div>
</div>
<%
    request.setAttribute("language",request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));%>
<div class="container demo-1" style="padding:0;width:100%;">
    <div class="content">
        <div id="large-header" class="large-header">
            <canvas id="demo-canvas"></canvas>
            <div class="login-detail">
                <h3>PMTC</h3>
                <form class="login-form" action="${ctx}/login" method="post" id="loginForm">
                    <div class="login-detail-one">
                        <input type="hidden" id="userType" name="userType" value="website">
                        <div><input id="username"  name="username"  type="text" placeholder="<spring:message code="yonghuming"/>" ></div>
                        <div><input id="password"  name="password" type="password" placeholder="<spring:message code="mima"/>"></div>
                        <div class="yanzheng"><input type="text"  id="validateCode" name="validateCode" placeholder="<spring:message code="yanzhengma"/>"><img <sys:validateWebCode name="validateCode" /></div>
                        <div style="margin:0;"><button type="submit" class="denglu" id="dengluBtn"><spring:message code="denglu"/></button><div></div></div>
                        <div><span onclick="toRegister()" class="register" style="cursor: pointer"><spring:message code="zhuce"/></span><span onclick="forgetPass()" class="registerone" style="cursor: pointer"><spring:message code="wangjimima"/></span></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function toRegister() {
        window.location.href="/f/register";
    }
    function forgetPass() {
        window.location.href="/f/homeForgetPass";
    }
</script>
<style>
    .zhezhao{position: absolute;left:0;top:0;width:100%;height:100%;background:rgba(0,0,0,0.6);z-index:999;display: flex;justify-content: center;align-items: center;}
    .zhezhao>div{width:800px;height:500px;background:#fff;}
    .zhezhao>div>p{color:#000;font-size:50px;text-align: center;height:400px;line-height:400px;margin:0;}
    .zhezhao .btn{width:100px;height:45px;text-align: center;line-height:40px;margin:0 auto;display: block;font-size:18px;}
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
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js" data-deps="formValidation"></script>
<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/qysoftui/vendor/toastr/toastr.min.js"></script>

<script src="${ctxStatic}/qysoftui/js/loginjs/TweenLite.min.js"></script>
<script src="${ctxStatic}/qysoftui/js/loginjs/EasePack.min.js"></script>
<script src="${ctxStatic}/qysoftui/js/loginjs/rAF.js"></script>
<script src="${ctxStatic}/qysoftui/js/loginjs/demo-1.js"></script>


<script type="text/javascript" data-deps="formValidation">

    (function (document, window, $) {

        if(${language != "zh_CN"}){
            //toastr.info("切换为中文");
			$('.validateCodeRefresh').click();
        }

        $('#loginForm').formValidation().on('success.form.fv', function (e) {
            e.preventDefault();
            var $form = $(e.target),
                fv    = $(e.target).data('formValidation');



            if($("#username").val() == ""){
                toastr.info("请输入用户名");
                return false;
            }
            if($("#password").val() == ""){
                toastr.info("请输入密码");
                return false;
            }
            if($("#validateCode").val() == ""){
                toastr.info("请输入验证码");
                return false;
            }

            $("#dengluBtn").html("登录中请稍后。。。");
            $('#dengluBtn').attr('disabled',"true")


		





            $.app.ajax( '${ctx}/getVerificationCode', {validateCode:document.getElementById('validateCode').value}, function (data) {


                $.app.ajax( '${ctx}/login', $form.serialize(), function (data) {

                    setTimeout(function () {
                        window.location.href = "${ctx}";
                    } ,500);

                },function () {
                    fv.disableSubmitButtons(false);
                    $("#dengluBtn").html("用户登陆");
                    $('#dengluBtn').removeAttr("disabled");

                });

            },function () {
               fv.disableSubmitButtons(false);
               $("#dengluBtn").html("用户登陆");
               $('#dengluBtn').removeAttr("disabled");
            });
        });


        $('#admui-pageContent').on('click', '.reload-vify', function () { //验证码刷新
            var $img = $(this).children('img'),
                URL = $img.prop('src');
            $img.prop('src', URL + '?tm=' + Math.random());
        });

    })(document, window, jQuery);
</script>
<script>
    function zhezhaoHide(){
        $(".zhezhao").eq(0).hide();
    }

    $(":text,:password").blur(function(){
        $("#selectYuyAN").show();
    });

    $(":text,:password").focus(function(){

        $("#selectYuyAN").hide();
    });


    function uploadlang() {
        window.location.href = "/f/language?type="+$("#lang").val();
    }
</script>

</body>

</html>
