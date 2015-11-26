//客户管理
function mykhgl(){
    $("#khgl").html("<div class='title'>客户管理</div>"+  
                    "<div class='content' style='padding-left:1em;'>" +
                        "<div class='box box1' onclick='newUser()'><img src='images/xjkh.png'/><span>新建客户</span></div>"+
                        "<div class='box box2'><img src='images/clkh.png'/><span>存量客户</span></div>"+
                        "<div class='box box3' onclick='editUser()'><img src='images/khwh.png'/><span>客户维护</span></div>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//新建客户
function newUser(){
    $("#khgl").html("<div class='title'>客户管理-新建客户</div>"+  
                    "<div class='content' style='margin-top:100px;'>" +
                        "<p>客户姓名:<input type='text'/></p>"+
                        "<p>证件类型:<select><option>身份证</option></select></p>"+
                        "<p>证件号码:<input type='text'/></p>"+
                        "<p>" +
                            "<input type='button' class='btn btn-info' value='确定' onclick='newUser1()'/>"+                       
                            "<input type='button' class='btn' value='返回' onclick='mykhgl()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//新建客户1
function newUser1(){
    $("#khgl").html("<div class='title'>客户管理-新建客户</div>"+  
                    "<div class='content' style='margin-top:100px;padding-left:1em;'>" +
                        "<div class='box box3 box4' onclick='khxxzlcj()'>" +
                            "<img src='images/xxzl.png'/>" +
                            "<span>客户信息资料采集</span>"+
                        "</div>"+
                        "<div class='box box3 box4' onclick='khyxzlcj()'>" +
                            "<img src='images/yxzl.png'/>" +
                            "<span>客户影像资料采集</span>"+
                        "</div>"+                      
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户信息资料采集
function khxxzlcj(){
    $("#khgl").html("<div class='title'>客户管理-新建客户</div>"+  
                    "<div class='content' style='margin-top:100px;'>" +
                        "<p><input type='button' class='btn btn-info btn-large' value='客户基本信息' onclick='khjbxx()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='客户经营信息' onclick='khjyxx()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='客户财务信息' onclick='khcwxx()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='客户其他信息' onclick='khqtxx()'/></p>" +
                        "<p><input type='button' class='btn' value='返回' onclick='newUser1()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户影像资料采集
function khyxzlcj(){
    $("#khgl").html("<div class='title'>影像资料采集</div>"+  
                    "<div class='content'>" +
                        "<table class='cpTable'>"+
							"<tr>"+                             
								"<td style='width:25%;'>房产证</td>"+         
								"<td><input type='file'/></td>"+
							"</tr>"+
							"<tr>"+                             
								"<td style='width:25%;'>结婚证</td>"+         
								"<td><input type='file'/></td>"+
							"</tr>"+
							"<tr>"+                             
								"<td style='width:25%;'>征信报告</td>"+         
								"<td><input type='file'/></td>"+
							"</tr>"+
							"<tr>"+                             
								"<td style='width:25%;'>银行流水</td>"+         
								"<td><input type='file'/></td>"+
							"</tr>"+
						"</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='mykhgl()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户维护
function editUser(){
    $("#khgl").html("<div class='title'>客户管理-客户维护</div>"+  
                    "<div class='content' style='margin-top:100px;'>" +
                        "<p><input type='button' class='btn btn-info btn-large' value='客户资料查询' onclick='khzlcx()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='客户维护计划' onclick='khwhjh()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='客户维护日志' onclick='khwhrz()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='客户催收日志' onclick='khcsrz()'/></p>" +
                        "<p><input type='button' class='btn' value='返回' onclick='mykhgl()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}