String.prototype.toCoord = function(){
    return "X:" + this.getCoord(0) + " Y: " + this.getCoord(1) + " Z: " + this.getCoord(2);
}

String.prototype.getCoord = function (n) {
    let _str = this.split('>>')[n]
    return _str.split('.')[0]
}