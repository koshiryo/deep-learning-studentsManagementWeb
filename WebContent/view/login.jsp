<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="bookmark" href="favicon.ico"/>
<link href="h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="h-ui/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">

<script type="text/javascript" src="easyui/jquery.min.js"></script> 
<script type="text/javascript" src="h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="h-ui/lib/icheck/jquery.icheck.min.js"></script> 

<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>

<script type="text/javascript">
	$(function(){
		//Login
		$("#submitBtn").click(function(){
			if($("#radio-2").attr("checked") && "${systemInfo.forbidStudent}" == 1){
				$.messager.alert("Notice", "Students cannot log in to the system!", "warning");
				return;
			}
			if($("#radio-3").attr("checked") && "${systemInfo.forbidTeacher}" == 1){
				$.messager.alert("Notice", "Teachers cannot log in to the system!", "warning");
				return;
			}
			
			var data = $("#form").serialize();
			$.ajax({
				type: "post",
				url: "LoginServlet?method=Login",
				data: data, 
				dataType: "text", //返回数据类型
				success: function(msg){
					if("admin" == msg){
						window.location.href = "SystemServlet?method=toAdminView";
					} else if("student" == msg){
						window.location.href = "SystemServlet?method=toStudentView";
					} else if("teacher" == msg){
						window.location.href = "SystemServlet?method=toTeacherView";
					}
				}
				
			});
		});
		
		//设置复选框
		$(".skin-minimal input").iCheck({
			radioClass: 'iradio-blue',
			increaseArea: '25%'
		});
	})
</script> 
<title>Login|学生成績管理システム</title>
<meta name="keywords" content="学生成績管理システム">
</head>
<body>

<div class="header" style="padding: 0;">
	<h2 style="color: white; width: 400px; height: 60px; line-height: 60px; margin: 0 0 0 30px; padding: 0;">学生成績管理システム</h2>
</div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form id="form" class="form form-horizontal" method="post">
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-8">
          <input id="" name="account" type="text" placeholder="Account" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-8">
          <input id="" name="password" type="password" placeholder="Password" class="input-text size-L">
        </div>
      </div>
      
      <div class="mt-20 skin-minimal" style="text-align: center;">
		<div class="radio-box">
			<input type="radio" id="radio-2" name="type" checked value="2" />
			<label for="radio-1">Student</label>
		</div>
		<div class="radio-box">
			<input type="radio" id="radio-3" name="type" value="3" />
			<label for="radio-2">Teacher</label>
		</div>
		<div class="radio-box">
			<input type="radio" id="radio-1" name="type" value="1" />
			<label for="radio-3">Administrator</label>
		</div>
	</div>
      
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input id="submitBtn" type="button" class="btn btn-success radius size-L" value="Login">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright 2019 4&nbsp; koshiryo </div>


</body>
</html>