//资产负债表-资产状况
function zczk(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td>现金</td>"+          
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>银行存款</td>"+  
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>应收账款</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>预付账款</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>存货</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>固定资产</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>待摊租金</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>预付押金</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>其他预付款</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>备注</td>"+    
                                "<td><textarea></textarea></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='zcfzb()'/></p>"+
                    "</div>");
$(".right").hide();
$("#khgl").show();
}
//资产负债表-负债情况
function fzqk(){
    $("#khgl").html("<div class='title'>张三&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 320404165620223102</div>"+  
                    "<div class='content'>"+
                        "<table class='cpTable'>"+
                            "<tr>"+                             
                                "<td>应付账款</td>"+          
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>其他应付账款</td>"+  
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>信用卡透支</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>其他机构借款</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>个人借贷</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>长期负债</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>其他负债</td>"+    
                                "<td><input type='text'/></td>"+
                            "</tr>"+
                            "<tr>"+                             
                                "<td>备注</td>"+    
                                "<td><textarea></textarea></td>"+
                            "</tr>"+
                        "</table>"+
                        "<p><input type='button' class='btn' value='返回' onclick='zcfzb()'/></p>"+
                    "</div>");
$(".right").hide();
$("#khgl").show();
}