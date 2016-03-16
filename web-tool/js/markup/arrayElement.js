
var ArrayElement = function(args){
	
	this.id = args.id,
	this.lengthX = args.lengthX,
	this.lengthY = args.lengthY,
	this.DOM,
	this.rows = [],
	this.marked,
	this.defaultBackgroundColor = "#efefef",

	/*
		initializes and builds a structure
	*/
	this.init = function(){
		
		this.marked = [];
		this.rows = [];
		this.DOM = document.createElement("div");
		this.DOM.id = "array_element";
		var table = document.createElement("table");
		table.setAttribute("class", "array_table");
		// column index
		var tr = document.createElement("tr");
		tr.id = "index_tr";
		var columns = [];
		for(var j = 0; j <= this.lengthX; j ++){
				td = document.createElement("td");
				td.id = "index_"+j;
				td.innerText = j-1;
				if(j == 0){
					td.innerText = "index";
				}
				td.setAttribute("class","array_cell");
				columns.push(td);
				tr.appendChild(td);
				table.appendChild(tr);
		}
		tr.appendChild(td);
		this.rows.push({tr: tr, columns: columns, length: columns.length});
	
		for(var i = 0; i < this.lengthY; i++){
			
			var tr = document.createElement("tr");
			tr.id = "y"+i;
			var columns = [];
			
			// row id index
			var td = document.createElement("td");
			td.id = "index_"+i;
			td.setAttribute("class","array_cell");
			td.innerText = i;
			columns.push(td);
			tr.appendChild(td);
			
			for(var j = 0; j < this.lengthX; j ++){
				td = document.createElement("td");
				td.id = "x"+j;
				td.setAttribute("class","array_cell");
				columns.push(td);
				tr.appendChild(td);
				
			}
			table.appendChild(tr);
			this.rows.push({tr: tr, columns: columns, length: columns.length});
		}
		
		var id = document.createElement("p");
		id.innerText = this.id;
		
		this.DOM.appendChild(id);
		this.DOM.appendChild(table);
	},
	
	/*
		inserts some value into a cell at x, y
	*/
	
	this.insertValue = function(args){
		
		var x = args.x;
		var y = args.y;
		var value = args.value;
		this.rows[y+1].columns[x+1].innerText = value;
		
	},
	
	/*
		marks a non-index cell at x, y with color
	*/
	this.markCell = function(args){
		var x = args.x;
		var y = args.y;
		var color = args.color;
		this.rows[y+1].columns[x+1].style.backgroundColor = color;
		this.marked.push(this.rows[y+1].columns[x+1]);
	},
	
	/*
		marks an index cell att x and/or y with color
	*/
	this.markIndex = function(args){
		var x = args.x;
		var y = args.y;
		var color = args.color;
		if(y != undefined){
			this.rows[y+1].columns[0].style.backgroundColor = color;
			this.marked.push(this.rows[y+1].columns[0]);
		}
		if(x != undefined){
			this.rows[0].columns[x+1].style.backgroundColor = color;
			this.marked.push(this.rows[0].columns[x+1]);
		}
		
	},
	
	this.clearMarked = function(){
		while(this.marked.length > 0){
			this.marked.pop().style.backgroundColor = this.defaultBackgroundColor;
		}
	}
	
}