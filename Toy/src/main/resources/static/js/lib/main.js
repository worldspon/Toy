var menu_box_id = document.getElementsByClassName('menu_box');


/**
 * @Authur Johnny
 * @role 화면 내 메뉴들의 이벤트 정의 목록
 * @date 2019.01.16
*/
$(document).ready(function () {

    // 음식 메뉴 수량 증가 버튼 클릭 이벤트
    $('.quantity_plus').click(function(){
        if (fn_isRun(false))
        {
            fn_quantity_plus(this);
        }
    });

    // 음식 메뉴 수량 감소 버튼 클릭 이벤트
    $('.quantity_minus').click(function(){
        if (fn_isRun(false))
        {
            fn_quantity_minus(this);
        }
    });
});






/**
 * 
 * @author Johnny
 * @Params []
 * @date 2019-01-10
 * @description 음식 메뉴 수량 감소 함수
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
 * @Params []
 * @date 2019-01-10
 * @description 음식 메뉴 수량 감소 함수
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





//@TODO 장바구니에 옮겨담는 함수 구현
$('.add_cart').click(function(){
    console.log("A");
});