//登录
function mylogin(){
    $("#login").html("<div class='loginBox'>"+
                        "<form class='login_form' action='index.html' method='post'>"+
                            "<img src='images/login.png'/>"+                
                            "<div class='login'>"+
                                "<h1>PAD</h1>"+
                                "<input type='text' placeholder='用户名' style='background-image:url(\"images/login1.png\");' />"+
                                "<input type='password' placeholder='密码' style='background-image:url(\"images/login2.png\");'/>"+
                                "<!--错误信息提示--> "+
                                "<span class='errorMessage'>"+
                                    "<img src='images/error.png'/>&nbsp;用户名或密码错误！"+
                                "</span>"+
                                "<!--/错误信息提示--> "+
                                "<input type='button' class='login_btn' value='登  录' onclick='dl()'>"+
                            "</div>"+
                        "</form>"+
                    "</div>");
    $(".right").hide();
    $("#login").show();
}
//登录
function dl(){
    $("#page1").hide();
    $("#page2").show();
    $("#login").hide();
    $("#cp").show();
}
//登出
function dc(){
    $("#page2").hide();
    $("#page1").show();
    $(".right").hide();
    $("#cp").show();
}