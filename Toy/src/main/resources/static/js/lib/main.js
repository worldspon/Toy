var menu_box_id = document.getElementsByClassName('menu_box');
var cookie_index = 0;


/**
 * @Authur Johnny
 * @role 화면 내 메뉴들의 이벤트 정의 목록
 * @date 2019.01.18
*/
$(document).ready(function () {

    // 음식 메뉴 수량 증가 버튼 클릭 이벤트
    $(document).on('click', '.quantity_plus', function () {
        if (fn_isRun(false))
        {
            fn_quantity_plus(this);
        }
    });

    // 음식 메뉴 수량 감소 버튼 클릭 이벤트
    $(document).on('click', '.quantity_minus', function () {
        if (fn_isRun(false))
        {
            fn_quantity_minus(this);
        }
    });

    // 장바구니에 담기 버튼 클릭 이벤트
    $(document).on('click', '.add_cart', function () {
        if (fn_isRun(false))
        {
            fn_createCookie(this);
        }
    });
});






/**
 * 
 * @author Johnny
 * @date 2019-01-10
 * @role 음식 메뉴 수량 감소 함수
 */

function fn_quantity_plus(this_) {
    let menu_quantity = document.getElementsByClassName("quantity_box");
    let menu_price = document.getElementsByClassName('menu_price');
    let index = $('.quantity_plus').index(this_);    // 상품 인덱스
    let foodpirce = Number($(this_).parent().parent().find('#foodprice_' + index).val());    // 상품 가격값
    let max_stock = $('#foodstock_' + index).val(); // 상품 최대 수량값

    if(menu_quantity[index].innerHTML != max_stock){
        menu_quantity[index].innerHTML++;
        menu_price[index].innerHTML = foodpirce * menu_quantity[index].innerHTML + '원';
    } else {
        return 0;
    }
}



/**
 * 
 * @author Johnny
 * @date 2019-01-10
 * @role 음식 메뉴 수량 감소 함수
 */
function fn_quantity_minus(this_) {
    let menu_quantity = document.getElementsByClassName("quantity_box");
    let menu_price = document.getElementsByClassName('menu_price');
    let index=$(".quantity_minus").index(this_); // 상품 인덱스
    let foodpirce = Number($(this_).parent().parent().find('#foodprice_' + index).val());    // 상품 가격값

    if(menu_quantity[index].innerHTML!=1){
        menu_quantity[index].innerHTML--;
        menu_price[index].innerHTML = foodpirce * menu_quantity[index].innerHTML + '원';
    } else {
        return 0;
    }
}




/**
 * 
 * @author Johnny
 * @date 2019-01-18
 * @role 선택한 상품을 쿠키에 저장하는 함수
 */
function fn_createCookie(this_) {
    let expire = new Date();
    let cookie;

    let cookiedata = {};
    let fid = Number($(this_).parent().find('.fid').val());
    let quantity = Number($(this_).parent().find('.quantity_box').text());
    let price =  $(this_).parent().parent().find('.foodprice').val() * Number(quantity);

    console.log('fid: ' + fid);
    console.log('quantity : ' + quantity);
    console.log('price : ' + price);

    cookiedata.fid = fid;
    cookiedata.quantity = quantity;
    cookiedata.price = price;

    expire.setDate(expire.getDate() + 60); // 쿠키 유효 기간 오늘 일자 + 60일
    cookie = 'cart' + cookie_index + ' = ' + JSON.stringify(cookiedata) + '; path=/ ; expires=' + expire.toGMTString() + ';';

    document.cookie = cookie;

    cookie_index += 1;
}