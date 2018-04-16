/**
 * 
 */
function refreshCaptchaImage(imageId){     
    var imgSrc = $("#"+imageId);     
    var srcTemp = imgSrc.attr("src").split("&rnd");  
    var timestamp = (new Date()).valueOf();
    imgSrc.attr("src",srcTemp[0]+"&rnd="+timestamp);
}
$(document).ready(function() {
	//表单验证
	$(".bind_validator").validate({
        rules:{
        	  email:{
                  required:true,
                  email:true
              },
            validateCode:{
                  required:true
              }
        },
        messages:{
             email:{
                 required:"邮箱地址不能为空"
             },
            validateCode:{
                 required: "验证码不能为空"
             }
         }
		
	});

	//表单提交
	$(".bind_validator").submit(function(evt){
		evt.preventDefault();
		var $form = $(this);
		$form.ajaxSubmit({
	      type:"post",
	      url:$form.attr("action"),
	      dataType: "json",
	      beforeSubmit:function(formData,jqForm,options){
			  return jqForm.valid();
	      },
	      success:function(dat){
              if(dat.status==1){
                  layer.msg(dat.info);
                  window.location.href="/f/center";
              }else{
                  layer.msg(dat.info);
              }
	      },
            error:function (ret) {
                layer.msg("登录超时");
                setTimeout(function () {
                    location.reload();
                },2000)
            }

	    });
	    return false; //此处必须返回false，阻止常规的form提交
	});

});