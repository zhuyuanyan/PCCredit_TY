//产品管理
function mycpgl(){
    $("#cpgl").html("<div class='title'>产品管理</div>"+  
                    "<div class='content'>"+
                        "<ul class='list'>"+
                            "<li onclick='mycpxx()'>" +
                                "<img src='images/cp/1.jpg'/>" +
                                "<span>" +
                                    "产品1<br/>" +
                                    "<font>产品期限：<font class='blue'>2015-12-12</font></font>" +
                                    "<font>产品利率：<font class='red'>3%</font></font>" +
                                "</span>" +
                            "</li>"+
                            "<li onclick='mycpxx()'>" +
                                "<img src='images/cp/2.jpg'/>" +
                                "<span>" +
                                    "产品2<br/>" +
                                    "<font>产品期限：<font class='blue'>2015-12-12</font></font>" +
                                    "<font>产品利率：<font class='red'>3%</font></font>" +
                                "</span>" +
                            "</li>"+
                            "<li onclick='mycpxx()'>" +
                                "<img src='images/cp/3.jpg'/>" +
                                "<span>" +
                                    "产品3<br/>" +
                                    "<font>产品期限：<font class='blue'>2015-12-12</font></font>" +
                                    "<font>产品利率：<font class='red'>3%</font></font>" +
                                "</span>" +
                            "</li>"+
                            "<li onclick='mycpxx()'>" +
                                "<img src='images/cp/4.jpg'/>" +
                                "<span>" +
                                    "产品4<br/>" +
                                    "<font>产品期限：<font class='blue'>2015-12-12</font></font>" +
                                    "<font>产品利率：<font class='red'>3%</font></font>" +
                                "</span>" +
                            "</li>"+
                            "<li onclick='mycpxx()'>" +
                                "<img src='images/cp/5.jpg'/>" +
                                "<span>" +
                                    "产品5<br/>" +
                                    "<font>产品期限：<font class='blue'>2015-12-12</font></font>" +
                                    "<font>产品利率：<font class='red'>3%</font></font>" +
                                "</span>" +
                            "</li>"+
                            "<li onclick='mycpxx()'>" +
                                "<img src='images/cp/6.jpg'/>" +
                                "<span>" +
                                    "产品6<br/>" +
                                    "<font>产品期限：<font class='blue'>2015-12-12</font></font>" +
                                    "<font>产品利率：<font class='red'>3%</font></font>" +
                                "</span>" +
                            "</li>"+
                        "</ul>"+
                    "</div>"+
                    "<div class='buttons'><input type='text' placeholder='搜索'/></div>");
    $(".right").hide();
    $("#cpgl").show();
}
//产品信息
function mycpxx(){
    $("#cpxx").html("<div class='title'>产品1</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td style='width:25%;'>产品类别</td>"+         
                                "<td>XXXXXXXXX</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>产品描述</td>"+            
                                "<td>XXXXXXXXX</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>产品授信区间</td>"+          
                                "<td>XXXXXXXXX</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>产品期限</td>"+            
                                "<td>XXXXXXXXX</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>产品利率</td>"+            
                                "<td>XXXXXXXXX</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>产品还款规则</td>"+          
                                "<td>XXXXXXXXX</td>"+
                            "</tr>"+
                        "</table>"+
                    "</div>"+
                    "<div class='buttons'>" +
                        "<input type='button' class='btn btn-large btn-primary' value='申请' onclick='$(\"#customer\").click();newUser()'/>"+                       
                        "<input type='button' class='btn btn-large' value='返回' onclick='mycpgl()'/>" +
                    "</div>");
    $(".right").hide();
    $("#cpxx").show();
}