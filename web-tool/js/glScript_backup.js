	
	
	var VisualizationEnvironment =  function(){
		// orientation 
		this.prev_x = 0.0,
		this.prev_y = 0.0,
		this.x,
		this.y,
		this.mousedown,
		this.camera_theta = Math.PI / 4.0, 
		this.camera_phi = Math.PI / 6.0,
		this.camera_r = 75.0,

		// THREE.js
		this.renderer = null, 	
		this.scene = null, 			
		this.camera = null, 	
		this.raycaster = new THREE.Raycaster(),	
		

		this.ambient = 0xd1d1d1,
		this.specular = 0x212121,
		
		this.light, 
		this.ambientLight,
	
		// bounding volumes
		this.mouse = new THREE.Vector2(),
		
		// DOM
		this.DOM,
		
		// browser specifics
		this.browser,
		
		/*
			
		*/
		
		
		this.endLoad = function(){
			document.body.removeChild(this.DOM["spinner"]);
			//graphTest();
			//test();
		},
		
		this.setUpDOM = function(){
			
			this.DOM = [];
			
			this.CANVAS_WIDTH = window.innerWidth;
			this.CANVAS_HEIGHT= window.innerHeight - 50;
			this.getClientBrowser();
			
			// container window
			this.DOM["container_window"] = document.getElementById("container_window");
			this.DOM["container_window"].style.width = this.CANVAS_WIDTH+"px";
			this.DOM["container_window"].style.height = this.CANVAS_HEIGHT+"px";
			// container
			this.DOM["container"] = document.getElementById("container");
			
			/*
			if(this.browser == "firefox"){
				this.DOM["container"].addEventListener("mouseMove", this.motion, false);
				this.DOM["container"].addEventListener("mouseup", this.mouseUp, false);
				this.DOM["container"].addEventListener("mousedown", this.mouseDown, false);
			}
			*/
			// debug
			this.DOM["debug_container"] = document.getElementById("debug_container");
			// spinner
			var spinner = new Spinner();
			this.DOM["spinner"] = spinner.getSpinner("spinner");
			document.body.appendChild(this.DOM["spinner"]);
			// input text
			document.getElementById("text_input").value="";
			this.DOM["input_text_window"] = document.getElementById("input_text_window");
			this.DOM["container_window"].removeChild(this.DOM["input_text_window"]);
			
			// uplaod file window
			this.DOM["upload_file_window"] = document.getElementById("upload_file_window");
			this.DOM["upload_file_form"] = document.getElementById("upload_file_form");
			//this.DOM["container_window"].removeChild(this.DOM["upload_file_window"]);
			// data display
			this.DOM["data_display"] = document.getElementById("data_display");
			// player
			this.DOM["player"] = document.getElementById("player");
			this.DOM["container_window"].removeChild(this.DOM["player"]);
			// footer
			this.DOM["footer"] = document.getElementById("footer");
			var margin = this.CANVAS_HEIGHT + 50;
			this.DOM["footer"].style.marginTop = margin +"px";
			// 
			this.DOM["window_frame"] = document.createElement("div");
			this.DOM["window_frame"].setAttribute("class","fadein_half_class");
			this.DOM["window_frame"].style.zIndex = 2;
			this.DOM["window_frame"].id = "window_frame";

		},
		
		this.openFileWindow = function(){
			alert("ok");
			this.DOM["input_text_window"].style.zIndex = 3;
			this.DOM["container_window"].appendChild(this.DOM["input_text_window"]);
			this.DOM["container_window"].appendChild(this.DOM["window_frame"]);
		},
		
		this.closeFileWindow = function(){
			this.DOM["input_text_window"].style.zIndex = 0;
			this.DOM["container_window"].removeChild(this.DOM["input_text_window"]);
			this.DOM["container_window"].removeChild(this.DOM["window_frame"]);
		},
		
		/*
			change dimensions when browser window is rezised
		*/
		
		this.onWindowResize = function() {
			
			this.CANVAS_WIDTH = window.innerWidth;
			this.CANVAS_HEIGHT= window.innerHeight;
			this.camera.aspect = (window.innerWidth*0.7)/ window.innerHeight;
			this.camera.updateProjectionMatrix();
			this.renderer.setSize( window.innerWidth*0.7, window.innerHeight );
			
			this.DOM["container_window"].style.width = this.CANVAS_WIDTH+"px";
			this.DOM["container_window"].style.height = this.CANVAS_HEIGHT+"px";
			
			this.DOM["container"].style.width = (this.CANVAS_WIDTH*0.7)+"px";
			this.DOM["container"].style.height = this.CANVAS_HEIGHT+"px";
			
			this.DOM["debug_container"].style.width = (this.CANVAS_WIDTH*0.3)+"px";
			this.DOM["debug_container"].style.height = this.CANVAS_HEIGHT+"px";
			
			this.display();
		},
		
		
		this.displayData = function(str){
			this.DOM["data_display"].innerHTML = "<source>"+str+"</source>";
		},
		
		
		this.currentProgram,
		this.programs = {
			"bubble sort": 
			function(markup){
				var currentProgram;
				currentProgram = new BubbleSortVisualizer({markup: markup});
				currentProgram.init();
				currentProgram.sort();
				return currentProgram;
				//object["body"] = currentProgram.sequence.events;
			},
			"breadth first search":
			function(markup){
				var currentProgram;
				currentProgram = new BFSVisualizer({markup: markup});
				currentProgram.init();
				currentProgram.search();
				return currentProgram;
			},
			"3d grid":
			function(markup){
				var currentProgram;
				currentProgram = new gridVisualizer({markup: markup});
				currentProgram.init({size:10});
				return currentProgram;
			},
			"graph":
			function(args){
				var currentProgram;
				currentProgram = new GraphVisualizer({markup: args.markup, environment: args.environment});
				currentProgram.init({size:10});
				return currentProgram;
			}
		},
		
		this.parseText = function(text){
			
			
			this.DOM["upload_file_form"].appendChild(this.DOM["spinner"]);
		
			try{
				var object = JSON.parse(text);
			}catch(e){
				alert("could not parse text: "+e.message);
				this.DOM["input_text_window"].removeChild(this.DOM["spinner"]);
				return false;
			}
		
			
			// choose program
			var markup = object;
			this.currentProgram = this.programs[markup.header.visual]({markup: markup, environment: this});
			this.DOM["upload_file_form"].removeChild(this.DOM["spinner"]);
			this.DOM["container_window"].removeChild(this.DOM["upload_file_window"]);
			this.DOM["container_window"].appendChild(this.DOM["player"]);
			this.display();
		
		},
		
		this.runVisualization = function(){
			this.currentProgram.play();
		},
		
		this.pauseVisualization = function(){
			this.currentProgram.pause();
		},
		
		this.stopVisualization = function(){
			this.currentProgram.stop();
		},

		this.init = function(){
			
			/*
				renderer
			*/
			
			this.renderer = new THREE.WebGLRenderer( { clearColor: 0xf8f8f8, clearAlpha: 1, 
				antialiasing: true, pixelRatio: window.devicePixelRatio, alpha: true  } );
			this.renderer.setSize( this.CANVAS_WIDTH, this.CANVAS_HEIGHT );
			//this.renderer.setClearColor( 0xf8f8f8 );
			this.DOM["container"].appendChild( this.renderer.domElement );
			
			/*
				scene
			*/
			
			this.scene = new THREE.Scene();
			
			/*
				lights
			*/
			
			// ambient
			this.ambientLight = new THREE.AmbientLight( this.ambient); // soft white light
			this.scene.add( this.ambientLight );
			
			// specular
			this.light = new THREE.PointLight( this.specular );
			this.light.position.set( 2, 20, 0 );
			this.scene.add(this.light);
			
			
			/*
				camera
			*/
			
			this.camera = new THREE.PerspectiveCamera(
			35, this.CANVAS_WIDTH / this.CANVAS_HEIGHT, 1, 10000);
			this.camera.position.set( -15, 10, 10 );
			this.camera.lookAt( this.scene.position );
			this.scene.add( this.camera );
			
			
			/*
				other
			*/
			
			this.renderer.clear(true, false,false);
			
			window.addEventListener( 'resize', this.onWindowResize, false );
			window['onresize'] = this.onWindowResize;
			this.onWindowResize();
		},
		
		
		
		/*
			refresh scene
		*/
		
		this.display = function(){
			
			//this.raycaster.setFromCamera(this.mouse, this.camera );
			//console.log("displ: "+this.mouse.x+", "+this.mouse.y);
			
		// calculate objects intersecting the picking ray
	/*
			var intersects = raycaster.intersectObjects( scene.children );
			for(var i = 0; i < intersects.length; i++){
				intersects[i].object.material.color.setHex(0x000000);
			}
		*/		
			var coords = this.sphericalToCartesian(this.camera_theta, this.camera_phi, this.camera_r);
			this.camera.position.x = coords[0];
			this.camera.position.y = coords[1] + 10;
			this.camera.position.z = coords[2];
			
			this.updateScene();
			this.light.position.set( coords[0],coords[1],coords[2]);
			
			this.camera.lookAt( this.scene.position );
			
			this.renderer.render( this.scene, this.camera );
			
		},
		
		this.updateScene = function(){
			//graph.update();
		},

		/*
			convert coordinates
		*/
		
		this.sphericalToCartesian = function(theta, phi, r){
			return [r * Math.sin(theta)*Math.sin(phi),
								r * Math.cos(phi), 
								r * Math.cos(theta)*Math.sin(phi) ];
		},
		
		
		
		/*
			mouse events
		*/
		
		this.mouseDown = function(evt){
			this.mousedown = true;
		},

		this.mouseUp = function(evt){
			
			this.mousedown = false;
			
			if(this.browser == "Firefox"){
				this.mouse.x=evt.clientX;
				this.mouse.y=evt.clientY;
			}else{
				this.mouse.x=window.event.clientX;
				this.mouse.y=window.event.clientY;
				btnType = window.event.button;
			}

			this.mouse.x = ((this.mouse.x/ window.innerWidth ) -0.5)*2;
			
			var y = ((this.mouse.y/ window.innerHeight)-0.5)*-2;
			this.mouse.y = (((this.mouse.y-20)/ window.innerHeight ) -0.5)*-2;
			
			this.display();
			
			return false; 
		},

		/*
			mouse motion
		*/
		
		this.motion = function(evt){
			
			var btnType;
			
			if(this.browser == "Firefox"){
				this.x=evt.clientX;
				this.y=evt.clientY;
			}else{
				this.x=window.event.clientX;
				this.y=window.event.clientY;
				btnType = window.event.button;
			}
			
			this.delta_x = this.x - this.prev_x;
			this.delta_y = this.y - this.prev_y;
			
			//alert(delta_x+", "+delta_y+", "+x+", "+y);
			
			
			if(this.mousedown && (btnType== 2))
			{
				this.camera_r -= this.delta_y * 0.3;
				// make sure cameraDistance does not become too small
				this.camera_r = Math.max(0.1, this.camera_r);
				this.display();
			}
			
			if(this.mousedown && (btnType == 0))
			{
				this.camera_phi	-= this.delta_y * 0.3 * Math.PI / 180.0;
				this.camera_phi = Math.min(Math.max(0.01, this.camera_phi), Math.PI - 0.01);
				this.camera_theta -= this.delta_x * 0.3 * Math.PI / 180.0;
				
				this.display();
			}
			
			this.prev_x = this.x;
			this.prev_y = this.y;
			//console.log(this.y + " "+ this.x);
			return false; 
		},
		
		/*
			BROWSER INFO
		*/
		this.getClientBrowser = function(){
			var nAgt = navigator.userAgent;
			if (nAgt.indexOf("Firefox")!=-1){
				this.browser = "Firefox";
			}
			if(nAgt.toLowerCase().indexOf('chrome') > -1){
				this.browser = "Chrome";
			}
		}
	}
	
	
	
	
	