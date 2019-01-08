<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
JOIN PAGE!
<input type="text" id="txt_userid">
<input type="button" value="중복체크" id="btn_checkid">
<script src="/js/jquery-3.3.1.min.js"></script>
<script>
	$(document).ready(function () {
		$("#btn_checkid").on('click', function () {
			checkId();
		});
	});
	
	function checkId() {
		var userid = $.trim($("#txt_userid").val());
		
		$.ajax({
			type: 'POST',
			url: '/join/checkid',
			dataType: 'json',
			data: { "userid": userid }
		}).done(function (result) {
			alert(JSON.stringify(result));
			location.reload();
		}).fail(function (err) {
			alert(JSON.stringify(err));
			console.log(err);
		});
	}
</script>
</body>
</html>