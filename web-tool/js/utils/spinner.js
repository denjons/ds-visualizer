

var Spinner = function(){
	this.getSpinner = function(id){
			
		var spinnerParent = document.createElement("div");
		spinnerParent.setAttribute("class","spinner_parent");
			customSpinner =  this.getSpinnerWheel();
		
		spinnerParent.appendChild(customSpinner);
		spinnerParent.id = id;
		
		return spinnerParent;
	},
	this.getSpinnerWheel = function(){
			
		var customSpinner = document.createElement("div");
		customSpinner.setAttribute("class","custom_spinner");
		var spi = 1;
		for(spi = 1; spi <= 8; spi++){
			var spin = document.createElement("div");
			spin.setAttribute("class","spin"+(spi+1));
			customSpinner.appendChild(spin);
		}
		 return customSpinner;
			
	}
}