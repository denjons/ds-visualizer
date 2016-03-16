
var gridVisualizer = function(args){
	
	this.points,
	this.markup = args.markup,
	this.sequence,
	this.matrix,
	this.grid,
	this.size,
	
	this.init = function(args){
		
		
		this.size = args.size;
		
		var name = null;
		for(var i = 0; i < this.markup.header["variables"].length; i++){
			if(this.markup.header["variables"][i]["type"] == "matrix"){
				name = this.markup.header["variables"][i]["name"];
			}
			
		}
		
		if(name == null){
			alert("gridVisualizer: no matrix present.");
			return 0;
		}
		
		this.matrix = this.markup.init[name];
		
		var lengthZ = 0;
		var lengthY = 0;
		var lengthX = this.matrix.length;
		
		// dimensions
		for(var i = 0; i < this.matrix.length; i++){
				lengthZ = Math.max(this.matrix[i].length, lengthZ);
			for(var j = 0; j < this.matrix[i].length; j++){
				lengthY = Math.max(this.matrix[i][j],lengthY );
			}
		}

		// grid
		this.grid = new Grid();
		var mat = this.matrix;
		this.grid.init({x: lengthX ,y: this.size, z: lengthZ, func: function(x, z){return mat[x/3|0][z/3|0];}, 
		color:0xefefef, scale:[this.size,this.size,this.size], position:[0,-1,0]});

	}
	
	
}