function graphTest(){
		
		graph = new Graph();
		graph.init();
		for(var i = 0; i < 10; i ++){
			var hex = 0x5fcbff;
			var sphereGeometry = new THREE.SphereGeometry( 1, 20, 20 );
			var material = new THREE.MeshLambertMaterial({ color: hex});
			var sphere1 = new THREE.Mesh(sphereGeometry, material);
			var pos = 20;
			sphere1.position.set(pos*Math.random() - pos*Math.random(),
								 pos*Math.random() - pos*Math.random(),
								 pos*Math.random() - pos*Math.random());
			graph.add({object: sphere1});
		}
		
		graph.positionNodes();
		
		for(var j = 1; j < graph.nodes.length; j++){
			for(var i = 0; i < graph.nodes.length - j; i++){
			
				graph.connect({obj1: graph.nodes[i], obj2: graph.nodes[j]});
			}
			
		}
		
		runGraphTest(0, graph.nodes[graph.nodes.length*Math.random()|0]);
	}
	
	var time;
	
	function runGraphTest(it, node){
		
		if(node.graph.adjecent.length <= 0 ){
			return 0;
		}
		
		var edgeObj = node.graph.adjecent[node.graph.adjecent.length*Math.random()|0];
		var node2 = edgeObj.edge.y;
		
		//console.log(node2);
		console.log(node2.graph.id);
		
		graph.mark({object:node, color: 0xffa0a9});
		graph.markEdge({edge:edgeObj, color: 0x00000});
		graph.traverse({object: node2, color: 0x9e1b08});
		node2.material.color.setHex(0x9e1b08);
		display();
		it ++;
		if(it < 200){
			time = setTimeout(function(){
				runGraphTest(it, node2);
			},1000);
		}
	}