layui.define(['layer','form'],function(exports){ 
	
	
 var obj = {
		 
	//同意拒绝
	openPromptMsg: function(title,typeMsg,userName,dic,defaultDic,url){
    
		var layer = layui.layer;
		$("#openPromptMsgForm").remove();
		
		var promtStr = '<form id="openPromptMsgForm" modelAttribute="userUserinfo" action="" method="post" class="form-horizontal" style="display: none;">';
		promtStr += '<div class="control-group"></div>';
		promtStr += '<div class="control-group">';
		promtStr += '<label class="">审核用户：</label> ';
		promtStr += '<label class=""><b >'+userName+'</b></label> ';
		promtStr += '</div>';
		promtStr += '<div class="control-group">';
		promtStr += '<label class="">'+typeMsg+'：</label> <select name="promValue" required>';
		for ( var inx in dic) {
			if(defaultDic == dic[inx]['value']){
				promtStr += "<option value='"+dic[inx]['value']+"' selected >"+dic[inx]['label']+"</option>";
			}
			else{
				promtStr += "<option value='"+dic[inx]['value']+"'>"+dic[inx]['label']+"</option>";
			}
			
		}
		promtStr += '';
		promtStr += '</select>';
		promtStr += '</div>';
		promtStr += '<div class="control-group">';
		promtStr += '<label class="">处理意见：</label>';
		promtStr += '<textarea htmlEscape="false" rows="4" maxlength=255 class="input-xlarge" name="promMsg"  required lay-verify="required"></textarea>';
		promtStr += '</div>';
		promtStr += '</form>';
		
		$("body").append(promtStr);
		
		$("#openPromptMsgForm").attr('action',url);
		$("#openPromptMsgForm").find("#openProUser").html(userName);

		//页面层
		layer.open({
		   type: 1,
		   zIndex:1000,
		   title:title,
		   skin: 'layui-layer-lan',
		   area: ['452px', '304px'], //宽高
		   btn: ['确认', '取消'],
		   yes: function(index, layero){
			  $("#openPromptMsgForm").submit();
		   },
		   content: $("#openPromptMsgForm")
		});
		
		
	
		
		$("#openPromptMsgForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
	   });
		
		$.validator.setDefaults({
		    submitHandler: function() {
		 	  layer.close(index); 
		    }
		});
    },
 
 	
 
  };
 
 
 
 	
  
 
  exports('qyframe', obj);
});    
