<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>${fns:getOption("system_trans","trans_name")}交易平台| ${fns:getOption("system_trans","trans_name")}</title>
    <%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>

    <link href="${ctxStatic}/themes/basic/css/page_center.css" rel="stylesheet" media="screen">

</head>
<!-- 头部开始 -->
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/top.jsp" %>
<!-- 头部结束 -->

<div id="doc_body">
    <div class="section head_notice" id="head_notice">
        <div class="notice_info" style="">

        </div>
        <a class="close" href="#" id="close_notice">
            <i class="icon_right_close_2"></i>
        </a>
    </div>    <div class="section doc_section">


    <div class="doc_left_bar">

        <dl class="doc_left_menu" id="doc_left_menu">

            <dt data-name="finance" class=" cur "><i class="icon_nav_finance"></i>资产信息<i class="icon_gray_arrows"></i></dt>
            <dd class=" open ">
                <p class=" cur "><a href="${ctx}/user/myBankCard">我的银行卡</a><i class="icon_gray_arrows_2"></i></p>
                <p><a href="${ctx}/user/myBurse">我的钱包</a><i class="icon_gray_arrows_2"></i></p>
            </dd>

            <dt data-name="deposit" id="allow_currency" data-allow_cny="1" data-allow_usd="" class=" cur  "><i class="icon_nav_deposit"></i>${fns:getOption("system_trans","trans_name")}转内网 <i class="icon_gray_arrows"></i></dt>
            <dd class=" open ">
                <p class=""><a href="${ctx}/user/myWithdraw">${fns:getOption("system_trans","trans_name")}转内网</a><i class="icon_gray_arrows_2"></i></p>
                <p class=""><a href="${ctx}/user/myWithdrawLog">转内网记录</a><i class="icon_gray_arrows_2"></i></p>
            </dd>
        </dl>
    </div>

    <div class="doc_main_wrap">
        <div class="mod mod_main">
            <div class="mod_hd">
                <h3 class="mod_title">我的银行卡信息</h3>
            </div>
            <div class="mod_bd">
                <!--资产账户详细信息-->
                <div class="c_balance">
                    <ul class="item">
                        <li class="c_1">
                            <span>${userbank.bankName}</span><br>
                            <strong class="cny_cny_available">${userbank.bankUserName}</strong>

                        </li>
                        <li class="c_2">
                            <span class="cny_cny_frozen">${userbank.bankUserNum}</span>

                        </li>
                        <li class="c_4">
                            <a href="${ctx}/user/myBankCardEdit?bankId=${userbank.id}" class="btn btn_orange size_s">修改</a>
                        </li>
                    </ul>
                    <div class="c_balance_total">
                        <div class="c_1">
                            <!--
                                <span>
                                    <i class="label">资产折合：</i>
                                    <b class="cny_total" data-weight="700" data-flaunt="0.00">0.00</b><b>CNY</b>
                                </span>

                                <span>
                                    <i class="label">净资产折合：</i>
                                    <b class="cny_net_asset" data-weight="700" data-flaunt="0.00">0.00</b><b>CNY</b>
                                </span>
                                -->
                        </div>
                        <div class="c_2"></div>
                    </div>
                </div>

            </div>
        </div>

    </div>


</div>
</div>

<!-- -->
<div id="doc_foot" class="doc_foot">

    <div class="foot_partner">
        <div class="section"  style="display:none">
            <b>友情链接</b>
            <a href="https://www.huobi.com/" target="_blank">比特币</a>
            <a href="https://www.huobi.com/" target="_blank">莱特币</a>
            <a href="https://www.huobi.com/" target="_blank">以太坊</a>
            <a href="http://www.yuncaijing.com" target="_blank">今日股市行情</a>
            <a href="https://qianbao.qukuai.com/" target="_blank">快钱包</a>
            <a href="http://www.hao123.com/bitcoin" target="_blank">hao123比特币</a>

        </div>
    </div>
    <div class="section foot_copyright">
        <div class="float_left">
            <ul class="foot_record clear_fix">
                <li> </li>
                <li>&nbsp;</li>
            </ul>        <div class="foot_end">
            <ul>
                <li><a rel="nofollow" class="one" href="#" target="_blank"></a></li>
                <li><a rel="nofollow" class="two" href="#" target="_blank"></a></li>
                <li><a rel="nofollow" class="three" href="#" target="_blank"></a></li>
            </ul>
        </div>
        </div>

        <div class="foot_lang"  style="display:none">
            &nbsp;
        </div>
    </div>
</body>
</html>
