<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link href="${ctxStatic}/adminShop/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctxStatic}/adminShop/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${ctxStatic}/adminShop/css/animate.css" rel="stylesheet">
    <link href="${ctxStatic}/adminShop/css/v2.css?v=4.1.0" rel="stylesheet">
    <link href="${ctxStatic}/adminShop/css/common.css?v=2.0.0" rel="stylesheet">
    <link href="${ctxStatic}/adminShop/fonts/iconfont.css?v=2016070717" rel="stylesheet" type="text/css">
    <script src="${ctxStatic}/adminShop/js/lib/jquery-1.11.1.min.js"></script>
    <script src="${ctxStatic}/adminShop/js/dist/jquery/jquery.gcjs.js"></script>
    <script src="${ctxStatic}/adminShop/js/app/util.js"></script>


    <script src="${ctxStatic}/adminShop/js/require.js"></script>
    <script>
        require.config({
            waitSeconds: 0
        });
    </script>
    <script src="${ctxStatic}/adminShop/js/myconfig.js"></script>
    <script type="text/javascript">
        if (navigator.appName == 'Microsoft Internet Explorer') {
            if (navigator.userAgent.indexOf("MSIE 5.0") > 0 || navigator.userAgent.indexOf("MSIE 6.0") > 0 || navigator.userAgent.indexOf("MSIE 7.0") > 0) {
                alert('您使用的 IE 浏览器版本过低, 推荐使用 Chrome 浏览器或 IE8 及以上版本浏览器.');
            }
        }
        myrequire.path = "${ctxStatic}/adminShop/js/";
        function preview_html(txt) {
            var win = window.open("", "win", "width=300,height=600"); // a window object
            win.document.open("text/html", "replace");
            win.document.write($(txt).val());
            win.document.close();
        }
    </script>

</head>


<body class="white-bg " style="padding-left: 20px;">


<div class="page-content" style="display: block;">

    <style type="text/css" xmlns="http://www.w3.org/1999/html">
        .dd-handle {
            height: 40px;
            line-height: 30px
        }

        .dd-list {
            width: 1060px;
        }
    </style>

    <div class="page-heading">
    <span class="pull-right">
        <a href="#" class="btn btn-warning"> 设置分类层级</a>
        <a href="#" class="btn btn-primary"><i class="fa fa-plus"></i> 添加新分类</a>

    </span>
        <h2>商品分类</h2>
    </div>


    <form action="" method="post" class="form-validate">

        <div class="dd" id="div_nestable">
            <ol class="dd-list">

                {loop $category $row}
                {if empty($row['parentid'])}
                <li class="dd-item full" data-id="{$row['id']}">

                    <div class="dd-handle">
                        [ID: {$row['id']}] {$row['name']}
                        <span class="pull-right">

                        <div class='label {if $row[' enabled']==1}label-success{else}label-default{/if}'
                             {ifp 'goods.category.edit'}
                             data-toggle='ajaxSwitch'
                             data-switch-value='{$row['enabled']}'
                             data-switch-value0='0|隐藏|label label-default|{php echo webUrl('goods/category/enabled',array('enabled'=>1,'id'=>$row['id']))}'
                             data-switch-value1='1|显示|label label-success|{php echo webUrl('goods/category/enabled',array('enabled'=>0,'id'=>$row['id']))}'
                             {/if}
                             >
                             {if $row['enabled']==1}显示{else}隐藏{/if}</div>

                    {if intval($_W['shopset']['category']['level'])>1 }{ifp 'goods.category.add'}<a
                        class='btn btn-default btn-sm'
                        href="{php echo webUrl('goods/category/add', array('parentid' => $row['id']))}" title='添加子分类'><i
                        class="fa fa-plus"></i></a>{/if}{/if}
                    {ifp 'goods.category.edit|goods.category.view'}
                    <a class='btn btn-default btn-sm'
                       href="{php echo webUrl('goods/category/edit', array('id' => $row['id']))}"
                       title="{ifp 'goods.category.edit'}修改{else}查看{/if}"><i class="fa fa-edit"></i></a>
                    {/if}
                    {ifp 'goods.category.delete'}<a class='btn btn-default btn-sm' data-toggle='ajaxPost'
                                                    href="{php echo webUrl('goods/category/delete', array('id' => $row['id']))}"
                                                    data-confirm='确认删除此分类吗？'><i class="fa fa-remove"></i></a>{/if}
                    </span>
        </div>
        {if count($children[$row['id']])>0}

        <ol class="dd-list">
            {loop $children[$row['id']] $child}
            <li class="dd-item full" data-id="{$child['id']}">
                <div class="dd-handle" style="width:100%;">
                    <img src="{php echo tomedia($child['thumb']);}" width='30' height="30" onerror="$(this).remove()"
                         style='padding:1px;border: 1px solid #ccc;float:left;'/> &nbsp;
                    [ID: {$child['id']}] {$child['name']}
                    <span class="pull-right">
                                    <div class='label {if $child[' enabled']==1}label-success{else}label-default{/if}'
                                         {ifp 'goods.category.edit'}
                                         data-toggle='ajaxSwitch'
                                         data-switch-value='{$child['enabled']}'
                                         data-switch-value0='0|隐藏|label label-default|{php echo webUrl('goods/category/enabled',array('enabled'=>1,'id'=>$child['id']))}'
                                         data-switch-value1='1|显示|label label-success|{php echo webUrl('goods/category/enabled',array('enabled'=>0,'id'=>$child['id']))}'
                                         {/if}
                                         >
                                         {if $child['enabled']==1}显示{else}隐藏{/if}</div>

                {if intval($_W['shopset']['category']['level'])>2}
                {ifp 'goods.category.add'}<a class='btn btn-default btn-sm'
                                             href="{php echo webUrl('goods/category/add', array('parentid' => $child['id']))}"
                                             title='添加子分类'><i class="fa fa-plus"></i></a>{/if}
                {/if}
                {ifp 'goods.category.edit|goods.category.view'}<a class='btn btn-default btn-sm'
                                                                  href="{php echo webUrl('goods/category/edit', array('id' => $child['id']))}"
                                                                  title="{ifp 'goods.category.edit'}修改{else}查看{/if}"><i
                    class="fa fa-edit"></i></a>{/if}
                {ifp 'goods.category.delete'} <a class='btn btn-default btn-sm' data-toggle='ajaxPost'
                                                 href="{php echo webUrl('goods/category/delete', array('id' => $child['id']))}"
                                                 data-confirm="确认删除此分类吗？"><i class="fa fa-remove"></i></a>{/if}
                </span>
</div>
{if count($children[$child['id']])>0 && intval($_W['shopset']['category']['level'])==3}

<ol class="dd-list" style='width:100%;'>
    {loop $children[$child['id']] $third}
    <li class="dd-item" data-id="{$third['id']}">
        <div class="dd-handle">
            <img src="{php echo tomedia($third['thumb']);}" width='30' height="30" onerror="$(this).remove()"
                 style='padding:1px;border: 1px solid #ccc;float:left;'/> &nbsp;
            [ID: {$third['id']}] {$third['name']}
            <span class="pull-right">
						 <div class='label {if $third[' enabled']==1}label-success{else}label-default{/if}'
											{ifp 'goods.category.edit'}
											data-toggle='ajaxSwitch'
											data-switch-value='{$third['enabled']}'
											data-switch-value0='0|隐藏|label label-default|{php echo webUrl('goods/category/enabled',array('enabled'=>1,'id'=>$third['id']))}'
											data-switch-value1='1|显示|label label-success|{php echo webUrl('goods/category/enabled',array('enabled'=>0,'id'=>$third['id']))}'
											{/if}
											>
											{if $third['enabled']==1}显示{else}隐藏{/if}</div>

        {ifp 'goods.category.edit|goods.category.view'}<a class='btn btn-default btn-sm'
                                                          href="{php echo webUrl('goods/category/edit', array('id' => $third['id']))}"
                                                          title="{ifp 'goods.category.edit'}修改{else}查看{/if}"><i
            class="fa fa-edit"></i></a>{/if}
        {ifp 'goods.category.delete'}<a class='btn btn-default btn-sm' data-toggle='ajaxPost'
                                        href="{php echo webUrl('goods/category/delete', array('id' => $third['id']))}"
                                        data-confirm="确认删除此分类吗？"><i class="fa fa-remove"></i></a>{/if}
        </span>
        </div>
    </li>
    {/loop}
</ol>
{/if}
</li>
{/loop}
</ol>
{/if}

</li>
{/if}
{/loop}

</ol>
<table class='table'>
    <tr>
        <td>

            {ifp 'goods.category.edit'}
            <input id="save_category" type="submit" class="btn btn-primary" value="保存">
            {/if}
            <input type="hidden" name="token" value="{$_W['token']}"/>
            <input type="hidden" name="datas" value=""/>
        </td>
    </tr>
    </tbody>
</table>
</div>


</form>


</div>

</body>

</html>

