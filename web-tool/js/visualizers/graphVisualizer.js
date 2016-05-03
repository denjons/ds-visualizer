
var GraphVisualizer = function(args){
	
	this.graph,
	this.dataStructure = args.dataStructure,
	this.environment3d = args.environment,
	this.arrayElement2d,
	this.writeColor3d = 0xff6d6d,
	this.writeColor2d = "#FF6D6D",
	this.readColor3d = 0x4ab23a,
	this.readColor2d = "#4ab23a",
	
	/*
			INIT
	*/
	
	this.init = function(args){
		
		this.environment3d.init(args);
		

		// create 2d DOM elements of data structures
		this.arrayElement2d = new ArrayElement({
			lengthX: 	this.dataStructure.attributes.size[0], 
			lengthY: 	this.dataStructure.attributes.size[1],
			id: 		this.dataStructure.identifier });
			
		this.arrayElement2d.init();
		
		// sizes of each variable 			
		var size = this.dataStructure.attributes.size[0];
		
		this.environment3d.debugContainer.appendChild(this.arrayElement2d.DOM);
		
		// create graph and insert nodes 
		this.graph = new Graph({environment: this.environment3d});
		this.graph.init();
		
		this.sequence.addVisualizer(this);
		
	},
	
	this.addNode = function(index){
		var hex = 0x5fcbff;
		var sphereGeometry = new THREE.SphereGeometry( 1, 20, 20 );
		var material = new THREE.MeshLambertMaterial({ color: hex});
		var sphere = new THREE.Mesh(sphereGeometry, material);
		var pos = 20;
		
		sphere.position.set(pos*Math.random() - pos*Math.random(),
							 pos*Math.random() - pos*Math.random(),
							 pos*Math.random() - pos*Math.random());
		
		var hex = 0x000000;
		
		this.graph.add({object: sphere, index: index });
		
		this.graph.positionNodes();
	},
	
	this.addAll = function(array){
		for(var i =0; i < array.length; i++){
			this.addNode(i);
		}
		this.graph.positionNodes();
	},
	
	this.ownData = function(entity){
		return this.checkId(entity, this.dataStructure);
	},
	
	this.clearMarked = function(){
		this.arrayElement2d.clearMarked();
		this.graph.clearMarked();
	},
	
	
	
	/*
	
		CONTROLLS
	*/

	this.display = function(evt){
		//this.environment3d.displayData(evt.op+" <br>id: "+evt.id+"; <br>index: "+evt.index+"; <br>value: "+evt.value+"; ");
		if(evt.operation == "read"){
			this.read(evt.operationBody);
		}else if(evt.operation == "write"){
			this.write(evt.operationBody);
		}
	},
	
	
	
	/*
		OPERATIONS
	*/
	
	
	
	this.matrix = {};
	
	this.read = function(evt){
		
		this.clearMarked();
		
		if( (evt.source.index != null && evt.source.index.length == 2) && 
			this.ownData(evt.source) ){	
			
			this.traverseEdge(evt);
		}
		else if( (evt.source.index != null && evt.source.index.length == 1) 
			&& this.ownData(evt.source) ){
				
			this.traverseNode({
				id: evt.source.identifier,
				index: evt.source.index,
				value: evt.value
			});
		}
		
		if( (evt.target.index != null && evt.target.index == 1) 
			&& this.ownData(evt.target) ){
				
			this.traverseNode({
				id: evt.target.identifier,
				index: evt.target.index,
				value: evt.value
			});
		}
		
		this.environment3d.display();
	},
	
	this.traverseNode = function(args){
		var id = args.id;
		var index = args.index;
		var value = args.value;
		
		this.graph.traverse({object: this.graph.nodes[index[0]], color: this.readColor3d});
		this.arrayElement2d.markIndex({x: index[0], color: this.readColor2d});
	},
	
	this.traverseEdge = function(evt){

		var index = evt.source.index;
		var value = evt.value;
		
		// 
		if( this.graph.nodes[index[0]].graph.adjecent[index[1]] == null ){
			// create new edge
			this.graph.mark({objects: [this.graph.nodes[index[0]], this.graph.nodes[index[1]]], color: this.readColor3d});
			this.arrayElement2d.markIndex({x: index[0], y: index[1], color: this.readColor2d});
			this.arrayElement2d.markCell({x:index[0], y: index[1], color: this.readColor2d});
		}else{
		
			// fetch edge
			var edgeObj = this.graph.nodes[index[0]].graph.adjecent[index[1]];
			var node2 = edgeObj.edge.value;
			this.graph.colorObject({object: edgeObj, color: 0x000000});
			this.graph.mark({objects: [edgeObj, this.graph.nodes[index[0]], this.graph.nodes[index[1]]], color: this.readColor3d});
			this.arrayElement2d.markIndex({x: index[0], y: index[1], color: this.readColor2d});
			this.arrayElement2d.markCell({x:index[0], y: index[1], color: this.readColor2d});
		}
	}
	
	/*
		write operations are passed to this function
	*/
	
	this.write = function(evt){
		
		var id = evt.target.identifier;
		var index = evt.target.index;
		var value = evt.value;
		
		this.clearMarked();
		
		if(!this.ownData(evt.target)){
			this.read(evt);
			return;
		}
		
		// init nodes
		if(value instanceof Array ){
			if(index == null || index.length <= 0){
				this.addAll(value);
			}
		}
		// init edges
		else if(index != null && index.length == 1){
			this.connectAll({
				node: evt.target,
				nodes: value
			});
		}
		else if( index != null && index.length == 2 ){
			// connect two nodes
			this.connectNodes({ 
				node1: evt.target.index[0], 
				node2: evt.target.index[1], 
				value: evt.value, 
				id: evt.target.identifier
				});
				
			// mark source entity
			if(this.ownData(evt.source)){
				
			}
		}
		
		
		
		this.environment3d.display();
	},
	
	
	this.connectNodes = function(args){
		
		var node1 = args.node1;
		var node2 = args.node2;
		var value = args.value;
		var id = args.id;
		
	
		if(this.graph.nodes[node1] == null){
			this.addNode(node1);
		}
		
		if(this.graph.nodes[node2] == null){
			this.addNode(node2);
		}
		
		var edge = this.graph.nodes[node1].graph.adjecent[node2];
		
		if(edge == null){
			edge = this.graph.connectNodes({id1: node1 ,  id2: node2, index: node2, value: value});
		}
	
		this.arrayElement2d.markCell({x: node1, y: node2, color: this.writeColor2d});
		this.arrayElement2d.markIndex({x: node1, y: node2, color: this.writeColor2d});
		this.arrayElement2d.insertValue({x: node1, y: node2, value: value});
		this.graph.mark({objects: [edge, this.graph.nodes[node1], this.graph.nodes[node2]], color: this.writeColor3d});
		
	},
	
	this.connectAll = function(args){
		var node = args.node;
		var nodes = args.nodes;
		for(var i =0; i < nodes.length; i++){
			this.connectNodes({
				node1: node.index[0],
				node2: i,
				value: nodes[i],
				id: node.identifier,
			});
		}
	}
	
}

GraphVisualizer.prototype = Visualizer;
