<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>

<title>自主升级</title>


<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="col-sm-12 col-md-9">
                        <div class="example-wrap">
                            <h4 class="example-title title_h4">自主升级</h4>
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
                                        <label class="col-sm-3 control-label">会员等级:</label>
                                        <div class="col-sm-6">
                                            <span class="col-sm-3 ">${userinfo.userLevel.levelName}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">升级等级:</label>
                                        <div class="col-sm-6">
                                            <select name="userLevelId"  class="form-control">
                                                <c:forEach items="${userLevels}" var="dic">
                                                    <option value="${dic.levelCode }" >${dic.levelName }</option>
                                                </c:forEach>
                                            </select>

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
<div class="biaodan" id="biaodan">
    <form class="form-horizontal" id="subForms">
        <div class="biaodan-one">
            <h3>升级信息</h3>
            <div class="biaodan-detail">
                <span>升级会员姓名：</span><span>${userinfo.trueName}</span>
            </div>
            <div class="biaodan-detail">
                <span>升级会员手机：</span><span>${userinfo.mobile}</span>
            </div>
            <div class="biaodan-detail">
                <span>会员原等级：</span><span>${userinfo.userLevel.levelName}</span>
            </div>
            <div class="biaodan-detail">
                <span>升级后等级：</span><span id="newLevel"></span>
            </div>
            <div class="biaodan-detail">
                <span>消耗奖金币：</span><span id="levelMoney"></span>
            </div>
            <div class="biandan-but">
                <button type="submit" >确认</button><div class="btn" onclick="biaodanHide()">取消</div>
            </div>
        </div>
    </form>
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
                    $.app.ajax($.ctx + 'levelInfo', $("#userInfo").serialize(), function (data) {

                        $("#newLevel").html(data.newLevel.levelName);
                        $("#levelMoney").html(data.levelMoney);
                        $("#biaodan").css({display:"flex",display:"-webkit-flex"});

                    });
                });
                $('#subForms').formValidation().on('success.form.fv', function (e) {
                    e.preventDefault();
                    biaodanHide();
                    $.app.ajax($.ctx + 'levelChange', $("#userInfo").serialize(), function (data) {
                        $.pjax({
                            url: $(".site-contabs").find('ul.con-tabs>li.active>a').attr('href'),
                            container: '#admui-pageContent'
                        });
                        toastr.info("保存成功！");
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