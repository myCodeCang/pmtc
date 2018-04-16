<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>修改密码</title>


<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/forms/validation.css">


<div class="page animation-fade page-forms">
    <div class="page-content container-fluid">
        <div class="row">

            <div class="col-md-12">
                <div class="panel" id="exampleWizardForm">
                    <div class="panel-heading title_h3_one">
                        <h3 class="panel-title">登录密码修改</h3>
                    </div>
                    <div class="panel-body message_group">
                        <form class="form-horizontal" id="pwdForm" autocomplete="off">
                            <input type="hidden" name="type" value="pwd">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">旧密码:</label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" name="pwd" data-fv-notempty="true"
                                           data-fv-notempty-message="请填写旧密码" placeholder=""
                                           autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">新密码:</label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" name="newpwd" data-fv-notempty="true"
                                           data-fv-notempty-message="请填写新密码" data-fv-identical="true"
                                           data-fv-identical-field="newpwd2" data-fv-identical-message="两次输入内容不一致"
                                           placeholder=""
                                           autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">重复新密码:</label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" name="newpwd2" data-fv-notempty="true"
                                           data-fv-notempty-message="请填写确认密码" data-fv-identical="true"
                                           data-fv-identical-field="newpwd" data-fv-identical-message="两次输入内容不一致"
                                           placeholder=""
                                           autocomplete="off">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-6 col-sm-offset-3">
                                    <button type="submit" class="btn btn-warning" >确定提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-12">

                <div class="panel" id="exampleWizardFormContainer">
                    <div class="panel-heading title_h3_one">
                        <h3 class="panel-title">二级密码修改</h3>
                    </div>
                    <div class="panel-body message_group">
                        <form class="form-horizontal" id="paypwdForm" autocomplete="off">
                            <input type="hidden" name="type" value="paypwd">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">新密码:</label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" name="banknewpwd" data-fv-notempty="true"
                                           data-fv-notempty-message="请填写新二级密码" data-fv-identical="true"
                                           data-fv-identical-field="banknewpwd2" data-fv-identical-message="两次输入内容不一致"
                                           placeholder=""
                                           autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">重复新密码:</label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" name="banknewpwd2" data-fv-notempty="true"
                                           data-fv-notempty-message="请填写确认二级密码" data-fv-identical="true"
                                           data-fv-identical-field="banknewpwd" data-fv-identical-message="两次输入内容不一致"
                                           placeholder=""
                                           autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">短信验证码:</label>
                                <span class="col-sm-6">
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="validCode"  placeholder="请输入验证码">
                                                <span class="input-group-addon" style="cursor: pointer;padding: 4px 30px;" id="getVerifyCodeNum">点击获取</span>
                                            </div>
                                        </span>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-6 col-sm-offset-3">
                                    <button type="submit" class="btn btn-warning" >确定提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>




<script src="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js"
        data-deps="formValidation"></script>


<script type="text/javascript" data-deps="formValidation">

    $('#getVerifyCodeNum').on('click', function () {
        getVerify();
    });

    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        window.Content = {
            run: function () {

                this.initFormValidate();
            },

            initFormValidate : function(){

                $('#pwdForm').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + 'updatePwd', $("#pwdForm").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("保存成功！");

                        setTimeout(function () {
                            location.reload();
                        },2000);
                    },function () {
                        fv.disableSubmitButtons(false);
                    });
                });

                $('#paypwdForm').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + 'updatePwd', $("#paypwdForm").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("保存成功！");

                        setTimeout(function () {
                            location.reload();
                        },2000);
                    },function () {
                        fv.disableSubmitButtons(false);
                    });
                });
            }

        };
    })(document, window, jQuery);

    function getVerify() {
        var userName = $("#userName").val();
        if (userName == "") {
            toastr.info("请输入账户");
            return;
        }
        var param = {
            userName: userName,
            type:"pass"
        };
        $('#getVerifyCodeNum').off('click');
        $.app.ajax('/f/user/sendCode', param, function (data) {
            $("#getVerifyCodeNum").attr("disabled", true);
            $("#getVerifyCodeNum").text(summ + '秒重新获取');
            //一秒执行一次
            clock = setInterval(doLoop, 1000);
            toastr.info("验证码已发送到注册手机,请查收");
        }, function () {

        });
    }


    function doLoop() {
        summ--;
        if (summ > 0) {
            $("#getVerifyCodeNum").attr("disabled", true);
            $("#getVerifyCodeNum").text(summ + '秒重新获取');
        } else {
            clearInterval(clock);
            //清除js定时器
            $("#getVerifyCodeNum").removeAttr("disabled");
            $("#getVerifyCodeNum").text('获取验证码');
            summ = 60;
            //重置时间

            $('#getVerifyCodeNum').on('click', function () {
                getVerify();
            });
        }
    }

</script>
