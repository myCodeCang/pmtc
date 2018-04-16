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
            ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
        ];
        for(var i=0; i<regStrs.length; i++){
            var reg = new RegExp(regStrs[i][0]);
            th.value = th.value.replace(reg, regStrs[i][1]);
        }
    }

</script>
<title>矿机钱包转入</title>


<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-9">
                        <div class="example-wrap">
                            <h4 class="example-title title_h4">矿机钱包转入</h4>
                            <div class="example message_group">
                                <form class="form-horizontal" id="userInfo" autocomplete="off">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">会员编号:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 ">${userinfo.userName}</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">矿机钱包余额:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 ">${userinfo.money2}</span>
                                        </div>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">选择账户:</label>--%>
                                        <%--<div class="col-sm-7">--%>
                                            <%--<select class="form-control" name="bankId" id="bankId">--%>
                                                <%--<option value="money">在线钱包 (余额：${userinfo.money2})</option>--%>
                                            <%--</select>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">选择账户:</label>
                                        <div class="input-group">
                                            <div>
                                                <div class="radio-custom radio-primary">
                                                    <input type="radio" id="inputAwesome" name="porto_is"  data-fv-notempty="true" value="awesome" data-fv-field="porto_is"   checked>
                                                    <label for="inputAwesome">交易钱包余额: <span style="color:red;">${userinfo.money}</span></label>
                                                </div>
                                            </div>
                                            <%--<div>--%>
                                                <%--<div class="radio-custom radio-primary">--%>
                                                    <%--<input type="radio" id="inputVeryAwesome" name="porto_is" value="very-awesome" data-fv-field="porto_is">--%>
                                                    <%--<label for="inputVeryAwesome">以上全部</label>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转换数额:</label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" onKeyUp="amount(this)" name="moneyNum" placeholder=""
                                                   autocomplete="off"  data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写转换数额">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">二级密码:</label>
                                        <div class="col-sm-6">
                                            <input type="password" class="form-control" name="bankPwd" placeholder=""
                                                   autocomplete="off"  data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写二级密码">
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
                    $.app.ajax($.ctx + '/f/user/transferToMoney2', $("#userInfo").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info(data.info);
                    },function () {
                        fv.disableSubmitButtons(false);
                    });

                });

            }

        };
    })(document, window, jQuery);

</script>