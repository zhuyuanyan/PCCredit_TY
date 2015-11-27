//我的计划
function mywdjh(){
    $("#wdjh").html("<div class='title'>我的计划</div>"+  
                    "<div class='content' style='padding-left:1em;'>" +
                        "<div class='box box10' onclick='mykhwhjh()'><img src='images/khwhjh.png'/><span>客户维护计划</span></div>"+
                        "<div class='box box11' onclick='khcsjh()'><img src='images/khcsjh.png'/><span>客户催收计划</span></div>"+
                        "<div class='box box12' onclick='pxjh()'><img src='images/pxjh.png'/><span>培训计划</span></div>"+
                        "<div class='box box2' onclick='gzjh()'><img src='images/gzjh.png'/><span>工作计划</span></div>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
}
//客户维护计划
function mykhwhjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable' style='text-align:center;'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>产品标识</th>"+
                                "<th>贷款金额</th>"+
                                "<th>还款状态</th>"+
                                "<th>贷款余额</th>"+
                                "<th width='10%'>维护方式</th>"+
                                "<th width='10%'>维护目标</th>"+
                                "<th>维护时间</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td><span class='label'>还款中</span></td>"+
                                "<td>50000</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td><span class='label label-warning'>已逾期</span></td>"+
                                "<td>50000</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td><span class='label label-success'>已还款</span></td>"+
                                "<td>50000</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td><span class='label label-important'>已拒绝</span></td>"+
                                "<td>50000</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td><span class='label label-inverse'>已关闭</span></td>"+
                                "<td>50000</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   
//客户催收计划
function khcsjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable' style='text-align:center;'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>产品标识</th>"+
                                "<th>贷款金额</th>"+
                                "<th>逾期金额</th>"+
                                "<th>逾期期数</th>"+
                                "<th width='10%'>催收方式</th>"+
                                "<th width='10%'>催收目标</th>"+
                                "<th>催收时间</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>100000</td>"+
                                "<td><font class='red'>3000<font></td>"+
                                "<td>1</td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   
//培训计划
function pxjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable' style='text-align:center;'>"+
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
                                "<td><input type='button' class='btn btn-large btn-info' value='确认'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn btn-large' value='返回' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   
  //工作计划
function gzjh(){
    $("#wdjh").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table id='gzjh' class='cpTable' style='text-align:center;'>"+
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
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='date' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                                "<td><input type='text' class='addinput'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p class='Left'>" +
                            "<button class='btn btn-info btn-small' onclick='addTd(\"gzjh\")'><img src='images/add.png'/></button>" +
                            "<button class='btn btn-info btn-small' onclick='removeTd(\"gzjh\")'><img src='images/del.png'/></button>" +
                        "</p>"+
                        "<p><input type='button' class='btn btn-large btn-primary' value='保存并继续' onclick='mywdjh()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdjh").show();
  }   