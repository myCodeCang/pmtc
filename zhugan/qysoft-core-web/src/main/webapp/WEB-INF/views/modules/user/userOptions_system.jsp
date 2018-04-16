<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>配置管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("[name=inputForm]").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>

</head>
<body>
<sys:message content="${message}"/>
<div class="layui-tab layui-tab-brief" lay-filter="optionlayuitab">
    <ul class="layui-tab-title">
        <c:forEach items="${optionList}" var="dic" varStatus="status">

            <li lay-id="${dic.optionName}"
                class="${dic.optionName  eq selOptLabel ? 'layui-this':''}">${dic.optionLabel } [${dic.optionName }]
            </li>
        </c:forEach>
    </ul>
    <div class="layui-tab-content">

        <div class="layui-tab-item layui-show">

            <form name="inputForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/save" method="post"
                  class="form-horizontal">
                <input type="hidden" name="optName" value="system_sys"/>

                <blockquote class="layui-elem-quote">平台基本信息设置</blockquote>
                <div class="control-group">
                    <label class="control-label">网站名称：</label>
                    <div class="controls">
                        <input type="text" name="site_name"
                               value="${options.system_sys.site_name }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[siteName]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">网站域名：</label>
                    <div class="controls">
                        <input type="text" name="domain"
                               value="${options.system_sys.domain }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[domain]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">网站logo：</label>
                    <div class="controls">
                            <input type="hidden" id="nameImage" name="site_logo" value="${options.system_sys.site_logo}" htmlEscape="false" maxlength="255" class="input-xlarge" >
                            <sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100" />
                            <span class="help-inline"><font color="red">*</font>标识:[site_logo]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">网站简介:</label>
                    <div class="controls">
                        <textarea id="siteIntro" name="site_intro" >${options.system_sys.site_intro}</textarea>
                        <sys:ckeditor replace="siteIntro"/>
                        <span class="help-inline"><font color="red">*</font>
								标识:[siteIntro]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">客服地址：</label>
                    <div class="controls">
                        <input type="text" name="site_kefu"
                               value="${options.system_sys.site_kefu }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[siteKefu]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">APP下载地址：</label>
                    <div class="controls">
                        <input type="text" name="site_app"
                               value="${options.system_sys.site_app }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[site_app]</span>
                    </div>
                </div>


                <div class="form-actions">
                    <shiro:hasPermission name="user:userUserinfo:edit">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>

            </form>


        </div>

        <div class="layui-tab-item">
            <form name="lkSmsForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/saveLKMsm" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_sms"/>

                <blockquote class="layui-elem-quote">凌凯短信设置</blockquote>
                <div class="control-group">
                    <label class="control-label">短信接口地址：</label>
                    <div class="controls">
                        <input type="text" name="lk_address" id="lk_address" ${options.system_sms.lk_open=='on'?'':'readonly' }
                               value="${options.system_sms.lk_address}" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[lk_address]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">账号：</label>
                    <div class="controls">
                        <input type="text" name="lk_acc" id="lk_acc" ${options.system_sms.lk_open=='on'?'':'readonly' }
                               value="${options.system_sms.lk_acc}" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[lk_acc]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">密码：</label>
                    <div class="controls">
                        <input type="password" name="lk_pwd" id="lk_pwd" ${options.system_sms.lk_open=='on'?'':'readonly' }
                               value="${options.system_sms.lk_pwd }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[lk_pwd]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">验证码消息模板：</label>
                    <div class="controls">
                        <input type="text" name="lk_msg_template" id="lk_msg_template" ${options.system_sms.lk_open=='on'?'':'readonly' }
                               value="${options.system_sms.lk_msg_template }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[lk_msg_template]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">注册成功消息模板：</label>
                    <div class="controls">
                        <input type="text" name="lk_msg_registe" id="lk_msg_registe" ${options.system_sms.lk_open=='on'?'':'readonly' }
                               value="${options.system_sms.lk_msg_registe }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[lk_msg_registe]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">是否开启凌凯短信：</label>
                    <div class="controls">
                        <input type="checkbox" name="lk_open"  ${options.system_sms.lk_open=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="lkOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[lk_open] </span>

                    </div>
                </div>
                <div class="form-actions">
                    <shiro:hasPermission name="user:userOptions:edit:">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>
            </form>
            <%--<form name="lkSmsForm" modelAttribute="optionValue"
                  action="${ctx}/user/userMsm/sendVerifyCode" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="mobile" value="18192079964"/>
                <div class="form-actions">
                    <shiro:hasPermission name="user:userOptions:edit:">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="测试短信"/>&nbsp;</shiro:hasPermission>
                </div>
            </form>--%>
        </div>

        <div class="layui-tab-item">
            <form name="appUpdateForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/saveAppVersionInfo" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_app_version"/>

                <blockquote class="layui-elem-quote">App版本设置</blockquote>
                <div class="control-group">
                    <label class="control-label">版本号: </label>
                    <div class="controls">
                        <input type="text" name="app_version" id="app_version" ${options.system_app_version.app_version_on=='on'?'':'readonly' }
                               value="${options.system_app_version.app_version}" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[app_version]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">提示信息: </label>
                    <div class="controls">
                        <input type="text" name="app_tips" id="app_tips" ${options.system_app_version.app_version_on=='on'?'':'readonly' }
                               value="${options.system_app_version.app_tips }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[app_tips]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">IOS下载地址: </label>
                    <div class="controls">
                        <input type="text" name="ios_download_url" id="ios_download_url" ${options.system_app_version.app_version_on=='on'?'':'readonly' }
                               value="${options.system_app_version.ios_download_url }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[ios_download_url]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Android下载地址: </label>
                    <div class="controls">
                        <input type="text" name="android_download_url" id="android_download_url" ${options.system_app_version.app_version_on=='on'?'':'readonly' }
                               value="${options.system_app_version.android_download_url }" htmlEscape="false"
                               class="input-xlarge required "/> <span class="help-inline"><font
                            color="red">*</font> 标识:[android_download_url]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">是否开启App版本设置：</label>
                    <div class="controls">
                        <input type="checkbox" name="app_version_on"  ${options.system_app_version.app_version_on=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="appVersionOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[app_version_on] </span>

                    </div>
                </div>
                <div class="form-actions">
                    <shiro:hasPermission name="user:userOptions:edit:">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>
            </form>
        </div>
    </div>
</div>


<script type="text/javascript">
    var smsForm = null;
    var appVersionForm = null;
    layui.use(['element', 'form', 'template'], function () {
        var element = layui.element();
        smsForm = layui.form("lkSmsForm");
        appVersionForm = layui.form("appUpdateForm");
        element.tabChange('optionlayuitab', '${selOptLabel}'); //假设当前地址为：http://a.com#test1=222，那么选项卡会自动切换到“发送消息”这一项

        smsForm.on('switch(lkOpenStatus)', function(data){
            if(data.elem.checked){
                $("#lk_address").removeAttr("readonly");
                $("#lk_acc").removeAttr("readonly");
                $("#lk_pwd").removeAttr("readonly");
                $("#lk_msg_template").removeAttr("readonly");
            }
            else{
                $("#lk_address").attr('readonly', 'readonly');
                $("#lk_acc").attr('readonly', 'readonly');
                $("#lk_pwd").attr('readonly', 'readonly');
                $("#lk_msg_template").attr('readonly', 'readonly');
            }
        });

        appVersionForm.on('switch(appVersionOpenStatus)', function(data){
            if(data.elem.checked){
                $("#app_version").removeAttr("readonly");
                $("#app_tips").removeAttr("readonly");
                $("#ios_download_url").removeAttr("readonly");
                $("#android_download_url").removeAttr("readonly");
            }
            else{
                $("#app_version").attr('readonly', 'readonly');
                $("#app_tips").attr('readonly', 'readonly');
                $("#ios_download_url").attr('readonly', 'readonly');
                $("#android_download_url").attr('readonly', 'readonly');
            }
        });
    });
</script>
</body>
</html>