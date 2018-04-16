<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<script>

    function amount(th){
        var regStrs = [
            ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
            ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
            ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
            ['^(\\d+\\.\\d{0}).+', '$1'] //禁止录入小数点后两位以上
        ];
        for(var i=0; i<regStrs.length; i++){
            var reg = new RegExp(regStrs[i][0]);
            th.value = th.value.replace(reg, regStrs[i][1]);
        }
    }

</script>
<title>总钱包互转</title>


<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-9">
                        <div class="example-wrap">
                            <h4 class="example-title title_h4">总钱包互转</h4>
                            <div class="example message_group">
                                <form class="form-horizontal" id="userInfo" autocomplete="off">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转出账户:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 ">${userinfo.userName}</span>
                                        </div>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">会员姓名:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<span class="col-sm-3 ">${userinfo.trueName}</span>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">报单中心:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<span class="col-sm-3 ">${userinfo.serverName}</span>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">交易钱包余额:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<span class="col-sm-3 ">${userinfo.money}</span>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">账户类型:</label>
                                        <div class="input-group">
                                            <div>
                                                <div class="radio-custom radio-primary" onchange="radioSelect()">
                                                    <input type="radio" id="money" name="porto_is" value="money" required=""  checked>
                                                    <label for="money">交易钱包 余额: <span style="color:red;">${userinfo.money}</span></label>
                                                    <span>&nbsp;(必须为${fns:getOption('system_help','multiple')}的倍数)</span>
                                                </div>
                                            </div>
                                            <div>
                                                <div class="radio-custom radio-primary" onchange="radioSelect()">
                                                    <input type="radio" id="money2" name="porto_is" value="money2" required="" >
                                                    <label for="money2">矿机钱包 余额: <span style="color:red;">${userinfo.money2}</span></label>
                                                    <span>&nbsp;(扣除${fns:getOption('system_help','poundage_wallet')*100}%的手续费,且必须为${fns:getOption('system_help','multiple')}的倍数)</span>
                                                </div>
                                            </div>
                                            <div>
                                                <div class="radio-custom radio-primary" onchange="radioSelect()">
                                                    <input type="radio" id="money3" name="porto_is"  value="money3" data-fv-field="porto_is">
                                                    <label for="money3">动态钱包 余额: <span style="color:red;">${userinfo.money3}</span></label>
                                                    <span>&nbsp;(扣除${fns:getOption('system_help','poundage_wallet')*100}%的手续费,且必须为${fns:getOption('system_help','multiple')}的倍数)</span>
                                                </div>
                                            </div>
                                            <%--<div>--%>
                                                <%--<div class="radio-custom radio-primary">--%>
                                                    <%--<input type="radio" id="money4" name="porto_is"  value="money4" data-fv-field="porto_is">--%>
                                                    <%--<label for="money4">冻结钱包 余额: <span style="color:red;">${userinfo.money4}</span></label>--%>
                                                <%--</div>--%>
                                    <%--</div>--%>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转出数量:</label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" onchange="moneySelect()" onKeyUp="amount(this)" name="moneyNum" placeholder=""
                                                   autocomplete="off"  data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写转移数额">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转入账户:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 " id="inAccount"></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转入金额/数量:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 " id="inMoney"></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">支付密码:</label>
                                        <div class="col-sm-6">
                                            <input type="password" class="form-control" name="bankPwd" placeholder=""
                                                   autocomplete="off"  data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写二级密码">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">备注:</label>
                                        <div class="col-sm-6">
                                            <textarea class="form-control" name="message" id="textareaDefault" rows="3"></textarea>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" style="color:#EF5B44;"></label>
                                        <div class="col-sm-7">
                                            <%--<p>注:转移数额最低为50且为50的倍数</p>--%>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <button type="submit" class="btn btn-warning">确认提交</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js"
        data-deps="formValidation"></script>

<script type="text/javascript" data-deps="formValidation">
    radioSelect();
    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        window.Content = {
            run: function () {

                this.initFormValidate();
            },

            initFormValidate : function(){
                $('#userInfo').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    // toastr.info("转账功能未开放！");
                    // return;
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + '/f/user/transferToMoney', $("#userInfo").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("成功！");
                    },function () {
                        fv.disableSubmitButtons(false);
                    });
                });

            }

        };
    })(document, window, jQuery);
    var shouxuMoney = 0;
    function radioSelect() {
        var moneyType = $(":input[name='porto_is']:checked").val();
        shouxuMoney = ${fns:getOption('system_help','poundage_wallet')};
        if(moneyType == "money"){
            shouxuMoney = 0;
            $("#inAccount").text("矿机钱包");
        } else if(moneyType == "money2"){
            $("#inAccount").text("交易钱包");
        } else if(moneyType == "money3"){
            $("#inAccount").text("交易钱包");
        }
        moneySelect()
    }
    
    function moneySelect() {
        var money = $(":input[name='moneyNum']").val();
        $("#inMoney").text((money*(1-shouxuMoney)).toFixed(2));
    }
</script>