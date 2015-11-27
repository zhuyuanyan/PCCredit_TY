//左侧导航
function changeNav(obj){
    $(".left .nav li").css("background","#364860");
    $(obj).css("background","#2a3950");
}
//表格添加行
function addTd(table){ 
    if(table=="fcz"){//房产证
        var num= $('#fcz tr').length;
        $("#"+table).append("<tr>"+    
                                "<td>"+num+"</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>");      
    }
    if(table=="jhz"){//结婚证
        var num= $('#jhz tr').length;
        $("#"+table).append("<tr>"+    
                                "<td>"+num+"</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>");      
    }
    if(table=="zxbg"){//征信报告
        var num= $('#zxbg tr').length;
        $("#"+table).append("<tr>"+    
                                "<td>"+num+"</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>");      
    }
    if(table=="yhls"){//银行流水
        var num= $('#yhls tr').length;
        $("#"+table).append("<tr>"+    
                                "<td>"+num+"</td>"+
                                "<td><input type='file'/></td>"+
                                "<td><button class='btn btn-success btn-small'><img src='images/ps.png'/></button></td>"+
                            "</tr>");      
    }
  if(table=="gzjh"){//银行流水
      var num= $('#gzjh tr').length;
      $("#"+table).append("<tr>"+    
                              "<td>"+num+"</td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='date' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                              "<td><input type='text' class='addinput'/></td>"+
                          "</tr>");      
  }
}
//表格删除行
function removeTd(table){   
    var tr= document.getElementById(table).getElementsByTagName("tr");
    if(tr.length>1)//至少要保留一行
        document.getElementById(table).deleteRow(tr.length-1);//删除最后一行
       
}
