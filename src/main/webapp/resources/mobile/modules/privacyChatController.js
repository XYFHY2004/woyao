
/**
 * Created by lzd on 2016.
 */

define(['jquery','avalon', 'text!./privacyChat.html','socket','swiper',"domReady!",'qqface'], function ($,avalon,_privacyChat,socket,swiper,domReady) {
    avalon.templateCache._privacyChat = _privacyChat;

    var privacySocket = socket;
//    privacySocket.onmessage = function(message) {
//        var msg = JSON.parse(message.data);
//        console.log("get masage:");
//        console.log(msg);
//
////        console.log(msg.text);
//        msg.text = replace_em(msg.text);
//
//        
//        // 判断是否为私聊消息 同时判断 发送者是否为当前聊天对象
//        if(!msg.privacy){
//            if(msg.sender.id == pChatController.toWho.id || msg.command == 'smACK'){
//            	 pChatController.msgList.push(msg);
//                 $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 100},500,'swing');
//            }else{
//            	avalon.vmodels.rootController.privacyMsg.push(msg);
//            	avalon.vmodels.mainController.pMsgCount = avalon.vmodels.rootController.privacyMsg.length;
//            }
//        }
        
        


//        var seconds = msg.msgType*1000;
//        if(seconds != 0){
//            mainController.sreenShow = true;
//            mainController.sreenImg = text.imgUrl;
//            mainController.sreenMsg = text.msg;
//            mainController.sreenTime = text.msgType;
//            mainController.sreenShowSeconds = seconds/1000;
//            var fl = '';
//            fl = setInterval(function(){
//                mainController.sreenShowSeconds--;
//                if(mainController.sreenShowSeconds == 0){
//                    clearTimeout(fl);
//                    mainController.sreenImg = '';
//                    mainController.sreenMsg = '';
//                    mainController.sreenTime = '';
//                    mainController.sreenShow = false;
//                    return
//                }
//            },1000)
//        }
//    }

    
        var pChatController = avalon.define({
            $id : "pChatController",
            pluginShow : false, // 显示功能区标记
            emojiShow : false, // 显示emoji标记
            popshow : false, // 显示弹出层标记
            popSendCtnShow : false, // 显示发送面板标记
            tabShowFlag : false, // tab显示切换 false = 霸屏； true = 打赏；
            isShowPhoto: false, //显示照片墙
            senttext:'', // 输入框文字
            sreenShow:false, // 霸屏显示
            forHerShow:false, // 为他霸屏
            toWho:{},
            sreenShowSeconds:0, // 倒计时
            sreenImg:'/resources/static/img/delate/photo1.jpg', // 霸屏图片
            sreenMsg:'', // 霸屏文字
            sreenTime:'', // 霸屏时间
            pMsgList:[],
            userInfo:{
            	id:1
            },
            togglePlugin : function(){ // 显示功能区 按钮
                pChatController.emojiShow = false;
                pChatController.pluginShow = !pChatController.pluginShow;
            },
            toggleEmoji : function(){ // 显示emoji 按钮
                pChatController.pluginShow = false;
                pChatController.emojiShow = !pChatController.emojiShow;
            },
            showPopSend:function(){ // 显示发送提交面板
                pChatController.popshow = true;
                setTimeout(function(){
                    pChatController.popSendCtnShow = true;
                },10)
            },
            stopPropagation:function(e){
                e.stopPropagation();
            },
            stopPreventDefault:function(e){
                e.preventDefault();
            },
            closeAllPop: function (e) {
                e.preventDefault();
                if(pChatController.popSendCtnShow){
                    pChatController.popSendCtnShow = false;
                    setTimeout(function(){
                        pChatController.popshow = false;
                    },300);
                }
            },
            hidePopSend:function(){
                pChatController.popSendCtnShow = false;
                setTimeout(function(){
                    pChatController.popshow = false;
                },300);
            },
            tabChange:function(type){
                if(type == 1){
                    pChatController.tabShowFlag = false;
                }else{
                    pChatController.tabShowFlag = true;
                }
            },
            showPhotoWall:function(){
                if(pChatController.isShowPhoto){
                    $(".pop-photoWall").css('display','none');
                    pChatController.isShowPhoto = false;
                    setTimeout(function(){
                        pChatController.popshow = false;
                    },300)
                }else{
                    pChatController.popshow = true;
                    $(".pop-photoWall").css('display','block');
                    setTimeout(function(){
                        pChatController.isShowPhoto = true;
                        showPhoto();
                    },10)
                }
            },
            msgText:'', //文字内容
            imgUrl:'', //发送图片 base64
            sendMsg:function(){
            	var content = {
                        text:pChatController.msgText,
                        pic:pChatController.imgUrl,
                    };
                    function sliceContent(content){
                        var strings = JSON.stringify(content);
                        var sLength = strings.length;
                        var size = 2000;
                        var mod = sLength%size;
                        var l = Math.ceil(sLength/size);
                        var blocks = new Array();
                        for(var i = 0;i<l;i++){
                            blocks[i] = {
                                block:strings.substr(size*i, size),
                                seq:i
                            }
                        }
                        return blocks;
                    }

                    var blocks = sliceContent(content);
                    for(var i = 0;i<blocks.length;i++){
                        var msg = undefined;
                        var type = 'msgBlock';
                        if (i==0) {
                            type = 'msg';
                            msg = {
                                msgId:1,
                                to:pChatController.toWho.id,
                                blockSize:blocks.length,
                                productId:'',
                                block:blocks[0],
                            };
                        }else{
                            msg = {
                                msgId:1,
                                block:blocks[i],
                            }
                        }
                        var msgContent = type+"\n" + JSON.stringify(msg);
                        privacySocket.send(msgContent);
                    }

                pChatController.hidePopSend();
                if(pChatController.emojiShow){
                    pChatController.pluginShow = false;
                    pChatController.emojiShow = false;
                }

                pChatController.msgType = '0';
                pChatController.msgText = '';
                pChatController.pluginShow = false;
                pChatController.imgUrl = '';
                pChatController.imgViewSrc = '/resources/static/img/photo.png';
                $("#pPhotoInput").val('');
            }
            ,
            imgViewSrc:'/resources/static/img/photo.png',
            fileChange:function(e){
            	if (!this.files.length) return;
                var files = Array.prototype.slice.call(this.files);
                if (files.length > 1) {
                    alert("一次只能上传一张图片");
                    return;
                }
                files.forEach(function(file, i) {
                    if (!/\/(?:jpeg|png|gif)/i.test(file.type)) return;
                    var reader = new FileReader();
                    var li = document.createElement("li");
                    var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
                    reader.onload = function() {
                        var result = this.result;
                        var img = new Image();
                        img.src = result;
                        pChatController.imgViewSrc = result;
                        //如果图片大小小于200kb，则直接上传
                        if (result.length <= 200 * 1024) {
                            img = null;
                            pChatController.imgUrl = result;
                            return;
                        }
//                      图片加载完毕之后进行压缩，然后上传
                        if (img.complete) {
                            callback();
                        } else {
                            img.onload = callback;
                        }
                        function callback() {
                            var data = compress(img);
                            pChatController.imgUrl = data;
                            img = null;
                        }
                    };
                    reader.readAsDataURL(file);
                });
            },
            pClearImg:function(){
            	pChatController.imgUrl = '';
            	pChatController.imgViewSrc = '/resources/static/img/photo.png';
                $("#pPhotoInput").val('');
            }
        });

        avalon.scan();
        
        
        pChatController.pMsgList.$watch('length', function( a, b) {
        	initData();
        });
        
        
        
        function init(){
        	privacySocket = socket;
        	// 保存toId
        	pChatController.toWho = avalon.vmodels.rootController.toWho;
//        	document.title
            /* qqface */
            setTimeout(function(){
                $('.emoji-btn').qqFace({
                    id : 'facebox', //表情容器的ID
                    assign:'sendIpt', //文本框
                    path:'/resources/js/qqface/face/',	//表情存放的路径
                    container:'faceCtn'
                });
            },300);
            /* qqface */
            getMsg();
        }
        
        init();

      
       
        // 从消息池获取消息
        function getMsg(){
        	// 从消息池里面抽出消息
            pChatController.pMsgList = [];
            avalon.vmodels.rootController._privacyMsg.forEach(function(item){
            	if(item.sender.id == pChatController.toWho.id){
            		pChatController.pMsgList.push(item);
            	}
            });
            initData();
        }
        
        

        // compile QQ faceCode
        function replace_em(str){
            str = str.replace(/\</g,'&lt;');
            str = str.replace(/\>/g,'&gt;');
            str = str.replace(/\n/g,'<br/>');
            str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/js/qqface/face/$1.gif'/>");
            return str;
        };
        /* qqface */
        
        
        

        var textContain = $(".msg-block-contain");
        var textcontainer = $(".msg-block-container");




        $(".msg-block-contain").scroll(function(e){
            var $this =$(this),
                viewH =$(this).height(),//可见高度
                contentH =$(this).get(0).scrollHeight,//内容高度
                scrollTop =$(this).scrollTop();//滚动高度
            if(scrollTop - (contentH - viewH) == 0){
                $(this).scrollTop(contentH - viewH - 1);
            }
            if(scrollTop == 0){
                $(this).scrollTop(1);
            }
        });


        // 获取初始化数据 滑动到底部
        function initData(){
        	 $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 100},500,'swing');
        }

        // init Swiper
        function showPhoto(){
            var swiper = new Swiper('.swiper-container',{
                preventClicks : false,
                preventLinksPropagation : false,
                touchRatio : 1,
                lazyLoading : true,
            });
        }

        
        /*  图片压缩 上传 */
        //    用于压缩图片的canvas
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext('2d');

        //    瓦片canvas
        var tCanvas = document.createElement("canvas");
        var tctx = tCanvas.getContext("2d");

        //    使用canvas对大图片进行压缩
        function compress(img) {
            var initSize = img.src.length;
            var width = img.width;
            var height = img.height;

            //如果图片大于四百万像素，计算压缩比并将大小压至400万以下
            var ratio;
            if ((ratio = width * height / 4000000) > 1) {
                ratio = Math.sqrt(ratio);
                width /= ratio;
                height /= ratio;
            } else {
                ratio = 1;
            }

            canvas.width = width;
            canvas.height = height;

//            铺底色
            ctx.fillStyle = "#fff";
            ctx.fillRect(0, 0, canvas.width, canvas.height);

            //如果图片像素大于100万则使用瓦片绘制
            var count;
            if ((count = width * height / 1000000) > 1) {
                count = ~~(Math.sqrt(count) + 1); //计算要分成多少块瓦片

//                计算每块瓦片的宽和高
                var nw = ~~(width / count);
                var nh = ~~(height / count);

                tCanvas.width = nw;
                tCanvas.height = nh;

                for (var i = 0; i < count; i++) {
                    for (var j = 0; j < count; j++) {
                        tctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio, nh * ratio, 0, 0, nw, nh);

                        ctx.drawImage(tCanvas, i * nw, j * nh, nw, nh);
                    }
                }
            } else {
                ctx.drawImage(img, 0, 0, width, height);
            }

            //进行最小压缩
            var ndata = canvas.toDataURL('image/jpeg', 0.1);

            alert("图片大于200KB 进行压缩 压缩前：" + initSize + '压缩后：' + ndata.length);

            tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;

            return ndata;
        }

        /*  图片压缩 上传  */
        

        $(".msg-block-contain").scroll(function(e){
            var $this =$(this),
                viewH =$(this).height(),//可见高度
                contentH =$(this).get(0).scrollHeight,//内容高度
                scrollTop =$(this).scrollTop();//滚动高度
            if(scrollTop - (contentH - viewH) == 0){
                $(this).scrollTop(contentH - viewH - 1);
            }
            if(scrollTop == 0){
                $(this).scrollTop(1);
            }
        });

        return privacyChat = {
            'init':function(){
                init();
            },
        }
});