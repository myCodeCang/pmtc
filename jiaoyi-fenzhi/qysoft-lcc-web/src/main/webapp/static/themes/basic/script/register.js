/**
 *
 */
/**
 * 刷新验证码图片
 * @param imageId 图片ID
 */
function refreshCaptchaImage(imageId) {
    var imgSrc = $("#" + imageId);
    var srcTemp = imgSrc.attr("src").split("=");
    //时间戳     
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
    var timestamp = (new Date()).valueOf();
    imgSrc.attr("src", srcTemp[0] + "=" + timestamp);
}

/**
 * 验证码按钮等待
 * @param jqObj jQuery对象
 * @param wait 等待时间(s)
 */
function timeWait(jqObj, wait) {
    if (wait == 0) {
        jqObj.prop("disabled", "");
        jqObj.html("获取验证码");
    } else {
        jqObj.prop("disabled", "true");
        jqObj.html("重新获取(" + wait + ")");
        wait--;
        setTimeout(function () {
            timeWait(jqObj, wait)
        }, 1000);
    }
}
var registerFlag= false;
$(document).ready(function () {

    $(".sms_sender").click(function () {
        var mobile = $("#mobile").val();
        var captchaCode = $("#captchaCode").val();
        var type = $("#country").val();
        if (!mobile) {
            layer.msg("请输入您的手机号！");
            return false;
        }
        if (captchaCode == '') {
            layer.msg("请输入图形验证码");
            return false;
        }
        var oThis = this;
        if (registerFlag){
            return;
        }
        registerFlag = true;
        $.post("/f/getVerificationCode?validateCode="+captchaCode, function (res) {

            if (res.status == 1){
                   $(this).prop("disabled", "true");
                $.getJSON("/msm/lkMsm/sendVerifyCode?mobile=" + mobile+"&captchaCode="+captchaCode+"&type="+type, function (dat) {
                    registerFlag = false;
                    layer.msg(dat.info);
                    if (dat.status == 1) timeWait($(oThis), 120);
                });
            }else {
                registerFlag = false;
                layer.msg(res.info);
            }

        });
    });
    //表单验证
    $(".bind_validator").validate({
        rules: {
            mobile: {
                required: true
            },
            idName: {
                required: true
            }
            , password: {
                required: true
                , rangelength: [6, 20]
            }
            , rePassword: {
                equalTo: "#password"
            }

            , phoneCode: {
                required: true
            },
            protocol: {
                required: true
            }
        },
        messages: {
            mobile: {
                required: "请输入手机号码"

            },
            idName: {
                required: "请输入真实姓名"
            },

            password: {
                required: "请输入密码"
                , rangelength: $.validator.format("密码必须为{0}到{1}位字母和数字组合。")
            },
            rePassword: {
                equalTo: "两次密码输入不一致，请重新输入"
            },


            phoneCode: {
                required: "请输入短信验证码"
            },
            required: {
                required: "必须阅读并同意《用户协议》"
            }
        }

    });

    //表单提交
    $(".bind_validator").submit(function (evt) {
        evt.preventDefault();
        var $form = $(this);

        $form.ajaxSubmit({
            type: "post",
            url: $form.attr("action"),
            dataType: "json",
            beforeSubmit: function (formData, jqForm, options) {
                return jqForm.valid();
            },
            success: function (dat) {
                if (dat.status == 1) {
                    layer.msg("注册成功", function () {
                        window.location.href = "/f/login";
                    });

                } else {
                    $('.validateCodeRefresh').click();
                    layer.msg(dat.info);
                }
            }
        });
        return false; //此处必须返回false，阻止常规的form提交
    });

});