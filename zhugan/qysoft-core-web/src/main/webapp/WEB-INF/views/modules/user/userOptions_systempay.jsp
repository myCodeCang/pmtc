<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>配置管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("[name=weChatFrom]").validate({
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

            <form name="weChatFrom" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/savepay" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_weix"/>

                <blockquote class="layui-elem-quote">微信支付设置</blockquote>
                <div id="WeiXinS"  ${options.system_weix.weix_open=='on'?'':'style="display: none;"' }>
                <div class="control-group">
                    <label class="control-label">AppId：</label>
                    <div class="controls">
                        <input type="text" name="appId" id="appId" ${options.system_weix.weix_open=='on'?'':'readonly' }
                               value="${options.system_weix.appId }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[appId]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">AppSecret：</label>
                    <div class="controls">
                        <input type="text" name="appSecret" id="appSecret" ${options.system_weix.weix_open=='on'?'':'readonly' }
                               value="${options.system_weix.appSecret }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[appSecret]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">商户ID：</label>
                    <div class="controls">
                        <input type="text" name="shopId" id="shopId" ${options.system_weix.weix_open=='on'?'':'readonly' }
                               value="${options.system_weix.shopId }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[shopId]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">商户密钥：</label>
                    <div class="controls">
							<textarea name="shopKey" style="width: 500px;" id="shopKey" ${options.system_weix.weix_open=='on'?'':'readonly' }
                                      rows="5">${options.system_weix.shopKey }</textarea> <span
                            class="help-inline"><font
                            color="red">*</font> 标识:[shopKey]</span>
                    </div>
                </div>
                </div>
                <div class="control-group">
                    <label class="control-label">是否开启微信支付：</label>
                    <div class="controls">
                        <input type="checkbox" name="weix_open"  ${options.system_weix.weix_open=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="weixOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[weix_open] </span>

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
            <form name="alipayForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/savepay" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_zhifb"/>

                <blockquote class="layui-elem-quote">支付宝APP支付设置</blockquote>
                <div id="ZhiFuS" ${options.system_zhifb.zhifb_open=='on'?'':'style="display: none;"' }>
                <div class="control-group">
                    <label class="control-label">合伙人ID(Partner ID): </label>
                    <div class="controls">
                        <input type="text" name="partner_id" id="partner_id" ${options.system_zhifb.zhifb_open=='on'?'':'readonly' }
                               value="${options.system_zhifb.partner_id }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[partner_id]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">商户号(Seller): </label>
                    <div class="controls">
                        <input type="text" name="seller" id="seller" ${options.system_zhifb.zhifb_open=='on'?'':'readonly' }
                               value="${options.system_zhifb.seller }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[seller]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">开发者公钥: </label>
                    <div class="controls">
                        <textarea name="public_key" id="public_key" ${options.system_zhifb.zhifb_open=='on'?'':'readonly' }
                                  style="width: 500px;" rows="4">${options.system_zhifb.public_key }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[public_key]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">支付宝公钥: </label>
                    <div class="controls">
                        <textarea name="public_key_zhifb" id="public_key_zhifb" ${options.system_zhifb.zhifb_open=='on'?'':'readonly' }
                                  style="width: 500px;" rows="4">${options.system_zhifb.public_key_zhifb }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[public_key_zhifb]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">私钥: </label>
                    <div class="controls">
                        <textarea name="private_key" id="private_key" ${options.system_zhifb.zhifb_open=='on'?'':'readonly' }
                                  style="width: 500px;" rows="7">${options.system_zhifb.private_key }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[private_key]</span>
                    </div>
                </div>

                <blockquote class="layui-elem-quote">支付宝WAP支付设置</blockquote>
                <div class="control-group">
                    <label class="control-label">APP_ID: </label>
                    <div class="controls">
                        <input type="text" name="zfb_appid" id="zfb_appid" }
                               value="${options.system_zhifb.zfb_appid }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[zfb_appid]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">应用公钥: </label>
                    <div class="controls">
                        <textarea name="webdev_public_key" id="webdev_public_key" }
                                  style="width: 500px;" rows="4">${options.system_zhifb.webdev_public_key }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[webdev_public_key]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">支付宝公钥: </label>
                    <div class="controls">
                        <textarea name="web_public_key" id="web_public_key" }
                                  style="width: 500px;" rows="4">${options.system_zhifb.web_public_key }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[web_public_key]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">私钥: </label>
                    <div class="controls">
                        <textarea name="web_private_key" id="web_private_key" }
                                  style="width: 500px;" rows="7">${options.system_zhifb.web_private_key }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[web_private_key]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">WAP支付成功跳转地址: </label>
                    <div class="controls">
                        <input type="text" name="redirect_url" id="redirect_url"  }
                               value="${options.system_zhifb.redirect_url }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[redirect_url]</span>
                    </div>
                </div>
                </div>
                <%--<div class="control-group">
                    <label class="control-label">回调地址(notify_url): </label>
                    <div class="controls">
                        <input type="text" name="notify_url" id="notify_url" ${options.system_zhifb.zhifb_open=='on'?'':'readonly' }
                               value="${options.system_zhifb.notify_url }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[notify_url]</span>
                    </div>
                </div>--%>
                <div class="control-group">
                    <label class="control-label">是否开启支付宝支付：</label>
                    <div class="controls">
                        <input type="checkbox" name="zhifb_open"  ${options.system_zhifb.zhifb_open=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="zhifbOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[zhifb_open] </span>

                    </div>
                </div>

                <div class="form-actions">
                    <shiro:hasPermission name="user:userUserinfo:edit">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>
            </form>
        </div>

        <!-- 环迅支付 -->
        <div class="layui-tab-item">
            <form name="huanxunPayForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/savepay" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_huanxun"/>

                <blockquote class="layui-elem-quote">环迅支付设置</blockquote>
                <div id="HuanXunS" ${options.system_huanxun.huanxun_open=='on'?'':'style="display: none;"' }>
                <div class="control-group">
                    <label class="control-label">商户号: </label>
                    <div class="controls">
                        <input type="text" name="merchant_num" id="merchant_num"  }
                               value="${options.system_huanxun.merchant_num }" htmlEscape="false"
                               maxlength="64" class="input-xlarge required"/> <span
                            class="help-inline"><font color="red">*</font>
								标识:[merchant_num]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">交易账号: </label>
                    <div class="controls">
                        <input type="text" name="transaction_account" id="transaction_account" }
                               value="${options.system_huanxun.transaction_account }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[transaction_account]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">MD5证书: </label>
                    <div class="controls">
                        <textarea name="md5_cert" id="md5_cert"  }
                                  style="width: 500px;" rows="4">${options.system_huanxun.md5_cert }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[md5_cert]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">RSA公钥: </label>
                    <div class="controls">
                        <textarea name="rsa_key" id="rsa_key"  }
                                  style="width: 500px;" rows="4">${options.system_huanxun.rsa_key }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[rsa_key]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">支付请求地址: </label>
                    <div class="controls">
                        <input type="text" name="request_url" id="request_url"  }
                               value="${options.system_huanxun.request_url }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[request_url]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">支付请求参数: </label>
                    <div class="controls">
                        <input type="text" name="request_param" id="request_param"  }
                               value="${options.system_huanxun.request_param }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[request_param]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">WAP支付成功跳转地址: </label>
                    <div class="controls">
                        <input type="text" name="success_url" id="success_url"  }
                               value="${options.system_huanxun.success_url }" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[success_url]</span>
                    </div>
                </div>
                    <blockquote class="layui-elem-quote">环迅委托付款设置</blockquote>
                    <div class="control-group">
                        <label class="control-label">商户名: </label>
                        <div class="controls">
                            <input type="text" name="MerName" id="MerName"  }
                                   value="${options.system_huanxun.MerName }" htmlEscape="false"
                                   class="input-xlarge required"/> <span class="help-inline"><font
                                color="red">*</font> 标识:[MerName]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">版本号: </label>
                        <div class="controls">
                            <input type="text" name="version" id="version"  }
                                   value="${options.system_huanxun.version }" htmlEscape="false"
                                   class="input-xlarge required"/> <span class="help-inline"><font
                                color="red">*</font> 标识:[version]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">币种: </label>
                        <div class="controls">
                            <input type="text" name="currency" id="currency"  }
                                   value="${options.system_huanxun.currency }" htmlEscape="false"
                                   class="input-xlarge required"/> <span class="help-inline"><font
                                color="red">*</font> 标识:[currency]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">下发类型: </label>
                        <div class="controls">
                            <input type="text" name="BizId" id="BizId"  }
                                   value="${options.system_huanxun.BizId }" htmlEscape="false"
                                   class="input-xlarge required"/> <span class="help-inline"><font
                                color="red">*</font> 标识:[BizId]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">渠道类型: </label>
                        <div class="controls">
                            <input type="text" name="channel" id="channel"  }
                                   value="${options.system_huanxun.channel}" htmlEscape="false"
                                   class="input-xlarge required"/> <span class="help-inline"><font
                                color="red">*</font> 标识:[channel]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">付款请求地址: </label>
                        <div class="controls">
                            <input type="text" name="wsUrl" id="wsUrl"  }
                                   value="${options.system_huanxun.wsUrl }" htmlEscape="false"
                                   class="input-xlarge required"/> <span class="help-inline"><font
                                color="red">*</font> 标识:[wsUrl]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">查询请求地址: </label>
                        <div class="controls">
                            <input type="text" name="qwsUrl" id="qwsUrl"  }
                                   value="${options.system_huanxun.qwsUrl }" htmlEscape="false"
                                   class="input-xlarge required"/> <span class="help-inline"><font
                                color="red">*</font> 标识:[qwsUrl]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">3deskey: </label>
                        <div class="controls">
                            <textarea name="deskey" id="deskey"  }
                                      style="width: 500px;" rows="4">${options.system_huanxun.deskey}</textarea> <span
                                class="help-inline"><font color="red">*</font>
								标识:[deskey]</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">3desiv: </label>
                        <div class="controls">
                             <textarea name="desiv" id="desiv"  }
                                       style="width: 500px;" rows="4">${options.system_huanxun.desiv}</textarea> <span
                                class="help-inline"><font color="red">*</font>
								标识:[desiv]</span>
                        </div>
                    </div>

                </div>
                <div class="control-group">
                    <label class="control-label">是否开启环迅支付：</label>
                    <div class="controls">
                        <input type="checkbox" name="huanxun_open"  ${options.system_huanxun.huanxun_open=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="huanxunOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[huanxun_open] </span>
                    </div>
                </div>
                <div class="form-actions">
                    <shiro:hasPermission name="user:userUserinfo:edit">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>
            </form>
        </div>

        <%--线下转账--%>
        <div class="layui-tab-item">
            <form name="offLineForm" modelAttribute="optionValue"
                  action="${ctx}/user/userOptions/savepay" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_xxzz"/>

                <blockquote class="layui-elem-quote">线下转账设置</blockquote>
                <div id="XianXiaS" ${options.system_xxzz.xxzz_open=='on'?'':'style="display: none;"' }>
                <div class="control-group">
                    <label class="control-label">转账备注: </label>
                    <div class="controls">
                        <textarea name="xxzz_comnt" style="width: 500px;" rows="4">${options.system_xxzz.xxzz_comnt }</textarea> <span
                            class="help-inline"><font color="red">*</font>
								标识:[xxzz_comnt]</span>
                    </div>
                </div>
                </div>
                <div class="control-group">
                    <label class="control-label">是否开启线下转账：</label>
                    <div class="controls">
                        <input type="checkbox" name="xxzz_open"  ${options.system_xxzz.xxzz_open=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="offLineFormStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[xxzz_open] </span>

                    </div>
                </div>

                <div class="form-actions">
                    <shiro:hasPermission name="user:userUserinfo:edit">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>
            </form>
        </div>

    </div>
</div>
    </div>
</div>


<script type="text/javascript">
    var wechatForm = null;
    var alipayForm = null;
    var huanxunPayForm = null;
    var offLineForm = null;
    layui.use(['element','form','template'], function () {
        var element = layui.element();
        //var form = layui.form();
        wechatForm = layui.form("weChatFrom");
        alipayForm = layui.form("alipayForm");
        huanxunPayForm = layui.form("huanxunPayForm");
        offLineForm = layui.form("offLineForm");
        element.tabChange('optionlayuitab', '${selOptLabel}'); //假设当前地址为：http://a.com#test1=222，那么选项卡会自动切换到“发送消息”这一项

        wechatForm.on('switch(weixOpenStatus)', function(data){
            if(data.elem.checked){
                $("#WeiXinS").show();
                $("#appId").removeAttr("readonly");
                $("#appSecret").removeAttr("readonly");
                $("#shopId").removeAttr("readonly");
                $("#shopKey").removeAttr("readonly");
            }
            else{
                $("#WeiXinS").hide();
                $("#appId").attr('readonly', 'readonly');
                $("#appSecret").attr('readonly', 'readonly');
                $("#shopId").attr('readonly', 'readonly');
                $("#shopKey").attr('readonly', 'readonly');
            }
        });

        alipayForm.on('switch(zhifbOpenStatus)', function(data){
            if(data.elem.checked){
                $("#ZhiFuS").show();
                $("#partner_id").removeAttr("readonly");
                $("#seller").removeAttr("readonly");
                $("#public_key").removeAttr("readonly");
                $("#public_key_zhifb").removeAttr("readonly");
                $("#private_key").removeAttr("readonly");
                $("#notify_url").removeAttr("readonly");
            }
            else{
                $("#ZhiFuS").hide();
                $("#partner_id").attr('readonly', 'readonly');
                $("#seller").attr('readonly', 'readonly');
                $("#public_key").attr('readonly', 'readonly');
                $("#public_key_zhifb").attr('readonly', 'readonly');
                $("#private_key").attr('readonly', 'readonly');
                $("#notify_url").attr('readonly', 'readonly');
            }
        });

        huanxunPayForm.on('switch(huanxunOpenStatus)', function(data){
            if(data.elem.checked){
                $("#HuanXunS").show();

            }
            else{
                $("#HuanXunS").hide();

            }
        });

        offLineForm.on('switch(offLineFormStatus)', function(data){
            if(data.elem.checked){
                $("#XianXiaS").show();

            }
            else{
                $("#XianXiaS").hide();

            }
        });
    });
</script>
</body>
</html>