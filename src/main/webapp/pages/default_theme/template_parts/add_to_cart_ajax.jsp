<script>
	function addItemToCart(sku)
	{
		console.log('Add to cart!'+sku);
				
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/addtocart', 
			type: 'POST', 
			dataType: 'text',			
			data : {sku:sku},
			complete: function(response){
				console.log(response.responseText);
				$('#popupbody').html(response.responseText);
				updateCartItemCount();	
				$('#popup').popup().open();
			}
		}); 
	}
	
	function updateCartItemCount()
	{
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/updateCartItemCount', 
			type: 'GET', 
			dataType: 'text',	
			contentType: "application/json",
			complete: function(responseData, status, xhttp){ 				
				$('#cart-item-count').html('('+responseData.responseText+')');
			}
		});
	}
	updateCartItemCount();
	
	</script>