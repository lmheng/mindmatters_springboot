function validate(){
	console.log("function called");
	var pwd = document.getElementById("newPassword").value;
	var cPwd = document.getElementById("confirmPassword").value;
	var submitOk = true;
	var pwdPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&]).{8,}$/;
	console.log(pwdPattern.test(pwd))
	if(!pwdPattern.test(pwd)){
		submitOk = false;
		document.getElementById("newPwdError").innerHTML = "*At least 8 characters. Should be combination of numbers, uppercase, lowercase and one special"
		+ " character from @$!%*#?&";
	}
	
	if(pwd!==cPwd){
		submitOk =  false;
		document.getElementById("confirmPwdError").innerHTML ="*Passwords do not match" ;
	}
	console.log(submitOk);
	return submitOk;
}