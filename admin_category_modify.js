var html = new Vue ({
	el:"#modi",
	data:{
		method: "POST",
		enctype:"application/x-www-form-urlencoded",
		action:"./code_modifyok.do",
		cock:false
	},
	methods:{
		del:function(aidx){
				if(confirm("해당 데이터 삭제 시 복구되지 않습니다. 삭제하시겠습니까?")){
				location.href='./code_delete.do?aidx='+aidx;
			}
		},
		modiok:function(){
			var code = /[^A-Z]/g;  
			var airname = /[A-Z0-9가-힣\s]/gi;
			var airflight = /[^a-z]/gi;
			
			if( f.airplane.value == "" || code.test(f.airplane.value) == true){
				alert("올바른 공항코드를 입력하세요.");
				this.airplane = "";
			}
			else if(f.airname.value == "" || code.test(f.airname.value) == false){
				alert("올바른 항공사명을 입력하세요.");
				this.airname = "";
			}
			else if(f.airflight.value == "" || code.test(f.airflight.value) == false ){
				alert("항공편명을 입력하세요.");
				f.airflight.focus();
			}
			else if(this.cock == "false"){
				alert("중복 확인을 진행해주세요.");
			}
			else{
				f.method = this.method;
				f.action = this.action;
				f.enctype = this.enctype;
				f.submit();
			}
		},
		codeck:function(){
				fetch("./codeck.do?aircode="+f.aircode.value).then(function(data){
					return data.text();
				}).then(function(data){
					html.ck(data);
				}).catch(function(error){
					console.log("Ajax 통신 오류");
				})
			},
		ck:function(data){
			 console.log(data);
				if(data == "ok"){
					alert('사용가능한 코드입니다.');
					f.aircode.readOnly = "true" 
					this.cock = "true";
				}
				else{
					alert('중복된 코드입니다.');
				}
			}
		}
	});