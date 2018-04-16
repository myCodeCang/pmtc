<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>银行卡管理</title>

<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/jquery-selective/jquery-selective.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/team/projects.css">

<div class="page animation-fade page-projects">
    <div class="page-header">
        <h1 class="page-title title_h4">银行卡管理</h1>
        <div class="row margin-top-15">
            <div class="col-sm-2 col-lg-2">
                <button type="button" class="btn btn-block btn-warning" data-target="#addProjectForm" data-toggle="modal" >添加银行卡</button>
            </div>
        </div>
    </div>
    <div class="page-content">
        <div class="projects-wrap" style="padding-top:10px;">
            <ul class="blocks blocks-100 blocks-xlg-5 blocks-md-4 blocks-sm-3 blocks-xs-2" data-plugin="animateList" data-child=">li">
                <c:forEach items="${userBankList}" var="bank">
                <li class="animation-scale-up" style="animation-fill-mode: backwards; animation-duration: 250ms; animation-delay: 0ms;">
                    <div class="panel">
                        <figure class="overlay overlay-hover animation-hover card-one">
                            <img class="caption-figure" src="${ctxStatic}/qysoftui/images/credit-card.png">
                            <div class="card-detail">
                                <div>
                                    <div><span>${bank.bankName}</span></div>
                                    <div><span>${bank.bankUserNum}</span></div>
                                    <div><span>${bank.bankUserName}</span></div>
                                </div>
                            </div>
                                <figcaption class="overlay-panel overlay-background overlay-fade text-center vertical-align">
                                    <button type="button" data-tag="project-delete" data="${bank.id}"  class="btn btn-outline btn-default project-button">删除银行卡
                                    </button>
                                </figcaption>
                        </figure>
                        <div class="time pull-right"><fmt:formatDate value="${bank.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                        <div class="text-truncate">添加时间</div>
                    </div>
                </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <button class="site-action btn-raised btn btn-success btn-floating" data-target="#addProjectForm" data-toggle="modal" type="button">
        <i class="icon wb-plus" aria-hidden="true"></i>
    </button>

</div>

<div class="modal fade" id="addProjectForm" aria-hidden="true" aria-labelledby="addProjectForm" role="dialog" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" aria-hidden="true" data-dismiss="modal">×</button>
                <h4 class="modal-title">添加银行卡</h4>
            </div>
            <form class="form-horizontal" id="centerForm">
                <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label margin-bottom-15">开户银行：</label>
                            <select class="form-control" name="bankCode">
                                <c:forEach items="${userBankWithdraws}" var="bankw">
                                <option value="${bankw.bankCode}">${bankw.bankName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label margin-bottom-15">开户人：</label>
                            <input type="text" class="form-control" placeholder="开户人" name="bankUserName"  data-fv-notempty-message="请填写开户人">
                            <%--<input type="text" class="form-control" placeholder="开户人" name="bankUserName" autocomplete="off"  data-fv-notempty="true"--%>
                                   <%--data-fv-notempty-message="请填写开户人" value="${userinfo.trueName}" readonly>--%>
                        </div>
                        <div class="form-group">
                            <label class="control-label margin-bottom-15">开户账号：</label>
                            <input type="text" class="form-control" placeholder="开户账号" name="bankUserNum" autocomplete="off"  data-fv-notempty="true"
                                   data-fv-notempty-message="请填写开户账号">
                        </div>
                        <div class="form-group">
                            <label class="control-label margin-bottom-15">开户行地址：</label>
                            <input type="text" class="form-control" placeholder="开户行地址" name="bankUserAddress" autocomplete="off"  data-fv-notempty="true"
                                   data-fv-notempty-message="请填写开户行地址">
                        </div>

                </div>
                <div class="modal-footer text-right">
                    <button class="btn btn-primary" type="submit">确定</button>
                    <a class="btn btn-sm btn-white" data-dismiss="modal" href="javascript:;">取消</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${ctxStatic}/qysoftui/vendor/jquery-selective/jquery-selective.min.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/bootbox/bootbox.min.js"></script>
<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js"
        data-deps="formValidation"></script>


<script type="text/javascript" data-deps="formValidation">

    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        window.Content = {
            run: function () {

                this.initFormValidate();
                this.handleProject();
            },

            initFormValidate : function(){

                $('#centerForm').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + 'bankChange', $("#centerForm").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("添加成功！");
                    },function () {
                        fv.disableSubmitButtons(false);
                    });
                });

            },
            handleProject: function () {
                $('#admui-pageContent').on('click', '[data-tag=project-delete]', function (e) {
                    bootbox.setDefaults($.po('bootbox'));
                     var value =  $(e.currentTarget).attr("data");
                    bootbox.dialog({
                        message: "您确定要删除这个项目吗？",
                        buttons: {
                            success: {
                                label: "删除",
                                className: "btn-danger",
                                callback: function () {
                                    $.app.ajax($.ctx + 'bankDelete', {bankId:value}, function (data) {
                                        $.pjax({
                                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                                            container: '#admui-pageContent'
                                        });
                                        toastr.info("删除成功！");
                                    });
                                }
                            }
                        }
                    });
                });
            },

        };
    })(document, window, jQuery);

</script>
