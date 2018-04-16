/**
 * Admui v1.0.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
	'use strict';

	var $content = $("#admui-pageContent");

	/* 公用模块对象 */
	$.app = $.app = {};

	$.extend($.app, {
		_queue: {
			prepare: [],
			run: [],
			complete: []
		},
		run: function () {
			var self = this;
			this.dequeue('prepare', function () {
				self.trigger('before.run', self);
			});

			this.dequeue('run', function () {
				self.dequeue('complete', function () {
					self.trigger('after.run', self);
				});
			});
		},
		dequeue: function (name, done) { // 队列当前状态离队，进行下一步操作
			var self = this,
				queue = this.getQueue(name),
				fn = queue.shift(),
				next = function () {
					self.dequeue(name, done);
				};

			if (fn) {
				fn.call(this, next);
			} else if ($.isFunction(done)) {
				done.call(this);
			}
		},
		getQueue: function (name) { // 获取队列状态信息
			if (!$.isArray(this._queue[name])) {
				this._queue[name] = [];
			}

			return this._queue[name];
		},
		extend: function (obj) { // 公用模块对象扩展方法
			$.each(this._queue, function (name, queue) {
				if ($.isFunction(obj[name])) {
					queue.push(obj[name]);

					delete obj[name];
				}
			});
			$.extend(this, obj);
			return this;
		},
		trigger: function (name, data, $el) { // 离队状态执行动作

			if (typeof name === 'undefined') {
			    return;
            }
			if (typeof $el === 'undefined') {
			    $el = $content;
            }

			$el.trigger(name + '.app', data);
		}
	});

	window.App = $.app.extend({
		handleSlidePanel: function () {
			if (typeof $.slidePanel === 'undefined') {
			    return;
            }

			$content.on('click', '[data-toggle=slidePanel]', function (e) {
				$.slidePanel.show({
					url: $(this).data('url'),
					settings: {
						cache: false
					}
				}, $.po('slidePanel', {
                    template: function (options) {
                        return '<div class="' + options.classes.base + ' ' + options.classes.base + '-' + options.direction + '">' +
                            '<div class="' + options.classes.base + '-scrollable"><div>' +
                            '<div class="' + options.classes.content + '"></div>' +
                            '</div></div>' +
                            '<div class="' + options.classes.base + '-handler"></div>' +
                            '</div>';
                    },
                    afterLoad: function () {
                        this.$panel.find('.' + this.options.classes.base + '-scrollable')
                            .slimScroll($.po('slimScroll'));
                    }
                }));

				e.stopPropagation();
			});
		},
		handleMultiSelect: function () {
			var $all = $('.select-all');

			$content.on('change', '.multi-select', function (e, isSelectAll) {
				if (isSelectAll) {
				    return;
                }

				var $select = $('.multi-select'),
					total = $select.length,
					checked = $select.find('input:checked').length;
				if (total === checked) {
					$all.find('input').prop('checked', true);
				} else {
					$all.find('input').prop('checked', false);
				}
			});

			$all.on('change', function () {
				var checked = $(this).find('input').prop('checked');

				$('.multi-select input').each(function () {
					$(this).prop('checked', checked).trigger('change', [true]);
				});

			});
		},
		handleListActions: function () { // 操作主体部分，左侧菜单编辑
			$content.on('keydown', '.list-editable [data-bind]', function (event) {
				var keycode = (event.keyCode ? event.keyCode : event.which);

				if (keycode === 13 || keycode === 27) {
					var $input = $(this),
						bind = $input.data('bind'),
						$list = $input.parents('.list-group-item'),
						$content = $list.find('.list-content'),
						$editable = $list.find('.list-editable'),
						$update = bind ? $list.find(bind) : $list.find('.list-text');

					if (keycode === 13) {
						$update.html($input.val());
					} else {
						$input.val($update.text());
					}

					$content.show();
					$editable.hide();
				}
			});

			$content.on('click', '[data-toggle="list-editable-close"]', function () {
				var $btn = $(this),
					$list = $btn.parents('.list-group-item'),
					$content = $list.find('.list-content'),
					$editable = $list.find('.list-editable');

				$content.show();
				$editable.hide();
			});
		},
		pageAside: function(){
			var pageAside = $(".page-aside"),
				isOpen = pageAside.hasClass('open');

			pageAside.toggleClass('open', !isOpen);
		},
		run: function (next) {
			var self = this;

			// 小屏下侧边栏滚动
			$('#admui-pageContent').on('click', '.page-aside-switch', function (e) {
				self.pageAside();
                e.stopPropagation();
			});

            next();
		},
		//通用ajax
		ajax:function(url,dataParam,succCallback,errorCallback,param){
            var self = this;
            var param = param||{};

            $.ajax({
                url: url,
                type: 'POST',
                data: dataParam,
                dataType: 'JSON',
                success: function (data) {
                    if (data.status == 1) {
                    	if(succCallback){
                    		succCallback(data);
						}
                    } else {
                        toastr.error(data.info);
                        if(errorCallback){
                            errorCallback(data);
                        }
                    }
                },
                error: function (data) {
                    toastr.error('服务器繁忙，请刷新重试!');
                    setTimeout(function(){
                    	location.reload();
					},2000);
                    if(errorCallback){
                        errorCallback(data);
                    }
                }
            });
		},

        kyajax:function(url,dataParam,succCallback,errorCallback,param){
            var self = this;
            var param = param||{};
            var sync = param.sync === true ? false :true;
            console.log("ajax-url:" + url);
            $.ajax({
                url: url,
                async:sync,    //或false,是否异步
                type: 'POST',
                data: dataParam,
                dataType: 'JSON',
                timeout:5000,
                crossDomain: true,//请求偏向外域
                xhrFields: {
                    withCredentials: true
                },

                success: function (data) {
                    if (data.status == 1) {
                        if(succCallback){
                            succCallback(data);
                        }
                    } else {

                        toastr.error(data.info);
                        if(errorCallback){
                            errorCallback(data);
                        }
                    }
                },
                error: function (data) {
                    toastr.error('服务器异常，请稍后再试!');
                    if(errorCallback){
                        errorCallback(data);
                    }
                }
            });
        },
        getDictLabel:function (dic,value,labelId, labelVal) {
			for(var i=0;i<dic.length;i++){
				if(dic[i][labelId] == value){
					return dic[i][labelVal];
				}
			}

			return "-";
        }
	});
})
(document, window, jQuery);

(function($) {
    $.fn.serializeJSON = function(options) {
        var opts = $.extend({}, $.fn.serializeJSON.defaults, options);
        var toArray = function(value,char){
            return value.split(char);
        }
        var formData = this.serialize();
        var params = toArray(formData,"&");
        var resultJSON={};
        var connecter = function(name,source,value){
            if(!value || value.length<1)return source;
            var char = opts.connectName[name]?opts.connectName[name]:opts.connect;
            return source+char+value;
        }
        $.each(params,function(){
            var param = toArray(this,"=");
            var attr = param[0];
            var val = param[1];
            if(resultJSON[attr]){//multi
                resultJSON[attr] = connecter(attr,resultJSON[attr],val);
            }else{
                resultJSON[attr] = val;
            }
        })
        return resultJSON;
    };
    $.fn.serializeJSON.defaults = {
        connectName:{},
        connect:","
    };
})(jQuery);