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
                            "<input type='button' class='btn btn-large btn-primary' value='确定' onclick='newUser1()'/>"+                       
                            "<input type='button' class='btn btn-large' value='返回' onclick='mykhgl()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//新建客户1
function newUser1(){
    $("#khgl").html("<div class='title'>客户管理-新建客户</div>"+  
                    "<div class='content' style='padding-left:1em;'>" +
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
                        "<p><input type='button' class='tab' value='客户基本信息' onclick='khjbxx()'/></p>"+
                        "<p><input type='button' class='tab' value='客户经营信息' onclick='khjyxx()'/></p>"+
                        "<p><input type='button' class='tab' value='客户财务信息' onclick='khcwxx()'/></p>"+
                        "<p><input type='button' class='tab' value='客户其他信息' onclick='khqtxx()'/></p>" +
                        "<p><input type='button' class='btn btn-large' value='返回' onclick='newUser1()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户影像资料采集
function khyxzlcj(){
    $("#khgl").html("<div class='title'>影像资料采集</div>"+  
                    "<div class='content' style='margin-top:100px;'>" +
                        "<p><input type='button' class='tab' value='房产证' onclick='fcz()'/></p>"+
                        "<p><input type='button' class='tab' value='结婚证' onclick='jhz()'/></p>"+
                        "<p><input type='button' class='tab' value='征信报告' onclick='zxbg()'/></p>"+
                        "<p><input type='button' class='tab' value='银行流水' onclick='yhls()'/></p>" +
                        "<p><input type='button' class='btn btn-large' value='返回' onclick='newUser1()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户影像资料采集-房产证
function fcz(){
    $("#khgl").html("<div class='title'>影像资料采集</div>"+  
                    "<div class='content'>" +
                        "<table id='fcz' class='cpTable' style='text-align:center;'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>房产证文件路径</th>"+
                                "<th>操作</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p class='Left'>" +
                            "<button class='btn btn-info btn-small' onclick='addTd(\"fcz\")'><img src='images/add.png'/></button>" +
                            "<button class='btn btn-info btn-small' onclick='removeTd(\"fcz\")'><img src='images/del.png'/></button>" +
                        "</p>"+
                        "<p>" +
                            "<input type='button' class='btn btn-large btn-primary' value='确定'/>" +
                            "<input type='button' class='btn btn-large' value='返回' onclick='khyxzlcj()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户影像资料采集-结婚证
function jhz(){
    $("#khgl").html("<div class='title'>影像资料采集</div>"+  
                    "<div class='content'>" +
                        "<table id='jhz' class='cpTable' style='text-align:center;'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>结婚证文件路径</th>"+
                                "<th>操作</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p class='Left'>" +
                            "<button class='btn btn-info btn-small' onclick='addTd(\"jhz\")'><img src='images/add.png'/></button>" +
                            "<button class='btn btn-info btn-small' onclick='removeTd(\"jhz\")'><img src='images/del.png'/></button>" +
                        "</p>"+
                        "<p>" +
                            "<input type='button' class='btn btn-large btn-primary' value='确定'/>" +
                            "<input type='button' class='btn btn-large' value='返回' onclick='khyxzlcj()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户影像资料采集-征信报告
function zxbg(){
    $("#khgl").html("<div class='title'>影像资料采集</div>"+  
                    "<div class='content'>" +
                        "<table id='zxbg' class='cpTable' style='text-align:center;'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>征信报告文件路径</th>"+
                                "<th>操作</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p class='Left'>" +
                            "<button class='btn btn-info btn-small' onclick='addTd(\"zxbg\")'><img src='images/add.png'/></button>" +
                            "<button class='btn btn-info btn-small' onclick='removeTd(\"zxbg\")'><img src='images/del.png'/></button>" +
                        "</p>"+
                        "<p>" +
                            "<input type='button' class='btn btn-large btn-primary' value='确定'/>" +
                            "<input type='button' class='btn btn-large' value='返回' onclick='khyxzlcj()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户影像资料采集-银行流水
function yhls(){
    $("#khgl").html("<div class='title'>影像资料采集</div>"+  
                    "<div class='content'>" +
                        "<table id='yhls' class='cpTable' style='text-align:center;'>"+
                            "<tr>"+                             
                                "<th>序号</th>"+  
                                "<th>银行流水文件路径</th>"+
                                "<th>操作</th>"+
                            "</tr>"+
                            "<tr>"+    
                                "<td>1</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p class='Left'>" +
                            "<button class='btn btn-info btn-small' onclick='addTd(\"yhls\")'><img src='images/add.png'/></button>" +
                            "<button class='btn btn-info btn-small' onclick='removeTd(\"yhls\")'><img src='images/del.png'/></button>" +
                        "</p>"+
                        "<p>" +
                            "<input type='button' class='btn btn-large btn-primary' value='确定'/>" +
                            "<input type='button' class='btn btn-large' value='返回' onclick='khyxzlcj()'/>" +
                        "</p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}
//客户维护
function editUser(){
    $("#khgl").html("<div class='title'>客户管理-客户维护</div>"+  
                    "<div class='content' style='margin-top:100px;'>" +
                        "<p><input type='button' class='tab' value='客户资料查询' onclick='khzlcx()'/></p>"+
                        "<p><input type='button' class='tab' value='客户维护计划' onclick='khwhjh()'/></p>"+
                        "<p><input type='button' class='tab' value='客户维护日志' onclick='khwhrz()'/></p>"+
                        "<p><input type='button' class='tab' value='客户催收日志' onclick='khcsrz()'/></p>" +
                        "<p><input type='button' class='btn btn-large' value='返回' onclick='mykhgl()'/></p>" +
                    "</div>");
    $(".right").hide();
    $("#khgl").show();
}