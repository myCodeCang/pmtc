layui.define(['layer', 'zepto', 'pageRefresh'], function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var layer = layui.layer;
    var obj = {
        //jquery的ajax
        qyajax: function (url, data, callback, errorback, option) {
            if(url.indexOf("http:") < 0 ){
                url = CONS_AJAX_URL +  url ;
            }
            option = option || {};
            var index = 0;
            var sync = option.sync === true ? false : true;
            var time = 5000;
            if(option.time){
                time=option.time;
            }
            console.log("ajax-url:" + url);
            $.ajax({
                url: url,
                type: 'POST', //GET
                async: sync,    //或false,是否异步
                cache: false,
                data: data,
                crossDomain: true,//请求偏向外域
                xhrFields: {
                    withCredentials: true
                },
                timeout: time,    //超时时间
                dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                /*headers: {
                 "X-USER-AGENT-PARAM": "app",
                 "X-USER-AGENT-VALUE": "qysoft",
                 "HTTP_USER_AGENT": "CK 2.0"
                 },*/
                /*beforeSend: function (xhr) {
                 xhr.setRequestHeader("IS_WEB", "QYSOFT");
                 },*/
                success: function (ret, textStatus) {
                    console.log("ajax-return-success:" + JSON.stringify(ret));

                    if (ret) {
                        if (ret.status == 1) {
                            //成功返回信息
                            if (callback) {
                                callback(ret);
                            }
                        } else if (ret.status == 0) {
                            layer.msg(ret.info);
                            if (errorback) {
                                errorback(ret);
                            }
                        } else if (ret.status == -1) {
                            layer.msg('请重新登录!');
                            if (errorback) {
                                errorback(ret);
                            }

                        } else {
                            layer.msg('发生位置错误,请稍候再试!');
                        }
                    } else {
                        layer.msg('网络繁忙,请稍候再试!');
                    }

                },
                error: function (xhr, textStatus) {
                    console.log("ajax-return-error:" + JSON.stringify(textStatus));
                    if (errorback) {
                        errorback("error");
                    }else{
                        layer.msg('网络繁忙,请重新登陆!');
                       }
                },
                complete: function () {
                    layer.close(index);
                }
            });

        },

        //分页数据查询
        pageajax: function (loadData, param, container) {
            param = param || {};

            $('#' + container).dropload({
                scrollArea: window,
                autoLoad: true,
                domDown: {
                    domClass: '',
                    domRefresh: '',
                    domLoad: '',
                    domNoData: ''
                },
                loadDownFn: function (me) {
                    $(".jiazai-box").empty();
                    $(".jiazai-box").append('<div class="jiazai-one"><span>上拉加载更多</span><img src="../image/jiazai.gif" /></div>').show();

                    setTimeout(function () {
                        ++param.pageNo;
                        if (loadData) {
                            loadData(param, function (pageData) {
                                //请求结束
                                $(".jiazai-box").empty();

                                obj.pageajaxEnd(pageData, me);
                                me.resetload();
                            });
                        }
                    }, 500);
                },
                distance: 50,
                threshold: 50
            });

        },

        pageajaxEnd: function (pageData, me) {
            if (pageData.pageNo >= parseInt((pageData.count + pageData.pageSize - 1) / pageData.pageSize)) {
                $(".jiazai-box").empty();
                if (pageData.pageNo >= 1) {
                    if (me) {
                        me.lock();
                        me.noData();
                    }
                    $(".jiazai-box").append('<div class="jiazai-one"><span>已显示全部数据!</span></div>').show();
                }
            }
        },

        //获取url请求参数
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURIComponent(r[2]);
            return null;
        }
    };

    //输出test接口
    exports('qyForm', obj);
});    