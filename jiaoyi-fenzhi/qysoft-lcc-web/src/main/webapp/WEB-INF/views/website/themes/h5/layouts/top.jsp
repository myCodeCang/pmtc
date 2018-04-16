<!-- 头部开始 -->
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="top_bar" id="doc_top_bar">
    <div class="section clear_fix">
        <div class="left">
            <div class="doc_ticker" id="doc_ticker">
                <marquee direction="left" behavior="scroll" loop="3" scrollamount="1" scrolldelay="10" align="top" width="500">欢迎进入 ${fns:getOption("system_sys","site_name")}国际数字货币交易中心 </marquee>
            </div>
        </div>
        <div class="right">
            <c:if test="${userinfo != null}">
                <span class="user" id="user_slide">
                    <i class="top_icons"></i>
                      您好，UID:(${userinfo.userName})${userinfo.trueName}</span>
                <a class="top_logout" href="${ctx}/center">设置</a>
                <a class="top_logout" href="${ctx}/loginOut"> 退出</a>
                <s></s>
            </c:if>
            <span class="menu" id="menu_slide">
            </span>
        </div>
        <div class="menu_slider" id="menu_slide_box">
            <i class="top_arrow"></i>

        </div>
    </div>
</div>

<div class="header" id="doc_head">
    <div class="section">
        <h1 class="head_logo" style="width:200px;"><a href="/"><img src="${fns:getOption('system_sys','site_logo')}" style="width:200px;" alt="交易平台"></a></h1>

        <div class="head_nav">
            <ul>
                <li>
                    <a href="${ctx}/indexl">首页</a>
                </li>
                <li>
                    <a href="${ctx}/trans/pricedaylog?siteBar=12" class="">交易中心</a>
                </li>
                <li>
                    <a href="${ctx}/user/zichan" class="">资产</a>
                </li>
                <%--<li>--%>
                <%--<a href="/column/market_info.jhtml">新闻</a>--%>
                <%--</li>--%>
            </ul>
        </div>
        <c:choose>
            <c:when test="${userinfo != null}">
                <a href="${ctx}/user/zichan">
                <div class="head_balance" id="head_balance" data-currency="setting" data-allow_cny="1" data-allow_usd="" data-refresh="" data-widget="#head_balance">
                    总币量
                    <b></b><b class="convert_net_all_cny" data-flaunt="0.00" data-flaunted="1" style="font-weight: 400;">${userinfo.money}</b>
                    <span class="eye" id="flaunt" data-visible="visible"><i class="top_icons"></i></span>
                </div>
                </a>
            </c:when>
            <c:otherwise>
                <div class="head_nosign">
                    <a href="${ctx}/login" class="head_a">登录</a>
                    <a href="${ctx}/register" class="head_btn">注册</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>