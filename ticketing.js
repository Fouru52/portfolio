var html = new Vue ({
	el:"#list",
	data:{
		all:false,
		a:[],
		b:[],
		data:"",
		search: username, // 단어 검색
		parts: parts,
	},
	methods:{
		cancel:function(){
			if(this.b == [] || this.b.length == 0){
				alert('취소할 예매정보를 선택해주세요.');
				
			}
			else{
				if(confirm('해당 예매정보를 취소하시겠습니까?')){
					location.href = "./ticket_cancel.do?tidx="+this.b;
				}
			}
		},
		oneclick:function(nidx,v){	// 개별
		if(v['target']['checked'] == false){
			this.b.pop(nidx)
		}
		else if(v['target']['checked'] == true){
			this.b.push(nidx);
		}
		
		var ckea = document.getElementsByName("t").length;
			
			if(this.a.length < ckea){
				this.all = false;
			}
			else {
				this.all = true;
			}
		},
		go_search:function(e){	//검색 부분의 메소드
			e.preventDefault();
			if(this.search == ""){
				alert("검색할 단어를 입력하세요.");
			}
			else{
				f.method = "post";
				f.action = "./ticket_list.do";
				f.submit();
			}
		},
	},
	computed:{
		abc:function(){
			if(this.parts == ""){
				this.parts = '1';
			}
			else{
				this.parts = this.parts
			}
		}
	}
});


