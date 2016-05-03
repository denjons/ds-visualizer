

var Graph = function(args){
	
	this.nodes,
	this.edges,
	this.ids,
	this.edgeIds,
	this.marked,
	this.traversing,
	this.environment = args.environment,
	this.nodeList = [];
	
	this.init = function(args){
		this.traversing = null;
		this.marked = [];
		this.nodes = [];
		this.edges = [];
		this.ids = 0;
		
	},
	
	/*
		adds object to graph and appends graph properties to the object
	*/
	
	this.add = function(args){
		var obj = args.object;
		var index = args.index;
		if(obj.graph != null){
			alert("cannnot add object to graph. object already have graph properties.");
		}
		obj.graph = { adjecent: [], id: index} ;
		obj.graph.defaultColor = obj.material.color.getHex();
		
		this.nodes[index] = obj;
		this.nodeList.push(obj);
		this.environment.scene.add(obj);
		this.ids ++;
	},
	
	this.remove = function(obj){
		if(obj != null){
			this.environment.scene.add(obj);
		}
		
	},
	
	this.clearScene = function(){
		for(var i=0; i < this.nodes.length; i++){
			this.remove(nodes[i]);
		}
		
		for(var i=0; i < this.edges.length; i++){
			this.remove(this.edges[i]);
		}
		
		this.nodes = [];
		this.edges = [];
		this.marked = [];
	}
	
	/*
		connects obj2 to obj1, ie: obj1 --> obj2
		pushes obj2 on obj1's adjecency list
	*/
	
	this.connect = function(args){
		
		var obj1 = args.obj1;
		var obj2 = args.obj2;
		var index = args.index;
		var value = args.value;
		var color = args.color;
		
		if(color == null){
			color = 0xd0d0d0;
		}
		
		var edgeLine;
		
		
		
		var lineMaterial = new THREE.LineBasicMaterial({ color: color });
		var lineGeometry = new THREE.Geometry();
		lineGeometry.vertices.push(
			new THREE.Vector3( 
				obj1.position.x + 0.5*Math.random(), 
				obj1.position.y + 0.5*Math.random(), 
				obj1.position.z + 0.5*Math.random()), // from node
			new THREE.Vector3( 
				obj2.position.x + 0.5*Math.random(), 
				obj2.position.y + 0.5*Math.random(), 
				obj2.position.z + 0.5*Math.random())  //  to node
		);
		
		edgeLine = new THREE.Line( lineGeometry, lineMaterial );
		edgeLine.edge = {x: obj1, y: obj2, id: this.edgeIds, value: value, adjacentIndex: index};
		edgeLine.graph = {defaultColor: 0x000000};
		edgeLine.graph.defaultColor = edgeLine.material.color.getHex();
		this.edgeIds ++;
		
		obj1.graph.adjecent[index] = edgeLine;
		this.edges.push(edgeLine);
		
		this.environment.scene.add(edgeLine);

		return edgeLine;
		
	},
	
	this.positionEdges = function(){
		var oldEdges = this.edges;
		this.edges = [];
		for(var i = 1; i < oldEdges.length; i++){
			var e = oldEdges[i];
			this.connect({
				obj1: e.edge.x, 
				obj2: e.edge.y, 
				index: e.edge.adjacentIndex, 
				value: e.edge.value, 
				color: e.graph.defaultColor
			});
			this.environment.scene.remove(e);
		}
	},
	
	/*
		
	*/
	
	this.connectNodes = function(args){
		
		console.log(args.id1+", "+args.id2);
		var node1 = this.nodes[args.id1];
		var node2 = this.nodes[args.id2];
		var edge = this.connect({obj1: node1,obj2: node2, index: args.index, value: args.value, color: args.color });
		
		return edge;
	},
	
	/*
		checks if a node with same id exists in list
	*/
	
	this.nodeExists = function(list, obj){
		for(var i = 0; i < list.length; i++){
			if(list[i].graph.id == obj.graph.id){
				return true;
			}
		}
		return false;
	},
	

	/*
		positions all nodes relative to eachother
	*/
	
	this.positionNodes = function(){
		
		var size =[ this.nodeList[0].scale.x*3*this.nodes.length,
					this.nodeList[0].scale.y*3*this.nodes.length,
					this.nodeList[0].scale.z*3*this.nodes.length,
					];
							
		var ind = 0;
		var length = this.nodeList.length;
		
		var rows_x = Math.floor(Math.sqrt(length)); 
		var rows_y = Math.floor(length/rows_x) + 1;
		var rest = length%rows_x;
		
		var step = []; 
		step[1] = rows_x;
		
		for(var i = 1; i < rows_y; i++){
			if(i < Math.floor(rows_y/2)){
				// next step
				step[rows_y - 1 - i] = rows_x;
				step[i+1] = rows_x + Math.floor(step[i]/Math.PI);
				step[rows_y - 1 - i] = step[rows_y - 1 - i] + Math.floor(step[i]/Math.PI);
				// this step
				step[i] = step[i] - Math.floor(step[i]/Math.PI);
				step[rows_y - i] = step[i];
			}
			
			if(i == Math.floor(rows_y/2)){
				step[i] = step[i] + rest;
			}
			
			var start = Math.PI*Math.random();//randomize the starting point on the xz circle
			for(var j = 0; j <=	step[i] - 1; j++){
				var end = (2*Math.PI)/step[i]*(j-1);
				var pos = {
				x: 0.0 + ((size[0]/2)*Math.sin(Math.PI/(rows_y )*i ))*Math.cos(end+start),
				y: 0.0 + (size[1]/2)*Math.sin(Math.PI/2-Math.PI/(rows_y )*i),
				z: 0.0 + ((size[2]/2)*Math.sin(Math.PI/(rows_y )*i))*Math.sin(end+start),
				};
				this.nodeList[ind].position.x = pos.x;
				this.nodeList[ind].position.y = pos.y;
				this.nodeList[ind].position.z = pos.z;
			
				ind ++;
			}
		}
		this.positionEdges();
		
	},
	
	
	
	this.mark = function(args){
		var obj = args.object;
		for(var i = 0; i < args.objects.length; i ++){
			args.objects[i].material.color.setHex(args.color);
			this.marked.push(args.objects[i]);
		}
		
	},
	
	
	this.clearMarked = function(args){
		while(this.marked.length > 0){
			var obj = this.marked.pop();
			obj.material.color.setHex(obj.graph.defaultColor);
		}
	},
	
	
	this.traverse = function(args){
		if(this.traversing != null){
			this.traversing.material.color.setHex(this.traversing.graph.defaultColor);
		}
		this.traversing = args.object;
		this.traversing.material.color.setHex(args.color);
	},
	
	this.colorObject = function(args){
		args.object.graph.defaultColor = args.color;
		args.object.material.color.setHex(args.color);
	}
	
} 