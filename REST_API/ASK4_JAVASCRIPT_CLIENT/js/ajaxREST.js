function createTableFromJSON(data, i) {
	var html = "<h4>Laptop " + i + "</h4><table><tr><th>Category</th><th>Value</th></tr>";
	for (const x in data) {
		var category = x;
		var value = data[x];
		html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
	}
	html += "</table><br>";
	return html;
}

// POST Request
// http://localhost:8080/ASK4_REST_API/test/newBloodTest/
function addBloodTest() {
	let myForm = document.getElementById('myForm');
	let formData = new FormData(myForm);
	const data = {};
	formData.forEach((value, key) => (data[key] = value));
	var jsonData = JSON.stringify(data);

	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById('msg').innerHTML = JSON.stringify(xhr.responseText);
		} else if (xhr.status !== 200) {
			document.getElementById('msg')
				.innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>" +
				JSON.stringify(xhr.responseText);
		}
	};
	xhr.open('POST', 'http://localhost:8080/ASK4_REST_API/test/newBloodTest/');
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonData);
}

// GET Request 1
// http://localhost:8080/ASK4_REST_API/test/bloodTests/03069200000?fromDate=2021-01-01&toDate=2021-11-11
function getBloodTest() {
	const xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			const obj = JSON.parse(xhr.responseText);
			document.getElementById("msg").innerHTML = "<h3>" + xhr.responseText + "</h3>";
		} else if (xhr.status !== 200) {
			document.getElementById('msg').innerHTML =
				'Request failed. Returned status of ' + xhr.status + "<br>" + JSON.stringify(xhr.responseText);
		}
	};
	var amka = document.getElementById("amka1").value;
	var URL = "http://localhost:8080/ASK4_REST_API/test/bloodTests/" + amka;

	// ?fromDate=2021-01-01&toDate=2021-11-11
	var fromDate = document.getElementById("from_date").value;  // this is optional
	var toDate = document.getElementById("to_date").value;      // this is optional   (I need to think if it needs to go and how)

	if (fromDate !== "" && toDate !== "") {
		URL += "?fromDate=" + fromDate + "&" + "toDate=" + toDate;
	}
	else if (fromDate !== "") {
		URL += "?fromDate=" + fromDate;
	} else if (toDate !== "") {
		URL += "?toDate=" + toDate;
	}

	xhr.open("GET", URL);
	xhr.setRequestHeader("Accept", "application/json");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send();
}

// GET Request 2
// http://localhost:8080/ASK4_REST_API/test/bloodTestMeasure/03069200000/cholesterol
function getBloodTestMeasure() {
	const xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			const obj = JSON.parse(xhr.responseText);
			document.getElementById("msg").innerHTML = "<h3>" + xhr.responseText + "</h3>";
		} else if (xhr.status !== 200) {
			document.getElementById('msg')
				.innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"
				+ JSON.stringify(xhr.responseText);
		}
	};

	var amka = document.getElementById("amka2").value;
	var measure = document.getElementById("measure0").value;
	xhr.open("GET", "http://localhost:8080/ASK4_REST_API/test/bloodTestMeasure/" + amka + "/" + measure);
	xhr.setRequestHeader("Accept", "application/json");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send();
}


// PUT Request
// http://localhost:8080/ASK4_REST_API/test/bloodTest/1/blood_sugar/98
function updateBloodTest() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById('msg').innerHTML = JSON.stringify(xhr.responseText);
		} else if (xhr.status !== 200) {
			document.getElementById('msg')
				.innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>" +
				JSON.stringify(xhr.responseText);
		}
	};
	var bloodTestID = document.getElementById("bloodtest_id0").value;
	var measure = document.getElementById("measure1").value;
	var value = document.getElementById("value").value;
	xhr.open("PUT", "http://localhost:8080/ASK4_REST_API/test/bloodTest/" + bloodTestID + "/" + measure + "/" + value);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send();
}


// DELETE Request
// http://localhost:8080/ASK4_REST_API/test/bloodTestDeletion/1
function deleteBloodTest() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById('msg').innerHTML = JSON.stringify(xhr.responseText);

		} else if (xhr.status !== 200) {
			document.getElementById('msg')
				.innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>" +
				JSON.stringify(xhr.responseText);
		}
	};
	var bloodTestID = document.getElementById("bloodtest_id1").value;
	xhr.open('DELETE', 'http://localhost:8080/ASK4_REST_API/test/bloodTestDeletion/' + bloodTestID);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send();
}
