<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>团队转账计算</title>
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
    <li class="active"><a href="##">团队转账计算</a></li>
</ul>
<form:form id="searchForm" modelAttribute="userAccountchange"
           action="${ctx}/user/hclmReport/teamTransfer/" method="post"
           class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>领导人:</label>
            <input type="text" name="leadName" value="${leadName}" placeholder="请输入领导人">
        </li>
        <li><label>时间：</label>
            <input name="createDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${createDateBegin}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
            <input name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${createDateEnd}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary"
                                type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<style>
    .bochu-one {
        margin: 20px 0;
        width: 100%;
    }

    .bochu-one > div {
        padding: 0 0.75rem;
        width: 50%;
        box-sizing: border-box;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        -o-box-sizing: border-box;
    }

    .bochu-one > div > div {
        background-color: #0e90d2;
        border-radius: 8px;
    }

    .bochu-one > div > div > p {
        margin: 0;
        padding: 0 30px;
        color: #fff;
        padding-bottom: 15px;
    }

    .bochu-one > div > div > div {
        padding: 15px 30px;
        padding-top: 20px;
    }

    .bochu-one > div > div > div > span {
        font-size: 24px;
        color: #fff;
    }

    .bochu {
        margin-top: 20px;
        width: 100%;
        margin-bottom: 20px;
    }

    .bochu ul {
        width: 100%;
    }

    .bochu li {
        margin-bottom: 20px;
        padding: 0 0.75rem;
        padding-right: 0.75rem;
        width: 50%;
        float: left;
        box-sizing: border-box;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        -o-box-sizing: border-box;
    }

    .bochu li > div {
        background-color: #0e90d2;
        border-radius: 8px;
    }

    .bochu li > div > div {
        padding: 15px 30px;
        padding-top: 20px;
    }

    .bochu li > div > p {
        margin: 0;
        padding: 0 30px;
        color: #fff;
        padding-bottom: 15px;
    }

    .bochu li > div > div > span {
        font-size: 24px;
        color: #fff;
    }
</style>
<%--<blockquote class="layui-elem-quote" style="height: 20px;">实时持币统计</blockquote>--%>
<div class="bochu">
    <ul>
        <li>
            <div>
                <div>
                    <span>团队转账转入[交易端-->会员端]:</span>&nbsp;<span>${allMoneyToIn}</span>
                </div>
                <p>当前领导人团队所有用户转入会员端总和</p>
            </div>
        </li>
        <li>
            <div>
                <div>
                    <span>团队转账转出[会员端->交易端]:</span>&nbsp;<span>${allMoneyToOut}</span>
                </div>
                <p>当前领导人团队所有用户转入交易端总和</p>
            </div>
        </li>
        <li>
            <div>
                <div>
                    <span>团队总转出减去团队总转入:</span>&nbsp;<span>${subResult}</span>
                </div>
                <p>当前领导人团队有所用户转出减去转入</p>
            </div>
        </li>

    </ul>
</div>
</body>
</html>