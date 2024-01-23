var html = new Vue ({
	el:"#notice",
	data:{
		all: false,
		a:[],
		b:[],
		data:"",
		c:[]
	},
	methods:{
		selectall:function(){	//전체
			
		var ckea = document.getElementsByName("t").length;
		
			if(this.all == true){
				var w = 0;
				
				while( w < ckea){
					this.a.push(f.t[w].value);
					this.b.push(f.t[w].value);
					w++;
				}
				//console.log(this.b);
			}
			else{
				this.a = [];
				var w = 0;
				
				while( w < ckea){
					this.b.pop(f.t[w].value);
					w++;
				}
			}
		},
		oneclick:function(nidx,v,url){	// 개별
		
		
		if(v['target']['checked'] == false){
			this.b.pop(nidx);
			this.c.pop(url);
		}
		else if(v['target']['checked'] == true){
			this.b.push(nidx);
			this.c.push(url);
		}
		
			var ckea = document.getElementsByName("t").length;
			
			if(this.a.length < ckea){
				this.all = false;
			}
			else {
				this.all = true;
			}
		},
		notice_delete:function(){
			if(this.b == [] || this.b.length == 0){
				alert('삭제할 게시물을 선택해주세요.');
				
			}
			else{
				if(confirm('해당 공지글을 삭제하시겠습니까?')){
					location.href = "./notice_check_del.do?nidx="+this.b+"&url="+this.c;
				}
			}
			
		},
		notice_registration:function(){
			location.href="./admin_notice_write.jsp";
		},
		notice_inner:function(nidx,a){
			location.href="./notice_view.do?nidx="+nidx+"&num="+a;
		}
	}
	
});