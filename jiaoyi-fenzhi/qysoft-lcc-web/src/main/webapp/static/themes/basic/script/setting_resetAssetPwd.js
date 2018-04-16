/**
 *
 */
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

function refreshCaptchaImage(imageId) {
    var imgSrc = $("#" + imageId);
    var srcTemp = imgSrc.attr("src").split("&rnd");
    var timestamp = (new Date()).valueOf();
    imgSrc.attr("src", srcTemp[0] + "&rnd=" + timestamp);
}

$(document).ready(function () {
    //表单验证
    $(".bind_validator").validate({
        rules: {

            npasswd: {
                required: true
            },
            rpasswd: {
                required: true,
                equalTo: "#npasswd"
            },
            verfiyCode: {
                required: true
            }
        },
        messages: {

            npasswd: {
                required: "新资金密码不能为空"
            },
            rpasswd: {
                required: "确认新密码不能为空",
                equalTo: "两次密码输入不一致，请重新输入"
            },
            verfiyCode: {
                required: "验证码不能为空"
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
                    layer.msg(dat.info);
                    setTimeout(function () {
                        window.location.href = "/f/center";
                    }, 2000);
                } else {
                    layer.msg(dat.info);
                }
            }
        });
        return false; //此处必须返回false，阻止常规的form提交
    });

    $(".sms_sender").click(function () {
        var mobile = $("#mobile").val();
        if (mobile == '') {
            layer.msg("请输入您的手机号！");
            return false;
        }

        var oThis = this;

        $(this).prop("disabled", "true");
        $.getJSON("/msm/lkMsm/sendVerifyCode?mobile=" + mobile, function (dat) {
            layer.msg(dat.info);
            if (dat.status == 1) timeWait($(oThis), 120);
        });

    });

});