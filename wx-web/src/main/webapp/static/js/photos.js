$(document).ready(function(){
	var domain = 'http://localhost:8080'
	
	$('input[name="fileName"]').bind('change', function(e){
		if (!window.File || !window.FileReader || !window.FileList || !window.Blob || !File.prototype.slice) {// 检测浏览器是否支html5的文件操作API
			alert("文件API不支持您现在正在使用的浏览器，请更换Firefox或者是Chrome浏览器后重试！");
			return false;
		}
			event.stopPropagation();
			event.preventDefault();
			var i, files, file, workers, worker,htmlINFO="",htmlMD5="",htmlSHA1="",htmlSHA256="";
			files = event.dataTransfer ? event.dataTransfer.files : event.target.files;
			output = [];
			for (i = 0; i < files.length; i += 1) {
				file = files[i];
				workers = [];
					worker = new Worker("calculator.worker.md5.js");
					worker.addEventListener("message", handle_worker_event("md5", e));
					workers.push(worker);
				hash_file(file, workers);
			}
			});
$('#addFile')[0].addEventListener('click', function(e){
	var target = e.target;
	if(target.name == "save"){
		var data = {}
		, $temp = $($(target).closest('.thumbnail'))
		data.id = target.dataset.id || "0";
		if(target.dataset.src == ""){
			alert("请选择需要上传的图片！");
			return;
		}
		if($('input[name="desc"]',$temp).val() == ""){
			alert("请输入描述内容！");

			return;
		}
		data.url = target.dataset.src;
		data.desc = $('input[name="desc"]',$temp).val();
		console.log(data);
		$.ajax({
			url:domain + '/wx/saveImage.action'
			,type : 'post'
			,data:{data: JSON.stringify(data)}
			,dataType: "json"
			,success: function callback(data){
				console.log(data);
				if(data.result == "0"){
					alert("保存成功！");
				}else if(data.result == "1"){
					alert("保存失败！");
				}
			}
		})
	}
}, false);
function appendBox(e, url){
	var target = e.target
	$(target).closest('.col-sm-6').before('<div class="col-sm-6 col-md-4 c-col">'
		+'<div class="thumbnail">'
		+'<div class="img-box" style="background-image: url('+ url +');">'
		+'</div>'
		+'<div class="caption">'
		+'<input type="text" value="'+target.files[0].name+'"  class="form-control"  name="desc">'
		+'<p>&nbsp;</p>'
		+'<p>'
		+'<input type="file" class="file-btn" role="button"  name="fileName">'
		+'<a href="#" class="btn btn-primary file-a" role="button">上传</a>'
		+'<a href="#" class="btn btn-default" role="button" name="save" data-id="" data-src="'+url+'">保存</a>'
		+'</p>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'</div>')
}
function handle_worker_event(id, e) {
	return function(event) {
		if (event.data.result) {
			$("#" + id).val(event.data.result);
			$('#addFile').ajaxSubmit(function(message){
				var $parent = $($(e.target).closest('.thumbnail'))
				message = JSON.parse(message);
				if(message.result == 0){
					if(e.target.id == "createFile"){
						appendBox(e, message.data);
					}else{
						$('.img-box',$parent).css('background-image', 'url('+message.data+')');
						$('a[name="save"]',$parent).attr('data-src', message.data);
//						$('input[name="desc"]', $parent).val(e.file.name);
					}
				}else if(message.result == 1){
					alert("上传图片失败！");
				}
			});
		} else {
			$("#" + id).val(event.data.block.end * 100 / event.data.block.file_size);
		}
	};
}
function handle_drag_over(event) {
	event.stopPropagation();
	event.preventDefault();
}

function hash_file(file, workers) {
	var i, buffer_size = 0, block = 0, threads = 0, reader = 0, blob = 0, handle_hash_block = 0, handle_load_block = 0;

	handle_load_block = function(event) {
		for (i = 0; i < workers.length; i += 1) {
			threads += 1;
			workers[i].postMessage({
				"message" : event.target.result,
				"block" : block
			});
		}
	};
	handle_hash_block = function(event) {
		threads -= 1;
		if (threads === 0) {
			if (block.end !== file.size) {
				block.start += buffer_size;
				block.end += buffer_size;
				if (block.end > file.size) {
					block.end = file.size;
				}
				reader = new FileReader();
				reader.onload = handle_load_block;
				blob = file.slice(block.start, block.end);
				reader.readAsArrayBuffer(blob);
			}
		}
	};
	buffer_size = 64 * 16 * 1024;
	block = {
		"file_size" : file.size,
		"start" : 0
	};
	block.end = buffer_size > file.size ? file.size : buffer_size;
	threads = 0;
	for (i = 0; i < workers.length; i += 1) {
		workers[i].addEventListener("message", handle_hash_block);
	}
	reader = new FileReader();
	reader.onload = handle_load_block;
	blob = file.slice(block.start, block.end);
	reader.readAsArrayBuffer(blob);
}

})