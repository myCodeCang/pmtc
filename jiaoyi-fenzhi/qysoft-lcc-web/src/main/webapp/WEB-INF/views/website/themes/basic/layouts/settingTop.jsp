<!-- 头部开始 -->
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 头部开始 -->
<div id="doc_head">
    <div class="head_masthead">
        <div class="section">
            <h1 class="head_logo"><a href="${ctx}/indexl" style="width:200px;height:50px"><img src="${fns:getOption('system_sys','site_logo')}" style="width:200px;" alt="交易平台"></a></h1>
            <h2 class="head_title">安全设置</h2>
            <div class="float_right head_user_option">
                <a href="${ctx}/center" class="option " id="user_slide">
                    您好， ${userinfo.trueName} (${userinfo.userName})</a>
                <a href="${ctx}/logout" class="option">退出</a>
                <i class="seg"></i>
                <a class="option sub_show" href="${ctx}/indexl" style="font-size:14pt;">首页</a>
                <i class="seg sub_show"></i>
            </div>
        </div>
    </div>
</div>
<!-- 头部结束 -->