<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vue - checkbox 활용 및 실시간 데이터 복제 </title>
<script src="https://cdn.jsdelivr.net/npm/vue@2.7.15"></script>
</head>
<body>
	<section id="box">
		<input type="text" v-model="search"> <!-- 사용자가 입력한 값 -->
		<span>{{search}}</span>				 <!-- v-model과 같은 이름일 경우 입력한 값을 바로 출력 -->
		<br><br>
		<input type="checkbox" name="s" value="HTML" v-model="subject">HTML
		<input type="checkbox" name="s" value="JAVA" v-model="subject">JAVA
		<input type="checkbox" name="s" value="VUE" v-model="subject">VUE
		<span>{{subject}}</span>
	</section>
	
	<form id="f" action="./vue4.jsp" method="get"> <!-- form은 vue와 상관없이 사용가능 -->
		<section id="box2">
		<!-- checkbox, radio는 value가 적용되어서 get, post로 값을 전송할 수 있음 -->
			<input type="checkbox" name="ag" value="Y" v-model="agree">동의함<br>
			<input type="button" value="클릭" v-on:click="aaa"><!-- vue 핸들링 -->
		</section>
	</form>
</body>
<script src="./vue4.js?v=1"></script>
</html>