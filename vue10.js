var html = new Vue ({
	el : "#box",
	data : {
		s: "",
		ea: "",
		total : "",
		view:"0"
	},
	methods:{
		cal:function(){
			this.total= Number(this.s) * Number(this.ea);
			if(this.total != "" || this.total > 0){
				this.view = 1;
			}
		}
	}
})