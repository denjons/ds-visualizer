
var Markup = function(args){
	
	this.header = args.header;
	this.init = args.init,
	this.body = args.body,

	this.print = function(){
		var _this = this;
		return "{\n"+
				this.printHeader() 	+ ", \n"+
				this.printInit() 	+ ", \n"	+
				this.printBody() 	+ "\n"	+
				"}";
	},
	
	this.printElement = function(elm){
		var _this = this;
		return ' {'+
			'"op": ' 	+ elm.op	+' , '+
			'"id": ' 	+ elm.id 	+' , '+
			'"index": ' 	+ _this.printArray(elm.index, function(y){return y+""})		+' , '+
			'"value": ' 	+ _this.printArray(elm.value, function(y){return y+""})		+' , '+
			'"set": ' 	+ elm.set 	+' , '+
			'"target": '+ elm.target+'  }\n';
	},
	
	this.printBody = function(){
		var _this = this;
		return '"body": ' + this.printArray(this.body,function(x){return _this.printElement(x)});
	}
	
	this.printInit = function(){
		var _this = this;
		return '"init": ' + 
			this.printObject(
					this.init, 
					function(x){
						return _this.printArray(x, function(y){return y+"";})
					});
	},
	
	this.printHeader = function(){
		var _this = this;
		return '"header":{ \n'+
				'	"variables":'	+this.printArray(
					this.header.variables,
					function(x){
						return _this.printObject(x, function(y){ if(y.length > 0) return'"'+_this.printArray(y, function(x){ return ""+x})+'"';return y+"";})
						}) 									+ ', \n'+
				'	"visual": "'	+this.header.visual 	+ '" \n'+
				
		"}\n";
	},
	
	this.printArray = function(array, printer){
		var str = "[  ";
		for(var i = 0; i < array.length; i++){
			str = str + printer(array[i])+", ";
		}
		return str.substring(0, str.length - 2) +"]";
	},
	
	this.printObject = function(obj, printer){
		var str = "{  ";
		for(var key in obj){
			str = str +'\n    "'+key+'"'+": "+ printer(obj[key])+", ";
		}
		return str.substring(0, str.length - 2) +"\n}";
	}
}