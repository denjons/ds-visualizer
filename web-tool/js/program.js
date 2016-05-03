
var VisualizationProgram = function(args){
	this.name = args.name,
	this.container = args.container,
	this.mainDiv,
	this.codeLines,
	this.codeVisualizer,
	this.color = "#4cec8f",
	this.defaultColor = "#fcfcfc",
	
	
	this.init = function(args){
		this.codeLines = args.codeLines;
		this.mainDiv = this.createWindow(args.visualizations);
		this.codeVisualizer = new CodeVisualizer();
		
		this.codeVisualizer.init({
			container: 	this.mainDiv.childNodes[0],
			lines: 		this.codeLines,
			color: 		this.defaultColor 
		});
		this.container.appendChild(this.mainDiv);
	},
	
	this.onDisplay = function(operation){
		if(operation.source != this.name){
			this.mainDiv.style.visibility = "hidden";
			return;
		}
		
		this.mainDiv.style.visibility = "visible";
		this.codeVisualizer.clear();
		this.codeVisualizer.mark({
			beginLine: 	operation.beginLine,
			endLine: 	operation.endLine,
			color: 		this.color
		});
	}
	
}

var Program = {
	bannerHeight: 50,
	sourceWidth: 0.3,
	visualizationWidth: 0.7,
	width: 0,
	height: 0,
	createWindow: function(visualizations){
		var mainDiv = document.createElement("div");
		mainDiv.className = "program_window";
		
		var sourceDiv = this.createSourceWindow(visualizations);
		sourceDiv.className = "source_window";
		mainDiv.appendChild(sourceDiv);
		
		var visualizationDiv = document.createElement("div"); 
		visualizationDiv.className = "visualization_window";
		mainDiv.appendChild(visualizationDiv);
		
		this.setSize(mainDiv, sourceDiv, visualizationDiv);
		
		this.initVisualization(visualizations, visualizationDiv);
		
		return mainDiv;
	},
	
	initVisualization: function(visualizations, visualizationDiv ){
		for(var i =0; i < visualizations.length; i++){
			visualizations[i].init({
				width: this.width*this.visualizationWidth, 
				height: this.height, 
				container: visualizationDiv
				});
		}
	},
	
	createSourceWindow: function(){
		var sourceDiv = document.createElement("div"); 
		sourceDiv.className = "source_window";
		return sourceDiv;
	},
	
	setSize: function(mainDiv, sourceDiv, visualizationDiv){
		
		this.width = window.innerWidth;
		this.height = window.innerHeight - this.bannerHeight;
		
		mainDiv.style.width = this.width+"px";
		mainDiv.style.height = this.height+"px";
		
		sourceDiv.style.width = (this.width*this.sourceWidth)+"px";
		visualizationDiv.style.width = (this.width*this.visualizationWidth)+"px";
		
		visualizationDiv.style.marginLeft = (this.width*this.sourceWidth)+"px";
		
	}
	
	
}

VisualizationProgram.prototype = Program;