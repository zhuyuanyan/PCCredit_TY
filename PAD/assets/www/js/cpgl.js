//产品管理
function mycpgl(){
	//工厂模式
    var data = JSON.stringify({
        "userId": window.sessionStorage.getItem("userId")
    });
    var post = crud.dom.factory("POST");
    post.doPost(wsProductList,data,ProductListCallback,"连接失败！");
    
}
//回调
function ProductListCallback(json){
	var obj = $.evalJSON(json);
    var result = obj.result;
    var str = "";
    str+="<div class='title'>产品管理</div>"+  
	    "<div class='content'>"+
	    "<ul class='list'>"
    for(var i=0;i<result.length;i++){
    	str+="<li onclick='mycpxx("+result[i].id+","+result+")'><img src='images/cp/1.jpg'/><span>"+result[i].productName+"<br/>"+
    	"<font>产品期限：<font class='blue'>"+result[i].prodLimitTime+"</font></font>" +
        "<font>产品利率：<font class='red'>"+result[i].rateRange+"</font></font>" +
    	"</span></li>";
    }
    str+= "</ul>"+
		    "</div>"+
		    "<div class='buttons'><input type='text' placeholder='搜索'/></div>";
    $("#cpgl").html(str);
	$(".right").hide();
	$("#cpgl").show();
}


//产品信息
function mycpxx(id,result){
	var str="";
	for(var i=0;i<result.length;i++){
		if(id==result[i].id){
			str="<div class='title'>"+result[i].productName+"</div>"+  
	            "<div class='content'>"+
	            "<table class='cpTable'>"+
	                "<tr>"+                             
	                    "<td style='width:25%;'>产品类别</td>"+         
	                    "<td>"+result[i].productTypeCode+"</td>"+
	                "</tr>"+
	                "<tr>"+                             
	                    "<td>产品描述</td>"+            
	                    "<td>XXXXXXXXX</td>"+
	                "</tr>"+
	                "<tr>"+                             
	                    "<td>产品授信区间</td>"+          
	                    "<td>"+result[i].prodCreditRange+"</td>"+
	                "</tr>"+
	                "<tr>"+                             
	                    "<td>产品期限</td>"+            
	                    "<td>"+result[i].prodLimitTime+"</td>"+
	                "</tr>"+
	                "<tr>"+                             
	                    "<td>产品利率</td>"+            
	                    "<td>"+result[i].rateRange+"</td>"+
	                "</tr>"+
	            "</table>"+
	        "</div>"+
	        "<div class='buttons'>" +
	            "<input type='button' class='btn btn-info' value='申请'/>"+                       
	            "<input type='button' class='btn' value='返回' onclick='mycpgl()'/>" +
	        "</div>"
		}
	}
    $("#cpxx").html(str);
    $(".right").hide();
    $("#cpxx").show();
}