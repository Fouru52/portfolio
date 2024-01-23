	var a = "<%=usernm%>";											

	if(a != ""){ 													
	var html = a + "관리자 <a href='#'>[개인정보 수정]</a><a href='./logout.do'>[로그아웃]</a>";
		document.getElementById("admin").innerHTML = html;			
		document.getElementById("admin").style.display = "block";
	}
	else{															
		document.getElementById("admin").style.display = "none";
	}
