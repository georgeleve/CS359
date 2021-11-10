/* Geogios Gerasimos Leventopoulos csd4152 */ 

// Check if passwords are the same
function checkPasswords(){
    var firstPassword = document.getElementById("password1").value;
    var secondPassword = document.getElementById("password2").value;

    if (firstPassword !== secondPassword) {
      document.getElementById("passwordErrorMessage").innerHTML = "<b>Error, both passwords must be exactly the same !!!</b>";
    }else{
      document.getElementById("passwordErrorMessage").innerHTML = "-";
    }
}

function checkDateAndAmka(){
	var amka = document.getElementById("amka").value.substring(0,6);

  var date1 = document.getElementById("birthday").value;
  var date = date1.substring(8,10) + date1.substring(5,7) + date1.substring(2,4);

  if(amka !== date){
    document.getElementById("amkaAndDateErrorMessage").innerHTML = "<b>The first 6 digits of your AMKA must match your Birthday</b>";
  }else{
    document.getElementById("amkaAndDateErrorMessage").innerHTML = "-";
  }
}

function isCheckboxChecked(){
  if (document.getElementById('mycheckbox').checked===false){
    document.getElementById("checkboxErrorMessage").innerHTML = "You must accept terms and conditions";
  }else{
    document.getElementById("checkboxErrorMessage").innerHTML = "-";
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


// >=80% of the characters are different
function isStrongPassword(){
    return false;
}

function atLeastHalfOfPasswordContainsASpecificCharacter(password) {
  return false;
}

function atLeastHalfOfPasswordAreNumbers(password) {
  const NUM_REGEX = /([\d])/g;
  let numberOfDigits = password.match(NUM_REGEX) ?? []; // store digits of the password inside an array
  return numberOfDigits.length >= (password.length/2);
}

function isWeakPassword(password){
  atLeastHalfOfPasswordAreNumbers(password) || atLeastHalfOfPasswordContainsASpecificCharacter(password);
}

function changeStrengthMessage(password_strength){
  document.getElementById("passwordStrengthMessage").innerHTML = password_strength;
}

function checkPassword1Strength(){
  checkPasswords();

  let password = document.getElementById("password1").value;

  if (isWeakPassword(password) && password.length>0){
    changeStrengthMessage("weak password");
  }else if (isStrongPassword()) {
    changeStrengthMessage("strong password");
  }else {
    changeStrengthMessage("medium password");
  }
}

function submitForm(){
  //checkPasswords();
  //checkDateAndAmka();
    isCheckboxChecked();
    return false;
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