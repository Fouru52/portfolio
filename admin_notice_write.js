window.onload = function(){
	var ck = CKEDITOR.replace("boardtext",{
		width:800,
		height:285
	});
}
var html = new Vue({
	el:"#page_section",
	data:{
		data:"N",
		data1:"",
		method:"POST",
		enctype:"multipart/form-data",
		action:"./notice_writeok.do"
	},
	methods:{
		check:function(){
			if(f.top.checked == true){
				this.data = "Y"
			}
			else{
				this.data = "N"
			}
		},
		notice_write:function(){
			var ck = CKEDITOR.instances.boardtext.getData();
			
			if(this.data1 == ""){
				alert("공지사항 제목을 입력하세요.");
				f.nsubject.focus();
			}
			else if (ck == ""){
				alert("내용을 입력하셔야 합니다.");
			}
			else{
				f.method = this.method;
				f.action = this.action;
				f.enctype = this.enctype;
				f.submit();
			}
		},
		notice_list:function(){
			location.href="./notice_list.do";
		}
	}
});