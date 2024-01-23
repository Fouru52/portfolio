var html = new Vue ({
	el:"#main",
	data:{
	aid:aid
	},
	methods:{
		ok:function(a,b){
			console.log(a)
			console.log(this.aid)
			if(this.aid == "admin"){
				location.href='./ok.do?aidx='+a+"&aid="+b;
			}
			else{
				alert('관리자만 사용가능합니다.');
			}
		},
		no:function(a){	
			if(this.aid == "admin"){
				location.href='./no.do?aidx='+a;
			}
			else{
				alert('관리자만 사용가능합니다.');
			}
		}
	}
});