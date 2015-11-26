//我的计划
function mywdjh(){
    $("#wdjh").html("<div class='title'>我的计划</div>"+  
                    "<div class='content' style='margin-top:100px;padding-left:1em;'>" +
                        "<p><input type='button' class='btn btn-info btn-large' value='客户维护计划' onclick='mykhwhjh()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='客户催收计划' onclick='khcsjh()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='培训计划' onclick='pxjh()'/></p>"+
                        "<p><input type='button' class='btn btn-info btn-large' value='工作计划' onclick='gzjh()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
}
//客户维护计划
function mykhwhjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>产品标识</th>"+
                                "<th>贷款金额</th>"+
                                "<th>还款状态</th>"+
                                "<th>贷款余额</th>"+
                                "<th>维护方式</th>"+
                                "<th>维护目标</th>"+
                                "<th>维护时间</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td>还款中</td>"+
                                "<td>50000</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   
//客户催收计划
function khcsjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>产品标识</th>"+
                                "<th>贷款金额</th>"+
                                "<th>逾期金额</th>"+
                                "<th>逾期期数</th>"+
                                "<th>催收方式</th>"+
                                "<th>催收目标</th>"+
                                "<th>催收时间</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td>3000</td>"+
                                "<td>1</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   
//培训计划
function pxjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户经理姓名</th>"+
                                "<th>培训目标</th>"+
                                "<th>培训方式</th>"+
                                "<th>培训时间</th>"+
                                "<th>考核方式</th>"+
                                "<th>客户经理确认</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>刘翔客户经理</td>"+
                                "<td>微贷知识</td>"+
                                "<td></td>"+
                                "<td>2015-12-12</td>"+
                                "<td>笔试</td>"+
                                "<td><input type='button' class='btn btn-info' value='确认'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   
  //工作计划
function gzjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>工作事项描述</th>"+
                                "<th>地点</th>"+
                                "<th>时间</th>"+
                                "<th>实施状态</th>"+
                                "<th>实施描述</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   