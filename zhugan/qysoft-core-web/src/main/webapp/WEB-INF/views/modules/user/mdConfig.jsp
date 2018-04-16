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
                  action="${ctx}/user/userOptions/savemdConfig" method="post"
                  class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="md_config"/>
                <fieldset class="layui-elem-field">
                    <legend>交易基础配置</legend>
                    <div class="control-group">
                        <label class="control-label">撮合交易天数配置（单位：天）：</label>
                        <div class="controls">
                            <input type="text" name="marry_days"
                                   value="${options.md_config.marry_days }" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[marry_days] </span>
                        </div>
                    </div>
                    <label class="control-label"> 撮合交易日配置：</label>
                    <input type="checkbox" name="Sunday" title="星期日" ${options.md_config.Sunday == 'on'?'checked':''}>
                    <input type="checkbox" name="Monday" title="星期一" ${options.md_config.Monday == 'on'?'checked':''}>
                    <input type="checkbox" name="Tuesday" title="星期二" ${options.md_config.Tuesday == 'on'?'checked':''}>
                    <input type="checkbox" name="Wensday" title="星期三" ${options.md_config.Wensday == 'on'?'checked':''}>
                    <input type="checkbox" name="Thursday" title="星期四" ${options.md_config.Thursday == 'on'?'checked':''}>
                    <input type="checkbox" name="Friday" title="星期五" ${options.md_config.Friday == 'on'?'checked':''}>
                    <input type="checkbox" name="Saturday" title="星期六" ${options.md_config.Saturday == 'on'?'checked':''}>
                </fieldset>
                <fieldset class="layui-elem-field">
                    <legend>交易起始时间配置</legend>
                    <div class="layui-field-box">
                        <div class="control-group">
                            <label class="control-label">交易开始时间：</label>
                            <div class="controls">
                                <input name="md_time_begin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                        value="${options.md_config.md_time_begin}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[md_time_begin] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">交易结束时间：</label>
                            <div class="controls">
                                <input name="md_time_end" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.md_config.md_time_end}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[md_time_end] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">交易开始时间2：</label>
                            <div class="controls">
                                <input name="md_time_begin2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.md_config.md_time_begin2}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[md_time_begin2] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">交易结束时间2：</label>
                            <div class="controls">
                                <input name="md_time_end2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.md_config.md_time_end2}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[md_time_end2] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">交易开始时间3：</label>
                            <div class="controls">
                                <input name="md_time_begin3" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.md_config.md_time_begin3}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[md_time_begin3] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">交易结束时间3：</label>
                            <div class="controls">
                                <input name="md_time_end3" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.md_config.md_time_end3}"
                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[md_time_end3] </span>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <%--<fieldset class="layui-elem-field">--%>
                <%--<legend >价格波动配置</legend>--%>
                <%--<div class="layui-field-box">--%>
                    <%--<div class="control-group">--%>
                        <%--<label class="control-label" >价格发布限制类型：</label>--%>
                        <%--<div class="controls">--%>
                            <%--<input type="radio" name="md_float_type" value="base" title="基数限制" ${options.md_config.md_float_type == 'base'?'checked':''}>--%>
                            <%--<input type="radio" name="md_float_type" value="float" title="浮动限制" ${options.md_config.md_float_type == 'float'?'checked':''}>--%>
                            <%--<span class="help-inline"><font color="red">*</font> 标识:[md_float_type] (基数限制是初始价格乘百分比,浮动限制是实时数据乘百分比)</span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="control-group">--%>
                        <%--<label class="control-label">发布上限：</label>--%>
                        <%--<div class="controls">--%>
                            <%--<input type="number" name="md_up_float"--%>
                                   <%--value="${options.md_config.md_up_float }" htmlEscape="false"--%>
                                   <%--class="input-xlarge required"/>--%>
                            <%--<span class="help-inline"><font--%>
                                    <%--color="red">*</font> 标识:[md_up_float] </span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="control-group">--%>
                        <%--<label class="control-label">发布下限：</label>--%>
                        <%--<div class="controls">--%>
                            <%--<input type="number" name="md_down_float"--%>
                                   <%--value="${options.md_config.md_down_float}" htmlEscape="false"--%>
                                   <%--class="input-xlarge required"/>--%>
                            <%--<span class="help-inline"><font--%>
                                    <%--color="red">*</font> 标识:[md_down_float] </span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--</fieldset>--%>
                <fieldset class="layui-elem-field">
                    <legend >出金配置</legend>
                    <div class="layui-field-box">

                        <div class="control-group">
                            <label class="control-label">出金开始时间：</label>
                            <div class="controls">
                                <input name="cj_time_begin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                       value="${options.md_config.cj_time_begin}"
                                       onclick="WdatePicker({dateFmt:' HH:mm:ss',isShowClear:false});"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[cj_time_begin] (出金结束时间为交易开始时间)</span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">出金周期(交易日)：</label>
                            <div class="controls">
                                <input type="number" name="out_day"
                                       value="${options.md_config.out_day }" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[out_day] </span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">每次出金最大比例：</label>
                            <div class="controls">
                                <input type="number" name="out_scale"
                                       value="${options.md_config.out_scale }" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[out_scale] </span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">承销商获得比例：</label>
                            <div class="controls">
                                <input type="number" name="cx_money"
                                       value="${options.md_config.cx_money }" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[cx_money] </span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">保证金比例：</label>
                            <div class="controls">
                                <input type="number" name="bzj_money"
                                       value="${options.md_config.bzj_money}" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[bzj_money] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">管理费比例：</label>
                            <div class="controls">
                                <input type="number" name="glf_money"
                                       value="${options.md_config.glf_money}" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[glf_money] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">代理商分配比例：</label>
                            <div class="controls">
                                <input type="number" name="dls_money"
                                       value="${options.md_config.dls_money}" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[dls_money] </span>
                            </div>
                        </div>

                    </div>
                </fieldset>
                <fieldset class="layui-elem-field">
                <legend >手续费配置</legend>
                <div class="layui-field-box">
                    <div class="control-group">
                        <label class="control-label">手续费百分比(买家)：</label>
                        <div class="controls">
                            <input type="number" name="buy_procedure_money"
                                   value="${options.md_config.buy_procedure_money}" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[buy_procedure_money] </span>
                        </div>

                    </div>
                    <div class="control-group">
                        <label class="control-label">手续费百分比(卖家)：</label>
                        <div class="controls">
                            <input type="number" name="sell_procedure_money"
                                   value="${options.md_config.sell_procedure_money}" htmlEscape="false"
                                   class="input-xlarge required"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[sell_procedure_money] </span>
                        </div>
                    </div>
                </div>
                </fieldset>
                <fieldset class="layui-elem-field">
                    <legend >首日交易振幅封顶配置</legend>
                    <div class="layui-field-box">
                        <div class="control-group">
                            <label class="control-label">上浮百分比：</label>
                            <div class="controls">
                                <input type="number" name="first_md_up_float"
                                       value="${options.md_config.first_md_up_float }" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[first_md_up_float] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">下浮百分比：</label>
                            <div class="controls">
                                <input type="number" name="first_md_down_float"
                                       value="${options.md_config.first_md_down_float}" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[first_md_down_float] </span>
                            </div>
                        </div>
                    </div>
                </fieldset>

                <fieldset class="layui-elem-field">
                    <legend >开盘交易振幅封顶配置</legend>
                    <div class="layui-field-box">
                        <div class="control-group">
                            <label class="control-label">上浮百分比：</label>
                            <div class="controls">
                                <input type="number" name="open_md_up_float"
                                       value="${options.md_config.open_md_up_float }" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[open_md_up_float] </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">下浮百分比：</label>
                            <div class="controls">
                                <input type="number" name="open_md_down_float"
                                       value="${options.md_config.open_md_down_float}" htmlEscape="false"
                                       class="input-xlarge required"/>
                                <span class="help-inline"><font
                                        color="red">*</font> 标识:[open_md_down_float] </span>
                            </div>
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

    //限制输入金额
    function amount(th){
        var regStrs = [
            ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
            ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
            ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
            ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
        ];
        for(var i=0; i<regStrs.length; i++){
            var reg = new RegExp(regStrs[i][0]);
            th.value = th.value.replace(reg, regStrs[i][1]);
        }
    }
    function overFormat(th){
        var v = th.value;
        if(v === ''){
            v = '0.00';
        }else if(v === '0'){
            v = '0.00';
        }else if(v === '0.'){
            v = '0.00';
        }else if(/^0+\d+\.?\d*.*$/.test(v)){
            v = v.replace(/^0+(\d+\.?\d*).*$/, '$1');
            v = inp.getRightPriceFormat(v).val;
        }else if(/^0\.\d$/.test(v)){
            v = v + '0';
        }else if(!/^\d+\.\d{2}$/.test(v)){
            if(/^\d+\.\d{2}.+/.test(v)){
                v = v.replace(/^(\d+\.\d{2}).*$/, '$1');
            }else if(/^\d+$/.test(v)){
                v = v + '.00';
            }else if(/^\d+\.$/.test(v)){
                v = v + '00';
            }else if(/^\d+\.\d$/.test(v)){
                v = v + '0';
            }else if(/^[^\d]+\d+\.?\d*$/.test(v)){
                v = v.replace(/^[^\d]+(\d+\.?\d*)$/, '$1');
            }else if(/\d+/.test(v)){
                v = v.replace(/^[^\d]*(\d+\.?\d*).*$/, '$1');
                ty = false;
            }else if(/^0+\d+\.?\d*$/.test(v)){
                v = v.replace(/^0+(\d+\.?\d*)$/, '$1');
                ty = false;
            }else{
                v = '0.00';
            }
        }
        th.value = v;
    }
</script>
</body>
</html>