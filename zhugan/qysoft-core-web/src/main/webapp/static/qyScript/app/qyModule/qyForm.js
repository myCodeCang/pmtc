layui.define(['qyWin'],function(exports){ //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);

    var obj = {
    qyajax : function (url,data,callback,errorback, option){
        if(url.indexOf("http:") < 0 ){
             url = CONS_AJAX_URL +  url ;
        }   
        option = option || {};

        var sync = option.sync === true ? false :true; 
        console.log("ajax-url:" + url);
        $.ajax({
            url:url,
            type:'POST', //GET
            async:sync,    //或false,是否异步
            cache:false,
            data:data,
            timeout:5000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            headers: {
		        "X-USER-AGENT-PARAM": "app",
	            "X-USER-AGENT-VALUE": "qysoft",
		    },
            beforeSend:function(xhr){
                if(sync){
                    api.showProgress({
                     modal: false
                    });
                }
            },
            success:function(ret,textStatus){
                
                console.log("ajax-return-success:" +  JSON.stringify(ret));

                 if (ret) {
                    if (ret.status == 1) {
                        //成功返回信息
                        if (callback) {
                            callback(ret);
                        }
                    } else if (ret.status == 0) {
                        api.toast({
                            msg : ret.info,
                            duration : 2000,
                            location : 'middle'
                        });

                        if (errorback) {
                            errorback(ret);
                        }
                    } else if (ret.status == -1) {
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

            },
            error:function(xhr,textStatus){
                 console.log("ajax-return-error:" +  JSON.stringify(textStatus));
                api.toast({
                    msg : "网络错误,请稍候再试!",
                    duration : 2000,
                    location : 'middle'
                });
            },
            complete:function(){
                   api.hideProgress();
            }
        });

    },

    // apicloud 的ajax
     apiajax : function(url, data, callback, errorback,option) {

            option = option || {};

            if(!option.hideProgress){
                 api.showProgress({
                     modal: false
                });
            }
           

            if(url.indexOf("http:") < 0 ){
                 url = CONS_AJAX_URL +  url ;
            }   
             console.log("ajax-url:" +  url);
            api.ajax({
                url : url,
                method : 'post',
                data : {
                    values : data
                },
                headers : {
	            "X-USER-AGENT-PARAM": "app",
	            "X-USER-AGENT-VALUE": "qysoft",
	            },
                dataType:"json"
            }, function(ret, err) {
                console.log("ajax-return-success:" +  JSON.stringify(ret));
                console.log("ajax-return-error:" + JSON.stringify(err));

                if(!option.hideProgress){
                   api.hideProgress();
                }
                
                if (ret) {
                    if (ret.status == 1) {
                    //成功返回信息
                    if (callback) {
                        callback(ret);
                    }
                } else if (ret.status == 0) {
                    api.toast({
                        msg : ret.info,
                        duration : 2000,
                        location : 'middle'
                    });

                    if (errorback) {
                        errorback(ret);
                    }
                } else if (ret.status == -1) {
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
                if(!option.hideProgress){
                   api.hideProgress();
                }
                api.toast({
                    msg : "网络错误,请稍候再试!",
                    duration : 2000,
                    location : 'middle'
                });
            }
        });


        },

        //分页数据查询
        pageajax : function (loadData, param){
            param = param || {};

            api.removeEventListener({
                name: 'scrolltobottom'
            });


            if(loadData){
                loadData(param,function(pageData){
                             //请求结束
                             $(".jiazai-box").empty();
                             param.isloadPage = false;

                             obj.pageajaxEnd(pageData);
                        });
            }

            api.addEventListener({
                name : 'scrolltobottom'
            }, function(ret, err) {

                if(param.isloadPage){
                    return;
                }

                param.isloadPage = true;

                $(".jiazai-box").empty();
                $(".jiazai-box").append('<div class="jiazai-one"><span>上拉加载更多</span><img src="../image/jiazai.gif" /></div>').show();
                
                setTimeout(function(){
                    api.pageDown({bottom:true});

                },200);
                  
  
                setTimeout(function() {

                    ++param.pageNo ;
                    if(loadData){
                        loadData(param,function(pageData){
                             //请求结束
                             $(".jiazai-box").empty();
                             param.isloadPage = false;

                             obj.pageajaxEnd(pageData);
                        });
                       
                    }
                }, 500);
            });

        },

        pageajaxEnd : function(pageData){

            if(pageData.pageNo >=  parseInt((pageData.count + pageData.pageSize -1) / pageData.pageSize)  ){

                api.removeEventListener({
                    name: 'scrolltobottom'
                });
                $(".jiazai-box").empty();
                if(pageData.pageNo > 1){
                    $(".jiazai-box").append('<div class="jiazai-one"><span>已显示全部数据!</span></div>').show();
                   
                }
                       
            }
          
        },


        //获取url请求参数
        GetQueryString : function(name)
        {
             var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
             var r = window.location.search.substr(1).match(reg);
             if(r!=null)return  decodeURIComponent(r[2]); return null;
        }
   

};

  //输出test接口
  exports('qyForm', obj);
});    