<%--
  Created by IntelliJ IDEA.
  User: yankai
  Date: 2017/7/24
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>公众号基本配置</title>
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
        <div class="layui-tab-item">
            <form name="wechatConfigForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/saveWeChatConfig" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="wechat_config"/>

                <blockquote class="layui-elem-quote">基础配置</blockquote>
                <div class="control-group">
                    <label class="control-label">APP_ID: </label>
                    <div class="controls">
                        <input type="text" name="app_id" id="app_id"
                               value="${options.wechat_config.app_id}" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[app_id]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">APP_SECRET: </label>
                    <div class="controls">
                        <input type="text" name="app_secret" id="app_secret"
                               value="${options.wechat_config.app_secret }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[app_secret]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">AES_KEY: </label>
                    <div class="controls">
                        <input type="text" name="aes_key" id="aes_key"
                               value="${options.wechat_config.aes_key }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[aes_key]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">TOKEN: </label>
                    <div class="controls">
                        <input type="text" name="token" id="token"
                               value="${options.wechat_config.token }" htmlEscape="false"
                               class="input-xlarge required "/> <span class="help-inline"><font
                            color="red">*</font> 标识:[token]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">关注欢迎语: </label>
                    <div class="controls">
                        <input type="text" name="welcome" id="welcome"
                               value="${options.wechat_config.welcome }" htmlEscape="false"
                               class="input-xlarge required "/> <span class="help-inline"><font
                            color="red">*</font> 标识:[welcome]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">默认回复: </label>
                    <div class="controls">
                        <input type="text" name="default_reply" id="default_reply"
                               value="${options.wechat_config.default_reply }" htmlEscape="false"
                               class="input-xlarge required "/> <span class="help-inline"><font
                            color="red">*</font> 标识:[default_reply]</span>
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
    layui.use(['element', 'form', 'template'], function () {
        var element = layui.element();
        element.tabChange('optionlayuitab', '${selOptLabel}'); //假设当前地址为：http://a.com#test1=222，那么选项卡会自动切换到“发送消息”这一项

    });
</script>
</body>
</html>
