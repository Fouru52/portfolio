var html = new Vue ({
	el : "#box",
	data : {
		search:"",
		subject: []		//v-model이 같은 이름일 경우 배열 기호로 받으면 배열 값으로 저장됨
	},
	methods : {
		
	}
});

var html2 = new Vue({
	el : "#box2",
	data : {
		agree: ""
	},
	methods : {
		aaa:function(){
			if(this.agree == ""){
				alert("동의함에 체크 하셔야합니다.");
			}
			else{
				//this.agree = "Y";
				f.submit();
				alert("동의 완료 되었습니다.");
			}
		}
	}
});