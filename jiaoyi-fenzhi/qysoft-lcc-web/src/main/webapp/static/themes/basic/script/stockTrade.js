$(document).ready(function(){
	$("#tmp_depth").on('click',"li", function(){ 
            var oul =  $(this).parent();
            // var price = $(this).find("span.cell_2").html();
            // price =  price.replace("￥","").replace("&nbsp;&nbsp;","");
            var price = $(this).attr("data-price");
            var qty = $(this).find("span.cell_3").html();
            var totPrice = 0;
            
            if (isNaN(price)===false && isNaN(qty) ===false){
                totPrice = Number(price)*Number(qty);
            }
            if(oul.hasClass("sell_5")){
                $("#buyQty").val(qty);
                $("#buyPrice").val(price);
                $("#totalBuy").html(totPrice.toFixed(2));
            }
            if(oul.hasClass("buy_5")){
                $("#saleQty").val(qty);
                $("#salePrice").val(price);
                $("#totalSale").html(totPrice.toFixed(2));
            }
        }); 
	$("#salePrice, #saleQty").blur(function(){
		var selePrice = $("#salePrice").val();
		var saleQty = $("#saleQty").val();
		var totalSale = selePrice*saleQty;
		$("#totalSale").html(totalSale.toFixed(2));
	});
	$("#salePrice").keyup(function(){
		if(/^\d+(\.[0-9]{0,2})?$/.test(this.value)==false){
			this.value = this.value.substr(0, this.value.length-1);
		}
	});
	$("#saleQty").keyup(function(){ this.value=this.value.replace(/\D/gi,"");});
	$("#buyPrice, #buyQty").blur(function(){
		var buyPrice = $("#buyPrice").val();
		var buyQty = $("#buyQty").val();
		var totalBuy = buyPrice*buyQty;
		$("#totalBuy").html(totalBuy.toFixed(2));
	});
	$("#buyPrice").keyup(function(){
		//this.value=this.value.replace(/\D/gi,"");}
		if(/^\d+(\.[0-9]{0,2})?$/.test(this.value)==false){
			this.value = this.value.substr(0, this.value.length-1);
		}
	});
	$("#buyQty").keyup(function(){ this.value=this.value.replace(/\D/gi,"");});
	$("#btnSale").click(function(evt){
		evt.preventDefault();
		var price =$("#salePrice").val();
		if(price == ""){
			alert("请输入卖出价格");
			return false;
		}
		var mode="01";
		var qty =$("#saleQty").val();
		if(qty == ""){
			alert("请输入卖出数量");
			return false;
		}
		var captchaCode =$("#saleCaptchaCode").val();
		if(captchaCode == ""){
			alert("请输入验证码");
			return false;
		}
		//alert(pdtCode);
		AccountTradeAjax.handleTrade(pdtCode, mode, qty, price, "02", captchaCode, function(ret){
			//if(ret.code==0){setTimeout(function(){location.reload();},2000);}
			//$("#saleAlert").html(ret.retMsg).show();
			alert(ret.retMsg);
			refreshCaptchaImage('captchaImgS');
			if(ret.code==0){
				$("#sellModal").modal("hide");
			}
		});
	});
	
	$("#btnBuy").click(function(evt){
		evt.preventDefault();
		var pdtCode= window.pdtCode;
		var price =$("#buyPrice").val();
		if(price == ""){
			alert("请输入买入价格");
			return false;
		}
		var mode="01";
		var qty =$("#buyQty").val();
		if(qty == ""){
			alert("请输入买入数量");
			return false;
		}
		var captchaCode =$("#buyCaptchaCode").val();
		if(captchaCode == ""){
			alert("请输入验证码");
			return false;
		}
		AccountTradeAjax.handleTrade(pdtCode, mode, qty, price, "01", captchaCode, function(ret){
			alert(ret.retMsg);
			refreshCaptchaImage('captchaImgB');
			if(ret.code==0){
				//alert(33);
				$("#buyModal").modal("hide");
			}
			//$("#buyAlert").html(ret.retMsg).show();
		});
	});
});