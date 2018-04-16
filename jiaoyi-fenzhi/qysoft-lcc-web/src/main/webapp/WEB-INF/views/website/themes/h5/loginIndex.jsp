<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/h5/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title> ${fns:getOption("system_sys","site_name")}交易平台|  ${fns:getOption("system_sys","site_name")}</title>
    <%@ include file="/WEB-INF/views/website/themes/h5/layouts/head.jsp" %>



    <!--<script type="text/javascript" src="${ctxStatic}/themes/h5/script/index.js"></script>-->
    <style>
        .weui-flex__item {
            text-align: center;
            height: 2.3em;
            line-height: 2.3em;
        }

        #tmp_trades {
            width: 100%;
        }

        #tmp_trades li {
            display: block;
            vertical-align: top;
            height: 35px;
            line-height: 30px;
            overflow: hidden
        }

        #tmp_trades li span {
            display: inline-block;
            float: left
        }

        #tmp_trades li .tx_1 {
            text-indent: 15px;
            width: 32%
        }

        #tmp_trades li .tx_2 {
            font-size: 13px;
            width: 32%
        }

        #tmp_trades li .tx_3 {
            font-size: 13px;
            min-width: 28%;
            text-align: right
        }

        #tmp_trades li.mid {
            padding: 0 20px;
            display: block;
            height: 10px
        }

        .font_buy {
            color: #ee3523;
            font-weight: 500;
        }

        .font_sell {
            color: #090;
            font-weight: 500;
        }

        hr {
            border: none;
            border-bottom-width: medium;
            border-bottom-style: none;
            border-bottom-color: currentcolor;
            border-bottom: 1px solid #e6e6e6;
            margin: 0;
        }

        #slide {
            position: absolute;
            height: 300px;
            width: 260px;
            color: #FA8E93;
            overflow: hidden;
            border: 1px solid #ccc
        }

        #slide p {
            height: 34px;
            line-height: 34px;
            overflow: hidden
        }

        #slide span {
            float: right
        }
    </style>
</head>

<body style="padding-bottom: 3rem; padding-top: 2.4rem;">

<!-- 头部 -->
<header class="header-nav">
    <div class="weui-header" style="background-color: #fff;">
        <img src="${ctxStatic}/themes/h5/image/logo.lcc.png" style="width:200px;heigt:50px;" alt="LCC交易平台">
        <div class="header-buttons">
        </div>
    </div>
</header>
<!-- 主体 -->
<section class="container">
    <!-- 图片切换 -->
    <div class="swiper-container homeBanner swiper-container-horizontal">
        <div class="swiper-wrapper" style="height: fit-content;">
            <div class="swiper-slide swiper-slide-duplicate swiper-slide-next" data-swiper-slide-index="0">
                <a href="#"><img src="${ctxStatic}/themes/h5/image/br1.jpg"></a>
            </div>
            <div class="swiper-slide swiper-slide-prev" data-swiper-slide-index="1" >
                <a href="#"><img src="${ctxStatic}/themes/h5/image/br2.jpg"></a>
            </div>
            <div class="swiper-slide swiper-slide-active" data-swiper-slide-index="2">
                <a href="#"><img src="${ctxStatic}/themes/h5/image/br3.jpg"></a>
            </div>
        </div>
        <div class="swiper-pagination swiper-pagination-bullets"><span class="swiper-pagination-bullet"></span><span class="swiper-pagination-bullet"></span><span class="swiper-pagination-bullet swiper-pagination-bullet-active"></span></div>
    </div>
    <!--  -->
    <div id="noticeSysBar" style="padding:5px;border: 1px solid #FFDA95;background-color: #FFFBF2; width: 100%;height:25px;">
        <div id="noticeSysDiv" style="height:25px;overflow:hidden;">

        </div>

    </div>
    <!--  -->
    <div class="weui-panel">
        <div class="page__hd" style="margin-top:1.5rem;">
            <h1 class="page__title"><i class="icon-x106"></i> LCC行情</h1>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><b>当前价</b></div>
            <div class="weui-flex__item"><b>周成交量</b></div>
            <div class="weui-flex__item"><b>月成交量</b></div>
            <div class="weui-flex__item"><b>24H成交量</b></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-currPrice">￥59.46</i></div>
            <div class="weui-flex__item"><i id="x-lowPrice">280500</i></div>
            <div class="weui-flex__item"><i id="x-topPrice">1660100</i></div>
            <div class="weui-flex__item"><i id="x-24hPrice">0</i></div><i id="x-24hPrice">
        </i></div><i id="x-24hPrice">
        <div class="page__hd" style="margin-top:1.5rem;">
            <h1 class="page__title"><i class="icon-x24"></i> LCC买卖盘</h1>
        </div>
        <div class="weui-flex" style="border-bottom: 1px solid #C4C7CA;">
            <div class="weui-flex__item"><i id="x-AType_0">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-APrice_0">--</i></div>
            <div class="weui-flex__item"><i id="x-AQty_0">--</i></div>
        </div>

        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_0">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_0" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_0">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_1">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_1" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_1">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_2">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_2" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_2">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_3">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_3" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_3">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_4">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_4" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_4">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_5">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_5" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_5">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_6">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_6" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_6">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_7">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_7" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_7">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_8">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_8" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_8">--</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i class=" " id="x-Type_9">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-Price_9" class=" ">--</i></div>
            <div class="weui-flex__item"><i class=" " id="x-Qty_9">--</i></div>
        </div>
    </i>
    </div><i id="x-24hPrice">
    <!--  -->
    <div class="weui-panel">
        <div class="page__hd" style="margin-top:1.5rem;">
            <h1 class="page__title"><i class="icon-x106"></i> 实时成交记录</h1>
        </div>

        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_0">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_0">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_0">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_1">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_1">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_1">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_2">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_2">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_2">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_3">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_3">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_3">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_4">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_4">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_4">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_5">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_5">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_5">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_6">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_6">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_6">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_7">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_7">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_7">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_8">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_8">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_8">0</i></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><i id="x-TTime_9">--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i></div>
            <div class="weui-flex__item"><i id="x-TPrice_9">0</i></div>
            <div class="weui-flex__item"><i id="x-TQty_9">0</i></div>
        </div>
    </div>

    <!--K线图 -->
    <script src="${ctxStatic}/themes/h5/script/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/themes/h5/css/style-m.css">
    <script type="text/javascript" src="${ctxStatic}/themes/h5/script/stock-m.js"></script>
    <div class="page__hd" style="margin-top:1.5rem;">
        <h1 class="page__title"><i class="icon-x54"></i> LCC行情走势</h1>
    </div>
    <div class="weui-panel weui-panel_access" style="height:380px;">
        <div id="stockLine" style="zoom: 0.646875;">
            <div id="loading" style="display: none;"></div>
            <div id="nav">
                <ul>
                    <li name="dayK" id="nav-dayK" class="current">日K</li>
                    <li name="weekK" id="nav-weekK">周K</li>
                    <li name="monthK" id="nav-monthK">月K</li>
                </ul>
            </div>
            <div class="item" id="minute" style="display: none;">
                <div class="touch" id="touch-minute">
                    <div class="K-line">
                        <div id="dot" style="left: -1000px;"></div>
                        <ul class="price">
                            <li class="green" style="top: 33px;">57.86</li>
                            <li class="green" style="top: 99px;">55.40</li>
                            <li class="green" style="top: 165px;">52.94</li>
                            <li>&nbsp;</li>
                            <li>&nbsp;</li>
                        </ul>
                        <ul class="range">
                            <li class="green" style="top: 33px;">-1.14%</li>
                            <li class="green" style="top: 99px;">-5.34%</li>
                            <li class="green" style="top: 165px;">-9.54%</li>
                            <li></li>
                            <li></li>
                        </ul><canvas id="minute-k" class="k" width="200" height="398"></canvas></div>
                    <ul class="KL-time">
                        <li class="one">0:00</li>
                        <li class="two">12:00</li>
                        <li class="three">23:59</li>
                    </ul>
                    <div class="L-line">
                        <ul class="volume">
                            <li class="max-volume">4350</li>
                        </ul><canvas id="minute-l" class="l" width="200" height="120"></canvas></div>
                </div>
            </div>
            <div class="item item-k" id="dayK" style="display: block;">
                <div class="touch" id="touch-dayK">
                    <div class="K-line">
                        <ul class="price">
                            <li style="top: 24px;">55.58</li>
                            <li style="top: 74px;">40.39</li>
                            <li style="top: 124px;">25.20</li>
                            <li style="top: 174px;">10.01</li>
                            <li></li>
                        </ul> <canvas id="dayK-k" class="k" width="1280" height="398"></canvas></div>
                    <ul class="KL-time">
                        <li style="display: none;"></li>
                        <li style="display: block; left: 214.9px;">2018-01-07</li>
                        <li style="display: block; left: 426.1px;">2018-02-09</li>
                        <li style="display: none;"></li>
                        <li style="display: none;"></li>
                    </ul>
                    <div class="L-line">
                        <ul class="volume">
                            <li class="max-volume">339100</li>
                            <li></li>
                            <li></li>
                        </ul><canvas id="dayK-l" class="l" width="1280" height="120"></canvas></div>
                    <ul class="avg">
                        <li style="color:#dd2200;">MA5：52.28</li>
                        <li style="color:#822e32;">MA10：45.70</li>
                        <li style="color:#f6c750;">MA20：36.40</li>
                        <li style="color:#155a9c;">MA30：32.14</li>
                    </ul>
                    <ul class="MACD">
                        <li style=" color:#dd2200">DIF:<span></span></li>
                        <li style="color:#155a9c">DEA:<span></span></li>
                        <li style=" color:#155a9c">MACD:<span></span></li>
                    </ul>
                    <ul class="KDJ">
                        <li style=" color:#f0f888">K:<span></span></li>
                        <li style="color:#54fcfc">D:<span></span></li>
                        <li style=" color:#ff80ff">J:<span></span></li>
                    </ul>
                    <div class="x"><span class="current-price"></span></div>
                    <div class="y"></div>
                </div>
                <div class="zoom"><span class="up"></span><span class="down"></span></div>
                <ul id="ktool">
                    <li class="current volume" name="normal">成交量</li>
                    <li class="volume" name="MACD">MACD</li>
                    <li class="volume" name="KDJ">KDJ</li>
                </ul>
            </div>
        </div>
    </div>
    <!--  客户电话-->
    <div style="height:3rem;">

    </div>

    <script type="text/javascript">
        window.COIN = 'LCC';
        window.pdtCode = 'LCC';
        var data = {};
        data.stockID = "LCC";
        data.stockType = "sz";
        data.time = parseInt("");

        data.day = "2017-12-03";
        data.isHqReload = eval("true");

        //菜单
        data.menu = [];

        data.menu.push({
            name: "dayK",
            title: "日K"
        });
        data.menu.push({
            name: "weekK",
            title: "周K"
        });
        data.menu.push({
            name: "monthK",
            title: "月K"
        });
        data.default = "dayK";

        //				$(document).ready(function() {
        //					stock.load(data);
        //					stock.menu.load("minute");
        //				});
    </script>
    <!-- -->
    <div class="weui-footer">
        <p class="weui-footer__text">Copyright © 2008-2016 uul</p>
    </div>
</i>
</section><i id="x-24hPrice">
    <!-- 底部 -->
    <footer class="footer-nav">
        <ul>
            <li class="on"><a href="index.jhtml"> <i class="icon-x27"></i> <b>首页</b> </a>
            </li>
            <li>
                <a href="trade.jhtml"> <i class="icon-x2"></i><b>交易</b> </a>
            </li>
            <li>
                <a href="finance.jhtml"> <i class="icon-x43"></i> <b>资产</b> </a>
            </li>
            <li>
                <a href="me.jhtml"> <i class="icon-x85"></i> <b>我</b> </a>
            </li>
        </ul>
    </footer>
    <script type="text/javascript" src="${ctxStatic}/themes/h5/script/swiper.js" charset="utf-8"></script>

    <script type="text/javascript">
        var curTime = new Date();
        //2把字符串格式转换为日期类
        var startTime = new Date(Date.parse('2018-02-10 21:00:00'.replace(/-/g, "/")));
        var endTime = new Date(Date.parse('2018-02-25'));
        //3进行比较
        //if(curTime>=startTime && curTime<=endTime){

    </script>

    <div id="full" class="weui-popup__container">
        <div class="weui-popup__overlay"></div>
        <div class="weui-popup__modal">
            <div class="toolbar">
                <div class="toolbar-inner">
                    <a href="javascript:;" class="picker-button close-popup">关闭</a>
                    <h1 class="title">详情</h1>
                </div>
            </div>
            <div class="modal-content annContent">
                <h1>大标题</h1>

            </div>
            <a href="javascript:;" class="weui-btn weui-btn_primary close-popup">关闭</a>
        </div>
    </div>
    <script type="text/javascript">
        <!--
        $(function() {
            var fot = $('body').has('footer').length;
            var hed = $('body').has('header').length;
            //console.log(navl);
            if(fot == 1) {
                $('body').css({
                    'padding-bottom': '3rem'
                });
            }
            if(hed == 1) {
                $('body').css({
                    'padding-top': '2.4rem'
                });
            }
            FastClick.attach(document.body);
        });

        var bodyScroll = function(event) {
            event.preventDefault();
        }
        //-->
    </script>

</i>
<div class="layui-layer-move"></div>
</body>

</html>