<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE >
<html>
<head>
    <title>充值</title>
    <meta charset="utf-8">
    <title>${fns:getConfig('productName')}</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <%@ include file="/WEB-INF/views/website/themes/basic_app/layouts/apicloud_header.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/qyScript/app/layer/css/layer-mobile.css" />
    <style type="text/css">
        .home.aui-bar-nav {
            background-color: ${fns:getConfig('app.page.mainColor')};
            padding-top: 20px;
            position: fixed;
            top:0;left:0;
        }
        .height-one{height:3.25rem;}
        .blank_10{height:10px;}
        .blank_15{height:15px;}
        .blank_20{height:20px;background:#FFFFFF;}
        /*线下转账*/
        .transfer{padding:0.75rem 0.75rem 0.25rem 0.75rem;background:#fff;}
        .transfer p{margin-bottom:0.4rem;}
        .transfer .transfer-p{color:${fns:getConfig('app.page.mainColor')};}
        .transfer .aui-btn{color:#fff;background:${fns:getConfig('app.page.mainColor')};border-radius:8px;font-size:0.75rem;}

        .transfer-one .aui-list-item-label{font-size:0.75rem;color:#555;}
        .transfer-one input::-webkit-input-placeholder{font-size:0.7rem;color:#999999;}

        .transfer-two .aui-list-item:active{background:#fff;border: none; background-size: 100% 1px; background-repeat: no-repeat; background-position: bottom; background-image: linear-gradient(0,#dddddd,#dddddd 50%,transparent 50%);}
        .transfer-two .aui-list-item-title{font-size:0.75rem;color:#555;}
        .transfer-two .aui-btn{padding:0 0.4rem;background:none;border:1px solid #666;}
        .transfer-two .transfer-two-but{color:#fff;background:${fns:getConfig('app.page.mainColor')};border:none;display: none;}
        #account{display: none;}
        #account-one{display: none;}

        .transfer-three .aui-list-item-label{font-size:0.75rem;}
        .transfer-three .aui-list-item-input{position: relative;height:5rem;padding:0.5rem 0;width: 77%;}
        .transfer-three .picture{position: absolute;top:0.75rem;left:0.75rem;border:1px solid #666;width:8rem;height:3.5rem;background:#ddd;border-radius:6px;text-align: center;line-height: 3.5rem;font-size:0.7rem;color:#666;overflow: hidden;}
        .transfer-three .picture img{width:100%;height:100%;}
        .transfer-four{padding:0.75rem 0.5rem;}
        .transfer-four p{text-align:justify ;}
        .transfer-but{padding:0 0.75rem;}
        .transfer-but .aui-btn{background:${fns:getConfig('app.page.mainColor')};color:#FFFFFF;font-size:0.75rem;height:2.25rem;line-height: 2.25rem;}
    </style>
</head>
<body>

<header class="aui-bar aui-bar-nav home">
    <a class="aui-pull-left aui-btn back" href="#">
        <span class="aui-iconfont aui-icon-left"></span>
    </a>
    <div class="aui-title">线下转账</div>
</header>

<div class="height-one"></div>
<div class="aui-content transfer">
    <p>银行名称:&nbsp;<span id="bankName"></span></p>
    <p>账户名称:&nbsp;<span id="userbankName"></span></p>
    <p>开户支行:&nbsp;<span id="commt">农业银行</span></p>
    <p>卡号:&nbsp;<span id="bankNumber"></span></p>
    <p class="transfer-p" >温馨提示:&nbsp;<span id="message"></span></p>
     <div class="aui-btn" style="background-color:#ee7e4d" onclick="change()">更换账号</div>
</div>
<div class="aui-content aui-margin-b-10 transfer-one">
    <ul class="aui-list aui-form-list">
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    转账银行
                </div>
                <div class="aui-list-item-input">
                    <select id="select">
                        <%--<option>中国银行</option>--%>
                        <%--<option>农业银行</option>--%>
                        <%--<option>工商银行</option>--%>
                    </select>
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    户名
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="请填写银行卡户名" id="bankUserName">
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    卡号
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="填写卡号" id="bankUserNumber">
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    转账金额
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="填写转账金额" id="money" onKeyUp="amount(this)"  onBlur="overFormat(this)"
                    >
                </div>
            </div>
        </li>
    </ul>
</div>
<label id="realBtn" class="btn btn-info">
<div class="aui-content transfer-three" onclick="setUserPic('bankImage')">
    <ul class="aui-list aui-form-list">
        <li class="aui-list-item">
            <div class="aui-list-item-inner " >
                <div class="aui-list-item-label">
                    上传凭证
                </div>
                <div class="aui-list-item-input">
                    <input type="hidden" name="bankImage" />
                    <div class="picture" id="bankImage">上传银行凭证</div>
                </div>
            </div>
        </li>
    </ul>
</div>
</label>
<div class="aui-content transfer-four">
    <p>${fns:getOption("system_xxzz","xxzz_comnt")}</p>
</div>
<div class="aui-content transfer-but" onclick="save()">
    <div class="aui-btn aui-btn-block" style="background-color:#ee7e4d">提交</div>
</div>
<style type="text/css">
    .box1st{position: fixed;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,0.6);display: flex;display: -webkit-flex;justify-content: center;-webkit-justify-content: center;align-items: center;-webkit-align-items:center;}
    .box{width:70%;height:40%;background: #fff;border-radius: 6px;overflow: hidden;}
    .box>h3{width:100%;height:40px;line-height:40px;text-align: center;border-bottom: 1px solid #CCCCCC;background:${fns:getConfig('app.page.mainColor')};color:#fff;}
    .box .box-one{height:80%;padding:0.75rem 0.5rem;overflow-y:scroll;}
    .box .box-one>div>div{width:100%;height:35px;line-height:35px;font-size:0.75rem;border-bottom:1px solid #CCc;}
</style>
<div class="aui-content box1st" style="display: none" id="change_select">
    <div class="box" >
        <h3  style="background-color:#ee7e4d">银行选择</h3>
        <div class="box-one">
            <div id="bank_select">

            </div>
        </div>
    </div>
</div >
<div class="blank_15"></div>
<div class="blank_15"></div>
<%@ include file="/WEB-INF/views/website/themes/basic_app/layouts/apicloud_footer.jsp" %>
<script type="text/javascript" src="${ctxStatic}/qyScript/app/other/common.js"></script>
<script  type="text/html" id="tpl-select">
    {{each userBankCommon}}
    <option value="{{$value.bankCode}}">{{$value.name}}</option>
    {{/each}}
</script>

<script type="text/javascript">
    qySoftInit("${ctxStatic}/qyScript/app/");
    var CONS_AJAX_URL = "${fns:getOption('system_sys','domain')}";
    var CONS_IMG_URL = "${fns:getOption('system_sys','domain')}";
    var saveFlag = false;
    var qyWin;
    var uiMediaScanner = null, imageFilter = null, imageBrowser = null;
    var isIOS = false;
    var dataAraray;
    //var qyApi;
    //apicloud准备完成
    apiready = function () {
        layui.use(['qyWin', 'qyForm','template'], function () {
            qyWin = layui.qyWin;

            uiMediaScanner = api.require('UIMediaScanner');
            // 引入过滤压缩模块
            imageFilter = api.require("imageFilter");
            // 引入图片浏览模块
            imageBrowser = api.require('imageBrowser');
            //qyApi = layui.qyApi;
            initView();
        });
    }



    function initView() {
        loadData();
        selectBank();

    }

    function overFormat(th){
        var v = th.value;
        if(v === ''){
            v = '0.00';
        }else if(v === '0'){
            v = '0.00';
        }else if(v === '0.'){
            v = '0.00';
        }else if(/^0+\d+\.?\d*.*$/.test(v)){
            v = v.replace(/^0+(\d+\.?\d*).*$/, '$1');
            v = inp.getRightPriceFormat(v).val;
        }else if(/^0\.\d$/.test(v)){
            v = v + '0';
        }else if(!/^\d+\.\d{2}$/.test(v)){
            if(/^\d+\.\d{2}.+/.test(v)){
                v = v.replace(/^(\d+\.\d{2}).*$/, '$1');
            }else if(/^\d+$/.test(v)){
                v = v + '.00';
            }else if(/^\d+\.$/.test(v)){
                v = v + '00';
            }else if(/^\d+\.\d$/.test(v)){
                v = v + '0';
            }else if(/^[^\d]+\d+\.?\d*$/.test(v)){
                v = v.replace(/^[^\d]+(\d+\.?\d*)$/, '$1');
            }else if(/\d+/.test(v)){
                v = v.replace(/^[^\d]*(\d+\.?\d*).*$/, '$1');
                ty = false;
            }else if(/^0+\d+\.?\d*$/.test(v)){
                v = v.replace(/^0+(\d+\.?\d*)$/, '$1');
                ty = false;
            }else{
                v = '0.00';
            }
        }
        th.value = v;
    }


    function amount(th){
        var regStrs = [
            ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
            ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
            ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
            ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
        ];
        for(var i=0; i<regStrs.length; i++){
            var reg = new RegExp(regStrs[i][0]);
            th.value = th.value.replace(reg, regStrs[i][1]);
        }
    }


    function loadData(){

        layui.qyForm.qyajax("/payment/offlineTransfer/userChargeBankList",{},function(data){
            if(data.userChargeBank.length < 1){
                layui.qyWin.toast("暂不支持线下转账!");
                setTimeout("layui.qyWin.closeWin(true)",1000);
            }
            dataAraray=data.userChargeBank;
            var html='';
            for(var i=0 ;i<dataAraray.length;i++){
                html +='<div onclick="changeBank('+i+')">'+dataAraray[i].bankName+'</div>';
            }
            $("#bank_select").html(html);
            changeBank(0);

        });
    }
    
    function selectBank() {
        layui.qyForm.qyajax("/payment/offlineTransfer/userCommonBankList",{},function(data){

            var html=template("tpl-select",data);
            $('#select').html(html);
        });
    }
    
    function change() {
        $("#change_select").show();
    }
    
    function changeBank(i) {
        $("#bankName").html(dataAraray[i].bankName);
        $("#userbankName").html(dataAraray[i].userBankName);
        $("#bankNumber").html(dataAraray[i].userBankNumber);
        $("#commt").html(dataAraray[i].commt);
        $("#message").html(dataAraray[i].remarks);
        $("#change_select").hide();
    }

    function save(){

        var bankNumber = $("#bankUserNumber").val();
        var money = $("#money").val();
        var bankCode = $('#select').val();
        var bankImage = $("input[name='bankImage']").val();
        var bankUser = $("#bankUserName").val();


        if(!bankNumber || !money || !bankCode ){
            layui.qyWin.toast("请将信息填写完整!");
            return ;
        }
        if(!bankImage){
            layui.qyWin.toast("所有图片必须上传!");
            return ;
        }

        if( money <= 0  ){
            layui.qyWin.toast("金额必须大于0!");
            return ;
        }
        if(saveFlag){
            return;
        }
        var parm = {
            changeMoney:money,
            bankImage:bankImage,
            bankNum:bankNumber,
            bankCode:bankCode,
            bankUser:bankUser
        }
        saveFlag = true;
        layui.qyForm.qyajax("/payment/offlineTransfer/userApplyCharge",parm,function(data){
            layui.qyWin.toast("申请成功,请耐心等待审核!");
            setTimeout("layui.qyWin.closeWin(true)",1000);
        },function(res){
            saveFlag = false;
        });
    }

    function setUserPic(divName) {
        api.actionSheet({
            title : '选择图片来源',
            buttons : ['优雅自拍', '浏览相册']
        }, function(ret, err) {
            var index = ret.buttonIndex;
            switch(index) {
                // 拍照
                case 1:
                    // 打开拍照
                    api.getPicture({
                        sourceType : "camera",
                        encodingType : "jpg",
                        destinationType : "url",
                        mediaValue : "pic",
                        quality : 50,
                        saveToPhotoAlbum : true
                    }, function(ret, err) {
                        if (ret && ret.data) {
                            // 拍照返回的本地路径
                            var returnUrl = ret.data;
                            // 图片压缩
                            imgCompress(returnUrl, 0.5, getExt(returnUrl), function(compressImg) {
                                uploadFile(CONS_AJAX_URL + '/payment/offlineTransfer/imgFileUpload', compressImg, function(serverImg) {
                                    $("#"+divName).html('<img src="'+CONS_IMG_URL+serverImg+'">');
                                    $("input[name='"+divName+"']").val(serverImg);
                                },"");
                            });
                        } else {
                            api.toast({
                                msg : '没有选择，或者选择出错'
                            });
                        }
                    });
                    break;
                // 打开图片选择器
                case 2:
                    uiMediaScanner.open({
                        type : 'picture',
                        column : 4,
                        classify : true,
                        max : 1,
                        sort : {
                            key : 'time',
                            order : 'desc'
                        },
                        texts : {
                            stateText : '已选*项',
                            cancelText : '取消',
                            finishText : '完成'
                        },
                        styles : {
                            bg : '#fff',
                            mark : {
                                icon : '',
                                position : 'bottom_left',
                                size : 20
                            },
                            nav : {
                                bg : '#b23e4b',
                                stateColor : '#fff',
                                stateSize : 18,
                                cancelBg : 'rgba(0,0,0,0)',
                                cancelColor : '#fff',
                                cancelSize : 18,
                                finishBg : 'rgba(0,0,0,0)',
                                finishColor : '#fff',
                                finishSize : 18
                            }
                        }
                    }, function(ret) {
                        if (ret) {
                            if(!ret.list){
                                return;
                            }
                            for (var i = 0; i < ret.list.length; i++) {
                                var selectImg = ret.list[i];
                                // 获取图片的路径
                                var selectimgPath = selectImg.path;
                                var selectimgThumbPath = selectImg.thumbPath;
                                // IOS需要将虚拟路径转换为本地路径才可以压缩
                                if (isIOS) {
                                    // 转换为真实路径
                                    uiMediaScanner.transPath({
                                        path : selectimgPath
                                    }, function(transObj) {
                                        // 图片压缩
                                        imgCompress(transObj.path, 0.5, selectImg.suffix, function(compressImg) {
                                            uploadFile(CONS_AJAX_URL + "/payment/offlineTransfer/imgFileUpload", compressImg, function(serverImg) {
                                                $("#"+divName).html('<img src="'+CONS_IMG_URL+serverImg+'">');
                                                $("input[name='"+divName+"']").val(serverImg);
                                            },"");
                                        });
                                    });
                                } else {
                                    // 图片压缩
                                    imgCompress(selectimgPath, 0.5, selectImg.suffix, function(compressImg) {
                                        uploadFile(CONS_AJAX_URL +"/payment/offlineTransfer/imgFileUpload", compressImg, function(serverImg) {
                                            //alert(CONS_IMG_URL+serverImg);
                                            $("#"+divName).html('<img src="'+CONS_IMG_URL+serverImg+'">');
                                            $("input[name='"+divName+"']").val(serverImg);
                                        }, "");
                                    });
                                }
                            }
                        }
                    });
                    break;
            }
        });
    }



</script>

</body>
</html>

