

var Sequence = function(){
	
	this.iteration = 0,
	//this.visualizer = args.visualizer,
	//this.markup = args.markup,
	this.markup,
	this.time,
	this.running = false,
	this.MILI_SECONDS = 500,
	this.visualizers = [],
	this.programs = [],
	this.setMarkup = function(markup){
		this.markup = markup;
	}
	
	this.addVisualizer = function(visualizer){
		this.visualizers.push(visualizer);
	},
	
	this.addProgram = function(args){
		this.programs.push(args.program);
	},
	
	this.display = function(operation){
		for(var i =0; i < this.programs.length; i++){
			this.programs[i].onDisplay(operation);
		}
		for(var i = 0; i < this.visualizers.length; i++ ){
			this.visualizers[i].display(operation);
		}
	},
	
	this.append = function(event){
		console.log(event.op+", "+event.id+", "+event.x[0]);
		this.markup.body.push(event);
	},
	

	this.stop = function(){
		this.running = false;
		this.iteration = 0;
	},
	
	this.pause = function(){
		this.running = false;
	},
	
	this.play = function(){
		if(this.running == false){
			this.running = true;
			this.iterate();
		}
	},
	
	this.fastForward = function(times){
		this.MILI_SECONDS = this.MILI_SECONDS/times;
	},
	
	this.slowDown = function(times){
		this.MILI_SECONDS = this.MILI_SECONDS*times;
	},
	
	this.step = function(){
		if(this.running){
			this.running = false;
		}else{
			this.running = true;
			this.iterate();
			this.running = false;
		}
		
		
	},
	
	this.iterate = function(){
		if(this.running){
			//this.visualizer.display(this.markup.body[this.iteration]);
			this.display(this.markup.body[this.iteration]);
			this.iteration ++;
			var _this = this;
			if(this.iteration < this.markup.body.length && this.running){
				this.time = setTimeout(function(){
					_this.iterate();
				}, this.MILI_SECONDS);
			}else{
				alert("End of visualization");
			}
		}
	}
}

//var sequence = new Sequence();

var Visualizer = {
	sequence: new Sequence(),
	checkId : function(ds1, ds2){
		
		return ((ds1 != null && ds2 != null) && (ds1.identifier == ds2.identifier));
	},
	
	play : function(){
		console.log("play");
		this.sequence.play(this);
	},
	
	stop : function(){
		this.sequence.stop(this);
	},
	
	pause : function(){
		this.sequence.pause(this);
	}
	
}



