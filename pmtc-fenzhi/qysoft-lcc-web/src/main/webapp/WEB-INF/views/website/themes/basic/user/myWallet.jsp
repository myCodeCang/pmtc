<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>

<title>我的钱包</title>

<link rel="stylesheet" href="${ctxStatic}/qysoftui/fonts/themify/themify.css">

<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/home/v2.css">
<link rel="stylesheet" h

      ref="${ctxStatic}/qysoftui/css/examples/components/advanced/masonry.css">

<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/team//documents.css">

<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/chartist-js/chartist.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/chartist-plugin-tooltip/chartist-plugin-tooltip.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/home/v1.css">


<script src="${ctxStatic}/qysoftui/vendor/datatables/jquery.dataTables.min.js" data-name="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.min.js"
        data-deps="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.min.js"
        data-deps="dataTables"></script>


<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">

                <div class="col-lg-3 col-md-6">
                    <div class="widget widget-shadow">
                        <div class="widget-header padding-30 bg-blue-600 white">
                            <a class="avatar avatar-100 img-bordered bg-white pull-right" href="javascript:;">
                                <img src="${ctxStatic}/qysoftui/images/pmtc.png" alt="">
                            </a>
                            <div class="vertical-align height-100 text-truncate">
                                <div class="vertical-align-middle" >
                                    <div style="font-size:30px"><strong>${userinfo.userNicename}</strong></div>
                                    <div>${userinfo.areaId}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-6 col-md-6">
                    <div class="widget widget-shadow">
                        <div class="widget-content padding-20 bg-blue-700 white height-full">
                            <%--<a class="avatar pull-left margin-right-20" href="javascript:;">--%>
                            <%--<img src="${ctxStatic}/layerUI/images/portraits/15.jpg" alt="">--%>
                            <%--</a>--%>
                            <div style="overflow:hidden;">
                                <small class="pull-right grey-200"></small>
                                <div class="font-size-18"><strong style="font-size: 23px">我的PMTC钱包地址:</strong></div>
                                <p><strong style="font-size: 25px">${userinfo.remarks}</strong></p>
                                <p>请牢记你的PMTC钱包地址,收款时复制使用</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="widget widget-shadow ">
                        <div class="widget-content bg-white padding-20">
                            <button type="button" class="btn btn-floating btn-sm btn-warning">
                                <i class="icon wb-users"></i>
                            </button>
                            <span class="margin-left-15 font-weight-400">业绩钱包</span>
                            <div class="content-text text-center margin-bottom-0">

                                <span class="font-size-40 font-weight-100">&yen;0</span>
                                <p class="blue-grey-400 font-weight-100 margin-0">.</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div>
                    <div class="col-lg-3 col-sm-6 col-xs-12 info-panel">
                        <div class="widget widget-shadow">
                            <div class="widget-content bg-white padding-20">
                                <button type="button" class="btn btn-floating btn-sm btn-warning">
                                    <i class="icon fa-yen"></i>
                                </button>
                                <span class="margin-left-15 font-weight-400">交易钱包</span>
                                <div class="content-text text-center margin-bottom-0">

                                    <span class="font-size-40 font-weight-100">&yen;${userinfo.money}</span>
                                    <p class="blue-grey-400 font-weight-100 margin-0">.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 col-xs-12 info-panel">
                        <div class="widget widget-shadow">
                            <div class="widget-content bg-white padding-20">
                                <button type="button" class="btn btn-floating btn-sm btn-danger">
                                    <i class="icon fa-yen"></i>
                                </button>
                                <span class="margin-left-15 font-weight-400">矿机钱包</span>
                                <div class="content-text text-center margin-bottom-0">

                                    <span class="font-size-40 font-weight-100" style="color:#FF6347">&yen;${userinfo.money2}</span>
                                    <p class="blue-grey-400 font-weight-100 margin-0">.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 col-xs-12 info-panel">
                        <div class="widget widget-shadow">
                            <div class="widget-content bg-white padding-20">
                                <button type="button" class="btn btn-floating btn-sm btn-success">
                                    <i class="icon fa-yen"></i>
                                </button>
                                <span class="margin-left-15 font-weight-400">动态钱包</span>
                                <div class="content-text text-center margin-bottom-0">

                                    <span class="font-size-40 font-weight-100">&yen;${userinfo.money3}</span>
                                    <p class="blue-grey-400 font-weight-100 margin-0">.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 col-xs-12 info-panel">
                        <div class="widget widget-shadow">
                            <div class="widget-content bg-white padding-20">
                                <button type="button" class="btn btn-floating btn-sm btn-primary">
                                    <i class="icon fa-yen"></i>
                                </button>
                                <span class="margin-left-15 font-weight-400">投资钱包</span>
                                <div class="content-text text-center margin-bottom-0">

                                    <span class="font-size-40 font-weight-100">&yen;${userinfo.money4}</span>
                                    <p class="blue-grey-400 font-weight-100 margin-0">.</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="">
                        <div class="row height-full">
                            <div class="col-lg-6 col-sm-6 col-xs-12" style="height:50%;">
                                <div class="widget widget-shadow bg-blue-600 white" id="widgetLinepoint">
                                    <div class="widget-content">
                                        <div class="padding-top-25 padding-horizontal-30">
                                            <div class="row no-space">
                                                <div class="col-xs-6">
                                                    <p>今日矿机产能</p>
                                                    <%--<p class="blue-200">最新单笔收入 &yen;223.45</p>--%>
                                                </div>
                                                <div class="col-xs-6 text-right">
                                                    <p class="font-size-30 text-nowrap">&yen;${compoundInterest}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ct-chart height-120"></div>
                                    </div>
                                </div>

                            </div>
                            <div class="col-lg-6 col-sm-6 col-xs-12" style="height:50%;">

                                <div class="widget widget-shadow bg-purple-600 white" id="widgetSaleBar">
                                    <div class="widget-content">
                                        <div class="padding-top-25 padding-horizontal-30">
                                            <div class="row no-space">
                                                <div class="col-xs-6">
                                                    <p>今日动态产能</p>
                                                    <%--<p class="purple-200">同比增长 2%</p>--%>
                                                </div>
                                                <div class="col-xs-6 text-right">
                                                    <p class="font-size-30 text-nowrap">&yen;0</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ct-chart height-120"></div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>


<script src="${ctxStatic}/qysoftui/js/examples/tables/data-tables/plug-ins/range-filtering.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/masonry/masonry.pkgd.min.js"></script>
<script src="${ctxStatic}/layerUI/layer/layer.js"></script>
<script src="${ctxStatic}/layerUI/layui.js"></script>

<script src="${ctxStatic}/qysoftui/vendor/skycons/skycons.min.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/matchheight/jquery.matchHeight.min.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/chartist-js/chartist.js" data-name="chartist"></script>
<script src="${ctxStatic}/qysoftui/vendor/chartist-plugin-tooltip/chartist-plugin-tooltip.js"
        data-deps="chartist"></script>
<script src="${ctxStatic}/qysoftui/js/examples/pages/home/home-v1.js"></script>
