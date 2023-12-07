var html = new Vue ({
	el : "#box",
	data:{
		view:3,
		banner_no:0 // 배너 번호를 배열번호 형태
	},
	methods: {
		banner_movie:function(z){	// 이전, 다음 버튼에 대한 핸들링 메소드
			console.log(z);
			if(z=="right"){		//다음
				this.banner_no++;
				if(this.banner_no > 2){	// + 값 중 맨 마지막 배너일 경우 처음 배너 번호로 호출
					this.banner_no = 0;
				}
			}
			else{	// 이전
				this.banner_no--;
				if(this.banner_no < 0){	// -값일 경우 맨 마지막 배너를 호출
					this.banner_no = 2;
				}
			}
		}
	}
});