<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE >
<html>
<head>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
</head>
<body>
<form id="form" action="/f/payment/pay/alipayWeb2" method="post">
    <input name="amount" type="hidden" value="${amount}" />
    <input name="tradeNo" type="hidden" value="${tradeNo}" />
    <input name="subject" type="hidden" value="${subject}" />
    <input name="body" type="hidden" value="${body}" />
</form>

<script type="text/javascript">
    $(document).ready(function(){
        $("#form").submit();
    });
</script>
</body>
</html>
