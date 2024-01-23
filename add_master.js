
var html = new Vue ({
	el:"#master",
	data:{
		aid :"",
		apass : "",
		apass2 : "",
		anm:"",
		aemail :"",
		atel :"",
		atel2 : "",
		atel3 :"",
		adepartment: "",
		aposition:"",
		pass_msg: "",
		data: "",
		method:"POST",
		action:"./joinok.do",
		enctype:"application/x-www-form-urlencoded"
	},
	methods:{
		idcheck:function(){
		    if(this.aid.length < 5){
		        alert("아이디는 5글자 이상 입력하셔야 합니다.");
		    }
		    else{
				console.log(this.aid)
				fetch("./idcheck.do?aid="+this.aid).then(function(aa){
					return aa.text();
				}).then(function(bb){
					html.message(bb);
				}).catch(function(error){
					console.log("통신오류 발생!");
				});
				
			}
		},		
		message:function(bb){
			if(bb == 1){
				alert("해당 아이디는 사용하실 수 없습니다.");
				this.data = "";
			}
			else{
				alert("해당 아이디는 사용 가능합니다.");
				this.data = "duplicate_clicks";
			}
		},
		admin_join:function(){
			if(this.aid == ""){
					alert("아이디를 입력하세요.");
					f.aid.focus();
				}
				else if(this.apass == ""){
					alert("패스워드를 입력하세요.");
					f.apass.focus();
				}
				else if(this.anm == ""){
					alert("이름을 입력하세요.");
					f.anm.focus();
				}
				else if(this.aemail == ""){
					alert("이메일을 입력하세요.");
					f.aemail.focus();
				}
				else if (this.atel == ""){
					alert("연락처 앞자리를 입력하세요.");
					f.atel.focus();
				}
				else if (this.atel2 == ""){
					alert("연락처 중간자리를 입력하세요.");
					f.atel2.focus();
				}
				else if (this.atel3 == ""){
					alert("연락처 뒷자리를 입력하세요.");
					f.atel3.focus();
				}
				else if (this.adepartment == ""){
					alert("담당자 부서를 선택하세요.");
					
				}
				else if (this.aposition == ""){
					alert("담당자 직책을 선택하세요.");
					
				}
				else if (this.data == ""){
					alert("중복체크를 눌러주세요.")
				}
		
				else{
					if(this.apass.length < 5){
						alert("패스워드는 최소 5글자 이상 입력하셔야 합니다.");
					}
					else{
						if(this.apass != this.apass2){
							alert("동일한 패스워드를 입력하셔야 합니다.");
						}
						else{
						f.method = this.method;
						f.action = this.action;
						f.enctype = this.enctype;
						f.submit();
							
						}
					}
				}
			
		},
		admin_cancel:function(){
			location.href="./index.jsp";
		}
	}
});