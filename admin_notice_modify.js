
window.onload = function(){
	var ck = CKEDITOR.replace("boardtext",{
		width:800,
		height:285
	});
}

var html = new Vue ({
	el:"#modify",
	data:{
		htmltag: filenm, 
		checked : "N",
		nsubject: nsubject,
		nwrite: nwrite,
		method: "POST",
		enctype:"multipart/form-data",
		action:"./notice_modifyok.do"
	},
	methods:{
		ckbox:function(){
			if(f.top.checked == true){
				this.checked = "Y"
			}
			else{
				this.checked = "N"
			}
		},
		notice_list:function(){
			location.href="./notice_list.do";
		},
		notice_modifyok:function(){
				if(this.nsubject == ""){
					alert("제목을 입력하세요.");
					f.nsubject.focus();
				}
				else if(this.ntext == ""){
					alert("내용을 입력하세요.");
					f.ntext.focus();
				}
				else{
					if(confirm('해당 내용을 수정하시겠습니까?')){
						f.method = this.method;
						f.action = this.action;
						f.enctype = this.enctype;
						f.submit();
					}
				}
			}
		},
	});