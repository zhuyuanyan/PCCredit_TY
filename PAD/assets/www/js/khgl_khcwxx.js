//客户财务信息
function khcwxx(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content' style='margin-top:100px;'>" +
                        "<p>" +
                            "<input type='button' class='btn btn-info btn-large' value='资产负债表' onclick='zcfzb()'/>" +
                            "<input type='button' class='btn btn-info btn-large' value='损益表' onclick='syb()'/>" +
                        "</p>"+
                        "<p>" +
                            "<input type='button' class='btn btn-info btn-large' value='现金流表' onclick='xjlb()'/>" +
                            "<input type='button' class='btn btn-info btn-large' value='点货单' onclick='dhd()'/>" +
                        "</p>" +
                        "<p>" +
                            "<input type='button' class='btn btn-info btn-large' value='固定资产清单' onclick='gdzcqd()'/>" +
                            "<input type='button' class='btn btn-info btn-large' value='应收预付清单' onclick='ysyfqd()'/>" +
                        "</p>" +
                        "<p>" +
                            "<input type='button' class='btn btn-info btn-large' value='应付预收清单' onclick='yfysqd()'/>" +
                            "<input type='button' class='btn btn-info btn-large' value='负债项目明细清单' onclick='fzxmmxqd()'/>" +
                        "</p>" +
                        "<p><input type='button' class='btn' value='返回' onclick='khxxzlcj()'/></p>" +
                    "</div>");
$(".right").hide();
$("#khgl").show();
}
//资产负债表
function zcfzb(){
  $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                  "<div class='content' style='margin-top:100px;'>"+
                      "<p>" +
                          "<input type='button' class='btn btn-info btn-large' value='资产状况' onclick='zczk()'/>" +
                          "<input type='button' class='btn btn-info btn-large' value='负债情况' onclick='fzqk()'/>" +
                      "</p>"+
                      "<p>" +
                          "<input type='button' class='btn btn-info btn-large' value='权益状况' onclick='qyzk()'/>" +
                          "<input type='button' class='btn btn-info btn-large' value='其他信息' onclick='zcfzqtxx()'/>" +
                      "</p>" +
                      "<p><input type='button' class='btn' value='返回' onclick='khcwxx()'/></p>"+
                  "</div>");
  $(".right").hide();
  $("#khgl").show();
}
//损益表
function syb(){
  $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                  "<div class='content' style='margin-top:100px;'>"+
                      "<p>" +
                          "<input type='button' class='btn btn-info btn-large' value='利润表简表' onclick='lrbjb()'/>" +
                      "</p>"+
                      "<p>" +                          
                          "<input type='button' class='btn btn-info btn-large' value='利润表标准表' onclick='lrbbzb()'/>" +
                      "</p>"+
                      "<p>" +                          
                          "<input type='button' class='btn btn-info btn-large' value='其他信息' onclick='syqtxx()'/>" +
                      "</p>" +
                      "<p><input type='button' class='btn' value='返回' onclick='khcwxx()'/></p>"+
                  "</div>");
  $(".right").hide();
  $("#khgl").show();
}   
//点货单
function dhd(){
  $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                  "<div class='content'>"+
                      "<table class='cpTable'>"+
                          "<tr>"+                             
                              "<th colspan='5'>点货时间<input type='date'/><input type='time'/></th>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<th>货物名称</th>"+  
                              "<th>数量</th>"+
                              "<th>买进单价</th>"+
                              "<th>买进总价</th>"+
                              "<th>卖出总价</th>"+
                          "</tr>"+
                          "<tr>"+    
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                          "</tr>"+
                      "</table>"+
                      "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='khcwxx()'/></p>"+
                  "</div>");
  $(".right").hide();
  $("#khgl").show();
}   
//固定资产清单
function gdzcqd(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th colspan='8'>点货时间<input type='date'/><input type='time'/></th>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>名称</th>"+
                                "<th>购置时间</th>"+
                                "<th>原始单价</th>"+
                                "<th>折旧比率</th>"+
                                "<th>数量</th>"+
                                "<th>总价</th>"+
                                "<th>折后价</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='khcwxx()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }   
//应收预付清单
function ysyfqd(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th colspan='8'>点货时间<input type='date'/><input type='time'/></th>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>应收/预付对方名</th>"+
                                "<th>交易时间</th>"+
                                "<th>到期时间</th>"+
                                "<th>应收/预付金额</th>"+
                                "<th>对方联系方式</th>"+
                                "<th>原因</th>"+
                                "<th>关系/生意模式</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='khcwxx()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }   
//应付预收清单
function yfysqd(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th colspan='8'>点货时间<input type='date'/><input type='time'/></th>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>应付/预收对方名</th>"+
                                "<th>交易时间</th>"+
                                "<th>到期时间</th>"+
                                "<th>应付/预收金额</th>"+
                                "<th>对方联系方式</th>"+
                                "<th>原因</th>"+
                                "<th>关系/生意模式</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='khcwxx()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }   

//负债项目明细清单
function fzxmmxqd(){
  $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                  "<div class='content'>"+
                      "<table class='cpTable'>"+
                          "<tr>"+                             
                              "<th>序号</th>"+  
                              "<th>欠款对象</th>"+
                              "<th>欠款金额</th>"+
                              "<th>余额</th>"+
                              "<th>期限</th>"+
                              "<th>用途</th>"+
                              "<th>欠款日期</th>"+
                              "<th>还款日期</th>"+
                              "<th>逾期利息</th>"+
                              "<th>担保方式</th>"+
                          "</tr>"+
                          "<tr>"+    
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='date' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='date' class='addinput'/></td>"+
                              "<td><input type='date' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                          "</tr>"+
                      "</table>"+
                      "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='khcwxx()'/></p>"+
                  "</div>");
  $(".right").hide();
  $("#khgl").show();
}   