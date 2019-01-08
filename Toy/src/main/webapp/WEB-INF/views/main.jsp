<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> 
<html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>main</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="">
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="#">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
        MAIN PAGE!!
        <h1><c:out value="${loginInfo.userid }" /></h1>
        <h1><c:out value="${loginInfo.username }" /></h1>
        <h1><c:out value="${loginInfo.useremail }" /></h1>

        <script src = "/js/jquery-3.3.1.min.js"></script>
        <script>
            $(document).ready(function() {
                loginCheckMsg();
            });

            // 인터셉터에 의하여 메인 페이지로 반환되는 경우
            function loginCheckMsg() {
                var msg = '<c:out value="${msg }" />';
                if (msg && msg.length > 0)
                {
                    window.alert(msg);
                }
            }
        </script>
    </body>
</html>