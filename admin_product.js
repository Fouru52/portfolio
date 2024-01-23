var html = new Vue ({
	el:"#list",
	data:{
		search:airname, // 단어 검색
		parts:parts,
	},
	methods:{
		go_search:function(e){	//검색 부분의 메소드
			e.preventDefault();
			if(this.search == ""){
				alert("검색할 항공사명을 입력하세요.");
			}
			else{
				f.method = "post";
				f.action = "./product_list.do";
				f.submit();
			}
		},
	},
});