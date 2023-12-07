<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    	String username = "홍길동";
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vue 외부변수 활용 및 html출력</title>
<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
</head>
<body>
	<section id="box">
		{{user}}님 환영합니다. 포인트는 {{money}} 입니다. <br>
		<span v-text="texts"><div></div></span> <!-- v-text : innerText(문자,숫자) -->
		<span v-html="htmls"></span>			<!-- v-html : innerHTML(HTML코드) -->
		<ul v-html="lists"></ul>				<!-- 데이터 출력 파트 -->
		<!-- 버튼 클릭시 vue에서 반복문으로 출력  -->
		<button type="button" v-on:click="ok">클릭</button>
	</section>
</body>
<script>
	var a = "<%=username%>"; // js코드로 back-end 변수값을 받음 
	var point = 1000;
</script>
<script src="./vue8.js?v=1"></script> <!-- vue 컨테이너 -->
</html>