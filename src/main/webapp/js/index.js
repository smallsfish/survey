(function ($) {
    $('document').ready(function () {
        var windowHeight=$(window).height();
        var windowWidth=$(window).width();
        //初始化主页视频样式
        initVideoList();

        function initVideoList() {
            var homeVideoWidth = $('.home-video').width();
            var videoListLis = $('.video-list li');
            var videoListLiWidth = videoListLis.width();
            var lisWidth = videoListLiWidth * videoListLis.length + 15 * (videoListLis.length - 1);
            for (var i = 0; i < videoListLis.length; i++) {
                $(videoListLis[i]).css({'left': (videoListLiWidth + 15) * i + 'px'});
            }
            if (!(homeVideoWidth > lisWidth)) {
                setInterval(function () {
                    var j=0;
                    for (var i = 0; i < videoListLis.length; i++) {
                        $(videoListLis[i]).animate({left: ($(videoListLis[i]).position().left - (videoListLiWidth + 15)) + 'px'},function(){
                            j++;
                            if(j==videoListLis.length){
                                for (var i = 0; i < videoListLis.length; i++) {
                                    if($(videoListLis[i]).position().left<0){
                                        $(videoListLis[i]).css({'left': (videoListLiWidth + 15) * (videoListLis.length-1) + 'px'});
                                    }
                                }
                                j=0;
                            }
                        });
                    }
                }, 2000);
            }
        }
        //v-video-list
        var vvlw=$(".v-video-list ul").width();
        if(vvlw!=undefined){
            var num=Math.floor(vvlw/244);
            if(num>5) num=5;
            var floatIndex=Math.floor((Math.floor(vvlw)-num*244)/(num+1));
            $(".v-video-list li").css({"margin-left":floatIndex+"px"});
        }
        initNewsTab();
        function initNewsTab(){
            $(".news-content li:eq("+0+")").show();
            $(".news-tab li:eq(0)").attr("class","news-tab-focus");
            $(".news-tab li").on("click",function(){
                clearNewsTab();
                $(".news-content li:eq("+$(".news-tab li").index($(this))+")").show();
                $(".news-tab li:eq("+$(".news-tab li").index($(this))+")").attr("class","news-tab-focus");
            });
        };
        function clearNewsTab(){
            $(".news-content li").hide();
            $(".news-tab li").attr("class","");
        }

        initPictureTab();
        function initPictureTab(){
            var pcw=$(".pictures-content").width();

            $(".pictures-content li:eq("+0+")").show();
            $(".pictures-tab li:eq(1)").attr("class","pictures-tab-focus");
            $(".pictures-tab").on("click",'li',function(){
                var index=$(".pictures-tab li").index($(this));
                if(index!=0){
                    clearPicturesTab();
                    $(".pictures-content li:eq("+(index-1)+")").show();
                    $(".pictures-tab li:eq("+index+")").attr("class","pictures-tab-focus");
                }
            });
            if(pcw!=undefined){
                var num=Math.floor(pcw/300);
                if(num>4) num=4;
                var floatIndex=Math.floor((Math.floor(pcw)-num*300)/(num+1));
                $(".p-picture-list").css({"margin-left":floatIndex+"px"});
            }
        };
        function clearPicturesTab(){
            $(".pictures-content li").hide();
            $(".pictures-tab li").attr("class","");
        }
        $(".pictures-content li").on("click",'div',function () {
            var showImg=$(".showImg");
            var imgIndex=$(".p-picture-list").index($(this));
            var imgSrc=$(".pictures-content img:eq(\""+imgIndex+"\")").attr("src");
            if(showImg.length==0){
                $("body").append("<div class=\"showImg\">" +
                    "    <img src="+imgSrc+" />" +
                    "</div>");
            }
            $(".showImg img").attr("src",imgSrc);
            $(".showImg").css({"width":windowWidth+"px","height":windowHeight+"px"});
            $(".showImg").show();
            $(".showImg").on("click",function () {
                $(".showImg").hide();
            });
        });

    });
})(jQuery);