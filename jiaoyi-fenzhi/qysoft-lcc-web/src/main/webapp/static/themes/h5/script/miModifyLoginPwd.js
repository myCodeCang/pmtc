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
            opasswd:{
                  required:true
              },
              npasswd:{
                  required:true
              },
              rpasswd:{
                  required:true,
                  equalTo:"#npasswd"
              },
              captchaCode:{
                  required:true
              }
        },
        messages:{
             opasswd:{
                  required:"旧登录密码不能为空"
              },
              npasswd:{
                  required:"新登录密码不能为空"
              },
              rpasswd:{
                  required:"确认新密码不能为空",
                  equalTo:"两次密码输入不一致，请重新输入"
              },
              captchaCode:{
                  required:"验证码不能为空"
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