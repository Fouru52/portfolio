// vue => Front-end(O), Back-end(X)
// react => Front-end(X), Back-end(O)


let html = new Vue ({
	el:"#box",
	data : {	//methods의 이름과 동일한 이름을 사용금지
		b:"button",
		numbers : 0,
		btn1:"+",
		btn2:"-"
	},
	methods:{	// data의 이름과 동일한 이름을 사용금지
		btn_numbers:function(z){		// 이벤트 핸들링
			if(z=="+"){
				this.numbers++;			// +1증가
			}
			else{
				if(this.numbers > 0){	//0보다 큰 값일 경우
				this.numbers--;			// -1감소
				}
			}
		}
	}
});




var html2 = new Vue ({
	el : "#box2",
	data : {
		s:"",
		ea:"",
		total_number:0
	},
	methods : {
		product:function(){
			console.log(this.s);
			console.log(this.ea);
			this.total_number = (Number(this.s) * Number(this.ea)).toLocaleString();
		}
	}
});









