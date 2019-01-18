/**
 * @Authur Johnny
 * @role 화면 내 메뉴들의 이벤트 정의 목록
 * @date 2019.01.18
*/
$(document).ready(function () {

    // 판매등록 버튼 클릭 이벤트
    $(document).on('click', '#sell_reg_btn', function () {
        if (fn_isRun(false))
        {
            fn_changeFoodStatus($('.sell_checkbox'), $('.hid-sell_fid'), 0, 1, '판매');
        }
    });
    // 판매삭제 버튼 클릭 이벤트
    $(document).on('click', '#sell_del_btn', function () {
        if (fn_isRun(false))
        {
            fn_changeFoodStatus($('.hold_checkbox'), $('.hid-hold_fid'), 1, 0, '비 판매');
        }
    });
});





/**
 * @Authur Johnny
 * @role 체크한 상품 항목의 판매 상태를 변경하는 함수
 * @date 2019.01.18
*/
function fn_changeFoodStatus(selector_checkbox, class_name_fid, find_status, food_status, status_msg) {
    var checked_box = [], // 체크된 체크박스 배열
        fooditem_id = []; // 체크된 음식 항목의 아이디

    var data = {}; // 서버로 전달하는 data

    // 체크된 체크박스 찾기
    for (var i = 0; i < selector_checkbox.length; i += 1)
    {
        if ($(selector_checkbox[i]).prop('checked'))
        {
            checked_box.push(selector_checkbox[i]);
        }
    }

    // 체크된 항목의 id 찾기
    $(checked_box).each(function (index) {
        // input='checkbox' > div (부모) > input='hidden'
        fooditem_id.push($(this).parent().find(class_name_fid).val());
    });

    if (fooditem_id && fooditem_id.length > 0)
    {
        if (window.confirm('상품을 ' + status_msg + '상태로 변경하시겠습니까?'))
        {
            data.fid = fooditem_id; // data객체에 fid 속성 정의
            data.findStatus = find_status; // data객체에 검색 조건절에 들어갈 상태 속성 정의
            data.status = food_status; // data객체에 상태 속성 정의

            $.ajax({
                url             : '/manager/food_sell',
                type            : 'POST',
                contentType     : 'application/json; charset=utf-8',
                dataType        : 'json',
                data            : JSON.stringify(data)
            }).done(function (obj) {
                // obj 상태 수정 처리 결과 정보 [0: 상태 수정 처리 성공, 1: 상태 수정 처리 중 문제 발생]
                if (obj === 0)
                {
                    window.alert('선택한 항목이 ' + status_msg + ' 처리되었습니다.');
                    window.location.reload();
                }
                else
                {
                    window.alert('처리 중 에러가 발생하였습니다.');
                    window.location.reload();
                }
            }).fail(function (err) {
                window.alert(JSON.stringify(err));
                console.log(err);
            });
        }
    }
    else
    {
        window.alert(status_msg + '할 상품을 선택하고 시도해주세요.');
    }
}