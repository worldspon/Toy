/**
 * @Authur Johnny
 * @role 화면 내 메뉴들의 이벤트 정의 목록
 * @date 2019.01.16
*/
$(document).ready(function () {
	// 엔터 입력 이벤트
	$(document).keyup(function (e) {
		// 엔터 입력 시 로그인 함수를 호출함
		if (fn_isRun(false))
		{
			if(e.keyCode == 13)
			{
				fn_login();
			}
		}
	});

	// 로그인 버튼 클릭 이벤트
	$('#btn_join').on('click', function () {
		if (fn_isRun(false))
		{
			fn_login();
		}
	});

});


/**
 * 
 * @author Johnny
 * @Params [obj]
 * obj = { msg: 0 or 1 } | 사용자 데이터 유무 조회 판단 결과 값
 * @date 2019-01-09
 * @descript 로그인 처리 함수
 */
function fn_login() {
	var userid = $.trim($('#id').val());
	var userpwd = $.trim($('#pw1').val());
	var check_val_arr = [userid, userpwd];
	var data = {};

	
	if (fn_validation(check_val_arr))
	{
		data = {
			'userid': userid,
			'userpwd': userpwd
		};
	
		$.ajax({
			type		: 'POST',
			url			: '/login',
			dataType	: 'json',
			contentType	: 'application/json; charset=utf-8',
			data		: JSON.stringify(data)
		}).done(function (obj) {
			if (obj.result === 0)
			{
				window.alert('존재하지 않는 정보입니다.');
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
	}	
}