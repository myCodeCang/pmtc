/**
 * 
 */
 function refreshCaptchaImage(imageId){     
    var imgSrc = $("#"+imageId);     
    var srcTemp = imgSrc.attr("src").split("&rnd");  
    var timestamp = (new Date()).valueOf();
    imgSrc.attr("src",srcTemp[0]+"&rnd="+timestamp);
}
$(function(){
   //切换币
    $(".buyNsell").click(function(){
      var COIN = $(this).attr("data-type");
      if(COIN=="buy"){
        $("#buyBody").show();
        $("#sellBody").hide();
        $(this).addClass("marketActive")
        $(this).siblings().removeClass("sellActive");
      }else{
        $("#sellBody").show();
        $("#buyBody").hide();
        $(this).addClass("sellActive")
        $(this).siblings().removeClass("marketActive");
      }
    });
    //启动线程
//  window.setInterval(function(){
//      $.getScript("/kline/stock_detail_"+window.COIN.toLowerCase()+"_data.json?"+Math.random(),function(){
//          var data=eval("hq_str_"+window.COIN.toLowerCase()).split(",");
//         //现价
//         $("#currPrice").val(data[3]);
//         $("#cny_cny_a_panel").html("￥"+ data[3]);
//         //最近卖五档
//          $("#tmp_depth > li").each(function(){
//             var idx = $(this).index();
//             var $spans = $(this).find("span");
//             var price =0 , qty =0;
//             qty = data[37];
//             price=data[36];
//            var  type = (qty > 0 ?'买':(qty == 0) ?'--':'卖');
//            var  className = (qty > 0 ?'font_buy':(qty == 0) ?' ':'font_sell');
//            $($spans[0]).html(type).attr('class', 'tx_1 '+className);
//             //成交价
//            $($spans[1]).html((price== 0?'--':price)).attr('class', 'tx_2 '+className);
//            //成交量
//            $($spans[2]).html((qty== 0?'--':qty.substr(1))).attr('class', 'tx_3 '+className);
//
//         });
//     }); 
// },1000);


    $("#saleQty").blur(function(){
        var selePrice = $("#currPrice").val();
        var saleQty = $("#saleQty").val();
        var totalSale = selePrice*saleQty;
        $("#totalSale").html(totalSale.toFixed(2));
    });
   
    $("#saleQty").keyup(function(){
        //this.value=this.value.replace(/\D/gi,"");
        if(/^\d+(\.[0-9]{0,2})?$/.test(this.value)==false){
            this.value = this.value.substr(0, this.value.length-1);
        }
    });
    $("#buyQty").blur(function(){
        var buyPrice = $("#currPrice").val();
        var buyQty = $("#buyQty").val();
        var totalBuy = buyPrice*buyQty;
        $("#totalBuy").html(totalBuy.toFixed(2));
    });
    
    $("#buyQty").keyup(function(){
      //this.value=this.value.replace(/\D/gi,"");
      if(/^\d+(\.[0-9]{0,2})?$/.test(this.value)==false){
        this.value = this.value.substr(0, this.value.length-1);
      }
    });
    var saleRuning = true;
    $("#btnSale").click(function(evt){
        evt.preventDefault();
        var pdtCode= window.COIN;
        if(saleRuning==false){
        	 sweetAlert("提示", "正在处理。。请稍候再提交", "error");
             return false;
        }
        saleRuning =false;
        var price =$("#currPrice").val();
        if(price == ""){
            sweetAlert("提示", "请输入卖出价格", "error");
            return false;
        }
        //
        var bankId =$("#bankId").val();
        if(bankId== null ||bankId == ""){
            sweetAlert("提示", "请先选择银行，先添加银行", "error");
            return false;
        }
        var mode="01";
        var qty =$("#saleQty").val();
        if(qty == ""){
            //alert("请输入卖出数量");
            sweetAlert("提示", "请输入卖出数量", "error");
            return false;
        }
        if(parseInt(qty)<50||parseInt(qty)%50 != 0){
            sweetAlert("提示", "买卖50个起挂，且必须是50的倍数", "error");
            return false;
        }
        var captchaCode =$("#saleCaptchaCode").val();
        if(captchaCode == ""){
            //alert("请输入验证码");
            sweetAlert("提示", "请输入验证码", "error");
            return false;
        }
        
        //$.getJSON("/api/webTradeOff/fee.jhtml", function(result){});
        	
       	 var tipQty = parseInt(qty) * 0.1;       
      swal({ 
        title: "扣保证金的提示", 
        text: "将要扣除您"+tipQty+"枚币作为保证金！", 
        type: "warning",
        showCancelButton: true, 
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "同意！继续交易",
        cancelButtonText: "取消交易！",
        closeOnConfirm: false
      },
      function(){
           $.getJSON("/api/webTradeOff/doSell.jhtml",{'pdtCode':pdtCode,'bankId':bankId, 'mode':mode, 'qty':qty, 'price':price, 'flag':'02','vCode':captchaCode},function(ret){
                //refreshCaptchaImage('captchaImgS');
        	   saleRuning = true;
                if(ret.code==0){
                   swal({
                      title: "提示",
                      text: ret.retMsg,
                      type: "success",
                      confirmButtonColor: "#DD6B55",
                      confirmButtonText: "确定",
                      closeOnConfirm: false
                    },
                    function(){
                      location.reload();
                    });
               }else{
                  sweetAlert("提示", ret.retMsg, "error"); 
                }
            });
      });

        
    });
    var buyRuning = true;
    $("#btnBuy").click(function(evt){
        evt.preventDefault();
        if(buyRuning==false){
       	 sweetAlert("提示", "正在处理。。请稍候再提交", "error");
            return false;
       }
        buyRuning =false;
        var pdtCode= window.COIN;
        var price =$("#currPrice").val();
        if(price == ""){
            sweetAlert("提示", "请输入买入价格", "error");
            return false;
        }
        var mode="01";
        var qty =$("#buyQty").val();
        if(qty == ""){
            sweetAlert("提示", "请输入买入数量", "error");
            return false;
        }
        if(parseInt(qty)<50||parseInt(qty)%50 != 0){
            sweetAlert("提示", "买卖50个起挂，且必须是50的倍数", "error");
            return false;
        }
        var captchaCode =$("#buyCaptchaCode").val();
        if(captchaCode == ""){
            //alert("请输入验证码");
            sweetAlert("提示", "请输入验证码", "error");
            return false;
        }
        $.getJSON("/api/webTradeOff/fee.jhtml", function(result){
        	
       	 var tipQty = parseInt(qty) * result.msg;
      swal({ 
        title: "扣保证金的提示", 
        text: "将要扣除您"+tipQty+"枚币作为保证金！", 
        type: "warning",
        showCancelButton: true, 
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "同意！继续交易",
        cancelButtonText: "取消交易！",
        closeOnConfirm: false
      },
      function(){
        $.getJSON("/api/webTradeOff/doBuy.jhtml",{'pdtCode':pdtCode, 'mode':mode, 'qty':qty, 'price':price, 'flag':'01','vCode':captchaCode},function(ret){
        	buyRuning = true;
        		if(ret.code==0){
                    swal({
                      title: "提示",
                      text: ret.retMsg,
                      type: "success",
                      confirmButtonColor: "#DD6B55",
                      confirmButtonText: "确定",
                      closeOnConfirm: false
                    },
                    function(){
                      location.reload();
                    });
                }else{
                    sweetAlert("提示", ret.retMsg, "error");
                }
            });
      });
        });       
    });
    //取消交易
    $(".breakOff").click(function(evt){
      evt.preventDefault();
      //layer.msg("hello word");
      var recordId = $(this).attr("href");
      layer.confirm('您是确认要取消该委托吗？', {
          title:'取消该委托'
        }, function(){
              layer.closeAll(); 
          $.getJSON("/trade/breakOff.jhtml?recordId="+recordId,function(ret){
                  if(ret.code==0){                    
                      swal({
                        title: "提示",
                        text: ret.retMsg,
                        type: "success",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定"
                      },
                      function(){
                        setTimeout(function(){location.reload();},1500);
                      });
                  }else{
                      sweetAlert("提示", ret.msg, "error");
                  }
              });
        });
    });
});