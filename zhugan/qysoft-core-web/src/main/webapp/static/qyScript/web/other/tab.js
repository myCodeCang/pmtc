/*data*/
apiready = function(){
    api.parseTapmode();
}
var tab = new auiTab({
    element:document.getElementById("tab"),
},function(ret){
    console.log(ret)
});