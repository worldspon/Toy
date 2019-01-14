var slide_btn = document.getElementsByClassName('slide-btn');
var ft = document.getElementById('ft');
var flag=0;

slide_btn[0].addEventListener('click',function(){
    console.log(flag);
    if(flag==0){
        slideDown();
        flag=1;
    } else{
        slideUp();
        flag=0;
    }
});

function slideDown() {
    ft.style.transition = "all 1s ease-in-out";
    ft.style.bottom = '-200px';
    slide_btn[0].innerHTML = '△';
};


function slideUp() {
    ft.style.transition = "all 1s ease-in-out";
    ft.style.bottom = '-10px';
};

//▽