var mod_form = document.getElementById("mod_form");
var mod_btn = document.getElementById("mod_btn");



/*----------------------------------------------------------------------------
//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// 함수 테스트 코드, 확인 후 삭제요망
*/

var buger1 = {
    img:"../../static/img/site/logo.png",
    name:'불고기',
    price:5900,
    quantity:15,
    is_sell:true
};

/*
//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
----------------------------------------------------------------------------//
*/




view_data(buger1);//화면에 상품을 표시하는 함수
console.log(mod_btn);
console.log(mod_form);

$(document).on("click", "#mod_btn", function(){
   console.log("A");
    //@TODO 수정사항 적용 코드 작성 요망
});






/**
 * @Authur JJH
 * @role 해당 상품의 상세정보를 표시하는 함수
 * @param val 클릭시 index값에 해당하는 상품객체
 * @date 2019.01.10
*/
function view_data(val){
   mod_form.innerHTML += '<br><h3>이미지 수정</h3><img src=\''+val.img+'\'><input type="file" class="image_file" accept=".jpg,.jpeg,.png"><h3>상품명</h3><input type="text" class="product_name" value=\''+val.name.toString()+'\'><h3>상품가격</h3><input type="text" class="product_price" value=\''+val.price+'\'><h3>상품수량</h3><input type="text" class="product_quantity" value=\''+val.quantity+'\'>';
};