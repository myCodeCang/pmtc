/**
 * 
 */
  /**
 * 刷新验证码图片
 * @param imageId 图片ID
 */
function refreshCaptchaImage(imageId){     
    var imgSrc = $("#"+imageId);     
    var srcTemp = imgSrc.attr("src").split("=");
    //时间戳     
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
    var timestamp = (new Date()).valueOf();     
    imgSrc.attr("src",srcTemp[0]+"="+timestamp);
}
/**
 * 验证码按钮等待
 * @param jqObj jQuery对象
 * @param wait 等待时间(s)
 */
function timeWait(jqObj, wait) { 
    if (wait == 0) {
        jqObj.prop("disabled","");
        jqObj.html("获取验证码"); 
    } else { 
        jqObj.prop("disabled","true");
        jqObj.html("重新获取("+wait+")"); 
        wait--; 
        setTimeout(function() { timeWait(jqObj,wait)  }, 1000); 
    } 
}
$(document).ready(function() {
	//
	$(".sms_sender").click(function(){
      var mobile = $("#mobile").val();
      var captchaCode = $("#validateCode").val();
      if(mobile == ''){
        layer.msg("请输入您的手机号！");
        return false;
      }
      if(captchaCode == ''){
        layer.msg("请输入验证码");
        return false;
      }

        var oThis = this;
        $.post("/f/getVerificationCode?validateCode="+captchaCode, function (res) {
            if (res.status == 1){
                $(this).prop("disabled", "true");
                $.getJSON("/msm/lkMsm/sendVerifyCode?mobile="+mobile,function(dat){
                    layer.msg(dat.info);
                    if(dat.status==1) timeWait($(oThis), 120);
                });
            }else {
                layer.msg(res.info);
            }
        });

	});
	//表单验证
	$(".findPwdStep_form1").validate({
        rules:{
        	mobile:{
                  required:true
              }
              ,validateCode:{
                  required:true
              }
              ,mobileCode:{
            	  required:true
              }
            ,pwd:{
                required:true
            }
            ,checkPwd:{
                required:true
            }

        },
        messages:{
             mobile:{
                 required:"请输入手机号码"

             },
            validateCode:{
                 required: "请输入验证码"
             },
             mobileCode:{
            	 required: "手机验证码"
             }
            ,
            pwd:{
                required: "请输入密码"
            },
            checkPwd:{
                required: "请确认密码"
            }
         }

	});

	//表单提交
	$(".findPwdStep_form1").submit(function(evt){
		evt.preventDefault();
		var $form = $(this);
		$form.ajaxSubmit({
	      type:"POST",
	      url:$form.attr("action"),
	      dataType: "json",
	      beforeSubmit:function(formData,jqForm,options){
			  return jqForm.valid();
	      },
	      success:function(dat){

	    	  	if(dat.status==1){
                    layer.msg(dat.info);
	    	  	    setTimeout(function () {
                        window.location.href="/f/login";
                    },2000)
	    	  	}else{
                    layer.msg(dat.info);
	    	  	}
	    	}
	    });
	    return false; //此处必须返回false，阻止常规的form提交
	});

	//表单验证
	$(".findPwdStep_form2").validate({
        rules:{
        	  mobile:{
                  required:true
              }
              ,password:{
                  required:true
                  ,rangelength:[6,20]
              }
              ,confirm_password:{
             	equalTo:"#password"
              }
              ,token:{
                  required:true
              }

        },
        messages:{
             mobile:{
                 required:"请输入手机号码"
             },
             password:{
                 required: "新登录密码"
                 ,rangelength: $.validator.format("密码必须为{0}到{1}位字母和数字组合。")
             },
             confirm_password:{
             	equalTo:"两次密码输入不一致，请重新输入"
             },

             token:{
                 required: "请先进行第一步操作"
             }
         }

	});
	//表单提交
	$(".findPwdStep_form2").submit(function(evt){
		evt.preventDefault();
		var $form = $(this);
		$form.ajaxSubmit({
	      type:"get",
	      url:$form.attr("action"),
	      dataType: "json",
	      beforeSubmit:function(formData,jqForm,options){
			  return jqForm.valid();
	      },
	      success:function(dat){
	    	  	if(dat.code!=1){
	    	  		layer.msg(dat.msg);
	    	  	}else{
	    	  		layer.msg(dat.msg);
	    	  		// $("#findPwdStep1").hide();
	    	  		// $("#findPwdStep2").hide();
	    	  		// $("#findPwdStep3").show();
	    	  	}
	    	}
	    });
	    return false; //此处必须返回false，阻止常规的form提交
	});
});