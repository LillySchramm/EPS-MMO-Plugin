String.prototype.toCoord = function(){
    return "X:" + this.getCoord(0) + " Y: " + this.getCoord(1) + " Z: " + this.getCoord(2);
}

String.prototype.getCoord = function (n) {
    let _str = this.split('>>')[n]
    return _str.split('.')[0]
}

Number.prototype.min = function (int) {    
    if(this < int) return int;
    else return this + 0;
}

Number.prototype.max = function (int) {    
    if(this > int) return int;
    else return this + 0;
}

function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
  }
  
function rgbToHex(r,g,b) {
    r = r.toString(16);
    g = g.toString(16);
    b = b.toString(16);
  
    if (r.length == 1)
      r = "0" + r;
    if (g.length == 1)
      g = "0" + g;
    if (b.length == 1)
      b = "0" + b;
  
    return "#" + r + g + b;
  }


function hexToRgb(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16).min(1).max(255),
        g: parseInt(result[2], 16).min(1).max(255),
        b: parseInt(result[3], 16).min(1).max(255)
    } : null;
}
  
module.exports = {rgbToHex, hexToRgb}
  