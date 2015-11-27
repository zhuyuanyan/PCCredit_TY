//我的首页
function mywdsy(){
    $("#wdsy").html("<div class='title'>我的首页</div>"+  
                    "<div class='content' style='padding-left:1em;'>" +
                        "<div class='box box5' onclick='khjjxx()'><img src='images/khjjxx.png'/><span>客户进件信息</span></div>"+
                        "<div class='box box6' onclick='khyyzk()'><img src='images/khyyzk.png'/><span>客户运营状况</span></div>"+
                        "<div class='box box7' onclick='wdzj()'><img src='images/wdzj.png'/><span>我的足迹</span></div>"+
                        "<div class='box box8' onclick='tz()'><img src='images/tz.png'/><span>通知</span></div>"+
                        "<div class='box box9' onclick='jljlxx()'><img src='images/jljlxx.png'/><span>奖励激励信息</span></div>"+
                     "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//客户进件信息
function khjjxx(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td style='width:25%;'>进件数量</td>"+          
                                "<td>10</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>审核通过数量</td>"+  
                                "<td>8</td>"+
                            "</tr>"+
                        "</table>"+
                        "<p>" +
                            "<input type='button' class='btn btn-info btn-large' value='补充进件' onclick='bcjj()'/>" +
                            "<input type='button' class='btn btn-info btn-large' value='拒绝进件' onclick='jjjj()'/>" +
                        "</p>" +
                        "<p><input type='button' class='btn' value='返回' onclick='mywdsy()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//客户进件信息-补充进件
function bcjj(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>接受</th>"+
                                "<th>拒绝</th>"+
                                "<th>是否变更维护计划</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td><input type='button' class='btn btn-warning' value='是'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='khjjxx()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
  }   
  
//客户进件信息-拒绝进件
function jjjj(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+ 
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>接受</th>"+
                                "<th>拒绝</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td>张三</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='khjjxx()'/></p>"+
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
  }   
//客户运营状况
function khyyzk(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td style='width:25%;'>客户授信余额总额</td>"+          
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>客户授信用信总额</td>"+  
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>客户逾期余额总额</td>"+  
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>客户贡献度总额</td>"+  
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>有效客户数</td>"+  
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>逾期客户数</td>"+  
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>核销客户数</td>"+  
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='mywdsy()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//我的足迹
function wdzj(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                        "<div class='map'>地图</div>"+
                        "<p><input type='button' class='btn' value='返回' onclick='mywdsy()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//通知
function tz(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td style='width:25%;'>审贷会通知</td>"+          
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='sdhtz()'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>培训通知</td>"+  
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='pxtz()'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>分配进件通知</td>"+  
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='fpjjtz()'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>风险事项通知</td>"+  
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='fxsxtz()'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>补充调查通知</td>"+ 
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='bcdctz()'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>拒绝进件通知</td>"+ 
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='jjjjtz()'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>催收客户通知</td>"+ 
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='cskhtz()'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>客户资料变更通知</td>"+
                                "<td>1<input type='button' class='btn btn-info' value='查看' onclick='khzlbgtz()'/></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='mywdsy()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//通知-审贷会通知
function sdhtz(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td style='width:25%;'>审贷会时间</td>"+          
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>审贷会地点</td>"+  
                                "<td></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>审贷会进件提示</td>"+  
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p>" +
                            "<input type='button' class='btn btn-info btn-large' value='确认' onclick=''/>" +
                            "<input type='button' class='btn btn-danger btn-large' value='拒绝' onclick=''/>" +
                        "</p>" +
                        "<p><input type='button' class='btn' value='返回' onclick='tz()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//通知-培训通知
function pxtz(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                       "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>培训目标</th>"+
                                "<th>培训方式</th>"+
                                "<th>培训时间</th>"+
                                "<th>考核方式</th>"+
                                "<th>客户经理确认</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='tz()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//通知-分配进件通知
function fpjjtz(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                       "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>接受</th>"+
                                "<th>拒绝</th>"+
                                "<th>是否变更维护计划</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='tz()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//通知-风险事项通知
function fxsxtz(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                       "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>风险事项描述</th>"+
                                "<th>是否变更维护计划</th>"+
                                "<th>是否变更工作计划</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='tz()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//通知-催收客户通知
function cskhtz(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                       "<table class='cpTable'>"+
                            "<tr>"+                        
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>产品标识</th>"+
                                "<th>逾期金额</th>"+
                                "<th>逾期期数</th>"+
                                "<th>是否变更维护计划</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='tz()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}
//通知-客户资料变更通知
function khzlbgtz(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                       "<table class='cpTable'>"+
                            "<tr>"+                        
                                "<th>序号</th>"+  
                                "<th>客户姓名</th>"+
                                "<th>客户身份标识</th>"+
                                "<th>产品标识</th>"+
                                "<th>变更项</th>"+
                                "<th>是否变更维护计划</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                                "<td></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='tz()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}

//奖励激励信息
function jljlxx(){
    $("#wdsy").html("<div class='title'>刘翔客户经理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00001</div>"+  
                    "<div class='content'>" +
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td style='width:25%;'>奖励激励信息</td>"+          
                                "<td>10</td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>风险保证金余额</td>"+  
                                "<td>8</td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='mywdsy()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#wdsy").show();
}