<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>page</title>
    
	<link rel="stylesheet" type="text/css" href="./css/page.css">

  </head>
  
  <body>
    <div class="paging">
		<div class="wrap" style="margin-top: 10px;">
			<div class="fenye">
				<ul>
					<li id="first">首页</li>
					<li id="top" onclick="topclick()">上一页</li>
					<li class="xifenye" id="xifenye">
						<a id="xiye"></a>/<a id="mo"></a>
						<div class="xab" id="xab" style="display:none">
							<ul id="uljia"></ul>
						</div>
					</li>
					<li id="down" onclick="downclick()">下一页</li>
					<li id="last">尾页</li>
					<li class="page" id="page">
						每页 
						<a id="size" style="text-decoration: underline;"></a> 
						<div class="sab" id="sab" style="display: none">
							<ul id="ulnum">
								<li class="li" onclick="f2(5)">5</li>
								<li class="li" onclick="f2(10)">10</li>
								<li class="li" onclick="f2(15)">15</li>
								<li class="li" onclick="f2(20)">20</li>
							</ul>
						</div>
						条
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<script src="./js/jQuery-2.1.4.min.js" type="text/javascript"></script>
	<script src="./js/page.js" type="text/javascript"></script>
  </body>
</html>
