<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
LOGIN PAGE!
<input type="text" id="userid">
<input type="password" id="userpwd">
<input type="button" id="btn-login" value="로그인">
<script src="/js/jquery-3.3.1.min.js"></script>
<script>
	$('#btn-login').on('click', function () {
		var userid = $.trim($('#userid').val());
		var userpwd = $.trim($('#userpwd').val());
		var data = {
			'userid': userid,
			'userpwd': userpwd
		}
		

		$.ajax({
			type: 'POST',
			url: '/login',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			data: JSON.stringify(data)
		}).done(function (obj) {
			if (obj.result === 0)
			{
				window.alert('로그인에 실패하였습니다. 다시 시도해주세요.');
				window.location.reload();
			}
			else if (obj.result === 1)
			{
				// 메인 페이지로 이동
				window.alert('로그인 되었습니다.');
				window.location.href = '/';
			}
		}).fail(function (err) {
			window.alert(JSON.stringify(err));
			console.log(err);
		});
	});
</script>
</body>
</html>