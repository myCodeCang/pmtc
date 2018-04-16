<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 2017/7/30
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>微信消息群发</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("[name=inputForm]").validate({
                submitHandler: function (form) {
                    loading('正在发送，请稍等...');
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
            <form name="textForm" modelAttribute="optionValue"
                  action="${ctx}/wechat/msgSend/sendText" method="post"
                  class="form-horizontal">
                <input type="hidden" name="groupName" value="wx_msg_group"/>
                <input type="hidden" name="optLabel" value="文本消息"/>

                <blockquote class="layui-elem-quote">文本消息群发</blockquote>
                <div class="control-group">
                    <label class="control-label">消息内容：</label>
                    <div class="controls">
                        <textarea type="text" name="text_content" style="width: 500px;" rows="10"
                                  htmlEscape="false"
                                  class="input-xlarge required"></textarea><span
                            class="help-inline"><font color="red">*</font>
								</span>
                    </div>
                </div>

                <div class="form-actions">
                    <input id="textSubmit" class="btn btn-primary" type="submit"
                           value="发送消息"/>
                </div>
            </form>
        </div>

        <%--<div class="layui-tab-item">
            <form name="mixForm" modelAttribute="optionValue"
                  action="${ctx}/wechat/msgSend/sendPic" method="post"
                  class="form-horizontal">
                <input type="hidden" name="groupName" value="wx_msg_group"/>
                <input type="hidden" name="optLabel" value="图文消息"/>

                <blockquote class="layui-elem-quote">图文消息群发</blockquote>
                <div class="control-group">
                    <label class="control-label">消息内容：</label>
                    <div class="controls">
                        <textarea type="text" name="text_content" style="width: 500px;" rows="10"
                                  htmlEscape="false"
                                  class="input-xlarge required"></textarea><span
                            class="help-inline"><font color="red">*</font>
								</span>
                    </div>
                </div>

                <div class="form-actions">
                    <input id="mixSubmit" class="btn btn-primary" type="submit"
                           value="发送消息"/>
                </div>
            </form>
        </div>--%>

        <div class="layui-tab-item">
            <form name="picForm"
                  action="${ctx}/wechat/msgSend/sendPic" method="post"
                  class="form-horizontal">
                <input type="hidden" name="groupName" value="wx_msg_group"/>
                <input type="hidden" name="optLabel" value="图片消息"/>
                <input type="hidden" name="picUrl" value="" id="picPath"/>

                <blockquote class="layui-elem-quote">图片消息群发</blockquote>
                <div class="control-group">
                    <label class="control-label">图片: </label>
                    <div class="controls">
                        <sys:ckfinder input="picPath" type="images" uploadPath="/wechat" selectMultiple="false" maxWidth="100" maxHeight="100"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
                </div>

                <div class="form-actions">
                    <input id="picSubmit" class="btn btn-primary" type="submit"
                           value="发送消息"/>
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
