

//初始化微信jsapi接口，调用微信js用
function initWxJsAndDo(jsApi,callMethod){
	$.ajax({
		type : "post",
		dataType : "json",
		async:false,
		data : {
			url : location.href
		},
		url : "http://101.201.209.109/act/wx/initWxJsApi",
		success : function(data){
			if(data.code=="0"){
                wx.config({
                    debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId : data.appId, // 必填，公众号的唯一标识
                    timestamp : data.timestamp, // 必填，生成签名的时间戳
                    nonceStr : data.noncestr, // 必填，生成签名的随机串
                    signature : data.signature,// 必填，签名，见附录1
                    jsApiList : jsApi// 必填，需要使用的JS接口列表
                });
                
                callMethod();
			}else{
				alert("操作失败");
			}
		}
	});
}
//监听微信分享朋友圈
initWxJsAndDo(['onMenuShareTimeline'],function(){
	wx.onMenuShareTimeline({
	    title: '', // 分享标题
	    link: '', // 分享链接
	    imgUrl: '', // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
});








	
https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1ffebf2bd2e6fe48&redirect_uri=http://test.tingjiandan.com/mobile/bdpage/bdindex.html&response_type=code&scope=snsapi_base&state=act#wechat_redirect
	
	
	//获取url参数值
	var getUrlParam = function (name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]); return null;
	};
	
	
	
//通过连接进入该页面，获取code和groupid  判断返回成功，进入群聊
$(function(){
	//获取授权后的code
	var code = getUrlParam("code");
	var groupid = getUrlParam("groupid");
	if(code){
		$.ajax({
			type : "post",
			dataType : "json",
			data : {
				groupid : groupid,
				code : code
			},
			url : "http://101.201.209.109/act/groups/joinGroup",
			success : function(data){
				//返回用户信息，调用环信的js登录，进入聊天群组界面
			}
		});
		
	}else{
		alert("请授权后在访问！");
	}
});




function speakOk(userId){
	$.ajax({
		type : "post",
		dataType : "json",
		data : {
			userId : userId
		},
		url : "http://101.201.209.109/act/users/isStopSpeak",
		success : function(data){
			if(data.code == '0' && data.isStopSpeak == '0'){
				alert("被禁言，不能发送信息");
			}
		}
	});
}
	



//登录
$.ajax({
	type : "post",
	dataType : "json",
	data : {
		username : username
	},
	url : "http://101.201.209.109/act/users/loginForMgr",
	success : function(data){
		if(data.code == '0'){
			//返回用户信息
		}else{
			//提示错误信息   data.errorMSG
		}
	}
});


$.ajax({
	type : "post",
	dataType : "json",
	data : {
		nickname : nickname, //用户名和昵称模糊查询
		username : username
	},
	url : "http://101.201.209.109/act/users/teacherList",
	success : function(data){
		if(data.code == '0'){
			//返回老师列表
		}else{
			//提示错误信息   data.errorMSG
		}
	}
});

















	