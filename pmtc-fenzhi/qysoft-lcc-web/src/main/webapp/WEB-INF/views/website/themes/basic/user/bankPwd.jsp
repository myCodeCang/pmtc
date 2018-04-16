<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>二级密码</title>

<div class="page animation-fade page-forms">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body">
                <h4 class="example-title title_h4">二级密码</h4>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body message_group">
                <form class="form-horizontal" id="bankPwd" autocomplete="off">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">二级密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" name="bankPwd" data-plugin="formatter" data-fv-notempty="true"
                                   data-fv-notempty-message="请填写二级密码" >
                        </div>
                        <label class="col-sm-3">请输入二级密码</label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-6 col-sm-offset-3">
                            <button type="submit" class="btn-warning btn">提交</button>
                            <button type="button" class="btn-default btn">取消</button>
                        </div>
                    </div>
                </form>
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

                $('#bankPwd').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    $.app.ajax($.ctx + 'bankPwd', $("#bankPwd").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("验证成功！");
                    });
                });

            }

        };
    })(document, window, jQuery);

</script>
