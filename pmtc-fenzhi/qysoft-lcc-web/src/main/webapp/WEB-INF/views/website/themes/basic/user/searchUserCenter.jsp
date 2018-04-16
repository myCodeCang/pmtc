<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>申请进度查询</title>

<!-- 表格响应式样式 -->
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">

<div class="page animation-fade page-forms">
    <div class="page-content">
        <div class="page-header">
            <h1 class="page-title title_h1">申请进度查询</h1>
            <div class="page-header-actions">
                <ol class="breadcrumb">
                    <li>
                        <a href="javascript:;">报单中心</a>
                    </li>
                    <li>
                        <a href="javascript:;">申请进度查询</a>
                    </li>
                    <li class="active">申请进度明细</li>
                </ol>
            </div>
        </div>
        <div class="panel">
            <div class="panel-body" style=" padding-top:30px">
                <div class="form-inline ">
                    <form id="searchForm">
                        <div class="form-group">
                            <label>报单中心名称:</label>
                            <input type="text" class="form-control" id="centerName" name="centerName" placeholder="报单中心名称">
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
                                    <th>报单中心名称</th>
                                    <th>申请时间</th>
                                    <th>申请等级</th>
                                    <th>报单中心地址</th>
                                    <th>备注信息</th>
                                    <th>处理状态</th>
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
                        "url": $.ctx + 'searchUserCenter',
                        "type": "POST",
                        "data": function (d) {
                            $.extend(d,$("#searchForm").serializeJSON()) ;
                        }
                    },
                    "columns": [
                        {"data": "userName"},
                        {"data": "centerName"},
                        {"data": "addtime"},
                        {"data": "level"},
                        {"data": "centerAddress"},
                        {"data": "commt"},
                        {"data": "status"}
                    ],
                    "columnDefs": [
                        {
                            "render": function (data, type, row) {

                                return $.app.getDictLabel(usercenterType,data,"value","label");
                            },
                            "targets": 6
                        },
                        {
                            "render": function (data, type, row) {

                                return data+"级报单中心";
                            },
                            "targets": 3
                        },
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