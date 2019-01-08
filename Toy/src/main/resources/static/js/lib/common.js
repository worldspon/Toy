//document.body.innerHTML+='<div>aa</div>';
var login=0;
var logout = () => {
    console.log("A");
    login=0;
    window.location.reload();
}

var parent = document.querySelector('.sign_box ul');
if(login){
    document.querySelector('.sign_box ul li:first-child').innerHTML = '정주호님 환영합니다!';
    document.querySelector('.sign_box ul li:nth-child(2)').innerHTML = '<a href="" onclick="logout();">로그아웃</a>';
} else {
    document.querySelector('.sign_box ul li:first-child').innerHTML = '<a href="join.html">회원가입</a>';
    document.querySelector('.sign_box ul li:nth-child(2)').innerHTML = '<a href="login.html">로그인</a>';
}

