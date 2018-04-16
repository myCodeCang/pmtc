<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>申请报单中心</title>

<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-8">

                        <div class="example-wrap">
                            <h4 class="example-title title_h4">申请报单中心</h4>
                            <div class="example message_group">
                                <form class="form-horizontal" id="centerForm">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">报单中心名称:</label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" name="centerName" placeholder="" autocomplete="off" data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写报单中心名">
                                        </div>
                                        <label class="col-sm-3 ">请填写报单中心名</label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">申请级别:</label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" disabled="" value="一级报单中心">
                                            <input type="hidden" id="level" name="level" value="1">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">报单中心地址:</label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" name="centerAddress" placeholder="" autocomplete="off" data-fv-notempty="true"
                                                   data-fv-notempty-message="请填写报单中心地址">
                                        </div>
                                        <label class="col-sm-3 ">请填写报单中心地址</label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">备注：</label>
                                        <div class="col-sm-6">
                                            <textarea class="form-control" placeholder="" name="commt"></textarea>
                                        </div>
                                        <label class="col-sm-3 ">请填写备注</label>
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

                $('#centerForm').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    var fv    = $(e.target).data('formValidation');
                    $.app.ajax($.ctx + 'applyUserCenter', $("#centerForm").serialize(), function (data) {
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
