function errorAlert(txt) {
	Swal.fire({
		icon: 'error',
		title: 'Oops...',
		text: txt
	});
}

function loginPOST() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
			window.location.href = '/HY-359/usermanagement.html';
        } else if (xhr.status !== 200) {
			const responseData = JSON.parse(xhr.responseText);
			if (xhr.status === 403) {
				errorAlert(responseData['error']);
				return;
			}
			errorAlert('Request failed. Returned status of ' + xhr.status);
        }
    };
	let myForm = document.getElementById('loginForm');
	let formData = new FormData(myForm);
	const data = {};
	formData.forEach((value, key) => (data[key] = value));
	var jsonData = JSON.stringify(data);
    xhr.open('POST', 'Login');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
}