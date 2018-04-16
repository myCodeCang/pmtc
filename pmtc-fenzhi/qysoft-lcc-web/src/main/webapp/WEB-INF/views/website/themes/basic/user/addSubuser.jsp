<%@ page import="com.thinkgem.jeesite.common.config.Global" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
    <style>
        .radio-customs label::after{
            top: 10px !important;}
        .radio-customs label::before {
            top: 3px;}
    </style>
</head>
<title>会员注册</title>


<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/forms/validation.css">
<script src="${ctxStatic}/layerUI/layer/skin/default/layer.css"></script>
<style>
    .message_group .form-group{margin-bottom:10px;}
</style>
<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">

                <div class="row row-lg">
                    <div class="col-sm-12 col-md-8">
                        <div class="example-wrap message_group">
                            <form class="form-horizontal" id="subForm">
                                <h4 class="example-title title_h4">会员基本信息</h4>
                                <div class="example">
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">接点人:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<input type="text" class="form-control" name="parentName" value="${userinfo.userName}"  autocomplete="off" placeholder="请填写接点人"  data-fv-notempty="true" data-fv-notempty-message="请填写接点人">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">推荐人:</label>
                                        <span class="col-sm-3">
                                            <input type="text" class="form-control" name="walterName" value="${userinfo.userName}" >
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">会员编号:</label>
                                        <span class="col-sm-3">
                                            <input type="text" class="form-control" id="userName"  name="userName" placeholder="" >
                                        </span>
                                        <label class="col-sm-6 ">只能是7到10位数字和小写字母组合</label>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">昵称:</label>
                                        <span class="col-sm-3">
                                            <input type="text" class="form-control" id="userNicename"  name="userNicename" placeholder="请输入您的昵称">
                                        </span>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">手机号:</label>
                                        <span class="col-sm-3">
                                            <input type="text" class="form-control" id="mobile" name="mobile" placeholder="请输入手机号"  >
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">登录密码:</label>
                                        <span class="col-sm-3">
                                            <input type="password" class="form-control" name="userPass" placeholder="" value="<% out.print(Global.getOption("system_sys","site_name").toLowerCase());%>123123">
                                        </span>
                                        <label class="col-sm-6">默认密码:<% out.print(Global.getOption("system_sys","site_name").toLowerCase());%>123123</label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">重复密码:</label>
                                        <span class="col-sm-3">
                                            <input type="password" class="form-control" name="userPass2" placeholder="" value="<% out.print(Global.getOption("system_sys","site_name").toLowerCase());%>123123" >
                                        </span>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">验证码:</label>--%>
                                        <%--<span class="col-sm-3">--%>
                                            <%--<input type="text" class="form-control" name="validCode" placeholder="请输入验证码" style="width:55%;float: left;">--%>
                                            <%--<div class="btn btn-block" id="getVerifyCode" style="width:40%;border:1px solid #ddd;height:41px;line-height:41px;padding:0;font-size:13px;color:#fff;-webkit-border-radius:3px;-moz-border-radius: 3px;border-radius: 3px;float: right;">获取验证码</div>--%>
                                        <%--</span>--%>
                                    <%--</div>--%>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">短信验证码:</label>
                                        <span class="col-sm-3">
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="validCode"  placeholder="请输入验证码">
                                                <span class="input-group-addon" style="cursor: pointer" id="getVerifyCodeTwo">点击获取</span>
                                            </div>
                                        </span>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">二级密码:</label>--%>
                                        <%--<span class="col-sm-3">--%>
                                            <%--<input type="password" name="bankPassword" value="pmtc123123" placeholder="" >--%>
                                        <%--</span>--%>
                                        <%--<label class="col-sm-3 ">默认二级密码:pmtc123123</label>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">确认二级密码:</label>--%>
                                        <%--<span class="col-sm-3">--%>
                                            <%--<input type="password" name="bankPassword2" value="pmtc123123" placeholder="">--%>
                                        <%--</span>--%>
                                    <%--</div>--%>
                                </div>


                                <div class="form-group">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <button type="submit" class="btn btn-warning" >提交注册</button>
                                    </div>
                                </div>
                            </form>      
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js"
        data-deps="formValidation"></script>
<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/layerUI/layer/layer.js" ></script>

<script type="text/javascript" data-deps="formValidation">

    $('#getVerifyCodeTwo').on('click', function() {
        getVerify();
    });
    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        var loadIndex = 0;

        window.Content = {
            run: function () {

                this.initFormValidate();


            },

            initFormValidate : function(){
                $('#subForm').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    var fv    = $(e.target).data('formValidation');
                    fv.disableSubmitButtons(true);
                    $.app.ajax($.ctx + '/f/register', $("#subForm").serialize(), function (data) {
                        toastr.info(data.info);
                        setTimeout(function () {
                            window.location.href="/f/user/userList";
                        },2000);

                    },function () {
                        fv.disableSubmitButtons(false);
                    });
                });
                // $('#subForms').formValidation().on('success.form.fv', function (e) {
                //     e.preventDefault();
                //     loadIndex = layer.load(1, {
                //         shade: [0.2,'#000000'] //0.1透明度的白色背景
                //     });
                //
                //
                //     biaodanHide();
                //     var fv    = $("#subForm").data('formValidation');
                //     $.app.ajax($.ctx + 'addSubuser', $("#subForm").serialize(), function (data) {
                //             $.pjax({
                //                 url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                //                 container: '#admui-pageContent'
                //             });
                //
                //         toastr.info("保存成功！");
                //     },function () {
                //         fv.disableSubmitButtons(false);
                //         layer.close(loadIndex);
                //     });
                // });

            }

        };
    })(document, window, jQuery);


    function getVerify() {
        var sMobile = $("#mobile").val();
        if ($("#mobile").val() == '' || $("#mobile").val() == 'undefined') {
            toastr.info('手机号码不能为空');
            return;
        }
        // if(!(/^1[1|2|3|4|5|6|7|8][0-9]\d{4,8}$/.test(sMobile))){
        //     toastr.info("不是正确的手机号码格式");
        //     return;
        // }
        var param = {
            mobile : $("#mobile").val()
        };
        $('#getVerifyCodeTwo').off('click');
        $.post("${pageContext.request.getContextPath()}/msm/lkMsm/sendVerifyCode", param, function(data) {
            $("#getVerifyCodeTwo").attr("disabled", true);
            $("#getVerifyCodeTwo").text(count + '秒重新获取');
            //一秒执行一次
            clock = setInterval(doLoop, 1000);
        });
    }
    function doLoop() {
        count--;
        if (count > 0) {
            $("#getVerifyCodeTwo").attr("disabled", true);
            $("#getVerifyCodeTwo").text(count + '秒重新获取');
        } else {
            clearInterval(clock);
            //清除js定时器
            $("#getVerifyCodeTwo").removeAttr("disabled");
            $("#getVerifyCodeTwo").text('获取验证码');
            count = 60;
            //重置时间

            $('#getVerifyCodeTwo').on('click', function() {
                getVerify();
            });
        }
    }
</script>
<script type="text/javascript">
    function biaodanHide(){
        var fv    = $("#subForm").data('formValidation');
        fv.disableSubmitButtons(false);
        $("#biaodan").hide();
    }
</script>