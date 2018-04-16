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
                  action="${ctx}/user/userOptions/savetransGroup" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_trans"/>

                <blockquote class="layui-elem-quote">基本配置</blockquote>
                <div class="control-group">
                    <label class="control-label">产品名称：</label>
                    <div class="controls">
                        <input type="text" name="trans_name"
                               value="${options.system_trans.trans_name }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[trans_name] </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">拍卖保证金：</label>
                    <div class="controls">
                        <input type="text" name="auction_money"
                               value="${options.system_trans.auction_money }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[auction_money] </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">积分兑换配置：</label>
                    <div class="controls">
                        <input type="text" name="attorn_scale"
                               value="${options.system_trans.attorn_scale }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[attorn_scale] </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">转让交易天数配置（单位：天）：</label>
                    <div class="controls">
                        <input type="text" name="attorn_days"
                               value="${options.system_trans.attorn_days }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[attorn_days] </span>
                    </div>
                </div>
                <input type="hidden" value="" name="system_trans_ids" id="system_trans_ids">
                <div class="control-group">
                    <label class="control-label" id="lables">交易配置：</label>
                    <div class="controls">
                        <input type="hidden"  name="trans_open" value="${options.system_trans.trans_open}" id="istransopen"/>
                        <input type="checkbox"  lay-skin="switch" lay-text="开启|关闭" lay-filter="shopOpenStatus" id="isOpenSwitch">
                        <span class="help-inline"><font color="red">*</font> 标识:[trans_open]</span>

                    </div>
                </div>

                <fieldset class="layui-elem-field" id="transOpenSetDiv" style="${options.system_trans.trans_open=='1'?'':'display:none;' }">
                    <legend>关闭原因</legend>
                    <div class="layui-field-box">
                        <div class="control-group">
                            <textarea name="system_trans_reason" required lay-verify="required" placeholder="请输入" class="layui-textarea">${options.system_trans.system_trans_reason}</textarea>
                        </div>
                    </div>
                </fieldset>

                <fieldset class="layui-elem-field">
                    <legend>交易起始时间配置</legend>
                    <div class="layui-field-box">
                        <div class="control-group">
                            <label class="control-label">交易开始时间：</label>
                            <div class="controls">
                                <input name="trans_time_begin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                        value="${options.system_trans.trans_time_begin}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[trans_time_begin] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">交易结束时间：</label>
                            <div class="controls">
                                <input name="trans_time_end" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.system_trans.trans_time_end}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[trans_time_end] </span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">交易开始时间2：</label>
                            <div class="controls">
                                <input name="trans_time_begin2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.system_trans.trans_time_begin2}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[trans_time_begin2] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">交易结束时间2：</label>
                            <div class="controls">
                                <input name="trans_time_end2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.system_trans.trans_time_end2}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[trans_time_end2] </span>
                            </div>
                        </div>

                    </div>
                </fieldset>
                <legend >价格波动配置</legend>
                <div class="layui-field-box">
                    <div class="control-group">
                        <label class="control-label" >价格发布限制类型：</label>
                        <div class="controls">
                            <input type="radio" name="trans_float_type" value="base" title="基数限制" ${options.system_trans.trans_float_type == 'base'?'checked':''}>
                            <input type="radio" name="trans_float_type" value="float" title="浮动限制" ${options.system_trans.trans_float_type == 'float'?'checked':''}>
                            <span class="help-inline"><font color="red">*</font> 标识:[trans_float_type] (基数限制是初始价格乘百分比,浮动限制是实时数据乘百分比)</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">发布上限：</label>
                        <div class="controls">
                            <input type="number" name="trans_up_float"
                                   value="${options.system_trans.trans_up_float }" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[trans_up_float] </span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">发布下限：</label>
                        <div class="controls">
                            <input type="number" name="trans_down_float"
                                   value="${options.system_trans.trans_down_float}" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[trans_down_float] </span>
                        </div>
                    </div>
                </div>

                <legend >交易振幅封顶配置</legend>
                <div class="layui-field-box">
                    <div class="control-group">
                        <label class="control-label">发布上浮：</label>
                        <div class="controls">
                            <input type="number" name="zhen_trans_up_float"
                                   value="${options.system_trans.zhen_trans_up_float }" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[zhen_trans_up_float] </span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">发布下浮：</label>
                        <div class="controls">
                            <input type="number" name="zhen_trans_down_float"
                                   value="${options.system_trans.zhen_trans_down_float}" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[zhen_trans_down_float] </span>
                        </div>
                    </div>
                </div>

                <legend >手续费配置</legend>
                <div class="layui-field-box">
                    <div class="control-group">
                        <label class="control-label">订货仓库保管费：</label>
                        <div class="controls">
                            <input type="number" name="trans_apply_shopMoney"
                                   value="${options.system_trans.trans_apply_shopMoney }" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[trans_apply_shopMoney] </span>
                        </div>
                    </div>
                    <%--<div class="control-group">--%>
                        <%--<label class="control-label">盈利积分百分比：</label>--%>
                        <%--<div class="controls">--%>
                            <%--<input type="number" name="profit_score"--%>
                                   <%--value="${options.system_trans.profit_score }" htmlEscape="false"--%>
                                   <%--class="input-xlarge required"/>--%>
                            <%--<span class="help-inline"><font--%>
                                    <%--color="red">*</font> 标识:[profit_score] </span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="control-group">--%>
                        <%--<label class="control-label">亏损积分百分比：</label>--%>
                        <%--<div class="controls">--%>
                            <%--<input type="number" name="loss_score"--%>
                                   <%--value="${options.system_trans.loss_score}" htmlEscape="false"--%>
                                   <%--class="input-xlarge required"/>--%>
                            <%--<span class="help-inline"><font--%>
                                    <%--color="red">*</font> 标识:[loss_score] </span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="control-group">
                        <label class="control-label">手续费百分比(买家)：</label>
                        <div class="controls">
                            <input type="number" name="buy_procedure_money"
                                   value="${options.system_trans.buy_procedure_money}" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[buy_procedure_money] </span>
                        </div>

                    </div>
                    <div class="control-group">
                        <label class="control-label">手续费百分比(卖家)：</label>
                        <div class="controls">
                            <input type="number" name="sell_procedure_money"
                                   value="${options.system_trans.sell_procedure_money}" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[sell_procedure_money] </span>
                        </div>
                    </div>

                    <%--<legend >奖励提成配置</legend>--%>
                    <%--<div class="layui-field-box">--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">购买字画提成：</label>--%>
                            <%--<div class="controls">--%>
                                <%--<input type="number" name="buy_goods_bonus"--%>
                                       <%--value="${options.system_trans.buy_goods_bonus }" htmlEscape="false"--%>
                                       <%--class="input-xlarge required"/>--%>
                                <%--<span class="help-inline"><font--%>
                                        <%--color="red">*</font> 标识:[buy_goods_bonus] </span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">认购字画提成：</label>--%>
                            <%--<div class="controls">--%>
                                <%--<input type="number" name="apply_goods_bonus"--%>
                                       <%--value="${options.system_trans.apply_goods_bonus}" htmlEscape="false"--%>
                                       <%--class="input-xlarge required"/>--%>
                                <%--<span class="help-inline"><font--%>
                                        <%--color="red">*</font> 标识:[apply_goods_bonus] </span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">手续费提成：</label>--%>
                            <%--<div class="controls">--%>
                                <%--<input type="number" name="procedure_money_bonus"--%>
                                       <%--value="${options.system_trans.procedure_money_bonus}" htmlEscape="false"--%>
                                       <%--class="input-xlarge required"/>--%>
                                <%--<span class="help-inline"><font--%>
                                        <%--color="red">*</font> 标识:[procedure_money_bonus] </span>--%>
                            <%--</div>--%>

                        <%--</div>--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">商城消费提成：</label>--%>
                            <%--<div class="controls">--%>
                                <%--<input type="number" name="shop_bonus"--%>
                                       <%--value="${options.system_trans.shop_bonus}" htmlEscape="false"--%>
                                       <%--class="input-xlarge required"/>--%>
                                <%--<span class="help-inline"><font--%>
                                        <%--color="red">*</font> 标识:[shop_bonus] </span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                <%--</div>--%>

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