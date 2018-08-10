<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>wangEditor</title>
    
	<link rel="stylesheet" type="text/css" href="./css/editor.css">
    <link rel="stylesheet" type="text/css" href="./css/wangEditor.css">
    <link rel="stylesheet" type="text/css" href="./css/railscasts.css">

  </head>

  <body>
	  <div style="min-width: 900px;"> 	
	   	<div class="user_div">
	   		<p class="user_p">
	   			用户：<span id="username"></span>
	   			<!-- <a href="" style="text-decoration: underline;margin-left: 10px;">[登出]</a> -->
	   		</p>
	   	</div>
	   	<div id="fid" style="display: none;">${fid}</div>
	   	<!-- title -->
	   	<div class="div_title">
	   		<div class="div_lable">
	   			<label class="lable" for="title">标题：</label>
	   		</div>
	   		<div class="div_input">
	   			<input type="text" id="title" class="Title" value="${title }" autofocus="autofocus" placeholder="请输入文本标题">
	   		</div>
	   	</div>
	   	<div class="div"></div>
	   	<div>
		  	<!-- wangEditor富文本编辑框 start -->
		    <div id="area" class="downEdit">${detail }</div>
		    <!-- END -->
		</div>
	    <!--  -->
		<div id="div_but">
			<button id="obtain" class="btn ob">获取HTML</button>
			<button id="all" class="btn ob">查询全部数据</button>
			<button id="query" class="btn ob">查询</button>
			<button id="emptied" class="btn float em">清空</button>
			<button id="release" class="btn float">发布</button>
		</div>
		<!-- 数据显示栏 -->
		<div id="showdiv" class="showdata">
			<div id="show"></div>
			<div style="padding-top: 10px; color: #ccc"></div>
			<div style="width: 100%;height: 25px;">
				<button onclick="back()" style="height: 100%;">关闭</button>
				<button onclick="toWord()" style="height: 100%;">生成word文档</button>
			</div>
		</div>
		<!-- 等待提示框 -->
		<div id="WaitLog" class="waitLog">   
			<img src="./img/loading.gif" />  
		</div>
		
		<div id="Intercept" class="waitLog">   
			<div>
				您还未登录，请先登录
				<br>
				<button onclick="toLanding()">跳转登录</button>
			</div>
		</div>
	  </div>
	  
	  <script src="./js/jQuery-2.1.4.min.js" type="text/javascript"></script>
	  <script src="./js/FileSaver.js" type="text/javascript"></script>
	  <script src="./js/jquery.wordexport.js" type="text/javascript"></script>
      <script src="./js/wangEditor.js" type="text/javascript"></script>
      <script src="./js/editor.js" type="text/javascript"></script>
  </body>
</html>
