function loadSlide(){
	$.get("http://mynona.xicp.net/wx/getWx1931.action", function(json){
		data = JSON.parse(json).data;
		var length = data.length,
		wrapper = document.getElementById("swiperWrapper");
		for(var i = 0; i < length; i++){
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
		onInit: function(swiper) {
			swiper.myactive = 0;
			// swiperAnimateCache(swiper); 
			swiperAnimate(swiper); 

		},
		onSlideChangeEnd: function(swiper){ 
			swiperAnimate(swiper);
			console.log(swiper);

		} ,
		onProgress: function(swiper) {
			for (var i = 0; i < swiper.slides.length; i++) {
				var slide = swiper.slides[i];
				var progress = slide.progress;
				var translate, boxShadow;

				translate = progress * swiper.height * 0.8;
				// scale = 1 ;
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



