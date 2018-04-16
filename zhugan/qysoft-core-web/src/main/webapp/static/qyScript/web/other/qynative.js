var websiteUrl = "http://cxtxguan.qynetwork.net/";
var syncUrl = "http://cxtxshopguan.qynetwork.net/";

/**
 * app公共方法接口
 */

apiready = function() {

	if ( typeof apiready_before === 'function') {
		apiready_before();
	}
	api.hideProgress();
	//api.setCustomRefreshHeaderInfo({
	//    bgColor: '#f0f0f0',
	//    image: {
	//        pull: [
	//            'widget://image/dropdown_anim_00.png',
	//            'widget://image/dropdown_anim_01.png',
	//            'widget://image/dropdown_anim_02.png',
	//            'widget://image/dropdown_anim_03.png',
	//            'widget://image/dropdown_anim_04.png',
	//            'widget://image/dropdown_anim_05.png',
	//            'widget://image/dropdown_anim_06.png',
	//            'widget://image/dropdown_anim_07.png',
	//            'widget://image/dropdown_anim_08.png',
	//            'widget://image/dropdown_anim_09.png',
	//            'widget://image/dropdown_anim_10.png',
	//        ],
	//        load: [
	//            'widget://image/dropdown_loading_00.png',
	//            'widget://image/dropdown_loading_01.png',
	//            'widget://image/dropdown_loading_02.png'
	//        ]}
	//}, function() {
	//    //下拉刷新被触发，自动进入加载状态，使用 api.refreshHeaderLoadDone() 手动结束加载中状态
	//    //下拉刷新被触发，使用 api.refreshHeaderLoadDone() 结束加载中状态
	//    setTimeout(function(){
	//        api.refreshHeaderLoadDone();
	//    },300)
	//});

	if ( typeof (CloseRefreshHeader) == "undefined") {
		api.setRefreshHeaderInfo({
			visible : true,
			bgColor : '#f4f4f4',
			textColor : '#666',
			textDown : '下拉刷新',
			textUp : '松开刷新',
			showTime : false
		}, function(ret, err) {
			api.refreshHeaderLoading();
			setTimeout(function() {
				location.reload();

				api.showProgress({
					style : 'default',
					animationType : 'fade',
					title : '努力加载中...',
					text : '先喝杯茶...',
					modal : true
				});

				api.refreshHeaderLoadDone();
			}, 300)
		});
	}

	var pageParam = api.pageParam;
	var winName = pageParam.winName;
	var frameName = api.frameName;

	////监听上一页面刷新
	//api.addEventListener({
	//    name:'viewappear'
	//},function(ret,err){
	//
	//    if("undefined" != typeof reload){
	//        if(reload=="1"){
	//            location.reload(true);
	//
	//        }
	//    }
	//
	//});

	if ( typeof apiready_after === 'function') {

		var js = 'if (typeof clearTitleBtn === "function") {clearTitleBtn();}';
		api.execScript({
			name : winName,
			script : js
		});

		apiready_after();
	}

	api.parseTapmode();

}
/**
 * 上拉加载
 * @param params
 * @param callback
 */
function upPullLoad(url, page, param, callback) {
	var user = api.getPrefs({
		sync : true,
		key : 'user'
	});

	api.addEventListener({
		name : 'scrolltobottom'
	}, function(ret, err) {
		$(".jiazai-box").empty();
		$(".jiazai-box").append($("<p />", {
			text : "上拉加载更多..."
		}).append($("<i />").addClass("aui-refresh-pull-arrow"))).show();
		setTimeout(function() {++page;
			$.extend(param, {
				page : page
			});
			qyajax(url, param, function(result) {
				if (!result.data || result.data.length == 0) {
					$(".jiazai-box").empty().append($("<p />", {
						text : "没有数据"
					})).show();
					setTimeout(function() {
						$(".jiazai-box").hide();
					}, 500);
				} else {
					if (callback) {
						callback(result);
					}

					setTimeout(function() {
						$(".jiazai-box").hide();
					}, 500);
				}
			});
		}, 500);
	});
}

//公用ajax

function qyajax(url, data, callback, errorback) {
	
	api.ajax({
		url : websiteUrl + url,
		method : 'POST',
		data : {
			values : data
		}
	}, function(ret, err) {
		if (ret) {
			if (ret.code == 1) {
				//成功返回信息
				if (callback) {
					callback(ret);
				}
			} else if (ret.code == 0) {
				api.toast({
					msg : ret.msg,
					duration : 2000,
					location : 'middle'
				});

				if (errorback) {
					errorback(ret);
				}
			} else if (ret.code == -1) {
				api.toast({
					msg : "请重新登录",
					duration : 2000,
					location : 'middle'
				});
			} else {
				api.toast({
					msg : "发生位置错误,请稍候再试!",
					duration : 2000,
					location : 'middle'
				});
			}

		} else {
			api.toast({
				msg : "网络错误,请稍候再试!",
				duration : 2000,
				location : 'middle'
			});
		}
	});

}

//打开对话框
/**
 *
 * @param {Object} name 对话框名称
 * @param {Object} url url
 * @param {Object} option 参数 JONS格式
 */
function openWin(name, url, option) {
	var delay = 0;

	var anima = "suck";
	if (api.systemType != 'ios') {
		delay = 0;
		anima = "push";
	}

	var param = {
		name : name,
		url : url,
		delay : delay,
		slidBackType : "edge",
		useWKWebView : false,
		animation : {
			type : "movein", //动画类型（详见动画类型常量）
			subType : "from_right", //动画子类型（详见动画子类型常量）
			duration : delay //动画过渡时间，默认300毫秒
		}

	};
	$.extend(param, option);

	api.openWin(param);

}

function openFrame(frameName, frameUrl, rect, option) {

	var param = {
		name : frameName,
		url : frameUrl,
		bounces : true,
		vScrollBarEnabled : false,
		hScrollBarEnabled : false,
		rect : rect,

	};
	$.extend(param, option);

	api.openFrame(param);

}

/**
 * 打开通用win
 * @param {Object} name  frame名称
 * @param {Object} frame  frameURL
 * @param {Object} option 参数 JONS格式
 */
function openNormalWin(frameName, frameUrl, title, option) {

	var param = {
		pageParam : {
			frameName : frameName,
			frameUrl : frameUrl,
			title : title
		}
	};
	$.extend(param, option);

	openWin(frameName + "_win", "./win/normalWin.html", param);

}

/**
 * 打开webwin
 * @param {Object} name  名称
 * @param {Object} frame  网址
 * @param {Object} option 参数 JONS格式
 */
function openWebWin(frameName, frameUrl, title, option, reload) {
	var param = {
		frameName : frameName,
		frameUrl : frameUrl,
		title : title,
		lastWin : api.winName,
		lastFrame : api.frameName,
		hxzf:option.hxzf,
	};

	$.extend(param, option);
	var params = {
		pageParam : param,
		reload : reload ? true : false
	};

	if (api.frameName == frameName) {
		var timestamp = (new Date()).valueOf();

		openWin(frameName + "_win" + timestamp, "win/webWin.html", params);
	} else {
		openWin(frameName + "_win", "win/webWin.html", params);
	}

}

/**
 * 关闭win
 */
function closeWin() {

	if (true) {

		var jsfun = 'location.reload();';
		api.execScript({
			name : api.pageParam.lastWin,
			frameName : api.pageParam.lastFrame,
			script : jsfun
		});

		setTimeout(function() {
			api.closeWin();

		}, 1000);
	} else {
		api.closeWin();
	}
}

function setWinTitle(title) {

	var pageParam = api.pageParam;
	var winName = pageParam.winName;
	var frameName = api.frameName;

	var js = 'if (typeof setTitle === "function") {setTitle("' + title + '");}';
	//var js = 'setTitle("'+title+'");';

	api.execScript({
		name : winName,
		script : js
	});
}

//给对话框增加btn
//addTitleBtn("share-btn"," aui-icon-forwardfill","","sharebtnFun()");
function addTitleBtn(id, icon, title, fun) {

	var pageParam = api.pageParam;
	var winName = pageParam.winName;
	var frameName = api.frameName;

	var js = 'addTitleBtn("' + id + '","' + icon + '","' + title + '","' + winName + '","' + frameName + '","' + fun + '");';

	api.execScript({
		name : winName,
		script : js
	});
}

/**
 * 显示父窗体搜索对话框
 * search 查询内容会回调本页面 search_callback 方法
 */
function showSearch() {

	var pageParam = api.pageParam;
	var winName = pageParam.winName;
	var frameName = api.frameName;

	var js = 'openSearch();';

	api.execScript({
		name : winName,
		script : js
	});
}

/**
 * 清理搜索框内容
 *
 */
function clearSearch() {

	var pageParam = api.pageParam;
	var winName = pageParam.winName;
	var frameName = api.frameName;

	var js = 'clearSearch();';

	api.execScript({
		name : winName,
		script : js
	});
}

/**
 * 开启分享功能
 */
function openFenxiang() {

	var pageParam = api.pageParam;
	var winName = pageParam.winName;
	var frameName = api.frameName;

	var js = 'openFenxiang();';

	api.execScript({
		name : winName,
		script : js
	});
}

/**
 * 解析get参数
 * @param {Object} name
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}

	return null;
}

function qyRound(x, num) {
	return Math.round(x * Math.pow(10, num)) / Math.pow(10, num);
}

function frameReload() {
	location.reload();

}

function share(val) {
	var url = websiteUrl + 'Mobile/Login/register/tg_id/' + val;

	api.actionSheet({
		title : '分享',
		cancelTitle : '取消',
		buttons : ['朋友圈分享', '微信分享']
	}, function(ret, err) {
		if (ret) {
			var index = ret.buttonIndex;
			switch (index) {
				case 1:
					var wx = api.require('wx');
					wx.shareWebpage({
						apiKey : 'wxcadd98777b8b788c',
						scene : 'timeline',
						title : '众云汇',
						description : '博盛租车',
						thumb : '../image/biao.png',
						contentUrl : url
					}, function(ret, err) {
						if (ret.status) {
							api.toast({
								msg : '分享成功'
							});
						} else {
							api.toast({
								msg : '分享失败'
							});
						}
					});
					break;
				case 2:
					var wx = api.require('wx');
					wx.shareWebpage({
						apiKey : '',
						scene : 'session',
						title : '众云汇',
						description : '博盛租车',
						thumb : '../image/biao.png',
						contentUrl : url
					}, function(ret, err) {
						if (ret.status) {
							api.toast({
								msg : '分享成功'
							});
						} else {
							api.toast({
								msg : '分享失败'
							});
						}
					});
					break;
			}
		} else {
		}
	});
}