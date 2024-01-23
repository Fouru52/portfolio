var footer = new Vue ({
	el:"#footer",
	data:{
		d1:"",
		d2:"",
		d3:"",
		d4:"",
		d5:"",
		d6:"",
		d7:"",
		d8:"",
		d9:"",
		d10:"",
	},
	methods:{
		abc:function(z){
			this.d1 = z["1"];
			this.d2 = z["2"];
			this.d3 = z["3"];
			this.d4 = z["4"];
			this.d5 = z["5"];
			this.d6 = z["6"];
			this.d7 = z["7"];
			this.d8 = z["8"];
			this.d9 = z["9"];
			this.d10 = z["10"];
			
		}
	},
	computed:{
		ajax:function(){
			fetch("./homepage.do").then(function(aa){
				return aa.json();
				}).then(function(bb){
					footer.abc(bb);
				}).catch(function(cc){
					console.log(cc);
				})
		}
	}
});