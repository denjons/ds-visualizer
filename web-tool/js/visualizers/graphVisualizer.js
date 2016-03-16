
var GraphVisualizer = function(args){
	
	this.graph,
	this.sequence,
	this.markup = args.markup,
	this.environment = args.environment,
	this.arrayElements2d,
	this.writeColor3d = 0xff6d6d,
	this.writeColor2d = "#FF6D6D",
	this.readColor3d = 0x4ab23a,
	this.readColor2d = "#4ab23a",
	
	/*
			INIT
	*/
	
	this.init = function(){
		
		this.arrayElements2d = [];
		
		var variables = this.markup.header.annotatedVariables;
		var size = [];
		var i = 0;
		// create 2d DOM elements of data structures
		for(var key in variables){
			if (variables.hasOwnProperty(key)) {

				var arrayElement = new ArrayElement({
					lengthX: 	variables[key].attributes.size[0], 
					lengthY: 	variables[key].attributes.size[1],
					id: 		variables[key].identifier });
				
				// sizes of each variable 			
				size[i] = variables[key].attributes.size[0];
				i++;
					
				arrayElement.init();
				
				this.arrayElements2d[arrayElement.id] = arrayElement;
				
				this.environment.DOM["debug_container"].appendChild(arrayElement.DOM);
			}
			
		}
		
		
		
		
		// create graph and insert nodes 
		this.graph = new Graph({environment: this.environment});
		this.graph.init();
		//console.log("scene: "+this.environment.scene);
		//console.log(this.markup.header.variables[0].size[0]);
		
		for(var i = 0; i < size[0]; i ++){
			
			var hex = 0x5fcbff;
			var sphereGeometry = new THREE.SphereGeometry( 1, 20, 20 );
			var material = new THREE.MeshLambertMaterial({ color: hex});
			var sphere = new THREE.Mesh(sphereGeometry, material);
			var pos = 20;
			
			sphere.position.set(pos*Math.random() - pos*Math.random(),
								 pos*Math.random() - pos*Math.random(),
								 pos*Math.random() - pos*Math.random());
			
			var hex = 0x000000;

			/*
			var bbox = new THREE.BoundingBoxHelper( sphere, hex );
			bbox.update();
			scene.add( bbox );
				*/				 
			this.graph.add({object: sphere});
			
		}
		
		this.graph.positionNodes();
		this.sequence = new Sequence({markup: this.markup, visualizer: this});
		
	},
	
	/*
	
		CONTROLLS
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
	
	this.display = function(evt){
		//this.environment.displayData(evt.op+" <br>id: "+evt.id+"; <br>index: "+evt.index+"; <br>value: "+evt.value+"; ");
		if(evt.operation == "read"){
			this.read(evt.operationBody);
		}else if(evt.operation == "write"){
			this.write(evt.operationBody);
		}
	},
	
	
	
	/*
		OPERATIONS
	*/
	
	
	/*
		ADJECENCY MATRIX
	*/
	
	
	this.matrix = {};
	
	this.read = function(evt){
		
		var id = evt.source.id;
		var index = evt.source.index;
		var value = evt.value;
		
		this.arrayElements2d[id].clearMarked();
		this.graph.clearMarked();
		
		if(index.length == 1){
			// 1 index (node), traverse single node
			
			this.graph.traverse({object: this.graph.nodes[index[0]], color: this.readColor3d});
			this.arrayElements2d[id].markIndex({x: index[0], color: this.readColor2d});
			
		}else if(index.length == 2){
			// 2 indexes (nodes), traverse edge
			// connect the objects if not connected
			if(this.graph.nodes[index[0]].graph.adjecent[index[1]] == null || value[0] == 0){
				//this.connectNodes(evt);
				this.graph.mark({objects: [this.graph.nodes[index[0]], this.graph.nodes[index[1]]], color: this.readColor3d});
				this.arrayElements2d[id].markIndex({x: index[0], y: index[1], color: this.readColor2d});
				this.arrayElements2d[id].markCell({x:index[0], y: index[1], color: this.readColor2d});
			}else{
			
			// fetch edge
				var edgeObj = this.graph.nodes[index[0]].graph.adjecent[index[1]];
				var node2 = edgeObj.edge.value;
				this.graph.colorObject({object: edgeObj, color: 0x000000});
				this.graph.mark({objects: [edgeObj, this.graph.nodes[index[0]], this.graph.nodes[index[1]]], color: this.readColor3d});
				this.arrayElements2d[id].markIndex({x: index[0], y: index[1], color: this.readColor2d});
				this.arrayElements2d[id].markCell({x:index[0], y: index[1], color: this.readColor2d});
			}
			
		}
		this.environment.display();
	},
	
	/*
		write operations are passed to this function
	*/
	
	this.write = function(evt){
		
		var id = evt.target.id;
		var index = evt.target.index;
		var value = evt.value;
		
		this.arrayElements2d[id].clearMarked();
		this.graph.clearMarked();
		
		//console.log("writing: obj1"+evt.index[0]+"obj2: "+evt.value[0]+", index: "+evt.index[1]);
		
		if(index.length == 2){
			// connect two nodes
			
			this.connectNodes(evt);
		}
		
		this.environment.display();
	},
	
	this.connectNodes = function(evt){
		
		var id = evt.target.id;
		var index = evt.target.index;
		var value = evt.value;
		
		console.log("connect: "+index[0] +", "+ index[1]+", length:"+index.length);
		var edge = this.graph.connectNodes({id1: index[0] ,  id2: index[1], index: index[1], value: value[0]});
		
		this.arrayElements2d[id].markCell({x: index[0], y: index[1], color: this.writeColor2d});
		this.arrayElements2d[id].markIndex({x:index[0], y: index[1], color: this.writeColor2d});
		this.arrayElements2d[id].insertValue({x: index[0], y: index[1], value: value[0]});
		this.graph.mark({objects: [edge, this.graph.nodes[index[0]], this.graph.nodes[index[1]]], color: this.writeColor3d});
		
	},
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	/* ------------------------------------------------------------------------------------------------------------------------------------------------- */
	/*
		ADJECENCY LIST
	*/
	this.list = {};
	/*
		read operations are passed to this function
	*/
	
	this.list.read = function(evt){
		
		this.arrayElements2d[evt.id].clearMarked();
		this.graph.clearMarked();
		
		if(evt.index.length == 1){
			// 1 index (node), traverse single node
			
			this.graph.traverse({object: this.graph.nodes[evt.index[0]], color: this.readColor3d});
			this.arrayElements2d[evt.id].markIndex({x: evt.index[0], color: this.readColor2d});
			
		}else if(evt.index.length == 2){
			// 2 indexes (nodes), traverse edge
			
			// connect the objects if not connected
			if(this.graph.nodes[evt.index[0]].graph.adjecent.length <=  evt.index[1] || 
				this.graph.nodes[evt.index[0]].graph.adjecent[evt.index[1]] == null){
				
				this.connectNodes(evt);
				
			}
			
			// fetch edge
			var edgeObj = this.graph.nodes[evt.index[0]].graph.adjecent[evt.index[1]];
			console.log("reading edge from: "+evt.index[1]);
			var node2 = edgeObj.edge.value;
			
			//this.graph.markEdge({edge:edgeObj, color: 0x00000});
			//this.graph.traverse({object: this.graph.nodes[evt.index[0]], color: this.readColor3d});
			this.graph.colorObject({object: edgeObj, color: 0x000000});
			this.graph.mark({objects: [edgeObj, this.graph.nodes[evt.index[0]], this.graph.nodes[evt.value[0]]], color: this.readColor3d});
			
			this.arrayElements2d[evt.id].markIndex({x: evt.index[0], y: evt.index[1], color: this.readColor2d});
			this.arrayElements2d[evt.id].markCell({x:evt.index[0], y: evt.index[1], color: this.readColor2d});
			
		}
		this.environment.display();
	},
	
	/*
		write operations are passed to this function
	*/
	
	this.list.write = function(evt){
		
		this.arrayElements2d[evt.id].clearMarked();
		this.graph.clearMarked();
		
		//console.log("writing: obj1"+evt.index[0]+"obj2: "+evt.value[0]+", index: "+evt.index[1]);
		
		if(evt.index.length == 2){
			// connect two nodes
			
			this.connectNodes(evt);
		}
		
		this.environment.display();
	},
	
	this.list.connectNodes = function(evt){
		
		var edge = this.graph.connectNodes({id1:evt.index[0] ,  id2:evt.value[0], index: evt.index[1] });
		this.arrayElements2d[evt.id].markCell({x: evt.index[0], y:evt.index[1], color: this.writeColor2d});
		this.arrayElements2d[evt.id].markIndex({x:evt.index[0], y: evt.index[1], color: this.writeColor2d});
		this.arrayElements2d[evt.id].insertValue({x: evt.index[0], y: evt.index[1], value: evt.value[0]});
		
		this.graph.mark({objects: [edge, this.graph.nodes[evt.index[0]], this.graph.nodes[evt.value[0]]], color: this.writeColor3d});
	}
	
	
}