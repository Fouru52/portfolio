var html = new Vue ({
	el:"#homepage",
	data:{
		use1: use1,
		use2: use2,
		use3: use3,
		use4: use4,
		use5: use5,
		method:"POST",
		action:"./admin_siteinfook.do",
		enctype:"application/x-www-form-urlencoded"
	},
	methods:{
		cancle:function(){
			window.location.reload();
		},
		save:function(){
				if(f.site_title.value == ""){
					alert("홈페이지 이름을 입력하세요.");
				}
				else if(f.site_email.value == ""){
					alert("관리자 메일 주소를 입력하세요.");
				}
				else if(f.site_level.value == ""){
					alert("권한 레벨을 입력하세요.");
				}
				else if(f.site_corpnm.value == ""){
					alert("회사명을 입력하세요.");
				}
				else if(f.site_corpno.value == ""){
					alert("사업자등록번호를 입력하세요.");
				}
				else if (f.site_ceo.value == ""){
					alert("대표자명을 입력하세요.");
				}
				else if (f.site_tel.value == ""){
					alert("대표전화번호를 입력하세요.");
				}
				else if (f.site_post.value == ""){
					alert("사업장 우편번호를 입력하세요.");
				}
				else if (f.site_add.value == ""){
					alert("사업장 주소를 입력하세요.");
				}
				else if (f.site_admin.value == ""){
					alert("정보관리 책임자명을 입력하세요.")
				}
				else if (f.site_admail.value == ""){
					alert("정보책임자 이메일을 입력하세요.")
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