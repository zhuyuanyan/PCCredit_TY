//客户经营信息
function khjyxx(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
            "<div class='content' style='margin-top:100px;'>" +
                "<p>" +
                    "<input type='button' class='tab' value='企业基本信息' onclick='qyjbxx()'/>" +
                    "<input type='button' class='tab' value='企业业务信息' onclick='qyywxx()'/>" +
                "</p>"+
                "<p>" +
                    "<input type='button' class='tab' value='企业店铺信息' onclick='qydpxx()'/>" +
                    "<input type='button' class='tab' value='企业开户信息' onclick='qykhxx()'/>" +
                "</p>" +
                "<p>" +
                    "<input type='button' class='tab' value='其他信息' onclick='qyqtxx()'/>" +
                "</p>" +
                "<p><input type='button' class='btn btn-large' value='返回' onclick='khxxzlcj()'/></p>" +
            "</div>");
$(".right").hide();
$("#khgl").show();
}
//企业基本信息
function qyjbxx(){
  $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                  "<div class='content'>"+
                      "<table class='cpTable'>"+
                          "<tr>"+                             
                              "<td>企业名称</td>"+          
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>组织类型</td>"+  
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>法人代表</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>实际控制人</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>股东股份情况</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>营业执照</td>"+           
                              "<td>" +
                                  "<select>" +
                                      "<option>有</option>" +
                                      "<option>无</option>" +
                                  "</select>" +
                               "</td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>经营起始时间</td>"+    
                              "<td><input type='date'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>经营年限</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>地址</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>电话</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                      "</table>"+
                      "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='khjyxx()'/></p>"+
                  "</div>");
  $(".right").hide();
  $("#khgl").show();
}

//企业业务信息
function qyywxx(){
$("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                "<div class='content'>"+
                    "<table class='cpTable'>"+
                        "<tr>"+                             
                            "<td>主要业务范围</td>"+          
                            "<td><input type='text'/></td>"+
                        "</tr>"+
                        "<tr>"+                             
                            "<td>主要经营模式</td>"+  
                            "<td><input type='text'/></td>"+
                        "</tr>"+
                        "<tr>"+                             
                            "<td>组织架构</td>"+    
                            "<td><input type='text'/></td>"+
                        "</tr>"+
                        "<tr>"+                             
                            "<td>业务流程</td>"+    
                            "<td><input type='text'/></td>"+
                        "</tr>"+
                    "</table>"+
                    "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='khjyxx()'/></p>"+
                "</div>");
$(".right").hide();
$("#khgl").show();
}
//企业店铺信息
function qydpxx(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td>营业场所类型</td>"+          
                                "<td>" +
                                    "<select>" +
                                        "<option>自有</option>" +
                                        "<option>自建</option>" +
                                        "<option>住经营场所</option>" +
                                        "<option>租住</option>" +
                                        "<option>其他</option>" +
                                    "</select>" +
                                 "</td>"+
                            "</tr>"+                         
                                "<td>装修情况</td>"+          
                                "<td>" +
                                    "<select>" +
                                        "<option>好</option>" +
                                        "<option>中</option>" +
                                        "<option>差</option>" +
                                        "<option>其他</option>" +
                                    "</select>" +
                                 "</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>住房面积</td>"+  
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>住房格局</td>"+    
                                "<td>" +
                                    "<select>" +
                                        "<option>一室一厅</option>" +
                                        "<option>两室一厅</option>" +
                                        "<option>两室两厅</option>" +
                                        "<option>三室一厅</option>" +
                                        "<option>三室两厅</option>" +
                                    "</select>" +
                                 "</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>启用起始年月</td>"+    
                                "<td><input type='date'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>居住场所调查方式</td>"+    
                                "<td>" +
                                    "<select>" +
                                        "<option>现场调查</option>" +
                                        "<option>外围调查</option>" +
                                        "<option>未调查</option>" +
                                    "</select>" +
                                "</td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='khjyxx()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }
//企业开户信息
function qykhxx(){
  $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                  "<div class='content'>"+
                      "<table class='cpTable'>"+
                          "<tr>"+                             
                              "<td>开户行</td>"+          
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>账号</td>"+  
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>开户行</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>账号</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>开户行</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>账号</td>"+    
                              "<td><input type='date'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>开户行</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                          "<tr>"+                             
                              "<td>账号</td>"+    
                              "<td><input type='text'/></td>"+
                          "</tr>"+
                      "</table>"+
                      "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='khjyxx()'/></p>"+
                  "</div>");
  $(".right").hide();
  $("#khgl").show();
}
//企业其他信息
function qyqtxx(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>"+
                        "<textarea placeholder='请在文本框内记录相关情况' style='width:98%;height:25em;margin:0;border-width:1px 0;border-radiuso:none;'></textarea>"+
                        "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='khjyxx()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
    }