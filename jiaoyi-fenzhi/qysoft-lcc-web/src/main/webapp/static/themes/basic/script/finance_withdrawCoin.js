var flag = false;
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
	//表单验证
	$(".bind_validator").validate({
        rules:{
        	  amount:{
                  required:true,
       			  number:true 
              },
              advPassword:{
                  required:true
              },
              verfiyCode:{
                  required:true
              }
        }
	});

	//表单提交
	$(".bind_validator").submit(function(evt){
		evt.preventDefault();
		var $form = $(this);

        if (!$("#amount").val()){
            return;
        }
        if (!$("#advPassword").val()){
            return;
        }
        if (!$("#verfiyCode").val()){
            return;
        }

        swal({
                title: "提示",
                text: "确认要执行转内网操作嘛！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认",
                cancelButtonText: "取消！",
                closeOnConfirm: false
            },
            function(){
                if (flag){
                    return;
                }
                flag = true;
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
                            window.location.href="/f/user/myWithdrawLog";
                        }else{
                            flag = false;
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
            });


	    return false; //此处必须返回false，阻止常规的form提交
	});
	//原密码
	  $("#sendCode").click(function(){
	    var mobile =$("#mobile").val();
	    var oThis = this;
          if(!mobile){
              layer.msg("请输入您的手机号！");
              return false;
          }
          $(oThis).prop("disabled","true");
          $.getJSON("/msm/lkMsm/sendVerifyCode?mobile="+mobile,function(dat){
              layer.msg(dat.info);
              if(dat.status==1){
                  timeWait($(oThis), 120);
              }else if (ret.status==0){
                  buyFlag=false;
                  sweetAlert("提示", ret.info, "error");
              }else{
                  swal({
                          title: "提示",
                          text: "登录超时",
                          type: "error",
                          confirmButtonColor: "#DD6B55",
                          confirmButtonText: "确定",
                          closeOnConfirm: false
                      },
                      function(){
                          location.reload();
                      });
              }
          });
	  });
});