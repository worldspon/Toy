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


// 서버의 상품목록을 받아 화면에 그리는 함수
view_product_list(bur_ary);



// + button click event
add_btn.addEventListener('click', function(){
    // 상품등록 박스를 추가하는 함수
    plus_reg_box();
});



reg_btn.addEventListener('click',function(){
    //@TODO 상품등록 box의 값을 읽어 서버로 보내는 함수 구현
    //@see 각 input tag는 .image_flie / .product_name / .product_price .product_quantity Class로 지정돼있음
});



del_btn.addEventListener('click',function(){
    //@TODO del_product() 함수 활용하여 상품 제거코드 구현
});




//@TODO 수정버튼 클릭시 해당 index에 해당하는 객체를 수정페이지로 이동시키는 코드 구현
$(mod_btn).on('click',function(){
    console.log($(mod_btn).index(this));
    window.location.href = 'manager_mod.html';
});








/**
 * @Authur JJH
 * @role 상품등록 form 아래 상품등록박스를 추가하는 함수
 * @see 현재 3개로 제한
 * @date 2019.01.10
*/
function plus_reg_box() {
    let img_file = document.getElementsByClassName('image_file');
    if(img_file.length<3){
        reg_form.innerHTML+= '<h3>이미지 업로드</h3><input type="file" class="image_file" accept=".jpg,.jpeg,.png"><h3>상품명</h3><input type="text" class="product_name"><h3>상품가격</h3><input type="text" class="product_price"><h3>상품수량</h3><input type="text" class="product_quantity">';
    }
};







/**
 * @Authur JJH
 * @role 서버에서 받아온 상품 목록을 상품제거 form 아래에 '이름' '상품등록여부' 순으로 나열
 * @param val 서버에서 받아온 배열
 * @date 2019.01.10
*/
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