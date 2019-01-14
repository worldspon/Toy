var add_btn = document.getElementById("add_div"); // 상품등록 + button id
var reg_form = document.getElementById('reg_form'); // 상품등록 아래 innerHTML을 위한 form id
var reg_btn = document.getElementById('reg_btn'); // 상품등록 '등록' button id
var del_form = document.getElementById('del_form'); // 상품제거 아래 현재 상품 목록 innerHTML을 위한 form id
var del_btn = document.getElementById('del_btn'); // 상품제거 '제거' button id
var mod_btn;

var bur_ary =[]; // 현재 서버에 있는 상품목록을 받아올 변수

/*----------------------------------------------------------------------------
//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// view_product_list() 함수 테스트 코드, 확인 후 삭제요망
*/

var buger1 = {
    name:'불고기',
    price:5900,
    quantity:5,
    is_sell:true
};
var buger2 = {
    name:'물고기',
    price:5900,
    quantity:3,
    is_sell:true
};
var buger3 = {
    name:'흙고기',
    price:5900,
    quantity:15,
    is_sell:false
};
var buger4 = {
    name:'바람고기',
    price:5900,
    quantity:35,
    is_sell:true
};
var buger5 = {
    name:'번개고기',
    price:5900,
    quantity:0,
    is_sell:false
};

var bur_ary = [buger1,buger2,buger3,buger4,buger5];

/*
//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
----------------------------------------------------------------------------//
*/



$(document).ready(function () {
    // + button click event
    add_btn.addEventListener('click', function() {
        // 상품등록 박스를 추가하는 함수
        plus_reg_box();
    });

    $(document).on('click', '#reg_btn', function () {
        var isRun = false;  // 바인딩 차단 판단값
        
        // 여러번의 클릭 바인딩 차단
        if(isRun)
        {
            return false;
        }
        else
        {
            isRun = true;
            if (window.confirm('작성하신 상품을 등록하시겠습니까?'))
            {
                // 이미지 파일 정보를 담는 배열 변수
                var files = [];
                files = $('.image_file');

                var start_idx = [],     // 파일 명의 시작 인덱스 변수
                    files_name = [];    // 순수 파일 명을 담을 변수 

                var foodname = [],  // 음식 메뉴 이름 정보
                    foodprice = [], // 음식 메뉴 가격 정보
                    foodstock = []; // 음식 메뉴 수량 정보

                foodname = $('.product_name').val();
                foodprice = $('.product_price').val();
                foodstock = $('.product_quantity').val();

                /*
                검증할 데이터 정보들
                var validate_data = [
                    foodname, 
                    foodprice, 
                    foodstock
                ];
                */

                // 서버에 전송할 데이터
                var data = {
                    'foodimgfile'   : files,
                    'foodname'      : foodname,
                    'foodprice'     : foodprice,
                    'foodstock'     : foodstock
                };
                

                /*
                데이터 검증 처리 미완성
                for (var i = 0;  i < validate_data.length; i += 1)
                {
                    switch (fn_validation(validate_data[i]))
                    {
                        case -1: 
                            window.alert('검증할 데이터가 존재하지 않습니다.');
                            break;
                        case 0: 
                            window.alert('모든 입력 내용을 작성해주시기 바랍니다.');
                            break;
                        default:
                            break; 
                    }
                }
                */
            }
        }
    });
    
    
    
    del_btn.addEventListener('click',function(){
        //@TODO del_product() 함수 활용하여 상품 제거코드 구현
    });
    
    
    
    
    //@TODO 수정버튼 클릭시 해당 index에 해당하는 객체를 수정페이지로 이동시키는 코드 구현
    $(mod_btn).on('click',function(){
        console.log($(mod_btn).index(this));
        window.location.href = 'manager_mod.html';
    });    
});









/**
 * @Authur jjh
 * @role 상품등록 form 아래 상품등록박스를 추가하는 함수
 * @see 현재 3개로 제한
 * @date 2019.01.10
*/
function plus_reg_box() {
    /*
    let img_file = document.getElementsByClassName('image_file');
    let file_idx = $('.image_file').length;   // 상품 등록 태그들의 인덱스 구하기

    var file = $('.image_file').val();
    console.log(file);

    if(img_file.length<3){
        reg_form.innerHTML += '<div>'
            + '<h3>이미지 업로드</h3>'
            + '<input type="file" class="image_file" accept=".jpg,.jpeg,.png" id="btn-file-' + (file_idx) + '">'
            + '<h3>상품명</h3>'
            + '<input type="text" class="product_name">'
            + '<h3>상품가격</h3>' 
            + '<input type="text" class="product_price">'
            + '<h3>상품수량</h3>'
            + '<input type="text" class="product_quantity">'
            + '</div>';
    }
    */
    let img_file = document.getElementsByClassName('image_file');
    // let file_idx = $('.image_file').length;   // 상품 등록 태그들의 인덱스 구하기

    // var file = $('.image_file').val();
    // console.log(file);

    let form_div = document.createElement('div');

    let img_title = document.createElement('h3');
    let img_title_text = document.createTextNode('이미지 업로드');
    img_title.appendChild(img_title_text);

    let img_input = document.createElement('input');
    img_input.type = "file";
    img_input.className = "image_file";
    img_input.accept = ".jpg, .jpeg, .png";
    //img_input.id = "btn-file-"+(file_idx);

    let pd_title = document.createElement('h3');
    let pd_title_text = document.createTextNode('상품명');
    pd_title.appendChild(pd_title_text);

    let pd_input = document.createElement('input');
    pd_input.type = "text";
    pd_input.className = "product_name";
    pd_input.setAttribute('placeholder', 'ex)와퍼 주니어');

    let price_title = document.createElement('h3');
    let price_title_text = document.createTextNode('상품가격');
    price_title.appendChild(price_title_text);

    let price_input = document.createElement('input');
    price_input.type = "text";
    price_input.className = "product_price";
    price_input.setAttribute('placeholder', 'ex)5700');

    let quantity_title = document.createElement('h3');
    let quantity_title_text = document.createTextNode('상품수량');
    quantity_title.appendChild(quantity_title_text);

    let quantity_input = document.createElement('input');
    quantity_input.type = "text";
    quantity_input.className = "product_quantity";
    quantity_input.setAttribute('placeholder', 'ex)70 최대 5자리수');

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
};







/**
 * @Authur JJH
 * @role 서버에서 받아온 상품 목록을 상품제거 form 아래에 '이름' '상품등록여부' 순으로 나열
 * @param val 서버에서 받아온 배열
 * @date 2019.01.10
*/
/*
function view_product_list(val){
    let index = val.length;
    for(let i=0; i<index; i++){
        
        let sell_val=''
        
        if(val[i].is_sell==true){
            sell_val="판매등록"
        } else {
            sell_val="판매 미등록"
        }
        del_form.innerHTML += '<br/><input type="checkbox" name="del_checkbox" class="del_checkbox"><label class="">'+val[i].name+'\t수량 : '+val[i].quantity+'\t<button type="button" class="mod_btn">수정</button><span style="float:right;">'+sell_val+'</span></label>';
    }
    mod_btn = document.getElementsByClassName("mod_btn");
};
*/




/**
 * @Authur JJH
 * @role 상품목록 중 checked 된 상품을 골라 서버와 통신하여 제거하는 함수
 * @TODO console.log() 삭제 후 서버통신 및 삭제코드 구현
 * @see del_checkbox는 view_product_list() 함수 구동시 생성되는 checkbox의 class name
 * @date 2019.01.10
*/
function del_product(){
    let del_checkbox_name = document.getElementsByClassName('del_checkbox');
    for(let i=0; i<bur_ary.length; i++){
        if(del_checkbox_name[i].checked==true){
            console.log(bur_ary[i]);
        }
    }
    location.reload();
};

/**
 * @Authur Johnny
 * @role input 태그들의 데이터 유효성 검사
 * @TODO 유효성 검사할 태그 데이터를 배열로써 인자에 전달해야함
 * @return -1: 검증할 데이터가 없음, 0: 데이터 요소 검증이 되지 않음 (데이터가 없음), 1: 데이터 요소의 검증이 완료됨
 * @date 2019.01.14
*/
function fn_validation(arr) {
    // 검증할 데이터가 존재하는지 판단
    if (arr)
    {
        for (var i = 0; i < arr.length; i += 1)
        {
            // 검증할 데이터가 작성되지 않았다면 0을 반환
            if (arr[i].length < 1)
            {
                return 0;
            }
        }

        return 1;   // 데이터 요소 검증 완료
    }
    else
    {
        return -1;
    }
}