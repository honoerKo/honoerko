<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>index</title>
    
	<link rel="stylesheet" type="text/css" href="./css/detail.css">

  </head>
  <body>
	  <div id="menu">
  	  	<div id="add">
  	  		<a href="we/editor">添加</a>
  	  	</div>
  	  	<div id="query_div">
  			<input type="text" name="fid" id="fid" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入序号">
  			<button id="query">查询</button>
  	  	</div>
  	  </div>
	  <div id="div_table">
	    <table id="table">
	    	<thead id="thead">
	    		<tr style="height: 30px;">
	    			<th class="wid1">序号</th>
	    			<th class="wid2">标题</th>
	    			<th class="wid3">内容</th>
	    			<th class="wid4">操作</th>
	    		</tr>
	    	</thead>
	    	<tbody id="tbody"></tbody>
	    </table>
	  </div>
	  <!-- include page 分页 -->
	  <jsp:include page="../page.jsp"></jsp:include>
	  <!-- 数据显示栏 -->
	  <div id="showdiv" class="showdata">
	  	<div id="id" style="display: none;"></div>
		<div id="show"></div>
		<div style="padding-top: 10px; color: #ccc"></div>
		<div style="width: 100%;height: 25px;">
			<button onclick="back()" style="height: 100%;">关闭</button>
			<button onclick="toWord()" style="height: 100%;">生成word文档</button>
			<button onclick="replace()" style="height: 100%;">重新编辑</button>
		</div>
	  </div>
	  
	  <script src="./js/FileSaver.js" type="text/javascript"></script>
	  <script src="./js/jquery.wordexport.js" type="text/javascript"></script>
	  <script src="./js/detail.js" type="text/javascript"></script>
  </body>
</html>
