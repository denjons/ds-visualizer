
var Staples = function(args){
	
	this.defaultColor = "#4ed0ec";
	this.width = args.width,
	this.height = args.height,
	this.staples = [],
	this.stapleList = [],
	this.maxHeight = 0,

	
	this.addStaple = function(args){
		
		
		this.staples[args.index] = {
				value: args.value, 
				height: this.evaluate(args.value),
				color: this.defaultColor,
				position: args.index
			};
		this.stapleList.push(this.staples[args.index]);
		
	},
	
	this.evaluate = function(value){
		if(isNaN(value)){
			this.maxHeight = Math.max(1, this.maxHeight);
			return 1;
		}
		this.maxHeight = Math.max(value, this.maxHeight);
		return value;
	},
	
	this.draw = function(args){
		if(this.staples[args.index] == null){
			this.addStaple(args);
		}
		this.staples[args.index].color = args.color;
		this.staples[args.index].value = args.value;
		this.staples[args.index].height = this.evaluate(args.value);
		this.staples[args.index].position = args.index;
		

	},
	
	this.update = function(args){
		
		args.context.clearRect(0, 0, this.width, this.height);
		
		var width = this.width/this.stapleList.length;
		var height = 0;
		var context = args.context;
		var posX;
		var posY;
		var textPosY;
		
		for(var i = 0; i < this.stapleList.length; i++){
			
			height = (this.height - 30)/this.maxHeight *this.stapleList[i].height;
			posX = this.width/this.stapleList.length*this.stapleList[i].position;
			posY = this.height - height;
			context.beginPath();
			context.rect(posX, posY, width, height);
			context.fillStyle = this.stapleList[i].color;
			context.fill();
			context.lineWidth = 1;
			context.strokeStyle = '#000000';
			context.stroke();
			
			var string = this.stapleList[i].value+"";
			var strLen = string.length;
			var fontSize = Math.min(Math.ceil(width/(strLen)),30);
			textPosY = posY;
			
			/*
			if(this.stapleList[i].height >= this.maxHeight/2){
				textPosY = textPosY + (fontSize + 2);
			}
			if(this.stapleList[i].height == this.maxHeight){
				textPosY = fontSize + 2;
			}*/
			
			context.beginPath();
			context.strokeStyle = "#000000";
			context.fillStyle = "#000000";
			context.font = fontSize+"px Arial";
			context.fillText(
				string,
				posX+1,
				textPosY
			);
			context.stroke();
			this.stapleList[i].color = this.defaultColor;
		}
	},
	
	this.clear = function(){
		this.staples = [];
		this.stapleList = [];
		this.maxSize = 0;
	}
	
	
	
}