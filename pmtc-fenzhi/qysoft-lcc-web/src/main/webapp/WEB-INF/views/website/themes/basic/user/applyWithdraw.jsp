<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>提现申请</title>

<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-8">

                        <div class="example-wrap">
                            <h4 class="example-title title_h4">提现申请</h4>
                            <div class="example message_group">
                                <form class="form-horizontal" id="centerForm">
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">申请编号</label>--%>
                                        <%--<div class="col-sm-7">--%>
                                            <%--<input type="text" class="form-control" id="recordcode" name="recordcode" placeholder="" disabled="" value="${vode}">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">申请人</label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control" id="userName" placeholder="" disabled="" value="${userinfo.userName}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">可提现奖金币</label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control" id="inputDisabled" placeholder="" disabled="" value="${userinfo.money}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">选择银行卡:</label>
                                        <div class="col-sm-7">
                                            <select class="form-control" name="bankId" id="bankId">
                                                <c:forEach items="${userBankList}" var="bank">
                                                    <option value="${bank.id}">${bank.bankName} (卡号：${bank.bankUserNum})</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">开户名</label>--%>
                                        <%--<div class="col-sm-7">--%>
                                           <%--<input type="text" class="form-control" id="inputDisabled" placeholder="" disabled="">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">银行卡号</label>--%>
                                        <%--<div class="col-sm-7">--%>
                                           <%--<input type="text" class="form-control" id="inputDisabled" placeholder="" disabled="">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">银行名称</label>--%>
                                        <%--<div class="col-sm-7">--%>
                                            <%--<input type="text" class="form-control" id="inputDisabled" placeholder="" disabled="">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">开户行地址</label>--%>
                                        <%--<div class="col-sm-7">--%>
                                            <%--<input type="text" class="form-control" id="inputDisabled" placeholder="" disabled="">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">提现金额</label>
                                        <div class="col-sm-7">
                                            <input type="number" class="form-control" name="money" placeholder="" autocomplete="off" data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写提现金额">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">二级密码</label>
                                        <div class="col-sm-7">
                                            <input type="password" class="form-control" name="payPwd" placeholder="" autocomplete="off" data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写二级密码">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" style="color:#EF5B44;">提现说明:</label>
                                        <div class="col-sm-7">
                                            <p>1. 提现时间:
                                                <span>${fns:getOption("system_user_set","Sunday")=='on'?'周日':''}</span>
                                                <span>${fns:getOption("system_user_set","Monday")=='on'?'周一':''}</span>
                                                <span>${fns:getOption("system_user_set","Tuesday")=='on'?'周二':''}</span>
                                                <span>${fns:getOption("system_user_set","Wensday")=='on'?'周三':''}</span>
                                                <span>${fns:getOption("system_user_set","Thursday")=='on'?'周四':''}</span>
                                                <span>${fns:getOption("system_user_set","Friday")=='on'?'周五':''}</span>
                                                <span>${fns:getOption("system_user_set","Saturday")=='on'?'周六':''}</span>
                                            </p>
                                            <p>2. 金额要求：每笔提现范围在${fns:getOption("system_user_set","minMoney")}- ${fns:getOption("system_user_set","maxMoney")}之间，提现金额必须为${fns:getOption("system_user_set","multiple")}的倍数!</p>
                                            <p>3. 手续费${fns:getOption("system_user_set","poundage")*100}%</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <button type="submit" class="btn btn-warning">确认申请</button>
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
    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        window.Content = {
            run: function () {

                this.initFormValidate();
            },

            initFormValidate : function(){

                $('#centerForm').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + 'applyWithdraw', $("#centerForm").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("申请提交成功！");
                    },function () {
                        fv.disableSubmitButtons(false);
                    });
                });

            }

        };
    })(document, window, jQuery);

</script>
  

