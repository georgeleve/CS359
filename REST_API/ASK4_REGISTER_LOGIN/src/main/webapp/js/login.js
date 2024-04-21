function isLoggedIn() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			setChoicesForLoggedUser();
			$("#ajaxContent").html("Welcome again + xhr.responseText");
		} else if (xhr.status !== 200) {
			$("#choices").load("buttons.html");
		}
	};
	xhr.open('GET', 'Login');
	xhr.send();
}

function goToUserProfile(){
	window.location = "loggedin_user.html";
}

function loginPOST() {
	let username = document.getElementById("username").value;
	let password = document.getElementById("password").value;
	alert("get into loginPost()" + "username=" + username + " password="+ password);
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			//setChoicesForLoggedUser();
			//$("#ajaxContent").html("Successful Login");
			//document.getElementById("loginLabel").innerHTML = "Successful Login";
			
			alert("success login");
			document.getElementById("error").innerHTML = "Successful Login";
			goToUserProfile();
		} else if (xhr.status !== 200) {
			alert("failed to login");
			document.getElementById("error").innerHTML = "Error, wrong credentials";
			//alert(xhr.status + xhr.responseText);
			//$("#error").html("Wrong Credentials");
			//('Request failed. Returned status of ' + xhr.status);
		}
	};
	//var data = $('#loginForm').serialize();
	
	xhr.open('POST', 'Login');
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send(username + '&' + password + '&' + " ");
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
