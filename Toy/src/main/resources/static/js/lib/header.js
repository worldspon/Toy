$(document).ready(function() {
	// 회원가입 클릭 이벤트
	$('#a-join').on('click', function () {
		fn_move('join');
	});
	// 로그인 클릭 이벤트
	$('#a-login').on('click', function () {
		fn_move('login');
	});
	// 로그아웃 클릭 이벤트
    $('#a-logout').on('click', function () {
        fn_logout();
    });
});

/**
 * 
 * @author Johnny
 * @Params [path]
 * path 문자열 : 이동 할 페이지 정보 
 * @date 2019-01-09
 * @descript 페이지 이동 함수
 */
function fn_move(path) {
	window.location.href = '/' + path;
};


/**
 * 
 * @author Johnny
 * @Params [obj]
 * obj = { msg: "로그아웃 처리..." or "로그아웃 처리 중 에러..." } | 로그아웃 처리 메시지 정보
 * @date 2019-01-09
 * @descript 로그아웃 처리 함수
 */
function fn_logout() {
    $.ajax({
        type        : 'GET',
        url         : '/logout',
        contentType : 'application/json; charset=utf-8',
        dataType    : 'json'
    }).done(function (obj) {
        window.alert(obj.msg);
        window.location.reload();
    }).fail(function (err) {
        window.alert(JSON.stringify(err));
        console.log(err);
    });
};