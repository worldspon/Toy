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

/**
 * @Authur Johnny
 * @role 중복 클릭 이벤트 바인딩 차단
 * @TODO 버튼에 대한 클릭이벤트가 1회 이상 중복 발생할 수 없도록 제어함
 * @return boolean [true: 클릭 이벤트 로직 진행, false: 클릭 이벤트 로직 진행 차단]
 * @date 2019.01.16
*/
function fn_isRun(isRun) {
    if (isRun)
    {
        return false;
    }
    else
    {
        return true;
    }
}




/**
 * @Author Johnny
 * @Authur JJH
 * @role input 태그들의 데이터 유효성 검사
 * @TODO 유효성 검사할 태그 데이터를 배열로써 인자에 전달해야함
 * @return true: 데이터 요소 검증이 되지 않음 (데이터가 없음), false: 데이터 요소의 검증이 완료됨
 * @date 2019.01.14
*/
function fn_validation(arr) {
    // 검증할 데이터가 존재하는지 판단
    if (arr && arr.length > 0)
    {
        for (let i = 0; i < arr.length; i++)
        {
            let arr_value = '';
            arr_value = arr[i];
            // 검증할 데이터가 작성되지 않았다면 0을 반환
            if (arr_value==null || arr_value=='' || arr_value.length < 1)
            {
                alert("모든 입력란을 등록해주세요.");
                return false;
            }
        }

        return true;   // 데이터 요소 검증 완료
    }

    return false;
}



/**
 * @Author Johnny
 * @role input 태그들의 숫자 데이터 유효성 검사
 * @TODO 유효성 검사할 태그 데이터를 배열로써 인자에 전달해야함
 * @return true: 데이터 요소 검증이 되지 않음 (데이터가 없음), false: 데이터 요소의 검증이 완료됨
 * @date 2019.01.18
*/
function fn_numberValidation(arr) {
    let reg = /^[0-9]*$/g; // 숫자 검증 정규식
    
    // 검증할 데이터가 존재하는지 판단
    if (arr && arr.length > 0)
    {
        for (let i = 0; i < arr.length; i += 1)
        {
            if (!reg.test(arr[i]))
            {
                window.alert('숫자만 입력해주세요.');
                return false;
            }
        }

        return true;
    }

    return false;
}
