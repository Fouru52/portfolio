var html = new Vue ({
	el:"#admin_login",
	data:{
		aid:"",
		apass:"",
		method:"POST",
		action:"./loginok.do",
		enctype:"application/x-www-form-urlencoded"
	},
	methods:{
		new_admin:function(){
			location.href="./add_master.jsp";
		},
		login:function(){
			if(this.aid == "" || this.aid.length < 5){
				alert("아이디는 최소 5글자 이상 입력하세요.");
				f.aid.focus();
			}
			else if(this.apass == ""){
				alert("패스워드를 입력하세요.");
				f.apass.focus();
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