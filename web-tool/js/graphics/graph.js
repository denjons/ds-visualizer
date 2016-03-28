

var Graph = function(args){
	
	this.nodes,
	this.edges,
	this.ids,
	this.edgeIds,
	this.marked,
	this.markedEdges,
	this.traversing,
	this.boundingBox,
	this.boundingBoxes,
	this.environment = args.environment,
	
	this.init = function(args){
		this.traversing = null;
		this.marked = [];
		this.markedEdges = [];
		this.nodes = [];
		this.edges = [];
		this.ids = 0;
		
		/*
		var hex = 0xe37979;
		this.boundingBox = new THREE.Mesh(
			new THREE.CubeGeometry( 1,1,1 ),
			new THREE.MeshLambertMaterial({ color: hex}));
		//this.boundingBox.visible = false;
		scene.add(this.boundingBox);
		boundingVolumes.add(this.boundingBox);
*/
	},
	
	/*
		adds object to graph and appends graph properties to the object
	*/
	
	this.add = function(args){
		var obj = args.object;
		if(obj.graph != null){
			alert("cannnot add object to graph. object already have graph properties.");
		}
		obj.graph = { adjecent: [], id: this.ids} ;
		obj.graph.defaultColor = obj.material.color.getHex();
		
		this.nodes[this.ids] = obj;
		this.environment.scene.add(obj);
		this.ids ++;
	},
	
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
		
		if(!this.nodeExists(this.nodes, obj1)){
			this.add(obj1);
		}
		if(!this.nodeExists(this.nodes, obj2)){
			this.add(obj2);
		}
		
		
		//if(!this.isConnected(obj1.graph.adjecent, obj2)){
			// create line object
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
		edgeLine.edge = {x: obj1, y: obj2, id: this.edgeIds, value: value};
		edgeLine.graph = {defaultColor: 0x000000};
		edgeLine.graph.defaultColor = edgeLine.material.color.getHex();
		this.edgeIds ++;
		
		obj1.graph.adjecent[index] = edgeLine;
		this.edges.push(edgeLine);
		
		this.environment.scene.add(edgeLine);
			//console.log("connected nodes at: "+index);
		//}
		
		return edgeLine;
		
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
		checks if the node is in any edge in the list
	*/
	
	this.isConnected = function(list, obj){
		for(var i = 0; i < list.length; i++){
			console.log(list.length+", "+i+": "+list[i]);
			if(list[i] != null && 
				list[i].edge.y.graph.id == obj.graph.id){
				
				return true;
			}
		}
		return false;
	},
	
	/*
		Checks if the edge is in the list
	*/
	
	this.edgeExists = function(list, obj){
		for(var i = 0; i < list.length; i++){
			if(list[i].edge.id == obj.edge.id){
				return true;
			}
		}
		return false;
	},
	
	/*
		updates the positions of all the lines between modes
	*/
	
	this.update = function(){
		for(var i = 0; i < this.edges; i ++){
			this.edges[0].geometry.vertecies.set(
				this.edges[0].x.position.x + 0.1*Math.random(), 
				this.edges[0].x.position.y + 0.5*Math.random(), 
				this.edges[0].x.position.z + 0.5*Math.random());
			this.edges[1].geometry.vertecies.set(
				this.edges[1].x.position.x + 0.5*Math.random(), 
				this.edges[1].x.position.y + 0.5*Math.random(), 
				this.edges[1].x.position.z + 0.5*Math.random());
		}
	},
	
	/*
		positions all nodes relative to eachother
	*/
	
	this.positionNodes = function(){
		
		var size =[ this.nodes[0].scale.x*3*this.nodes.length,
					this.nodes[0].scale.y*3*this.nodes.length,
					this.nodes[0].scale.z*3*this.nodes.length,
					];
			
/*			
		this.boundingBox.scale.x = size[0];
		this.boundingBox.scale.y = size[1];
		this.boundingBox.scale.z = size[2];
	*/				
		var ind = 0;
		var length = this.nodes.length;
		
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
				this.nodes[ind].position.x	= 0.0 + ((size[0]/2)*Math.sin(Math.PI/(rows_y )*i ))*Math.cos(end+start);
				this.nodes[ind].position.y	= 0.0 + (size[1]/2)*Math.sin(Math.PI/2-Math.PI/(rows_y )*i);
				this.nodes[ind].position.z = 0.0 + ((size[2]/2)*Math.sin(Math.PI/(rows_y )*i))*Math.sin(end+start);
			
				ind ++;
			}
		}
		
	},
	
	this.mark = function(args){
		var obj = args.object;
		for(var i = 0; i < args.objects.length; i ++){
			args.objects[i].material.color.setHex(args.color);
			this.marked.push(args.objects[i]);
		}
		
	},
	
	/*
	
	*/
	
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