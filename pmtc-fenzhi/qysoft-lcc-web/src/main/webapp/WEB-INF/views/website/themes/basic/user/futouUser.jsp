<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>

<title>自主复投</title>

<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/forms/validation.css">
<script src="${ctxStatic}/layerUI/layer/skin/default/layer.css"></script>
<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-9">
                        <div class="example-wrap">
                            <h4 class="example-title title_h4">自主复投</h4>
                            <div class="example message_group">
                                <form class="form-horizontal" id="userInfo" autocomplete="off">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">会员编号:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 ">${userinfo.userName}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">会员姓名:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 ">${userinfo.trueName}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">复投金额:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 ">${userinfo.userLevel.money}</span>
                                        </div>
                                    </div>
                                    <input type="hidden" name="type" value="1">
                                    <div class="form-group">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <button type="submit" class="btn btn-warning">复投</button>
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
<div class="biaodan" id="biaodan">
    <form class="form-horizontal" id="subForms">
        <div class="biaodan-one" style="width:400px;height:100px;background:#fff;position: relative;">
            <h3>复投需要扣除您相应的奖金币，确认复投？</h3>
            <div class="biandan-but">
                <button type="submit" >确认</button><div class="btn" onclick="biaodanHide()">取消</div>
            </div>
        </div>
    </form>
</div>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js"
        data-deps="formValidation"></script>
<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/layerUI/layer/layer.js" ></script>

<script type="text/javascript" data-deps="formValidation">

    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        var loadIndex = 0;
        window.Content = {
            run: function () {

                this.initFormValidate();
            },

            initFormValidate : function(){

                $('#userInfo').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    $("#biaodan").css({display:"flex",display:"-webkit-flex"});
                });

                $('#subForms').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    loadIndex = layer.load(1, {
                        shade: [0.2,'#000000'] //0.1透明度的白色背景
                    });
                    $.app.ajax($.ctx + 'futouUser', $("#userInfo").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("复投成功！");
                    },function () {
                        biaodanHide();
                        layer.close(loadIndex);
                    });
                });

            }
        };
    })(document, window, jQuery);

</script>
<script type="text/javascript">
    function biaodanHide(){
        $("#biaodan").hide();
    }
</script>