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

function changePasswordStrength(password_strength){
  document.getElementById("passwordStrengthMessage").innerHTML = password_strength;
}

function checkPassword1Strength(){
  checkPasswords();

  let password = document.getElementById("password1").value;

  if (isWeakPassword(password) && password.length>0){
    changePasswordStrength("weak password");
  }else if (isStrongPassword()) {
    changePasswordStrength("strong password");
  }else {
    changePasswordStrength("medium password");
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

// RAPID API
function loadDoc() {
  //initialize
  const data = null;
  const xhr = new XMLHttpRequest();
  xhr.withCredentials = true;

  // what I do when the data arrive
  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === this.DONE) {
      //console.log(this.responseText);
      //alert(this.responseText);
      if(this.responseText === "{}"){
        document.getElementById("checkAddress").innerHTML = "Sorry, we couldn't find this address."; 
      }

      const obj = JSON.parse(xhr.responseText);
      var displayName = obj[0].display_name;
      var lon = obj[0].lon;
      var lat = obj[0].lat;

      if(displayName.includes('Crete') === false){
        document.getElementById("checkAddress").innerHTML = "Sorry, this service is only available in Crete at this moment."; 
      }else {
        document.getElementById("checkAddress").innerHTML = "Success !!! This address is located in Crete."; 
      }
    }else {
      //alert("Error on rapid api");
    }
  });

  // my input
  var addressName=document.getElementById("address").value; //"Chandakos";
  //var number=document.getElementById("number").value; //18;
  var city=document.getElementById("city").value; // "Heraklion";
  var country=document.getElementById("country").value; //"Greece";
  var address = addressName + " " + city + " " + country;

    //the request
  xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q="+ address +"&accept-language=en&polygon_threshold=0.0");
  xhr.setRequestHeader("x-rapidapi-host", "forward-reverse-geocoding.p.rapidapi.com");
  xhr.setRequestHeader("x-rapidapi-key", "2a6c4b07eamsh983963b34f4346fp1a7807jsnbf252b9be9e4");
  xhr.send(data);
}