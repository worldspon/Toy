'use strict'

var add_btn = document.getElementById("add_div");
var reg_form = document.getElementById('reg_form');
var reg_btn = document.getElementById('reg_btn');
var del_form = document.getElementById('del_form');
var del_btn = document.getElementById('del_btn');
var burger_ary =[];



var product_page = document.querySelector('.admin_box ul li:first-child');
var sell_page = document.querySelector('.admin_box ul li:last-child');
var on_product_page = function(){
    document.getElementById('product_area').style.display = 'block';
    document.getElementById('sell_area').style.display = 'none';
}

var on_sell_page = function(){
    document.getElementById('product_area').style.display = 'none';
    document.getElementById('sell_area').style.display = 'block';
}


view_product_list(burger_ary);

add_btn.addEventListener('click', function(){
    var img_file = document.getElementsByClassName('image_file');
    if(img_file.length<3){
        reg_form.innerHTML+= '<h3>이미지 업로드</h3><input type="file" class="image_file" accept=".jpg,.jpeg,.png"><h3>상품명</h3><input type="text" class="product_name"><h3>상품가격</h3><input type="text" class="product_price">';
    }
});

reg_btn.addEventListener('click',function(){
    //등록
});


/**
 * @Authur jjh
 * @role 함수의 목적 기능0
 * @param {*} val 서베에서 받아온 배열값
 * @date 2019.01.09
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
        del_form.innerHTML += '<br/><input type="checkbox" name="del_checkbox" class="del_checkbox"><label class="">'+val[i].name+'<span style="float:right;">'+sell_val+'</span></label>';
    }
};
del_btn.addEventListener('click',function(){
    console.log("A");
});

function del_product(){
    let del_checkbox_name = document.getElementsByClassName('del_checkbox');
    for(let i=0; i<burger_ary.length; i++){
        if(del_checkbox_name[i].checked==true){
            console.log(burger_ary[i]);
        }
    }
    location.reload();
}