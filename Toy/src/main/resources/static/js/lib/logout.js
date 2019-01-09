$(document).ready(function() {
    $('#a-logout').on('click', function () {
        fn_logout();
    });
});

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