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
            bname:{
                  required:true
              },
              openBank:{
                  required:true
              },
              bbranch:{
                  required:true
              },
              bnum:{
                  required:true
              },
              captchaCode:{
                  required:true
              }

        },
        messages:{
             bname:{
                 required:"持卡人姓名不能为空"
             },
             openBank:{
                 required: "开户银行不能为空"
             },
             bbranch:{
                 required: "分支行名称"
             },
             bnum:{
                 required: "银行卡账号"
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
              $.toast(dat.msg,function(){window.location.href="/h5/fiBankInfo.jhtml";});
            }
        }
      });
      return false; //此处必须返回false，阻止常规的form提交
  });

});