<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>计划任务管理</title>
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
    <li class="active"><a href="${ctx}/tasks/userTasks/">计划任务列表</a></li>
    <shiro:hasPermission name="user:tasks:userTasks:edit">
        <li><a href="${ctx}/tasks/userTasks/form">计划任务添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="userTasks" action="${ctx}/tasks/userTasks/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>任务名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        </li>
        <li class="btns">
            <a class="btn btn-success" type="button" href="${ctx}/tasks/userTasks/startAll">全部启动</a>
            <a class="btn btn-danger" type="button" href="${ctx}/tasks/userTasks/stopAll"
               onclick="return confirmx('确认要停止全部计划任务吗？', this.href)">全部停止</a>
            <a class="btn btn-warning" type="button" href="${ctx}/tasks/userTasks/list">刷新</a>
        </li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>任务名称</th>
        <th>任务组</th>
        <th>任务描述</th>
        <th>任务状态</th>
        <th>创建时间</th>
        <th>调度表达式</th>
        <th>上次执行时间</th>
        <th>上次执行状态</th>
        <th>业务Bean名称</th>
        <shiro:hasPermission name="user:tasks:userTasks:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="userTasks">
        <tr>
            <td><a href="${ctx}/tasks/userTasks/form?id=${userTasks.id}">
                    ${userTasks.name}
            </a></td>
            <td>${userTasks.taskGroup}</td>
            <td>${userTasks.description}</td>
            <td>
                <c:choose>
                    <c:when test="${userTasks.status == '1'}">
                        <span style="color: green; font-weight: bold">运行</span>
                    </c:when>
                    <c:otherwise>
                        <span style="color: red; font-weight: bold">停止</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatDate value="${userTasks.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${userTasks.scheduleCron}</td>
            <td><fmt:formatDate value="${userTasks.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <c:choose>
                    <c:when test="${userTasks.lastState == '1'}">
                        <span style="color: green; font-weight: bold">成功</span>
                    </c:when>
                    <c:otherwise>
                        <span style="color: red; font-weight: bold">失败</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${userTasks.beanName}</td>
            <shiro:hasPermission name="user:tasks:userTasks:edit">
                <td>
                    <a href="${ctx}/tasks/userTasks/resume?id=${userTasks.id}" style="display: ${userTasks.status == '1' ? 'none' : 'inline'}"
                       onclick="return confirmx('您当前的操作与金额或积分统计相关，若有任何疑问或对操作的结果无法确定，请联系管理员。您确认要执行当前操作吗？', this.href)">恢复</a>
                    <a href="${ctx}/tasks/userTasks/stop?id=${userTasks.id}" style="display: ${userTasks.status == '1' ? 'inline' : 'none'}"
                       onclick="return confirmx('您当前的操作与金额或积分统计相关，若有任何疑问或对操作的结果无法确定，请联系管理员。您确认要执行当前操作吗？', this.href)">停止</a>
                    <a href="${ctx}/tasks/userTasks/start?id=${userTasks.id}"
                       onclick="return confirmx('您当前的操作与金额或积分统计相关，若有任何疑问或对操作的结果无法确定，请联系管理员。您确认要执行当前操作吗？', this.href)">执行一次</a>
                    <a href="${ctx}/tasks/userTasks/getLog?id=${userTasks.id}">日志</a>
                    <a href="${ctx}/tasks/userTasks/form?id=${userTasks.id}"
                       onclick="return confirmx('您当前的操作与金额或积分统计相关，若有任何疑问或对操作的结果无法确定，请联系管理员。您确认要执行当前操作吗？', this.href)">修改</a>
                    <a href="${ctx}/tasks/userTasks/delete?id=${userTasks.id}"
                       onclick="return confirmx('确认要删除该计划任务吗？', this.href)"
                       onclick="return confirmx('您当前的操作与金额或积分统计相关，若有任何疑问或对操作的结果无法确定，请联系管理员。您确认要执行当前操作吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>