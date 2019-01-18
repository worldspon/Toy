/**
 * @Authur Johnny
 * @role 화면 내 메뉴들의 이벤트 정의 목록
 * @date 2019.01.16
*/
$(document).ready(function () {

   // 파일 버튼 클릭 이벤트
   $(document).on('click', '#file-imgfile', function () {
      if (fn_isRun(false))
      {
         
      }
   });

   // 수정 버튼 클릭 이벤트
   $(document).on("click", "#mod_btn", function () {
      if (fn_isRun(false))
      {
         fn_mod_product();
      }
   });
});


/**
 * @Authur Johnny
 * @role 상품 내용 수정 요청 함수
 * @date 2019.01.17
*/
function fn_mod_product() {
   // 검증할 데이터들
   let foodname = $('#txt-foodname').val();
   let foodprice = $('#txt-foodprice').val();
   let stock = $('#txt-stock').val();

   let check_val = [foodname, foodprice, stock];

   if (fn_validation(check_val))
   {
      if (window.confirm("변경한 내용을 저장하시겠습니까?"))
      {
         document.forms[0].submit();
      }
   }
}
