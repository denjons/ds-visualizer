

var Sequence = function(args){
	
	this.iteration = 0,
	this.visualizer = args.visualizer,
	this.markup = args.markup,
	this.time,
	this.running = false,
	this.MILI_SECONDS = 500,
	
	this.append = function(event){
		console.log(event.op+", "+event.id+", "+event.x[0]);
		this.markup.body.push(event);
	},
	
	/*
	this.print = function(){
		for(var i = 0; i < this.markup.body.length; i++){
			console.log(this.markup.body[i].op+", "+
			this.markup.body[i].id+", "+this.markup.body[i].x[0]);
		}
	},*/
	
	
	this.slowDown =  function(times){
		this.MILI_SECONDS = MILI_SECONDS/times;
	},
	
	this.speedUp = function(times){
		
		MILI_SECONDS = MILI_SECONDS*times;
	},
	
	this.stop = function(){
		this.running = false;
		this.iteration = 0;
	},
	
	this.pause = function(){
		this.running = false;
	},
	
	this.play = function(){
		this.running = true;
		this.iterate();
	},
	
	this.iterate = function(){
		if(this.running){
			this.visualizer.display(this.markup.body[this.iteration]);
			this.iteration ++;
			var _this = this;
			if(this.iteration < this.markup.body.length && this.running){
				this.time = setTimeout(function(){
					_this.iterate();
				}, this.MILI_SECONDS);
			}
		}
	}
}
