var pno = window.location.search // 파라미터 값을 찾는 코드
console.log(pno);

var html = new Vue ({
	el:"#view",
	data:{
		checked : false
	},
	methods:{
		list:function(){
			location.href="./notice_list.do";
		},
		modi:function(){
			var aa = window.location.search.split("?nidx=");
			location.href='./notice_modify.do?nidx='+aa[1];
		},
		del:function(nidx, url){
			if(confirm("해당 데이터 삭제 시 복구되지 않습니다. 삭제하시겠습니까?")){
				location.href='./notice_delete.do?nidx='+nidx+"&url="+url;
			}
		}
	},
	computed:{
		ck:function(){
			var ckdata = document.getElementById("ck").value;
			if(ckdata == "N"){
				this.checked = false;
			}
			else{
				this.checked = true;
			}
		}
	}
});