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
				<a class="close" href="#" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="section doc_section">

				<div class="doc_left_bar">

					<dl class="doc_left_menu" id="doc_left_menu">

						<dt data-name="finance" class=" cur "><i class="icon_nav_finance"></i>资产信息 <i class="icon_gray_arrows"></i></dt>
						<dd class=" open ">
							<p>
								<a href="${ctx}/user/myBankCard">我的银行卡</a><i class="icon_gray_arrows_2"></i></p>
							<p>
								<a href="${ctx}/user/myBurse">我的钱包</a><i class="icon_gray_arrows_2"></i></p>
						</dd>

						<dt data-name="deposit" id="allow_currency" data-allow_cny="1" data-allow_usd="" class=" cur  "><i class="icon_nav_deposit"></i>${fns:getOption("system_trans","trans_name")}转内网 <i class="icon_gray_arrows"></i></dt>
						<dd class=" open ">
							<p class="">
								<a href="${ctx}/user/myWithdraw">${fns:getOption("system_trans","trans_name")}转内网</a><i class="icon_gray_arrows_2"></i></p>
							<p class="">
								<a href="${ctx}/user/myWithdrawLog">转内网记录</a><i class="icon_gray_arrows_2"></i></p>
						</dd>
					</dl>
				</div>

				<div class="doc_main_wrap">

					<div class="mod mod_main">
						<div class="mod_hd">
							<h3 class="mod_title">我的账户概况</h3>
						</div>
						<div class="mod_bd">
							<!--资产账户详细信息-->
							<div class="c_balance">
								<ul class="item">
									<li class="c_2">
										<span>可用${fns:getOption("system_trans","trans_name")}：</span><br>
										<strong class="cny_cny_available">${userinfo.money}</strong>
										<b>${fns:getOption("system_trans","trans_name")}</b>
									</li>
									<%--<li class="c_2">--%>
										<%--<span>冻结:</span><br>--%>
										<%--<strong class="cny_cny_available">${userinfo.money2}</strong>--%>
									<%--</li>--%>
									<li class="c_2">
										<span>当前价格:</span><br>
										<strong class="cny_cny_available">${nowMoney}</strong>
									</li>

									<li class="c_2" style="display:none">
										<span>我的资产:</span><br>
										<strong class="cny_cny_available">${userinfo.money}</strong>
									</li>
									<li class="c_4" style="display:none">
										<a href="deposit_LCC.jhtml" class="btn btn_orange size_s">转内网</a>
									</li>
								</ul>
								<div class="c_balance_total">
									<div class="group">
										<div class="col_1 align_right col_text">我的钱包地址</div>
										<div class="col_2">
											<ul class="coin_address m_b_20" id="coin_address">
												<li>
													<div class="float_left font_bold font_16">
														${userinfo.remarks}</div>
													<div class="float_right">

													</div>
												</li>
											</ul>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>

					<div class="mod mod_main m_t_30">
						<div class="mod_hd" id="list_hd">
							<h3 class="mod_title">账户明细</h3>
							<div class="mod_option">
								<!--
			<a href="javascript:;"><i class="icon_down" id="export_btn" title="下载"><span>下载</span></i></a>
			-->
							</div>
						</div>
						<div class="mod_bd">
							<div class="filter_box">
								<form action="#" method="get" id="form_finance">
									<div class="group" style="display:none">
										<span class="label">账户类型：</span>
										<a href="index.jhtml?actType=LCC">${fns:getOption("system_trans","trans_name")}</a>
									</div>
								</form>
							</div>
							<table class="table table_striped" style="font-size:12px">

								<thead>
									<tr>
										<th class="align_center">交易日期</th>
										<th class="align_center">帐变类型</th>
										<th class="align_center">帐变金额</th>
										<th class="align_center">备注</th>
									</tr>
								</thead>
								<tbody>
								<c:if test="${page.count == 0}">
									<tr class="table_crumbs">
										<td colspan="5" class="align_center">
											<i class="icon_info"></i>暂无记录
										</td>
									</tr>
								</c:if>
								<c:forEach items="${page.list}" var="account">
									<tr class="table_crumbs">
										<td  class="align_center">
											<fmt:formatDate value="${account.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td  class="align_center" >
												${fns:getDictLabel(account.changeType,"change_type","")}
										</td>
										<td  class="align_center">
												${account.changeMoney}
										</td>
										<td  class="align_center">
												${account.commt}
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							<%--<div class="align_right">--%>
								<%--<form id="fiAcBalanceItemListPagerPageForm" method="get" action="index.jhtml">--%>
									<%--<input type="hidden" name="fiAcBalanceItemListPager_pageId" value="fiAcBalanceItemListPager">--%>
									<%--<input type="hidden" name="fiAcBalanceItemListPager_pageSize" value="20">--%>
									<%--<input type="hidden" name="fiAcBalanceItemListPager_page" value="1">--%>
									<%--<div class="row">--%>
										<%--<div class="col-md-12">--%>
											<%--没有找到记录.</div>--%>
									<%--</div>--%>
								<%--</form>--%>
							<%--</div>--%>
						</div>

						<c:if test="${page.count > 0}">
							<div class="pagination">
									${page}
							</div>
						</c:if>
					</div>

				</div>

			</div>
		</div>
	<script>
        function page(pageNo,pageSize){
            window.location.href="${ctx}/user/zichan?siteBar=32&pageNo="+pageNo+"&pageSize="+pageSize;
        }
	</script>

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

</html>