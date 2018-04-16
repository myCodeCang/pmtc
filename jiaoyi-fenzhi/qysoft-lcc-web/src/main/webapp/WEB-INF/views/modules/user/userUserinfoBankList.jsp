<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>会员银行卡管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/user/userUserinfoBank/">会员银行卡列表</a></li>
    <%--<shiro:hasPermission name="user:userUserinfoBank:edit">--%>
        <%--<li><a href="${ctx}/user/userUserinfoBank/form">提款银行设置添加</a></li>--%>
    <%--</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="userUserinfoBank" action="${ctx}/user/userUserinfoBank/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>用户名：</label>
            <form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>用户名</th>
        <th>银行名称</th>
        <th>银行卡号</th>
        <th>开户人姓名</th>
        <%--<th>开户份</th>--%>
        <%--<th>开户市</th>--%>
        <%--<th>开户区</th>--%>
        <th>开户地址</th>
        <th>创建时间</th>
        <%--<th>银行代码</th>--%>

        <shiro:hasPermission name="user:userUserinfoBank:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="userUserinfoBank">
        <tr>
            <td>${userUserinfoBank.userName}</td>
            <td>
                    ${userUserinfoBank.bankName}
            </td>
            <td>
                    ${userUserinfoBank.bankUserNum}
            </td>
            <td>
                    ${userUserinfoBank.bankUserName}
            </td>
                <%--<td>--%>
                <%--${fns:getDictLabel(userUserinfoBank.joinActive, 'yes_no', '')}--%>
                <%--</td>--%>
            <%--<td>--%>
                    <%--${userUserinfoBank.provinces}--%>
            <%--</td>--%>
            <%--<td>--%>
                    <%--${userUserinfoBank.citys}--%>
            <%--</td>--%>
            <%--<td>--%>
                    <%--${userUserinfoBank.area}--%>
            <%--</td>--%>
            <td>
                    ${userUserinfoBank.bankUserAddress}
            </td>
            <td>
                <fmt:formatDate value="${userUserinfoBank.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <%--<td>--%>
                    <%--${userUserinfoBank.bankCode}--%>
            <%--</td>--%>

            <shiro:hasPermission name="user:userUserinfoBank:edit">
                <td>
                    <a href="${ctx}/user/userUserinfoBank/form?id=${userUserinfoBank.id}">查看</a>
                    <%--<a href="${ctx}/user/userUserinfoBank/delete?id=${userUserinfoBank.id}"--%>
                       <%--onclick="return confirmx('确认要删除该银行卡吗？', this.href)">删除</a>--%>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>