window.onload = function(){

	let container = document.getElementsByClassName('container');
	
			for(var i=0; i<container.length; i+=1)
				{
					container[i].style.width = "90%";
					container[i].style.boxShadow = "5px 5px 5px 1px #A3A3A3";
				}
	}

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
