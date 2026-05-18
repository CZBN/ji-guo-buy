$(function () {
    $("#loginBtn").on("click", function () {
        let name = $("#username").val();
        let pass = $("#password").val();
        
        // 检查输入是否为空
        if (!name || !pass) {
            $(".modal .modal-body p").html("用户名和密码不能为空");
            $(".modal").modal("show");
            return;
        }
        
        // 添加登录按钮禁用和加载提示
        $("#loginBtn").prop("disabled", true).text("登录中...");
        
        // 发送登录请求
        $.ajax({
            url: "/admin/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                name: name,
                password: pass
            }),
            success: function(rt) {
                if(rt.code !== 0) {
                    $(".modal .modal-body p").html(rt.message);
                    $(".modal").modal("show");
                    // 恢复登录按钮
                    $("#loginBtn").prop("disabled", false).text("登录");
                } else {
                    $(".modal .modal-body p").html("登录成功，正在跳转...");
                    $(".modal").modal("show");
                    setTimeout(function() {
                        location.href = "home.html";
                    }, 1000);
                }
            },
            error: function() {
                $(".modal .modal-body p").html("网络错误，请稍后再试");
                $(".modal").modal("show");
                // 恢复登录按钮
                $("#loginBtn").prop("disabled", false).text("登录");
            }
        });
    });
    
    $(".btn-danger").on("click", function() {
        $("#username").val("");
        $("#password").val("");
    });
    
    // 添加回车键登录支持
    $("#password").on("keyup", function(event) {
        if (event.key === "Enter") {
            $("#loginBtn").click();
        }
    });
    
    // 添加用户名输入框的回车键支持
    $("#username").on("keyup", function(event) {
        if (event.key === "Enter") {
            $("#password").focus();
        }
    });
});