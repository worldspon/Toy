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




/**
 * @Authur Johnny
 * @role 화면 내 메뉴들의 이벤트 정의 목록
 * @date 2019.01.22
*/
$(document).ready(function () {
    // 모든 상품 가격에 콤마 설정
    fn_setCommaFoodPrice();

    // 총 수량 갯수 
    fn_getTotalCount();

    // 총 금액
    fn_getTotalPrice();

    // list checkbox click event
    Array.from(list_check).forEach(function(e){
        e.addEventListener('click',function(){
            if (fn_isRun)
            {
                list_chkb_update(e);
            }
        });
    });

    // 단일 상품 주문 버튼 클릭 이벤트
    $(".menu-order").on('click', function () {
        if (fn_isRun)
        {
            fn_individualOrder(this);
        }
    });

    // 선택 상품 주문 버튼 클릭 이벤트
    $('.select-order').on('click', function () {
        if (fn_isRun)
        {
            
        }
    });

    // 전체 상품 주문 버튼 클릭 이벤트
    $(".all-order").on('click', function () {
        if (fn_isRun)
        {

        }
    });

    // 삭제버튼 click event
    Array.from(menu_del).forEach(function(e_val,index,e){
        e[index].addEventListener('click',function(){
            if (fn_isRun)
            {
                del_confirm(this);
            }
        });
    });

    // main checkbox 클릭시 list checkbox 수정, 수량 및 가격 수정
    main_check[0].addEventListener('click',function(){
        if (fn_isRun)
        {
            isCheck();
            //all_price_setting();
        }
    });

    // plus button click event
    Array.from(plus_btn).forEach(function(e_val,index,e){
        e[index].addEventListener('click',function(){
            let thisStock = $(this).parent().find('.list-quantity').text();
            let maxStock = $(this).parent().find('.hid-max-stock').val(); // 최대 주문 요청할 수 있는 최대 수량

            // 현재 수량이 상품의 최대 수량과 같은경우 수량 증가를 중지한다.
            if (thisStock === maxStock)
            {
                window.alert('현재 신청할 수 있는 최대 수량입니다.');
                return;
            }
            if (fn_isRun) 
            {
                plus_fnc(index);
            }
        });
    });

    // minus button click event
    Array.from(minus_btn).forEach(function(e_val,index,e){
        e[index].addEventListener('click',function(){
            if (fn_isRun)
            {
                minus_fnc(index);
            }
        });
    });

    // slide animation 호출
    slide_btn[0].addEventListener('click',function(){
        if(slide_flag==0){
            if (fn_isRun)
            {
                slideDown();
                slide_flag=1;
            }
        } else {
            if (fn_isRun)
            {
                slideUp();
                slide_flag=0;
            }
        }
    });

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

    fn_getTotalCount();
    fn_getTotalPrice();
}




/**
 * @author Johnny
 * @author JJH
 * @date 2019-01-22
 * @param e Array.from()으로 생성한 list checkbox 배열
 * @role 체크박스 체크 / 언체크 시 수량, 상품 가격 계산 처리
 */
function list_chkb_update(e) {
    if(e.checked==false){
        main_check[0].checked=false;
        //all_price_setting();
        fn_getTotalCount();
        fn_getTotalPrice();
    } else {
        //all_price_setting();
        fn_getTotalCount();
        fn_getTotalPrice();
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
 * @author Johnny
 * @author JJH
 * @date 2019-01-22
 * @param index click한 plus button의 index값
 * @role plus 버튼 클릭시 해당하는 menu의 수량, 가격 수정 후 최종 수량, 가격 수정
 */
function plus_fnc(index){
    // 수량 값 증가
    list_quantity[index].innerHTML++;
    //list_price[index].innerHTML = 7600*list_quantity[index].innerHTML;
    //all_price_setting();

    let finalFoodPrice = 0;
    let srcFoodPrice = $(list_price[index]).text(); // 현재 표현되는 가격
    // .menu-price (span) > #div-menu-price (div) > .hid-one-food-price (input)
    let oneFoodPrice = $(list_price[index]).parent().find('.hid-one-food-price').val(); // 상품 1개의 가격

    // 30,000원 -> 30,000 -> 30000
    srcFoodPrice = srcFoodPrice.split("원")[0].replace(',', '');

    finalFoodPrice = Number(srcFoodPrice) + Number(oneFoodPrice);
    // 콤마찍기
    finalFoodPrice = numberWithCommas(finalFoodPrice);
    fn_setFoodPrice(list_price[index], finalFoodPrice);

    fn_getTotalCount();
    fn_getTotalPrice();
}



/**
 * @author Johnny
 * @author JJH
 * @date 2019-01-22
 * @param index click한 minus button의 index값
 * @see 함수내 7600원은 테스트를 위한 임의의 값
 * @role minus 버튼 클릭시 해당하는 menu의 수량, 가격 수정 후 최종 수량, 가격 수정
 */
function minus_fnc(index){
    let finalFoodPrice = 0;
    let srcFoodPrice = $(list_price[index]).text(); // 현재 표현되는 가격
    // .menu-price (span) > #div-menu-price (div) > .hid-one-food-price (input)
    let oneFoodPrice = $(list_price[index]).parent().find('.hid-one-food-price').val(); // 상품 1개의 가격


    if (list_quantity[index].innerHTML == 1)
    {
        alert("수량은 1개 미만으로 선택하실 수 없습니다.\n상품제거는 삭제버튼을 눌러주세요.")
    }
    else
    {
        list_quantity[index].innerHTML--;
        // list_price[index].innerHTML = 7600*list_quantity[index].innerHTML;
        //all_price_setting();
        
        // 30,000원 -> 30,000 -> 30000
        srcFoodPrice = srcFoodPrice.split("원")[0].replace(',', '');

        finalFoodPrice = Number(srcFoodPrice) - Number(oneFoodPrice);
        // 콤마찍기
        finalFoodPrice = numberWithCommas(finalFoodPrice);
        fn_setFoodPrice(list_price[index], finalFoodPrice);

        fn_getTotalCount();
        fn_getTotalPrice();
    }
}




/**
 * @author Johnny
 * @author JJH
 * @date 2019-01-22
 * @param index 클릭한 삭제 버튼의 index
 * @role 삭제 버튼 index에 해당하는 menu 삭제 -> index를 버튼을 클릭한 this 객체로 변경
 * @todo index로 접근 시 div를 제거하면 모든 요소가 제거되지 않는 문제가 발생함
 */
function del_confirm(this_) {
    let fid = 0;
    let data = {};
    if (confirm('이 제품을 삭제하시겠습니까?'))
    {
        // .menu-del (input) > .menu-list (div) > div > .hid-fid (input)
        fid = $(this_).parent().parent().find('.hid-fid').val();
        data.fid = fid;

        // 쿠키 삭제
        $.ajax({
            url         : '/deleteCookie',
            type        : 'POST',
            contentType : 'application/json; charset=utf-8',
            dataType    : 'json',
            data        : JSON.stringify(data)
        }).done(function (obj) {
            // 쿠키 삭제 처리 중 문제 발생
            if (obj.process === 0)
            {
                window.alert(obj.msg);
                window.location.reload();
            }
            // 쿠키 삭제 처리 완료
            else if (obj.process === 1)
            {
                window.alert(obj.msg);
                window.location.reload();
            }
        }).fail(function (err) {
            window.alert(JSON.stringify(err));
            console.log(err);
        });
    }
}




/**
 * @author Johnny
 * @author JJH
 * @date 2019-01-22
 * @role 각 menu checkbox를 검사하여 checked 항목의 가격,수량을 계산하는 함수
 * @todo 가격에 대한 세팅은 콤마 및 수량 처리를 함수별로 분할시켜 처리함
 */
/*
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
*/




/**
 * @brief slide down animation function
 * @author JJH
 */
function slideDown() {
    ft.style.transition = "all 1s ease-in-out";
    ft.style.bottom = '-200px';
    slide_btn[0].innerHTML = '△';
}

/**
 * @brief slide up animation function
 * @author JJH
 */
function slideUp() {
    ft.style.transition = "all 1s ease-in-out";
    ft.style.bottom = '0px';
    slide_btn[0].innerHTML = '▽';
}





/**
 * @author Johnny
 * @date 2019-01-21
 * @role 모든 상품 가격에 콤마찍기
 */
function fn_setCommaFoodPrice() {
    let selector = $(list_price);
    let foodPrice = 0;
    let elFoodPrices = [];
    // 35000원 -> 35000
    elFoodPrices = selector.text().split('원');
    
    $(selector).each(function (index) {
        // 35000 -> 35,000
        foodPrice = numberWithCommas(elFoodPrices[index]);
        $(this).text(foodPrice + '원');
    });
}

/**
 * @author Johnny
 * @date 2019-01-21
 * @role 총 수량 설정하는 함수
 */
function fn_getTotalCount() {
    let totalCount = 0;
    let checkbox = [];
    checkbox = $(list_check);
    
    checkbox.each(function (index) {
        if ($(checkbox[index]).prop('checked'))
        {
            // .list-checkbox (input) > .menu-list (div) > .quantity-box (div) > .list-quantity (span)
            totalCount += Number($(this).parent().find('.quantity-box > .list-quantity').text());
        }
    });

    $('.total-quantity').find('span').text(totalCount + '개');
}

/**
 * @author Johnny
 * @date 2019-01-21
 * @role 총 금액 설정하는 함수
 */
function fn_getTotalPrice() {
    let foodPrice = 0;
    let strPrice = "";
    let elFoodPrices = [],
        checkbox = [];
    // 30,000원 -> 30,000
    elFoodPrices = $(list_price).text().split('원');
    checkbox = $(list_check);

    // 모든 가격을 합산함 추후 체크된 값만 합산하도록 수정되어야 함
    $(elFoodPrices).each(function (index) {
        if ($(checkbox[index]).prop('checked'))
        {
            // 30,000 -> 30000
            strPrice = this.replace(',', '');
            foodPrice += Number(strPrice);
        }
    });

    // 모두 합산한 가격에 콤마를 찍음
    foodPrice = numberWithCommas(foodPrice);

    $('.total-price').find('span').text(foodPrice + '원');
}

/**
 * @author Johnny
 * @date 2019-01-22
 * @role 플러스, 마이너스 버튼 클릭 시 상품 수량 수정
 */
function fn_setFoodPrice(this_, finalFoodPrice) {
    // .quantity-plus (input) > .quantity-box (div) > .menu-list (div) > #div-menu-price (div) > span
    $(this_).parent().parent().find('#div-menu-price > span').text(finalFoodPrice + '원');
}


/**
 * @author Johnny
 * @date 2019-01-22
 * @role 단일 상품 주문 요청 함수
 */
function fn_individualOrder(this_) {
    let foodname = $(this_).parent().find('.menu-foodname').text(); // .menu-order (input) > .menu-list (div) > .menu-foodname (div)
    let status = 1; // 주문 상태값 0: 대기 중, 1: 처리 됨, 2: 거절 됨, 3: 취소 됨
    let fid = $(this_).parent().parent().find('.hid-fid').val(); // .menu-order (input) > .menu-list (div) > div > .hid-fid (input)
    let stock = $(this_).parent().find('.quantity-box > .list-quantity').text(); // .menu-order (input) > .menu-list (div) > .quantity-box (div) > .list-quantity (input)
    let price = $(this_).parent().find('#div-menu-price > .menu-price').text();
    let data = {};

    // 세션정보 확인 후 로그인된 사용자인 경우 주문을 진행, 로그인되지 않은 사용자인 경우 로그인 페이지 이동 여부 묻기
    /*<![CDATA[*/
    console.log( /*[[ ${loginInfo } ]]*/);
    /*]]>*/
    if (window.confirm('\'' + foodname + '\' 상품을 주문하시겠습니까?'))
    {
        /*
        $.ajax({
            url         : '',
            type        : 'POST',
            contentType : 'application/json; charset=utf-8',
            dataType    : 'json',
            data        : ''
        }).done(function () {

        }).fail(function (err) {
            window.alert(JSON.stringify(err));
            console.log(err);
        });
        */
    }
}

/**
 * @author Johnny
 * @date 2019-01-22
 * @role 선택 상품 주문 요청 함수
 */
function fn_selectOrder() {
    let selected_checkbox = [],
        all_checkbox = [];
    all_checkbox = $(list_check);

    $(all_checkbox).each(function (index) {
        selected_checkbox.push(all_checkbox[index]);
    });

    if (window.confirm('선택한 상품을 주문하시겠습니까?'))
    {

    }
}

/**
 * @author Johnny
 * @date 2019-01-22
 * @role 전체 상품 주문 요청 함수
 */
function fn_allOrder() {

    if (window.confirm('전체 상품을 주문하시겠습니까?'))
    {

    }
}