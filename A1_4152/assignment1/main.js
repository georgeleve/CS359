// Just playing around with Javascript

console.log("Hello world !!!");

var str = "my name is george";
typeof(str);

console.log(Number('1')+Number('2'));

var str = '8';
var num = 8;
console.log(str == num)
// type coersion
// returns true

function myFunction(txt){
    alert(txt);
}

var i;
for (i=0; i < 10; i++){
    console.log(i);
}

function changeMessage(){
    document.getElementById("mytext").innerHTML = "Message changed successfully!!!";
}

/* document.write(5 + 6); */
// alert(typeof(i));

let x, y, z;
x = 4;
y = 9;
z = x + y;

function myFunction(txt) {
    alert(txt);
}
var same = myFunction; // same as myFunction

$(document).ready(function(){
    $("#myInput").on("keyup", function() {
      var value = $(this).val().toLowerCase();
      $("#myTable tr").filter(function() {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
    });
});