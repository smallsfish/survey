<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta name="keywords" content="留守儿童问卷调查" />
	<title>留守儿童问卷调查登录</title>
	<link rel="stylesheet" type="text/css" href="../../../../../../../../Users/Administrator/Desktop/wjdc/WebContent/css/style.css">
	<script type="text/javascript" src="../../../../../../../../Users/Administrator/Desktop/wjdc/WebContent/js/jquery-3.2.1.min.js"></script>
</head>
<body>
	<img id="bobybg" src="../../../../../../../../Users/Administrator/Desktop/wjdc/WebContent/img/bg.jpg">
	<div id="contentbody">
		<form action="javascript:void(0)" method="POST" onsubmit="return volidateSubmit()">
			<table cellspacing="30" >
				<tr>
					<td>留守儿童问卷调查</td>
				</tr>
				<tr>
					<td>
						<input type="text" name="uname" placeholder="请输入您的姓名" required="required"><br>
						<input id="phone" type='text' title="请输入正确手机号！" name="cellphone" required="required" maxlength="11" minlength="11" placeholder="请输入您的手机号" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" pattern="^1[3-9]\d{9}$" />
					</td>
				</tr>
				<tr>
					<td><input type="submit" value="登录"></td>
				</tr>
			</table>
			<img style="top:133px;left:90px;" src="../../../../../../../../Users/Administrator/Desktop/wjdc/WebContent/img/user.png">
			<img style="top:184px;left:90px;" src="../../../../../../../../Users/Administrator/Desktop/wjdc/WebContent/img/tel.png">
		</form>
	</div>
</body>
<script type="text/javascript">
	var windowHeight;
	var windowWidth;
	setContent();
	function setContent(){
		windowHeight=$(window).height();
		windowWidth=$(window).width();
		$("#contentbody").css({"top":(windowHeight-350)/2,"left":(windowWidth-500)/2});
	}

	function volidateSubmit() {
		window.location.href="../../../../../../../../Users/Administrator/Desktop/wjdc/WebContent/index.html";
		return false;
	}
	
	
	loadimg();
	//加载背景图的函数，该函数使图片适配所有屏幕的尺寸大小，尽可能的让图片显示正常
	function loadimg(){
		var imgheight=$("#bobybg").height();
		var imgwidth=$("#bobybg").width();
		var imgbl=imgheight/imgwidth;//图片高于宽的比例
		windowHeight=$(window).height();
		windowWidth=$(window).width();
		var width=0,height=0,top=0,left=0;
		//如果图片的高度和宽度都大于屏幕的宽高
		if(imgheight>windowHeight && imgwidth>windowWidth){
			if(imgwidth<imgheight){
				//图片宽小于高
				width=windowWidth;
				height=width*imgbl;
			}else{
				//图片宽大于高
				height=windowHeight;
				width=height*(1/imgbl);
			}
		}else if(imgheight>windowHeight && imgwidth<windowWidth){
			//如果图片的高大于屏幕的高但是图片的宽小于屏幕的宽

		}else if(imgheight<windowHeight && imgwidth>windowWidth){
			//如果图片的高小于屏幕的高但是图片的宽大于屏幕的宽

		}else{
			//如果图片的宽高都小于屏幕的宽高
		}
		//如果按比例缩放后还出现图片的高度或宽度小于屏幕的宽高，就再次进行等比例缩放
		if(width<windowWidth){
			width=windowWidth;
			height=width*imgbl;
		}
		if(height<windowHeight){
			height=windowHeight;
			width=height*(1/imgbl);
		}
		if(width>windowWidth){
			left=-(width-windowWidth)/2;
		}else if(height>windowHeight){
			top=-(height-windowHeight)/2;
		}
		$('#bobybg').css({"width":width,"height":height,"top":top,"left":left});
	}
	window.onresize=function(){
		setContent();
		loadimg();
	}
</script>
</html>