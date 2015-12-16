$(document).ready(function() {
	
	$(".inpl").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});
   $(".inpr").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});

});

function countlrjb(){
	countTotal2();
}

function count1(id){
	var result = 0;
	result = Number($("input[name ='" + id + "']").val());
	return result;
}

function countTotal2(){
	var yzyincome =0; var kbincome =0;
	
	//月主营业务收入（1）总计
	yzyincome = count1("a6")*count1("a11")+count1("a7")*count1("a12")+count1("a8")*count1("a13")+count1("a10")*count1("a14");
	$("input[name ='a15']").val(yzyincome.toFixed(2)); 
	//月主营业务收入（1）月平均
	$("input[name ='a16']").val((yzyincome/12).toFixed(2)); 
	
	//可变成本（成本率 ）（2）总计
	kbincome = count1("a17") + count1("a18") + count1("a19") + count1("a20");
	$("input[name ='a21']").val(kbincome.toFixed(2)); 
	//可变成本（成本率 ）（2）月平均
	$("input[name ='a22']").val((kbincome/12).toFixed(2)); 
	
	//毛利淡季月份
	$("input[name ='a23']").val(count1("a11")-count1("a17"));
	//毛利一般月份
	$("input[name ='a24']").val(count1("a12")-count1("a18"));
	//毛利旺季月份
	$("input[name ='a25']").val(count1("a13")-count1("a19"));
	//毛利xx月份
	$("input[name ='a26']").val(count1("a14")-count1("a20"));
	
	//毛利= (1)-(2) 总计
	//毛利= (1)-(2) 月平均
}


$.fn.extend({
	moneyFormat : function () {
		return this.each(function () {
			$(this).keyup(function () {
				var reg = /^\d*\.?\d{0,2}$/,
				reg2 = /(?:\d*\.\d{0,2}|\d+)/,
				reg3 = /[^.0-9]+/;
				var _val = $(this).val(),
				isPlus = /^-/.test(_val),
				_val = isPlus ? _val.substr(1) : _val;
				if (!reg.test(_val)) {
					_val = _val.replace(reg3, "").match(reg2);
					_val = _val == null ? "" : _val[0];
					$(this).val(isPlus ? ("-" + _val) : _val);
				}
			}).blur(function () {
				var reg1 = /^\d+$/,
				reg2 = /^\.\d{0,2}$/,
				reg3 = /^\d+\.\d{0,2}$/,
				reg4 = /^0+(?:[1-9]\d*|0)\.\d{0,2}$/,
				reg5 = /^0+((?:[1-9]\d*|0)\.\d{0,2})$/;
				var _val = $(this).val(),
				isPlus = /^-/.test(_val),
				_val = isPlus ? _val.substr(1) : _val;
				if (reg1.test(_val)) {
					_val = _val + ".00";
				}
				if (reg4.test(_val)) {
					_val = _val.replace(reg5, "$1");
				}
				if (reg2.test(_val)) {
					_val = "0" + _val;
				}
				if (reg3.test(_val)) {
					var len = _val.length - _val.indexOf(".") - 1,
					str = "";
					for (var i = 0; i < 2 - len; i++) {
						str += "0";
					}
					_val += str;
				}
				$(this).val(isPlus ? ("-" + _val) : _val);
			});
		});
	}
});