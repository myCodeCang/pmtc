<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>
<title>市场架构</title>
<link rel="stylesheet" href="${ctxStatic}/qysoftui/vendor/bootstrap-treeview/bootstrap-treeview.css">

<div class="page animation-fade page-forms">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row row-lg">
                    <div class="clearfix hidden-xs"></div>
                    <div class="col-md-6 col-sm-6">
                        <div class="example-wrap">
                            <%--<h4 class="example-title">查找</h4>--%>
                            <form class="form-group" id="exampleSearchForm" role="search">
                                <div class="input-search">
                                    <input type="search" class="form-control" id="inputSearchable"
                                           placeholder="输入关键字搜索">
                                    <button type="submit" class="input-search-btn">
                                        <i class="icon wb-search" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </form>
                            <div class="example">
                                <div id="exampleSearchableTree" data-show-tags="true" ></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>


<script>
        (function (document, window, $) {
        'use strict';
        var getExampleDatas = ${data};
        var ExampleDatas = (function () {

    })(),
        defaults, treeViewExample = {};

        defaults = $.extend({}, {
        injectStyle: false,
        expandIcon: "icon wb-check",
        collapseIcon: "icon wb-check-circle",
        emptyIcon: "icon",
        nodeIcon: "icon wb-user",
        showBorder: false,
        color: "#000000",
        backColor: "#FFFFFF",
        borderColor: $.colors("blue-grey", 200),
        onhoverColor: $.colors("blue-grey", 100),
        selectedColor: "#ffffff",
        selectedBackColor: $.colors("purple", 600),

        searchResultColor: $.colors("purple", 600),
        searchResultBackColor: "#ffffff"
    });

        $.extend(treeViewExample, {
        basic: function () {
        $('.treeview', '#admui-pageContent').each(function () {
        var $item = $(this),
        basic_options = $.extend({}, defaults, $item.data(), {
        data: getExampleDatas
    });

        $item.treeview(basic_options);
    });
    },

        check: function () { // 查找示例
        var options = $.extend({}, defaults, {
        data: getExampleDatas
    });

        var $searchableTree = $('#exampleSearchableTree').treeview(options);

        $('#admui-pageContent').on('keyup', '#inputSearchable', function (e) {
        var pattern = $(e.target).val();

        $searchableTree.treeview('search', [pattern, {
        'ignoreCase': true,
        'exactMatch': false
    }]);
    });
    },

        foldChange: function () { // 展开收起示例
        var options = $.extend({}, defaults, {
        data: getExampleDatas
    }),
        $expandibleTree = $('#exampleExpandibleTree').treeview(options);

        $('#admui-pageContent').on('click', '#exampleExpandAll', function () {
        $expandibleTree.treeview('expandAll', {
        levels: '99'
    });
    });

        $('#admui-pageContent').on('click', '#exampleCollapseAll', function () {
        $expandibleTree.treeview('collapseAll');
    });
    },

        event: function () {
        var events_toastr, options;

        events_toastr = function (msg) {
        toastr.info(msg, '', {
        iconClass: 'toast-just-text toast-info',
        positionClass: 'toast-bottom-right',
        containertId: 'toast-bottom-right'
    });
    };

        options = $.extend({}, defaults, { // 事件
        data: getExampleDatas,
        onNodeCollapsed: function (event, node) {
        events_toastr(node.text + ' 被收起');
    },
        onNodeExpanded: function (event, node) {
        events_toastr(node.text + ' 被展开');
    },
        onNodeSelected: function (event, node) {
        events_toastr(node.text + ' 被选中');
    },
        onNodeUnselected: function (event, node) {
        events_toastr(node.text + ' 被取消选中');
    }
    });

        $('#exampleEvents').treeview(options);
    },

        run: function () {
        this.basic();
        this.check();
        this.foldChange();
        this.event();
    }
    });

        treeViewExample.run();
    })(document, window, jQuery);
</script>
