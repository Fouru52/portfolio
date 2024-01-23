window.onload = function(){
	var ck = CKEDITOR.replace("boardtext",{
		width:800,
		height:385
	});
}
var html = new Vue({
	el:"#write",
	data:{
		fsubject:"",
		method:"POST",
		enctype:"application/x-www-form-urlencoded",
		action:"./faq_writeok.do"
	},
	methods:{
		write:function(){
			var ck = CKEDITOR.instances.boardtext.getData();
			
			if(this.fsubject == ""){
				alert("제목을 입력하세요.");
				f.fsubject.focus();
			}
			else if (ck == ""){
				alert("내용을 입력하셔야 합니다.");
				f.ftext.focus();
			}
			else{
				f.method = this.method;
				f.action = this.action;
				f.enctype = this.enctype;
				f.submit();
			}
		}
	}
});