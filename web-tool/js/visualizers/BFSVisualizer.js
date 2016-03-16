
var BFSVisualizer = function(args){
	
	this.graph,
	this.dataStructure,
	this.variables,
	this.sequence,
	this.markup = args.markup
	
	this.init = function(){
		
	
		// do something with the datastructures
		for(var i =0 ; i < this.markup.header.annotaVariables.length; i++){
			
			var type = this.markup.header.variables[i].type;
			if(type == "edge list" ){
				
			}else if(type == "adjecency list"){
				
			}else if(type == "adjecency matrix"){
				
			}
		}
		
		// we only have this now 
		if(this.markup.header.variables[0] == null){
			alert("Graph program: no datastructure defined!");;
		}
		var type = this.markup.header.variables[0].type;
		if(type != "adjecency list" && type != "adjecency matrix" ){
			alert("Graph program: could not find apropriate datastructure");
		}
		
		this.dataStructure = this.markup.init[this.markup.header.variables[0].name];
		
		
		
		this.graph = new Graph();
		this.graph.init();
		
		for(var i = 0; i < this.dataStructure.length; i ++){
			
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
		
		for(var i = 0; i < this.dataStructure.length; i++){
			for(var j = 0; j < this.dataStructure[i].length; j++){
				console.log("connecting: "+i+" to "+this.dataStructure[i][j]);
				this.graph.connect({
					obj1: this.graph.nodes[i], 
					obj2: this.graph.nodes[this.dataStructure[i][j]], 
				    index: this.graph.nodes[i].graph.adjecent.length});
			}
			
		}
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
		if(evt.op == 1){
			this.read(evt);
		}
	},

	this.read = function(evt){
		if(evt.x.length == 1){
			this.graph.traverse({object: this.graph.nodes[evt.x[0]], color: 0x9e1b08});
		}else if(evt.x.length == 2){
			console.log("x: "+evt.x[0]+"y: "+evt.y[0]+", length: "+this.graph.nodes[evt.x[0]].graph.adjecent.length);
			//alert(this.graph.nodes[evt.x[0]].graph.adjecent.length+", "+evt.y[0]);
			var edgeObj = this.graph.nodes[evt.x[0]].graph.adjecent[evt.y[0]];
			var node2 = edgeObj.edge.y;
			this.graph.markEdge({edge:edgeObj, color: 0x00000});
			this.graph.traverse({object: this.graph.nodes[evt.x[0]], color: 0x9e1b08});
		}
		display();
	}
	
	/*
	
	*/
	
	this.search = function(){
		this.sequence = new Sequence({markup: this.markup, visualizer: this}); 
		//this.sequence.setVisualizer(this);
		this.breadthFirstSearch(this.dataStructure, 0, this.sequence);
		//this.markup.body = this.sequence.events;
		
	},
	
	/*
		breadth first search algorithm for tutorial purpose
	*/
	
	this.breadthFirstSearch = function(adjList, start, sequence){
		
		var marked = [];
		
		for(var i = 0; i < adjList.length; i ++){
			marked[i] = false;
		}
		
		var left = [];
		left.push(start);
		
		var i = 0 ;
		
		while(i < left.length){
			
			sequence.append({op:1,id:0,x:[left[i]],y:[], set: false, target: 0});
			
			
				
				for(var j = 0; j < adjList[left[i]].length; j ++){
					
					sequence.append({op:1,id:0,x:[left[i]],y:[], set: false, target: 0});
					
					if(!marked[adjList[left[i]][j]]){
						
						left.push(adjList[left[i]][j]);
						marked[adjList[left[i]][j]] = true;
						console.log("sort: "+i);
						sequence.append({op:1,id:0,x:[left[i],0],y:[j], set: false, target: 0});
						sequence.append({op:1,id:0,x:[adjList[left[i]][j]],y:[], set: false, target: 0});
						
					}
					
				}
				marked[left[i]] = true;
			
			i ++;
		}
		
		//alert(left);
	}
	
	
	
	
}