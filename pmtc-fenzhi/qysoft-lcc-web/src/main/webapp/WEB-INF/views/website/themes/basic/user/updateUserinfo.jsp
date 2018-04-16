<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>

<title>个人资料</title>
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
                            <h4 class="example-title title_h4">个人资料</h4>
                            <div class="example message_group">
                                <form class="form-horizontal" id="userInfo" autocomplete="off">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">会员编号:</label>
                                        <span class="col-sm-3 ">${userinfo.userName}</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">真实姓名:</label>
                                        <span class="col-sm-3 ">

                                            <c:if test='${userinfo.trueName == "" || userinfo.trueName == null }'>
                                            <input type="text" name="trueName"  value="${userinfo.trueName}" id="trueName" placeholder="请输入真实姓名" style="border:1px solid #ccc;border-radius:4px;padding-left:10px;">
                                            </c:if>
                                             ${userinfo.trueName}
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">会员昵称:</label>
                                        <span class="col-sm-3 "><input type="text" name="userNicename" value="${userinfo.userNicename }" id="userNicename" placeholder="请输入昵称姓名" style="border:1px solid #ccc;border-radius:4px;padding-left:10px;"></span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">证件类型:</label>
                                        <span class="col-sm-3 ">身份证</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">证件编号:</label>
                                        <span class="col-sm-3 ">
                                            <c:if test="${userinfo.idCard == '' || userinfo.idCard == null}">
                                                <input type="text" name="idCard" id="idCard"  value="${userinfo.idCard}" placeholder="请输入证件编号" style="border:1px solid #ccc;border-radius:4px;padding-left:10px;">

                                            </c:if>
                                            ${userinfo.idCard}
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">移动电话:</label>
                                        <span class="col-sm-3 " readonly="true" name="mobile" id="mobile">${userinfo.mobile}</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">电子邮箱:</label>
                                        <span class="col-sm-3 "><input type="text" name="userEmail" id="userEmail" value="${userinfo.userEmail}" placeholder="请输入电子邮箱" style="border:1px solid #ccc;border-radius:4px;padding-left:10px;"></span>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">推荐人:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<span class="col-sm-3 ">${userinfo.walterName == ''?'无':userinfo.walterName}</span>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">个人等级:</label>--%>
                                        <%--<span class="col-sm-3 ">${userinfo.areaId}</span>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="col-sm-3 control-label">身份证号:</label>--%>
                                        <%--<div class="col-sm-6">--%>
                                            <%--<input type="text" class="form-control" name="idCard" placeholder=""--%>
                                                   <%--autocomplete="off" value="${userinfo.idCard}" data-fv-notempty="true"--%>
                                                   <%--data-fv-notempty-message="请填写身份证号">--%>
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

    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        window.Content = {
            run: function () {

                this.initFormValidate();
            },

            initFormValidate : function(){

                $('#userInfo').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + 'updateUserinfo', $("#userInfo").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("保存成功！");
                    },function () {
                        fv.disableSubmitButtons(false);
                    });
                });

            }

        };
    })(document, window, jQuery);

</script>