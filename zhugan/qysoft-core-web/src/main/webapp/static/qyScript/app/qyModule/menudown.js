layui.define(['jquery'],function(exports){ //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);

	var obj = {

		init : function (menuNum,callback){
			var v_width= layui.jquery(document.body).width();
			layui.jquery(".select_textul").width(v_width);

			layui.jquery(".topmenu dt").css("width",(100/menuNum).toFixed(1)+"%");

			layui.jquery(".select_textdiv").click(function(){
				layui.jquery(this).parent().parent().siblings().find(".select_textul").hide();
				layui.jquery(".select_textdiv").removeClass("divfocus");
				layui.jquery(this).addClass("divfocus");
				layui.jquery(this).siblings(".select_textul").fadeToggle(0);
				var lilength = layui.jquery(this).siblings(".select_textul").find("li.focus").has(".select_second_ul").length;
				if(lilength > 0){
					layui.jquery(this).siblings(".select_textul").find("li.focus>.select_second_ul").show();
				}else{
					layui.jquery(".select_first_ul>li>p").css("width","100%");
				}
			})
			layui.jquery(".select_first_ul>li>p").click(function(){
				layui.jquery(".select_second_ul").hide();
				layui.jquery(this).parent("li").addClass("focus").siblings("li").removeClass("focus");
				var ynul = layui.jquery(this).parent("li").has(".select_second_ul").length;
				if(ynul == 0){

					var choose = layui.jquery(this).text();
					var value = layui.jquery(this).attr('value');
					layui.jquery(this).parents(".select_textul").siblings(".select_textdiv").find(".s_text").text(choose);
					layui.jquery(this).parents(".select_textul").siblings("input").val(choose);
					layui.jquery(this).parents(".select_textul").hide();

					if(callback){
						callback(choose,value);
					}
				}else{
					layui.jquery(".select_second_ul").hide();
					layui.jquery(this).siblings(".select_second_ul").show();
					event.stopPropagation();
					//chooseclick();
				}
			
			});

			obj.chooseclick(callback);

		},

		chooseclick : function (callback){

			layui.jquery(".select_second_ul>li").click(function(){
				var choose = layui.jquery(this).text();
				var value = layui.jquery(this).attr('value');
				layui.jquery(this).addClass("focusli").siblings("li").removeClass("focusli");
				layui.jquery(this).parents(".select_textul").siblings(".select_textdiv").find(".s_text").text(choose);
				layui.jquery(this).parents(".select_textul").siblings("input").val(choose);
				layui.jquery(this).parents(".select_textul").hide();
				event.stopPropagation();

				if(callback){
					  callback(choose,value);
				}
			});
		}


	};

  //输出test接口
  exports('menudown', obj);
});    