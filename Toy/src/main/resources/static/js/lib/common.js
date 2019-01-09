//document.body.innerHTML+='<div>aa</div>';
var login=0;
var logout = () => {
    console.log("A");
    login=0;
    window.location.reload();
}


/**
 * 
 * @author Johnny
 * @Params []
 * @date 2018-01-09
 * @descript 헤더의 엘리먼트 태그 표현은 js가 아닌 Thymeleaf 템플릿 엔진 구문으로 처리함
 */
/*
var parent = document.querySelector('.sign_box ul');
if(login){
    document.querySelector('.sign_box ul li:first-child').innerHTML = '정주호님 환영합니다!';
    document.querySelector('.sign_box ul li:nth-child(2)').innerHTML = '<a href="" onclick="logout();">로그아웃</a>';
} else {
    document.querySelector('.sign_box ul li:first-child').innerHTML = '<a href="join.html">회원가입</a>';
    document.querySelector('.sign_box ul li:nth-child(2)').innerHTML = '<a href="login.html">로그인</a>';
}
*/
