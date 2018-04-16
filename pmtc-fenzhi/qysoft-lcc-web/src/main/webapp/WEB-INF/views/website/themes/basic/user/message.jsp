<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
    <style>
        .radio-customs label::after{
            top: 10px !important;}
        .radio-customs label::before {
            top: 3px;}
    </style>
</head>
<title>公告详情</title>

<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/bootstrap-select/bootstrap-select.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/team/documents.css">
<script>
    function selectCateId() {
        window.location.href="/f/user/message?cateId="+$("#selectCateId").val();
    }
    function article(id) {
        window.location.href="/f/user/message?articleId="+id+"&cateId="+$("#selectCateId").val();
    }
</script>
<div class="page animation-fade page-documents">
    <div class="page-header">
        <h1 class="page-title margin-bottom-10">公告分类</h1>
    </div>
    <div class="page-content">
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-btn">
                    <select data-plugin="selectpicker" id="selectCateId" onchange="selectCateId()" data-style="btn-outline">
                        <c:forEach items="${cateList}"  var="cate">
                            <option value="${cate.id}" ${cate.id == select?'selected':''}>
                                ${cate.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <%--<button type="submit" class="input-search-btn">--%>
                    <%--<i class="icon wb-search" aria-hidden="true"></i>--%>
                <%--</button>--%>
                <%--<input type="text" class="form-control" placeholder="在知识库中查找...">--%>
            </div>
        </div>
        <div class="documents-wrap article">
            <div class="article-sidebar" id="articleAffix">
                <ul class="list-group nav">
                    <c:forEach items="${titleList}" var="cate">
                        <li class="list-group-item active" onclick="article(${cate.id})" style="position: inherit;">
                            <a href="#section-1">${cate.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="article-content" id="article">
                ${article}

            </div>
        </div>
    </div>
</div>

<script src="${ctxStatic}/qysoftui/vendor/bootstrap-select/bootstrap-select.min.js"></script>

<script src="${ctxStatic}/qysoftui/themes/classic/base/js/app.js" data-name="app"></script>
<script src="${ctxStatic}/qysoftui/js/examples/pages/team/documents.js" data-deps="app"></script>

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
