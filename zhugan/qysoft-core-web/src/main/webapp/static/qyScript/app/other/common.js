function NewGuid() {
	function S4() {
		return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	}

	return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}
// 获取当前的时间，拼接成2015-11-09这样的格式，主要用于对图片进行时间分类
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate
	return currentdate;
}

// 获取文件拓展名
function getExt(fileName) {
	return fileName.substring(fileName.lastIndexOf('.') + 1);
}

// 图片压缩
// imgsrc：源图片的路径
// quality：图片压缩质量，一般建议0.5
// scale：图片压缩比例，也是建议0.5
// ext：源图片拓展名
// callback：转换成功之后回调函数
function imgCompress(imgsrc, quality, ext, callback) {
	// 压缩文件的保存目录
	var savePath = api.cacheDir + "/" + getNowFormatDate() + "/";
	// 压缩文件生成的随机文件名称
	var savename = NewGuid() + "." + ext;
	imageFilter.compress({
		img : imgsrc,
		quality : quality,
		size : {
			w:800,
			h:800
		},
		save : {
			album : false,
			imgPath : savePath,
			imgName : savename
		}
	}, function(ret, err) {
		if (ret) {
			callback(savePath + savename);
		} else {
			alert(JSON.stringify(err));
		}
	});
}

function imgCompre(imgsrc, quality, ext, callback) {
	// 压缩文件的保存目录
	var savePath = api.cacheDir + "/" + getNowFormatDate() + "/";
	// 压缩文件生成的随机文件名称
	var savename = NewGuid() + "." + ext;
	imageFilter.compress({
		img : imgsrc,
		quality : quality,
		size : {
			w:800,
			h:800
		},
		save : {
			album : false,
			imgPath : savePath,
			imgName : savename
		}
	}, function(ret, err) {
		if (ret) {
			callback(savePath + savename);
		} else {
			alert(JSON.stringify(err));
		}
	});
}

// 上传图片
// url：上传的url地址
// data：上传的文件
// callback：上传成功返回地址
function uploadFile(url, data, callback, param) {

    api.showProgress({
        style : 'default',
        animationType : 'fade',
        title : '努力加载中...',
        text : '先喝杯茶...',
        modal : false
    });
    console.log(data);
    api.ajax({
        url : url,
        method : 'post',
        timeout : 5000,
        dataType : 'json',
        returnAll : false,
        data : {
            values : {
                name : param
            },
            files : {
                "file" : data
            }
        }
    }, function(ret, err) {
        if (ret) {
            if (ret.status == 1) {
                $api.setStorage("user", ret.data);
                callback(ret.path);
            } else if (ret.status == 0) {
                alert(ret.info);
            }
        } else {
            api.alert({
                msg : ('错误码：' + err.code + '；错误信息：' + err.msg + '；网络状态码：' + err.statusCode)
            });
        }
        api.hideProgress();
    });
}

// 批量上传图片
// url：上传的url地址
// data：上传的文件
// callback：上传成功返回地址
function uploadFiles(url, data, msg, address, callback) {

	api.showProgress({
		style : 'default',
		animationType : 'fade',
		title : '努力加载中...',
		text : '先喝杯茶...',
		modal : false
	});
	console.log(data);
	api.ajax({
		url : url,
		method : 'post',
		timeout : 30,
		dataType : 'json',
		returnAll : false,
		data : {
			files : data,
			values : {
				post_content : msg,
				location : address
			}
		}
	}, function(ret, err) {
		if (ret) {
			if (ret.code == 1) {
				callback()
			} else if (ret.code == 0) {
				alert("上传失败");
			}
		} else {
			api.alert({
				msg : ('错误码：' + err.code + '；错误信息：' + err.msg + '；网络状态码：' + err.statusCode)
			});
		}
		api.hideProgress();
	});
}

// 打开图片浏览
// imgs：需要预览的图片集合
function openImageBrowser(imgs) {
	imageBrowser.openImages({
		imageUrls : imgs,
		showList : false,
		activeIndex : 0
	});
}

// 添加长按方法
function addPress(obj, index) {
	// 获取目前长按的对象
	var hammertime = new Hammer(obj[0]);
	// 绑定长按对象
	hammertime.on("press", function(e) {
		api.confirm({
			title : '温馨提示',
			msg : '您确定要删除该图片吗？',
			buttons : ['确定', '取消']
		}, function(ret, err) {
			if (ret.buttonIndex == 1) {
				// 移除自己
				$(obj).remove();
				api.toast({
					msg : '删除成功！'
				});
			}
		});
	});
}

