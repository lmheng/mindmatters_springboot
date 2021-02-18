/**
 * 
 */

window.onload = function(){
	let downArrow = document.getElementById("bottom-arrow");
	let mybutton = document.getElementById("myBtn");
	
	window.onscroll = function() {scrollFunction()};
	
	function scrollFunction() {
  if (document.body.scrollTop > 150 || document.documentElement.scrollTop > 150) {
    mybutton.style.display = "block";
	downArrow.style.display = "none";
  } else {
    mybutton.style.display = "none";
	downArrow.style.display = "block";
  }
}
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}
