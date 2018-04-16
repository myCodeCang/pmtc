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
<title>总钱包内部转账</title>
<style>
    .message_group .form-group{margin-bottom:10px;}
</style>

<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-9">
                        <div class="example-wrap">
                            <h4 class="example-title title_h4">总钱包内部转账</h4>
                            <div class="example message_group">
                                <form class="form-horizontal" id="userInfo" autocomplete="off">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转出账户:</label>
                                        <span class="col-sm-3 ">${userinfo.userName}</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">账户类型:</label>
                                        <span class="col-sm-3 ">交易钱包</span>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">报单中心:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<span class="col-sm-3 ">${userinfo.serverName}</span>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">交易钱包余额:</label>
                                        <span class="col-sm-3 ">${userinfo.money}</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">接收会员编号或钱包地址:</label>
                                        <span class="col-sm-3">
                                            <input type="text" value="${userName}" id="userName" style="border:1px solid #ccc;border-radius:4px;padding-left:10px;" onchange="selectUser()">
                                            <input type="hidden" id="address"  name="address">
                                        </span>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">接收会员昵称:</label>
                                        <span class="col-sm-3 " id="userNiceName"></span>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">收款用户钱包地址:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<input type="text" value="${address}" class="form-control" name="address" placeholder=""--%>
                                                   <%--autocomplete="off"  data-fv-notempty="true"--%>
                                                   <%--data-fv-notempty-message="请填写收款用户钱包地址">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转出金额数量:</label>
                                        <span class="col-sm-3">
                                            <input type="text" onKeyUp="amount(this)" name="moneyNum" style="border:1px solid #ccc;border-radius:4px;padding-left:10px;">
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">手机号:</label>
                                        <span class="col-sm-3 ">${userinfo.mobile}</span>
                                    </div>
                                    <div class="form-group" style="display: ${userinfo.isShop != '0'?'block':'none'}">
                                        <label class="col-sm-3 control-label" style="float: left;">短信验证码:</label>
                                        <span class="col-sm-3" style="float: left;">
                                            <div class="input-group">
                                                <input type="text" name="validCode"  placeholder="请输入验证码" style="border:1px solid #ccc;width: 63%;border-radius:4px;padding-left:10px;float: left;">
                                                <span style="display:inline-block;cursor: pointer;float: right;width:35%;background-color: #f3f7f9;border: 1px solid #ccc;height:34px;line-height:34px;border-radius:4px;text-align: center;" id="getVerifyCode">获取</span>
                                            </div>
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">备注:</label>
                                        <span class="col-sm-3">
                                            <textarea style="height:100px;width:94%;border:1px solid #ddd;border-radius: 3px;"></textarea>
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">支付密码:</label>
                                        <span class="col-sm-3">
                                            <input type="password" name="bankPwd" style="border:1px solid #ccc;border-radius:4px;padding-left:10px;">
                                        </span>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label" style="color:#EF5B44;"></label>--%>
                                        <%--<div class="col-sm-7">--%>
                                            <%--<p>注：转移金额不得小于200，并且必须为100的倍数!</p>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group margin-top-30">
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

    var mobile = "${userinfo.mobile}";

    if(${userName != null}){
        selectUser();
    }
    (function (document, window, $) {

        var commitFlag = false;

        $('#getVerifyCode').on('click', function() {
            getVerify();
        });

        var $content = $("#admui-pageContent");

        window.Content = {
            run: function () {

                this.initFormValidate();
            },

            initFormValidate : function(){

                $('#userInfo').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    if(commitFlag){
                        return;
                    }
                    commitFlag = true;
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + 'transferOfAccount', $("#userInfo").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("成功！");
                        commitFlag = false;

                        setTimeout(function () {
                            location.reload();
                        },2000);

                    },function () {
                        fv.disableSubmitButtons(false);
                        commitFlag = false;
                    });
                });

            }

        };
    })(document, window, jQuery);

    function getVerify() {

        if ($("#mobile").val() == '' || $("#mobile").val() == 'undefined') {
            toastr.info('手机号码不能为空');
            return;
        }
        // if(!(/^1[1|2|3|4|5|6|7|8][0-9]\d{4,8}$/.test(mobile))){
        //     toastr.info("不是正确的手机号码格式");
        //     return;
        // }
        var param = {
            mobile : mobile
        };
        $('#getVerifyCode').off('click');
        $.post("${pageContext.request.getContextPath()}/msm/lkMsm/sendVerifyCode", param, function(data) {
            $("#getVerifyCode").attr("disabled", true);
            $("#getVerifyCode").text(countNum + '秒');
            //一秒执行一次
            clock = setInterval(doLoop, 1000);
        });
    }
    function doLoop() {
        countNum--;
        if (countNum > 0) {
            $("#getVerifyCode").attr("disabled", true);
            $("#getVerifyCode").text(countNum + '秒');
        } else {
            clearInterval(clock);
            //清除js定时器
            $("#getVerifyCode").removeAttr("disabled");
            $("#getVerifyCode").text('获取');
            countNum = 60;
            //重置时间

            $('#getVerifyCode').on('click', function() {
                getVerify();
            });
        }
    }
    
    function selectUser() {
        var userName = $('#userName').val();
        $.app.ajax($.ctx + 'selectUserByName', {userName:userName}, function (data) {
            if (data.User){
                $("#userNiceName").text(data.User.userNicename);
                $("#address").val(data.User.userName);
            }else{
                toastr.info("未找到该用户");
            }
            //toastr.info("成功！");
        },function () {
            toastr.info("查询失败！");
        });
    }

</script>