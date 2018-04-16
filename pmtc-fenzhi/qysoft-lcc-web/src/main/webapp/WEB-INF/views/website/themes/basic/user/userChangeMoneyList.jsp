<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>转账记录</title>

<!-- 表格响应式样式 -->
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">


<div class="page animation-fade page-forms">
    <div class="page-content">
        <%--<div class="page-header">--%>
            <%--<h1 class="page-title title_h1">线下转账列表</h1>--%>
            <%--<div class="page-header-actions">--%>
                <%--<ol class="breadcrumb">--%>
                    <%--<li>--%>
                        <%--<a href="javascript:;">充值提现</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="javascript:;">线下转账列表</a>--%>
                    <%--</li>--%>
                    <%--<li class="active">转账信息</li>--%>
                <%--</ol>--%>
            <%--</div>--%>
        <%--</div>--%>


        <div class="panel">
            <div class="panel-body">

                <div   class="dataTables_wrapper form-inline dt-bootstrap">
                    <div class="row">
                        <div class="col-xs-12">
                            <table class="table table-bordered table-hover dataTable table-striped width-full text-nowrap" id="dataTable">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>记录编号</th>
                                    <th>充值金额</th>

                                    <th>打款人姓名</th>
                                    <th>备注</th>
                                    <th>创建时间</th>
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

    var positionSite = ${fns:toJson(fns:getDictList('usercenter_type'))};
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
                        "url": $.ctx + 'userChangeMoneyList',
                        "type": "POST",
                        "data": function (d) {
                            $.extend(d,$("#searchForm").serializeJSON()) ;
                        }
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "recordcode"},
                        {"data": "changeMoney"},
                        {"data": "bankUser"},
                        {"data": "commt"},
                        {"data": "createDate"},
                        {"data": "status"}
                    ],
                    "columnDefs": [
                        {
                            "render": function (data, type, row) {

                                return $.app.getDictLabel(positionSite,data,"value","label");
                            },
                            "targets": 6
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
