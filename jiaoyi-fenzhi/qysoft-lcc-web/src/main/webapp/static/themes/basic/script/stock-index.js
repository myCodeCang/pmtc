//核心文件
var stock={
	color:{
		border:"#ccc",//边框颜色
		withinBorder:"#dedede",//内边框颜色
		solid:"#333",//虚线颜色
		trend:"#1879fc",//K线颜色
		fill:"rgba(154,196,248,.3)",//填充颜色
		arc:"red",	//圆心
		volume:"#999",	//成交量颜色
		red:"#dd2200",
		green:"#32a90f",
		minuteAvg:"#ffff00",//分时均线
		avg:{//日K
			_5:"#dd2200",
			_10:"#822e32",
			_20:"#f6c750",
			_30:"#155a9c"
			},
		MACD:{
			DIF:"#dd2200",
			DEA:"#155a9c"
		},
		KDJ:{
			K:"#f0f888",
			D:"#54fcfc",
			J:"#ff80ff"
		}
	},
	touch:{left:5,top:100},
	stockID:"",//当前股票ID,
	stockType:"",//股票分类
	successData:[],//当前行情数据,
	data:null,//当前行情数据,
	isPC:false,//判断电脑还是手机
	jspath:null,//当前JS路径
	day:null,//今天日期
	current:null,//当前菜单选择
	addNum:function(num1, num2) {
		var sq1,sq2,m;
		try {sq1 = num1.toString().split(".")[1].length;}
		catch (e) {sq1 = 0;}
		try {sq2 = num2.toString().split(".")[1].length;}
		catch (e) {sq2 = 0;}
		m = Math.pow(10,Math.max(sq1, sq2));
		return (num1 * m + num2 * m) / m;
	},
	//
	
	ajax:function(data,c,o){
		var timeout=15000;
		if(data.Action=="current-hq"){timeout=3000;}
		var dataUrl = "/kline/stock_"+data.Action.toLowerCase() +"_"+ data.stockID.toLowerCase()+"_data.json?"+Math.random();

		var ajaxTimeoutTest=$.ajax({
				  method: "GET",
				  timeout : timeout,
				  url: dataUrl+'?'+Math.random(),
				  //data: data,
				  dataType:"text",
				  complete : function(XMLHttpRequest,status){
				　　　　if(status=='timeout'){
				 　　　　　 ajaxTimeoutTest.abort();　
				　　　　}
				　　}
				}).done(function( data ) {
					c.call(o,true,data);
					delete data;
				}).fail(function() {
					c.call(o,false)
				});
	},
	//行情
	hq:{
		time:0,
		//是否交易时间内
		isRun:function(){
			
			return false;
		},
		//首次加载行情
		load:function(data){
			if(data.hq!=null){
				$("#name").html(data.hq.split(",")[0]);
			}
	
			if(data.isHqReload){
				this.reload(function(result){
					if(result){
						stock.menu.load(data.default);
						stock.menu.click();//添加点击事件
						stock.hq.run();//启动线程
					}else{
						stock.error("<div onclick='window.location=window.location'>加载失败，点击重试</div>");
					}
				});
			}else{
				
				stock.data=data.hq.split(",");
				//更新顶部
				//stock.header();

				stock.menu.load(data.default);
				stock.menu.click();//添加点击事件
				stock.hq.run();//启动线程

			}
		},

		//更新行情
		reload:function(c,o){
			//console.log("===R=====>");
			$.getScript("/kline/stock_detail_"+stock.stockID.toLowerCase()+"_data.json?"+Math.random()).done(function(){
				stock.data=eval("hq_str_"+stock.stockID.toLowerCase()).split(",");
				c.call(o,true);
			}).fail(function() {
				c.call(o,false);
			});
		},
		run:function(){

			var index=0;

			//启动线程
			window.setInterval(function(){
				//alert(77+"=="+stock.hq.isRun());
				if(stock.hq.isRun()){
					index++;
					stock.hq.reload(function(result){
						//console.log("===R=====>"+result);
						if(result){
							if($("#minute").length==1){
								stock_minute.update();
							}
						}
					});
					//一分钟(20次)
					if(index==10){
						index=0;
						if($("#minute").length==1){
							stock_minute.reload();
						}
					}
				}else{
					$("#dot").css({left:-1000});
				}
				stock.hq.time+=3;
			},3000);

		}
	},
	//错误提示
	error:function(msg,top){
		if($("#error").length==0){
			$("#stockLine").append('<div id="error"><div></div></div>');
		}
		$("#loading").hide();
		$("#error").show();
		if(top==undefined){top=0;}
		$("#error").css({top:top})
		$("#error div").html(msg);
	},
	configData:null,
	//只加载一次
	load:function(data){
		$.ajaxSetup({cache: true});
		$("#stockLine").append('<div id="loading"></div>');

		this.stockID=data.stockID;
		this.stockType=data.stockType;
		this.day=data.day;
		this.hq.time=data.time;

		this.isPC=this.script.isPC();

		if(this.isPC){
			//K线总高言
			$("#stockLine").css({width:725,height:680,overflow: 'hidden'})
		}else{
			$(window).bind('resize load', function() {
				$("#stockLine").css("zoom", $(window).width() / 640);
			});
		}
		this.jspath=this.script.jspath();

		stock.tpl.header();//创建顶部模板

		stock.tpl.menu(data);//创建菜单
		if(data.default==undefined){
			data.default=data.menu[0].name;
		}
		this.hq.load(data);
		delete data;
	},
	//处理菜单
	menu:{
		load:function(name){
			$("#nav li").removeClass("current");
			$("#nav #nav-"+name).addClass("current");
			stock.current=name;
			$("#error").hide();
			$("#header ul").hide();
			$(".item").hide();
			if($("#"+name).length==0){
			 	$("#loading").show();
			 	stock.script.load(name);//引入JS
			 	$("#"+name).show();
			}else{
				$("#loading").hide();
				
				$("#"+name).show();
			}
		},
		//点击
		click:function(){
			$("#nav li").click(function(){
				stock.menu.load($(this).attr("name"));
			})
		  }
	},
	//模板(只创建一次)
	tpl:{
		//顶部
		header:function(){
			var str="";
			str+='<div id="net-layout"></div><div id="net-error">网络已断开</div><div id="header"><span id="coin-name"></span><span id="name"></span><span id="current-price"></span><span id="current-bargain"></span><span id="current-time"></span><span id="current-msg"></span><ul></ul><span id="close"><img src="/scripts/stock/images/close.png"></span></div>';
			//$("#stockLine").append(str);
			//释放内存
			delete str;
		},
		//菜单
		menu:function(data){
			var str="";
			str+='<div id="nav"><ul>';
			for(var i=0;i<data.menu.length;i++){
				str+='<li name="'+data.menu[i].name+'" id="nav-'+data.menu[i].name+'">'+data.menu[i].title+'</li>';
			}
			str+='</ul></div>';
			$("#stockLine").append(str);
			//释放内存
			delete str;
			delete data;
		},
		//曲线模板
		min:function(name){
			if($("#"+name).length==0){
				str="";
				str+='<div class="item item-min" id="'+name+'"><div class="touch" id="touch-'+name+'">';

				str+='<div class="K-line"><ul class="price"> <li class="red"></li> <li class="red"></li><li class="green"></li></ul><ul class="range"><li class="red"></li><li class="red"></li><li class="green"></li></ul><canvas id="'+name+'-k" class="k"></canvas></div>';
				//str+='<div class="logo"></div>';

				str+='<ul class="KL-time"><li></li><li></li><li></li><li></li></ul>';

				str+='<div class="L-line"><ul class="volume"><li class="max-volume"></li> </ul><canvas id="'+name+'-l" class="l"></canvas></div>';

				str+='</div></div>';
				$("#stockLine").append(str);
				str=null;
			}
			name=null;
		},
		//K线模板
		k:function(name){
			if($("#"+name).length==0){
				str="";
				str+='<div class="item item-k" id="'+name+'"><div class="touch" id="touch-'+name+'">';

				str+='<div class="K-line"><ul class="price"> <li></li><li></li><li></li><li></li><li></li></ul> <canvas id="'+name+'-k" class="k"></canvas></div>';
				//str+='<div class="logo"></div>';

				str+='<ul class="KL-time"><li></li> <li></li><li></li><li></li><li></li></ul>';

				str+='<div class="L-line"><ul class="volume"><li class="max-volume"></li><li></li><li></li></ul><canvas id="'+name+'-l" class="l"></canvas></div>';


				str+='<ul class="avg"></ul>';
				str+='<ul class="MACD"><li style=" color:'+stock.color.MACD.DIF+'">DIF:<span></span></li><li style="color:'+stock.color.MACD.DEA+'">DEA:<span></span></li><li style=" color:#155a9c">MACD:<span></span></li></ul>';

				str+='<ul class="KDJ"><li style=" color:'+stock.color.KDJ.K+'">K:<span></span></li><li style="color:'+stock.color.KDJ.D+'">D:<span></span></li><li style=" color:'+stock.color.KDJ.J+'">J:<span></span></li></ul>';

				str+='<div class="x"><span class="current-price"></span></div><div class="y"></div>';

				str+='</div>';

				//str+='<ul id="ktool"><li class="fq" name="qfq">前复权</li><li class="current fq" name="normal">不复权</li><li class="fq" name="hfq">后复权</li><li class="current volume" name="normal">成交量</li> <li class="volume" name="MACD">MACD</li><li class="volume" name="KDJ">KDJ</li></ul>';
				
				str+='<div class="zoom"><span class="up"></span><span class="down"></span></div>';
				str+='<ul id="ktool"><li class="current volume" name="normal">成交量</li> <li class="volume" name="MACD">MACD</li><li class="volume" name="KDJ">KDJ</li></ul>';
				str+='</div>';
				$("#stockLine").append(str);
				str=null;
			}
			name=null;
		},
		minute:function(name){
			if($("#"+name).length==0){
				var str="";
				str+='<div class="item" id="'+name+'"><div class="touch" id="touch-'+name+'">';
				str+='<div class="K-line"><div id="dot"></div><ul class="price"> <li>&nbsp;</li><li>&nbsp;</li><li>&nbsp;</li> <li>&nbsp;</li> <li>&nbsp;</li></ul><ul class="range"><li></li> <li></li> <li></li> <li></li> <li></li></ul><canvas id="minute-k" class="k"></canvas></div>';
				//str+='<div class="logo"></div>';
				str+='<ul class="KL-time"><li class="one">0:00</li> <li class="two">12:00</li> <li class="three">23:59</li> </ul>';
				str+='<div class="L-line"><ul class="volume"><li class="max-volume"></li> </ul><canvas id="'+name+'-l" class="l"></canvas></div>';
				str+='</div>';
				$("#stockLine").append(str);
				str=null;
			}
			name=null;
		}
	},
	//加载JS
	script:{
		jspath:function(){
			var js="stock.js";
			var scripts = document.getElementsByTagName("script");
			var path = "";
			for (var i = 0, l = scripts.length; i < l; i++) {
				var src = scripts[i].src;
				if (src.indexOf(js) != -1) {
					var ss = src.split(js);
					path = ss[0];
					break;
				}
			}

			var href = location.href;
			href = href.split("#")[0];
			href = href.split("?")[0];
			var ss = href.split("/");
			ss.length = ss.length - 1;
			href = ss.join("/");
			if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
				path = href + "/" + path;
			}
			return path;
		},
		isPC:function() {
			var userAgentInfo = navigator.userAgent;
			var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
			var flag = true;
			for (var v = 0; v < Agents.length; v++) {
				if (userAgentInfo.indexOf(Agents[v]) > 0) {
					flag = false;
					break;
				}
			}
			return flag;
		},
		reload:function(name){
			$("#loading").show();
			this.load(name);
		},
		//JS入口
		load:function(name){

			this.include.load(name,function(){

				eval("stock_"+name).load(name,function(result){
					if(!result){
						stock.error('<span onclick="stock.script.reload(\''+name+'\')" >点击重新加载'+name+'</span>',90);
						return false;
					}
					$("#error").hide();
					//创建模板
					if(name=="minute"){
						stock.tpl.minute(name);
					}else if(name=="dayK"||name=="weekK"||name=="monthK"||name=="1minute"||name=="5minute"||name=="15minute"||name=="30minute"||name=="60minute"){
						stock.tpl.k(name);
					}else{
						stock.tpl.min(name);
					}
					//画图
					eval("stock_"+name).canvas(function(){
						$(".item").hide();
						$("#"+name).show();
						$("#loading").hide();//隐藏加载框
					});
					delete name;
				});
			});


		},
		//加载js(只加载一次)
		include:{
			jslist:{},
			load:function(name,c,o){
				if(eval("this.jslist._"+name)==undefined){
					eval("this.jslist._"+name+"='"+name+"'");
					if(name=="minute"){
						//$.getScript(stock.jspath+'stock-'+name+'.js?v=1.1').done(function() {
						$.getScript('/scripts/stock/lib/stock-'+name+'.js?v=1.4').done(function() {
							c.call(o);
						}).fail(function() {
							eval("stock.script.include.jslist._"+name+"=undefined");
							stock.error('<span onclick="stock.script.reload(\''+name+'\')" >点击重新加载</span>',90);
						});

					}else{
						//modify by Ricky
						var ajscodeUrl = "stock_"+name+".js";

						$.getScript('/scripts/stock/lib/'+ajscodeUrl).done(function() {
							c.call(o);
						}).fail(function(jqxhr, settings, exception) {
							eval("stock.script.include.jslist._"+name+"=undefined");
							stock.error('<span onclick="stock.script.reload(\''+name+'\')" >点击重新加载</span>',90);
						});
					}
				}else{
					c.call(o);
				}
			}
		}
	}
}