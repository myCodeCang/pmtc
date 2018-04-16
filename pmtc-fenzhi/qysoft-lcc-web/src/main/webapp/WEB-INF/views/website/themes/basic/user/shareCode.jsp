<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/website/themes/basic/layouts/taglib.jsp" %>
<head>
    <meta name="decorator" content="cms_default_${site.theme}"/>
</head>

<title>我的推广</title>


<style>
    @media screen and (min-width:640px){
        #imgCode{width:260px;margin-top:15px;}

    }
    @media screen and (min-width: 324px) and (max-width: 639px){
        #imgCode{width:100%;}
    }
    .erweima h3{font-size:23px;height:50px;line-height: 50px;position: relative;}
    .erweima h3::after{content: "";position:absolute;bottom:0;left:50%;width:180px;margin-left:-90px;height:3px; background: #4e97d9;}
    .erweima .bianhao{font-size:18px;margin-bottom: 10px}
    .erweima .dizhi{font-size:16px;margin-top:20px;color:#ff0000;}
</style>

<div class="page animation-fade">
    <div class="page-content">
        <div class="panel">
            <div class="panel-body container-fluid" style="text-align: center;padding-bottom:50px;">

                <div class="col-lg-12 col-md-12 erweima" >
                    <h3>我的推广二维码:</h3>
                    <div class="bianhao">用户编号:${userinfo.userName}</div>
                    <div id="code" >
                    </div>
                    <img id="imgCode"/>
                    <div class="dizhi">注:下级扫描二维码即可注册</div>
                </div>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript" data-deps="formValidation">

    (function (document, window, $) {
        var userName = "${userinfo.userName}";
        var qrcodeOne = $("#code").qrcode({
            width: 400, //宽度
            height:400, //高度
            text: "http://"+window.location.host+"/f/register?tj="+userName
        }).hide();
        var canvas = qrcodeOne.find('canvas').get(0);
        $('#imgCode').attr('src',canvas.toDataURL('image/jpg'));

    })(document, window, jQuery);

</script>

</body>
