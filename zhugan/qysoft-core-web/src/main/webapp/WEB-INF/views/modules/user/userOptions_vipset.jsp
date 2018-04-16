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

            if(${options.system_user_set.double_line_open == "on"}){
                $("#isOpenSwitch").attr("checked",true);
            }else{
                $("#isOpenSwitch").attr("checked",false);
            }
            if(${options.system_user_set.center_open == "on"}){
                $("#isOpenCenterSwitch").attr("checked",true);
            }else{
                $("#isOpenCenterSwitch").attr("checked",false);
            }
            if(${options.system_user_set.singleUserLogin == "on"}){
                $("#isOpenSingleUserLogin").attr("checked",true);
            }else{
                $("#isOpenSingleUserLogin").attr("checked",false);
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

            <form name="inputForm" modelAttribute="optionValue" action="${ctx}/user/userOptions/saveUserSet" method="post" class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_user_set"/>

                <blockquote class="layui-elem-quote">会员注册配置</blockquote>

                <input type="hidden" value="" name="double_line_id" id="double_line_id">

                <div class="control-group">
                    <label class="control-label" id="lables">开启双线限制：</label>
                    <div class="controls">
                        <input type="hidden"  name="double_line_open" value="${options.system_user_set.double_line_open}" id="istransopen"/>
                        <input type="checkbox"  lay-skin="switch" lay-text="开启|关闭" lay-filter="doubleLineLimit" id="isOpenSwitch">
                        <span class="help-inline"><font color="red">*</font> 标识:[double_line_open]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" id="lable">启用报单中心：</label>
                    <div class="controls">
                        <input type="hidden"  name="center_open" value="${options.system_user_set.center_open}" id="isCenteropen"/>
                        <input type="checkbox"  lay-skin="switch" lay-text="开启|关闭" lay-filter="centerReport" id="isOpenCenterSwitch">
                        <span class="help-inline"><font color="red">*</font> 标识:[center_open]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" id="labless">启用单用户登录：</label>
                    <div class="controls">
                        <input type="hidden"  name="singleUserLogin" value="${options.system_user_set.singleUserLogin}" id="singleUserLogin"/>
                        <input type="checkbox"  lay-skin="switch" lay-text="开启|关闭" lay-filter="singleUserLogincenterReport" id="isOpenSingleUserLogin">
                        <span class="help-inline"><font color="red">*</font> 标识:[singleUserLogin]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" >会员账号生成方式：</label>
                    <div class="controls">
                        <input type="radio" name="user_create_type" value="rand" title="随机生成" ${options.system_user_set.user_create_type == 'rand'?'checked':''}>
                        <input type="radio" name="user_create_type" value="sequence" title="序列生成" ${options.system_user_set.user_create_type == 'sequence'?'checked':''}>
                        <span class="help-inline"><font color="red">*</font> 标识:[user_create_type]</span>
                    </div>
                </div>
                <div class="control-group">
                        <label class="control-label" >会员账号生成前缀：</label>
                        <div class="controls">
                            <input type="text" name="user_id_prefix"  value="${options.system_user_set.user_id_prefix}" placeholder="请输入前缀" style="width: 80px;" class="input-xlarge" >
                            <span class="help-inline"><font color="red">*</font> 标识:[user_id_prefix]</span>
                        </div>
                </div>
                <div class="control-group">
                    <label class="control-label" >每个手机号最多可注册用户数配置：</label>
                    <div class="controls">
                        <input type="text" name="max_user"  value="${options.system_user_set.max_user}"  style="width: 80px;" class="input-xlarge" >
                        <span class="help-inline"><font color="red">*</font> 标识:[max_user]</span>
                    </div>
                </div>
		
		<blockquote class="layui-elem-quote">网络图配置</blockquote>

                <div class="control-group">
                    <label class="control-label" id="lablesUser">是否开启左右区业绩量：</label>
                    <div class="controls">
                        <input type="checkbox" name="network_open"  ${options.system_user_set.network_open=='on'?'checked':'' }
                               lay-skin="switch" lay-text="开启|关闭">
                        <span class="help-inline"><font
                                color="red">*</font> 标识:[network_open] </span>

                    </div>
                </div>
		
                <blockquote class="layui-elem-quote">提现配置</blockquote>

                <label class="control-label"> 提现日期配置：</label>
                <input type="checkbox" name="Sunday" title="星期日" ${options.system_user_set.Sunday == 'on'?'checked':''}>
                <input type="checkbox" name="Monday" title="星期一" ${options.system_user_set.Monday == 'on'?'checked':''}>
                <input type="checkbox" name="Tuesday" title="星期二" ${options.system_user_set.Tuesday == 'on'?'checked':''}>
                <input type="checkbox" name="Wensday" title="星期三" ${options.system_user_set.Wensday == 'on'?'checked':''}>
                <input type="checkbox" name="Thursday" title="星期四" ${options.system_user_set.Thursday == 'on'?'checked':''}>
                <input type="checkbox" name="Friday" title="星期五" ${options.system_user_set.Friday == 'on'?'checked':''}>
                <input type="checkbox" name="Saturday" title="星期六" ${options.system_user_set.Saturday == 'on'?'checked':''}>
                <div class="layui-field-box">
                    <div class="control-group">
                        <label class="control-label">提现开始时间：</label>
                        <div class="controls">
                            <input name="withdraw_time_begin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                   value="${options.system_user_set.withdraw_time_begin}"
                                   onclick="WdatePicker({dateFmt:' HH:mm:ss',isShowClear:false});"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[withdraw_time_begin] </span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">提现结束时间：</label>
                        <div class="controls">
                            <input name="withdraw_time_end" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                   value="${options.system_user_set.withdraw_time_end}"
                                   onclick="WdatePicker({dateFmt:' HH:mm:ss',isShowClear:false});"/>
                            <span class="help-inline"><font
                                    color="red">*</font> 标识:[withdraw_time_end] </span>
                        </div>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">手续费配置：</label>
                    <div class="controls">
                        <input type="number" name="poundage" id="poundage"
                               value="${options.system_user_set.poundage}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[poundage]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">提现最小额度配置：</label>
                    <div class="controls">
                        <input type="number" name="minMoney" id="minMoney"
                               value="${options.system_user_set.minMoney}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[minMoney]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">提现最大额度配置：</label>
                    <div class="controls">
                        <input type="number" name="maxMoney" id="maxMoney"
                               value="${options.system_user_set.maxMoney}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[maxMoney]</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">提现倍数配置：</label>
                    <div class="controls">
                        <input type="number" name="multiple" id="multiple"
                               value="${options.system_user_set.multiple}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[multiple]</span>
                    </div>
                </div>

                <div class="form-actions">
                    <shiro:hasPermission name="user:userUserinfo:edit">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>

            </form>
        </div>

        <div class="layui-tab-item layui-show">

            <form name="bounsForm" modelAttribute="optionValue" action="${ctx}/user/userOptions/saveUserSet" method="post" class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_user_bonus"/>
                <blockquote class="layui-elem-quote">基础数据配置</blockquote>

                <div class="control-group">
                    <label class="control-label">共享包基础单价: </label>
                    <div class="controls">
                        <input type="text" name="goods_money" id="goods_money"
                               value="${options.system_user_bonus.goods_money}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[goods_money]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">手续费百分比: </label>
                    <div class="controls">
                        <input type="text" name="poundage_per" id="poundage_per"
                               value="${options.system_user_bonus.poundage_per}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[poundage_per]</span>
                    </div>
                </div>


                <div class="form-actions">
                    <shiro:hasPermission name="user:userUserinfo:edit">
                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                               value="保 存 配 置"/>&nbsp;</shiro:hasPermission>
                </div>

            </form>
        </div>

        <div class="layui-tab-item layui-show">

            <form name="bounsForm" modelAttribute="optionValue" action="${ctx}/user/userOptions/saveUserSet" method="post" class="form-horizontal layui-form">
                <input type="hidden" name="optName" value="system_static_bonus"/>
                <blockquote class="layui-elem-quote">静态奖金配置</blockquote>

                <div class="control-group">
                    <label class="control-label">红包金额解冻比例: </label>
                    <div class="controls">
                        <input type="text" name="red_packet_persent" id="red_packet_persent"
                               value="${options.system_static_bonus.red_packet_persent}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[red_packet_persent]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">红包金额解冻比例-金币: </label>
                    <div class="controls">
                        <input type="text" name="red_packet_persent_gold" id="red_packet_persent_gold"
                               value="${options.system_static_bonus.red_packet_persent_gold}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[red_packet_persent_gold]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">红包金额解冻比例-商城积分: </label>
                    <div class="controls">
                        <input type="text" name="red_packet_persent_points" id="red_packet_persent_points"
                               value="${options.system_static_bonus.red_packet_persent_points}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[red_packet_persent_points]</span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">红包金额解冻比例-手续费: </label>
                    <div class="controls">
                        <input type="text" name="red_packet_persent_charge" id="red_packet_persent_charge"
                               value="${options.system_static_bonus.red_packet_persent_charge}" htmlEscape="false"
                               class="input-xlarge required"/> <span class="help-inline"><font
                            color="red">*</font> 标识:[red_packet_persent_charge]</span>
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

        form.on('switch(doubleLineLimit)', function(data){
            if(data.elem.checked){
                $("#istransopen").val("on");
            }
            else{
                $("#istransopen").val("off");
            }
        });

        form.on('switch(centerReport)', function(data){
            if(data.elem.checked){
                $("#isCenteropen").val("on");
            }
            else{
                $("#isCenteropen").val("off");
            }
        });
        form.on('switch(singleUserLogincenterReport)', function(data){
            if(data.elem.checked){
                $("#singleUserLogin").val("on");
            }
            else{
                $("#singleUserLogin").val("off");
            }
        });
    });
</script>
</body>
</html>