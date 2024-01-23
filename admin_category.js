var html = new Vue ({
	el:"#list",
	data:{
		all:false,
		a:[],
		b:[],
		data:"",
		search: airname, // 단어 검색
		parts: parts,
	},
	methods:{
		code_del:function(){
			console.log(this.b.length == 0)
			if(this.b == [] || this.b.length == 0){
				alert('삭제할 항공코드를 선택해주세요.');
				
			}
			else{
				if(confirm('해당 항공코드를 삭제하시겠습니까?')){
					location.href = "./code_check_del.do?aidx="+this.b;
				}
			}
		},
		selectall:function(){	//전체
			if(this.all == true){
		var ckea = document.getElementsByName("t").length;
				
				var w = 0;
				
				while( w < ckea){
					this.a.push(f.t[w].value);
					this.b.push(f.t[w].value);
					w++;
				}
				console.log(this.b);
			}
			else{
				var ckea = document.getElementsByName("t").length;
				this.a = [];
				var w = 0;
				
				while( w < ckea){
					this.b.pop(f.t[w].value);
					w++;
				}
				console.log(this.b);
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
				f.action = "./air_list.do";
				f.submit();
			}
		},
		modi:function(aidx){
			location.href = './code_modify.do?aidx='+aidx;
		}
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


