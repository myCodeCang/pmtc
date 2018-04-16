<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>

</head>
<link href="${ctxStatic}/OrgChart/css/font-awesome.min.css" rel="stylesheet" />
<link href="${ctxStatic}/OrgChart/css/jquery.orgchart.css" rel="stylesheet" />
<title>推荐网络图</title>
<sys:messageQyWeb content="${message}"/>
<div class=" page animation-fade">
    <p id="vip-word">会员网络图在pc端查看效果更佳</p>
    <div class="page-content">
        <div class="page-header">
            <h1 class="page-title title_h1">推荐网络图</h1>
            <div class="page-header-actions">
                <ol class="breadcrumb">
                    <li>
                        <a href="javascript:;">会员管理</a>
                    </li>
                    <li>
                        <a href="javascript:;">推荐网络图</a>
                    </li>
                    <li class="active">推荐网络图</li>
                </ol>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body" style="padding-top:30px;">
                <div class="form-inline ">
                    <form id="searchForm" action="${ctx}/user/user/userSun" method="post">
                        <input type="hidden" name="_pjax" value="#admui-pageContent">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <div class="form-group">
                            <label>账号:</label>
                            <input type="text" class="form-control" id="userName" name="userName" placeholder="账号" value="${userUserinfo.userName}">
                        </div>
                        <div class="form-group">
                            <label>手机号:</label>
                            <input type="text" class="form-control" id="mobile" name="mobile" placeholder="手机号" value="${userUserinfo.mobile}">
                        </div>
                        <div class="form-group">
                            <button type="submit" id="searchBtn" class="btn btn-primary">查询</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row tubiao">
                    <div class="col-xlg-12 col-lg-12 col-md-12 tubiao-one">
                        <div id="chart-container" class="chart-container">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/OrgChart/js/jquery.orgchart.js" type="text/javascript"></script>


<script type="text/javascript" data-deps="formValidation">

    (function (document, window, $) {
        var $content = $("#admui-pageContent");

        window.Content = {
            run: function () {

                this.initFormValidate();
            },

            initFormValidate : function(){

                var datascource = ${treeMap};

                var ajaxURLs = {
                    'children': function(nodeData){
                        return '${ctx}/user/user/userGridChildrenTo?id=' + nodeData.id;
                    },
                    'parent': function(nodeData){
                        return '${ctx}/user/user/userGridParentTo?id=' + nodeData.id;
                    },
                    'siblings': function(nodeData) {
                        return '${ctx}/user/user/userGridSiblingsTo?id=' + nodeData.id;
                    },
                    'families': function(nodeData) {
                        return '${ctx}/user/user/userGridFamiliesTo?id=' + nodeData.id;
                    }
                };

                $('#chart-container').orgchart({
                    'data' : datascource,
                    'nodeContent': 'title',
                    'nodeId': 'id',
                    'ajaxURL': ajaxURLs,
                    'pan': true,
                    'zoom': true
                });

            }

        };
    })(document, window, jQuery);

</script>

