/**
 * Admui v1.0.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, doc, $) {
    'use strict';

    window.Content = {
        run: function () {
            $('.tabList').on('click', 'li', function () {
                var $item = $(this);

                $item.siblings('li').removeClass('active');
                $item.addClass('active');
            });

            // 获取消息数量

            // $.ajax({
            //     url: $.ctx + '/user/account',
            //     dataType: 'JSON',
            //     success: function (data) {
            //         if(data.success){
            //             $('.msg-number').text(data.msgCount);
            //             $('.log-number').text(data.logCount);
            //         }else{
            //             toastr.error('出错了，请重试！');
            //         }
            //     },
            //     error: function () {
            //         toastr.error('服务器异常，请稍后再试！');
            //     }
            // });
        }
    };

})(window, document, jQuery);
