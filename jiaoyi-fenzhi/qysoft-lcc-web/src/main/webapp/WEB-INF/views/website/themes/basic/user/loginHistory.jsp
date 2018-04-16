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
        <div class="notice_info" style="">&nbsp;
        </div>
        <a class="close" href="javascript:void(0);" id="close_notice">
            <i class="icon_right_close_2"></i>
        </a>
    </div>    <div class="section doc_section">
    <div class="doc_left_bar">

        <dl class="doc_left_menu" id="doc_left_menu">
            <dt data-name="safe" class="cur"><i class="icon_nav_safe"></i>安全中心<i class="icon_gray_arrows"></i></dt>
            <dd class="open">
                <p>
                    <a href="${ctx}/center">安全设置</a><i class="icon_gray_arrows_2"></i></p>
                <p class="cur">
                    <a href="${ctx}/user/loginHistory">安全记录</a><i class="icon_gray_arrows_2"></i></p>
                <p >
                    <a href="${ctx}/user/account">基本信息</a><i class="icon_gray_arrows_2"></i></p>
                <p>
                    <a href="${ctx}/user/realnameAuth">身份信息</a><i class="icon_gray_arrows_2"></i></p>
            </dd>
        </dl>
    </div>
    <div class="doc_main_wrap">
        <div class="mod mod_main">
            <div class="mod_hd">
                <ul class="mod_tabs">
                    <li class="cur"><a href="http://www.filmlcc.com/security/loginHistory.jhtml#">最近登录历史</a></li>
                </ul>
            </div>

            <!--安全设置历史-->
            <br>
            <div class="alert alert_orange">
                <p></p>
            </div>
            <br>
            <div class="mod_bd">

                <!--安全设置历史-->
                <table class="table table_striped">

                    <thead>
                    <tr>
                        <th>登录时间</th>
                        <th>登录IP</th>
                        <th>参考地点</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>2018-03-16 20:22:23.000</td>
                        <td>
                            36.45.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2018-03-16 20:09:58.000</td>
                        <td>
                            36.45.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2018-01-23 18:23:55.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2018-01-22 22:55:55.000</td>
                        <td>
                            111.19.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2018-01-10 19:31:04.000</td>
                        <td>
                            36.45.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2018-01-06 19:15:33.000</td>
                        <td>
                            113.133.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-21 08:56:07.000</td>
                        <td>
                            1.80.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-15 21:11:06.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-15 21:10:47.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-15 21:10:40.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-12 15:37:38.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-11 15:06:34.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-11 15:05:53.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-11 15:03:47.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-11 14:58:16.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-11 12:02:32.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-11 11:39:49.000</td>
                        <td>
                            113.139.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-10 17:00:20.000</td>
                        <td>
                            1.86.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-10 16:59:24.000</td>
                        <td>
                            1.86.***.***</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2017-12-10 16:59:21.000</td>
                        <td>
                            1.86.***.***</td>
                        <td></td>
                    </tr>
                    </tbody>

                </table>
            </div>

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
            <a href="https://qianbao.qukuai.<!--  -->om/" target="_blank">快钱包</a>
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
                <li><a rel="nofollow" class="one" href="http://www.filmlcc.com/security/loginHistory.jhtml#" target="_blank"></a></li>
                <li><a rel="nofollow" class="two" href="http://www.filmlcc.com/security/loginHistory.jhtml#" target="_blank"></a></li>
                <li><a rel="nofollow" class="three" href="http://www.filmlcc.com/security/loginHistory.jhtml#" target="_blank"></a></li>
            </ul>
        </div>
        </div>

        <div class="foot_lang" style="display:none">
            &nbsp;
        </div>
    </div>


</div><div><div class="sweet-overlay" tabindex="-1"></div><div class="sweet-alert" tabindex="-1"><div class="icon error"><span class="x-mark"><span class="line left"></span><span class="line right"></span></span></div><div class="icon warning"> <span class="body"></span> <span class="dot"></span> </div> <div class="icon info"></div> <div class="icon success"> <span class="line tip"></span> <span class="line long"></span> <div class="placeholder"></div> <div class="fix"></div> </div> <div class="icon custom"></div> <h2>Title</h2><p>Text</p><button class="cancel" tabindex="2">Cancel</button><button class="confirm" tabindex="1">OK</button></div></div></body></html>