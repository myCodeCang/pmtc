/**
 * 
 */

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
var loginFlag= false;
$(document).ready(function() {

	//表单验证
	$(".bind_validator").validate({
        rules:{
            username:{
                  required:true
              },
              password:{
                  required:true
              },
            validateCode:{
                  required:true
              }
        },
        messages:{
             userCode:{
                 required:"不能为空"
             },
             password:{
                 required: "不能为空"
             },
             captchaCode:{
                 required: "不能为空"
             }
         }
		
	});

	//表单提交
	$(".bind_validator").submit(function(evt){
       var captchaCode =  $("#validateCode").val();
		evt.preventDefault();
    //if(time_range ("8:59", "22:00")){
  		var $form = $(this);
  		if (loginFlag){
            return;
        }
        loginFlag = true;
        $.post("/f/getVerificationCode?validateCode="+captchaCode, function (res) {
            if (res.status == 1){
                $form.ajaxSubmit({
                    type:"post",
                    url:$form.attr("action"),
                    dataType: "json",
                    beforeSubmit:function(formData,jqForm,options){
                        return jqForm.valid();
                    },
                    success:function(dat){
                        if(dat.status==1){
                            layer.msg("登录成功，正在为您跳转",function(){window.location.href="/f/center";});
                        }else{
                            $('.validateCodeRefresh').click();
                            // refreshCaptchaImage('captchaImg');
                            layer.msg(dat.info);
                            loginFlag = false;
                        }
                    }
                });
            }else {
                layer.msg(res.info);
                loginFlag = false;
            }

        });

     // }else{
     //   sweetAlert("提示", "您好，我们在这个时间22:00:00-09:00:00 进行交易数据处理，在此期间暂停访问，感谢您的支持与配合！", "error");
    //  }
	    return false; //此处必须返回false，阻止常规的form提交
	});

});