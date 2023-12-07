let html = new Vue({	// 실시간 데이터를 반영할 수 있음.
	el:"#box",
	data:{
		t1 : "",
		t2 : "",
		p : "숫자를 입력하세요",
		total: ""
	},
	methods:{
		abc:function(){ // vue의 html 값은 기본 문자
			// this => data함수를 지칭
			this.total = Number(this.t1) + Number(this.t2);
		}
	}
});



let html2 = new Vue ({
	el:"#box2",
	data : {
		code : "",			// 사용자 입력값
		no : "0123456",		// 발급된 보안코드
		msg : "" 			// 메세지 출력 변수
	},
	methods : {
		bbb:function(){
			if(this.code == ""){
				this.msg = "보안코드를 입력하세요.";
			}
			else{
				if(this.code == this.no){
					this.msg = "보안코드를 확인했습니다.";
				}
				else{
					this.msg = "해당 보안코드가 잘못됐습니다.";
				}
			}
		}
	}
});