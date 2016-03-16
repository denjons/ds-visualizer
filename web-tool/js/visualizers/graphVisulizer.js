
var GraphVisualizer = function(args){
	
	this.graph,
	this.dataStructure,
	this.variables,
	this.sequence,
	this.markup = args.markup,
	
	this.init = function(){
		
		
		
		if(this.markup.header.variables[0] == null){
			alert("Graph program: no datastructure defined!");;
		}
		var type = this.markup.header.variables[0].type;
		
		// we only have this now 
		if(type != "adjecency list" && type != "adjecency matrix" ){
			alert("Graph program: could not find apropriate datastructure");
		}
		
		this.dataStructure = this.markup.init[this.markup.header.variables[0].name];
		
		// create graph and insert nodes 
		this.graph = new Graph();
		this.graph.init();
		
		for(var i = 0; i < this.markup.size; i ++){
			
			var hex = 0x5fcbff;
			var sphereGeometry = new THREE.SphereGeometry( 1, 20, 20 );
			var material = new THREE.MeshLambertMaterial({ color: hex});
			var sphere1 = new THREE.Mesh(sphereGeometry, material);
			var pos = 20;
			
			sphere1.position.set(pos*Math.random() - pos*Math.random(),
								 pos*Math.random() - pos*Math.random(),
								 pos*Math.random() - pos*Math.random());
								 
			this.graph.add({object: sphere1});
			
		}
		
		this.graph.positionNodes();
		
	},
	
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
		if(evt.op == "read"){
			this.read(evt);
		}
	},

	this.read = function(evt){
		
		if(evt.x.length == 1){
			
			this.graph.traverse({object: this.graph.nodes[evt.x[0]], color: 0x9e1b08});
			
		}else if(evt.x.length == 2){
			
			//console.log("x: "+evt.x[0]+"y: "+evt.y[0]+", length: "+this.graph.nodes[evt.x[0]].graph.adjecent.length);
			//alert(this.graph.nodes[evt.x[0]].graph.adjecent.length+", "+evt.y[0]);
			
			// connect the objects if not connected
			if(this.graph.nodes[evt.x[0]].graph.adjecent.length <=  evt.x[1] || 
				this.graph.nodes[evt.x[0]].graph.adjecent[evt.x[1]] == null){
				this.graph.connect({id1:evt.x[0] , id2:evt.y[0], index: evt.x[1] });
			}
			
			var edgeObj = this.graph.nodes[evt.x[0]].graph.adjecent[evt.x[1]];
			
			var node2 = edgeObj.edge.y;
			this.graph.markEdge({edge:edgeObj, color: 0x00000});
			this.graph.traverse({object: this.graph.nodes[evt.x[0]], color: 0x9e1b08});
		}
		display();
	}
	
	
}