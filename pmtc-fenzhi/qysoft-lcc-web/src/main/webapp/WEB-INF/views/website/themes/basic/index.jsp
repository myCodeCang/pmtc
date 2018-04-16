<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>

<title>首页</title>

<link rel="stylesheet" href="${ctxStatic}/qysoftui/fonts/themify/themify.css">

<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/home/v2.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/components/advanced/masonry.css">



<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/pages/team//documents.css">

<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/css/examples/components/basic/icon.css">
<link rel="stylesheet" href="${ctxStatic}/qysoftui/fonts/glyphicons/glyphicons.css">

<script src="${ctxStatic}/layerUI/layer/layer.js"></script>
<script src="${ctxStatic}/layerUI/layui.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables/jquery.dataTables.min.js" data-name="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-bootstrap/dataTables.bootstrap.min.js"
        data-deps="dataTables"></script>
<script src="${ctxStatic}/qysoftui/vendor/datatables-responsive/dataTables.responsive.min.js"
        data-deps="dataTables"></script>
<script>
    function toDetail(articleId) {
        window.location.href="/f/user/message?articleId="+articleId+"&cateId=70606";
    }
</script>
<style>
    .btn-floating.btn-sm {
        width: 26px;
        height: 26px;
    }
    .header-top{background-repeat: no-repeat;height:230px;background-size:cover;background-position:left bottom;display: flex;display:-webkit-flex;justify-content: center;-webkit-justify-content: center;align-items: center;-webkit-align-items: center;}
    @media screen and (max-width: 640px) {
        .header-top{height: 150px !important;}
    }
    .vertical-align:before{height: initial;}
</style>
<div class="page animation-fade page-index-v2" style="min-height:0 !important;">
    <div class="page-header margin-bottom-30 header-top">
        <div class="text-center blue-grey-800 margin-top-50 margin-xs-0">
            <strong><div class="font-size-20 blue-grey-800" style="color:#1C86EE !important;">${userinfo.userName}(${userinfo.userNicename})您好!<br>欢迎登入系统</div></strong>
            <div class="row"
                 style="display: flex;display: -webkit-flex;justify-content: center;-webkit-justify-content: center;align-items: center;-webkit-align-items: center;">
            </div>
        </div>
    </div>

    <%--<div class="col-lg-3 col-md-6">--%>
        <%--<div class="widget widget-shadow">--%>
            <%--<div class="widget-header padding-30 bg-white">--%>
                <%--<a class="avatar avatar-100 pull-left margin-right-20" href="javascript:;">--%>
                    <%--<img src="${ctxStatic}/qysoftui/images/portraits/2.jpg" alt="">--%>
                <%--</a>--%>
                <%--<div class="vertical-align text-right height-100 text-truncate">--%>
                    <%--<div class="vertical-align-middle" style="font-size: 20px">--%>
                        <%--<div class="font-size-20 margin-bottom-5 blue-600 text-truncate"><strong>${userinfo.trueName}</strong></div>--%>
                        <%--<div class="font-size-14 text-truncate">一级天使</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="col-lg-3 col-md-6">
        <div class="widget widget-shadow" style="margin-bottom: 15px !important;">
            <div class="widget-header padding-20 bg-blue-600 white">
                <%--<a class="avatar avatar-100 img-bordered bg-white pull-left" href="javascript:;">--%>
                    <%--<img src="${ctxStatic}/qysoftui/images/pmtc.png" alt="">--%>
                <%--</a>--%>
                <div class="vertical-align height-100 text-truncate">
                    <div class="vertical-align-middle">
                        <div class="font-size-16">您好,欢迎登陆</div>
                        <div class="font-size-14 margin-top-5">等级: ${userinfo.areaId}</div>
                        <div class="font-size-14 margin-top-5">有效推荐: ${walterListSize} 人</div>
                        <div class="font-size-14">注册时间: <fmt:formatDate value="${userinfo.createDate}" pattern="yyyy-MM-dd"/></div>
                        <div class="font-size-14">上次登录: <fmt:formatDate value="${userinfo.lastLoginTime}" pattern="yyyy-MM-dd"/></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-6 col-md-6">
        <div class="widget widget-shadow" style="margin-bottom: 15px !important;">
            <div class="widget-content padding-20 bg-blue-700 white height-full">
                <%--<a class="avatar pull-left margin-right-20" href="javascript:;">--%>
                    <%--<img src="${ctxStatic}/layerUI/images/portraits/15.jpg" alt="">--%>
                <%--</a>--%>
                <div style="overflow:hidden;">
                    <small class="pull-right grey-200"></small>
                    <div class="font-size-18"><strong style="font-size: 23px">我的PMTC钱包地址:</strong></div>
                    <p><strong style="font-size: 25px;word-wrap: break-word;">${userinfo.remarks}</strong></p>
                    <p>请牢记你的PMTC钱包地址,收款时复制使用</p>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="widget widget-shadow" style="margin-bottom: 15px !important;">
            <div class="widget-content padding-20 bg-blue-700 white height-full">
                <%--<a class="avatar pull-left margin-right-20" href="javascript:;">--%>
                <%--<img src="${ctxStatic}/layerUI/images/portraits/15.jpg" alt="">--%>
                <%--</a>--%>
                <div style="overflow:hidden;">
                    <small class="pull-right grey-200"></small>
                    <div class="font-size-18"><strong style="font-size: 23px;">我的分享地址:</strong></div>
                    <p><strong style="font-size: 25px;word-wrap: break-word;" id="share"></strong></p>
                </div>
            </div>
        </div>
    </div>



    <div class="col-lg-2 col-sm-6 col-xs-12 info-panel" style="height:110px;">
        <div class="widget-shadow margin-bottom-15">
            <div class="widget-content bg-white padding-15" style="background-color: #8080c0">
                <button type="button" class="btn btn-floating btn-sm btn-warning">
                    <i class="icon fa-yen"></i>
                </button>
                <span class="margin-left-15 font-weight-400 font-size-16" style="color:#fff;">交易钱包</span>
                <div class="content-text text-center margin-bottom-0">

                    <span class="font-size-26 font-weight-100" style="color:#fff;">&yen;${userinfo.money}</span>

                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-2 col-sm-6 col-xs-12 info-panel" style="height:110px;">
        <div class="widget-shadow margin-bottom-15">
            <div class="widget-content bg-white padding-15" style="background-color: #008000">
                <button type="button" class="btn btn-floating btn-sm btn-success">
                    <i class="icon fa-yen"></i>
                </button>
                <span class="margin-left-15 font-weight-400 font-size-16" style="color:#fff;">动态钱包</span>
                <div class="content-text text-center margin-bottom-0">

                    <span class="font-size-26 font-weight-100" style="color:#fff;">&yen;${userinfo.money3}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-4 col-sm-6 col-xs-12 info-panel" style="height:110px;">
        <div class="widget-shadow margin-bottom-15">
            <div class="widget-content bg-white padding-15" style="background-color: #0080c0">
                <button type="button" class="btn btn-floating btn-sm btn-danger">
                    <i class="icon fa-yen"></i>
                </button>
                <span class="margin-left-15 font-weight-400 font-size-16" style="color:#fff;">矿机钱包</span>
                <div class="content-text text-center margin-bottom-0">

                    <span class="font-size-26  font-weight-100" style="color:#fff;">&yen;${userinfo.money2}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-2 col-sm-6 col-xs-12 info-panel" style="height:110px;">
        <div class="widget-shadow margin-bottom-15">
            <div class="widget-content bg-white padding-15" style="background-color: #804000">
                <button type="button" class="btn btn-floating btn-sm btn-primary">
                    <i class="icon fa-yen"></i>
                </button>
                <span class="margin-left-15 font-weight-400 font-size-16" style="color:#fff;">投资钱包</span>
                <div class="content-text text-center margin-bottom-0">

                    <span class="font-size-26 font-weight-100" style="color:#fff;">&yen;${userinfo.money4}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-2 col-md-6 col-xs-12 info-panel" style="height:110px;">
        <div class="widget-shadow margin-bottom-15">
            <div class="widget-content bg-white padding-15" style="background-color: #408080">
                <button type="button" class="btn btn-floating btn-sm btn-warning">
                    <i class="icon wb-users"></i>
                </button>
                <span class="margin-left-15 font-weight-400 font-size-16" style="color:#fff;">团队持币</span>
                <div class="content-text text-center margin-bottom-0">

                    <span class="font-size-26  font-weight-100" style="color:#fff;">&yen;${allMoney}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-xs-12 info-panel" style="height:110px;">
        <div class="widget-shadow margin-bottom-15">
            <div class="widget-content bg-white padding-15" style="background-color: #408080">
                <button type="button" class="btn btn-floating btn-sm btn-warning">
                    <i class="icon fa-yen"></i>
                </button>
                <span class="margin-left-15 font-weight-400 font-size-16" style="color:#fff;">价格动态</span>
                <div class="content-text text-center margin-bottom-0">

                    <span class="font-size-26  font-weight-100" style="color:#fff;">&yen;${nowPrice}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-xs-12 info-panel" style="height:110px;">
        <div class="widget-shadow margin-bottom-15">
            <div class="widget-content bg-white padding-15" style="background-color: #408080">
                <button type="button" class="btn btn-floating btn-sm btn-warning">
                    <i class="icon fa-yen"></i>
                </button>
                <span class="margin-left-15 font-weight-400 font-size-16" style="color:#fff;">日产${fns:getOption("system_sys","site_name")}</span>
                <div class="content-text text-center margin-bottom-0">

                    <span class="font-size-26  font-weight-100" style="color:#fff;">&yen;${allBonus}</span>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="page animation-fade">
    <div class="page-content padding-15 blue-grey-500">
        <ul class="blocks blocks-100 blocks-xlg-4 blocks-md-3 blocks-sm-2" data-plugin="masonry">

            <c:forEach items="${articleList}" var="article">
                <li class="masonry-item">
                    <div class="widget widget-article widget-shadow">
                        <div class="widget-header cover">
                            <img class="cover-image"
                                 src="${article.image}"
                                 alt="...">
                        </div>
                        <div class="widget-body">
                            <h3 class="widget-title">${article.title}</h3>
                            <p class="widget-metas type-link">
                                <a href="javascript:;"><fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
                            </p>
                            <p>${article.description}</p>
                            <div class="widget-body-footer">
                                <%--<div class="widget-actions pull-right">--%>
                                    <%--<a href="javascript:;"><i class="icon wb-heart" aria-hidden="true"></i> <span>108</span>--%>
                                    <%--</a>--%>
                                <%--</div>--%>
                                <button type="button" class="btn btn-outline btn-primary" onclick="toDetail(${article.id})" >阅读全文</button>
                            </div>
                        </div>
                    </div>
                </li>
            </c:forEach>


        </ul>
    </div>
</div>
</div>

<script>
   var address =  "http://"+window.location.host+"/f/register?tj=${userinfo.userName}";
    $("#share").html(address);
</script>

<script src="${ctxStatic}/qysoftui/js/examples/tables/data-tables/plug-ins/range-filtering.js"></script>
<script src="${ctxStatic}/qysoftui/vendor/masonry/masonry.pkgd.min.js"></script>
