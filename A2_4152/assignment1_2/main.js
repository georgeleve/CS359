/* Geogios Gerasimos Leventopoulos csd4152 */ 

function isCheckboxChecked(){
  if (document.getElementById('checkbox').checked===false){
      alert("You need to agree to the Terms & Conditions: ");
  }
}

// Check if passwords are the same
function checkPasswords(){
    var firstPassword = document.getElementById("password1").value;
    var secondPassword = document.getElementById("password2").value;

    if (firstPassword !== secondPassword) {
        alert("Passwords must be the same");
    }
}

function checkDateAndAmka(){
	var amka = document.getElementById("amka").value.substring(0,6);

  var date1 = document.getElementById("birthday").value;
  var date = date1.substring(8,10) + date1.substring(5,7) + date1.substring(2,4);

  if(amka !== date){
    alert("Please check you AMKA or your Birthday");
  }
}

function hideDoctorInfo(){
  document.getElementById("textarea").style.display = "none";
  document.getElementById("doctorlabel").style.display = "none";
  document.getElementById("doctortype").style.display = "none";
  document.getElementById('address').innerHTML = "Home Address".bold();
}

function showDoctorInfo(){
  document.getElementById("textarea").style.display = "block";
  document.getElementById("doctorlabel").style.display = "block";
  document.getElementById("doctortype").style.display = "block";
  document.getElementById('address').innerHTML = "Doctor's Office Address".bold();
}

function showOrHide(){
  var usertype = document.getElementById("usertype").value;
  
  if(usertype==="doctor"){
    showDoctorInfo();
  }else{
    hideDoctorInfo();
  }
}

//use button or onchange event
function checkPasswordStrength(){
  //weak password, strong password, medium password
  var text = document.getElementById("password2").value;
  passwordSize = text.length;
  alert(text);
  counter = text.replace(/[^0-9]/g,"").length
  
  if ((passwordSize) <= (counter/2)){
    alert("weak password");
  }

 // for(var i=0; i<text.length; i++){
   //   alert(text.charAt(i));
  //}
}

function checkForm(){
 //checkPasswords();
 // checkDateAndAmka();
//isCheckboxChecked();
  checkPasswordStrength();
}

function hidePassword1() {
    var x = document.getElementById("password1");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
}

function hidePassword2() {
    var x = document.getElementById("password2");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
}

/*
function makeAjaxReq() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    if (this.readyState = 4 && this.status = 200) {
    document.getElementById('demo').innerHTML
    = this.responseText; } };
    xhttp.open('GET' , 'http://myserver.com/servlet' ,
    true);
    xhttp.send();
}*/