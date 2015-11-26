//客户维护-客户资料查询
function khzlcx(){
    $("#khgl").html("<div class='title'>客户管理-客户资料查询</div>"+  
                    "<div class='content' style='margin-top:100px;'>" +
                        "<p>客户姓名:<input type='text'/></p>"+
                        "<p>证件类型:<select><option>身份证</option></select></p>"+
                        "<p>证件号码:<input type='text'/></p>"+
                        "<p>" +
                            "<input type='button' class='btn btn-info' value='查询' onclick='khcx()'/>"+                       
                            "<input type='button' class='btn' value='返回' onclick='editUser()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户维护-客户资料查询-查询
function khcx(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>" +
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td style='width:25%;'>贷款进度</td>"+          
                                "<td>审核中</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>还款状态</td>"+  
                                "<td>已放款</td>"+
                            "</tr>"+
                        "</table>"+
                        "<p>" +
                            "<input type='button' class='btn btn-info' value='客户资料查询' onclick=''/>"+                       
                            "<input type='button' class='btn' value='返回' onclick='khzlcx()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}

//客户维护-客户维护计划
function khwhjh(){
    $("#khgl").html("<div class='title'>客户维护计划</div>"+  
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
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='editUser()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }   
//客户维护-客户维护日志
function khwhrz(){
    $("#khgl").html("<div class='title'>客户维护日志</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>维护方式</th>"+
                                "<th>维护时间</th>"+
                                "<th>实施效果</th>"+
                                "<th>是否变更维护计划</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td>2015-12-12</td>"+
                                "<td></td>"+
                                "<td><input type='button' onclick='khwhjh()' class='btn btn-small btn-warning' value='是'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<textarea placeholder='客户维护实施描述' style='width:98%;height:15em;margin:0;border-width:1px 0;border-radius:none;'></textarea>"+
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='editUser()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }   
//客户维护-客户催收日志
function khcsrz(){
    $("#khgl").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>催收方式</th>"+
                                "<th>催收时间</th>"+
                                "<th>实施效果</th>"+
                                "<th>承诺还款金额</th>"+
                                "<th>承诺还款时间</th>"+
                                "<th>是否变更催收计划</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td>电话</td>"+
                                "<td>2015-12-12</td>"+
                                "<td></td>"+
                                "<td>3000</td>"+
                                "<td>2015-12-12</td>"+
                                "<td><input type='button' onclick='bgcsjh()' class='btn btn-small btn-warning' value='是'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<textarea placeholder='客户催收实施描述' style='width:98%;height:15em;margin:0;border-width:1px 0;border-radius:none;'></textarea>"+
                        "<p><input type='button' class='btn' value='返回' onclick='editUser()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }   
//变更催收计划
function bgcsjh(){
    $("#khgl").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
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
                        "<p><input type='button' class='btn btn-info' value='保存并继续' onclick='editUser()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
  }   