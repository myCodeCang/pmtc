/**
 * 
 */
$(function(){
    window.setInterval(function(){
        $.getScript("/kline/stock_detail_"+window.COIN.toLowerCase()+"_data.json?"+Math.random(),function(){
            //alert(hq_str_yqc);
            var data=eval("hq_str_"+window.COIN.toLowerCase()).split(",");
            //(当前价-昨收价)/昨收价
            var range=(data[3]-data[2])/data[2];

            if(data[3]==0){
                range=0;
            }
            range=(range*100).toFixed(2);
            var font_arr = (range>0?'font_up':'font_down')

            //现价
            $("#now_price").html("￥"+ data[3]);
            //最低价
            $("#now_low").html(data[34]);
            //最高价
            $("#now_high").html(data[35]);
            // //涨幅
            // $(overview[3]).children().html(range+"%");
            //24H成交量
            $("#now_total").html(data[8]);
            //最近买卖
            var tIdx = 5;
            // var shuLis =buyLis =sellLis = "";

            // for(var i=1;i<=5;i++){
            //     shuLis += "<li>"+ (i+1) +"</li>";
            //     var buyQty=parseInt( data[8+i*2]);
            //     var buyPrice=data[9+2*i];
            //     buyLis += '<li><span class="total">'+buyQty+'</span><span class="number">'+buyQty+'</span><span class="price">'+buyPrice+'</span><span class="tiao" style="width:'+buyQty+'%"></span></li>';
            //     var sellQty=parseInt( data[18+i*2]);
            //     var sellPrice=data[19+2*i];
            //     sellLis+= '<li><span class="price">'+sellPrice+'</span><span class="number">'+sellQty+'</span><span class="total">'+sellQty+'</span><span class="tiao" style="width:'+sellQty+'"></span></li>';
            // }

        });

    },1000);
});