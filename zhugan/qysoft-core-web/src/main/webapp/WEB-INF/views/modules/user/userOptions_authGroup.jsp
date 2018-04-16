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
                  action="${ctx}/user/userOptions/saveauthGroup" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="auth_shop"/>

                <blockquote class="layui-elem-quote">基本配置</blockquote>

                <input type="hidden" value="" name="auth_shop_ids" id="auth_shop_ids">

                <div class="control-group">
                    <label class="control-label" id="lables">整合玩淘宝商城：</label>
                    <div class="controls">
                        <input type="checkbox" name="auth_open"  ${options.auth_shop.auth_open=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="shopOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[auth_open] </span>

                    </div>
                </div>

                <fieldset class="layui-elem-field" id="shopOpenSetDiv" style="${options.auth_shop.auth_open=='on'?'':'display:none;' }">
                    <legend>详情配置</legend>
                    <div class="layui-field-box">

                        <div class="control-group">
                            <label class="control-label">商城通信地址：</label>
                            <div class="controls">
                                <input type="text" id="shopUrl" name="url"
                                       value="${options.auth_shop.url }" htmlEscape="false"
                                       class="input-xlarge required"/> <span class="help-inline"><font
                                    color="red">*</font> 标识:[url] </span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">通信密钥：</label>
                            <div class="controls">
                                <input type="text"  name="authpwd" id="authpwd"
                                       value="${options.auth_shop.authpwd }" htmlEscape="false"
                                       class="input-xlarge required"/> <span class="help-inline"><font
                                    color="red">*</font> 标识:[authpwd] </span>

                                <button class="layui-btn layui-btn-normal layui-btn-small " onclick="return randomKey()">随机生成</button>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"></label>
                            <div class="controls">
                                <button class="layui-btn  layui-btn-normal  layui-btn-small" id="addAuthShopParamBtn">
                                    添加通信参数配置
                                </button>
                            </div>
                        </div>

                        <div id="temp_auth_shop_param">

                            <c:forEach items="${options.auth_shop}" var="dic" varStatus="status">
                                <c:if test="${fn:indexOf(dic.key,\"shopid_\") ge 0}">
                                    <c:set var="shopidval" value="${fn:split(dic.key,\"_\" )[1]}"></c:set>

                                    <div id="temp_auth_shop_detail_${shopidval}">

                                        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
                                            <legend>通信参数配置-商城编号[${shopidval}] &nbsp;&nbsp;
                                                <button class="layui-btn layui-btn-normal layui-btn-small"
                                                        data="${shopidval}"
                                                        onclick="return checkAuthShopFun('${shopidval}')">通信检测
                                                </button>
                                                <button class="layui-btn layui-btn-small layui-btn-danger"
                                                        data="${shopidval}"
                                                        onclick="return deleteAuthShopFun('${shopidval}')">删除配置
                                                </button>
                                            </legend>
                                        </fieldset>

                                        <div class="control-group">
                                            <label class="control-label">商城编号：</label>
                                            <div class="controls">
                                                <input type="text" id="shopid_${shopidval}" name="shopid_${shopidval}"
                                                       value="${options.auth_shop['shopid_'.concat(shopidval)] }"
                                                       htmlEscape="false"
                                                       class="input-xlarge required" readonly/> <span
                                                    class="help-inline"><font
                                                    color="red">*</font> 标识:[shopid_${shopidval}] </span>
                                            </div>
                                        </div>

                                        <div class="control-group">
                                            <label class="control-label">商城名称：</label>
                                            <div class="controls">
                                                <input type="text" name="shopName_${shopidval}"
                                                       value="${options.auth_shop['shopName_'.concat(shopidval)] }"
                                                       htmlEscape="false"
                                                       class="input-xlarge required" readonly/> <span
                                                    class="help-inline"><font
                                                    color="red">*</font> 标识:[shopName_${shopidval}] </span>
                                            </div>
                                        </div>

                                        <div class="control-group">
                                            <label class="control-label">商城Key：</label>
                                            <div class="controls">
                                                <input type="text" name="shopKey_${shopidval}"
                                                       value="${options.auth_shop['shopKey_'.concat(shopidval)] }"
                                                       htmlEscape="false"
                                                       class="input-xlarge required"/> <span
                                                    class="help-inline"><font
                                                    color="red">*</font> 标识:[shopKey_${shopidval}] </span>
                                            </div>
                                        </div>

                                        <div class="control-group">
                                            <label class="control-label">同步金钱：</label>
                                            <div class="controls">
                                                <input type="checkbox" ${options.auth_shop['syncMoney_'.concat(shopidval)] == "on"?"checked":""}
                                                       lay-skin="switch" name="syncMoney_${shopidval}" lay-text="开启|关闭">
                                                <span class="help-inline"><font
                                                        color="red">*</font> 标识:[syncMoney_${shopidval}] </span>
                                            </div>
                                        </div>

                                        <div class="control-group">
                                            <label class="control-label">同步积分：</label>
                                            <div class="controls">
                                                <input type="checkbox" ${options.auth_shop['syncScore_'.concat(shopidval)] == "on"?"checked":""}
                                                       lay-skin="switch" name="syncScore_${shopidval}" lay-text="开启|关闭">
                                                <span class="help-inline"><font
                                                        color="red">*</font> 标识:[syncScore_${shopidval}] </span>

                                                <select name="syncScoreField_${shopidval}" style="margin-left: 10px" lay-ignore>
                                                    <c:forEach items="${fns:getDictList('sync_score_field')}" var="dic">
                                                        <option value="${dic.value }"
                                                            ${options.auth_shop['syncScoreField_'.concat(shopidval)] eq dic.value ? 'selected':''}>${dic.label }</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="help-inline"><font color="red">*</font>标识:[syncScoreField_${shopidval}]</span>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </fieldset>

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

<script type="text/html" id="tpl_auth_shop_param">

    <div id="temp_auth_shop_detail_{{shopid}}">

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
            <legend>通信参数配置-商城编号[{{shopid}}] &nbsp;&nbsp;
                <button class="layui-btn layui-btn-normal layui-btn-small" data="{{shopid}}"
                        onclick="return checkAuthShopFun('{{shopid}}')">通信检测
                </button>
                <button class="layui-btn layui-btn-small layui-btn-danger" data="{{shopid}}"
                        onclick="return deleteAuthShopFun('{{shopid}}')">删除配置
                </button>
            </legend>
        </fieldset>

        <div class="control-group">
            <label class="control-label">商城编号：</label>
            <div class="controls">
                <input type="text" id="shopid_{{shopid}}" name="shopid_{{shopid}}"
                       value="{{shopid}}" htmlEscape="false"
                       class="input-xlarge required" readonly/> <span class="help-inline"><font
                    color="red">*</font> 标识:[shopid_{{shopid}}] </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">商城名称：</label>
            <div class="controls">
                <input type="text" id="shopName_{{shopid}}" name="shopName_{{shopid}}"
                       value="" htmlEscape="false"
                       class="input-xlarge required" readonly/> <span class="help-inline"><font
                    color="red">*</font> 标识:[shopName_{{shopid}}] </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">商城Key：</label>
            <div class="controls">
                <input type="text" id="shopKey_{{shopid}}" name="shopKey_{{shopid}}"
                       value="" htmlEscape="false"
                       class="input-xlarge required"/> <span class="help-inline"><font
                    color="red">*</font> 标识:[shopKey_{{shopid}}] </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">同步金钱：</label>
            <div class="controls">
                <input type="checkbox" lay-skin="switch" name="syncMoney_{{shopid}}" lay-text="开启|关闭"> <span
                    class="help-inline"><font
                    color="red">*</font> 标识:[syncMoney_{{shopid}}] </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">同步积分：</label>
            <div class="controls">
                <input type="checkbox" lay-skin="switch" name="syncScore_{{shopid}}" lay-text="开启|关闭"> <span
                    class="help-inline"><font
                    color="red">*</font> 标识:[syncScore_{{shopid}}] </span>

                <select name="syncScoreField_{{shopid}}" style="margin-left: 10px" lay-ignore>
                    <c:forEach items="${fns:getDictList('sync_score_field')}" var="dic">
                        <option value="${dic.value}">${dic.label }</option>
                    </c:forEach>
                </select>
                <span class="help-inline"><font color="red">*</font>标识:[syncScoreField_{{shopid}}]</span>
            </div>
        </div>
    </div>
</script>


<script type="text/javascript">
    var form = null;
    layui.use(['element', 'form', 'template', 'qyForm'], function () {
        var element = layui.element();
        form = layui.form();
        element.tabChange('optionlayuitab', '${selOptLabel}'); //假设当前地址为：http://a.com#test1=222，那么选项卡会自动切换到“发送消息”这一项

        form.on('switch(shopOpenStatus)', function(data){
             if(data.elem.checked){
                 $("#shopOpenSetDiv").show();
             }
             else{
                 $("#shopOpenSetDiv").hide();
             }
        });
    });


    $('#addAuthShopParamBtn').bind("click", function (event) {
        layer.prompt({title: '请输入正确的商城编号'}, function (value, index, elem) {

            if ($("#shopid_" + value)[0]) {
                layer.close(index);
                layer.msg('不可添加已经存在的编号!', {icon: 5});
                return false;
            }

            var data = {
                "shopid": value,
                "shopName": "测试商城",
            };
            var result = template("tpl_auth_shop_param", data);
            $("#temp_auth_shop_param").append(result);

            form.render('checkbox'); //刷新select选择框渲染
            $('select').select2();
            layer.close(index);
        });

        return false;
    });


    //删除配置
    function deleteAuthShopFun(shopid) {
        $("#temp_auth_shop_detail_" + shopid).remove();
        return false;
    }

    function randomKey(){
        $("#authpwd").val(uuid());
        return false;
    }

    function checkAuthShopFun(shopid){
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/api/shopucapi/checkShopApi",
            data: {
                "shopId" : shopid
            },
            dataType: "json",
            success: function(data){
                if(data && data.status == 1){
                    layer.msg('恭喜,通信连接成功!');
                    $("#shopName_" + shopid).val(data.data.data);
                } else {
                    layer.msg(data.data.errMsg, {icon: 5});
                }
            },
            error :function(XMLHttpRequest, textStatus, errorThrown){
                layer.msg('通信失败,请检查配置!', {icon: 5});
                return false;
            }
        });


        return false;
    }


    function uuid() {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";

        var uuid = s.join("");
        return uuid;
    }


</script>
</body>
</html>