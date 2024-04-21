function errorAlert(txt) {
	Swal.fire({
		icon: 'error',
		title: 'Oops...',
		text: txt
	});
}

function logoutPOST() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			window.location.href = '/HY-359/usermanagement.html';
		}
	};
	xhr.open('POST', 'Logout');
	xhr.setRequestHeader('Content-type', 'application/json');
	xhr.send();
}


function askInput(t, callback) {
	Swal.fire({
		title: t,
		input: 'text',
		showCancelButton: true
	}).then((result) => {
		if (result.value) callback(result.value);
	});
}

function loadUser(user, pw){
	var xhr = new XMLHttpRequest();
		xhr.onload = function() {
			const responseData = JSON.parse(xhr.responseText);
		if (xhr.readyState === 4 && xhr.status === 200) {
			var json = JSON.parse(responseData['success']);
			for (let [key, value] of Object.entries(json)) {
				var elem = document.getElementById(key+"Field");
				if(elem != null) elem.value = value;
			}
		} else if (xhr.status !== 200) {
			if (xhr.status === 403) errorAlert(responseData['error']);
			else errorAlert('Request failed. Returned status of ' + xhr.status);
			logoutPOST();
		}
	};
	var data = {};
	var inData = {};
	inData['username'] = user;
	inData['password'] = pw;
	data['GET_USER_INFORMATION'] = inData;
	var jsonData = JSON.stringify(data);
	xhr.open('POST', 'UserManagement', true);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(jsonData); 
}

function loadPersonalInfo(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var r = JSON.parse(xhr.responseText);
			loadUser(r['username'],r['password']);
		} else if (xhr.status !== 200) {
			window.location.href = '/HY-359/login.html';
		}
	};
	xhr.open('GET', 'Login', false);
	xhr.send();
}

function insertRowCell(row, i, txt){
	let newCell = row.insertCell(i);
	let newText = document.createTextNode(txt);
	newCell.appendChild(newText);
}

function loadDocInfo(){
	var xhr = new XMLHttpRequest();
		xhr.onload = function() {
		const responseData = JSON.parse(xhr.responseText);
		if (xhr.readyState === 4 && xhr.status === 200) {
			var json = JSON.parse(responseData['success']);
			var tb = document.getElementById("docTable");
			$("#docTable tr").remove(); 
			for (let x of json) {
				let newRow = tb.insertRow(-1);
				insertRowCell(newRow, 0, x['firstname']);
				insertRowCell(newRow, 1, x['lastname']);
				insertRowCell(newRow, 2, x['address']);
				insertRowCell(newRow, 3, x['city']);
				insertRowCell(newRow, 4, x['telephone']);
				insertRowCell(newRow, 5, x['specialty']);
				insertRowCell(newRow, 6, x['doctor_info']);
			}
		} else if (xhr.status !== 200) {
			if (xhr.status === 403) {
				errorAlert(responseData['error']);
				return;
			}
			errorAlert('Request failed. Returned status of ' + xhr.status);
		}
	};
	var data = {};
	var inData = {};
	data['GET_DOCTORS'] = inData;
	var jsonData = JSON.stringify(data);
	xhr.open('POST', 'UserManagement', true);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(jsonData); 
}

function loadData() {
	loadPersonalInfo();
	loadDocInfo();
}

function update(type) {
	var XX = new XMLHttpRequest();
	XX.onload = function() {
		if (XX.readyState === 4 && XX.status === 200) {

			askInput("Enter new " + type + ":", function(result) {
				var xhr = new XMLHttpRequest();
				xhr.open("PUT", "UserManagement");
				xhr.setRequestHeader("Content-Type", "application/json");
				xhr.onreadystatechange = function() {
					if (xhr.readyState === 4 && xhr.status === 200) {
						const responseData = JSON.parse(xhr.responseText);
						Swal.fire('Success!', responseData['success'], 'success');
						loadData();
					} else if (xhr.status !== 200) {
						if (xhr.status === 403) {
							const responseData = JSON.parse(xhr.responseText);
							errorAlert(responseData['error']);
							return;
						}
						errorAlert('Request failed. Returned status of ' + xhr.status);
					}
				}
				var data = {};
				data['username'] = JSON.parse(XX.responseText)['username'];
				data[type] = result;
				var jsonData = JSON.stringify(data);
				xhr.send(jsonData);
			});



		} else if (XX.status !== 200) {
			window.location.href = '/HY-359/login.html';
		}
	};
	XX.open('GET', 'Login', false);
	XX.send();
}

function getBMI(){
	const data = null;
	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;
	xhr.addEventListener("readystatechange", function () {
		if (this.readyState === this.DONE) {
			const responseData = JSON.parse(xhr.responseText);
			if(responseData['status_code']!=200){
				errorAlert("Request failed: Error "+responseData['status_code']);
				return;
			}
			var res = "BMI: "+responseData['data']['bmi']+"<br>Health: "+responseData['data']['health']+"<br>Healthy BMI range: "+responseData['data']['healthy_bmi_range'];
			Swal.fire('Result:', res, 'info');
		}
	});
	const getAge = birthDate => Math.floor((new Date() - new Date(birthDate).getTime()) / 3.15576e+10)
	var height = document.getElementById("heightField").value, weight = document.getElementById("weightField").value, age = getAge(document.getElementById('birthdateField').value);
	xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/bmi?age="+age+"&weight="+weight+"&height="+height+"");
	xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
	xhr.setRequestHeader("x-rapidapi-key", "d379ee5671mshcb2d5dcf69d3585p146ff4jsnd2f7ecb4db55");
	xhr.send(data);
}

function getIdealWeight(){
	const data = null;
	
	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;
	
	xhr.addEventListener("readystatechange", function () {
		if (this.readyState === this.DONE) {
			const responseData = JSON.parse(xhr.responseText);
			if(responseData['status_code']!=200){
				errorAlert("Request failed: Error "+responseData['status_code']);
				return;
			}
			var res = "Your ideal weight is: "+responseData['data']['Devine'];
			Swal.fire('Result:', res, 'info');
		}
	});
	var height = document.getElementById("heightField").value, gender = document.getElementById("genderField").value;
	if(gender.toLowerCase()!="male" && gender.toLowerCase()!="female"){
		errorAlert("There are only two genders!");
		return;
	}
	xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/idealweight?gender="+gender+"&height="+height);
	xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
	xhr.setRequestHeader("x-rapidapi-key", "d379ee5671mshcb2d5dcf69d3585p146ff4jsnd2f7ecb4db55");
	
	xhr.send(data);
}