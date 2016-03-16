



var BubbleSortVisualizer = function(args){
	
	this.grid,
	this.array,
	this.variables,
	this.maxValue,
	this.ratio,
	this.sequence,
	this.markup = args.markup
	
	/*
		initalize
	*/
	
	this.init = function(){
		
		var array = this.markup["init"]["values"];
		this.ratio = 1;
		this.maxValue = 0;
		this.variables = [];
		this.array = [];
		
		var maxArray = this.max(array);
		var length = array.length;
		var gridYScale = 12; 
		this.ratio = gridYScale/maxArray;
		
		this.grid = new Grid();
		this.grid.init({
			x: length,
			y: length,
			z: length,
			func: function(x, z){return 0;},
			color: 0xe3e3e3,
			scale: [gridYScale,gridYScale,gridYScale],
			position: [0,0,0]
		});
		
		
		for(var i = 0; i < length; i++){
			
			this.array[i] = array[i];
			
			var hex = 0xe37979;
			var cube = new THREE.Mesh(
			new THREE.CubeGeometry( 1,1,1 ),
			new THREE.MeshLambertMaterial({ color: hex}));
			cube.scale.set(1.0,this.ratio*array[i],1.0);
			this.grid.add(
			{ object: cube, x: i, y: 0, z: 0 }
			);
			scene.add(cube);
			
		}
		
	},
	
	/*
		
	*/
	
	this.play = function(){
		this.sequence.play(this);
	},
	
	this.stop = function(){
		this.sequence.stop(this);
	},
	
	this.pause = function(){
		this.sequence.pause(this);
	},
	
	/*
		interface
	*/
	
	this.display = function(evt){
		this.grid.clearMarked({color:0x67d48f});
		if(evt.op == 1){
			this.read(evt);
		}else if(evt.op == 2){
			this.write(evt);
		}
		display();
	},
	
	/*
		interprets read operations
	*/
	
	this.read = function(evt){
		var obj = this.grid.get({x:evt.x[0], y:0, z:0});
		obj.scale.z += (this.grid.mesh.scale.z/this.grid.zLength)/4;
		this.grid.place({
				object: obj,
				x: obj.grid.x,
				y:0,
				z:0	});
		/*
			read into variable
		*/
		if(evt.set){
			this.variables[evt.target] = obj;
			this.grid.markObj({object:obj, color: 0x67d48f });
		}else{
		/*
			read into unknown destination
		*/
			this.grid.markObj({object:obj, color: 0x67d48f});
		}
	},
	
	/*
		interprets write operations
	*/
	this.write = function(evt){
		if(evt.set){
			/*
				write operation from variable
			*/
			var obj = this.variables[evt.target];
			obj.scale.z += (this.grid.mesh.scale.z/this.grid.zLength)/4;
			this.grid.place({
				object: obj,
				x: evt.index[0],
				y:0,
				z:0	
				});	
			this.grid.markObj({object:obj, color: 0x67d48f});
		}else{
			/*
				write operation from unknown source
			*/
			var hex = 0xe37979;
			var cube = new THREE.Mesh(
			new THREE.CubeGeometry( 1,1,1 ),
			new THREE.MeshLambertMaterial({ color: hex}));
			var value = evt.value[0];
			cube.scale.set(1.0,value*this.ratio,1.0)
			this.grid.add({object: cube, 
				x:evt.index[0], 
				y:0, 
				z:0});
			this.grid.markObj({object:obj, color: 0x67d48f});
		}
	},
	
	/*
		sorting
	*/
	
	this.sort = function(){
		var array = this.array.slice();
		this.sequence = new Sequence({markup: this.markup, visualizer: this}); 
		//this.sequence.setVisualizer(this);
		this.bubbleSort(array, this.sequence);
		//this.markup.body = this.sequence.events;
	},
	
	/*
		performs bubble sort on a copy of the aggregated array
	*/
	
	this.bubbleSort = function(array, sequence){
		
		var temp;
		for ( var i = 0; i < array.length; i++){
			for(j = 0; j < array.length - i - 1; j++){
				temp = array[j];
				sequence.append({op:1,id:0,index:[j],value:[array[j]], set:true, target:"x"});
				sequence.append({op:1,id:0,index:[j + 1],value:[array[j + 1]]});
				if(temp > array[j + 1]){
					
					array[j] = array[j + 1];
					// int tmp = array[j + 1]
					// array[j] = tmp
					sequence.append({op:1,id:0,index:[j+1],value:[array[j+1]], set:true, target:"y"});
					sequence.append({op:2,id:0,index:[j],value:[array[j+1]], set:true, target:"y"});
					array[j + 1] = temp; 
					sequence.append({op:2,id:0,index:[j+1],value:[temp], set:true, target:"x"});
					
				}
			}
			
		}
	},
	
	/*
	
	*/
	
	this.max = function(array){
		var max = 0;
		for(var i = 0; i < array.length; i++){
			max = Math.abs(Math.max(max, array[i]));
		}
		return max;
	}
}