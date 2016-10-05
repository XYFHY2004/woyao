<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我要-聊天室</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="apple-mobile-web-app-title" content="I want">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <script type="text/javascript" src="/resources/plugin/fastclick-master/lib/fastclick.js"></script>
    <link rel="stylesheet" href="/resources/static/css/msg.css"/>
    <link rel="SHORTCUT ICON" href=""/>
</head>
<body ms-controller="butTom">
        <div class="all">
                <div class="all-top">
                    <p class="p" ms-on-tap="allChang" ms-class="bottom:showBottom">全部</p>
                    <p class="p" ms-on-tap="girlsChang" ms-class="bottom:showBottoms">美女</p>
                    <p class="p" ms-on-tap="boysChang" ms-class="bottom:showBottomss">帅哥</p>
                    <p class="p" ms-on-tap="showChang" ms-class="bottom:showBottomls">消息</p>
                </div>
            <!--消息界面-->
            <div ms-if="changHide">
            <div class="rank-list">
                <div class="fist">
                    <img src="/resources/static/img/head.jpg" alt="">
                    <div class="rank-name">
                        <div><span>给我你的心</span><img src="/resources/static/img/weman1.png" alt="">
                        </div>
                        <div class="rank-number">你在干嘛</div>
                    </div>
                    <div class="rank-right">
                        <p>11:30</p>
                        <p class="number-one">1</p>
                    </div>
                </div>
            </div>
            <div class="rank-list">
                <div class="fist">
                    <img src="/resources/static/img/head.jpg" alt="">
                    <div class="rank-name">
                        <div><span>给我你的心</span><img src="/resources/static/img/weman1.png" alt="">
                        </div>
                        <div class="rank-number">你在干嘛</div>
                    </div>
                    <div class="rank-right">
                        <p>11:30</p>
                        <p class="number-one" ms-hover="hover">1</p>
                    </div>
                </div>
            </div>
            </div>
            <!--全部界面-->
            <div  ms-if="all">
                <div class="alls">
                     <div class="all-left">
                         <img src="/resources/static/img/head.jpg" alt="">
                         <div class="all-left-text">
                             <span class="text">给我你的心</span>
                             <img src="/resources/static/img/weman1.png" alt="">
                             <p>距离5千米</p>
                         </div>
                     </div>
                    <div class="all-right">
                        <p>上午10：30</p>
                        <p ms-on-tap="chat">发消息</p>
                    </div>
                </div>
                <div class="alls">
                    <div class="all-left">
                        <img src="/resources/static/img/head.jpg" alt="">
                        <div class="all-left-text">
                            <span class="text">给我你的心</span>
                            <img src="/resources/static/img/weman1.png" alt="">
                            <p>距离5千米</p>
                        </div>
                    </div>
                    <div class="all-right">
                        <p>上午10：30</p>
                        <p ms-on-tap="chat">发消息</p>
                    </div>
                </div>
                <div class="alls">
                    <div class="all-left">
                        <img src="/resources/static/img/head.jpg" alt="">
                        <div class="all-left-text">
                            <span class="text">给我你的心</span>
                            <img src="/resources/static/img/weman1.png" alt="">
                            <p>距离5千米</p>
                        </div>
                    </div>
                    <div class="all-right">
                        <p>上午10：30</p>
                        <p ms-on-tap="chat">发消息</p>
                    </div>
                </div>
            </div>
            <!--美女界面-->
            <div ms-if="girls">
                <div class="alls">
                    <div class="all-left">
                        <img src="/resources/static/img/head.jpg" alt="">
                        <div class="all-left-text">
                            <span class="text">给我你的心</span>
                            <img src="/resources/static/img/weman1.png" alt="">
                            <p>距离5千米</p>
                        </div>
                    </div>
                    <div class="all-right">
                        <p>上午10：30</p>
                        <p ms-on-tap="chat">发消息</p>
                    </div>
                </div>
            </div>
            <!--帅哥界面-->
            <div ms-if="boys">
                <div class="alls">
                    <div class="all-left">
                        <img src="/resources/static/img/head.jpg" alt="">
                        <div class="all-left-text">
                            <span class="text">给我你的心</span>
                            <img src="/resources/static/img/weman1.png" alt="">
                            <p>距离5千米</p>
                        </div>
                    </div>
                    <div class="all-right">
                        <p>上午10：30</p>
                        <p>发消息</p>
                    </div>
                </div>
                <div class="alls">
                    <div class="all-left">
                        <img src="/resources/static/img/head.jpg" alt="">
                        <div class="all-left-text">
                            <span class="text">给我你的心</span>
                            <img src="/resources/static/img/weman1.png" alt="">
                            <p>距离5千米</p>
                        </div>
                    </div>
                    <div class="all-right">
                        <p>上午10：30</p>
                        <p ms-on-tap="chat">发消息</p>
                    </div>
                </div>
            </div>
        </div>
</body>
<script type="text/javascript" src="/resources/plugin/avalon-master/dist/avalon.mobile.min.js"></script>
<script type="text/javascript" src="/resources/static/js/msg.js"></script>
</html>