<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>记录管理</title>
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
    <li class="active"><a href="${ctx}/user/transcodeBuyLog?status=${transcodeBuyLog.status}&statusBegin=${transcodeBuyLog.statusBegin}">记录列表</a></li>
    <%--<shiro:hasPermission name="user:transcodeBuyLog:edit"><li><a href="${ctx}/user/transcodeBuyLog/form">撮合成功记录添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="transcodeBuyLog" action="${ctx}/user/transcodeBuyLog/?status=${transcodeBuyLog.status}&statusBegin=${transcodeBuyLog.statusBegin}" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>订单编号：</label>
            <form:input path="id" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li><label>买家用户名：</label>
            <form:input path="buyUserName" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li><label>卖家用户名：</label>
            <form:input path="sellUserName" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单编号</th>
        <th>买家</th>
        <th>卖家</th>
        <th>买家订单号</th>
        <th>卖家订单号</th>
        <th>交易数量</th>
        <th>交易单价</th>
        <%--<th>凭证图片</th>--%>
        <th>完成类型</th>
        <th>状态</th>
        <th>订单时间</th>
        <%--<th>更新时间</th>--%>

        <%--<th>备注</th>--%>

        <shiro:hasPermission name="user:transcodeBuyLog:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="transcodeBuyLog">
        <tr>
            <td>
                    ${transcodeBuyLog.id}
            </td>
            <td>
                    ${transcodeBuyLog.buyUserName}
            </td>
            <td>
                    ${transcodeBuyLog.sellUserName}
            </td>
            <td>
                    ${transcodeBuyLog.buyId}
            </td>
            <td>
                    ${transcodeBuyLog.sellId}
            </td>
            <td>
                    ${transcodeBuyLog.num}
            </td>
            <td>
                    ${transcodeBuyLog.money}
            </td>
            <%--<td>--%>
                <%--<img src="${transcodeBuyLog.images}" style="width: 200px;height: 200px">--%>

            <%--</td>--%>
            <td>
                    ${fns:getDictLabel(transcodeBuyLog.type,'trans_log_type', '2')}

            </td>
            <td>
                    ${fns:getDictLabel(transcodeBuyLog.status,'trans_log_status', '')}

            </td>
            <td>

                <fmt:formatDate value="${transcodeBuyLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <%--<td>--%>
                <%--<fmt:formatDate value="${transcodeBuyLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
            <%--</td>--%>
                <%--<td>--%>
                <%--${transcodeBuyLog.remarks}--%>
                <%--</td>--%>
            <shiro:hasPermission name="user:transcodeBuyLog:edit">
                <td>
                    <c:if test="${transcodeBuyLog.status > 0}">
                    <a href="${ctx}/user/transcodeBuyLog/form?id=${transcodeBuyLog.id}" class="layui-btn layui-btn-mini layui-btn-normal">查看</a>
                    </c:if>
                    <c:if test="${transcodeBuyLog.status == '0'}">
                        <a href="${ctx}/user/transcodeBuyLog/buyUnconfirmed?id=${transcodeBuyLog.id}" class="layui-btn layui-btn-mini layui-btn-normal"
                           onclick="return confirmx('买方未确认该订单,是否仲裁该订单?', this.href)" >买方未打款</a>
                    </c:if>
                    <c:if test="${transcodeBuyLog.status == '1'}">
                        <a href="${ctx}/user/transcodeBuyLog/buyUnconfirmed?id=${transcodeBuyLog.id}" class="layui-btn layui-btn-mini layui-btn-normal"
                           onclick="return confirmx('买方未确认该订单,是否仲裁该订单?', this.href)" >买方未打款</a>
                        <a href="${ctx}/user/transcodeBuyLog/sellUnconfirmed?id=${transcodeBuyLog.id}" class="layui-btn layui-btn-mini layui-btn-normal"
                           onclick="return confirmx('卖方未确认该订单,是否仲裁该订单？', this.href)" >卖方未确认</a>
                    </c:if>
                    <%--<a href="${ctx}/user/transcodeBuyLog/stop?id=${transcodeBuyLog.id}" class="layui-btn layui-btn-mini layui-btn-normal"--%>
                       <%--onclick="return confirmx('卖方未确认该订单,是否完成该订单？', this.href)" >终止订单</a>--%>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>