layui.define(['zepto', 'api'], function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);

    var obj = {
        // 打开frame
        frame: function (name, url, rect, optionVal) {
            var option = {};

            option.name = name;
            option.url = url;
            option.rect = rect;
            option.headers = {
                "X-USER-AGENT-PARAM": "app",
                "X-USER-AGENT-VALUE": "qysoft",
            };
            option.vScrollBarEnabled = false;
            option.hScrollBarEnabled = false;
            option.progress = {
                type: "default",                //加载进度效果类型，默认值为default，取值范围为default|page，default等同于showProgress参数效果；为page时，进度效果为仿浏览器类型，固定在页面的顶部
                title: "",               //type为default时显示的加载框标题
                text: "",                //type为default时显示的加载框内容
                color: ""                //type为page时进度条的颜色，默认值为#45C01A，支持#FFF，#FFFFFF，rgb(255,255,255)，rgba(255,255,255,1.0)等格式
            };

            var param = api.pageParam;
            if (param.lastWin) {
                var lastWin = {
                    "winName": param.lastWin.winName,
                    "frameName": param.lastWin.frameName
                }
                option.pageParam = {
                    "lastWin": lastWin
                }

                console.log(JSON.stringify(lastWin));
            }


            $.extend(option, optionVal);

            api.openFrame(option);


        },

        win: function (name, url, optionVal) {

            var option = {};

            option.name = name;
            option.url = url;
            option.headers = {
                "X-USER-AGENT-PARAM": "app",
                "X-USER-AGENT-VALUE": "qysoft",
            };
            option.vScrollBarEnabled = false;
            option.hScrollBarEnabled = false;
            option.progress = {
                type: "default",                //加载进度效果类型，默认值为default，取值范围为default|page，default等同于showProgress参数效果；为page时，进度效果为仿浏览器类型，固定在页面的顶部
                title: "",               //type为default时显示的加载框标题
                text: "",                //type为default时显示的加载框内容
                color: ""                //type为page时进度条的颜色，默认值为#45C01A，支持#FFF，#FFFFFF，rgb(255,255,255)，rgba(255,255,255,1.0)等格式
            };

            var lastWin = {
                "winName": api.winName,
                "frameName": api.frameName
            }
            option.pageParam = {
                "lastWin": lastWin
            }
            console.log(JSON.stringify(lastWin));
            $.extend(option, optionVal);

            api.openWin(option);


        },

        //关闭页面, 传true,则会刷新上个界面, 上个界面需要重写方法 reload()
        closeWin: function (reload) {

            if (reload) {
                var param = api.pageParam;
                var jsfun = 'if(typeof(reload)==="function"){ reload(); }else{ location.reload(); }';
                api.execScript({
                    name: param.lastWin.winName,
                    frameName: param.lastWin.frameName,
                    script: jsfun
                });
                setTimeout(function () {
                    api.closeWin();
                }, 100);
            }
            else {
                api.closeWin();
            }
        },


        // optionVal = {
        //   param1 : "" ,
        //   param2 : "" ,
        //   param3 : "" ,
        //   frmoption:{
        //
        //   }
        // }
        //
        frameWin: function (name, title, url, winoption, optionVal) {

            var option = {};
            winoption = winoption || {};
            optionVal = optionVal || {};

            optionVal.name = name + "_frm";
            optionVal.url = url;
            optionVal.title = title;


            option.name = name;
            option.url = "./common/titleWin.html";
            option.headers = {
                "X-USER-AGENT-PARAM": "app",
                "X-USER-AGENT-VALUE": "qysoft",
            };
            option.vScrollBarEnabled = false;
            option.hScrollBarEnabled = false;
            option.progress = {
                type: "default",                //加载进度效果类型，默认值为default，取值范围为default|page，default等同于showProgress参数效果；为page时，进度效果为仿浏览器类型，固定在页面的顶部
                title: "",               //type为default时显示的加载框标题
                text: "",                //type为default时显示的加载框内容
                color: ""                //type为page时进度条的颜色，默认值为#45C01A，支持#FFF，#FFFFFF，rgb(255,255,255)，rgba(255,255,255,1.0)等格式
            };
            var lastWin = {
                "winName": api.winName,
                "frameName": api.frameName
            }
            optionVal.lastWin = lastWin;

            option.pageParam = optionVal;
            $.extend(option, winoption);


            api.openWin(option);
        },

        hideProgress: function () {
            api.hideProgress();
        },

        toast: function (msg) {

            api.toast({
                msg: msg,
                duration: 3000,
                location: 'middle'
            });
        },
        //设置偏好数据
        setPrefs: function (name, value) {
            api.setPrefs({
                key: name,
                value: value
            });

        },
        //获取偏好设置值
        getPrefs: function (name) {
            var value = api.getPrefs({
                sync: true,
                key: name
            });

            return value;
        },

        //开启底部菜单首页
        createMainWin: function (name, option) {
            if (option.frameType) {
                obj.frame(name, BATH_PATH + '../html/common/mainWin.html', {}, {"pageParam": option});

            }
            else {
                obj.win(name, BATH_PATH + '../html/common/mainWin.html', {"pageParam": option});
            }

        },
        //适配iOS7+、Android4.4+系统状态栏，为传入的DOM元素增加适当的上内边距，避免header与状态栏重叠
        fixStatusBar: function (header) {
            var header = document.querySelector('#' + header);
            $api.fixStatusBar(header);
        },

        //开启底部菜单首页
        parseA2OpenWin: function () {
            $(document).off('click', 'a');
            $(document).on('click', 'a', function (e) {
                var $target = $(e.currentTarget);

                if (isInRouterBlackList($target)) {
                    return;
                }
                e.preventDefault();
                if ($target.hasClass('back')) {
                    obj.closeWin(false);
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
                        className = className.replace(/\r\n/ig, "_");
                    }

                    //obj.frameWin(className,"字画",url,{},{});
                    obj.win(className, url, {});
                }


            });
        },

        setRefreshHeaderInfo: function (callback) {
            api.setRefreshHeaderInfo({
                bgColor: '#ccc',
                textColor: '#fff',
                textDown: '下拉刷新...',
                textUp: '松开刷新...'
            }, function (ret, err) {
                callback();
                setTimeout(function () {
                    api.refreshHeaderLoadDone();
                }, 1000);

            });
        },
        setRefreshHeaderAUI: function (contain, callback) {
            layui.use(['aui-pull-refresh'], function () {

                var pullRefresh = new auiPullToRefresh({
                    container: contain,
                    triggerDistance: 100
                }, function (ret) {
                    if (ret.status == "success") {

                        setTimeout(function () {
                            callback();
                            pullRefresh.cancelLoading(); //刷新成功后调用此方法隐藏
                        }, 1000)
                    }
                })
            });
        }
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