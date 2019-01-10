$(document).ready(function () {
    $('#btn_join').on('click', function () {
        fn_login();
    });
});

/**
 * 
 * @author Johnny
 * @Params [obj]
 * obj = { msg: 0 or 1 } | 매니저 데이터 유무 조회 판단 결과 값
 * @date 2019-01-09
 * @descript 로그인 처리 함수
 */
function fn_login() {
	var managerid = $.trim($('#managerid').val());
	var managerpwd = $.trim($('#managerpw').val());
	var data = {
		'managerid': managerid,
		'managerpwd': managerpwd
    };

	$.ajax({
		type		: 'POST',
		url			: '/manager/login',
		dataType	: 'json',
		contentType	: 'application/json; charset=utf-8',
		data		: JSON.stringify(data)
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
};