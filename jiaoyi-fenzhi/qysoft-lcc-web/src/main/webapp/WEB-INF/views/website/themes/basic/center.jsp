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


<body>
<!-- 头部开始 -->
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/top.jsp" %>
<!-- 头部结束 -->

<div id="doc_body">
    <div class="section head_notice" id="head_notice">
        <div class="notice_info" style="display: none;">&nbsp;
        </div>
        <a class="close" href="javascript:void(0);" id="close_notice">
            <i class="icon_right_close_2"></i>
        </a>
    </div>    <div class="section doc_section">
    <div class="doc_left_bar">

        <dl class="doc_left_menu" id="doc_left_menu">
            <dt data-name="safe" class="cur"><i class="icon_nav_safe"></i>安全中心<i class="icon_gray_arrows"></i></dt>
            <dd class="open">
                <p class="cur">
                    <a href="javascript:void(0)">安全设置</a><i class="icon_gray_arrows_2"></i></p>
                <%--<p>--%>
                    <%--<a href="${ctx}/user/loginHistory">安全记录</a><i class="icon_gray_arrows_2"></i></p>--%>
                <p >
                    <a href="${ctx}/user/account">基本信息</a><i class="icon_gray_arrows_2"></i></p>
                <p>
                    <a href="${ctx}/user/realnameAuth">身份信息</a><i class="icon_gray_arrows_2"></i></p>
            </dd>
        </dl>
    </div>
    <div class="doc_main_wrap">
        <div class="s-set">
            <dl>
                <dd>
                    <p>
                        ${userinfo.trueName}${userinfo.mobile}，您的账号安全级别设置
                    </p>
                    您可以:
                    <a href="${ctx}/user/resetAssetPwd">设置资金密码</a>
                </dd>
            </dl>
        </div>

        <div class="s-policy s-setting">
            <dl>
                <%--<dt>您已设置 <b>3</b> 个保护项，还有 <b>1</b> 保护项可设置</dt>--%>
                <dd class="${not empty userinfo.userEmail?'':'no'}">
                    <i class="icon-pilicy-email"></i>
                    <div class="security-col">
                        <div class="validate"><b>绑定邮箱</b></div>

                        <div class="pass">
                            <c:if test="${empty userinfo.userEmail}">
                                <b class="no">未绑定</b>
                                暂未绑定邮箱
                                <a href="${ctx}/user/bindEmail">绑定</a>
                            </c:if>
                            <c:if test="${not empty userinfo.userEmail}">
                                <b>已绑定</b>您已绑定邮箱:${userinfo.userEmail}
                            </c:if>

                        </div>
                    </div>
                </dd>
                <dd>
                    <i class="icon-pilicy-mobile"></i>
                    <div class="security-col">
                        <div class="validate"><b>绑定手机</b></div>
                        <div class="pass">
                            <b>已绑定</b>您已绑定手机${userinfo.mobile}</div>
                    </div>
                </dd>
                <dd class="${userinfo.usercenterAddress eq '0' ?'no':''}" style="">
                    <i class="icon-pilicy-google"></i>
                    <div class="security-col">
                        <div class="validate"><b>绑定会员端钱包地址</b></div>
                        <div class="pass">

                            <c:if test="${userinfo.usercenterAddress eq '0'}">
                                <b class="no">未绑定</b>
                                请您绑定您的会员端钱包地址
                                <a href="${ctx}/user/bingUserBurseAdd">绑定</a>
                            </c:if>
                            <c:if test="${userinfo.usercenterAddress ne  '0'}">
                                <b>已绑定</b>您的会员钱包地址为:${userinfo.usercenterAddress}
                            </c:if>

                        </div>
                    </div>
                </dd>
                <dd>
                    <i class="icon-pilicy-login"></i>
                    <div class="security-col">
                        <div class="validate"><b>登录密码</b></div>
                        <div class="pass">
                            <b>已设置</b>
                            登录平台时使用。
                            <a href="${ctx}/user/changeLoginPwd">重置</a>
                        </div>
                    </div>
                </dd>
                <dd>
                    <i class="icon-pilicy-money"></i>
                    <div class="security-col">
                        <div class="validate"><b>资金密码</b></div>
                        <div class="pass">
                            <b>已设置</b>
                            账户资金变动时，需先验证该资金密码。(默认和登录密码一样)
                            <a href="${ctx}/user/resetAssetPwd">设置</a>
                        </div>
                    </div>
                </dd>
            </dl>
        </div>
    </div>
</div>
</div>

<!-- -->
<div id="doc_foot" class="doc_foot">

    <div class="foot_partner">
        <div class="section" style="display:none">
            <b>友情链接</b>
            <a href="https://www.huobi.com/" target="_blank">比特币</a>
            <a href="https://www.huobi.com/" target="_blank">莱特币</a>
            <a href="https://www.huobi.com/" target="_blank">以太坊</a>
            <a href="http://www.yuncaijing.com/" target="_blank">今日股市行情</a>
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
                <li><a rel="nofollow" class="one" href="http://www.filmlcc.com/security/center.jhtml#" target="_blank"></a></li>
                <li><a rel="nofollow" class="two" href="http://www.filmlcc.com/security/center.jhtml#" target="_blank"></a></li>
                <li><a rel="nofollow" class="three" href="http://www.filmlcc.com/security/center.jhtml#" target="_blank"></a></li>
            </ul>
        </div>
        </div>

        <div class="foot_lang" style="display:none">
            &nbsp;
        </div>
    </div>


</div><div><div class="sweet-overlay" tabindex="-1"></div><div class="sweet-alert" tabindex="-1"><div class="icon error"><span class="x-mark"><span class="line left"></span><span class="line right"></span></span></div><div class="icon warning"> <span class="body"></span> <span class="dot"></span> </div> <div class="icon info"></div> <div class="icon success"> <span class="line tip"></span> <span class="line long"></span> <div class="placeholder"></div> <div class="fix"></div> </div> <div class="icon custom"></div> <h2>Title</h2><p>Text</p><button class="cancel" tabindex="2">Cancel</button><button class="confirm" tabindex="1">OK</button></div></div></body></html>