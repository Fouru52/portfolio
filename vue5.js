var b = new Vue({
	el:"#box2",
	data : {
		search:""
	},
	methods :{
		gopage:function(k){
			k.preventDefault();
			if(this.search==""){
				alert("검색할 상품명을 입력해주세요!");
				//sh.search.focus();		//2.5 버전 이상 정상작동
				this.$refs.search.focus();	//2.5 버전 이하
			}
			else{
				sh.submit();
			}
		}
	}
});



var a = new Vue ({
	el : "#box",
	data : {
		cp:""	///radio 선택값
	},
	methods : {
		abc:function(z){
			z.preventdefault();	//submit 전용, return false 동일
			if(this.cp == ""){
				alert("쿠폰을 선택해주셔야 합니다.");
			}
			else{
				//returm false, return true (vue에서 존재하지 않음)
				f.submit();
			}
		}
	}
});