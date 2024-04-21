var para = null;
var pwField = null;
var docType = null;
var docInfo = null;
var amkaField = null;
var locInfo = null;
var mapDiv = null;

var validPassword = true;
var validAmka = false;

function togglePasswordVisibility() {
	var x = document.getElementById("inputPassword");
	if (x.type === "password") x.type = "text";
	else x.type = "password";
}

function validatePassword() {
	var pw = document.getElementById("inputPassword");
	var pwConf = document.getElementById("inputPasswordConfirm");
	displayMissmatch(pw.value != pwConf.value);
}

function displayMissmatch(cond) {
	if (cond != (para == null)) return;
	if (para == null) {
		para = document.createElement("p");
		para.style.color = "red";
		validPassword = false;
		var node = document.createTextNode("Passwords do not match!");
		para.appendChild(node);
		insertAfter(document.getElementById('inputPasswordConfirm'), para);
	} else {
		validPassword = true;
		para.remove();
		para = null;
	}
}

function displaySecurity() {
	var s = pwSecurity();
	if (pwField != null) pwField.remove();
	pwField = null;
	if (s == -1) return;
	pwField = document.createElement("p");
	var node;
	if (s == 0) {
		pwField.style.color = "red";
		node = document.createTextNode("Weak password!");
	} else if (s == 1) {
		pwField.style.color = "orange";
		node = document.createTextNode("Medium password!");
	} else {
		pwField.style.color = "green";
		node = document.createTextNode("Strong password!");
	}
	pwField.appendChild(node);
	insertAfter(document.getElementById('inputPasswordConfirm'), pwField);
}

function pwSecurity() {
	var pw = document.getElementById("inputPassword").value;
	var n = pw.length, nums = 0;
	if (n == 0) return 0; //Empty pw
	var mp = {};
	for (var i = 0; i < n; i++) {
		if ('0' <= pw.charAt(i) && pw.charAt(i) <= '9') nums++;
		if (!mp[pw.charAt(i)]) mp[pw.charAt(i)] = 1;
		else mp[pw.charAt(i)]++;
	}
	if (nums >= n / 2) return 0; //weak
	var mx = 0, sz = 0;
	for (var key in mp) {
		mx = Math.max(mx, mp[key]);
		sz++;
	}
	if (mx >= n / 2) return 0; //weak
	if (sz * 100.0 / n >= 80) return 2; //strong
	return 1; //medium
}

function docUpdate() {
	var txt = document.getElementById("inputUserType").value;
	if (docType != null) docType.remove();
	if (docInfo != null) docInfo.remove();
	if (txt == "Doctor") {
		document.getElementById("inputAddress").placeholder = "Office Address";
		if (docType == null) {

			docType = document.createElement("select");
			docType.id = "inputDocType";
			var array = ["Pathologist", "General Doctor"];
			for (var i = 0; i < array.length; i++) {
				var option = document.createElement("option");
				option.value = array[i];
				option.text = array[i];
				docType.appendChild(option);
			}
			docInfo = document.createElement("textarea");
			docInfo.id = "inputDocInfo";
			docInfo.placeholder = "Doctor information";
			docType.className = "col-md-12 form-elem";
			docInfo.className = "col-md-12 form-elem";

		}
		insertAfter(document.getElementById('inputUserType'), docType);
		insertAfter(docType, docInfo);
	} else {
		document.getElementById("inputAddress").placeholder = "Address";
	}
}


function validateAmkaDate() {
	var amka = document.getElementById("inputAMKA").value;
	var date = (document.getElementById("inputBirthday").value).split("-");
	var year = date[0], month = date[1], day = date[2];
	if (amka.length >= 6 && amka.substring(0, 2) == day && amka.substring(2, 4) == month && amka.substring(4, 6) == year.substring(2, 4)) {
		if (amkaField != null) amkaField.remove();
		validAmka = true;
	} else {
		if (amkaField == null) {
			amkaField = document.createElement("p");
			amkaField.style.color = "red";
			validAmka = false;
			var node = document.createTextNode("Amka doesn't match the date!");
			amkaField.appendChild(node);
		}
		insertAfter(document.getElementById('inputAMKA'), amkaField);
	}
}

function insertAfter(referenceNode, newNode) {
	referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}

function findLocationPress() {
	locInfo = null;
	if (mapDiv != null) toggleMapViewPress();
	const data = null;
	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;
	xhr.addEventListener("readystatechange", function() {
		if (this.readyState === this.DONE) {
			var json = JSON.parse(this.responseText);
			if (json[0] == null) {
				document.getElementById("mapLabel").innerHTML = "Unable to find the given location!";
				document.getElementById("mapLabel").style.color = "red";
				return;
			}
			if (!(json[0]['display_name'].includes("Crete"))) {
				document.getElementById("mapLabel").innerHTML = "This tool only supports Crete!";
				document.getElementById("mapLabel").style.color = "red";
				return;
			}
			locInfo = [json[0]['lon'], json[0]['lat']];
			document.getElementById("mapLabel").innerHTML = "Location initialized! You can view it on the map.";
			document.getElementById("mapLabel").style.color = "green";
		}
	});
	var country = document.getElementById("inputCountry").value;
	var city = document.getElementById("inputCity").value;
	var address = document.getElementById("inputAddress").value;
	var query = country + " " + city + " " + address;
	xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q=" + encodeURI(query) + "&accept-language=en&polygon_threshold=0.0");
	xhr.setRequestHeader("x-rapidapi-host", "forward-reverse-geocoding.p.rapidapi.com");
	xhr.setRequestHeader("x-rapidapi-key", "d379ee5671mshcb2d5dcf69d3585p146ff4jsnd2f7ecb4db55");
	xhr.send(data);
}

function toggleMapViewPress() {
	if (mapDiv != null) {
		mapDiv.remove();
		mapDiv = null;
		return;
	}
	if (locInfo == null) {
		document.getElementById("mapLabel").innerHTML = "Please find a valid location first!";
		document.getElementById("mapLabel").style.color = "red";
		return;
	}
	mapDiv = document.createElement("div");
	mapDiv.id = "Map";
	mapDiv.className = "form-elem";
	mapDiv.style.height = "600px";
	mapDiv.style.width = "100%";
	insertAfter(document.getElementById('inputAddress'), mapDiv);


	var map = new OpenLayers.Map("Map");
	var mapnik = new OpenLayers.Layer.OSM();
	map.addLayer(mapnik);
	var markers = new OpenLayers.Layer.Markers("Markers");
	map.addLayer(markers);
	var position = setPosition(locInfo[0], locInfo[1]);
	markers.addMarker(new OpenLayers.Marker(position));
	map.setCenter(position, 15);
}

function setPosition(lat, lon) {
	var position = new OpenLayers.LonLat(lat, lon).transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
	return position;
}

/* Submission */
function errorAlert(txt) {
	Swal.fire({
		icon: 'error',
		title: 'Oops...',
		text: txt
	});
}

function registerPOST() {
	console.log("da");
	if (!validPassword) {
		errorAlert("Passwords don't match!");
		return;
	}
	if (!validAmka) {
		errorAlert("Please make sure the AMKA matches the birthday!");
		return;
	}
	if (locInfo == null) {
		errorAlert("Please find your location first!");
		return;
	}
	let myForm = document.getElementById('mainForm');
	let formData = new FormData(myForm);
	const data = {};
	
	data['userType'] = document.getElementById('inputUserType').value;
	if(data['userType']=="Doctor"){
		var docType = document.getElementById('inputDocType').value;
		var docInfo = document.getElementById('inputDocInfo').value;
		data['specialty'] = docType;
		data['doctor_info'] = docInfo;
	}
	
 	formData.forEach((value, key) => (data[key] = value));
	data['lat'] = locInfo[0];
	data['lon'] = locInfo[1];

	var jsonData = JSON.stringify(data);	

	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		const responseData = JSON.parse(xhr.responseText);
		if (xhr.readyState === 4 && xhr.status === 200) {
			Swal.fire('Success!', responseData['success'], 'success');
		} else if (xhr.status !== 200) {
			
			if (xhr.status === 403) {
				errorAlert(responseData['error']);
				return;
			}
			errorAlert('Request failed. Returned status of ' + xhr.status);
		}
	};
	xhr.open('POST', 'Register');
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonData);
}