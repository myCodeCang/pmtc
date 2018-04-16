<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title> ${fns:getOption("system_sys","site_name")}交易平台|  ${fns:getOption("system_sys","site_name")}</title>
    <%@ include file="/WEB-INF/views/website/themes/basic/layouts/head.jsp" %>

    <link href="${ctxStatic}/themes/basic/css/index.code.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="${ctxStatic}/themes/basic/script/login.js?v=1.4"></script>
    <script type="text/javascript" src="${ctxStatic}/themes/basic/script/index.js?v=1.2"></script>
    <script type="text/javascript" src="${ctxStatic}/themes/basic/script/echarts.min.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/top.jsp" %>

<!-- 头部结束 -->

<div id="doc_body">
    <div class="blank_400"></div>
    <div class="ab_box">
        <div class="index_banner_box" id="index_banner_box">
            <div class="banner_big_box" id="banner_big_box">
                <p style="opacity: 0;"></p>
                <p style="opacity: 0;"></p>
                <p style="opacity: 1;"></p>

            </div>
            <div class="index_t">
                <div class="banner_box" id="banner_box">
                    <%--<c:forEach items="${imgTab}" var="userPhoto">--%>
                        <b class="slideUp" style="background-image: url(/userfiles/photo/banner1.jpg);background-repeat: no-repeat;background-size:100%; left: 0px; opacity: 1; z-index: 5;"><a href="javascript:void(0);"></a></b>

                        <b class="slideUp" style="background-image: url(/userfiles/photo/banner2.jpg);background-repeat: no-repeat;background-size:100%; left: 0px; opacity: 1; z-index: 5;"><a href="javascript:void(0);"></a></b>

                        <b class="slideUp" style="background-image: url(/userfiles/photo/banner3.jpg);background-repeat: no-repeat;background-size:100%; left: 0px; opacity: 1; z-index: 5;"><a href="javascript:void(0);"></a></b>
                    <%--</c:forEach>--%>
                </div>
                <div class="section floor_top">
                    <!-- 轮播的页码  开始 -->
                    <ul id="page_list">

                    </ul>
                    <div class="index_login_box" style="top:50px;">
                        <div class="login_box">
                            <div class="opacity_bg"></div>

                            <!--登录成功-->
                            <c:choose>
                                <c:when test="${isLogin}">
                                    <div class="tab tab01">
                                        <h3 class="title">
                                            <a href="${ctx}/center" class="font_20">
                                                UID:(${userinfo.userName})
                                            </a>
                                        </h3>
                                        <p class="m_t_10"><a href="javascript:void(0)" class="btn btn_orange btn_url">总量:<b>${userinfo.money}</b></a></p>
                                        <p class="button_box">
                                            <a href="${ctx}/trans/buysell?siteBar=11" class="btn_white">交易</a>

                                            <a href="${ctx}/user/myWithdraw" class="btn_white">转内网</a>

                                        </p>
                                        <hr class="login_hr">
                                        <p class="hr_tis"><i class="icon_tis"></i> <a href="#">&nbsp;</a></p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <!--登录页面-->
                                    <div class="tab tab01">
                                    <form action="${ctx}/login" class="form_login bind_validator" autocomplete="off" novalidate="novalidate">
                                        <input type="hidden" id="userType" name="userType" value="website">
                                        <h4 class="title">登录</h4>
                                        <p>
                                            <select name="country" id="country" class="input_text size_full new_country_code">
                                                <option value="86">中国大陆(+86)</option>
                                                <%--<option value="0044">英国(+0044)</option>--%>
                                                <option value="001">美国(+001)</option>
                                                <%--<option value="00852">香港(+00852)</option>--%>
                                                <%--<option value="886">台灣(+886)</option>--%>
                                                <%--<option value="95">缅甸(+95)</option>--%>
                                                <%--<option value="65">新加坡(+65)</option>--%>
                                            </select>
                                        </p>
                                        <p style="display:none">
                                            <input type="text" placeholder="请输入手机号码" id="mobileNo" name="mobileNo" class="input_login" autocomplete="off">
                                        </p>
                                        <p>
                                            <input type="text" id="userCode" name="username" class="input_login" value="" placeholder="请输入手机号码/会员ID" autocomplete="off">
                                        </p>
                                        <div class="relative">
                                            <div class="mail_complete_list absolute"></div>
                                        </div>
                                        <p>
                                            <input type="password" name="password" class="input_login" data-type="*" data-msg-null="请输入密码" placeholder="请输入密码">
                                        </p>
                                        <p class="relative" id="vcode_p">
                                            <input type="text" class="input_login" data-type="*" id="validateCode" name="validateCode" data-msg-null="请输入验证码" placeholder="验证码">
                                            <input type="hidden" name="captcha_key" id="captcha_key" value="">
                                            <span class="absolute"><a  class="captchaImg">

                                                <img <sys:validateWebCode name="validateCode" />
                                                       </a></span>
                                        </p>
                                        <div class="form_tip"></div>
                                        <p><button class="btn btn_orange sign_btn" type="submit">登录</button></p>
                                        <p class="help">
                                            <a href="${ctx}/homeForgetPass">忘记密码？</a>
                                        </p>
                                        <input type="hidden" name="step" value="">
                                    </form>
                                    <hr class="login_hr">
                                    <p class="hr_tis"><i class="icon_tis"></i>
                                        <a href="#">&nbsp;</a>
                                    </p>
                                </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="hide">
        <input type="hidden" name="coin_type" value="cny_btc">
    </div>

    <div class="index_box_1 market_part">
        <div class="section head_notice" id="head_notice">
            <div class="notice_info" style="display: none;">
                <a href="javascript:void(0);" target="_blank">
                    <i class="icon_notice_horn"></i> 公告：<span></span>
                </a>
            </div>
            <a class="close" href="#" id="close_notice">
                <i class="icon_right_close_2"></i>
            </a>
        </div>
        <div class="section clear_fix p_t_30">
            <div class="market_left">
                <h2>${fns:getOption("system_trans","trans_name")}行情</h2>
                <ul class="market_type" id="market_type">
                    <li data-type="LCC">${fns:getOption("system_trans","trans_name")}</li>
                </ul>
            </div>
            <div class="market_right">
                <div class="market_sub">
                    <div class="sub_top market_top">
                        <ul class="top_" id="tmp_overview">
                            <li class="price_box price_rose font_arr"><span class="price">￥${nowMoney}</span><i class="icon_arrows_big"></i></li>
                            <li class="price_box">周成交量 <span class="rose">${weekAmount}</span></li>
                            <li class="price_box">月成交量 <span class="rose">${monthAmount}</span></li>
                            <li class="price_box">总成交量 <span class="rose">￥${allAmount}</span></li>
                            <li class="total_box">
                                <div class="total_table">
                                    24H成交量 <span class="total">￥${dayAmount}</span>
                                </div>
                            </li>

                        </ul>
                    </div>
                    <div class="sub_bottom clear_fix" id="market">
                        <div class="sub_flash">
                            <%--//<iframe src="LCC_KIndex.html" name="HB_Home_iframe" id="HB_Home_iframe" height="100%" width="100%" frameborder="0" cellpadding="0" cellspacing="0" scrolling="no"></iframe>--%>
                                <div  id="main" style="width: 100%;height: 850px;">

                                </div>
                        </div>
                        <div class="sub_tx">
                            <div class="tx_title"><b>买卖盘</b>
                                <div class="index_depth">

                                </div>
                            </div>
                            <div id="market_all">
                                <%--<ul class="m_t_o" id="tmp_title" style="border-bottom: 1px solid #C4C7CA;">--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                <%--</ul>--%>
                                <ul class="m_t_o" id="tmp_depth">
                                    <li><span class="tx_1  ">买</span><span class="tx_2  ">${nowMoney}</span><span class="tx_3  ">${buyNowNum}</span></li>
                                    <li><span class="tx_1  ">卖</span><span class="tx_2  ">${nowMoney}</span><span class="tx_3  ">${sellNowNum}</span></li>
                                    <li><span class="tx_1  "></span><span class="tx_2  "></span><span class="tx_3  "></span></li>
                                    <li><span class="tx_1  "></span><span class="tx_2  "></span><span class="tx_3  "></span></li>
                                    <%--<li><span class="tx_1  "></span><span class="tx_2  "></span><span class="tx_3  "></span></li>--%>
                                    <%--<li><span class="tx_1  "></span><span class="tx_2  "></span><span class="tx_3  "></span></li>--%>
                                    <%--<li><span class="tx_1  "></span><span class="tx_2  "></span><span class="tx_3  "></span></li>--%>
                                    <%--<li><span class="tx_1  ">--</span><span class="tx_2  ">--</span><span class="tx_3  ">--</span></li>--%>
                                    <%--<li><span class="tx_1  ">--</span><span class="tx_2  ">--</span><span class="tx_3  ">--</span></li>--%>
                                    <%--<li><span class="tx_1  ">--</span><span class="tx_2  ">--</span><span class="tx_3  ">--</span></li>--%>

                                </ul>
                                <div class="tx_title tx_s"><b>实时匹配结果</b></div>
                                <ul class="m_t_y" id="tmp_trades">
                                    <c:forEach items="${logList}" begin="0" end="9" var="log">
                                    <li><span class="tx_1">${log.money}</span><span class="tx_2">${log.num}</span><span class="tx_3"><fmt:formatDate value="${log.createDate}" pattern="HH:mm:ss"/></span></li>
                                    </c:forEach>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>

                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                    <%--<li><span class="tx_1">--</span><span class="tx_2">--</span><span class="tx_3">--</span></li>--%>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="index_box_1 cooperate" style="display:none;">
        <div class="section">
            <div class="cooperate_title">安全可信赖的数字资产交易平台</div>
            <div class="cooperate_info_box">
                <img src="${ctxStatic}/themes/basic/image/information_1.png" alt="安全">
                <div class="info_title">安全</div>
                <p>超过10年金融风控经验团队</p>
                <p>全方位可定制的安全策略体系</p>
                <p>98% ${fns:getOption("system_trans","trans_name")}资产存储多重签名冷钱包</p>
            </div>
            <div class="cooperate_info_box">
                <img src="${ctxStatic}/themes/basic/image/information_2.png" alt="快捷">
                <div class="info_title">快捷</div>
                <p>点对点撮合式交易</p>
                <p>不涉及第三方的P2P交易平台</p>
                <p>交易过程简单方便快捷</p>
            </div>
            <div class="cooperate_info_box">
                <img src="${ctxStatic}/themes/basic/image/information_3.png" alt="稳定">
                <div class="info_title">稳定</div>
                <p>交易限价、交易限时、交易限量</p>
                <p>三种交易规则，保证${fns:getOption("system_trans","trans_name")}及时稳定到帐</p>
                <p>中英双语客服为您服务</p>
            </div>
        </div>
    </div>
    <div class="risk_warning align_center">
        温馨提示：数字资产是创新的投资产品，价格波动较大，具有较高的投资风险，请您投资前对数字资产充分认知，理性判断自己的投资能力，审慎做出投资决策。
    </div>

    <%--<div class="index_box_2 information">--%>
        <%--<div class="section p_t_x">--%>
            <%--<div class="information_part ml0">--%>
                <%--<div class="information_title">公司公告</div>--%>
            <%--</div>--%>
            <%--<div class="information_part">--%>
                <%--<div class="information_title">市场新闻</div>--%>
            <%--</div>--%>
            <%--<div class="information_part">--%>
                <%--<div class="information_title">行业资讯</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="index_box_2 information">
        <div class="top-split" style="border-top: #ffb266 5px solid;width: 90%;z-index: 1;margin:0 auto;"></div>

        <div class="section p_t_x" style="padding-top: 6px;">
            <div class="information_part ml0" style="line-height:40px;border-right: #edecec 1px solid;width:23%">
                <div class="information_title">${cfg.serviceOneName}</div>
                <p>
                    <b>工作微信：</b> ${cfg.serviceOneWeChat}
                </p>
                <%--<p>--%>
                    <%--<b>工作QQ：</b>${cfg.serviceOneQQ}--%>
                    <%--<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=2090419846&amp;site=qq&amp;menu=yes"></a>--%>
                <%--</p>--%>
                <%--<p>--%>
                    <%--<b>工作电话：</b>${cfg.serviceOneMobile}--%>
                <%--</p>--%>
            </div>
            <div class="information_part" style="line-height:40px;border-right: #edecec 1px solid;;width:23%">
                <div class="information_title">${cfg.serviceTwoName}</div>
                <p>
                    <b>工作微信：</b> ${cfg.serviceTwoWeChat}
                </p>
                <%--<p>--%>
                    <%--<b>工作QQ：</b>${cfg.serviceTwoQQ}--%>
                    <%--<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=2184381344&amp;site=qq&amp;menu=yes"></a>--%>
                <%--</p>--%>
                <%--<p>--%>
                    <%--<b>工作电话：</b>${cfg.serviceTwoMobile}--%>
                <%--</p>--%>
            </div>
            <div class="information_part" style="line-height:40px;border-right: #edecec 1px solid;;width:23%">
                <div class="information_title">3号客服</div>
                <p>
                    <b>工作微信：</b> ----
                </p>
                <%--<p>--%>
                <%--<b>工作QQ：</b>${cfg.serviceTwoQQ}--%>
                <%--<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=2184381344&amp;site=qq&amp;menu=yes"></a>--%>
                <%--</p>--%>
                <%--<p>--%>
                <%--<b>工作电话：</b>${cfg.serviceTwoMobile}--%>
                <%--</p>--%>
            </div>
            <div class="information_part" style="line-height:40px;width:23%">
                <div class="information_title">服务时间</div>
                <p>
                    <b>上午：</b> ${cfg.morning_service_time_begin} ---- ${cfg.morning_service_time_end}
                </p>
                <p>
                    <b>下午：</b> ${cfg.afternoon_service_time_begin}---- ${cfg.afternoon_service_time_end}
                </p>
            </div>
        </div>
    </div>
    <%--<div class="index_box_2 friend">--%>
        <%--<div class="slogan_title">战略合作机构</div>--%>
        <%--<div class="slogan_tis">清华大学五道口金融学院互联网金融实验室 &nbsp;&nbsp; 金融客咖啡 &nbsp;&nbsp; 正和岛</div>--%>
        <%--<div class="section clear_fix">--%>
            <%--<div class="friend_logo_box">--%>
                <%--<img src="${ctxStatic}/themes/basic/image/wudaokou.png" alt="清华大学五道口金融学院互联网金融实验室">--%>
            <%--</div>--%>
            <%--<div class="friend_logo_box">--%>
                <%--<img src="${ctxStatic}/themes/basic/image/jinrongke.png" alt="金融客咖啡">--%>
            <%--</div>--%>
            <%--<div class="friend_logo_box">--%>
                <%--<img src="${ctxStatic}/themes/basic/image/zhenghedao.png" alt="正和岛">--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
</div>

<script type="text/javascript">
    window.COIN = "LCC";
    $(function() {
        /**
         $("#countryCode").change(function(evt){
					//$(".write_country_code").html(this.value);
					var areaCode = $("#countryCode").val();
					var mobileCode = $("#mobileNo").val();
					$("#userCode").val(areaCode+ mobileCode);
				});
         $("#mobileNo").blur(function(){
					var areaCode = $("#countryCode").val();
					var mobileCode = $("#mobileNo").val();
					$("#userCode").val(areaCode+ mobileCode);
				});
         **/
    })
</script>
<script type="text/javascript" src="${ctxStatic}/themes/basic/script/index.js"></script>
<script type="text/javascript">
    var curTime = new Date();
    //2把字符串格式转换为日期类
    var startTime = new Date(Date.parse('2018-02-10 21:00:00'.replace(/-/g, "/")));
    var endTime = new Date(Date.parse('2018-02-25'));
</script>
<div class="layui-layer-move"></div>

<!-- -->
<div id="doc_foot" class="doc_foot">

    <div class="foot_partner">
        <div class="section" style="display:none">
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
            </ul>
            <div class="foot_end">
                <ul>
                    <li>
                        <a rel="nofollow" class="one" href="#" target="_blank"></a>
                    </li>
                    <li>
                        <a rel="nofollow" class="two" href="#" target="_blank"></a>
                    </li>
                    <li>
                        <a rel="nofollow" class="three" href="#" target="_blank"></a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="foot_lang" style="display:none">
            &nbsp;
        </div>
    </div>

</div>
<div>
    <div class="sweet-overlay" tabindex="-1"></div>
    <div class="sweet-alert" tabindex="-1">
        <div class="icon error"><span class="x-mark"><span class="line left"></span><span class="line right"></span></span>
        </div>
        <div class="icon warning"> <span class="body"></span> <span class="dot"></span> </div>
        <div class="icon info"></div>
        <div class="icon success"> <span class="line tip"></span> <span class="line long"></span>
            <div class="placeholder"></div>
            <div class="fix"></div>
        </div>
        <div class="icon custom"></div>
        <h2>Title</h2>
        <p>Text</p><button class="cancel" tabindex="2">Cancel</button><button class="confirm" tabindex="1">OK</button></div>
</div>
</body>
<script>
    var date = new Array();
    <c:forEach items="${priceList}" var="log">
    date.push(['<fmt:formatDate value="${log.createDate}" pattern="yyyy/MM/dd"/>',${log.startMoney},${log.nowMoney},${log.startMoney>log.nowMoney?log.nowMoney:log.startMoney},${log.startMoney>log.nowMoney?log.startMoney:log.nowMoney},${log.amount}]);
    </c:forEach>
    var myChart = echarts.init(document.getElementById('main'));
    // 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
    var data = splitData(date);


    console.log(data);
    //数组处理
    function splitData(rawData) {
        var datas = [];
        var times = [];
        var vols = [];
        var macds = []; var difs = []; var deas = [];
        for (var i = 0; i < rawData.length; i++) {
            datas.push(rawData[i]);
            times.push(rawData[i].splice(0, 1)[0]);
            vols.push(rawData[i][4]);

        }


        return {
            datas: datas,
            times: times,
            vols: vols,
            macds: macds,
            difs: difs,
            deas: deas
        };
    }

    //分段计算
    function fenduans(){
        var markLineData = [];
        var idx = 0; var tag = 0; var vols = 0;
        for (var i = 0; i < data.times.length; i++) {
            //初始化数据
            if(data.datas[i][5] != 0 && tag == 0){
                idx = i; vols = data.datas[i][4]; tag = 1;
            }
            if(tag == 1){ vols += data.datas[i][4]; }
            if(data.datas[i][5] != 0 && tag == 1){
                markLineData.push([{
                    xAxis: idx,
                    yAxis: data.datas[idx][1]>data.datas[idx][0]?(data.datas[idx][3]).toFixed(2):(data.datas[idx][2]).toFixed(2),
                    value: vols
                }, {
                    xAxis: i,
                    yAxis: data.datas[i][1]>data.datas[i][0]?(data.datas[i][3]).toFixed(2):(data.datas[i][2]).toFixed(2)
                }]);
                idx = i; vols = data.datas[i][4]; tag = 2;
            }

            //更替数据
            if(tag == 2){ vols += data.datas[i][4]; }
            if(data.datas[i][5] != 0 && tag == 2){
                markLineData.push([{
                    xAxis: idx,
                    yAxis: data.datas[idx][1]>data.datas[idx][0]?(data.datas[idx][3]).toFixed(2):(data.datas[idx][2]).toFixed(2),
                    value: (vols/(i-idx+1)).toFixed(2)+' M'
                }, {
                    xAxis: i,
                    yAxis: data.datas[i][1]>data.datas[i][0]?(data.datas[i][3]).toFixed(2):(data.datas[i][2]).toFixed(2)
                }]);
                idx = i; vols = data.datas[i][4];
            }
        }
        return markLineData;
    }

    //MA计算公式
    function calculateMA(dayCount) {
        var result = [];
        for (var i = 0, len = data.times.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += data.datas[i - j][1];
            }
            result.push((sum / dayCount).toFixed(2));
        }
        return result;
    }

    var option = {
        title: {
            text: '今日行情',
            left: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line'
            }
        },
        legend: {
            data: ['KLine', 'MA5','MA10','MA20']
        },
        grid: [           {
            left: '8%',
            right: '1%',
            height: '60%'
        },{
            left: '8%',
            right: '1%',
            top: '71%',
            height: '10%'
        }],
        xAxis: [{
            type: 'category',
            data: data.times,

            axisLine: { onZero: false },
            splitLine: { show: false },
            axisLine: { lineStyle: { color: '#777' } },
            axisLabel: {
                formatter: function (value) {
                    return echarts.format.formatTime('MM-dd', value);
                }
            },

            min: 'dataMin',
            max: 'dataMax',

        }, {
            type: 'category',
            gridIndex: 1,
            data: data.times,
            scale: true,

            splitLine: {show: false},
            axisLabel: {show: false},
            axisTick: {show: false},
            axisLine: { lineStyle: { color: '#777' } },
            splitNumber: 20,
            min: 'dataMin',
            max: 'dataMax',

        }],
        yAxis: [{
            scale: true,
            splitArea: {
                show: false
            }
        },{
            gridIndex: 1,
            splitNumber: 3,
            axisLine: {onZero: false},
            axisTick: {show: false},
            splitLine: {show: false},
            axisLabel: {show: true}
        }],
        series: [{
            name: '价格',
            type: 'candlestick',
            barWidth:15,
            data: data.datas,
            itemStyle: {
                normal: {
                    color: '#ef232a',
                    color0: '#14b143',
                    borderColor: '#ef232a',
                    borderColor0: '#14b143'
                }
            }
        }, {
            name: 'MA5',
            type: 'line',
            data: calculateMA(5),
            smooth: true,
            lineStyle: {
                normal: {
                    opacity: 0.5
                }
            }
        },
            {
                name: 'MA10',
                type: 'line',
                data: calculateMA(10),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: calculateMA(20),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },{
                name: '成交量',
                type: 'bar',
                xAxisIndex: 1,
                barWidth:15,
                yAxisIndex: 1,
                data: data.vols,
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var colorList;
                            if (data.datas[params.dataIndex][1]>=data.datas[params.dataIndex][0]) {
                                colorList = '#ef232a';
                            } else {
                                colorList = '#14b143';
                            }
                            return colorList;
                        },
                    }
                }
            }
        ]
    };
    myChart.setOption(option);


</script>
</html>
