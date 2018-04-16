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
            if(${options.system_trans.trans_open}){
                $("#isOpenSwitch").attr("checked",true);
                $("#transOpenSetDiv").hide();
            }else{
                $("#isOpenSwitch").attr("checked",false);
                $("#transOpenSetDiv").show();
            }
        });


    </script>


</head>
<body>
<sys:message content="${message}"/>
<div class="layui-tab layui-tab-brief" lay-filter="optionlayuitab">
    <%--<ul class="layui-tab-title">--%>
        <%--<c:forEach items="${optionList}" var="dic" varStatus="status">--%>

            <%--<li lay-id="${dic.optionName}"--%>
                <%--class="${dic.optionName  eq selOptLabel ? 'layui-this':''}">${dic.optionLabel } [${dic.optionName }]--%>
            <%--</li>--%>
        <%--</c:forEach>--%>
    <%--</ul>--%>
    <div class="layui-tab-content">

        <div class="layui-tab-item layui-show">

            <form name="inputForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/savetransGroup" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="trans_price"/>

                <blockquote class="layui-elem-quote">基本配置</blockquote>
                <div class="control-group">
                    <label class="control-label">最新价格：</label>
                    <div class="controls">
                        <input type="text" name="nowPrice"
                               value="${options.trans_price.nowPrice }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[nowPrice] </span>
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
        </div>
        <div class="layui-tab-item">其他设置</div>
        <div class="layui-tab-item">4</div>
        <div class="layui-tab-item">5</div>
        <div class="layui-tab-item">6</div>
    </div>
</div>



<script type="text/javascript">
    var form = null;
    layui.use(['element', 'form', 'template'], function () {
        var element = layui.element();
        form = layui.form();
        element.tabChange('optionlayuitab', '${selOptLabel}'); //假设当前地址为：http://a.com#test1=222，那么选项卡会自动切换到“发送消息”这一项


        form.on('switch(shopOpenStatus)', function(data){
            if(data.elem.checked){
                $("#transOpenSetDiv").hide();

                $("#istransopen").val("1");
            }
            else{
                $("#transOpenSetDiv").show();
                $("#istransopen").val("0");
            }
        });
    });
</script>
</body>
</html>