/**
 * 
 */

window.onload = function(){

	let header = document.getElementsByClassName('resource_header');
	let userAgent = window.navigator.userAgent.toLowerCase();
	let container = document.getElementsByClassName('container');
	let outcomeBtn = document.getElementById('outcome_btn');
	
	let downArrow = document.getElementById("bottom-arrow");
	let mybutton = document.getElementById("myBtn");
	
	if (userAgent.includes('wv')) {	
			for(var i=0; i<header.length; i+=1)
		        {header[i].style.display = "none";
		        }
		        
		    for(var i=0; i<container.length; i+=1){
		    	container[i].style.width = "100%";
		    	container[i].style.boxShadow = "none";
		    }
		    
		    mybutton.style.left="0px";
		    mybutton.style.padding="10px";
		    } else{
		for(var i=0; i<header.length; i+=1)
		    {header[i].style.display = "block";}
		    
		    for(var i=0; i<container.length; i+=1){
		    	container[i].style.width = "90%";
		    	container[i].style.boxShadow = "5px 5px 5px 1px #A3A3A3";
		    }
		    
		    if(outcomeBtn != null)
		    	{outcomeBtn.value="Resources";}
		    mybutton.style.left="30px";
		    mybutton.style.padding="15px";   
		}
	
	window.onscroll = function() {scrollFunction()};
	
	function scrollFunction() {
  if (document.body.scrollTop > 150 || document.documentElement.scrollTop > 150) {
    mybutton.style.display = "block";
	downArrow.style.display = "none";
  } else {
    mybutton.style.display = "none";
	downArrow.style.display = "block";
  }
    var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
  var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
  var scrolled = (winScroll / height) * 100;
  document.getElementById("myBar").style.width = scrolled + "%";
}
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

function closeSelection() {
	document.getElementById("modal1").style.display = "none";
}
