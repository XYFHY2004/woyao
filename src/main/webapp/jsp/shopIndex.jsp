<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>大屏幕</title>
</head>
<link rel="stylesheet" href="/show/resources/show.css">
<script src="/show/resources/jquery-1.9.1/jquery.min.js"></script>
<body>
<div class="wall-all-ctn">
    <div class="wall-all">
        <div class="wall-all-left">
            <div>
                <img class="wall-all-left-img" src="/show/resources/img/man.png" alt="">
                <p><span>Hmily671. <img src="/show/resources/img/woman.png" class="wall-sexy" alt=""></span></p>
                <p class="wall-p"><span class="wall-text">我要霸屏30秒：</span>生活不止眼前的苟且，还有诗和远方</p>
            </div>
        </div>
        <div class="wall-all-right">
            <img class="wall-all-right-img" src="/show/resources/img/1_07.png" alt="">
            <!--<div class="across-one"></div>-->
            <!--<div class="across-two"></div>-->
            <!--<div class="stand-one"></div>-->
            <!--<div class="stand-two"></div>-->
        </div>
    </div>
</div>
<div class="all">
    <div class="wrap">
        <ul id="aUl">
            <li class="show0">
                <div class="show-back">
                    <img class="show-header" src="/show/resources/img/portrait.png" alt="">
                    <p class="show-name"></p>
                    <p class="show-time">欢迎光临</p>
                </div>
            </li>
            <li class="show1">
                <div class="show-back">
                    <img class="show-header" src="/show/resources/img/portrait.png" alt="">
                    <p class="show-name"></p>
                    <p class="show-time">欢迎光临</p>
                </div>
            </li>
            <li class="show2">
                <div class="show-back">
                    <img class="show-header" src="/show/resources/img/portrait.png" alt="">
                    <p class="show-name"></p>
                    <p class="show-time">欢迎光临</p>
                </div>
            </li>
            <li class="show3">
                <div class="show-back">
                    <img class="show-header" src="/show/resources/img/portrait.png" alt="">
                    <p class="show-name"></p>
                    <p class="show-time">欢迎光临</p>
                </div>
            </li>
            <li class="show4">
                <div class="show-back">
                    <img class="show-header" src="/show/resources/img/portrait.png" alt="">
                    <p class="show-name"></p>
                    <p class="show-time">欢迎光临</p>
                </div>
            </li>
        </ul>
    </div>
    <div class="chat">
    <div>
        <div class="chat-text m0">
            <img class="chat-text-img" src="/show/resources/img/portrait.png" alt="">
            <div class="chat-right">
                <p class="right-name">1<img src="/show/resources/img/woman.png" alt=""></p>
                <p class="right-tip">欢迎光临</p>
            </div>
            <div class="chat-time">23:30</div>
        </div>
        <div class="chat-text m1">
            <img class="chat-text-img" src="/show/resources/img/portrait.png" alt="">
            <div class="chat-right">
                <p class="right-name">2<img src="/show/resources/img/woman.png" alt=""></p>
                <p class="right-tip">欢迎光临</p>
            </div>
            <div class="chat-time">23:30</div>
        </div>
        <div class="chat-text m2">
            <img class="chat-text-img" src="/show/resources/img/portrait.png" alt="">
            <div class="chat-right">
                <p class="right-name">3<img src="/show/resources/img/woman.png" alt=""></p>
                <p class="right-tip">欢迎光临</p>
            </div>
            <div class="chat-time">23:30</div>
        </div>
        <div class="chat-text m3">
            <img class="chat-text-img" src="/show/resources/img/portrait.png" alt="">
            <div class="chat-right">
                <p class="right-name">4<img src="/show/resources/img/woman.png" alt=""></p>
                <p class="right-tip">欢迎光临</p>
            </div>
            <div class="chat-time">23:30</div>
        </div>
        <div class="chat-text m4">
            <img class="chat-text-img" src="/show/resources/img/portrait.png" alt="">
            <div class="chat-right">
                <p class="right-name">5<img src="/show/resources/img/woman.png" alt=""></p>
                <p class="right-tip">欢迎光临</p>
            </div>
            <div class="chat-time">23:30</div>
        </div>
     </div>
    </div>
</div>
</body>
<script>
    window.onload = function () {

        var socket = undefined;
        // 创建一个Socket实例
        var isHttps = ('https:' == window.location.protocol);
        var protocol = 'ws://';
        if (isHttps) {
            protocol = 'wss://';
        }
        socket = new WebSocket(protocol + window.location.host + '/mobile/chat/socket');

        // 打开Socket
        socket.onopen = function(event) {
            console.log("聊天室连接成功");
        };

        socket.onmessage = function(message) {
            var msg = JSON.parse(message.data);
            console.log("get masage:");
            console.log(msg);

            if(msg.command == 'err'){
                alert(msg.reason);
                return
            }
            if(msg.command == 'selfInfo'){
                return;
            }
            if(msg.command == 'roomInfo'){
                return;
            }
            if(msg.command == 'prePay'){

            }
        };


        setTimeout(function(){
            var msg = {name:'asssssssssss1',msg:'pasasaasasddasdasdasp1',pic:'/show/resources/img/portrait.png'};
            msgList.push(msg);
        },5000);

        var msgList = [{name:'pp1',msg:'pp1',pic:'/show/resources/img/portrait.png'},{name:'pp2',msg:'pp2',pic:'/show/resources/img/portrait.png'},{name:'pp3',msg:'pp3',pic:'/show/resources/img/portrait.png'}];
        var showIndex = 0;

        //轮播部分
        var oLi=document.getElementsByTagName('li');
        var classArr = ["show0","show1","show2","show3","show4"];
        function add(){
            var last=classArr.pop();
            classArr.unshift(last);
            for (var i = 0; i < classArr.length; i++) {
                oLi[i].className = classArr[i];
            }
            if(showIndex == msgList.length - 1){
                showIndex = 0;
            }else{
                showIndex++;
            }
            var changeBlock = $('.show4');
            changeBlock.find('.show-header').attr('src',msgList[showIndex].pic);
            changeBlock.find('.show-name').html(msgList[showIndex].name);
            changeBlock.find('.show-time').html(msgList[showIndex].msg);
            changeBlock.attr('style',"background:url('" + msgList[showIndex].pic + "')");
        }
        window.setInterval(function(){
            add();
        },3000);

/*===========================================================================================*/
        setTimeout(function(){
            var msg = {name:'asssssssssss1',msg:'pasasaasasddasdasdasp1',pic:'/show/resources/img/portrait.png'};
            allMsgList.push(msg);
        },5000);

        var allMsgList = [{name:'pp1',msg:'pp1',pic:'/show/resources/img/portrait.png'},{name:'pp2',msg:'pp2',pic:'/show/resources/img/portrait.png'},{name:'pp3',msg:'pp3',pic:'/show/resources/img/portrait.png'}];
        var allMsgIndex = 0;

        //轮播部分
        var msgLi=$('.chat-text');
        var msgClassArr = ["m0","m1","m2","m3","m4"];
        function allAdd(){
            var last=msgClassArr.pop();
            msgClassArr.unshift(last);
            for (var i = 0; i < msgClassArr.length; i++) {
                msgLi[i].className = msgClassArr[i];
            }
            if(allMsgIndex == allMsgList.length - 1){
                allMsgIndex = 0;
            }else{
                allMsgIndex++;
            }
            var changeBlock = $('.m4');
            changeBlock.find('.chat-text-img').attr('src',msgList[allMsgIndex].pic);
            changeBlock.find('.right-name').html(msgList[allMsgIndex].name);
            changeBlock.find('.right-tip').html(msgList[allMsgIndex].msg);
        }
        window.setInterval(function(){
            allAdd();
        },2000);
    }

</script>
</html>