<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>账变列表</title>

<!-- 表格响应式样式 -->
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">


<div class="page animation-fade page-forms">
    <div class="page-content">
        <%--<div class="page-header">--%>
            <%--<h1 class="page-title title_h1">帐变明细</h1>--%>
            <%--<div class="page-header-actions">--%>
                <%--<ol class="breadcrumb">--%>
                    <%--<li>--%>
                        <%--<a href="javascript:;">财务管理</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="javascript:;">帐变列表</a>--%>
                    <%--</li>--%>
                    <%--<li class="active">帐变明细</li>--%>
                <%--</ol>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="panel">
            <div class="panel-body" style="padding-top:30px;">
                <div class="form-inline ">
                    <form id="searchForm">


                        <div class="form-group">
                            <label>账变类型:</label>
                            <select name="changeType" class="form-control">
                                <option value="">全部</option>
                                <option value="41">总钱包内部转账</option>
                                <option value="300">总钱包互转</option>
                                <option value="400">奖金查询</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>消费类型:</label>
                            <select name="moneyType" class="form-control">
                                <option value="">全部</option>
                                <c:forEach items="${fns:getDictList('money_type')}" var="dict">
                                    <option value="${dict.value}">${dict.label}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>开始日期:</label>
                            <input type="text" class="form-control" id="createDateBegin" name="createDateBegin" placeholder="开始日期"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">
                        </div>
                        <div class="form-group">
                            <label>结束日期:</label>
                            <input type="text" class="form-control" id="createDateEnd" name="createDateEnd" placeholder="结束日期"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">
                        </div>
                        <div class="form-group">
                            <button type="button" id="searchBtn" class="btn btn-primary">查询</button>
                            <button type="reset" id="resetBtn" class="btn btn-info">重置</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="panel">
            <div class="panel-body">

                <div   class="dataTables_wrapper form-inline dt-bootstrap">
                    <div class="row">
                        <div class="col-xs-12">
                            <table class="table table-bordered table-hover dataTable table-striped width-full text-nowrap" id="dataTable">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>用户名</th>
                                    <%--<th>会员昵称</th>--%>
                                    <th>账变类型</th>
                                    <th>消费类型</th>
                                    <th>账变金额</th>
                                    <th>账变前金额</th>
                                    <th>账变后金额</th>
                                    <th>创建时间</th>
                                    <th>备注</th>
                                </tr>
                                </thead>

                            </table>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 表格筛选js -->
<script src="${ctxStatic}/qysoftui/vendor/datatables/jquery.dataTables.min.js" data-name="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.min.js"
        data-deps="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.min.js"
        data-deps="dataTables"></script>


<script type="text/javascript">
    var arr1 = ['201','203','202','205'];
    var arr2 = ['9','14','16','20'];
    var changeType = ${fns:toJson(fns:getDictList('change_type'))};
    var moneyType = ${fns:toJson(fns:getDictList('money_type'))};
    (function (document, window, $) {
        var $content = $("#admui-pageContent");


        window.Content = {
            run: function () {

                this.initTable();
            },

            initTable: function () {

                var table = $('#dataTable').DataTable($.po('dataTable', {
                    "processing": true,
                    "serverSide": true,
                    "ordering": false,
                    "searching": false,
                    "lengthMenu": [10, 25, 50],
                    "ajax": {
                        "url": $.ctx + 'accoundChange',
                        "type": "POST",
                        "data": function (d) {
                            $.extend(d,$("#searchForm").serializeJSON()) ;
                        }
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "userName"},
                        // {"data": "userUserinfo.userNicename"},
                        {"data": "changeType"},
                        {"data": "moneyType"},
                        {"data": "changeMoney"},
                        {"data": "beforeMoney"},
                        {"data": "afterMoney"},
                        {"data": "createDate"},
                        {"data": "commt"}
                    ],
                    "columnDefs": [
                        {
                            "render": function (data, type, row) {
                                if(arr1.indexOf(data)>-1){
                                    data = 300;
                                }
                                if(arr2.indexOf(data) >-1){
                                    data = 400;
                                }
                                return $.app.getDictLabel(changeType,data,"value","label");
                            },
                            "targets": 2
                        },
                        {
                            "render": function (data, type, row) {

                                return $.app.getDictLabel(moneyType,data,"value","label");
                            },
                            "targets": 3
                        }
                    ]
                }));

                $content.on('click', '#searchBtn', function () {
                    table.draw();
                });
                $content.on('click', '#resetBtn', function () {
                    table.draw();
                });

            }

        };
    })(document, window, jQuery);
</script>
