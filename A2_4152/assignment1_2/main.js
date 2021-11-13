/* Geogios Gerasimos Leventopoulos csd4152 */ 

// Question 1   OSM MAPS

// Check if passwords are the same
function checkPasswords(){
    var firstPassword = document.getElementById("password1").value;
    var secondPassword = document.getElementById("password2").value;

    if (firstPassword !== secondPassword) {
      document.getElementById("passwordErrorMessage").innerHTML = "<b>Error, both passwords must be exactly the same !!!</b>";
      return false;
    }else{
      document.getElementById("passwordErrorMessage").innerHTML = "-";
      return true;
    }
}

// The first 6 numbers of amka need to be the same as birthday
function checkDateAndAmka(){
	var amka = document.getElementById("amka").value.substring(0,6);

  var date1 = document.getElementById("birthday").value;
  var date = date1.substring(8,10) + date1.substring(5,7) + date1.substring(2,4);

  if(amka !== date){
    document.getElementById("amkaAndDateErrorMessage").innerHTML = "<b>The first 6 digits of your AMKA must match with your Birthday</b>";
  }else{
    document.getElementById("amkaAndDateErrorMessage").innerHTML = "-";
  }
}

function isCheckboxChecked(){
  if (document.getElementById('customCheck1').checked===false){
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

function countDifferentCharacters(password){
  var uniqueChars = "";

  for(var i=0; i<password.length;i++){
      if(uniqueChars.includes(password[i]) === false) {
          uniqueChars += password[i]
      }
  }
  return uniqueChars.length;
}

// >=80% of the characters are different
function isStrongPassword(password){
  if(countDifferentCharacters(password) >= (password.length*0.8)){
      return true
  }
  return false;
}

function atLeastHalfOfPasswordContainsASpecificCharacter(password) {
  for(var i=0; i<password.length;i++){
    var character_frequency = password.split(password.charAt(i)).length - 1;
    if(character_frequency >= (password.length/2)){
      //console.log(password.charAt(i));
      return true;
    }
  }
  return false;
}

function isNum(val){
  return !isNaN(val)
}

function atLeastHalfOfPasswordAreDigits(password) {
  var digitsCounter = 0
  for(var i = 0; i < password.length; i++){
    if (isNum(password.charAt(i))) {
      digitsCounter++;
    }
  }
  if (digitsCounter >= (password.length/2)){
    return true;
  }
  return false;
}

function isWeakPassword(password){
  if(atLeastHalfOfPasswordAreDigits(password) || atLeastHalfOfPasswordContainsASpecificCharacter(password)){
    return true;
  }
  return false;
}

function changePasswordStrength(password_strength){
  document.getElementById("passwordStrengthMessage").innerHTML = "<b>" + password_strength + "</b>" ;
}

function checkPasswordStrength(){
  if(checkPasswords() === true) {
    var password = document.getElementById("password1").value;

    if (isWeakPassword(password)){
      changePasswordStrength("Weak Password");
    }else if (isStrongPassword(password)) {
      changePasswordStrength("Strong Password");
    }else {
      changePasswordStrength("Medium Password");
    }
  }
}

function submitForm(){
    checkPasswords();
    checkDateAndAmka();
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

//////////////////// Question B   OSM MAPS  ////////////////////
var lon;
var lat;
var isInCrete = false;

// Get the input that user typed in the form
function getUserAddress(){
    var addressName=document.getElementById("home_address").value;
    var addressNumber=document.getElementById("addressNumber").value;
    var city=document.getElementById("city").value;
    var country=document.getElementById("country").value;
    var address=addressName+" "+addressNumber+" "+city+" "+country;
    return address;
}

// RAPID API
function geocodingSearch() {
  //Initialize
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
        inCrete = false;
      }

      const obj = JSON.parse(xhr.responseText);
      var displayName = obj[0].display_name; // pick the first address as most relevant
      lon = obj[0].lon;
      lat = obj[0].lat;

      if(displayName.includes('Crete') === false){
        document.getElementById("checkAddress").innerHTML = "Sorry, this service is only available in Crete at this moment.";
        isInCrete = false;
      }else {
        document.getElementById("checkAddress").innerHTML = "Success !!! This address is located in Crete.";
        isInCrete = true;
      }
    }else {
      //alert("Error on rapid api");
    }
  });

  var address = getUserAddress();
  //alert(address);

  //the request
  xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q="+ address +"&accept-language=en&polygon_threshold=0.0");
  xhr.setRequestHeader("x-rapidapi-host", "forward-reverse-geocoding.p.rapidapi.com");
  xhr.setRequestHeader("x-rapidapi-key", "2a6c4b07eamsh983963b34f4346fp1a7807jsnbf252b9be9e4");
  xhr.send(data);
}

//Declare Thesis
function setPosition(lat, lon){
  var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
  var toProjection   = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
  var position       = new OpenLayers.LonLat(lon, lat).transform( fromProjection, toProjection);
  return position;
}

// Declare Handler
function handler(position, message){
  var popup = new OpenLayers.Popup.FramedCloud(
    "Popup",
    position, null,
    message, null,
    true // <-- true if we want a close (X) button, false otherwise
  );
  map.addPopup(popup);

}

function mapExists(){
  if(document.getElementById("Map").style.display === "none"){
    return false;
  }
  return true;
}

function displayLocation(){
  if (isInCrete === true && mapExists() === false) {
    document.getElementById("Map").style.display = "block";
    $("#Map").show();

    document.getElementById('mapMessage').innerHTML = 'Success !!! See the map on the top of the page.';
    //Declare Marker
    map = new OpenLayers.Map("Map");
    var mapnik = new OpenLayers.Layer.OSM();
    map.addLayer(mapnik);

    //Markers
    var markers = new OpenLayers.Layer.Markers("Markers");
    map.addLayer(markers);

    //Declare Marker	
    var position=setPosition(lat, lon);
    var mar=new OpenLayers.Marker(position);
    markers.addMarker(mar);	
    mar.events.register('mousedown', mar, function(evt) { 
      handler(position, getUserAddress()); //getUserAddress() returns the address of the user
    });
    
    //Declare zoom	
    const zoom = 2;
    map.setCenter(position, zoom);
  }else{
    if (isInCrete === false) document.getElementById('mapMessage').innerHTML = 'Error !!! Your location needs to be in Crete';
    if (mapExists() === true) document.getElementById('mapMessage').innerHTML = 'Error !!! A map already exists';
  }
}

function deleteMap() {
  document.getElementById("Map").style.display = "none"; 
  $("#Map").empty();
  document.getElementById("checkAddress").innerHTML = "-";
  document.getElementById("mapMessage").innerHTML = "-";
}



//////////////////// Question c) ////////////////////

function showError(error) {
  var x = document.getElementById("findLocationLabel");
  switch(error.code) {
    case error.PERMISSION_DENIED:
      x.innerHTML = "User denied the request for Geolocation.";
      break;
    case error.POSITION_UNAVAILABLE:
      x.innerHTML = "Location information is unavailable.";
      break;
    case error.TIMEOUT:
      x.innerHTML = "The request to get user location timed out.";
      break;
    case error.UNKNOWN_ERROR:
      x.innerHTML = "An unknown error occurred.";
      break;
  }
}

function fillInputElements(road, road_number, city, country, postcode){
  document.getElementById("home_address").value = road;
  document.getElementById("addressNumber").value = road_number;
  document.getElementById("city").value = city;
  document.getElementById("country").value = country;
  document.getElementById("postcode").value = postcode;
  document.getElementById("findLocationLabel").innerHTML = "Success !!!";
}

function reverseGeocoding(lat, lon){
  const data = null;

  const xhr = new XMLHttpRequest();
  xhr.withCredentials = true;

  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === this.DONE) {
      //console.log(this.responseText);
      if(this.responseText === "{}"){
        document.getElementById("findLocationLabel").innerHTML = "Sorry, we couldn't find your address.";
      }
      //alert(xhr.responseText);
      const obj = JSON.parse(xhr.responseText);
      //console.log(obj);
      var road = obj.address.road;
      var road_number = 10;
      var city = obj.address.city;
      var country = obj.address.country;
      var postcode = obj.address.postcode;

      fillInputElements(road, road_number, city, country, postcode);
    }else{
      //alert("error");
    }
  });

  xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/reverse?lat=" + lat + "&lon=" + lon + "&accept-language=en&polygon_threshold=0.0");
  xhr.setRequestHeader("x-rapidapi-host", "forward-reverse-geocoding.p.rapidapi.com");
  xhr.setRequestHeader("x-rapidapi-key", "2a6c4b07eamsh983963b34f4346fp1a7807jsnbf252b9be9e4");
  xhr.send(data);
}

function showPosition(position) {
  var latitude = position.coords.latitude;
  var longitude = position.coords.longitude;

  document.getElementById("findLocationLabel").innerHTML = 
  "Latitude: " + latitude + "<br>Longitude: " + longitude;

  reverseGeocoding(latitude, longitude);
}

function findLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition, showError);
  } else {
    document.getElementById("findLocationLabel").innerHTML = "Geolocation is not supported by this browser.";
  }
  // geocodingSearch();
  // displayLocation();
}
















