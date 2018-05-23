//链接跳转问题
mui('body').on('tap', 'a', function() {
	document.location.href = this.href;
});

//启用右滑关闭功能
mui.init({
	swipeBack: true //启用右滑关闭功能
});
mui('.mui-scroll-wrapper').scroll();
var slider = mui("#slider");
slider.slider({
	interval: 1000
});

mui.init({
	swipeBack: true //启用右滑关闭功能
});


function allSelect() {
	if($(":checkbox").attr("checked") != "checked") {
		$(":checkbox").attr("checked", "checked");
	} else {
		$(":checkbox").removeAttr("checked");
	}
}
