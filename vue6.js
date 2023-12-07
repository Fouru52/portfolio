var html = new Vue ({
	el:"#box",
	data : {
		
	},
	methods : {
		kkk:function(){ // 이벤트 핸들링 메소드
			this.bbb();	// this를 이용하여 해당 메소드로 이동
		},
		bbb:function(){	// 이벤트 메소드
			console.log("메소드 이동");
			var result = this.ccc();
			console.log(result);
		},
		ccc:function(){	//클로저 메소드 
			return "홍길동";
		}
	}
});