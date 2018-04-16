var time_range = function (beginTime, endTime) {
    var strb = beginTime.split (":");
    if (strb.length != 2) {
        return false;
    }

    var stre = endTime.split (":");
    if (stre.length != 2) {
        return false;
    }
    var b = new Date ();
    var e = new Date ();
    var n = new Date ();

    b.setHours (strb[0]);
    b.setMinutes (strb[1]);
    e.setHours (stre[0]);
    e.setMinutes (stre[1]);

    if (n.getTime () - b.getTime () > 0 && n.getTime () - e.getTime () < 0) {
        return true;
    } else {
        console.info("当前时间是：" + n.getHours () + ":" + n.getMinutes () + "，不在该时间范围内！");
        return false;
    }
}

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
$(document).ready(function() {
	$("#userCode").blur(function(e) {     
		$(this).val($(this).val().toUpperCase());
	});
	//表单验证
	$(".bind_validator").validate({
        rules:{
        	  userCode:{
                  required:true
              },
              password:{
                  required:true
              },
              captchaCode:{
                  required:true
              }
        },
        messages:{
             userCode:{
                 required:"用户名不能为空"
             },
             password:{
                 required: "密码不能为空"
             },
             captchaCode:{
                 required: "验证码不能为空"
             }
         }
		
	});

	//表单提交
	$(".bind_validator").submit(function(evt){
        $.toast("登录成功，正在为您跳转");
        return false;
		evt.preventDefault();
		//if(time_range ("8:59", "22:00")){
			var $form = $(this);
			$form.ajaxSubmit({
			  type:"get",
			  url:$form.attr("action"),
			  dataType: "json",
			  beforeSubmit:function(formData,jqForm,options){
				  return jqForm.valid();
			  },
			  success:function(dat){
					if(dat.code==0||dat.code==1){
                        layer.msg("登录成功，正在为您跳转",function(){window.location.href="/h5/finance.jhtml";});
				}else{
					
				  refreshCaptchaImage('captchaImg');
				  $.toast(dat.msg,"cancel");
				}
			  }
			});
		  //}else{
		//	  $.toast("您好，我们在这个时间22:00:00-09:00:00 进行交易数据处理，在此期间暂停访问，感谢您的支持与配合！", "text");
		//  }
	    return false; //此处必须返回false，阻止常规的form提交
	});

});