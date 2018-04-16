
/**
 * 
 */
(function () {
        function slide(index, o_index) {
            arguments[1];
            iNow = index, ali.eq(index).addClass("cur").siblings().removeClass(), ali.eq(index).stop().animate({
                width: 28
            }, 400).siblings().stop().animate({
                width: 10
            }, 400).removeClass().stop().animate({
                width: 10
            }, 400), aPage.eq(index).siblings().stop().animate({
                opacity: 0
            }, 600), aPage.eq(index).stop().animate({
                opacity: 1
            }, 600), aslide_img.eq(index).stop().animate({
                opacity: 1
            }, 600).css({
                "z-index": 5
            }).siblings().stop().animate({
                opacity: 0
            }, 600).css({
                "z-index": 3
            })
        }

        function autoRun() {
            ++iNow == ali.length && (iNow = 0), slide(iNow)
        }
        for (var ali_box = $("#page_list"), aPage = $("#banner_big_box").find("p"), aslide_img = $(".banner_box b"), iNow = 0, list_num = aslide_img.length, l = 0; l < list_num; l++) 
            0 == l ? ali_box.append('<li class="cur" style="width: 28px;"></li>') : ali_box.append("<li></li>");
        
        var ali = $("#index_banner_box").find("li");
        ali.each(function (index) {
            $(this).click(function () {
                var o_index = ali_box.find(".cur").index();
                slide(index, o_index)
            })
        });
        var timer = setInterval(autoRun, 4e3);
        ali.hover(function () {
            clearInterval(timer)
        }, function () {
            timer = setInterval(autoRun, 4e3)
        }), aslide_img.hover(function () {
            clearInterval(timer)
        }, function () {
            timer = setInterval(autoRun, 4e3)
        })
})();

/**
 * 
 */

var time_range = function (beginTime, endTime) {
    var strb = beginTime.split (":");
    if (strb.length != 2) {
        return false;
    }

    var stre = endTime.split (":");
    if (stre.length != 2) {
        return false;
    }
    var b = new Date ();
    var e = new Date ();
    var n = new Date ();

    b.setHours (strb[0]);
    b.setMinutes (strb[1]);
    e.setHours (stre[0]);
    e.setMinutes (stre[1]);

    if (n.getTime () - b.getTime () > 0 && n.getTime () - e.getTime () < 0) {
        return true;
    } else {
        return false;
    }
}
$(function(){
    window.setInterval(function(){
       //$("#head_notice").html(window.COIN);
       //

    },1000);


    //表单验证
    $(".bind_validator").validate({
        rules:{
              userCode:{
                  required:true
              },
              password:{
                  required:true
              },
              captchaCode:{
                  required:true
              }
        }
    });

    // //表单提交
    // $(".bind_validator").submit(function(evt){
    //     evt.preventDefault();
    //     //if(time_range ("8:59", "22:00")){
    //       var $form = $(this);
    //       $form.ajaxSubmit({
    //         type:"get",
    //         url:$form.attr("action"),
    //         dataType: "json",
    //         beforeSubmit:function(formData,jqForm,options){
    //             return jqForm.valid();
    //         },
    //         success:function(dat){
    //               if(dat.code==0||dat.code==1){
    //                   layer.msg("登录成功，正在为您跳转",function(){window.location.href="/security/center.jhtml";});
    //               }else{
    //
    //                 refreshCaptchaImage('captchaImg');
    //                 layer.msg(dat.msg);
    //               }
    //         }
    //       });
    //     //}else{
		// //	sweetAlert("提示", "您好，我们在这个时间22:00:00-09:00:00 进行交易数据处理，在此期间暂停访问，感谢您的支持与配合！", "error");
		// //}
    //     return false; //此处必须返回false，阻止常规的form提交
    // });
    //切换币
    $("#market_type li").click(function(){
      $(this).siblings().removeClass("cur");
      $(this).addClass("cur")
      //alert($(this).attr("data-type"));
      var COIN = $(this).attr("data-type");
      window.COIN =COIN;
      $("#HB_Home_iframe").attr("src","/market/"+COIN+"_kline.jhtml");

    });
});
// /***
//  * 刷新验证码图片
//  * @param imageId 图片ID
//  */
// function refreshCaptchaImage(imageId){
//     var imgSrc = $("#"+imageId);
//     var srcTemp = imgSrc.attr("src").split("=");
//     //时间戳
//     //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
//     var timestamp = (new Date()).valueOf();
//     imgSrc.attr("src",srcTemp[0]+"="+timestamp);
// }