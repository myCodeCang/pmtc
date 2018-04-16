<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>奖金报表</title>

<!-- 表格响应式样式 -->
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">

<div class="page animation-fade page-forms">
    <div class="page-content">
        <%--<div class="page-header">--%>
            <%--<h1 class="page-title title_h1">奖金报表</h1>--%>
            <%--<div class="page-header-actions">--%>
                <%--<ol class="breadcrumb">--%>
                    <%--<li>--%>
                        <%--<a href="javascript:;">财务管理</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="javascript:;">奖金报表</a>--%>
                    <%--</li>--%>
                    <%--<li class="active">奖金报表</li>--%>
                <%--</ol>--%>
            <%--</div>--%>
        <%--</div>--%>
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
                                    <th>编号</th>
                                    <th>用户名</th>
                                    <%--<th>充值</th>--%>
                                    <%--<th>提现</th>--%>
                                    <th>首次直推奖励</th>
                                    <th>倍增奖励(复利)</th>
                                    <th>代数奖</th>
                                    <th>领导奖励</th>
                                    <th>团队奖励</th>
                                    <%--<th>报单中心奖</th>--%>
                                    <%--&lt;%&ndash;  <th>静态奖励</th>--%>
                                    <%--<th>手续费</th>--%>
                                    <%--<th>慈孝基金</th>--%>
                                    <%--<th>复消奖励</th>&ndash;%&gt;--%>
                                    <%--<th>实发收益</th>--%>
                                    <%--<th>手续费</th>--%>
                                    <th>统计时间</th>
                                   <%-- <th>是否结算</th>
                                    <th>是否发放</th>
                                    <th>操作</th>--%>
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

    var status1 = ${fns:toJson(fns:getDictList('yes_no'))};
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
                        "url": $.ctx + 'bonusReport',
                        "type": "POST",
                        "data": function (d) {
                            $.extend(d,$("#searchForm").serializeJSON()) ;
                        }
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "userName"},
                        {"data": "dividendTwo"},
                        {"data": "dividendSix"},
                        {"data": "dividendSeven"},
                        {"data": "dividendEight"},
                        {"data": "dividendTen"},
                        {"data": "createDate"}
                        // {"data": "dividendEight"},
                        // {"data": "dividendNine"},
                        // {"data": "dividendThirteen"},
                        // {"data": "dividendSeven"},
                        // {"data": "createDate"}
//                        {"data": "status"},
//                        {"data": "isCheck"}
                    ],
                    "columnDefs": [
//                        {
//                        "targets": 13,
//                        "data": null,
//                        "defaultContent": '<button  class="searchTBtn btn btn-outline btn-sm btn-primary" >明细</button>'
//                    },
//                    {
//                        "render": function (data, type, row) {
//
//                            return $.app.getDictLabel(status1,data,"value","label");
//                        },
//                        "targets": 11
//                    },
                   {
                       "render": function (data, type, row) {

                           return data.toString().substring(0,10);
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
