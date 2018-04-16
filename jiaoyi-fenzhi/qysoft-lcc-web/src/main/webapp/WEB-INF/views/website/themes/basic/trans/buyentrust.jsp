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
				<a class="close" href="#" id="close_notice">
					<i class="icon_right_close_2"></i>
				</a>
			</div>
			<div class="section doc_section">

				<!-- 左侧开始 -->
				<%@ include file="/WEB-INF/views/website/themes/basic/layouts/transLeft.jsp" %>
				<!-- 左侧结束 -->

				<div class="doc_main_wrap">
					<input type="hidden" name="coin_type" value="cny_btc">
					<div class="mod mod_main">
						<div class="mod_hd">
							<h3 class="mod_title">买入委托记录</h3>
							<div class="mod_option">
								<!-- <a href="/trade/cny_btc_match?pn=&down=1"><i class="icon_down" title="下载"><span>下载</span></i></a>-->
							</div>
						</div>
						<div class="mod_bd">
							<table class="table table_striped align_right">
								<thead>
									<tr>
										<th class="align_center">操作</th>
										<th class="align_center">委托号</th>
										<th class="align_center" style="">买家</th>
										<th class="align_center">时间</th>
										<th class="align_center">数量</th>
										<th class="align_center">已成交量</th>
										<th class="align_center">单价</th>
									</tr>
								</thead>
								<tbody>
								<c:if test="${page.count == 0}">
									<tr class="table_crumbs">
										<td colspan="7" class="align_center">
											<i class="icon_info"></i>暂无记录
										</td>
									</tr>
								</c:if>
								<c:forEach items="${page.list}" var="transcodeBuy">
									<tr class="table_crumbs">
										<td  class="align_center"  >
												<a href="javascript:void(0);" onclick="soldOut('${transcodeBuy.id}')">下架</a>
										</td>
										<td  class="align_center">
												${transcodeBuy.id}
										</td>

										<td  class="align_center" >
												${transcodeBuy.userName}
										</td>
										<td  class="align_center">
											<fmt:formatDate value="${transcodeBuy.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td  class="align_center">
												${transcodeBuy.sellNum}
										</td>
										<td  class="align_center">
												${transcodeBuy.sellNum - transcodeBuy.nowNum}
										</td>
										<td  class="align_center">
												${transcodeBuy.money}
										</td>

									</tr>
								</c:forEach>
									<%--<tr class="table_crumbs">--%>
										<%--<td colspan="7" class="align_center">--%>
											<%--<i class="icon_info"></i>暂无记录--%>
										<%--</td>--%>
									<%--</tr>--%>
								</tbody>
							</table>
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
		<script type="text/javascript">
            function page(pageNo,pageSize){
                window.location.href="${ctx}/trans/buyentrust?siteBar=21&pageNo="+pageNo+"&pageSize="+pageSize;
            }

			 function soldOut (id) {
				var currWin = window;

				swal({
					title: '确定要下架该订单吗?',
					text: "你将无法恢复！",
					type: "warning",
					showCancelButton: true,
					confirmButtonColor: "#3085d6",
					cancelButtonColor: '#d33',
					confirmButtonText: '是的,确定要下架',
					cancelButtonText: '不了，再想想',
					closeOnConfirm: false,
					allowOutsideClick: false
				}, function() {
					$.post("${ctx}/trans/revokeaction",{id:id}, function(dat) {
						var modalType = 'error';
						if(dat.status == 1) {
							modalType = 'success';
							setTimeout(function() {
								window.location.reload(true);
							}, 2000);

						}
						swal({
							title: dat.info,
							text: dat.info,
							timer: 2000,
							showConfirmButton: false
						});
					});
				});
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