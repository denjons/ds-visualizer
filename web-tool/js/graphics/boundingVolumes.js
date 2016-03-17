
var BoundingVolumes = function(){
	this.boundingVolumes = [],
	this.ids = 0,
	
	this.add = function(object){
		object.geometry.boundingVolume = {id: this.ids };
		this.boundingVolumes.push(object.geometry);
		this.ids ++;
	}
	
}