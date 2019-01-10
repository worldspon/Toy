var sell_reg_form_id = document.getElementById('sell_reg_form');
var sell_del_form_id = document.getElementById('sell_del_form');
var bur_ary = []; // 상품목록 전부를 저장하는 변수
var bur_ary_true = []; // 판매중인 상품을 저장하는 변수
var bur_ary_false = []; // 미판매 상품을 저장하는 변수


/*----------------------------------------------------------------------------
//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// view_product_list() 함수 테스트 코드, 확인 후 삭제요망
*/



var buger1 = {
    name:'불고기',
    price:5900,
    is_sell:true
};
var buger2 = {
    name:'물고기',
    price:5900,
    is_sell:true
};
var buger3 = {
    name:'흙고기',
    price:5900,
    is_sell:false
};
var buger4 = {
    name:'바람고기',
    price:5900,
    is_sell:true
};
var buger5 = {
    name:'번개고기',
    price:5900,
    is_sell:false
};



/*
//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
----------------------------------------------------------------------------//
*/





/////////////////////////////////////////////////////////////////////////

var bur_ary = [buger1,buger2,buger3,buger4,buger5]; //상품목록 저장 테스트코드 확인 후 삭제요망

/////////////////////////////////////////////////////////////////////////



view_sell_data(bur_ary,bur_ary_false,bur_ary_true); //화면에 상품을 표시하는 함수









/**
 * @Authur JJH
 * @role 상품 목록을 판매상태별로 분류하여 화면에 표시하고 각 배열에 저장하는 함수
 * @param val 서버에서 받아온 전체 상품목록 배열
          val_false 미판매상품 저장 배열
          val_true 판매상품 저장 배열
 * @date 2019.01.10
*/
function view_sell_data(val, val_false, val_true){
    let index = val.length;
    for(let i=0; i<val.length; i++){
        if(val[i].is_sell==false){
            val_false.push(val[i]);
            sell_reg_form_id.innerHTML += '<br /><input type="checkbox" class="reg_checkbox"><label for="">'+val[i].name+'<span style="float:right;">미판매</span></label>';
        } else{
            val_true.push(val[i]);
            sell_del_form_id.innerHTML += '<br /><input type="checkbox" class="del_checkbox"><label for="">'+val[i].name+'<span style="float:right;">판매중</span></label>';
        }
    }
};





/**
 * @Authur JJH
 * @role 미판매목록 중 checked 된 상품을 골라 서버와 통신하여 판매상태를 true로 변경하는 함수
 * @TODO console.log() 삭제 후 서버통신 및 변경코드 구현
 * @see reg_checkbox는 view_sell_data() 함수 구동시 생성되는 checkbox의 class name
 * @date 2019.01.10
*/
function reg_sell(){
    let reg_checkbox_name = document.getElementsByClassName('reg_checkbox');
    
    
    for(let j=0; j<reg_checkbox_name.length; j++){
        if(reg_checkbox_name[j].checked==true){
            console.log(bur_ary_false[j]);
        }
    }
    //location.reload();
};






/**
 * @Authur JJH
 * @role 판매 목록 중 checked 된 상품을 골라 서버와 통신하여 판매상태를 false로 변경하는 함수
 * @TODO console.log() 삭제 후 서버통신 및 변경코드 구현
 * @see del_checkbox는 view_sell_data() 함수 구동시 생성되는 checkbox의 class name
 * @date 2019.01.10
*/
function del_sell(){
    let del_checkbox_name = document.getElementsByClassName('del_checkbox');

    
    for(let j=0; j<del_checkbox_name.length; j++){
        if(del_checkbox_name[j].checked==true){
            console.log(bur_ary_true[j]);
        }
    }
    //location.reload();
};