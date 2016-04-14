
var Environment2D =  function(args){
	
	this.container = args.container, 
	this.browser = args.browser,
	this.divisions = args.divisions,
	this.position = args.position,
	this.canvas,
	this.ENV_WIDTH,
	this.ENV_HEIGHT,
	this.context,
	this.environmentContainer,
	this.margins,
	
	this.init = function(){
		
		this.environmentHeader = document.createElement("div");
		this.environmentHeader.id = "environment_header";
		
		this.environmentContainer = document.createElement("div");
		//this.environmentContainer.style.position = "absolute";
		this.environmentContainer.id = "environment_container";

		this.canvas = document.createElement("canvas");
		
		this.ENV_WIDTH = window.innerWidth;
		this.ENV_HEIGHT= window.innerHeight - 50;

		
		this.ENV_HEIGHT =  this.calculateHeight({height: this.ENV_HEIGHT, divisions: this.divisions});
		this.ENV_WIDTH =  this.calculateWidth({width: this.ENV_WIDTH, divisions: this.divisions});
		this.HEADER_HEIGHT = Math.min( Math.max( this.ENV_HEIGHT/10, 30), 30 );
		
		this.margins = this.calculateMargin({position:this.position});
		
		this.environmentContainer.style.marginLeft = (this.margins.w*this.ENV_WIDTH)+"px";
		this.environmentContainer.style.marginTop = (this.margins.h*this.ENV_HEIGHT)+"px";
		this.environmentContainer.style.width = this.ENV_WIDTH+"px";
		this.environmentContainer.style.height = this.ENV_HEIGHT+"px";
		
		this.canvas.width = this.ENV_WIDTH;
		this.canvas.height = (this.ENV_HEIGHT - this.HEADER_HEIGHT);
		this.canvas.style.width = this.ENV_WIDTH+"px";
		this.canvas.style.height = (this.ENV_HEIGHT -this.HEADER_HEIGHT)+"px";
		//this.canvas.style.marginTop = this.HEADER_HEIGHT + "px";
		this.environmentHeader.style.height = this.HEADER_HEIGHT+"px";
		
		this.environmentContainer.appendChild(this.environmentHeader);
		this.environmentContainer.appendChild(this.canvas);
		this.context = this.canvas.getContext('2d');
		this.container.appendChild(this.environmentContainer);
	
		
	},
	
	this.getCanvasHeight = function(){
		return this.ENV_HEIGHT -this.HEADER_HEIGHT;
	},
	
	this.setHeader = function(args){
		var txt = "<p>"+args.ds.identifier+"</p>";
		this.environmentHeader.innerHTML = txt;
	}
}



Environment2D.prototype = Environment;
