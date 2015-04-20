$(function () {
    $("#btnCheck").bind("click", function () {
    	alert(1);
	var domain = 'http://localhost:8080';
		alert("click");
		$.ajax({
			url:domain+'/wx/saveRouter.action'
			,type : 'post'
			,data: {data:JSON.stringify(data)}
			,dataType: "json"
			,success:function(data){
				alert(123);
			}
		});
	})
})