var reg_form = document.getElementById('reg_form'); // 상품등록 아래 innerHTML을 위한 form id
var whitereg = /\s/;



/**
 * @Authur Johnny
 * @role 화면 내 메뉴들의 이벤트 정의 목록
 * @date 2019.01.16
*/
$(document).ready(function () {
    // 음식 항목 등록 폼 추가 클릭 이벤트
    $(document).on('click', '#add_div', function () {
        if (fn_isRun(false))
        {
            // 상품등록 박스를 추가하는 함수
            fn_plus_reg_box();
        }
    });

    // 음식 항목 등록 클릭 이벤트
    $(document).on('click', '#reg_btn', function () {
        if (fn_isRun(false))
        {
            fn_add_product();
        }
    });

    // 음식 항목 삭제 클릭 이벤트
    $(document).on('click', '#del_btn', function () {
        if (fn_isRun(false))
        {
            // 음식 항목 삭제 함수
            fn_del_product();
        }
    });

    // 음식 항목 수정 클릭 이벤트
    $(document).on('click', '.mod_btn', function () {
        if (fn_isRun(false))
        {
            // 음식 항목 수정 함수
            fn_mod_product(this);
        }
    });
});





/**
 * @Authur jjh
 * @role 상품등록 form 아래 상품등록박스를 추가하는 함수
 * @see 현재 3개로 제한
 * @date 2019.01.10
*/
function fn_plus_reg_box() {
    let formidx = $('#reg_form > div').length;

    let img_file = document.getElementsByClassName('image_file');


    let form_div = document.createElement('div');
    form_div.className = 'food_form';

    let img_title = document.createElement('h3');
    let img_title_text = document.createTextNode('이미지 업로드');
    img_title.appendChild(img_title_text);

    let img_input = document.createElement('input');
    img_input.type = "file";
    img_input.className = "image_file";
    img_input.name = 'imgfile[' + formidx + ']';
    img_input.accept = ".jpg, .jpeg, .png";
    img_input.setAttribute('required', 'required');
    
    //img_input.id = "btn-file-"+(file_idx);

    let pd_title = document.createElement('h3');
    let pd_title_text = document.createTextNode('상품명');
    pd_title.appendChild(pd_title_text);

    let pd_input = document.createElement('input');
    pd_input.type = "text";
    pd_input.className = "product_name";
    pd_input.name = 'foodlist[' + formidx + '].foodname';
    pd_input.setAttribute('placeholder', 'ex)와퍼 주니어');
    pd_input.setAttribute('required', 'required');
    

    let price_title = document.createElement('h3');
    let price_title_text = document.createTextNode('상품가격');
    price_title.appendChild(price_title_text);

    let price_input = document.createElement('input');
    price_input.type = "text";
    price_input.className = "product_price";
    price_input.name = 'foodlist[' + formidx + '].foodprice';
    price_input.setAttribute('placeholder', 'ex)5700');
    price_input.setAttribute('maxlength', '7');
    price_input.setAttribute('required', 'required');
    

    let quantity_title = document.createElement('h3');
    let quantity_title_text = document.createTextNode('상품수량');
    quantity_title.appendChild(quantity_title_text);

    let quantity_input = document.createElement('input');
    quantity_input.type = "text";
    quantity_input.className = "product_quantity";
    quantity_input.name = 'foodlist[' + formidx + '].stock';
    quantity_input.setAttribute('placeholder', 'ex)70 최대 5자리수');
    quantity_input.setAttribute('maxlength', '5');
    quantity_input.setAttribute('required', 'required');
    

    form_div.appendChild(img_title);
    form_div.appendChild(img_input);
    form_div.appendChild(pd_title);
    form_div.appendChild(pd_input);
    form_div.appendChild(price_title);
    form_div.appendChild(price_input);
    form_div.appendChild(quantity_title);
    form_div.appendChild(quantity_input);

    if(img_file.length<3){
        reg_form.appendChild(form_div);
    }
}


/**
 * @Authur Johnny
 * @role 상품 등록 처리 함수
 * @date 2019.01.16
*/
function fn_add_product() {
    // 이미지 파일 정보를 담는 배열 변수
    var file_arr = [];

    var foodname_arr = [],  // 음식 메뉴 이름 정보
        foodprice_arr = [], // 음식 메뉴 가격 정보
        foodstock_arr = []; // 음식 메뉴 수량 정보

    // 검증할 데이터들 배열에 채워넣기
    $('.food_form').each(function (i) {
        // div (.food_form) > elements
        file_arr.push($(this).find('.image_file').val());
        foodname_arr.push($(this).find('.product_name').val());
        foodprice_arr.push($(this).find('.product_price').val());
        foodstock_arr.push($(this).find('.product_quantity').val());
    });


    // 각 입력 항목들 데이터 검증
    if (fn_validation(file_arr) && fn_validation(foodname_arr) && fn_numberValidation(foodprice_arr) && fn_validation(foodprice_arr) && fn_numberValidation(foodstock_arr) && fn_validation(foodstock_arr))
    {
        if (window.confirm('작성하신 상품을 등록하시겠습니까?'))
        {
            document.forms[0].submit();
        }
    }
}




/**
 * @Authur JJH
 * @role 상품 목록 중 checked 된 상품을 골라 서버와 통신하여 제거하는 함수
 * @date 2019.01.10
*/
function fn_del_product(){
    var del_checkbox = $('.del_checkbox'); // 모든 체크박스 객체
    var checked_box = [], // 체크된 체크박스 객체
        fooditem_id = []; // 체크된 음식 항목의 아이디

    var data = {}; // 서버로 전달하는 data

    // 체크된 체크박스 찾기
    for (var i = 0; i < del_checkbox.length; i++) 
    {
        if ($(del_checkbox[i]).prop('checked'))
        {
            checked_box.push(del_checkbox[i]);
        }
    }

    // 체크된 항목의 id 찾기
    $(checked_box).each(function (index) {
        // input='checkbox' > div (부모) > input='hidden'
        fooditem_id.push($(this).parent().find('.del_food_fid').val());
    });

    if (fooditem_id && fooditem_id.length > 0)
    {
        if (window.confirm('상품을 삭제하시겠습니까?'))
        {
            data.fid = fooditem_id; // data객체에 fid 속성 정의

            $.ajax({
                url             : '/manager/food_delete',
                type            : 'POST',
                contentType     : 'application/json; charset=utf-8',
                dataType        : 'json',
                data            : JSON.stringify(data)
            }).done(function (obj) {
                // obj 삭제 처리 결과 정보 [0: 삭제 처리 성공, 1: 삭제 처리 중 문제 발생]
                if (obj === 0)
                {
                    window.alert('선택한 항목이 삭제되었습니다.');
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
}




/**
 * @Authur Johnny
 * @role 상품 수정 페이지 이동 함수
 * @date 2019.01.14
*/
function fn_mod_product(this_) {
    var food_id = $(this_).parent().find('.del_food_fid').val(); // 수정할 음식 항목의 아이디

    // 수정 페이지로 이동
    window.location.href = '/manager/food_modify?fid=' + food_id;
}