$(document).ready(function(){
	
	$(".linkMinus").on("click",function(event){
		event.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity"+productId);
		newQuantity = parseInt(quantityInput.val()) - 1;
		if(newQuantity >0){
			quantityInput.val(newQuantity);
		}else{
		alert("Minimum Quantity is 1");
		}
		
	});
	$(".linlPlus").on("click",function(event){
		event.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity"+productId);
		newQuantity = parseInt(quantityInput.val()) + 1;
		
		if(newQuantity <= 5){
			quantityInput.val(newQuantity);
		}else{
		alert("Minimum Quantity is 1");
		}
	});
});