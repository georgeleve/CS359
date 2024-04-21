function getBMI() {
	const data = null;
	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;
	xhr.addEventListener("readystatechange", function() {
		if (this.readyState === this.DONE) {
			console.log(this.responseText);
			const obj = JSON.parse(this.responseText);
			document.getElementById("show_bmi_label").innerHTML = "bmi=" + obj.data.bmi + "<br>health=" + obj.data.health + "<br>healthy_bmi_range=" + obj.data.healthy_bmi_range;
		} else {
			//alert("error while calling get bmi API");
		}
	});
	var myweight = document.getElementById("weight").value;
	var myheight = document.getElementById("height").value;
	xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/bmi?age=25&weight=" + myweight + "&height=" + myheight);
	xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
	xhr.setRequestHeader("x-rapidapi-key", "2a6c4b07eamsh983963b34f4346fp1a7807jsnbf252b9be9e4");
	xhr.send(data);
}

function getIdealWeight() {
	//alert("got into getIdealWeight()");
	const data = null;
	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;
	xhr.addEventListener("readystatechange", function() {
		if (this.readyState === this.DONE) {
			console.log(this.responseText);
			const obj = JSON.parse(this.responseText);
			document.getElementById("get_ideal_weight").innerHTML = "Devine=" + obj.data.Devine;
		} else {
			//alert("Error while calling weight API");
		}
	});
	var userGender = "male"; // document.getElementByName("gender").value;
	var userHeight = document.getElementById("height").value;
	xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/idealweight?gender=" + userGender + "&height=" + userHeight);
	xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
	xhr.setRequestHeader("x-rapidapi-key", "2a6c4b07eamsh983963b34f4346fp1a7807jsnbf252b9be9e4");
	xhr.send(data);
}

function getUserDetails() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			//$("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
			alert(this.responseText);
			//  $("#ajaxContent").html("Successful Login");
		} else if (xhr.status !== 200) {
			//$("#ajaxContent").html("User not exists");
			alert("error");
		}
	};
	var data = $('#loginForm').serialize();
	xhr.open('GET', 'GetUser?' + data);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send();
}

// get request
function fillFormWithUserInfo() {

}

function updatePassword() {
	//alert("got into update Password");
	var username = document.getElementById("username").value;
	var value = document.getElementById("password").value;
	updateUserInfo(username, "password", value);
}

function updateEmail() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("email").value;
	updateUserInfo(username, "email", value);
}

function updateFirstName() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("firstname").value;
	updateUserInfo(username, "firstname", value);
}

function updateLastname() {
	//alert("updateLastNmae()");
	var username = document.getElementById("username").value;
	var value = document.getElementById("lastname").value;
	updateUserInfo(username, "lastname", value);
}

function updateBirthdate() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("birthdate").value;
	updateUserInfo(username, "birthdate", value);
}

function updateGender() {
	//alert("updateGender()");
	if (document.getElementById('malegender').checked) {
		var value = "Male";
	} else if (document.getElementById('femalegender').checked) {
		var value = "Female";
	} else {
		var value = "Other";
	}

	var username = document.getElementById("username").value;
	updateUserInfo(username, "gender", value);
}

function updateCountry() {
	//alert("got into updateCountry");
	var username = document.getElementById("username").value;
	var value = document.getElementById("country").value;
	updateUserInfo(username, "country", value);
}

function updateCity() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("city").value;
	updateUserInfo(username, "city", value);
}

function updateAddress() {
	//alert("got into updateaddress");
	var username = document.getElementById("username").value;
	var value = document.getElementById("address").value;
	updateUserInfo(username, "address", value);
}

function updateTelephone() {
	//alert("got into updateTelephone");
	var username = document.getElementById("username").value;
	var value = document.getElementById("telephone").value;
	updateUserInfo(username, "telephone", value);
}

function updateHeight() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("height").value;
	updateUserInfo(username, "height", value);
}

function updateWeight() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("weight").value;
	updateUserInfo(username, "weight", value);
}

function updateBlooddonor() {
	if (document.getElementById('one').checked) {
		var value = "1";
	} else if (document.getElementById('zero').checked) {
		var value = "0";
	}

	var username = document.getElementById("username").value;
	updateUserInfo(username, "blooddonor", value);
}

function updateBloodType() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("blood_type").value;
	updateUserInfo(username, "bloodtype", value);
}

function updateLat() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("lat").value;
	updateUserInfo(username, "lat", value);
}

function updateLon() {
	var username = document.getElementById("username").value;
	var value = document.getElementById("lon").value;
	updateUserInfo(username, "lon", value);
}

//PUT Request
function updateUserInfo(username, key, value) {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			alert("success");
			//document.getElementById('msg').innerHTML = JSON.stringify(xhr.responseText);
		} else if (xhr.status !== 200) {
			//document.getElementById('msg')
			//.innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>" +
			JSON.stringify(xhr.responseText);
			alert("request failed");
		}
	};

	xhr.open('POST', 'UpdateUserDetails');
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(username + '&' + key + '&' + value);
}

function createTableFromJSON(data) {
	var html = "<table><tr><th>Category</th><th>Value</th></tr>";
	for (const x in data) {
		var category = x;
		var value = data[x];
		html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
	}
	html += "</table>";
	return html;
}

function getCertifiedDoctors() {
	alert("got into getCertified Doctors()")
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			//$("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
			alert(this.responseText);
			//const obj = JSON.parse(xhr.responseText);
			//var JSONInPrettyFormat = JSON.stringify(obj, undefined, 4);
			//document.getElementById('jsonarea').value = JSONInPrettyFormat ;

			//alert(JSONInPrettyFormat);
			//  $("#ajaxContent").html("Successful Login");
		} else if (xhr.status !== 200) {
			//$("#ajaxContent").html("User not exists");
			alert("error");
		}
	};
	//var data = $('#loginForm').serialize();
	xhr.open('GET', 'getCertifiedDoctors');
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send();
}

function showLoginRegisterPage(){
	window.location = "index.html";
}

function logout() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			//$('#choices').load("buttons.html");
			//$("#ajaxContent").html("Successful Logout");
			alert("Successful Logout");
			showLoginRegisterPage();
		} else if (xhr.status !== 200) {
			alert('Request failed. Returned status of ' + xhr.status);
		}
	};
	xhr.open('POST', 'Logout');
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send();
}

function hidePassword() {
	//alert("got inside hidepassword()");
	var x = document.getElementById("password");
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
}
