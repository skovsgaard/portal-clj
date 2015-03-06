if(typeof Math.imul == "undefined" || (Math.imul(0xffffffff,5) == 0)) {
    Math.imul = function (a, b) {
        var ah  = (a >>> 16) & 0xffff;
        var al = a & 0xffff;
        var bh  = (b >>> 16) & 0xffff;
        var bl = b & 0xffff;
        // the shift by 0 fixes the sign on the high part
        // the final |0 converts the unsigned value into a signed value
        return ((al * bl) + (((ah * bl + al * bh) << 16) >>> 0)|0);
    }
}


;(function(){
if ("undefined" === typeof b) {
  var b = function() {
    throw Error("No *print-fn* fn set for evaluation environment");
  }
}
if ("undefined" === typeof d) {
  var d = null
}
if ("undefined" !== typeof Symbol) {
  var g = Symbol;
  "object" != typeof g || !g || g instanceof Array || g instanceof Object || Object.prototype.toString.call(g);
}
var h = "undefined" !== typeof Math.imul && 0 !== (Math.imul.a ? Math.imul.a(4294967295, 5) : Math.imul.call(null, 4294967295, 5)) ? function(a, c) {
  return Math.imul.a ? Math.imul.a(a, c) : Math.imul.call(null, a, c);
} : function(a, c) {
  var e = a & 65535, f = c & 65535;
  return e * f + ((a >>> 16 & 65535) * f + e * (c >>> 16 & 65535) << 16 >>> 0) | 0;
};
function k(a) {
  a = h(a, 3432918353);
  a = 0 ^ h(a << 15 | a >>> -15, 461845907);
  a = h(a << 13 | a >>> -13, 5) + 3864292196 ^ 0;
  a = h(a ^ a >>> 16, 2246822507);
  h(a ^ a >>> 13, 3266489909);
}
k(1);
k(0);
console.log("We are go for Clojurescript!");

})();
