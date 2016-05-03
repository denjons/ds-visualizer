var CodeVisualizer = function(args){
	this.codeLines,
	this.container,
	this.defaultColor,
	this.marked = [],
	this.lines = [],
	
	this.init = function(args){
		this.defaultColor = args.color;
		this.codeLines = args.lines;
		this.container = args.container;
	
		this.populate();
	},
	
	this.populate = function(){
		var table = document.createElement("table");
		table.className = "code_table";
		var line;
		var linNumber;
		var code;
		for(var i = 0; i < this.codeLines.length; i++){
			
			line = document.createElement("tr");
			line.className = "code_line";
			linNumber = document.createElement("td");
			linNumber.innerHTML = "<code>"+i+"</code>";
			linNumber.className = "line_number";
			code = document.createElement("td");
			code.innerHTML = "<code>"+this.codeLines[i]+"</code>";
			code.className = "line_code";
			line.appendChild(linNumber);
			line.appendChild(code);
			table.appendChild(line);
			this.lines.push(line);
		}
		this.container.appendChild(table);
	},
	
	this.mark = function(args){
		if(args.beginLine >= this.lines.length){
			console.log("begin line index out of bounds: "+args.beginLine);
			return;
		}
		if(args.endLine >= this.lines.length){
			console.log("end line index out of bounds: "+args.endLine);
			return;
		}
		for(var i = args.beginLine; i <= args.endLine; i++) {
			this.lines[i].style.background = args.color;
			this.marked.push(this.lines[i]);
		}
	},
	
	this.clear = function(){
		while(this.marked.length > 0){
			this.marked.pop().style.background = this.defaultColor;
		}
	}
	
	
	
}