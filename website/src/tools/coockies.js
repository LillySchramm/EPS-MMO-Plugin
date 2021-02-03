function setCookie (cookieName, cookieValue, hourToExpire) {
    let date = new Date();
    date.setTime(date.getTime()+(hourToExpire*60*60*1000));
    document.cookie = cookieName + " = " + cookieValue + "; expires = " + date.toGMTString();
};

function readCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return null;
  }

function deleteCoockie(name) {
    document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"; 
}


module.exports = {setCookie, readCookie, deleteCoockie};