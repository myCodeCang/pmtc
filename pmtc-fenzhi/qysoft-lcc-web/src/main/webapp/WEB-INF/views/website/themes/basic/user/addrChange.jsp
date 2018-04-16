<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>地址管理</title>


<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/jquery-selective/jquery-selective.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/team/projects.css">

<div class="page animation-fade page-projects">
    <div class="page-header">
        <h1 class="page-title title_h4">地址管理</h1>
        <div class="row margin-top-15">
            <div class="col-sm-2 col-lg-2">
                <button type="button" class="btn btn-block btn-warning"  data-target="#addProjectForm" data-toggle="modal">添加地址</button>
            </div>
        </div>
    </div>
    <div class="page-content">
        <div class="projects-wrap">
            <ul class="blocks blocks-100 blocks-xlg-4 blocks-md-3 blocks-sm-2 blocks-xs-2" data-plugin="animateList" data-child=">li">
                <c:forEach items="${userAdddressList}" var="addr">
                <li class="animation-scale-up" style="animation-fill-mode: backwards; animation-duration: 250ms; animation-delay: 0ms;">
                    <div class="panel">
                        <figure class="overlay overlay-hover animation-hover  site-one">
                            <img class="caption-figure" src="${ctxStatic}/qysoftui/images/site-two.png" style="border-radius:8px;">
                            <div class="site-detail">
                                <div><span>${addr.userName}</span></div>
                                <div><span>地址：</span><span>${addr.address}</span></div>
                                <div><span>电话：</span><span>${addr.mobile}</span></div>
                            </div>
                            <figcaption class="overlay-panel overlay-background overlay-fade text-center vertical-align">
                                <button type="button" data-tag="project-delete" data="${addr.id}" class="btn btn-outline btn-default project-button">删除地址
                                </button>
                            </figcaption>
                            <div class="site-time">
                                <div class="time pull-right"><fmt:formatDate value="${addr.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                                <div class="text-truncate">添加时间</div>
                            </div>
                        </figure>
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
                <h4 class="modal-title">添加地址</h4>
            </div>
            <form class="form-horizontal" id="centerForm">
                <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label margin-bottom-15">地址：</label>
                            <input type="text" class="form-control" placeholder="请填写地址" name="address"  autocomplete="off"  data-fv-notempty="true"
                                   data-fv-notempty-message="请填写地址">
                        </div>
                        <div class="form-group">
                            <label class="control-label margin-bottom-15">联系人：</label>
                            <input type="text" class="form-control" placeholder="请填写联系人" name="trueName" autocomplete="off"  data-fv-notempty="true"
                                   data-fv-notempty-message="请填写联系人">
                        </div>
                        <div class="form-group">
                            <label class="control-label margin-bottom-15">联系电话：</label>
                            <input type="text" class="form-control" placeholder="请填写联系电话" name="mobile" autocomplete="off"  data-fv-notempty="true"
                                   data-fv-notempty-message="请填写联系电话">
                        </div>
                        <%--<div class="form-group">--%>
                            <%--<label class="control-label margin-bottom-15">邮编：</label>--%>
                            <%--<input type="text" class="form-control" placeholder="请填写邮编" name="postcode">--%>
                        <%--</div>--%>
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
                    $.app.ajax($.ctx + 'addrChange', $("#centerForm").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("提交成功！");
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
                                    $.app.ajax($.ctx + 'addrDelete', {addressId:value}, function (data) {
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