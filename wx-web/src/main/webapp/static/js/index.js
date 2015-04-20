function loadSlide(){
	$.get("http://mynona.xicp.net/wx/getWx1931.action", function(json){
		data = JSON.parse(json).data;
		var length = data.length,
		wrapper = document.getElementById("swiperWrapper");
		for(var i = 0; i < length; i++){
			//alert(data[i].url);
			wrapper.innerHTML += '<div class="swiper-slide" style="background: url('+ data[i].url+') no-repeat center;"></div>';
		}
		slide();

	});
}

function slide(){
	function fixPagesHeight() {
		$('.swiper-slide,.swiper-container').css({
			height: $(window).height(),
		})
	}
	$(window).on('resize', function() {
		fixPagesHeight();
	})
	fixPagesHeight();
	var mySwiper = new Swiper('.swiper-container', {
		direction: 'vertical',
		mousewheelControl: true,
		watchSlidesProgress: true,
		watchActiveIndex: false,
		preloadImages: false,
		lazyLoading: true,
		onInit: function(swiper) {
			swiper.myactive = 0;
			swiperAnimateCache(swiper); 
			swiperAnimate(swiper); 
			for(var i = 5; i < 10; i++){
				$("#slide-" + i).css("visibility", "hidden");
			}

		},
		onSildeChangeStart: function(swiper){
			console.log(swiper.slideNext());
		},
		
		onSlideChangeEnd: function(swiper){ 
            var index = swiper.activeIndex;
            var prev = index - 1;
            var next = index + 1;
            for(var i = 0; i < index - 2; i ++){
                if($('#slide-' + i) != null){
                    $('#slide-' + i).css('visibility', "hidden");
			console.log(swiper.activeIndex);
                }
            }
            for(var j = index + 2; j < swiper.slides.length; j++){
                if($('#slide-' + i) != null){
                    $('#slide-' + i).css('visibility', "hidden");
                }
            }
            $('#slide-' + prev).css('visibility', "visible");
            $('#slide-' + next).css('visibility', "visible");
            $('#slide-' + index).css('visibility', "visible");


			swiperAnimate(swiper);
			
		},
		onProgress: function(swiper) {
            for (var i = 0; i < swiper.slides.length; i++) {
				var slide = swiper.slides[i];
				var progress = slide.progress;
				var translate, boxShadow;

				translate = progress * swiper.height * 0.8;
				scale = 1 - Math.min(Math.abs(progress * 0.5), 1);
				boxShadowOpacity = 0;

				slide.style.boxShadow = '0px 0px 10px rgba(0,0,0,' + boxShadowOpacity + ')';

				if (i == swiper.myactive) {
					es = slide.style;
					es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = 'translate3d(0,' + (translate) + 'px,0) scale(' + scale + ')';
					es.zIndex=0;
					
				}else{
					es = slide.style;
					es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform ='';
					es.zIndex=1;
				}
			}
		},


		onTransitionEnd: function(swiper, speed) {
			for (var i = 0; i < swiper.slides.length; i++) {
					//	es = swiper.slides[i].style;
					//	es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = '';
					//	swiper.slides[i].style.zIndex = Math.abs(swiper.slides[i].progress);
				}
				swiper.myactive = swiper.activeIndex;
			},
			onSetTransition: function(swiper, speed) {

				for (var i = 0; i < swiper.slides.length; i++) {
						//if (i == swiper.myactive) {
							es = swiper.slides[i].style;
							es.webkitTransitionDuration = es.MsTransitionDuration = es.msTransitionDuration = es.MozTransitionDuration = es.OTransitionDuration = es.transitionDuration = speed + 'ms';
						//}
					}

				}

			});
}

$(document).ready(function(){
	$('#music').click(function(e){
		var audio = document.getElementById("audio");
		if(audio.paused){
			e.target.className += " play";
			audio.play();
		}else{
			e.target.classList.remove("play");
			audio.pause();
		}
	})
})