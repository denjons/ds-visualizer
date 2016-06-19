
	var VisualizationEnvironment =  function(){
		
		// DOM
		this.DOM,
		
		// browser specifics
		this.browser,
		this.allVisualizations = [],
		this.backupWindow,
		this.currentPrograms = [],
		this.sequence,
		
		this.init = function(){
			this.getClientBrowser();
			this.setUpDOM();
		}
		
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
			// spinner
			var spinner = new Spinner();
			this.DOM["spinner"] = spinner.getSpinner("spinner");
			document.body.appendChild(this.DOM["spinner"]);
			// input text
			//document.getElementById("text_input").value="";
			//this.DOM["input_text_window"] = document.getElementById("input_text_window");
			//this.DOM["container_window"].removeChild(this.DOM["input_text_window"]);
			
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
			
			this.DOM["container_window"].style.width = this.CANVAS_WIDTH+"px";
			this.DOM["container_window"].style.height = this.CANVAS_HEIGHT+"px";
			
			this.DOM["container"].style.width = (this.CANVAS_WIDTH)+"px";
			this.DOM["container"].style.height = this.CANVAS_HEIGHT+"px";
			
		},
		
		
		this.displayData = function(str){
			this.DOM["data_display"].innerHTML = "<source>"+str+"</source>";
		},
		
		
		this.programs = {
			"adjacencymatrix":
			function(args){			
				var currentProgram = new GraphVisualizer(
					{
						dataStructure: args.dataStructure, 
						environment: new Environment3D({
							position: args.position,
							divisions: args.divisions,
							//container: args.DOM["container"], 
							//debugContainer: args.DOM["debug_container"],
							browser: args.browser
						})
					}
				);
					
				//currentProgram.init();
				
				return currentProgram;
			},
			"binarytree":
			function(args){	
			
				var currentProgram = new BinaryTreeVisualizer(
					{
						dataStructure: args.dataStructure, 
						environment: new Environment2D({
							position: args.position,
							divisions: args.divisions,
							//container: args.DOM["container"], 
							browser: args.browser
					})
				});
				//currentProgram.init();

				return currentProgram;
			},
			"array":
			function(args){	
			
				var currentProgram = new StapleVisualizer(
					{
						dataStructure: args.dataStructure, 
						environment: new Environment2D({
							position: args.position,
							divisions: args.divisions,
							browser: args.browser
					})
				});
				//currentProgram.init();

				return currentProgram;
			},
			"matrix":
			function(args){	
			
				var currentProgram = new MatrixVisualizer(
					{
						dataStructure: args.dataStructure, 
						environment: new Environment2D({
							position: args.position,
							divisions: args.divisions,
							//container: args.DOM["container"], 
							browser: args.browser
					})
				});
				//currentProgram.init();

				return currentProgram;
			}
			
		},
		
		this.programs["tree"] = this.programs["binarytree"];
		
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
			this.sequence = Visualizer.sequence;
			this.sequence.setMarkup(markup);
			
			// v2.1 log specification
			
			//this.initSources(markup.header.sources);
			this.initSourceArrays(markup.header.sources);
			
			// annotated variables are in header, which is also always the case.
			if(this.allVisualizations.length <= 0){
				this.backup(markup);
			}
			
			this.DOM["upload_file_form"].removeChild(this.DOM["spinner"]);
			this.DOM["container_window"].removeChild(this.DOM["upload_file_window"]);
			this.DOM["container_window"].appendChild(this.DOM["player"]);

		},
		
		this.backup = function(markup){
			
			console.log("running backup");
			var visualizers = this.initVariables(markup.header.annotatedVariables);
			
			this.backupWindow = document.createElement("div");
			var width = window.innerWidth -20;
			var height = window.innerHeight-70;
			
			this.backupWindow.style.width = (width*0.7)+"px";
			this.backupWindow.style.height = height+"px";
			this.backupWindow.style.marginLeft = (width*0.3)+"px";
			
			for(var i = 0; i < visualizers.length; i++){
				visualizers[i].init({
					width: width*0.7, 
					height: height, 
					container: this.backupWindow
					});
				this.sequence.addVisualizer(visualizers[i]);
			}
			this.DOM["container"].appendChild(this.backupWindow); 
		},
		
		this.initSourceArrays = function(sources){
			for(var key in sources){
				
				if (sources.hasOwnProperty(key)) {
					
					var program = new VisualizationProgram({
						container: this.DOM["container"],
						name: key
					});
					
					program.init({
						visualizations: [],
						codeLines: sources[key]
					});
					
					Visualizer.sequence.addProgram({
						program: program
						});
						
					this.currentPrograms[key] = program;
				}
			}
		},
		
		
		// this code expects rhe annotated variables to be in source.
		/*
		this.initSources = function(sources){
			for(var key in sources){
				
				if (sources.hasOwnProperty(key)) {
					var program = new VisualizationProgram({
						container: this.DOM["container"],
						name: key
					});
			
					var variables = sources[key].annotatedVariables;
					
					if(variables != null){
						var visualizations = this.initVariables(variables);
						program.init({
							visualizations: visualizations,
							codeLines: sources[key].sourceLines
						});
					}else{
						program.init({
							visualizations: [],
							codeLines: sources[key].sourceLines
						});
					}
					
					
					// adding program to sequence here
					Visualizer.sequence.addProgram({
						program: program
						});
					this.currentPrograms[key] = program;
				}
			}
		},*/
		
		this.handleAbstractType = function(dataStructure){
			/*
			if(dataStructure.attributes.size != null &&
				dataStructure.attributes.size.length == 2 ){
				dataStructure.abstractType = "matrix";
			}*/
		},
		
		this.initVariables = function(variables){
			
			var currentPrograms = [];
			if(variables == null){
				return currentPrograms;
			}
			var keys = Object.keys(variables).length;
			var position = 0;
			var programConstructor;
			var prog;
			for(var key in variables){
				if (variables.hasOwnProperty(key)) {
					this.handleAbstractType(variables[key]);
					programConstructor = this.programs[variables[key].abstractType];
					
					if(programConstructor != null){
						prog = programConstructor({
								position: position,
								divisions: keys,
								dataStructure: variables[key], 
								//DOM: this.DOM, 
								browser : this.browser
							});
						currentPrograms.push(prog);
						this.allVisualizations.push(prog);
					}else{
						alert("no suitable program found for abstractType: "+variables[key].abstractType);
					}
					
					position++;
				}
			}
			return currentPrograms;
		},
		
		this.resetVisualization = function(){
			this.sequence.play();
		},
		
		this.runVisualization = function(){
			this.sequence.play();
		},
		
		this.pauseVisualization = function(){
			this.sequence.pause();
		},
		
		this.stopVisualization = function(){
			this.sequence.stop();
		},
		
		this.fastForwardVisualization = function(){
			this.sequence.fastForward(2);
		},
		
		this.slowDownVisualization = function(){
			this.sequence.slowDown(2);
		},
		
		this.stepVisualization = function(){
			this.sequence.step();
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
	
	
	
	
	