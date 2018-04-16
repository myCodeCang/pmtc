<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
	<title>${fns:getOption("system_trans","trans_name")}交易平台| ${fns:getOption("system_trans","trans_name")}</title>
	<%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>

	<link href="${ctxStatic}/themes/basic/css/page_center.css" rel="stylesheet" media="screen">

</head>
	<body style="height: 100%;width:100%">

	<%@ include file="/WEB-INF/views/website/themes/basic/layouts/settingTop.jsp" %>

		<div id="doc_body">
			<div class="section head_notice" id="head_notice">
				<div class="notice_info" style="">&nbsp;
				</div>
				<a class="close" href="javascript:void(0);" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="section doc_section">
				<div class="doc_main_wrap">
					<div class="mod mod_setting">
						<div class="mod_hd">
							<div class="crumb_nav">
								<a href="${ctx}/center">设置页面</a> &gt; 绑定会员端钱包地址
							</div>
						</div>
						<div class="mod_bd p_all_30">
							<div class="alert alert_orange m_b_30"> 您正在为账户<span class="font_orange">UID:(${userinfo.userName})${userinfo.trueName}</span>绑定会员端钱包地址
							</div>

							<form action="${ctx}/user/bingUserBurseAdd" method="post" class="bind_validator" validator="true" novalidate="novalidate">
								<input name="pwdSecurityScore" value="9" type="hidden">
								<div class="group">
									<div class="col_1  col_text  align_right">会员钱包地址</div>

									<div class="col_3">
										<input class="input_text" style="width:300px;"  id="address" name="address" type="text">
										<div class="v_info"></div>
									</div>
								</div>
								<div class="group">
									<div class="col_1 align_right">短信验证码</div>
									<div class="col_2">
										<div class="input_sms sms_resend">
											<input name="verfiyCode" class="input_text" style="width: 100px" data-type="*" data-msg-null="请输入验证码" type="text">

											<span class="resend_box">
											<button type="button" id="sendCode" class="btn btn_link resend">点击获取</button>
										</span>

										</div>
										<div class="group_help" style="display:none">验证码已经发送至您的手机，10分钟内输入有效</div>
										<div class="v_info"></div>
									</div>
								</div>

								<div class="group">
									<div class="col_1"></div>
									<div class="col_2">
										<button type="submit" class="btn btn_orange small">确认</button>
										    <p class="font_12 font_orange m_t_15">重要操作需要先绑定矿机钱包地址.</p>
											<p class="font_12 font_orange m_t_15">每个会员钱包地址只可绑定一次,一旦绑定无法修改,请您谨慎操作</p>

									</div>
									<br>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="doc_dialog doc_init_dialog  no_close">
					<div class="mod_dialog">
						<div class="mod_hd">
							<span class="icon_wrap"><i class="icon"></i></span>
							<span class="mod_title">
                  
            </span>
							<div class="mod_option">
								<a href="javascript:;" class="close">×</a>
							</div>
						</div>
						<div class="mod_bd">


						</div>
						<div class="mod_ft">

						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
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

                        address:{
                            required:true
                        },
                        verfiyCode:{
                            required:true
                        }
                    },
                    messages:{

                        address:{
                            required:"钱包地址不能为空"
                        },
                        verfiyCode:{
                            required:"验证码不能为空"
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
                        }
                    });
                    return false; //此处必须返回false，阻止常规的form提交
                });

                $("#sendCode").click(function(){
                    var address =$("#address").val();
                    var oThis = this;
                    $(this).prop("disabled", "true");
                    $.getJSON("${ctx}/user/sendCodeByUserApi?address="+address,function(dat){
                        layer.msg(dat.info);
                        if(dat.status==1)
                        {timeWait($(oThis), 120)}
                        else {
                            $(oThis).prop("disabled", "");
						};
                    });
                });
            });

		</script>

		<!-- -->
		<div class="footer section">


		</div>

		<div>
			<div class="sweet-overlay" tabindex="-1"></div>
			<div class="sweet-alert" tabindex="-1">
				<div class="icon error"><span class="x-mark"><span class="line left"></span><span class="line right"></span></span>
				</div>
				<div class="icon warning"> <span class="body"></span> <span class="dot"></span> </div>
				<div class="icon info"></div>
				<div class="icon success"> <span class="line tip"></span> <span class="line long"></span>
					<div class="placeholder"></div>
					<div class="fix"></div>
				</div>
				<div class="icon custom"></div>
				<h2>Title</h2>
				<p>Text</p><button class="cancel" tabindex="2">Cancel</button><button class="confirm" tabindex="1">OK</button></div>
		</div>
	</body>

</html>