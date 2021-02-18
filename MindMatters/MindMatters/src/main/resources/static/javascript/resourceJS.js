/**
 * 
 */
window.onload = function(){
	let header = document.getElementsByClassName('resource_header');
	let userAgent = window.navigator.userAgent.toLowerCase();
	
	let container = document.getElementsByClassName('container');
	
	if (userAgent.includes('wv')) {	
			for(var i=0; i<header.length; i+=1)
		        {header[i].style.display = "none";}
		    for(var i=0; i<container.length; i+=1)
				{	
					container[i].style.width = "100%";
					container[i].style.boxShadow = "none";
				}
		    } 
			
	else{
			for(var i=0; i<header.length; i+=1)
				{header[i].style.display = "block";}
			for(var i=0; i<container.length; i+=1)
				{
					container[i].style.width = "90%";
					container[i].style.boxShadow = "5px 5px 5px 1px #A3A3A3";
				}
	}
}

function openSelection() {
	document.getElementById("modal").style.display = "block";
}

function closeSelection() {
	document.getElementById("modal").style.display = "none";
}