/**
 * Admui v1.0.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    $.configs = $.configs || {}; // 配置基本信息

    $.leavePage = null; // 离开页面方法
    
    $.ctx =$('#admui-signOut').data('ctx') || '';

    $.extend($.configs, {
        _data: {},
        get: function (name) {
            var data = this._data;

            for (var i = 0; i < arguments.length; i++) {
                name = arguments[i];

                data = data[name];
            }

            return data;
        },
        set: function (name, value) {
            this._data[name] = value;
        },
        extend: function (name, options) {
            var value = this.get(name);
            return $.extend(true, value, options);
        }
    });

    $.colors = function (name, level) { // 获取颜色配置信息
        if (!$.configs.colors && typeof $.configs.colors[name] === 'undefined') {
            return null;
        }

        if (level && typeof $.configs.colors[name][level] !== 'undefined') {
            return $.configs.colors[name][level];
        }else{
            return $.configs.colors[name];
        }
    };

    $.po = function (name, options) { // 3rd调用参数
        var defaults = $.components.getDefaults(name);
        return $.extend(true, {}, defaults, options);
    };

    $.components = $.components || {}; // 实现插件的提前检测和调用

    $.extend($.components, {
        _components: {},
        register: function (name, obj) {
            this._components[name] = obj;
        },
        init: function (args, name,context) {
            var self = this, obj;
                args =  args || true;

            if (typeof name === 'undefined') {
                $.each(this._components, function (name) {
                    self.init(args, name);
                });
            } else {
                context = context || document;

                obj = this.get(name);

                if (!obj) {
                    return;
                }

                switch (obj.mode) {
                    case 'default':
                        return this._initDefault(name, context);
                    case 'init':
                        return this._initComponent(obj, context);
                    case 'api':
                        return this._initApi(obj, context,args);
                    default:
                        this._initApi(obj, context,args);
                        this._initComponent(obj, context);
                        return;
                }
            }
        },
        _initDefault: function (name, context) { // jquery 3rd的基本用法
            if (!$.fn[name]) {
                return;
            }

            var defaults = this.getDefaults(name);

            $('[data-plugin=' + name + ']', context).each(function () {
                var $this = $(this),
                    options = $.extend(true, {}, defaults, $this.data());

                $this[name](options);
            });
        },
        _initComponent: function (obj, context) { // jquery 3rd的高级用法
            if ($.isFunction(obj.init)) {
                obj.init.call(obj, context);
            }
        },
        _initApi: function (obj, context, args) { // 其他处理
            if (args && $.isFunction(obj.api)) {
                obj.api.call(obj, context);
            }
        },
        getDefaults: function (name) {
            var component = this.get(name);

            return component && typeof component.defaults !== "undefined" ? component.defaults : {};
        },
        get: function (name) {
            if (typeof this._components[name] !== "undefined") {
                return this._components[name];
            } else {
                console.error('component:' + name + ' 脚本文件没有注册任何信息！');
                return undefined;
            }
        }
    });

})(window, document, jQuery);
