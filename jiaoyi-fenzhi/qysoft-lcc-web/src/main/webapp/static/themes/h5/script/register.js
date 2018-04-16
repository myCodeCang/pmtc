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
      var captchaCode = $("#captchaCode").val();
      if(mobile == ''){
        $.toast("请输入您的手机号！","cancel");
        return false;
      }
      if(captchaCode == ''){
         $.toast("请输入验证码","cancel");
        return false;
      }
      var oThis = this;
      $.getJSON("/api/webSMS/nonCode.jhtml?captchaCode="+captchaCode+"&mobile="+mobile,function(dat){
         $.toast(dat.msg,"text");
       if(dat.code==0) timeWait($(oThis), 120);
      });
	});
	//表单验证
	$(".bind_validator").validate({
        rules:{
        	  mobile:{
                  required:true
              }
              ,captchaCode:{
                  required:true
              }
              ,mobileCode:{
                  required:true
              }
              ,idName:{
                  required:true
              }
              ,idNo:{
                  required:true
              }
        },
        messages:{
             mobile:{
                 required:"请输入手机号码"
             },
             
             captchaCode:{
                 required: "请输入验证码"
             },
             mobileCode:{
                required: "请输入短信验证码"
             },
             idName:{
                required: "请输入真实姓名"
             },
             idNo:{
                  required:"请输入身份证号码"
             }
         }
		
	});

	//表单提交
	$(".bind_validator").submit(function(evt){
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
	    	  	if(dat.code!=0){
	    	  		$.toast(dat.msg,"cancel");
	    	  	}else{
	    	  	
	    	  		$.toast(dat.msg,function(){window.location.href="/h5/me.jhtml";});
	    	  		
	    	  	}
	    	}
	    });
	    return false; //此处必须返回false，阻止常规的form提交
	});

});