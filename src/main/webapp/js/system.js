$(document).ready(function() {


	//侧边栏菜单交互效果开始
	var menuIsClose=false;
	var windowHeight=$(document).height();
	var windowWidth=$(document).width();
	$('.system-side-menu-switch').bind("click",function(){
		
		//判断菜单是否是关闭状态，执行不同的交互逻辑
		if(!menuIsClose){
			$(".system-side").css({
				"animation":"menu-frame-close 1s ease forwards"
			});
			$(".system-content").css({
				"animation":"system-content-close 1s ease forwards"
			});
			menuIsClose=true;
		}else{
			$(".side-menu-small-items").css("left","-35px");
			$(".system-side").css({
				"animation":"menu-frame-open 1s ease forwards"
			});
			$(".system-content").css({
				"animation":"system-content-open 1s ease forwards"
			});
			menuIsClose=false;
			setTimeout(function(){
				$(".side-menu-items").css("left","0px");
			},300);
		}
		//设置主菜单关闭时小菜单弹出
		setTimeout(function(){
			if(menuIsClose){
				//关闭菜单项，弹出图标项
				$(".side-menu-items").css("left","-200px");
				$(".side-menu-small-items").css("left","0px");
			}
		},666);
		setTimeout(function(){
			tabnavwidth=$(".system-tab-nav").width();
			checkIsOutTab();
		},1000);
	});
	//清除菜单样式
	function clearMenuChildHeight(){
		$(".side-menu-item ul").css({"height":"0px","display":"none"});
		$(".side-menu-small-item ul").css({"height":"0px","display":"none"});
		$('.side-menu-square-open').attr('class','side-menu-square');
		$('.side-menu-small-square-open').attr('class','side-menu-small-square');
	}
	//获得侧边栏的最大高度
	var maxHeight=$('.system-side').height()-35-45*($(".side-menu-small-item-icon").length);
	//为侧边栏的每一个菜单按钮设置点击事件，弹出子菜单
	$(".side-menu-item-title").bind('click',function(){
		var i=$(".side-menu-item-title").index($(this));
		if($(this).next('ul').height()==0){
			//clearMenuChildHeight();
			$(this).children('span').attr('class','side-menu-square-open');
			$(".side-menu-small-item-icon:eq("+i+")").children('span').attr('class','side-menu-small-square-open');
			$(this).next('ul').css({"height":"auto","display":"block","max-height":maxHeight+'px'});
			$(".side-menu-small-item-icon:eq("+i+")").next('ul').css({"height":"auto","display":"block","max-height":maxHeight+'px'});
		}else{
			$(this).children('span').attr('class','side-menu-square');
			$(".side-menu-small-item-icon:eq("+i+")").children('span').attr('class','side-menu-small-square');
			//clearMenuChildHeight();
            $(".side-menu-item ul:eq("+i+")").css({"height":"0px","display":"none"});
            $(".side-menu-small-item ul:eq("+i+")").css({"height":"0px","display":"none"});
		}
	});
	//为侧边栏的每一个小图标菜单设置点击事件，弹出子菜单
	$(".side-menu-small-item-icon").bind('click',function(){
		
		var i=$(".side-menu-small-item-icon").index($(this));
		if($(this).next('ul').height()==0){
			//clearMenuChildHeight();
			$(this).children('span').attr('class','side-menu-small-square-open');
			$(".side-menu-item-title:eq("+i+")").children('span').attr('class','side-menu-square-open');
			$(this).next('ul').css({"height":"auto","display":"block","max-height":maxHeight+'px'});
			$(".side-menu-item-title:eq("+i+")").next('ul').css({"height":"auto","display":"block","max-height":maxHeight+'px'});
		}else{
			$(this).children('span').attr('class','side-menu-small-square');
			$(".side-menu-item-title:eq("+i+")").children('span').attr('class','side-menu-square');
			//clearMenuChildHeight();
            $(".side-menu-item ul:eq("+i+")").css({"height":"0px","display":"none"});
            $(".side-menu-small-item ul:eq("+i+")").css({"height":"0px","display":"none"});
		}
	});


	//侧边栏菜单交互效果结束


	//设置header菜单自适应开始
	var systemLogoWidth=$('.system-logo').width();
	var systemlayoutrightlis=$(".system-layout-right li");
	var lisLength=systemlayoutrightlis.length;
	var liWidth=systemlayoutrightlis.width();
	function listenerHeaderMenu(){
		systemLogoWidth=$('.system-logo').width();
		$('.system-layout-right').css({"max-width": (windowWidth- systemLogoWidth-30)+'px'});
	}
	//监听窗体变化事件
	listenerHeaderMenu();
	$(window).resize(function(){
		listenerHeaderMenu();
		tabnavwidth=$(".system-tab-nav").width();
		checkIsOutTab();
	});

	//设置header菜单自适应结束

	//tab选项卡增加删除开始
	//创建存放选项卡的数组
	var menuNames=new Array();
	var menuLis=$(".system-tab-nav ul li");
	var navulwidth=15;
	var tabnavwidth=$(".system-tab-nav").width();
	//将已经存在的选项卡放入到数组中
	for (var i =0; i < menuLis.length; i++) {
		menuNames[i]=$(menuLis[i]).attr("data-name");
		navulwidth+=$(menuLis[i]).width()+20;
	}
	checkIsOutTab();
	function checkIsOutTab(){
		if(navulwidth+5>tabnavwidth){
			removeMenuNamesById(1);
			$(".system-tab-nav ul li:eq("+1+")").remove();
			$('.system-content-show:eq('+1+')').remove();
			navulwidth-=($(".system-tab-nav ul li:eq("+1+")").width()+20);
			checkIsOutTab();
		}
	}
	//检测要打开的选项卡是否存在
	function checkMenuName(title){

		for (var i = 0; i < menuNames.length; i++) {
			if(menuNames[i]==title){
				//如果检查到tab选项卡中有对应的参数，就把当前选项卡切换到显示状态
				clearTabStyle();
				clearTabContent();
				$('.system-tab-nav ul li:eq('+i+')').addClass("system-tab-nav-item-selected");
				$('.system-content-show:eq('+i+')').css("display","block");
				return true;
			}
		}
		return false;
	}


	//选项卡交互开始
	//创建选项卡函数，参数是一个对象类型的{title*---标题，isShowClose*-是否显示关闭按钮 true显示，false不显示，url*-选项卡页面的url}
	createTab=function (obj){
		if(!checkMenuName(obj.title)){
			var button="";
			if(obj.isShowClose){
				button="<a href='javascript:;' class='system-tab-item-del-button'><img src='img/icon/icon-close.png'></a>"
			}
			clearTabStyle();
			$(".system-tab-nav ul").append('<li data-name="'+obj.title+'" class="system-tab-nav-item system-tab-nav-item-selected"><span title="'+obj.title+'">'+obj.title+'</span>'+button+'</li>');
			menuNames[menuNames.length]=obj.title;
			$('.system-content-iframe').append('<div class="system-content-show"><iframe src="'+obj.url+'"></iframe></div>');
			navulwidth+=$(".system-tab-nav ul li:eq("+(menuNames.length-1)+")").width()+20;
			checkIsOutTab();
		}
	}

	//清除所有的选项卡样式
	function clearTabStyle(){
		$('.system-tab-nav-item').each(function(){
			$(this).removeClass("system-tab-nav-item-selected");
		});
	}
	//清除所有选项卡内容
	function clearTabContent(){
		$('.system-content-show').css("display","none");
	}

	//为每一个tab选项卡动态设置点击事件
	$('.system-tab-nav ul').on('click','li',function(ev){
		var liid=$('.system-tab-nav ul li').index($(this));
		/*如果点击是当前显示的tab选项卡，就刷新当前页*/
		if($(this).hasClass("system-tab-nav-item-selected")){
			//刷新当前显示的iframe
			$('.system-content-show:eq('+liid+') iframe').attr('src',$('.system-content-show:eq('+liid+') iframe').attr('src'));
		}else{
			//切换选项卡
			clearTabStyle();
			clearTabContent();
			$('.system-tab-nav ul li:eq('+liid+')').addClass("system-tab-nav-item-selected");
			$('.system-content-show:eq('+liid+')').css("display","block");
		}
		ev.stopPropagation();
	});
	//根据下标删除数组中的指定元素
	function removeMenuNamesById(id){
		var tempArray=new Array();
		var j=0;
		for (var i = 0; i < menuNames.length; i++) {
			if(i!=id){
				tempArray[j]=menuNames[i];
				j++;
			}
		}
		menuNames=null;
		menuNames=tempArray;
	}
	//位有关闭按钮的选项卡设置点击事件
	$('.system-tab-nav ul').on('click','a',function(ev){
		var closeId=$('.system-tab-nav ul li').index($(this).parent());

		removeMenuNamesById(closeId);
		navulwidth-=($(".system-tab-nav ul li:eq("+(menuNames.length-1)+")").width()+20);
		
		$(this).parent().remove();
		$('.system-content-show:eq('+closeId+')').remove();
		if($(this).parent().hasClass('system-tab-nav-item-selected')){
			//删除自己将焦点回归到上一个tab
			if(menuNames.length!=0){
				$('.system-tab-nav ul li:eq('+(closeId-1)+')').addClass("system-tab-nav-item-selected");
				clearTabContent();
				$('.system-content-show:eq('+(closeId-1)+')').css("display","block");
				
			}
		}
		ev.stopPropagation();
	});
	//选项卡交互结束



	//侧边小选项卡鼠标进入与离开样式交互开始
	$(".side-menu-small-item-icon,.side-menu-small-item ul li").bind('mouseover',function(){
		var withBodyHeight=$(this).offset().top;
		var height=($(this).height());
		var data=$(this).attr("data-toast");
		if($('.stable-left-toast').length==0){
			$('body').append("<div class='stable-left-toast'>"+data+"</div>");
		}else{
			$('.stable-left-toast').html(data);
		}
		$('.stable-left-toast').css({"display":"block","padding":(height-$('.stable-left-toast').height())/2+'px',"left":$(this).width()+'px',"top":withBodyHeight-1+'px'});
	});
	$(".side-menu-small-item-icon,.side-menu-small-item ul li").bind('mouseout',function(){
		$('.stable-left-toast').css({"display":"none"});
	});
	//侧边小选项卡鼠标进入与离开样式交互结束
});