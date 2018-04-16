<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>提现明细</title>

<!-- 表格响应式样式 -->
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">

<div class="page animation-fade page-forms">
    <div class="page-content">
        <div class="page-header">
            <h1 class="page-title title_h1">提现明细</h1>
            <div class="page-header-actions">
                <ol class="breadcrumb">
                    <li>
                        <a href="javascript:;">充值提现</a>
                    </li>
                    <li>
                        <a href="javascript:;">提现列表</a>
                    </li>
                    <li class="active">提现明细</li>
                </ol>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body" style=" padding-top:30px;">
                <div class="form-inline ">
                    <form id="searchForm">
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
                                    <th>用户名</th>
                                    <th>提款编码</th>
                                    <th>提现前金额</th>
                                    <th>账变后金额</th>
                                    <th>提现金额</th>
                                    <th>实际到账金额</th>
                                    <th>银行卡号</th>
                                    <th>提现时间</th>
                                    <th>提现状态</th>
                                    <th>处理建议</th>
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

    var usercenterType = ${fns:toJson(fns:getDictList('usercenter_type'))};
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
                        "url": $.ctx + 'withdrawDetail',
                        "type": "POST",
                        "data": function (d) {
                            $.extend(d,$("#searchForm").serializeJSON()) ;
                        }
                    },
                    "columns": [
                        {"data": "userName"},
                        {"data": "recordcode"},
                        {"data": "beforeMoney"},
                        {"data": "afterMoney"},
                        {"data": "changeMoney"},
                        {"data": "realMoney"},
                        {"data": "userBankNum"},
                        {"data": "createDate"},
                        {"data": "status"},
                        {"data": "commt"}
                    ],
                    "columnDefs": [
                        {
                            "render": function (data, type, row) {

                                return $.app.getDictLabel(usercenterType,data,"value","label");
                            },
                            "targets": 8
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
