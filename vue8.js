// 자바스크립트 파일에는 백엔드 코드 적용 불가능

var code = new Vue({
	el:"#box",
	data:{
		user: a,	// js에서 넘어오는 back-end 변수값을 vue 가상변수에 적용
		money: point,
		texts: "[45인치]UHD 모니터",
		htmls:"<ul><li>공지사항1</li><li>공지사항2</li></ul>",	
		lists:""
 	},
	methods:{
		ok:function(){
			var w= 0;
			while(w<5){
				this.lists += "<li>게시물"+w+"</li>";
				w++;
			}
		}
	}
});