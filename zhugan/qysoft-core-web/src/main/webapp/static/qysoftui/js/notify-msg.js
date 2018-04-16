/**
 * Admui v1.0.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
$(function () {
    'use strict';

    /**
     * 消息通知
     */
    var notifyMsg = {
        init: function () {
            var $msgDom = $('#admui-navbarMessage').find('span.msg-num'),
                msgNumber;

            $.ajax({
                url: $.ctx + '/message/queryUnreadCount',
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        msgNumber = data.count === 0 ? '' : data.count;
                        $msgDom.text(msgNumber);
                    } else {
                        toastr.error('出错了，请重试！');
                    }
                },
                error: function () {
                    toastr.error('服务器异常，请稍后再试！');
                }
            });

            var socket = new WebSocket('ws://' + window.location.host + $.ctx + '/socket');

            socket.onopen = function () {

                socket.onmessage = function (event, data) {
                    var html = template('admui-messageTpl', [data]),
                        $adminMsg = $('#admui-messageContent');
                    msgNumber++;
                    $msgDom.text(msgNumber);

                    if ($('#accountContent').size() > 0) {
                        $(".tabList>li.news span").text(msgNumber);
                        if ($('[data-pjax="#accountContent"]').closest('.new').is('.active') && $('#paging').data('page') === 1) {
                            $adminMsg.append(html);
                        }
                    }
                };

                socket.onclose = function (event) {
                    toastr.info('消息通知服务已关闭', event);
                };
            };
        },
        render: function () {
            var self = this;
            template.helper('iconType', function (type) {
                switch (type) {
                    case 'SYSTEM':
                        return 'fa-desktop system';
                    case 'TASK':
                        return 'fa-tasks task';
                    case 'SETTING':
                        return 'fa-cog setting';
                    case 'EVENT':
                        return 'fa-calendar event';
                    default:
                        return 'fa-comment-o other';
                }
            });
            template.helper('timeMsg', function (date) {
                var msgTime, arr,
                    currentTime = new Date();

                // ios new Data兼容
                arr = date.split(/[- : \/]/);
                msgTime = new Date(arr[0], arr[1] - 1, arr[2], arr[3], arr[4], arr[5]);

                return self.timeDistance(msgTime, currentTime);

            });
        },
        loading: function () {
            var html;

            this.render();
            $.ajax({
                url: $.ctx + '/message/queryUnread',
                type: 'GET',
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        if (data.msgList.length === 0) {
                            return;
                        }

                        html = template('admui-messageTpl', data);
                        $('#admui-messageContent').html(html);

                    } else {
                        toastr.error('出错了，请重试！');
                    }
                },
                error: function () {
                    toastr.error('服务器异常，请稍后再试！');
                }
            });

        },
        timeDistance: function (reference, current) {
            var distance;

            for (var i = 0; i < 6; i++) {
                switch (i) {
                    case 0:
                        distance = current.getFullYear() - reference.getFullYear();
                        if (distance !== 0) {
                            return distance + '年前';
                        }
                        break;
                    case 1:
                        distance = current.getMonth() - reference.getMonth();
                        if (distance !== 0) {
                            return distance + '月前';
                        }
                        break;
                    case 2:
                        distance = current.getDate() - reference.getDate();
                        if (distance !== 0) {
                            return distance + '天前';
                        }
                        break;
                    case 3:
                        distance = current.getHours() - reference.getHours();
                        if (distance !== 0) {
                            return distance + '小时前';
                        }
                        break;
                    case 4:
                        distance = current.getMinutes() - reference.getMinutes();
                        if (distance !== 0) {
                            return distance + '分钟前';
                        }
                        break;
                    case 5:
                        distance = current.getSeconds() - reference.getSeconds();
                        if (distance !== 0) {
                            return distance + '秒前';
                        }
                        break;
                }
            }
        }
    };

    notifyMsg.init();

    // 获取消息
    $('#admui-navbarMessage > .msg-btn').on('click', function () {
        notifyMsg.loading();
    });
});