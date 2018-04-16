<%@ page contentType="text/html;charset=UTF-8" %>
<div class="doc_left_bar">

    <dl class="doc_left_menu" id="doc_left_menu">

        <dt data-name="LCC" class="${10 < siteBar && siteBar < 20?'cur':''}"> <i class="icon_nav_btc"></i>我要交易<i class="icon_gray_arrows"></i></dt>
        <dd class="open">
            <p class="${siteBar == 11?'cur':''}">
                <a href="${ctx}/trans/buysell?siteBar=11">买入/卖出</a><i class="icon_gray_arrows_2"></i></p>
            <p class="${siteBar == 12?'cur':''}" >
                <a href="${ctx}/trans/pricedaylog?siteBar=12">行情图表</a><i class="icon_gray_arrows_2"></i></p>
        </dd>
        <dt data-name="LCC" class="${20 < siteBar && siteBar < 30?'cur':''}" > <i class="icon_nav_btc"></i>我的委托<i class="icon_gray_arrows"></i></dt>
        <dd class="open">
            <p class="${siteBar == 21?'cur':''}" >
                <a href="${ctx}/trans/buyentrust?siteBar=21">买入委托记录</a><i class="icon_gray_arrows_2"></i></p>
            <p class="${siteBar == 22?'cur':''}" >
                <a href="${ctx}/trans/sellentrust?siteBar=22">卖出委托记录</a><i class="icon_gray_arrows_2"></i></p>
            <p class="${siteBar == 23?'cur':''}" >
                <a href="${ctx}/trans/entrusthistory?siteBar=23">历史委托记录</a><i class="icon_gray_arrows_2"></i></p>
        </dd>
        <dt data-name="LCC" class="${30 < siteBar && siteBar < 40?'cur':''}" > <i class="icon_nav_btc"></i>我的成交<i class="icon_gray_arrows"></i></dt>
        <dd class="open">
            <p class="${siteBar == 31?'cur':''}" >
                <a href="${ctx}/trans/buys?siteBar=31">交易确认</a><i class="icon_gray_arrows_2"></i></p>
            <%--<p class="${siteBar == 32?'cur':''}" >--%>
                <%--<a href="${ctx}/trans/sells?siteBar=32">卖方交易确认</a><i class="icon_gray_arrows_2"></i></p>--%>
            <p class="${siteBar == 33?'cur':''}" >
                <a href="${ctx}/trans/allList?siteBar=33">成交记录</a><i class="icon_gray_arrows_2"></i></p>
        </dd>
    </dl>

</div>