
var Tree2d = function(args){
	this.defaultColor = "#4ed0ec";
	this.arity = args.arity,
	this.treeHeight = args.treeHeight,
	this.width = args.width,
	this.height = args.height,
	this.tree = [],
	this.nodeList = [],
	this.nodeSize,
	this.maxSize = Math.pow(this.arity, this.treeHeight + 1) - 1,
	

	this.addNode = function(i){
		i = i+1;
		var h = Math.ceil(Math.log2(i + 1) - 1);
		
		if(h + 1 > this.treeHeight){
			this.treeHeight = h + 1;
		}
		
		var div = Math.pow(this.arity, h) + 1;
		var p = i - Math.pow(this.arity, h) + 1;

		this.tree[i-1] = new TreeNode({
			position: i-1,
			h: h,
			p: p,
			div: div,
			color: '#003300',
			marked: false
			
		});
		this.nodeList.push(this.tree[i-1]);
		return this.tree[i-1];//return " "+i+"("+h+", "+div+", "+p+")";
	},
	
	/*
	this.init = function(args){
		var n = args.nodes;
		this.treeHeight = Math.ceil(Math.log2(n + 1) - 1);
	},*/

	this.test = function(){
		var str = "";
		for(var i =1; i < 20;i++){
			str += this.addNode(i);
		}
		alert(str);
	},

	this.draw = function(args){

		if(this.tree[args.index] == null){
			this.addNode(args.index);
		}

		this.tree[args.index].color = args.color;
		
		if(args.value != null){
			this.tree[args.index].value = args.value;
		}
		
		
	},
	
	this.update = function(args){
		
		args.context.clearRect(0, 0, this.width, this.height);
		
		for(var i = 0; i < this.nodeList.length; i++){
			
			var size = Math.min(
				this.width/Math.pow(this.arity, this.treeHeight), 
				this.height/(this.treeHeight+1)
			);
			
			this.nodeList[i].draw({
				size: size,
				width: this.width,
				height: this.height,
				treeHeight: this.treeHeight,
				context: args.context,
				color: args.color
			});
			
			this.nodeList[i].color = this.defaultColor;
		}
	},
	
	
	
	this.clear = function(){
		this.tree = [];
		this.nodeList = [];
		this.maxSize = Math.pow(this.arity, this.treeHeight + 1) - 1;
	}
	
}



var TreeNode = function(args){
	
	this.position = args.position,
	this.h = args.h,
	this.p = args.p,
	this.div = args.div,
	this.marked = args.marked,
	this.color,
	this.value,

	this.draw = function(args){
		
		var centerX = (args.width/this.div)*this.p;
		var centerY = args.height/args.treeHeight*(this.h)+(args.height/args.treeHeight)/2;
		var radius = args.size/2;
		var context = args.context;
		//this.color = args.color;
		//alert(centerX +" "+centerY+" "+radius);
		context.beginPath();
		context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
		context.fillStyle = this.color;
		context.fill();
		context.lineWidth = 1;
		context.strokeStyle = "#000000";
		context.stroke();
		
		var string = this.value+"";
		var strLen = string.length;
		var fontSize = Math.ceil(args.size/(strLen+1));
		
		context.beginPath();
		context.strokeStyle = "#000000";
		context.fillStyle = "#000000";
		context.font = fontSize+"px Arial";
		context.fillText(
			string,
			centerX-(strLen*(fontSize+1))/2,
			centerY+fontSize/2
		);
		context.stroke();
		
	}
	

} 
