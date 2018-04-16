/**
 * Admui v1.0.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    window.Content = {
        loadMsg: function (page) {
            var html, self = this;

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

            $.ajax({
                url: $.ctx + '/message/query?page=' + page,
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        if (data.msgList.length === 0) {
                            return;
                        }

                        var msgNumber = $('#admui-navbarMessage').find('span.msg-num').text();

                        // 显示未读消息
                        $(".tabList>li.news span").text(msgNumber);

                        html = template('newMessge', data);
                        $('#messageLists').html(html);
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
        },
        run: function () {

            this.loadMsg('');
            var self = this,
                $pageContent = $('#admui-pageContent'),
                $news = $(".tabList>li.news span"),
                $navNews = $('#admui-navbarMessage').find('span.msg-num');

            function readNews($element) {  //读取消息
                var $readTab = $element.parents(".media").children(".media-right").find("a"),
                    $title = $element.parents(".media").children(".media-body").find("i"),
                    ID = $element.parents(".list-group-item").attr('data-id'),
                    msgsNum = Number($news.text());

                $.ajax({
                    url: $.ctx + '/message/read',
                    type: 'POST',
                    data: {messageId: ID},
                    dataType: 'JSON',
                    success: function (data) {
                        if (data.success) {
                            $title.remove();
                            $readTab.attr("title", "删除");
                            $readTab.removeClass("wb-check").addClass("wb-close");

                            msgsNum -= 1;
                            msgsNum = msgsNum === 0 ? '' : msgsNum;

                            $news.text(msgsNum);
                            $navNews.text(msgsNum);
                        }
                        else {
                            toastr.error('出错了，请重试！');
                        }
                    },
                    error: function () {
                        toastr.error('服务器异常，请稍后再试！');
                    }
                });
            }

            $pageContent.on("click", ".news-list", function () { // 查看未读消息
                if ($(this).find("i.icon").size() > 0) {
                    readNews($(this));
                }
            });

            $pageContent.on("click", ".wb-check", function () { // 直接更改状态为已读
                readNews($(this));
            });

            $pageContent.on("click", ".wb-close", function () { // 删除已读消息
                var $item = $(this).closest(".list-group-item"),
                    $total = $('.msg-number'),
                    total = $total.text(),
                    page = $('#paging').data('page'),
                    ID = $item.attr('data-id');

                alertify.theme('bootstrap')
                    .confirm("你确定要删除吗？", function () {

                        $.ajax({
                            url: $.ctx + '/message/delete',
                            type: 'POST',
                            data: {messageId: ID},
                            dataType: 'JSON',
                            success: function (data) {
                                if (data.success) {
                                    if ($item.siblings().size() === 0) {
                                        self.loadMsg(page);
                                    }

                                    $item.remove();
                                    total--;
                                    $total.text(total);

                                    toastr.success('删除成功！');
                                } else {
                                    toastr.error('出错了，请重试！');
                                }
                            },
                            error: function () {
                                toastr.error('服务器异常，请稍后再试！');
                            }
                        });
                    });
            });

            $pageContent.on('click', '.previous, .next', function () {
                var $item = $(this),
                    page = $item.parents('#paging').data('page'),
                    maxPage = $item.parents('#paging').data('max-page'),
                    $prev = $('.previous'),
                    $next = $('.next');

                if ($item.is('.previous')) {
                    if ($next.is(':hidden')) {
                        $next.show();
                    }
                    if (page === 2) {
                        $prev.hide();
                    }
                    page--;
                }
                else if ($item.is('.next')) {
                    if ($prev.is(':hidden')) {
                        $prev.show();
                    }
                    if (page === maxPage - 1) {
                        $next.hide();
                    }
                    page++;
                }
                self.loadMsg(page);
            });

        }
    };

})(window, document, jQuery);
