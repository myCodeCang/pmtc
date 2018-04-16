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
            <%--if(${options.system_trans.trans_open}){--%>
                <%--$("#isOpenSwitch").attr("checked",true);--%>
                <%--$("#transOpenSetDiv").hide();--%>
            <%--}else{--%>
                <%--$("#isOpenSwitch").attr("checked",false);--%>
                <%--$("#transOpenSetDiv").show();--%>
            <%--}--%>
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
                  action="${ctx}/user/userOptions/saveHelpGroup" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_help"/>
                <blockquote class="layui-elem-quote">基本配置</blockquote>

                <div class="control-group">
                    <label class="control-label" id="lables">是否开启内测模式：</label>
                    <div class="controls">
                        <input type="checkbox" name="isClosedBeta"  ${options.system_help.isClosedBeta=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="shopOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[isClosedBeta] </span>

                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" id="lable2">是否发直推奖励：</label>
                    <div class="controls">
                        <input type="checkbox" name="putDirectAward"  ${options.system_help.putDirectAward=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="shopOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[putDirectAward] </span>

                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" id="lable">直推奖励是否开启首次发放：</label>
                    <div class="controls">
                        <input type="checkbox" name="firstDirectAward"  ${options.system_help.firstDirectAward=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭" lay-filter="shopOpenStatus">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[firstDirectAward] </span>

                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">动态、矿机转交易手续费配置：</label>
                    <div class="controls">
                        <input type="number" name="poundage_wallet"
                               value="${options.system_help.poundage_wallet }" htmlEscape="false"
                               class="input-xlarge required" step="0.01"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[poundage_wallet] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">矿机钱包封顶配置：</label>
                    <div class="controls">
                        <input type="number" name="money2_max"
                               value="${options.system_help.money2_max }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[money2_max] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">动态钱包每日进账比例配置：</label>
                    <div class="controls">
                        <input type="number" name="money3_scale"
                               value="${options.system_help.money3_scale }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[money3_scale] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">[矿机+动态]钱包每日转入交易钱包比例配置：</label>
                    <div class="controls">
                        <input type="number" name="money2_scale"
                               value="${options.system_help.money2_scale }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[money2_scale] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">投资钱包进账比例配置：</label>
                    <div class="controls">
                        <input type="number" name="money4_scale"
                               value="${options.system_help.money4_scale }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[money4_scale] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">总钱包互转金额倍数配置(会员内部钱包互转)：</label>
                    <div class="controls">
                        <input type="number" name="multiple"
                               value="${options.system_help.multiple }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[multiple] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">总钱包外部转账金额倍数配置：</label>
                    <div class="controls">
                        <input type="number" name="outMultiple"
                               value="${options.system_help.outMultiple }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[outMultiple] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">每日会员之间交易钱包互转额度为矿机钱包余额的倍数配置：</label>
                    <div class="controls">
                        <input type="number" name="account_multiple"
                               value="${options.system_help.account_multiple }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[account_multiple] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">团队奖从多少层开始计算:</label>
                    <div class="controls">
                        <input type="number" name="teamBonus_noBegin"
                               value="${options.system_help.teamBonus_noBegin }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[teamBonus_noBegin] </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">团队级别本人最低持币数限制:</label>
                    <div class="controls">
                        <input type="number" name="teamLevel_money"
                               value="${options.system_help.teamLevel_money }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[teamLevel_money] </span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">团队级别直推人数限制:</label>
                    <div class="controls">
                        <input type="number" name="teamLevel_ztNum"
                               value="${options.system_help.teamLevel_ztNum }" htmlEscape="false"
                               class="input-xlarge required"/>
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[teamLevel_ztNum] </span>
                    </div>
                </div>


                    <div class="control-group">
                        <label class="control-label">禁止转账开始时间：</label>
                        <div class="controls">
                            <input name="transfer_begin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                   value="${options.system_help.transfer_begin}"
                                   onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[transfer_begin] </span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">禁止转账结束时间：</label>
                        <div class="controls">
                            <input name="transfer_end" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                   value="${options.system_help.transfer_end}"
                                   onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[transfer_end] </span>
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
    var messageRemindForm = null;
    layui.use(['element', 'form', 'template'], function () {
        var element = layui.element();

        messageRemindForm = layui.form("messageRemindForm");
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

        messageRemindForm.on('switch(shortMessageStatus)', function(data){
            if(data.elem.checked){
                $("#remind").show();

            }
            else{
                $("#remind").hide();

            }
        });
    });
</script>
</body>
</html>