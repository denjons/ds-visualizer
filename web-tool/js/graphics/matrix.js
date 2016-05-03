
var Matrix = function(args){
	
	this.defaultColor = "#4ed0ec";
	this.width = args.width,
	this.height = args.height,
	this.matrix = [],
	this.matrixList = [],
	this.maxX = 0,
	this.maxY = 0,

	
	this.addCell = function(args){
		
		if(this.matrix[args.x] == null){
			this.matrix[args.x] = [];
		}
		
		this.matrix[args.x][args.y] = {
				value: args.value, 
				color: this.defaultColor,
				x: args.x,
				y: args.y
			};
			
		this.maxX = Math.max(this.maxX, args.x);
		this.maxY = Math.max(this.maxY, args.y);
		this.matrixList.push(this.matrix[args.x][args.y]);
		
	},
	
	
	this.draw = function(args){
		
		if(this.matrix[args.x] == null || this.matrix[args.x][args.y] == null){
			this.addCell(args);
		}
		
		this.matrix[args.x][args.y].color = args.color;
		this.matrix[args.x][args.y].value = args.value;
		this.matrix[args.x][args.y].x = args.x;
		this.matrix[args.x][args.y].y = args.y;

	},
	
	this.update = function(args){
		
		args.context.clearRect(0, 0, this.width, this.height);
		var context = args.context;
		var width = this.width/(this.maxX + 1);
		var height = this.height/(this.maxY);
		
		for(var i = 0; i < this.matrixList.length; i++){
			
			posX = this.matrixList[i].x*width;
			posY = (this.matrixList[i].y )*height;
			context.beginPath();
			context.rect(posX, posY, width, height);
			context.fillStyle = this.matrixList[i].color;
			context.fill();
			context.lineWidth = 1;
			context.strokeStyle = '#000000';
			context.stroke();
			
			var string = this.valueToString(this.matrixList[i].value);
			
			var strLen = string.length;
			var fontSize = Math.min(Math.ceil(width/(strLen)),30);
			var textPosY = posY +height/2 + fontSize/2;
			var textPosX = posX + width/2 - fontSize/2;
			
			context.beginPath();
			context.strokeStyle = "#000000";
			context.fillStyle = "#000000";
			context.font = fontSize+"px Arial";
			context.fillText(
				string,
				textPosX,
				textPosY
			);
			context.stroke();
			this.matrixList[i].color = this.defaultColor;
		}
	},
	
	this.clear = function(){
		this.maxX = 0;
		this.maxY = 0;
		this.matrix = [];
		this.matrixList = [];
	},
	
	this.valueToString = function(value){
		var string = value+"";
		if(string.length > 4){
			return string.substring(0,4);
		}
		return string;
	}

}