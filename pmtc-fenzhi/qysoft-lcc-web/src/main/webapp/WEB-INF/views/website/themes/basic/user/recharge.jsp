<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>线下充值</title>
<sys:messageQyWeb content="${message}"/>
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/blueimp-file-upload/jquery.fileupload.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/dropify/dropify.css">

<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-8">

                        <div class="example-wrap">
                            <h4 class="example-title title_h4">线下充值</h4>
                            <div class="example message_group">
                                <form method="post" class="form-horizontal" action="${ctx}/user/transfer" id="subForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <div class="col-sm-3 control-label">银行名称:</div>
                                        <div class="col-sm-7">
                                            <select name="BackList"  id="BackList" class="form-control">
                                                <option value="">请选择</option>
                                                <c:forEach items="${userBankCommon}" var="dict">
                                                    <option value="${dict.id}">${dict.bankName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div style="display:none;" id="bankDetail">
                                        <div class="col-sm-3 control-label">账户名称:</div>
                                        <div class="col-sm-7">
                                            <p style="color:#666;line-height: 30px;margin: 5px 0;" id="bankUserName"></p>
                                        </div>
                                        <div class="col-sm-3 control-label">开户支行:</div>
                                        <div class="col-sm-7">
                                            <p style="color:#666;line-height: 30px;margin: 5px 0;" id="bankAddress"></p>
                                        </div>
                                        <div class="col-sm-3 control-label">卡号:</div>
                                        <div class="col-sm-7">
                                            <p style="color:#666;line-height: 30px;margin: 5px 0;" id="bankNum"></p>
                                        </div>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">户名</label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control" name="bankUserName" placeholder="" value="${bankUserName}">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">转账金额</label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control" name="turnMoney" placeholder="" value="${turnMoney}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">上传凭证</label>
                                        <div class="col-sm-7">
                                            <div class="example-wrap">
                                                <div class="example">
                                                    <div class="dropify-wrapper">
                                                        <div class="dropify-message"><span class="file-icon"></span>
                                                            <p>单击或直接拖动需要上传的文件到此处</p>
                                                            <p class="dropify-error">出错了…</p>
                                                        </div>
                                                        <div class="dropify-loader"></div>
                                                        <div class="dropify-errors-container">
                                                            <ul></ul>
                                                        </div>
                                                        <input type="file" class="form-control" id="input-file-now" name="file" data-plugin="dropify" data-default-file="" style="height:100%;width:100%;">
                                                        <button type="button" class="dropify-clear">移除</button>
                                                        <div class="dropify-preview"><span class="dropify-render"></span>
                                                            <div class="dropify-infos">
                                                                <div class="dropify-infos-inner">
                                                                    <p class="dropify-filename"><span class="file-icon"></span> <span class="dropify-filename-inner"></span></p>
                                                                    <p class="dropify-infos-message">将文件拖放到此处或单击此处替换</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label"></label>
                                        <div class="col-sm-7">
                                            如果未能及时转账，请联系客服！
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" class="btn btn-warning"></input>
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
<script src="${ctxStatic}/qysoftui/vendor/jquery-pjax/jquery.pjax.min.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/jquery-ui/jquery-ui.min.js" data-name="jquery-ui"></script>
<script src="${ctxStatic}/qysoftui/vendor/blueimp-tmpl/tmpl.min.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/blueimp-file-upload/jquery.fileupload.js" data-name="fileupload" data-deps="jquery-ui"></script>
<script src="${ctxStatic}/qysoftui/vendor/dropify/dropify.min.js"></script>
<script src="${ctxStatic}/qysoftui/js/examples/forms/dropify.js" data-deps="fileupload"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="${ctxStatic}/qysoftui/vendor/formvalidation/framework/bootstrap.min.js"
        data-deps="formValidation"></script>
<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/layerUI/layer/layer.js" ></script>

<script type="text/javascript" data-deps="formValidation">


    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        var loadIndex = 0;

        $("#BackList").change(function(){
            if(!$("#BackList").val()){
                $("#bankDetail").hide();
              return;
            }
            $("#bankDetail").show();
            var data={
                BankId:$("#BackList").val()
            }
            $.app.ajax($.ctx + 'bankDetaile', data, function (data) {
                $("#bankUserName").html(data.userBankList.userBankName);
                $("#bankAddress").html(data.userBankList.commt);
                $("#bankNum").html(data.userBankList.userBankNumber);
            },function () {
                fv.disableSubmitButtons(false);
            });
        });

    })(document, window, jQuery);
</script>
