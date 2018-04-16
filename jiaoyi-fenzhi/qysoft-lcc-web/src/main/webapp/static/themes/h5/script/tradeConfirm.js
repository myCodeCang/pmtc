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
	    	  	if(dat.code!=0){
	    	  		$.toast(dat.msg,50000,"cancel");
	    	  	}else{
	    	  		$.toast(dat.msg,function(){window.location.href="/h5/trSuccess_LCC.jhtml";});
	    	  	}
	      }
	    });
	    return false; //此处必须返回false，阻止常规的form提交
	});
});
