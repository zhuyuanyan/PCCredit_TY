var validator = $($formName).validate({
	rules : {
		maintenanceDay : {
			required : true, number:true
		}
	},
	messages : {
		maintenanceDay : {
			required : "维护天数不能为空",number : "请输入整数"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});