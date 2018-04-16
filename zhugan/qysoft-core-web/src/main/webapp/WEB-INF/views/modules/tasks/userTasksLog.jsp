<%@page import="com.thinkgem.jeesite.common.web.Servlets"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>日志</title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
<div class="container-fluid">
    <div class="page-header"><h1>日志内容: </h1></div>
    <div style="margin-bottom: 10px">${logInfo}</div>
    <div><a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></div>
    <script>try{top.$.jBox.closeTip();}catch(e){}</script>
</div>
</body>
</html>