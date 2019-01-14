var main_check = document.getElementsByClassName('main-checkbox'); // 전체선택/해제checkbox
var list_check = document.getElementsByClassName('list-checkbox'); // 각 menu div의 checkbox

var menu_list = document.getElementsByClassName('menu-list'); // 표시된 menu div
var list_quantity = document.getElementsByClassName('list-quantity'); // 각 menu의 수량
var menu_del = document.getElementsByClassName('menu-del'); // 각 menu div의 삭제버튼
var list_price = document.getElementsByClassName('menu-price'); // 각 menu의 가격


var plus_btn = document.getElementsByClassName('quantity-plus'); // plus button
var minus_btn = document.getElementsByClassName('quantity-minus'); // minus button



var slide_btn = document.getElementsByClassName('slide-btn'); // slide button
var slide_flag=0; // slide 제어를 위한 flag



var total_quantity = document.getElementById('total-quantity-inner'); // 메뉴 하단의 총 수량
var total_price = document.getElementById('total-price-inner'); // 메뉴 하단의 총 가격
var ft_total_quantity = document.getElementById('ft-total-quantity'); // footer 내부 총 수량
var ft_total_price = document.getElementById('ft-total-price'); // footer 내부 총 가격



var ft = document.getElementById('ft'); // footer











// list checkbox click event
Array.from(list_check).forEach(function(e){
    e.addEventListener('click',function(){
        list_chkb_update(e);
    });
});



// 삭제버튼 click event
Array.from(menu_del).forEach(function(e_val,index,e){
    e[index].addEventListener('click',function(){
        del_confirm(index);
    });
});






// main checkbox 클릭시 list checkbox 수정, 수량 및 가격 수정
main_check[0].addEventListener('click',function(){
    isCheck();
    all_price_setting();
});






// plus button click event
Array.from(plus_btn).forEach(function(e_val,index,e){
    e[index].addEventListener('click',function(){
        plus_fnc(index);
    });
});



// minus button click event
Array.from(minus_btn).forEach(function(e_val,index,e){
    e[index].addEventListener('click',function(){
        minus_fnc(index);
    });
});




// slide animation 호출
slide_btn[0].addEventListener('click',function(){
    if(slide_flag==0){
        slideDown();
        slide_flag=1;
    } else{
        slideUp();
        slide_flag=0;
    }
});





/**
 * @brief main checkbox click으로 모든 list checkbox checked 속성 제어
 * @author JJH
 */
function isCheck() {
    let count = list_check.length;
    if(main_check[0].checked==true){
        for(let i=0; i<count; i++){
            list_check[i].checked=true;
        }
    } else {
        for(let i=0; i<count; i++){
            list_check[i].checked=false;
        }
    }
}




/**
 * @brief 각 list checkbox의 click event, 하나라도 checked false시\n
 *        main checkbox checked도 false, checked 된 menu만 가격, 수량 합산
 * @author JJH
 * @param e Array.from()으로 생성한 list checkbox 배열
 */
function list_chkb_update(e) {
    if(e.checked==false){
        main_check[0].checked=false;
        all_price_setting();
    } else {
        all_price_setting();
    }
    let flag_count = 0;
    for(let i=0; i<list_check.length; i++){
        if(list_check[i].checked==true){
            flag_count++;
        }
    }
    if(flag_count==menu_list.length){
        main_check[0].checked=true;
    } else {
        main_check[0].checked=false;
    }
}




/**
 * @brief plus 버튼 클릭시 해당하는 menu의 수량, 가격 수정 후 최종 수량, 가격 수정
 * @author JJH
 * @param index click한 plus button의 index값
 * @see 함수내 7600원은 테스트를 위한 임의의 값
 * @todo 7600원을 객체.price 값을 받아 쓰길 바람, 필요하다면 price param 추가하세요. 
 */
function plus_fnc(index){
    list_quantity[index].innerHTML++;
    list_price[index].innerHTML = 7600*list_quantity[index].innerHTML;
    all_price_setting();
}



/**
 * @brief minus 버튼 클릭시 해당하는 menu의 수량, 가격 수정 후 최종 수량, 가격 수정
 * @author JJH
 * @param index click한 minus button의 index값
 * @see 함수내 7600원은 테스트를 위한 임의의 값
 * @todo 7600원을 객체.price 값을 받아 쓰길 바람, 필요하다면 price param 추가하세요. 
 */
function minus_fnc(index){
    if(list_quantity[index].innerHTML==1){
        alert("수량은 1 이상입니다. 상품제거는 삭제버튼을 눌러주세요.")
    }else{
        list_quantity[index].innerHTML--;
        list_price[index].innerHTML = 7600*list_quantity[index].innerHTML;
        all_price_setting();
    }
}






/**
 * @brief 삭제 버튼 index에 해당하는 menu 삭제
 * @author JJH
 * @param index 클릭한 삭제 버튼의 index
 */
function del_confirm(index) {
    if(confirm('이 제품을 삭제하시겠습니까?')){
        menu_list[index].remove();
        all_price_setting();
    }
}




/**
 * @brief 각 menu checkbox를 검사하여 checked 항목의 가격,수량을 계산하는 함수
 * @author JJH
 */
function all_price_setting() {
    let total_qu=0;
    let total_pri=0;


    if(main_check[0].checked==true){
        for(let i=0; i<menu_list.length; i++){
            total_qu += Number(list_quantity[i].innerHTML);
            total_pri += Number(list_price[i].innerHTML);
        }
        total_quantity.innerHTML = total_qu;
        total_price.innerHTML = total_pri;
        ft_total_price.innerHTML = total_pri;
        ft_total_quantity.innerHTML = total_qu;
    } else if(main_check[0].checked==false){
        for(let i=0; i<menu_list.length; i++){
            if(list_check[i].checked ==true){
                total_qu += Number(list_quantity[i].innerHTML);
                total_pri += Number(list_price[i].innerHTML); 
            }
        }
        total_quantity.innerHTML = total_qu;
        total_price.innerHTML = total_pri;
        ft_total_price.innerHTML = total_pri;
        ft_total_quantity.innerHTML = total_qu;
    }
}





/**
 * @brief slide down animation function
 * @author JJH
 */
function slideDown() {
    ft.style.transition = "all 1s ease-in-out";
    ft.style.bottom = '-200px';
    slide_btn[0].innerHTML = '△';
};

/**
 * @brief slide up animation function
 * @author JJH
 */
function slideUp() {
    ft.style.transition = "all 1s ease-in-out";
    ft.style.bottom = '-10px';
    slide_btn[0].innerHTML = '▽';
};