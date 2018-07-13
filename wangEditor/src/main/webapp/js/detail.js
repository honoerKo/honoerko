$("#query").click(function(){
	fid = $("#fid").val();
	pageData();
})

var title;
function edit(fid){
	$.ajax({
		url : "we/query",
		data : {
			"fid":fid
		},
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			if (data.value == "1") {
				$('#showdiv').show()
				title = data.title
				var id = document.getElementById("id")
				id.innerHTML = data.fid
				var show = document.getElementById("show")
				show.innerHTML = "<h3>标题：" + title + "</h3>" + data.msg
			} else {
				alert(data.msg)
			}
		},
		error : function() {
			$('#WaitLog').hide()
			alert("数据错误！")
		}
	})
}

function dele(fid){
	if(confirm("确认删除该条数据？")){
		$.ajax({
			url : "we/delete",
			data : {
				"fid" : fid
			},
			type : "POST",
			dataType : "JSON",
			success : function(data){
				if(data.msg == 0){
					alert("删除失败！")
				}else{
					$.ajax({
						url : "we/pagedata",
						data : {
							"currentPage" : currentPage
							,"pageSize" : pageSize
							,"fid" : null
						},
						type : "POST",
						dataType : "JSON",
						success : function(data){
							$("#xiye").html(data.pageNum);
							currentPage = data.pageNum;
							$("#mo").html(data.pages);
							totalPage = data.pages;
							$("#size").html(pageSize);
							var tr = '<tr><td class="wid1"><div class="tbody_div td_lineheight"></div></td><td class="wid2"><div class="tbody_div td_lineheight"></div></td><td class="wid3"><div class="tbody_div detail"></div></td><td class="wid4"><div class="tbody_div td_lineheight"></div></td></tr>'
							$("#tbody").html("")
							for(var i in data.list){
								$('table[id="table"] tbody').append(tr)
								$('table[id="table"] tbody tr:eq('+i+') td:eq(0) div').text(data.list[i].fid)
								$('table[id="table"] tbody tr:eq('+i+') td:eq(1) div').text(data.list[i].title)
								$('table[id="table"] tbody tr:eq('+i+') td:eq(2) div').text(data.list[i].detail)
								$('table[id="table"] tbody tr:eq('+i+') td:eq(3) div').append("<img class='edit_img' src='./img/edit.png' title='查看' onclick='edit("+data.list[i].fid+")' />&nbsp;&nbsp;<img class='dele_img' src='./img/dele.png' title='删除' onclick='dele("+data.list[i].fid+")' />")
							}
						},
						error : function(){
							alert("服务器响应失败！")
						}
					})
				}
			},
			error : function(){
				alert("服务器响应失败！")
			}
		})
	}
}

//打印Word
function toWord() {
	$('#show').wordExport(title)
}

function back() {
	$('#showdiv').hide()
}

function replace() {
	var id = $("#id").html()
	window.location.href = "we/replace?fid="+id
}