/* Geogios Gerasimos Leventopoulos csd4152 */ 

// Check if passwords are the same
function checkPasswords(){
    var firstPassword = document.getElementById("password1").value;
    var secondPassword = document.getElementById("password2").value;

    if (firstPassword !== secondPassword) {
        document.getElementById(message).style.display = "inline";
    }
    else{
        document.getElementById(message).style.display = "none";
    }
}

function hidePassword1() {
    var x = document.getElementById("password1");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
}
function hidePassword1() {
    var x = document.getElementById("password2");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
}

//use button or onchange event
function checkpasswordStrength(){
    //weak password, strong password, medium password
    var text="abcdef";
    for(var i=0; i<text.length;i++){
        console.log(text.charAt(i));
    }

}

// Ιnternist, general practitioner
//text area Information for the doctor
// 	Doctor's Office Address
function isDoctor(){

}


function makeAjaxReq() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    if (this.readyState = 4 && this.status = 200) {
    document.getElementById('demo').innerHTML
    = this.responseText; } };
    xhttp.open('GET' , 'http://myserver.com/servlet' ,
    true);
    xhttp.send();
}