var Grid = function(){
	
	this.gridMatrix,
	this.xLength,
	this.zLength,
	this.yLength,
	this.mesh,
	this.xLine,
	this.yLine,
	this.zLine,
	this.LINE_PADDING = 0.04;
	this.marked,
	this.timeout,
	this.ids,
	
	this.init = function(args){
		
		this.xLength = args.x;
		this.yLength = args.y;
		this.zLength = args.z;
		this.timeout = 1000;
		var func = args.func;
		var hexColor = args.color;
		var scale = args.scale;
		var position = args.position;
		
		this.ids = 0;
		
		//alert( args.x+", "+args.y+", "+args.z);
		
		this.marked = [];
		this.gridMatrix = [];
		
		// vertecies floor
		var vertecies = [];
		var delta_x = 1.0/this.xLength;
		var delta_z = 1.0/this.zLength;
		var delta_y = 1.0/this.yLength;
		var indexes = 0
		for(var x = 0; x <= this.xLength; x++){
			for(var z = 0; z <= this.zLength; z++ ){
				vertecies[indexes*3] = 	-0.5 + x*delta_x;	//x
				vertecies[indexes*3 + 1] = -0.5 + func(x, z)*delta_y;	//y
				vertecies[indexes*3 + 2] = -0.5 + z*delta_z; //z
				indexes ++;
			}
		}
		
		// geometry
		var geometry = new THREE.Geometry();
		var colors = [];
		
		for(var i=0; i<vertecies.length; i += 3){
			var vertex = new THREE.Vector3(
			vertecies[i],
			vertecies[i + 1],
			vertecies[i + 2]);
			geometry.vertices.push(vertex);
		}
		
		// geometry, faces
		var verts = 0;
		var inds = 0;
		for(var i = 0; i < this.xLength; i++){
			for(var j = 0; j < this.zLength; j++){
				geometry.faces.push( new THREE.Face3(verts, verts + 1, verts + this.zLength + 2) );
				geometry.faces.push( new THREE.Face3(verts, verts + this.zLength + 2, verts + this.zLength + 1) );
				
				// colors
				/*
				var mul = 0x010101;
				geometry.faces[ inds ].color.setHex( hexColor*((i+j)%2)*mul );
				geometry.faces[ inds + 1 ].color.setHex( hexColor*((i+j)%2)*mul );
				*/
				geometry.faces[ inds ].color.setHex( hexColor );
				geometry.faces[ inds + 1 ].color.setHex( hexColor );
				inds = inds+2;
				verts ++;
			}
			verts ++;
		}
		
		// normals
		geometry.computeFaceNormals();
		
		// create mesh
		this.mesh = new THREE.Mesh( 
					geometry,
					new THREE.MeshLambertMaterial( { vertexColors: THREE.VertexColors } )
				);	
				
		this.mesh.scale.set(scale[0], scale[1], scale[2] );
		this.mesh.position.set(position[0], position[1], position[2] );
		scene.add(this.mesh);
		
		// lines
		var lineMaterial = new THREE.LineBasicMaterial({ color: 0xe4c4c4c });
		var lineGeometry = new THREE.Geometry();
		lineGeometry.vertices.push(
			new THREE.Vector3( -0.5 - this.LINE_PADDING, - 0.5, 0.5 + this.LINE_PADDING ), // x LINE
			new THREE.Vector3( -0.5 - this.LINE_PADDING, - 0.5, - 0.5 - this.LINE_PADDING)  // x LINE
		);
		this.xLine = new THREE.Line( lineGeometry, lineMaterial );
		this.xLine.scale.set(scale[0], scale[1], scale[2] );
		this.xLine.position.set(position[0], position[1], position[2] );
		scene.add( this.xLine );
		
		lineMaterial = new THREE.LineBasicMaterial({ color: 0xe4c4c4c });
		lineGeometry = new THREE.Geometry();
		lineGeometry.vertices.push(
			new THREE.Vector3( -0.5 - this.LINE_PADDING, - 0.5, - 0.5 - this.LINE_PADDING ), // z LINE
			new THREE.Vector3( 0.5 + this.LINE_PADDING, - 0.5, - 0.5 - this.LINE_PADDING)  // z LINE
		);
		this.zLine = new THREE.Line( lineGeometry, lineMaterial );
		this.zLine.scale.set(scale[0], scale[1], scale[2] );
		this.zLine.position.set(position[0], position[1], position[2] );
		scene.add( this.zLine );
		
		lineMaterial = new THREE.LineBasicMaterial({ color: 0xe4c4c4c });
		lineGeometry = new THREE.Geometry();
		lineGeometry.vertices.push(
			new THREE.Vector3( -0.5 - this.LINE_PADDING, - 0.5, - 0.5 - this.LINE_PADDING ), // y LINE
			new THREE.Vector3( -0.5 - this.LINE_PADDING, 0.5 + this.LINE_PADDING, - 0.5 - this.LINE_PADDING)  // y LINE
		);
		this.yLine = new THREE.Line( lineGeometry, lineMaterial );
		this.yLine.scale.set(scale[0], scale[1], scale[2] );
		this.yLine.position.set(position[0], position[1], position[2] );
		scene.add( this.yLine );
		
	},
	
	/*
		
	*/
	
	this.add = function(args){
		
		var obj = args.object;
		var x = args.x;
		var z = args.z;
		var y = args.y;
	
		
		if(x >= this.xLength || z >= this.zLength){
			alert("Grid: indexd out of range!");
		}
		
		var m = Math.max(obj.scale.x,obj.scale.z);

		var w = (this.mesh.scale.x/this.xLength)/m;
		
		var sx = obj.scale.x;
		var sy = obj.scale.y;
		var sz = obj.scale.z;
		
		obj.scale.set(
			w*sx, 
			w*sy, 
			w*sz);
			
		obj.grid = {x:0,y:0,z:0,id:this.ids};
		obj.grid.standardColor = obj.material.color.getHex();
		this.ids ++;
		
		this.place({
			object: obj, 
			x: x, 
			y: y, 
			z: z
			});
		
	},
	
	/*
	
	*/
	
	this.place = function(args){
		
		var obj = args.object;
		var x = args.x;
		var z = args.z;
		var y = args.y;
		
		obj.grid.x = x;
		obj.grid.y = y;
		obj.grid.z = z;
		
		obj.position.set(
			this.mesh.position.x + obj.scale.x/2 - this.mesh.scale.x/2 + this.mesh.scale.x/this.xLength*x , 
		    this.mesh.position.y + obj.scale.y/2 - this.mesh.scale.y/2 + this.mesh.scale.y/this.yLength*y , 
			this.mesh.position.z + obj.scale.z/2 - this.mesh.scale.z/2 + this.mesh.scale.z/this.zLength*z);
			
		this.gridMatrix[z*this.zLength + x] = obj;
	},
	
	this.remove = function(obj){
		var x = obj.grid.x;
		var z = obj.grid.z;
		this.gridMatrix[z*this.zLength + x] = null;
	
	},
	
	this.get = function(args){
		var x = args.x;
		var y = args.y;
		var z = args.z;
		return this.gridMatrix[z*this.zLength + x];
	},	
	
	/*
		
	*/
	
	this.move = function(args){
		var obj = this.get({x: args.x, y: args.y ,z: args.z});
		
		if(obj == null ){
			alert("("+args.x+", "+args.z+") is null!");
		}
		
		this.place({
			object: obj, 
			x: args.destX, 
			y: args.destY, 
			z: args.destZ 
			});
		 
	},
	
	/*
		
	*/

	this.mark = function(args){
		
		var obj = this.gridMatrix[args.z*this.zLength + args.x];
		
		if(this.exists(this.marked, obj)){
			return 0;
		}
		
		obj.material.color.setHex(args.color);
		
		this.marked.push(obj);
		
		
	},
	
	/*
	
	*/
	
	this.markObj = function(args){
		
		var obj = args.object;
		
		if(this.exists(this.marked, obj)){
			return 0;
		}
		
		obj.material.color.setHex(args.color);
		
		this.marked.push(obj);
		
	},
	
	/*
	
	*/
	
	this.clearMarked = function(args){
		
		var color = args.color;
		
		for(var i = this.marked.length - 1; i >= 0; i--){
			this.marked[i].material.color.setHex(this.marked[i].grid.standardColor);
			this.marked.pop();
		}
	},
	
	/*
	
	*/
	
	this.exists = function(list, obj){
		for(var i = 0; i < list.length; i++){
			if(list[i].grid.id == obj.grid.id){
				return true;
			}
		}
		return false;
	}
	
}

/*

this.swap = function(args){
		var obj1 = this.get({x: args.x1, z: args.z1});
		var obj2 = this.get({x: args.x2, z: args.z2});
		obj1.grid.x = args.x2;
		obj1.grid.z = args.z2;
		obj2.grid.x = args.x1;
		obj2.grid.z = args.z1;
		this.place(obj1);
		this.place(obj2);
	
	},
*/

