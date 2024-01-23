var html = new Vue ({
	el:"#faq",
	data:{
		search:faqname // 단어 검색
	},
	methods:{
		go_search:function(e){	//검색 부분의 메소드
			e.preventDefault();
			if(this.search == ""){
				alert("검색할 내용을 입력하세요.");
			}
			else{
				f.method = "post";
				f.action = "./faq_list.do";
				f.submit();
			}
		},
		faq_del:function(fidx){
				if(confirm('삭제시 복구 되지 않습니다. 해당 공지글을 삭제하시겠습니까?')){
				location.href = "./faq_delete.do?fidx="+fidx;
			}
		},
		faq_inner:function(fidx){
			location.href="./faq_view.do?fidx="+fidx;
		}
	}
});