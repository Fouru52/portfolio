<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vue - 반복문 v-for(for in)</title>
<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
</head>
<body> 
<!-- body 태그에는 vue 컨테이너 생성이 안됨 -->
<!-- v-for : 배열이 기본(키배열, 배열) -->
	<aside id="box">
		<ul>
			<!-- v-for는 vue 전용 반복문 출력 (for in) 기본 사항 -->
			<!-- lists 배열값 a 배열의 데이터 값 -->
			<li v-for="a in lists">{{a}}</li>
		</ul>
		
		<ol>
			<!-- key 배열 키 이름을 이용하여 출력형태, index : 배열번호 -->
			<li v-for="b,index in person">{{b.names}}-{{b.age}} {{index}}</li>
		</ol>
		
		<dl>
			<dt>응시자 시험합격 현황</dt>
			<!-- c : 배열 데이터값, d : 배열 키 이름, e : 배열 노드번호 -->
			<dd v-for="c,d,e in pass">
				{{c}}
			</dd>
		</dl>
		
		<!-- 배열 값 없이 사용시 숫자는 1부터 시작 aa(배열번호) bb(노드번호) -->
		<ul>
			<li v-for="aa,bb in fileno"><input type="file" name="mfile">{{bb}}</li>
		</ul>
	</aside>
</body>
<script src="./vue11.js?v=3"></script>
</html>






