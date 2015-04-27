$(document).ready(function(){
	var domain = "http://mynona.xicp.net"
	$('#list')[0].addEventListener('touchend', function(e){
		var e = e || window.event
		, target = e.target;
		if($(target).hasClass("head")){
			var data = {}
			, idolID = target.dataset.id;
			data.fans_id = $("#idols").attr('_fans_id');
			data.idol_id = idolID;
			for(var i = 0 ; i < idols.length; i++){
				if(idolID == idols[i]) return
			}
			$.ajax({
				url:domain + '/wx/addFocus.action'
				,type : 'post'
				,data:{data: JSON.stringify(data)}
				,dataType: "json"
				,success: function callback(data){
					console.log(data);
					if(data.result == "0"){
						$('#idols').append($($(target).closest("li")).clone());
						idols.push(idolID);
					}else if(data.result == "1"){
						console.log("保存失败！");
					}
				}
			})
		}
	}, false);
	$('#idols')[0].addEventListener('touchend', function(e){
		var e = e || window.event
		, target = e.target;
		if($(target).hasClass("head")){
			var data = {}
			, idolID = target.dataset.id;
			data.fans_id = $("#idols").attr('_fans_id');
			data.idol_id = target.dataset.id;
			$.ajax({
				url:domain + '/wx/deleteFocus.action'
				,type : 'post'
				,data:{data: JSON.stringify(data)}
				,dataType: "json"
				,success: function callback(data){
					console.log(data);
					if(data.result == "0"){
						$($(target).closest("li")).css("display", "none");
						for(var j = 0 ; j< idols.length; j++){
							if(idols[j] == parseInt(idolID)){
								idols.splice(j,1);
							}
						}
						idols.shift(idolID);
					}else if(data.result == "1"){
						alert("保存失败！");
					}
				}
			})
		}
	}, false);
})