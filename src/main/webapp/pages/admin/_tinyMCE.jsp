<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>	
	<script>
	tinymce.init({		
		  selector: 'textarea',  // change this value according to your HTML
		  auto_focus: 'element1',		  
		  plugins: [
			    'advlist autolink lists link image charmap print preview hr anchor pagebreak',
			    'searchreplace wordcount visualblocks visualchars code fullscreen',
			    'insertdatetime media nonbreaking save table contextmenu directionality',
			    'template paste textcolor colorpicker textpattern imagetools codesample toc help',
			    'code'
			  ],		  	
		  min_height: 600,
		  relative_urls: false,		  
		  toolbar1: 'insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | imageupload | code',
		  toolbar2: 'print preview media | forecolor backcolor | codesample help',
		  images_upload_base_path: "",
		  setup: function(editor) {
		    				
				  // create input and insert in the DOM
				  var inp = $('<input id="tinymce-uploader" type="file" name="pic" accept="image/*" style="display:none">');
				  $(editor.getElement()).parent().append(inp);

				  // add the image upload button to the editor toolbar
				  editor.addButton('imageupload', {
				    text: 'Add image',	
				    icon: 'image',
				    onclick: function(e) { // when toolbar button is clicked, open file select modal
				      inp.trigger('click');
				    }
				  });

				  // when a file is selected, upload it to the server
				  inp.on("change", function(e){
				    uploadFile($(this), editor);
				  });
				

				function uploadFile(inp, editor) {
				  var input = inp.get(0);
				  var data = new FormData();
				  data.append('files', input.files[0]);

				  $.ajax({
				    url: '${pageContext.request.contextPath}/a/images',
				    type: 'POST',
				    data: data,
				    enctype: 'multipart/form-data',
				    dataType : 'json',
				    processData: false, // Don't process the files
				    contentType: false, // Set content type to false as jQuery will tell the server its a query string request
				    success: function(data, textStatus, jqXHR) {
				      editor.insertContent('<img class="content-img" src="${pageContext.request.contextPath}' + data.location + '" data-mce-src="${pageContext.request.contextPath}' + data.location + '" />');
				    },
				    error: function(jqXHR, textStatus, errorThrown) {
				      if(jqXHR.responseText) {
				        errors = JSON.parse(jqXHR.responseText).errors
				        alert('Error uploading image: ' + errors.join(", ") + '. Make sure the file is an image and has extension jpg/jpeg/png.');
				      }
				    }
				  });
				}
		  }
		});
	
	
	</script>