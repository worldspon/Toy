<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
var msg = '<c:out value="${msg }" />';
window.alert(msg);
window.location.href = '/login'; 
</script>