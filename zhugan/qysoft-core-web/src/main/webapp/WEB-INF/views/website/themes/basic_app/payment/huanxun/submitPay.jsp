<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE >
<html>
<head>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
</head>
<body>
<form id="form" action="${formAction}" method="post">
    <input name="${requestParam}" type="hidden" value="${paramValue}" />
</form>

<script type="text/javascript">
    $(document).ready(function(){
        $("#form").submit();
    });
</script>
</body>
</html>
