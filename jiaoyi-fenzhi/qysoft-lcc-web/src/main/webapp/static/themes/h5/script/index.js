
/**
 * 
 */
$(function(){
    window.setInterval(function(){
       //$("#head_notice").html(window.COIN);
        $.getScript("/kline/stock_detail_"+window.COIN.toLowerCase()+"_data.json?"+Math.random(),function(){
            var data=eval("hq_str_"+window.COIN.toLowerCase()).split(",");
            
            //   tmp_overview
            var overview = $("#tmp_overview > li");
            //现价  
            $("#x-currPrice").html("￥"+ data[3]);
            //最低价
            $("#x-lowPrice").html(data[34]);
            //最高价
            $("#x-topPrice").html(data[35]);
           
            //24H成交量
            $("#x-24hPrice").html(data[8]);
            //最近买卖 
            $("#x-sellPrice").html(data[21]);
            $("#x-sellQty").html(data[20]);
            $("#x-buyPrice").html(data[11]);
            $("#x-buyQty").html(data[10]);

var aqty = data[37];
var aprice = data[36];
var  atype = (aqty > 0 ?'买':(aqty == 0) ?'--':'卖');
$("#x-AType_0").html(atype +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//成交价
$("#x-APrice_0").html((aprice== 0?'--':aprice));
//成交量
$("#x-AQty_0").html((aqty== 0?'--':aqty.substr(1)));

			//
            for(var idx =0; idx <10; idx++ ){
                var price =0 , qty =0;
                qty = data[10+idx*2];
                price=data[11+2*idx];
               var  type = (qty > 0 ?'买'+(idx+1):(qty == 0) ?'--':'卖'+(idx+1));
               var  className = (qty > 0 ?'font_buy':(qty == 0) ?' ':'font_sell');
               $("#x-Type_" +idx).html(type +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").attr('class', className);
                //成交价
               $("#x-Price_"+idx).html((price== 0?'--':price)).attr('class', className);
               //成交量
               $("#x-Qty_"+idx).html((qty== 0?'--':qty.substr(1))).attr('class',className);

            }
            
        });
        
        //
        $.get("/kline/stock_success_"+window.COIN.toLowerCase()+"_data.json?"+Math.random(),function(dat){
        	if(dat =="") return false;
        	var successData = dat.split('|');
        	
        	//最近成交
        	 for(var idx =0; idx <10; idx++ ){
        		 if(idx < successData.length){
	        		 var _dat = successData[idx].split(',');
	        		 $("#x-TTime_" +idx).html(_dat[0]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	        		 $("#x-TPrice_" +idx).html(_dat[1]);
	        		 $("#x-TQty_" +idx).html(_dat[2]);
        		 }else{
        			 $("#x-TTime_" +idx).html("--"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	        		 $("#x-TPrice_" +idx).html('--');
	        		 $("#x-TQty_" +idx).html('--');
        		 }
        	 }

        },"text");
    },1000);

});
