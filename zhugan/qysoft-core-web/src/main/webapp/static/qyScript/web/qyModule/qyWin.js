layui.define(['zepto', 'zeptoCookie', 'layer'], function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var obj = {
        win: function (name, url, optionVal) {
            if(url.indexOf("?") >= 0){
                window.location.href = url+"&t="+Date.parse(new Date());
            }else{
                window.location.href = url+"?t="+Date.parse(new Date());
            }
        },

        back: function (reload) {
            if (reload) {
                history.back();
            } else {
                history.go(-1);
            }
        },

        toast: function (msg, callback) {
            layer.msg(msg, {
                time : 3000
            }, function() {
                if (callback) {
                    callback();
                }
            });
        },
            
        closeWin: function (reload) {
            if (reload) {
                history.back();
            } else {
                history.go(-1);
            }
        },
        
        getPrefs: function(c_name) {
            if (document.cookie.length>0)
            {
                c_start=document.cookie.indexOf(c_name + "=");
                if (c_start!=-1)
                {
                    c_start=c_start + c_name.length+1;
                    c_end=document.cookie.indexOf(";",c_start);
                    if (c_end==-1) c_end=document.cookie.length;
                    return decodeURIComponent (document.cookie.substring(c_start,c_end));
                }
            }
            return ""
        },

        setPrefs: function(c_name, value,expiredays) {
            var exdate=new Date();
            exdate.setDate(exdate.getDate()+expiredays);
            document.cookie=c_name+ "=" +encodeURIComponent (value)+ ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
        },

        showProgress: function() {
            layer.load(1);
        },

        hideProgress: function() {
            layer.close(layer.index);
        },
        timeToDay:function (){
            var timestamp = (new Date()).valueOf();
            return timestamp ;
        },

        pageBackReload: function (callback) {
            var isPageHide = false;
            window.addEventListener('pageshow', function () {
                if (isPageHide) {
                    if(callback){
                        callback();
                    }
                }
            });
            window.addEventListener('pagehide', function () {
                isPageHide = true;
            });
        },
        //开启底部菜单首页
        parseA2OpenWin: function () {
            $(document).on('click', 'a', function (e) {

                var $target = $(e.currentTarget);
                if (isInRouterBlackList($target)) {
                    return;
                }

                e.preventDefault();

                if ($target.hasClass('back')) {
                    history.back();
                } else {

                    var url = $target.attr('href');
                    if (!url || url === '#') {
                        e.preventDefault();
                        return;
                    }

                    var ignoreCache = $target.attr('data-nocache') === 'true';
                    var url = $target.attr("href");

                    var className = "";
                    if ($target.attr('winName')) {
                        className = $target.attr("winName");
                    }
                    else {
                        className = $target.attr("class");
                        if (!className) {
                            $targetParent = $target;
                            for (var i = 0; i < 5; i++) {
                                className = $targetParent.parent().attr('class');
                                $targetParent = $targetParent.parent();

                                if (className) {
                                    break;
                                }
                            }
                        }
                        className = className.replace(/\\s+/g, "-");
                    }

                    //obj.frameWin(className,"字画",url,{},{});
                    obj.win(className, url, {});
                }
            });
        },
    };

    /**
     * 判断一个链接是否使用 router 来处理
     *
     * @param $link
     * @returns {boolean}
     */
    function isInRouterBlackList($link) {

        var classBlackList = [
            'external',
            'tab-link',
            'open-popup',
            'close-popup',
            'open-panel',
            'close-panel'
        ];

        for (var i = classBlackList.length - 1; i >= 0; i--) {
            if ($link.hasClass(classBlackList[i])) {
                return true;
            }
        }

        var linkEle = $link.get(0);
        var linkHref = linkEle.getAttribute('href');
        var protoWhiteList = [
            'http',
            'https',
            'widget'
        ];

        //如果非noscheme形式的链接，且协议不是http(s)，那么路由不会处理这类链接
        if (/^(\w+):/.test(linkHref) && protoWhiteList.indexOf(RegExp.$1) < 0) {
            return true;
        }

        //noinspection RedundantIfStatementJS
        if (linkEle.hasAttribute('external')) {
            return true;
        }

        return false;
    }
    // 默认转化所有a标签
    obj.parseA2OpenWin();


    //输出test接口
    exports('qyWin', obj);
});    