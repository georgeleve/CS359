function checkPasswords(){
    var firstPassword = document.getElementById("password1").value;
    var secondPassword = document.getElementById("password2").value;

    if (firstPassword !== secondPassword) {
        alert("Error! \"Password\" and \"Password Confirmation\" must be the same, please try again.\n")
    }
    /*else{
        alert("Success!!!\n")
    }*/
}
