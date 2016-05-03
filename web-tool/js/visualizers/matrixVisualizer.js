var MatrixVisualizer = function(args){
	
	this.dataStructure = args.dataStructure,
	this.environment2d = args.environment,
	this.matrix,
	
	this.init = function(args){
	
		this.environment2d.init(args);
		this.environment2d.setHeader({ds:this.dataStructure});

		this.matrix = new Matrix({
			width: this.environment2d.ENV_WIDTH,
			height: this.environment2d.getCanvasHeight()
		});

		this.sequence.addVisualizer(this);
	
	},
	
	this.display = function(evt){
		//this.environment3d.displayData(evt.op+" <br>id: "+evt.id+"; <br>index: "+evt.index+"; <br>value: "+evt.value+"; ");
		
		if(evt.operation == "read" ){
				
			this.read(evt.operationBody, true);

		}else if(evt.operation == "write"){
			
			this.write(evt.operationBody, true);
			
		}
		
		this.matrix.update({
				context: this.environment2d.context
			});
		
		
	},
	
	this.ownData = function(entity){
		return this.checkId(entity, this.dataStructure);
	},
	
	this.writeAll2D = function(args){
		for(var i = 0; i < args.value.length; i ++){
			this.writeAll({
				index: i,
				value: args.value[i],
				color: args.color
			});
		}
	},
	
	this.writeAll = function(args){
		for(var i = 0; i < args.value.length; i ++){
			this.writeCell({
				value: args.value[i],
				color: args.color,
				x: args.index,
				y: i
			});
			
		}
	},
	
	this.writeCell = function(args){
		this.drawOperation({
				value: args.value , 
				color: args.color,
				x: args.x,
				y: args.y,
			});
	},
	
	this.drawOperation = function(args){
		//console.log("write "+value);
		this.matrix.draw({
			x: args.x,
			y: args.y,
			color: args.color,
			value: args.value,
			context: this.environment2d.context
		});

	},
	
	
	this.write = function(evt, first){
		
		var index = evt.target.index;
		var value = evt.value;
		var color = "#ff7d7d";
		
		if(first && !this.ownData(evt.target)){
			this.read(evt, false);
			return;
		}
		
		// init nodes
		if(value instanceof Array ){
			if(index == null || index.length <= 0){
				this.writeAll2D({
					value: value,
					color: color
				});
			}
		}
		// init edges
		else if(index != null && index.length == 1){
			this.writeAll({
				index: index[0],
				value: value,
				color: color
			});
		}
		else if( index != null && index.length == 2 ){
			this.writeCell({ 
				value: 	value, 
				color: 	color,
				x: 		index[0], 
				y: 		index[1]
			});
				
		}
		
		if(first){
			this.read(evt, false);
		}
		
	},
	
	this.read = function(evt, first){
		
		var color = "#4cec8f";
		var value = evt.value;
		var index = evt.source.index;
		
		if(first && !this.ownData(evt.source)){
			this.write(evt, false);
			return;
		}
		
		if(index != null){
			
			if(index.length == 2){	
				this.writeCell({ 
					value: value, 
					color: color,
					x: index[0], 
					y: index[1]
				});
			}
			else if(index.length == 1){
				this.writeAll({
					index: index[0],
					value: value,
					color: color
				});
			}
			else if(index.length == 0){
				this.writeAll2D({
					value: value,
					color: color
				});
			}
			
		}else if(value instanceof Array){
			this.writeAll2D({
					value: value,
					color: color
				});
		}
		
		if(first){
			this.write(evt, false);
		}
		
	}
}

MatrixVisualizer.prototype = Visualizer;