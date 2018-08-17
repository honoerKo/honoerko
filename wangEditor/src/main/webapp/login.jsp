<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录</title>

	<link rel="stylesheet" type="text/css" href="css/login.css">
	
	<style type="text/css">
        html, body {
		  overflow: hidden;
		  margin: 0;
		}
    </style>
  </head>
  
  <body>
  	<div class="form">
		<div id="landing">登录</div>
		<div id="registered">注册</div>
		<div class="fix"></div>
		<div id="landing-content">
			<div id="photo">
				<img src="img/editor.png" />
			</div>
			<div class="inp">
				<input type="text" id="username" class="input" placeholder="用户名" autofocus="autofocus" />
			</div>
			<div class="inp">
				<input type="password" id="password" class="input" placeholder="密码" />
				<div class="msg"></div>
			</div>
			<div class="inp">
				<button class="login" value="1">登录</button>
			</div>
			<div id="bottom">
				<span id="forgotpassword">忘记密码</span>
			</div>
		</div>
		<div id="registered-content">
			<div id="registered-msg"></div>
			<div class="inp">
				<input type="text" id="newuser" class="input" placeholder="用户名" maxlength="10" /> <!-- onkeyup="value=value.replace(/[\W]/ig, '')" -->
				<div class="msg"></div>
			</div>
			<div class="inp">
				<input type="password" id="newpwd" class="input" placeholder="密码" />
				<div class="msg"></div>
				<div id="pwdmsg">
					<div class="msg">长度为8-16个字符</div>
					<div class="msg">只能使用字母、数字、下划线</div>
				</div>
			</div>
			<div class="inp">
				<div class="code-prefix">
					<input type="text" id="nation" readonly="readonly" value="+86" />
					<img alt="0" src="img/down.png">
				</div>
				<input type="tel" id="tel" placeholder="手机号码" />
				<div class="msg"></div>
			</div>
			<div class="inp" id="code-block" style="display: none;">
				<input type="text" id="code" placeholder="短信验证码" />
				<button id="sendcode">发送短信验证码</button>
				<div class="msg"></div>
			</div>
			<div class="inp">
				<button class="login" value="0">立即注册</button>
			</div>
		</div>
	</div>
	<div class="codemsg"></div>
	
	<script type="text/javascript" src="js/jQuery-2.1.4.min.js"></script>
	<script type="text/javascript" src="js/canvas.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
  </body>
</html>
