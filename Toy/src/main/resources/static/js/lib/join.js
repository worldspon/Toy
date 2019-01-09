var id_flag = pw1_flag = pw2_flag = username_flag = email_flag = 0;
// 각 구간마다 올바른 검증이 되었을시 1 저장
var final_flag=0;
//모든 영역의 검증이 충족되면 1을 저장

var id = document.querySelector("#id");
var idmsg = document.querySelector('#id_msg');
var pw1 = document.querySelector("#pw1");
var pw1msg = document.querySelector('#pw1_msg');
var pw2 = document.querySelector("#pw2");
var pw2msg = document.querySelector('#pw2_msg');
var username = document.querySelector("#name");
var namemsg = document.querySelector('#name_msg');
var email = document.querySelector("#email");
var emailmsg = document.querySelector('#email_msg');
var btnjoin = document.querySelector('#btn_join');
// 구간의 id와 msg 영역의 값을 저장

var regid = /^[a-z][a-z0-9]{5,11}/;
// 첫 글자 소문자, 6~12자의 소문자+숫자 조합의 아이디 검증 정규식
var regpw = /^[a-z][a-z0-9!@#$%^&*()]{7,19}/;
// 첫 글자 소문자, 8~20자의 소문자+숫자+특수문자 조합의 비밀번호 검증 정규식
var regnum = /[0-9]/;
// check_num 함수에 사용되는 숫자 검증 정규식
var regspchar = /[!@#$%^&*()]/;
// check_spchar 함수에 사용되는 특수문자 검증 정규식
var regname = /[^가-힣]/;
// 한글 2~8자의 이름 검증 정규식
var regemail = /^[a-z0-9]+[@]+?[a-z]+[.]+?[a-z]{2,}$/;
// 이메일 형식 검증 정규식

var check_null = function(val){
    if(val==''||val==null){
        return 0;
    } else return 1;
}; //미입력, null 값 검증

var check_space = function(val){
    for(let i=0; i<val.length; i++){
        if(val[i]==' '){
            return 0;
        }
    }
    return 1;
}; //공백 검증, 공백 입력시 에러

var check_num = function(val){
    for(let i=0; i<val.length; i++){
        if(regnum.test(val)){
            return 1;
        }
    }
    return 0;
}; //숫자포함여부 검증

var check_spchar = function(val){
    for(let i=0; i<val.length; i++){
        if(regspchar.test(val)){
            return 1;
        }
    }
    return 0;
}; //특수문자 포함여부 검증

var check_flag = function(){
    if(id_flag == 1 && pw1_flag == 1 && pw2_flag == 1 && username_flag == 1 && email_flag==1){
        final_flag = 1;
    } else {
        final_flag = 0;
    }
}; // flag 변수 확인함수, 모든 flag에 1이 저장되면 final_flag에 1 저장

id.addEventListener('focusout', function(){
    var idval = this.value;
    idmsg.style.color = "red";
    id_flag=0;
    check_flag();

    var userid = $.trim($("#id").val());
    let result = 0;
    
    $.ajax({
        type: 'POST',
        url: '/join/checkid',
        dataType: 'json',
        data: { "userid": userid }
    }).done(function (result) {
        // { result: 0 or 1 }
        result = result.chkval;
        // alert(JSON.stringify(result));
        // location.reload();
    }).fail(function (err) {
        alert(JSON.stringify(err));
        console.log(err);
    });

    if(!check_null(idval)){
        idmsg.style.display = "block";
        idmsg.innerHTML = "아이디를 입력해주십시오.";
        return 0;
    } else if(!check_space(idval)){
        idmsg.style.display = "block";
        idmsg.innerHTML = "아이디에 공백을 제거해주십시오.";
        return 0;
    } else if(!regid.test(idval)){
        if(!isNaN(idval[0])){
            idmsg.style.display = "block";
            idmsg.innerHTML = "아이디는 영소문자로 시작해야합니다.";
            return 0;
        }
        idmsg.style.display = "block";
        idmsg.innerHTML = "영소문자, 숫자 조합의 6~12자 사이의 아이디를 입력하십시오.";
        return 0;
    } else if(!check_num(idval)){
        idmsg.style.display = "block";
        idmsg.innerHTML = "아이디에 숫자가 한 개 이상 포함되어야합니다.";
        return 0;
    } else if(check_spchar(idval)){
        idmsg.style.display = "block";
        idmsg.innerHTML = "아이디 특수문자는 허용되지않습니다.";
        return 0;
    }else if(result > 0) {
        idmsg.style.display = "block";
        idmsg.innerHTML = "중복된 아이디입니다.";
        return 0;
    } else {
        id_flag=1;
        idmsg.style.display = "block";
        idmsg.style.color = "green";
        idmsg.innerHTML = "멋진 아이디입니다!";
    }
    check_flag();
});

pw1.addEventListener('focusout', function(){
    let pw1val = this.value;
    pw1msg.style.color = "red";
    pw1_flag=0;
    check_flag();
    if(!check_null(pw1val)){
        pw1msg.style.display = "block";
        pw1msg.innerHTML = "비밀번호를 입력해주십시오.";
        return 0;
    } else if(!check_space(pw1val)){
        pw1msg.style.display = "block";
        pw1msg.innerHTML = "비밀번호에 공백을 제거해주십시오.";
        return 0;
    } else if(!regpw.test(pw1val)){
        pw1msg.style.display = "block";
        pw1msg.innerHTML = "영소문자, 숫자, 특수문자 조합의 8~20자 사이의 비밀번호를 입력하십시오.";
        return 0;
    } else if(!check_num(pw1val)){
        pw1msg.style.display = "block";
        pw1msg.innerHTML = "비밀번호에 숫자가 한 개 이상 포함되어야합니다.";
        return 0;
    } else if(!check_spchar(pw1val)){
        pw1msg.style.display = "block";
        pw1msg.innerHTML = "비밀번호에 특수문자가 한 개 이상 포함되어야합니다.";
        return 0;
    }else {
        pw1_flag=1;
        pw1msg.style.display = "block";
        pw1msg.style.color = "green";
        pw1msg.innerHTML = "안전한 비밀번호입니다!";
    }
    check_flag();
});


pw2.addEventListener('focusout', function(){
    let pw2val = this.value;
    pw2msg.style.color = "red";
    pw2_flag=0;
    check_flag();
    if(!check_null(pw2val)){
        pw2msg.style.display = "block";
        pw2msg.innerHTML = "비밀번호를 확인해주십시오.";
    } else if(pw1.value != pw2.value) {
        pw2msg.style.display = "block";
        pw2msg.innerHTML = "비밀번호가 다릅니다.";
    } else {
        pw2_flag=1;
        pw2msg.style.display = "block";
        pw2msg.style.color = "green";
        pw2msg.innerHTML = "올바르게 입력하셨습니다!";
    }
    check_flag();
});

username.addEventListener('focusout', function(){
    let usernameval = this.value;
    namemsg.style.color = "red";
    username_flag=0;
    check_flag();
    if(!check_null(usernameval)){
        namemsg.style.display = "block";
        namemsg.innerHTML = "이름을 입력해주십시오.";
        return 0;
    } else if(!check_space(usernameval)){
        namemsg.style.display = "block";
        namemsg.innerHTML = "이름에 공백을 제거해주십시오.";
        return 0;
    } else if(usernameval.length<2||usernameval.length>8){
        namemsg.style.display = "block";
        namemsg.innerHTML = "2~8자 사이의 한글 성명을 입력하십시오.";
        return 0;
    }else if(regname.test(usernameval)){
        namemsg.style.display = "block";
        namemsg.innerHTML = "이름에 미허용 문자가 있습니다.";
        return 0;
    } else {
        username_flag = 1;
        namemsg.style.color = "green";
        namemsg.style.display = "block";
        namemsg.innerHTML = "멋진 이름입니다!";
    }
    check_flag();
});

email.addEventListener('focusout', function(){
    let emailval = this.value;
    emailmsg.style.color = "red";
    email_flag = 0;
    check_flag();
    if(!check_null(emailval)){
        emailmsg.style.display = "block";
        emailmsg.innerHTML = "이메일을 입력해주십시오.";
        return 0;
    } else if(!check_space(emailval)){
        console.log("A");
        emailmsg.style.display = "block";
        emailmsg.innerHTML = "이메일에 공백을 제거해주십시오.";
        return 0;
    }else if(!regemail.test(emailval)){
        emailmsg.style.display = "block";
        emailmsg.innerHTML = "올바른 이메일 형식으로 입력해주십시오."
    } else {
        email_flag = 1;
        emailmsg.style.color = "green";
        emailmsg.style.display = "block";
        emailmsg.innerHTML = "이메일이 확인되었습니다!";
    }
    check_flag();
    
});

$(document).on("click", "#btn_join", function () {
    var data = {
        "userid": $.trim($("#id").val()),
        "userpwd": $.trim($("#pw2").val()),
        "username": $.trim($("#name").val()),
        "useremail": $.trim($("#email").val()) 
    };

    if (final_flag === 1) 
    {
        $.ajax({
            type: 'POST',
            url: '/join',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data)
        }).done(function (result) {
            window.alert(JSON.stringify(result.msg));
            window.location.href = '/';
        }).fail(function (err) {
            window.alert(JSON.stringify(err));
            console.log(err);
        });
    }
    else
    {
        window.alert("유효성 검사를 모두 완료해주세요.");
    }
});