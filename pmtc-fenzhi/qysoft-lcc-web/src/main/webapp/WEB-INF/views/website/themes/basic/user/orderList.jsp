<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>订单报表</title>

<!-- 表格响应式样式 -->
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">

<div class="page animation-fade page-forms">
    <div class="page-content">
        <div class="page-header">
            <h1 class="page-title title_h1">订单报表</h1>
            <div class="page-header-actions">
                <ol class="breadcrumb">
                    <li>
                        <a href="javascript:;">财务管理</a>
                    </li>
                    <li>
                        <a href="javascript:;">订单报表</a>
                    </li>
                    <li class="active">订单报表</li>
                </ol>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body" style="padding-top:30px;">
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
                                    <th>订单编号</th>
                                    <th>用户名</th>
                                    <th>商品名称</th>
                                    <th>订单价格</th>
                                    <th>收货地址</th>
                                    <th>手机号</th>
                                    <th>收货人姓名</th>
                                    <th>状态</th>
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

    var status1 = ${fns:toJson(fns:getDictList('order_status'))};
    var isCheck = ${fns:toJson(fns:getDictList('yes_no'))};

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
                        "url": $.ctx + 'orderList',
                        "type": "POST",
                        "data": function (d) {
                            $.extend(d,$("#searchForm").serializeJSON()) ;
                        }
                    },
                    "columns": [
                        {"data": "orderSn"},
                        {"data": "userName"},
                        {"data": "goodsName"},
                        {"data": "goodsPrice"},
                        {"data": "address"},
                        {"data": "mobile"},
                        {"data": "consignee"},
                        {"data": "status"}
                    ],
                    "columnDefs": [
                    {
                        "render": function (data, type, row) {

                            return $.app.getDictLabel(status1,data,"value","label");
                        },
                        "targets": 7
                    }


                    ]
                }));

                $content.on('click', '#searchBtn', function () {
                    table.draw();
                });
                $content.on('click', '#resetBtn', function () {
                    table.draw();
                });

                $content.on('click', '.searchTBtn', function () {
                    var data = table.row($(this).parents('tr')).data();

                    $.pjax({
                        url: "${ctx}/user/bonusBill?id="+data.id,
                        container: '#admui-pageContent'
                    });

                });
            }

        };
    })(document, window, jQuery);
</script>
