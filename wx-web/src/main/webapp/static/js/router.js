$(document).ready(function(){
//	var domain = 'http://mynona.xicp.net';
var domain = 'http://localhost:8080';
$('#list')[0].addEventListener("click", function(event){
	var e = event || window.event;
	var $target = e.target;
	if($target.name == "edit"){ //编辑按钮的click
		var $tr = $($target.closest('tr'));
		$tr.removeClass('disabled');
		$('input',$tr).each(function(){
			if($(this).attr('name') == "edit" || $(this).attr('name') == 'delete'){
				$(this).css('display', 'none');
			}else if($(this).attr('name') == 'save' || $(this).attr('name') == 'cancel' ||  $(this).attr('name') == 'save-text'){
				$(this).css('display', 'inline-block');
			}else{
				$(this).prop("disabled", false);
			}
		});
		$('select', $tr).each(function($e){
			$(this).prop("disabled", false);
		});
		$('textarea', $tr).each(function($e){
			$(this).prop("disabled", false);
		});
	}else if($target.name =="save"){//保存按钮的click
		var $tr = $($target.closest('tr'));
		var data = {};
		var $td = $($('td', $tr));
		data.id = $td[0].dataset.id;
		data.intercept = $('input', $td[1]).val();
		data.type = $('select', $td[2]).val();
		data.type_id = $('select', $td[3]).val();
		console.log(data)
		data.sortord = $('input', $td[4]).val();
		data = JSON.stringify(data)
		console.log(data);
		$.ajax({
			url:domain + '/wx/updateRouter.action'
			,type : 'post'
			,data:{data: data}
			,dataType: "json"
			,success: function callback(data){
				console.log(data);
				if(data.result == "0"){
					alert("保存成功！");
					location.reload();
				}else if(data.result == "1"){
					alert("保存失败！");
				}
			}
		});
	}else if($target.name=="save-text"){
		var data = {};
		var $tr = $($target.closest('tr'));
		var $td = $($('td', $tr));
		data.id = $td[0].dataset.id;
		data.desc = $('input[name="desc"]', $td[1]).val();
		data.content = $('input[name="content"]', $td[2]).val();
		data = JSON.stringify(data)
		$.ajax({
			url:domain + '/wx/saveText.action'
			,type : 'post'
			,data:{data: data}
			,dataType: "json"
			,success: function callback(data){
				console.log(data);
				if(data.result == "0"){
					alert("保存成功！");
					location.reload();
				}else if(data.result == "1"){
					alert("保存失败！");
				}
			}
		});
	}else if($target.name=="delete"){//删除按钮的click
		var $tr = $($target.closest('tr'))
		, id = $('td', $tr)[0].dataset.id;
		$.ajax({
			url:domain+'/wx/deleteRouter.action'
			,type : 'post'
			,data: {data:JSON.stringify({'data': id})}
			,dataType: "json"
			,success: function callback(data){
				console.log(data);
				if(data.result == "0"){
					alert("删除成功！");
					location.reload();
				}else if(data.result == "1"){
					alert("删除失败！");
				}
			}
		})
	}else if($target.name=="delete-text"){//删除按钮的click
		var $tr = $($target.closest('tr'))
		, id = $('td', $tr)[0].dataset.id;
		$.ajax({
			url:domain+'/wx/deleteText.action'
			,type : 'post'
			,data: {data:JSON.stringify({'data': id})}
			,dataType: "json"
			,success: function callback(data){
				console.log(data);
				if(data.result == "0"){
					alert("删除成功！");
					location.reload();
				}else if(data.result == "1"){
					alert("删除失败！");
				}
			}
		})
	}else if($target.name =="cancel"){
		location.reload();
	}else{
		if(e.preventDefault){
			e.preventDefault();
		}else{
			window.event.returnValue == false;
		}
	}
}, false);
	$('#submit').click(function(e){ //添加按钮的click
		var e = e || window.event
		,target = e.target
		,data = {};
		data.intercept = $('#intercept').val();
		data.type = $('#type').val();
		data.type_id = $('#type_id').val();
		data.sortord = $('#sortord').val();

		console.log(data)
		$.ajax({
			url:domain+'/wx/saveRouter.action'
			,type : 'post'
			,data: {data:JSON.stringify(data)}
			,dataType: "json"
			,success: function callback(data){
				console.log(data);
				if(data.result == "0"){
					alert("添加成功！");
					location.reload();
				}else if(data.result == "1"){
					alert("添加失败！");
				}
			}
		});
	})
	$('#submit-text').click(function(e){ //添加按钮的click
		var e = e || window.event
		,target = e.target
		,data = {};
		data.id = "0";
		data.desc = $('#desc').val();
		data.content = $('#content').val();
		$.ajax({
			url:domain+'/wx/saveText.action'
			,type : 'post'
			,data: {data:JSON.stringify(data)}
			,dataType: "json"
			,success: function callback(data){
				console.log(data);
				if(data.result == "0"){
					alert("添加成功！");
					location.reload();
				}else if(data.result == "1"){
					alert("添加失败！");
				}
			}
		});
	});
	$('select[name="selectType"]').change(function(e){ //获取回复类型的详细列表
		console.log(e.target.value)
		var i = {'data': e.target.value}
		var str = JSON.stringify(i);
		console.log(str)
		$.ajax({
			url: domain+'/wx/getMediaList.action'
			,data: {data:str}
			,dataType: "json"
			,type: "POST"
			,success: function(data){
				console.log(data);
				var data = data.data;
				var length = data.length;
				if($(e.target).closest('tr').length != 0){
					var $parent = $(e.target).closest('tr')
				}else if($(e.target).closest('ul').length != 0){
					var $parent = $(e.target).closest('ul')
				}
				var $select = $('select[name="content"]', $parent)
				$select.html("");
				for(var i = 0; i < length; i ++){
					$select.html($select.html() + 
						'<option value="'+data[i].id + '">'+data[i].desc + '</option>');
				}
			}
		})
	});
	function callback(data){
		console.log(data);
		if(data.result == "0"){
			alert("保存成功！");
			location.reload();
		}else if(data.result == "1"){
			alert("保存失败！");
		}
	}
})