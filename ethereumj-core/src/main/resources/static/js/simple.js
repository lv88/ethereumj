/**
 * Created by zhou on 16-9-2.
 */
$(document).ready(function(){
    $("#addButton").click(function(){
        addAccount();
    });
    $("#transferButton").click(function(){
        doTransfer();
    });

});

function addAccount() {
    var password=$("#password").val();
    var repeat = $("#repeat").val();
    if(password==""){
        alert("please input password");
        return;
    } else if(password!=repeat){
        alert("两次输入的密码应该相同");
        return;
    }
    $.ajax(
        {
            type:"POST",
            url:"/addAccount/"+password,
            dataType: "text",
            success:function(data){
                $("#newAccoutDiv").innerText=data;
                $("#newAccoutDiv").css("display","block");
            },
            error: function () {
              alert("新增账户失败");
            }
        }
    );
}

function doTransfer() {
    var fromAccount=$("#fromAccount").val();
    var password=$("#password").val();
    var toAccount=$("#toAccount").val();
    var amount = $("#amount").val();
    if(fromAccount==""){
        alert("请输入你的账号");
        return;
    } else if(password==""){
        alert("两次输入的密码应该相同");
        return;
    } else if(toAccount==""){
        alert("请输入对方的帐号");
        return;
    } else if(amount==""){
        alert("请输入转账金额");
        return;
    }
    $.ajax(
        {
            type:"POST",
            url:"/doTransaction/"+fromAccount+"/"+toAccount+"/"+amount+"/"+password,
            success:function(data){
                $("#transferResultDiv").innerText=data;
                $("#transferResultDiv").css("display","block");
            },
            error: function () {
                alert("转账失败");
            }
        }
    );
}