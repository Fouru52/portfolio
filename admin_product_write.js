var html = new Vue ({
	el:"#airwrite",
	data:{
		airplane_code: "",
		airname: "",
		aircode: "",
		airflight: "",
		start_point: "",
		end_point: "",
		seat_style: "",
		seat: "",
		method:"POST",
		action:"./product_new.do",
		enctype:"application/x-www-form-urlencoded"
	},
	methods:{
		air_select:function(sel){
			console.log(sel["aircode"]);
		},
		newcode:function(){
				if(this.airplane_code == ""){
					alert("공항코드를 선택하세요.");
					f.airplane_code.focus();
				}
				else if(this.airname == ""){
					alert("항공사명을 선택하세요.");
					f.airname.focus();
				}
				else if(this.aircode == ""){
					alert("항공코드를 선택하세요.");
					f.aircode.focus();
				}
				else if(this.airflight == ""){
					alert("항공편명을 입력하세요.");
					f.airflight.focus();
				}
				else if (this.start_point == ""){
					alert("출발지를 입력하세요.");
					f.start_point.focus();
				}
				else if (this.end_point == ""){
					alert("도착지를 입력하세요.");
					f.end_point.focus();
				}
				else if (this.seat_style == ""){
					alert("좌석형태를 입력하세요.");
					f.seat_style.focus();
				}
				else if (this.seat == ""){
					alert("좌석 수를 입력하세요.");
					f.seat.focus();
				}
				else{
					f.method = this.method;
					f.action = this.action;
					f.enctype = this.enctype;
					f.submit();
				}
			}
		},
		computed:{
			ajax:function(){
				fetch("./aircode_json.do").then(function(aa){
					return aa.json();
				}).then(function(bb){
					html.air_select(bb);
				}).catch(function(cc){
					console.log(cc);
				})
			}
		}
	});