<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();    
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";    
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit news for WeiXin</title>
</head>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>  
<script type="text/javascript" src="uploadify/jquery.uploadify.js"></script> 
<script type="text/javascript" src="uploadify/jquery.uploadify.min.js"></script> 
<link rel="stylesheet" type="text/css" href="uploadify/uploadify.css">
<!--
<script type="text/javascript">  
	$(document).ready(function() {
	    $('#fileName').uploadify({  
	        uploader: '<%=path%>/uploadFile.action',          // 服务器端处理地址  jsessionid=<%=session.getId()%> 解决在FireFox下的session问题
	        swf: 'uploadify/uploadify.swf',    // 上传使用的 Flash  
	        //cancelImg: 'uploadify/uploadify-cancel.png',取消上传的按钮可以在uploadify.css中设置
	        queueID : 'fileQueue',			    // 此处的queueID就是div中的ID
	        width: 80,                          // 按钮的宽度  
	        height: 23,                         // 按钮的高度  
	        buttonText: "上传图片",                 // 按钮上的文字  
	        buttonCursor: 'hand',                // 按钮的鼠标图标  
	        fileObjName: 'fileName',           // 上传参数名称 后台<span style="color:#ff0000;">action里面的属性uploadify</span>  
	        // 两个配套使用  
	        fileTypeExts: "*.jpg;*.png",    // 扩展名 (限制上传的文件) 
	        fileTypeDesc: "请选择 jpg png 图片 ",     // 文件说明  
	        auto: false,                // 选择之后，自动开始上传  
	        multi: false,               // 是否支持同时上传多个文件  
	        queueSizeLimit: 1,         // 允许多文件上传的时候，同时上传文件的个数 
	        
	        onUploadStart : function (file) {  
	        	var title = document.getElementById("title").value;
	        	var description = document.getElementById("description").value;
	        	var url = document.getElementById("url").value;
	        	var type = document.getElementById("type").value;
                $('#fileName').uploadify("settings", "formData", { 'title':title,'description':description,'type':type,'url':url});  
                //在onUploadStart事件中，也就是上传之前，把参数写好传递到后台。  
            }
	    });
	});  
</script>
-->
<body>
	<center>
		<form method="post" action="uploadFile.action">
			<div id="fileQueue" style=""></div>
			<h6>新闻标题：</h6><input type="text" name="title" id="title"/><br/>
			<h6>新闻摘要：</h6><input type="text" name="description" id="description"/><br/>
			<h6>新闻图片：</h6><input type="text" name="picurl" id="picurl" /> <br/>
			<h6>新闻链接：</h6><input type="text" name="url" id="url"/><br/>
			<h6>新闻类型：</h6>
			<select name="type" id="type">
			  <option value ="1">国内新闻</option>
			  <option value ="2">国际新闻</option>
			  <option value ="3">经济新闻</option>
			  <option value ="4">社会新闻</option>
			  <option value ="5">娱乐新闻</option>
			  <option value ="6">体育新闻</option>
			  <option value ="7">感悟人生</option>
			  <option value ="8">轻松时刻</option>
			</select> <br/>
			<input type="submit" value="编辑">
		</form>
	</center>
<!-- <a href="javascript:$('#fileName').uploadify('upload', '*')">编辑</a> -->
</body>
</html>