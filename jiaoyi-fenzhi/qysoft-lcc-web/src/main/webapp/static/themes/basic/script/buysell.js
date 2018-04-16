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
    window.setInterval(function(){
        // $.getScript("/kline/stock_detail_"+window.COIN.toLowerCase()+"_data.json?"+Math.random(),function(){
        //     //alert(hq_str_yqc);
        //     var data=eval("hq_str_"+window.COIN.toLowerCase()).split(",");
        //     //现价
        //     $("#currPrice").val(data[3]);
        //     $("#cny_cny_a_panel").html("￥"+ data[3]);
        //     //最近卖五档
        //   //最近卖五档
        //     $("#tmp_depth > li").each(function(){
        //        var idx = $(this).index();
        //        var $spans = $(this).find("span");
        //        var price =0 , qty =0;
        //        qty = data[37];
        //        price=data[36];
        //       var  type = (qty > 0 ?'买':(qty == 0) ?'--':'卖');
        //       var  className = (qty > 0 ?'font_buy':(qty == 0) ?' ':'font_sell');
        //       $($spans[0]).html(type).attr('class', 'cell_1 '+className);
        //        //成交价
        //       $($spans[1]).html((price== 0?'--':price)).attr('class', 'cell_2 '+className);
        //       //成交量
        //       $($spans[2]).html((qty== 0?'--':qty.substr(1))).attr('class', 'cell_3 '+className);
        //
        //     });
        // });
       // $.get("/kline/stock_success_"+window.COIN.toLowerCase()+"_data.json?"+Math.random(),function(dat){
       //
       //     // var successData = dat.split('|');
       //     // var strH ="";
       //     // // //最近成交
       //     // for(var i=0;i < successData.length;i++){
       //     //     var _dat = successData[i].split(',');
       //     //     var qty=style="";
       //     //     if(_dat[3]=='B'){
       //     //         style="font_buy";
       //     //         qty =_dat[2] +'↑';
       //     //     }else{
       //     //         style="font_sell";
       //     //         qty =_dat[2] +'↓';
       //     //     }
       //     //     strH += '<li><span>'+_dat[0]+'</span><span class="price">'+ _dat[1] +'</span><span class="'+style+'">'+qty+'</span></li>';
       //     // }
       //     // //alert(successData);
       //     // $("#trade_box").html(strH);
       // });
    },1000);


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
    var buyFlag=false;
    var sellFlag =false;
    $("#btnSale").click(function(evt){
        evt.preventDefault();
        var pdtCode= window.COIN;
        var price =$("#currPrice").val();
        if(price == ""){
            sweetAlert("提示", "请输入卖出价格", "error");
            return false;
        }

        // var bankId =$("#bankId").val();
        // if(bankId== null ||bankId == ""){
        //     sweetAlert("提示", "请先选择银行，先添加银行", "error");
        //     return false;
        // }
        var mode="01";
        var qty =$("#saleQty").val();
        if(qty == ""){
            //alert("请输入卖出数量");
            sweetAlert("提示", "请输入卖出数量", "error");
            return false;
        }
        var transMultiple = parseInt($("#transMultiple").val());
        if(parseInt(qty)<transMultiple||parseInt(qty)%transMultiple != 0){
            sweetAlert("提示", "买卖"+transMultiple+"个起挂，且必须是"+transMultiple+"的倍数", "error");
            return false;
        }
        var captchaCode =$("#saleCaptchaCode").val();
        if(captchaCode == ""){
            sweetAlert("提示", "请输入验证码", "error");
            return false;
        }

	var guaranty =  $("#guaranty").val()
	 var tipQty =(qty * guaranty).toFixed(2);
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
            if (sellFlag){
               return;
            }
            sellFlag = true;
	       $.post("/f/trans/sellaction",{ 'qty':qty,'vCode':captchaCode},function(ret){
	            //refreshCaptchaImage('captchaImgS');
	            if(ret.status==1){
	               swal({
	                  title: "提示",
	                  text: ret.info,
	                  type: "success",
	                  confirmButtonColor: "#DD6B55",
	                  confirmButtonText: "确定",
	                  closeOnConfirm: false
	                },
	                function(){
	                  location.reload();
	                });
	           }else if (ret.status==0){
                    sellFlag=false;
                    sweetAlert("提示", ret.info, "error");
	           }else{
                    swal({
                            title: "提示",
                            text: "登录超时",
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确定",
                            closeOnConfirm: false
                        },
                        function(){
                            location.reload();
                        });
	            }
	        });
		});


        
    });
    
    $("#btnBuy").click(function(evt){
        evt.preventDefault();
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
        var transMultiple = parseInt($("#transMultiple").val());
        if(parseInt(qty)<transMultiple||parseInt(qty)%transMultiple != 0){
            sweetAlert("提示", "买卖"+transMultiple+"个起挂，且必须是"+transMultiple+"的倍数", "error");
            return false;
        }
        var captchaCode =$("#buyCaptchaCode").val();
         if(captchaCode == ""){
             //alert("请输入验证码");
             sweetAlert("提示", "请输入验证码", "error");
             return false;
         }
         //$.getJSON("/api/webTradeOff/fee.jhtml", function(result){
        	// var tipQty = parseInt(qty) * result.msg;
        var guaranty =  $("#guaranty").val();
        var tipQty =(qty * guaranty).toFixed(2);
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
	                if (buyFlag){
                        return;
                    }
                    buyFlag = true;
					$.post("/f/trans/buyaction",{'pdtCode':pdtCode, 'mode':mode, 'qty':qty, 'price':price, 'flag':'01','vCode':captchaCode},function(ret){
			            if(ret.status==1){
			                swal({
			                  title: "提示",
			                  text: ret.info,
			                  type: "success",
			                  confirmButtonColor: "#DD6B55",
			                  confirmButtonText: "确定",
			                  closeOnConfirm: false
			                },
			                function(){
			                  location.reload();
			                });
			            }else if (ret.status==0){
                            buyFlag=false;
                            sweetAlert("提示", ret.info, "error");
                        }else{
                            swal({
                                    title: "提示",
                                    text: "登录超时",
                                    type: "error",
                                    confirmButtonColor: "#DD6B55",
                                    confirmButtonText: "确定",
                                    closeOnConfirm: false
                                },
                                function(){
                                    location.reload();
                                });
                        }
			        });
				});
	        
	    });
   });

