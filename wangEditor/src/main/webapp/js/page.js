//当前页码
var currentPage = 1;
//每页条数
var pageSize = 5;
//总页码
var totalPage;
//id查询条件
var fid = null;

pageData();
//分页的显示与隐藏
$("#xifenye").click(function(){
	$("#uljia").empty();
	$("#xab").toggle();
	//显示出的一共多少页
	var uljia = $("#uljia");
	var page = parseInt($("#xiye").html());//获取当前的页数
	var pages = parseInt($("#mo").html());//获取当前的总页数
	for(var i = 1;i <= pages;i++){
		var H = "<li  onclick='fl("+i+","+pages+")'>"+i+"</li>";//添加一共多少页和点击事件		
		uljia.append(H);
	}
	scrolltop(page);
})
//点击分页显示的方法
function fl(p1,p2){
	currentPage = p1;
	pageData();
}
//每页显示条数
$("#page").click(function(){
	$("#sab").toggle();
})
//修改每页显示数据条数
function f2(p){
	currentPage = 1;
	pageSize = p;
	pageData();
}
//分页的的上一页
function topclick(){
	currentPage--;
	pageData();
	scrolltop(currentPage);
}
//分页的的下一页
function downclick(){
	currentPage++;		
	pageData();
	scrolltop(currentPage);
}
//分页的的首页
$("#first").bind("click",function(){
	currentPage=1;
	pageData();
	scrolltop(currentPage);
})
//分页的的尾页
$("#last").bind("click",function(){
	weiye();
	currentPage=totalPage;
	pageData();
	scrolltop(currentPage);
})
//滚动条
function scrolltop(top){
	var hei=25*top-25;
	$("#xab").scrollTop(hei);
}
//ajax分页数据
function pageData(){
	$.ajax({
		url : "we/pagedata",
		data : {
			"currentPage" : currentPage
			,"pageSize" : pageSize
			,"fid" : fid
		},
		type : "POST",
		dataType : "JSON",
		success : function(data){
			$("#xiye").html(data.pageNum);
			currentPage = data.pageNum;
			$("#mo").html(data.pages);
			totalPage = data.pages;
			$("#size").html(pageSize);
			var tr = '<tr><td class="wid1"><div class="tbody_div td_lineheight"></div></td><td class="wid2"><div class="tbody_div td_lineheight"></div></td><td class="wid3"><div class="tbody_div detail"></div></td><td class="wid4"><div class="tbody_div td_lineheight"></div></td></tr>';
			$("#tbody").html("");
			for(var i in data.list){
				$('table[id="table"] tbody').append(tr);
				$('table[id="table"] tbody tr:eq('+i+') td:eq(0) div').text(data.list[i].fid);
				$('table[id="table"] tbody tr:eq('+i+') td:eq(1) div').text(data.list[i].title);
				$('table[id="table"] tbody tr:eq('+i+') td:eq(2) div').text(data.list[i].detail);
				$('table[id="table"] tbody tr:eq('+i+') td:eq(3) div').append("<img class='edit_img' src='./img/edit.png' title='查看' onclick='edit("+data.list[i].fid+")' />&nbsp;&nbsp;<img class='dele_img' src='./img/dele.png' title='删除' onclick='dele("+data.list[i].fid+")' />");
			}
		},
		error : function(){
			alert("服务器响应失败！");
		}
	})
}
//确定总页数
function weiye(){
	$.ajax({
		url : "we/weiye",
		data : {
			"pageSize" : pageSize
		},
		type : "POST",
		dataType : "JSON",
		success : function(data){
			$("#mo").html(data.pages);
			totalPage = data.pages;
		}
	})
}
