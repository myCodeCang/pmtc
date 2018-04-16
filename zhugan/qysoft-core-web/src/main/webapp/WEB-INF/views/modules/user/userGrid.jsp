<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员网络图</title>
	<meta name="decorator" content="default"/>

	<link href="${ctxStatic}/OrgChart/css/font-awesome.min.css" rel="stylesheet" />
	<link href="${ctxStatic}/OrgChart/css/jquery.orgchart.css" rel="stylesheet" />

	<script src="${ctxStatic}/jquery/jquery-3.1.0.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/OrgChart/js/jquery.orgchart.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			
		});

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/user/userGrid/">会员网络图</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userUserinfo" action="${ctx}/user/userUserinfo/userGrid/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>账号：</label>
				<input type="text" class="form-control"  name="userName" placeholder="账号" value="${userName}">
			</li>
			<li><label>手机号：</label>
				<input type="text" class="form-control" name="mobile" placeholder="手机号" value="${mobile}">
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>

	<div id="chart-container">



	</div>


<script type="text/javascript">


    (function($){

        $(function() {

            var datascource = ${treeMap};

            var ajaxURLs = {
                'children': function(nodeData){
                    return '${ctx}/user/userUserinfo/userGridChildren?id=' + nodeData.id;
				},
                'parent': function(nodeData){
                    return '${ctx}/user/userUserinfo/userGridParent?id=' + nodeData.id;
                },
                'siblings': function(nodeData) {
                    return '${ctx}/user/userUserinfo/userGridSiblings?id=' + nodeData.id;
                },
                'families': function(nodeData) {
                    return '${ctx}/user/userUserinfo/userGridFamilies?id=' + nodeData.id;
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
        });

    })(jQuery);
</script>

</body>
</html>