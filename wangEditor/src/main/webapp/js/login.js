$(".form").slideDown(500);

$("#landing").addClass("border-btn");

$("#registered").click(function() {
	$("#landing").removeClass("border-btn");
	$("#landing-content").hide(500);
	$(this).addClass("border-btn");
	$("#registered-content").show(500);
	
})

$("#landing").click(function() {
	$("#registered").removeClass("border-btn");
	$(this).addClass("border-btn");		
	$("#landing-content").show(500);
	$("#registered-content").hide(500);
})

$("#newuser").blur(function(){
	var u = $(this).val();
	if(u == ""){
		$("#newuser").next("div").show("fast",function(){
			$(this).html("昵称不能为空");
		});	
	}else{
		var req = /\s/;
		if(req.test(u)){
			$("#newuser").next("div").show("fast",function(){
				$(this).html("昵称不能有空格");
			});	
		}else{
			$("#newuser").next("div").hide("fast",function(){
				$(this).html("");
			})
			//ajax后台验证用户名是否唯一
		}
	}
})

$("#newpwd").click(function(){
	$("#newpwd").next("div").hide("fast",function(){
		$(this).html("");
	})
	$("#pwdmsg>div").show("fast");
})

$("#newpwd").blur(function(){
	$("#pwdmsg>div").hide("fast");
	var pwd = $(this).val();
	if(pwd == ""){
		$("#newpwd").next("div").show("fast",function(){
			$(this).html("密码不能为空");
		})
	}else{
		var req = /^[a-zA-Z0-9_]+$/;
		if(pwd.length < 8 || pwd.length > 16){
			$("#newpwd").next("div").show("fast",function(){
				$(this).html("长度为8-16个字符");
			})
		}else if(!req.test(pwd)){
			$("#newpwd").next("div").show("fast",function(){
				$(this).html("只能使用字母、数字、下划线");
			})
		}else{
			$("#newpwd").next("div").hide("fast",function(){
				$(this).html("");
			})
		}
	}
})

$("#newpwd").bind("input propertychange", function(){
	$("#newpwd").next("div").hide("fast",function(){
		$(this).html("");
	})
	var pwd = $(this).val();
	var req = /^[a-zA-Z0-9_]+$/;
	if(pwd.length >= 8 && pwd.length <= 16){
		$('div[id="pwdmsg"] div:eq(0)').css('color', '#29AD27');
	}else{
		$('div[id="pwdmsg"] div:eq(0)').css('color', 'red');
	}
	if(req.test(pwd)){
		$('div[id="pwdmsg"] div:eq(1)').css('color', '#29AD27');
	}else{
		$('div[id="pwdmsg"] div:eq(1)').css('color', 'red');
	}
})

$(".code-prefix").click(function(){
	var v = $("#nation").next("img").attr("alt");
	if(v == "0"){
		$("#nation").next("img").attr({"src":"img/up.png","alt":"1"});
	}else if(v == "1"){
		$("#nation").next("img").attr({"src":"img/down.png","alt":"0"});
	}
})

$("#tel").click(function(){
	$("#tel").next("div").hide("fast",function(){
		$(this).html("");
	})
	$("#code-block").show("fast");
})  

$("#tel").blur(function(){
	var flag = $("#sendcode").is(":hover");
	if(flag == false){
		$("#code-block").hide("fast");
	}
})
        
$("#sendcode").click(function(){
	var tel = $("#tel").val();
	var req = /^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(166)|(17[0-9])|(18[0-9])|(19[8|9]))\d{8}$/;
	if(!req.test(tel)){
		$("#tel").next("div").show("fast",function(){
			$(this).html("手机号有误");
		})
	}else{
		$("#tel").next("div").hide("fast",function(){
			$(this).html("");
		})
		/*$.ajax({
			url: "user/sendCode",
			data: {
				"tel": tel
			},
			type: "POST",
			dataType: "JSON",
			success: function(data){
				if (data.value == 0) {
					$("#sendcode").css("background","");
					settime();
				}
				$(".codemsg").show(function(){
					$(".codemsg").html(data.msg);
				});
				setTimeout(function() {
					$(".codemsg").hide(function(){
						$(".codemsg").html("");
					});
				}, 2000);
			},
			error: function(){
				$(".codemsg").show(function(){
					$(".codemsg").html("服务器响应失败");
				});
				setTimeout(function() {
					$(".codemsg").hide(function(){
						$(".codemsg").html("");
					});
				}, 2000);
			}
		})*/
	}
})
var countdown = 10;
function settime() {
	if (countdown == 0) {
		document.getElementById("sendcode").removeAttribute("disabled");
		document.getElementById("sendcode").innerHTML = "发送短信验证码";
		countdown = 10;
	} else {
		document.getElementById("sendcode").setAttribute("disabled", true);
		document.getElementById("sendcode").innerHTML = "重新发送(" + countdown + ")";
		countdown--;
	}
	if (countdown != 10) {
		setTimeout(function() {
			settime()
		}, 1000)
	}
}

$("#sendcode").hover(
	function(){
		$(this).css("background","#36BCFF");
	}
	,function(){
		$(this).css("background","");
	}
)

$(".login").click(function(){
	var v = $(this).attr("value");
	if(v == "0"){
		var username = $("#newuser").val();
		var password = $("#newpwd").val();
		var tel = $("#tel").val();
		var code = $("#code").val();
		if(username != ""){
			var u = /\s/;
			if(!u.test(username)){
				if(password != ""){					
					var p = /^[a-zA-Z0-9_]+$/;
					if(p.test(password)){
						if(password.length >= 8 && password.length <= 16){
							var t = /^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(166)|(17[0-9])|(18[0-9])|(19[8|9]))\d{8}$/;
							if(t.test(tel)){
								if(code != ""){								
									$("#sendcode").next("div").hide("fast",function(){
										$(this).html("");
									})
									$.ajax({
										url : "user/registered",
										data : {
											"username" : username,
											"password" : password,
											"tel" : tel,
											"code" : code
										},
										type : "POST",
										dataType : "JSON",
										success : function(data){
											$(".codemsg").show(function(){
												$(".codemsg").html(data.msg);
											});
											setTimeout(function() {
												$(".codemsg").hide(function(){
													$(".codemsg").html("");
												});
												window.location.href = "we/editor";
											}, 2000);
										},
										error : function(){
											$(".codemsg").show(function(){
												$(".codemsg").html("服务器响应失败");
											});
											setTimeout(function() {
												$(".codemsg").hide(function(){
													$(".codemsg").html("");
												});
											}, 2000);
										}
									})
								}else{
									$("#code-block").show("fast");
									$("#sendcode").next("div").show("fast",function(){
										$(this).html("请填写验证码");
									})
								}								
							}else{
								$("#tel").next("div").show("fast",function(){
									$(this).html("手机号有误");
								})
							}
						}else{
							$("#newpwd").next("div").show("fast",function(){
								$(this).html("长度为8-16个字符");
							})
						}
					}else{
						$("#newpwd").next("div").show("fast",function(){
							$(this).html("只能使用字母、数字、下划线");
						})
					}
				}else{
					$("#newpwd").next("div").show("fast",function(){
						$(this).html("密码不能为空");
					})
				}
			}else{
				$("#newuser").next("div").show("fast",function(){
					$(this).html("昵称不能有空格");
				});
			}
		}else{
			$("#newuser").next("div").show("fast",function(){
				$(this).html("昵称不能为空");
			});
		}	
	}else if(v == "1"){
		var username = $("#username").val();
		var password = $("#password").val();
		alert(username+","+password);
	}
})

