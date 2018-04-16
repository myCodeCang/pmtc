<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>推荐会员列表</title>

<!-- 表格响应式样式 -->
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">


<div class="page animation-fade page-forms">
    <div class="page-content">
        <%--<div class="page-header">--%>
        <%--<h1 class="page-title title_h1">推荐会员列表</h1>--%>
        <%--<div class="page-header-actions">--%>
        <%--<ol class="breadcrumb">--%>
        <%--<li>--%>
        <%--<a href="javascript:;">报单中心</a>--%>
        <%--</li>--%>
        <%--<li>--%>
        <%--<a href="javascript:;">推荐会员列表</a>--%>
        <%--</li>--%>
        <%--<li class="active">会员信息</li>--%>
        <%--</ol>--%>
        <%--</div>--%>
        <%--</div>--%>
        <div class="panel">
            <div class="panel-body" style="padding-top:30px;">
                <div class="form-inline ">
                    <form id="searchForm">
                        <div class="form-group">
                            <input type="hidden" class="form-control" id="parentName" name="parentName">
                            <%--<label>用户名:</label>--%>
                            <%--<input type="text" class="form-control" id="userName" name="userName" placeholder="用户名">--%>
                        </div>
                        <div class="form-group">
                            <label>手机号:</label>
                            <input type="text" class="form-control" id="mobile" name="mobile" placeholder="手机号">

                        </div>
                        <div class="form-group">
                            <label>开始日期:</label>
                            <input type="text" class="form-control" id="beginAddtime" name="beginAddtime"
                                   placeholder="开始日期"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">
                        </div>
                        <div class="form-group">
                            <label>结束日期:</label>
                            <input type="text" class="form-control" id="endAddtime" name="endAddtime" placeholder="结束日期"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});">
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="userName" value="${userName}">
                            <button type="button" id="searchBtn" class="btn btn-primary">查询</button>
                            <button type="reset" id="resetBtn" class="btn btn-info">重置</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="panel">
            <div class="panel-body">

                <div class="dataTables_wrapper form-inline dt-bootstrap">
                    <div class="row">
                        <div class="col-xs-12">
                            <table class="table table-bordered table-hover dataTable table-striped width-full text-nowrap"
                                   id="dataTable">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>用户名</th>
                                    <%--<th>手机号</th>--%>
                                    <th>昵称</th>
                                    <th>用户状态</th>
                                    <%--<th>接点人</th>--%>
                                    <%--<th>报单中心</th>--%>
                                    <th>推荐人</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
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
<style>
    .modal {
        z-index: 2000;
    }

    .bg-in {
        background: rgba(0, 0, 0, 0.5);
    }
</style>
<div class="modal fade" id="projectForm" role="dialog" tabindex="-1">
    <div class="modal-dialog modal-center">
        <form class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭" onclick="activeUserOne()">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="exampleFormModalLabel">推荐用户激活</h4>
            </div>
            <div class="modal-body">
                <form>

                    <div class="row" style="padding-top:10px;">
                        <div class="col-lg-3 form-group" style="text-align: center;height: 32px;line-height: 32px;">
                            <span style="">激活编号:</span>
                        </div>
                        <div class="col-lg-9 form-group">
                            <input type="text" class="form-control" id="actUserName" readonly name="userName"
                                   placeholder="被激活人姓名">
                            <input type="hidden" class="form-control" id="actId" readonly>
                        </div>
                        <div class="col-lg-3 form-group" style="text-align: center;height: 32px;line-height: 32px;">
                            <span>昵称:</span>
                        </div>
                        <div class="col-lg-9 form-group">
                            <input type="text" class="form-control" id="actTrueName" readonly name="userName"
                                   placeholder="被激活人姓名">
                        </div>
                        <div class="col-lg-3 form-group" style="text-align: center;height: 32px;line-height: 32px;">
                            <span>PMTC:</span>
                        </div>
                        <div class="col-lg-9 form-group">
                            <input type="text" class="form-control" onKeyUp="amount(this)" onBlur="overFormat(this)"
                                   id="actMoney" placeholder="请输入激活数额">
                        </div>
                        <div class="modal-footer text-right" style="margin-top: 90px;">
                            <a class="btn btn-primary" href="javascript:void (0);" onclick="toActive()">确认</a>
                            <a class="btn btn-default" data-dismiss="modal" href="javascript:;"
                               onclick="activeUserOne()">取消</a>
                        </div>
                    </div>
                </form>
            </div>
        </form>
    </div>
</div>
<!-- 表格筛选js -->
<script src="${ctxStatic}/qysoftui/vendor/datatables/jquery.dataTables.min.js" data-name="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.min.js"
        data-deps="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.min.js"
        data-deps="dataTables"></script>


<script type="text/javascript">
    var positionSite = ${fns:toJson(fns:getDictList('position_site'))};
    var activeStatus = ${fns:toJson(fns:getDictList('active_status'))};
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
                        "url": $.ctx + 'userList',
                        "type": "POST",
                        "data": function (d) {
                            $.extend(d, $("#searchForm").serializeJSON());
                        }
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "userName"},
                        // {"data": "mobile"},
                        {"data": "userNicename"},
                        {"data": "activeStatus"},
//                        {"data": "userLevel.levelName"},
                        {"data": "parentName"},
//                        {"data": "serverName"},
//                        {"data": "walterName"},

                        {"data": "createDate"},
                        {"data": ""}
                    ],
                    "createdRow": function (row, ret, index) {
//                        alert(ret.activeStatus);
//                         if (ret.activeStatus == 1) {
//                             $('td', row).eq(7).html("");
//                         }
                    },
                    "columnDefs": [
                        {
                            "render": function (data, type, row) {

                                return $.app.getDictLabel(activeStatus, data, "value", "label");
                            },
                            "targets": 3
                        },
                        {
                            "targets": 6,
                            "data": null,
                            "render": function (data, type, row, meta) {

                                return '<button type="button"  data-rowindex="' + meta.row + '" class="searchTBtn btn btn-primary" >转账</button>';
                            }

                        }

                    ],

                }));

                $content.on('click', '#searchBtn', function () {
                    table.draw();
                });
                $content.on('click', '#resetBtn', function () {
                    table.draw();
                });
                $content.on('click', '.searchTBtn', function () {


                    var rowindex = $(this).attr("data-rowindex");
                    var data = table.rows(rowindex).data()[0];
                    // $("#actUserName").val(data.userName);
                    // $("#actId").val(data.id);
                    // $("#actTrueName").val(data.trueName);
                    activeUser(data.userName);


                });
            }
        };


    })(document, window, jQuery);


    function activeUser(userName) {
        window.location.href="/f/user/userCoinToServer?userName="+userName;
    }

    function activeUserOne() {
        $('#projectForm').removeClass('in bg-in');
        $('#projectForm').hide();
    }

    function toActive() {
        var actId = $("#actId").val();
        // var actMoney = $("#actMoney").val();
        // if (actMoney == "") {
        //     toastr.info("激活数额不能为空！");
        //     return;
        // }
        $.app.ajax('/f/user/transferOfAccount', {actId: actId}, function (data) {
            // toastr.info(data.info);
            // setTimeout(function () {
            //     location.reload();
            // }, 1000)

        }, function (res) {
            $("#actMoney").val("");
        });

        $('#projectForm').removeClass('in bg-in');
        $('#projectForm').hide();


    }

    //限制输入金额
    function amount(th) {
        var regStrs = [
            ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
            ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
            ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
            ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
        ];
        for (var i = 0; i < regStrs.length; i++) {
            var reg = new RegExp(regStrs[i][0]);
            th.value = th.value.replace(reg, regStrs[i][1]);
        }
    }

    function overFormat(th) {
        var v = th.value;
        if (v === '') {
            v = '0.00';
        } else if (v === '0') {
            v = '0.00';
        } else if (v === '0.') {
            v = '0.00';
        } else if (/^0+\d+\.?\d*.*$/.test(v)) {
            v = v.replace(/^0+(\d+\.?\d*).*$/, '$1');
            v = inp.getRightPriceFormat(v).val;
        } else if (/^0\.\d$/.test(v)) {
            v = v + '0';
        } else if (!/^\d+\.\d{2}$/.test(v)) {
            if (/^\d+\.\d{2}.+/.test(v)) {
                v = v.replace(/^(\d+\.\d{2}).*$/, '$1');
            } else if (/^\d+$/.test(v)) {
                v = v + '.00';
            } else if (/^\d+\.$/.test(v)) {
                v = v + '00';
            } else if (/^\d+\.\d$/.test(v)) {
                v = v + '0';
            } else if (/^[^\d]+\d+\.?\d*$/.test(v)) {
                v = v.replace(/^[^\d]+(\d+\.?\d*)$/, '$1');
            } else if (/\d+/.test(v)) {
                v = v.replace(/^[^\d]*(\d+\.?\d*).*$/, '$1');
                ty = false;
            } else if (/^0+\d+\.?\d*$/.test(v)) {
                v = v.replace(/^0+(\d+\.?\d*)$/, '$1');
                ty = false;
            } else {
                v = '0.00';
            }
        }
        th.value = v;
    }
</script>
