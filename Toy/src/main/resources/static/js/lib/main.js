var menu_box_id = document.getElementsByClassName('menu_box');



/*----------------------------------------------------------------------------
//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// view_menu_list 함수 테스트 코드, 확인 후 삭제요망
*/
/*
var buger1 = {
    name:'불고기',
    price:1900,
    quantity:5,
    is_sell:true,
    img:'더블비프불고기.png'
};
var buger2 = {
    name:'물고기',
    price:2900,
    quantity:3,
    is_sell:true,
    img:'더블비프불고기.png'
};
var buger3 = {
    name:'흙고기',
    price:3900,
    quantity:15,
    is_sell:false,
    img:'더블비프불고기.png'
};
var buger4 = {
    name:'바람고기',
    price:4900,
    quantity:35,
    is_sell:true,
    img:'더블비프불고기.png'
};
var buger5 = {
    name:'번개고기',
    price:5900,
    quantity:0,
    is_sell:false,
    img:'더블비프불고기.png'
};

var bur_ary = [buger1,buger2,buger3,buger4,buger5];
*/
/*
//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
----------------------------------------------------------------------------//
*/


//서버 판매목록을 받아 화면에 그리는 함수
//view_menu_list(bur_ary);




/**
 * 
 * @author Johnny
 * @Params []
 * @date 2019-01-11
 * @description 음식 메뉴 수량 증가 버튼 기능
 */
$('.quantity_plus').click(function(){
    let menu_quantity = document.getElementsByClassName("quantity_box");
    let menu_price = document.getElementsByClassName('menu_price');
    let index = $('.quantity_plus').index(this);    // 상품 인덱스
    let foodpirce = Number($(this).parent().parent().find('#foodprice_' + index).val());    // 상품 가격값
    let max_stock = $('#foodstock_' + index).val(); // 상품 최대 수량값

    if(menu_quantity[index].innerHTML != max_stock){
        menu_quantity[index].innerHTML++;
        menu_price[index].innerHTML = foodpirce * menu_quantity[index].innerHTML + '원';
    } else {
        return 0;
    }
});



/**
 * 
 * @author Johnny
 * @Params []
 * @date 2019-01-10
 * @description 음식 메뉴 수량 감소 버튼 기능
 */
$('.quantity_minus').click(function(){
    let menu_quantity = document.getElementsByClassName("quantity_box");
    let menu_price = document.getElementsByClassName('menu_price');
    let index=$(".quantity_minus").index(this); // 상품 인덱스
    let foodpirce = Number($(this).parent().parent().find('#foodprice_' + index).val());    // 상품 가격값

    if(menu_quantity[index].innerHTML!=1){
        menu_quantity[index].innerHTML--;
        menu_price[index].innerHTML = foodpirce * menu_quantity[index].innerHTML + '원';
    } else {
        return 0;
    }
});



//@TODO 장바구니에 옮겨담는 함수 구현
$('.add_cart').click(function(){
    console.log("A");
});


/**
 * 
 * @brief 객체 배열의 갯수만큼 메뉴를 생성
 * @author JJH
 * @param val 판매중인 메뉴객체 배열
 */
/*
function view_menu_list(val) {
    let count = val.length;

    for(let i=0; i<count; i++){
        menu_box_id[0].innerHTML += '<div class="menu">'+
        '<div class="menu_l">'+
        '<img src=\''+val[i].img+'\'>'+
        '</div>'+
        '<div class="menu_r">'
            +'<h1 class="menu_name">'+val[i].name+'</h1>'+
            '<span class="menu_price">'+val[i].price+'</span>'+
            '<div class="quantity_wrap">'+
                '<span class="quantity_text">수량선택</span>'+
                '<span class="quantity_box">1</span>'+
                '<button class="quantity_plus quantity_btn">+</button>'+
                '<button class="quantity_minus quantity_btn">-</button>'+
                '<button class="add_cart">장바구니에 담기</button>'+
            '</div>'+
        '</div>'+
    '</div>';
    }
};
*/