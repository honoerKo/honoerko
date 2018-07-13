// 取消打印log
wangEditor.config.printLog = false
var editor = new wangEditor('area')
editor.config.menus = $.map(wangEditor.config.menus, function(item, key) {
	if (item === 'source') {
		// 取消查看源码菜单
		return null;
	}
	if (item === 'emotion') {
		// 取消表情菜单
		return null;
	}
	if (item === 'video') {
		// 取消视频菜单
		return null;
	}
	if (item === 'location') {
		// 取消百度地图菜单
		return null;
	}
	return item;
})
// 配置图片上传端口
editor.config.uploadImgUrl = 'we/uploadImg'
editor.config.uploadImgFileName = 'myFileName'
editor.config.uploadParams = {
    user: $('#username').html()
}
// 取消网络图片菜单
editor.config.hideLinkImg = true
editor.create()

var title

$('#obtain').click(function() {
	// 读取 HTML,包括文本和标签
	alert(editor.$txt.html())
})

//查询所有数据
$('#all').click(function() {
	window.location.href = "we/detail"
})

$('#emptied').click(function() {
	// 一键清空
	editor.$txt.html('<p><br></p>')
})

// 监听title输入框
$('#title').bind('input propertychange', function() {
	$('.div_input').css('border-color','#ccc')
	var title = $('#title').val()
	if (title == null || title == "") {
		$('.div_input').css('border-color','red')
	}
})

$('#query').click(function() {
	$('#WaitLog').show()
	//查询出最后发布的那条数据
	$.ajax({
		url : "we/queryByMaxId",
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			$('#WaitLog').hide()
			if (data.value == "1") {
				$('#showdiv').show()
				title = data.title
				var div = document.getElementById('show')
				div.innerHTML = "<h3>" + title + "</h3>" + data.msg
			} else {
				alert(data.msg)
			}
		},
		error : function() {
			$('#WaitLog').hide()
			alert("数据错误！")
		}
	})
})

$('#release').click(function() {
	//获取编辑的内容传回controller层
	var fid = $('#fid').html()
	var title = $('#title').val()
	var detail = editor.$txt.html()
	if (title == null || title == "") {
		$('.div_input').css('border-color','red')
		alert("请输入标题！")
	} else {
		$('#WaitLog').show()
		$.ajax({
			url : "we/saveOrReplace",
			data : {
				"detail" : detail
				,"title" : title
				,"fid" : fid
			},
			type : "POST",
			dataType : "JSON",
			success : function(data) {
				$('#WaitLog').hide()
				alert(data.msg)
			},
			error : function() {
				$('#WaitLog').hide()
				alert("系统错误，请刷新重试！")
			}
		})
	}
})

//打印Word
function toWord() {
	$('#show').wordExport(title)
}

function back() {
	$('#showdiv').hide()
}