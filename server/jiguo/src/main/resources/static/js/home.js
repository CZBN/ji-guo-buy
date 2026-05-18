$(function(){
    let currentAdmin=null;
    let pageNo=1;

    let type="name";
    let key="";
    // 获取管理员信息
    $.get("/admin/info", (rt) => {
        if (rt.code !== 0) {
            alert(rt.message);
            location.href = "index.html";
        } else {
            currentAdmin = rt.data;
            if (currentAdmin.state == 1) {
                $(".admin-link").remove();
                $(".list-group a:contains('用户管理')").click();
            } else {
                $(".list-group a:contains('管理员管理')").click();
            }
            $(".admin-name").html(currentAdmin.name);
        }
    });

    // 退出登录
    $(".btn-exit").on("click", () => {
        $.get("/admin/logoff", () => {
            location.href = "index.html";
        });
    });

    // 侧边栏导航切换
    $(".list-group a").on("click", function () {
        $(".admin-content>div").hide();
        $(".list-group a").removeClass("active");
        $(this).addClass("active");
        let op = $(this).html();
        switch (op) {
            case "用户管理":
                pageNo = 1;
                $("#user").show();
                $(".user-link").addClass("active");
                listUsers();
                break;
            case "管理员管理":
                $("#admin").show();
                $(".admin-link").addClass("active");
                listAdmins();
                break;
            case "酷玩管理":
                pageNo=1;
                $("#cool").show();
                $(".cool-link").addClass("active");
                $(".cool-form").hide();
                listCoolItems();
                break;
            case "导购管理":
                pageNo = 1;
                $("#guide").show();
                $(".guide-link").addClass("active");
                listGuideItems();
                break;
            case "试用管理":
                pageNo = 1;
                $("#try").show();
                $(".try-link").addClass("active");
                break;
        }
    });

    function listCoolItems(){
        $(".cool-tbody").empty();
        key=$("[name=coolname]").val().trim();
        $.get("oper/cool/search",{pno:pageNo,type:type,key:key},(rt)=>{
            if(rt.code){
                alert(rt.message);
            }
            else{
                let cools=rt.data.list;
                for(let i=0;i<cools.length;i++){
                    addCoolItemToTbody(cools[i]);
                }
                modifyCoolPagination(rt.data);
            }
        })
    }

    function addCoolItemToTbody(item){
        let tr=createCoolTr(item);
        $(".cool-tbody").append(tr);
    }

    $("[name=cool-search-type]").on("change",function(){
        let value=$(this).val();
        type=value;
        if(type=="name"){
            $("[name=coolname]").attr("placeholder","请输入搜索的商品名");
        }
        else{
            $("[name=coolname]").attr("placeholder","请输入搜索的商品标题");
        }
    });

    $(".btn-cool-search").on("click",function(){
        pageNo=1;
        listCoolItems();
    });

    function createCoolTr(item){
        let tr=$("<tr>").addClass("cool-item-"+item.id);
        $("<td>").html(item.id).appendTo(tr);
        $("<td>").html(item.name).appendTo(tr);
        $("<td>").html(item.title).appendTo(tr);
        $("<td>").html(item.price).appendTo(tr);
        // 显示图片，使用/image/前缀访问图片
        $("<td>").html($("<img>").attr("src", "/image/" + item.image).attr("width", "50").attr("height", "50")).appendTo(tr);
        $("<td>").html(item.pubDate).appendTo(tr);
        let td=$("<td>").appendTo(tr);
        $("<button>").data("item",item).addClass("btn btn-xs btn-success btn-modify").html("修改").appendTo(td);
        $("<button>").data("id",item.id).addClass("btn btn-xs btn-danger btn-delete").html("删除").appendTo(td);
        return tr;
    }

    $("#cool .btn-show-form").on("click",function () {
        $(".cool-form").toggle();
        $("[name=id]").hide();
        $("#cool .btn-show-form").html("添加酷玩商品");
        // 清空表单
        $("#cool .cool-item-form")[0].reset();
    });

    $("#cool").on("click",".btn-modify",function(){
        let item=$(this).data("item");
        $("#cool .cool-form").show();
        $("#cool .btn-show-form").html("修改酷玩商品");
        $(".cool-item-form [name=id]").show().val(item.id);
        $(".cool-item-form [name=name]").val(item.name);
        $(".cool-item-form [name=title]").val(item.title);
        $(".cool-item-form [name=price]").val(item.price);
        $(".cool-item-form [name=pubDate]").val(item.pubDate);
    });

    $("#cool").on("click",".btn-delete",function(){
        let id=$(this).data("id");
        if(confirm("确定要删除这个酷玩商品吗？")) {
            $.post("oper/cool/delete/"+id, (ret)=>{
                if(ret.code){
                    alert(ret.message);
                }
                else{
                    $(".cool-item-"+id).remove();
                    alert("删除成功！");
                }
            });
        }
    });

    $("#cool .btn-add").on("click",function(){
        let op=$("#cool .btn-show-form").html();
        let formData = new FormData($(".cool-item-form")[0]);
        
        let url = "oper/cool/add";
        if(op=="修改酷玩商品"){
            url = "oper/cool/modify";
        }
        
        $.ajax(url, {
            type: "post",
            processData: false,
            contentType: false,
            data: formData,
            success: (ret) => {
                if(ret.code){
                    alert(ret.message);
                }
                else{
                    if(op=="修改酷玩商品") {
                        let newTr=createCoolTr(ret.data);
                        $(".cool-item-"+ret.data.id).replaceWith(newTr);
                        alert("修改成功！");
                        $("#cool .btn-show-form").html("添加酷玩商品");
                        $("#cool .cool-item-form")[0].reset();
                        $(".cool-form").hide();
                    }
                    else{
                        addCoolItemToTbody(ret.data);
                        alert("添加成功！");
                        $("#cool .cool-item-form")[0].reset();
                    }
                }
            }
        })
    });

    $("#cool .pagination").on("click","a",function(){
        let pno=$(this).data("pno");
        if(pno) {
            pageNo=pno;
            listCoolItems();
        }
    });

    function modifyCoolPagination(pager){
        let pg=$("#cool .pagination");
        pg.empty();
        let li=$("<li>");
        let a=$("<a>").appendTo(li).html("&laquo;");
        if(pager.isFirstPage){
            li.addClass("disabled");
            a.data("pno",null);//附加数据，当禁用时仅仅只是外观变化，并不影响点击事件的发生，因此通过不提供数据让动态事件绑定代码中进行判断是否处理点击事件
        }
        else{
            a.data("pno",pager.prePage);
        }
        pg.append(li);
        for(let i=0;i<pager.navigatepageNums.length;i++){
            li=$("<li>");
            a=$("<a>").appendTo(li).html(pager.navigatepageNums[i]+"");
            if(pager.pageNum==pager.navigatepageNums[i]){
                li.addClass("active");
                a.data("pno",null);
            }
            else{
                a.data("pno",pager.navigatepageNums[i]);
            }
            pg.append(li);
        }
        li=$("<li>");
        a=$("<a>").appendTo(li).html("&raquo;");
        if(pager.isLastPage){
            li.addClass("disabled");
            a.data("pno",null);
        }
        else{
            a.data("pno",pager.nextPage);
        }
        pg.append(li);
    }

    // 管理员管理相关功能
    function listAdmins() {
        $(".admin-tbody").empty();
        $.get("/oper/admin/list", (rt) => {
            if (rt.code) {
                alert(rt.message);
            } else {
                // 修复：正确处理管理员列表数据
                rt.data.forEach(admin => {
                    addAdminToTbody(admin);
                });
            }
        })
    }

    function addAdminToTbody(admin) {
        let tr = createAdminTr(admin);
        //append:父加子
        $(".admin-tbody").append(tr);
    }

    function createAdminTr(admin) {
        //创建：<标签名>
        let tr = $("<tr>");
        //appendTo:子加到父
        $("<td>").html(admin.id).appendTo(tr);
        $("<td>").html(admin.name).appendTo(tr);
        $("<td>").html(admin.state == 0 ? "已删除" : "正常").appendTo(tr);
        let td = $("<td>").appendTo(tr);
        let btn = $("<button>").addClass("btn btn-xs").appendTo(td);
        if (admin.state != 0) {
            btn.addClass("btn-danger").html("删除").on("click", function () {
                $.ajax({
                    url: "/oper/admin/delete/" + admin.id,
                    type: "POST",
                    success: (rt) => {
                        if (rt.code) {
                            alert(rt.message);
                        } else {
                            let newTr = createAdminTr({id: admin.id, name: admin.name, state: 0});
                            let oldTr = $(this).parents("tr");
                            oldTr.replaceWith(newTr);
                            alert("删除成功！");
                        }
                    }
                })
            });
        } else {
            btn.addClass("btn-success").html("恢复").on("click", function () {
                $.ajax({
                    url: "/oper/admin/recover/" + admin.id,
                    type: "POST",
                    success: (rt) => {
                        if (rt.code) {
                            alert(rt.message);
                        } else {
                            let newTr = createAdminTr({id: admin.id, name: admin.name, state: 1});
                            let oldTr = $(this).parents("tr");
                            oldTr.replaceWith(newTr);
                            alert("恢复成功！");
                        }
                    }
                })
            });
        }
        return tr;
    }

    // 修改这里：使用更具体的选择器，只针对管理员添加按钮
    $(".admin-form .btn-add").on("click", () => {
        let adminName = $("[name=adminname]").val();
        let adminPass = $("[name=adminpass]").val();
        
        // 检查输入是否为空
        if (!adminName || !adminPass) {
            alert("管理员名称和密码不能为空");
            return;
        }
        
        $.ajax({
            url: "/oper/admin/add",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                name: adminName,
                password: adminPass,
                state: 1
            }),
            success: (rt) => {
                if (rt.code) {
                    alert(rt.message);
                } else {
                    alert("添加成功！");
                    // 修复：正确添加新管理员到表格
                    addAdminToTbody(rt.data);
                    // 清空输入框
                    $("[name=adminname]").val("");
                    $("[name=adminpass]").val("");
                }
            },
            error: function() {
                alert("网络错误，请稍后再试");
            }
        });
    });

    // 用户管理相关功能
    function listUsers() {
        let key = $("[name=username]").val().trim();
        $.get("oper/user/search", {name: key, size: 5, pno: pageNo}, (rt) => {
            if (rt.code) {
                alert(rt.message);
            } else {
                $(".user-tbody").empty();
                let users = rt.data.list;
                for (let i = 0; i < users.length; i++) {
                    addUserToTbody(users[i]);
                }
                modifyPagination(rt.data);
            }
        })
    }

    // 添加这一部分：用户搜索按钮点击事件
    $(".btn-warning").on("click", function() {
        pageNo = 1;
        listUsers();
    });

    $(".btn-user-search").on("click",function(){
        pageNo=1;
        listUsers();
    });


    $("#user .pagination").on("click","a",function(e){
        e.preventDefault(); // Prevent default anchor behavior
        let pno=$(this).data("pno");
        if(pno) {
            pageNo=pno;
            listUsers();
        }
    });

    function modifyPagination(pager){
        let pg=$(".pagination");
        pg.empty();
        let li=$("<li>");
        let a=$("<a>").appendTo(li).html("&laquo;");
        if(pager.isFirstPage){
            li.addClass("disabled");
            a.data("pno",null);//附加数据，当禁用时仅仅只是外观变化，并不影响点击事件的发生，因此通过不提供数据让动态事件绑定代码中进行判断是否处理点击事件
        }
        else{
            a.data("pno",pager.prePage);
        }
        pg.append(li);
        for(let i=0;i<pager.navigatepageNums.length;i++){
            li=$("<li>");
            a=$("<a>").appendTo(li).html(pager.navigatepageNums[i]+"");
            if(pager.pageNum==pager.navigatepageNums[i]){
                li.addClass("active");
                a.data("pno",null);
            }
            else{
                a.data("pno",pager.navigatepageNums[i]);
            }
            pg.append(li);
        }
        li=$("<li>");
        a=$("<a>").appendTo(li).html("&raquo;");
        if(pager.isLastPage){
            li.addClass("disabled");
            a.data("pno",null);
        }
        else{
            a.data("pno",pager.nextPage);
        }
        pg.append(li);
    }

    // 导购管理相关功能
    function listGuideItems(){
        $(".guide-tbody").empty();
        $.get("guideitem/search",{pno:pageNo,size:10},(rt)=>{
            if(rt.code){
                alert(rt.message);
            }
            else{
                let guides=rt.data.list;
                for(let i=0;i<guides.length;i++){
                    addGuideItemToTbody(guides[i]);
                }
                modifyGuidePagination(rt.data);
            }
        })
    }

    function addGuideItemToTbody(item){
        let tr=createGuideTr(item);
        $(".guide-tbody").append(tr);
    }

    function createGuideTr(item){
        let tr=$("<tr>").addClass("guide-item-"+item.id);
        $("<td>").html(item.id).appendTo(tr);
        $("<td>").html(item.name).appendTo(tr);
        $("<td>").html(item.title).appendTo(tr);
        $("<td>").html(item.price).appendTo(tr);
        // 显示图片，使用/image/前缀访问图片
        $("<td>").html($("<img>").attr("src", "/image/" + item.image).attr("width", "50").attr("height", "50")).appendTo(tr);
        $("<td>").html(item.pubDate).appendTo(tr);
        let td=$("<td>").appendTo(tr);
        $("<button>").data("item",item).addClass("btn btn-xs btn-success btn-modify").html("修改").appendTo(td);
        $("<button>").data("id",item.id).addClass("btn btn-xs btn-danger btn-delete").html("删除").appendTo(td);
        return tr;
    }

    $("#guide .btn-show-form").on("click",function () {
        $(".guide-form").toggle();
        $("[name=id]").hide();
        $("#guide .btn-show-form").html("添加导购商品");
        // 清空表单
        $("#guide .guide-item-form")[0].reset();
    });

    $("#guide").on("click",".btn-modify",function(){
        let item=$(this).data("item");
        $("#guide .guide-form").show();
        $("#guide .btn-show-form").html("修改导购商品");
        $(".guide-item-form [name=id]").show().val(item.id);
        $(".guide-item-form [name=name]").val(item.name);
        $(".guide-item-form [name=title]").val(item.title);
        $(".guide-item-form [name=price]").val(item.price);
        $(".guide-item-form [name=pubDate]").val(item.pubDate);
    });

    $("#guide").on("click",".btn-delete",function(){
        let id=$(this).data("id");
        if(confirm("确定要删除这个导购商品吗？")) {
            $.post("/oper/guide/delete/"+id, (ret)=>{
                if(ret.code){
                    alert(ret.message);
                }
                else{
                    $(".guide-item-"+id).remove();
                    alert("删除成功！");
                }
            });
        }
    });

    $("#guide .btn-add").on("click",function(){
        let op=$("#guide .btn-show-form").html();
        let formData = new FormData($(".guide-item-form")[0]);
        
        let url = "/oper/guide/add";
        if(op=="修改导购商品"){
            url = "/oper/guide/modify";
        }
        
        $.ajax(url, {
            type: "post",
            processData: false,
            contentType: false,
            data: formData,
            success: (ret) => {
                if(ret.code){
                    alert(ret.message);
                }
                else{
                    if(op=="修改导购商品") {
                        let newTr=createGuideTr(ret.data);
                        $(".guide-item-"+ret.data.id).replaceWith(newTr);
                        alert("修改成功！");
                        $("#guide .btn-show-form").html("添加导购商品");
                        $("#guide .guide-item-form")[0].reset();
                        $(".guide-form").hide();
                    }
                    else{
                        addGuideItemToTbody(ret.data);
                        alert("添加成功！");
                        $("#guide .guide-item-form")[0].reset();
                    }
                }
            }
        })
    });

    $("#guide .pagination").on("click","a",function(){
        let pno=$(this).data("pno");
        if(pno) {
            pageNo=pno;
            listGuideItems();
        }
    });

    function modifyGuidePagination(pager){
        let pg=$("#guide .pagination");
        pg.empty();
        let li=$("<li>");
        let a=$("<a>").appendTo(li).html("&laquo;");
        if(pager.isFirstPage){
            li.addClass("disabled");
            a.data("pno",null);
        }
        else{
            a.data("pno",pager.prePage);
        }
        pg.append(li);
        for(let i=0;i<pager.navigatepageNums.length;i++){
            li=$("<li>");
            a=$("<a>").appendTo(li).html(pager.navigatepageNums[i]+"");
            if(pager.pageNum==pager.navigatepageNums[i]){
                li.addClass("active");
                a.data("pno",null);
            }
            else{
                a.data("pno",pager.navigatepageNums[i]);
            }
            pg.append(li);
        }
        li=$("<li>");
        a=$("<a>").appendTo(li).html("&raquo;");
        if(pager.isLastPage){
            li.addClass("disabled");
            a.data("pno",null);
        }
        else{
            a.data("pno",pager.nextPage);
        }
        pg.append(li);
    }

    function addUserToTbody(user) {
        //创建：<标签名>
        let tr = createUserTr(user);
        //append:父加子
        $(".user-tbody").append(tr);
    }

    function createUserTr(user) {
        let tr = $("<tr>");
        //appendTo:子加到父
        $("<td>").html(user.id).appendTo(tr);
        $("<td>").html(user.name).appendTo(tr);
        $("<td>").html(user.phone).appendTo(tr);
        $("<td>").html(user.image).appendTo(tr);
        $("<td>").html(user.state == 1 ? "正常" : "已删除").appendTo(tr);
        let td = $("<td>").appendTo(tr);
        let btn = $("<button>").addClass("btn btn-xs").appendTo(td);
        if (user.state == 1) {
            btn.addClass("btn-danger").html("删除");
        } else {
            btn.addClass("btn-success").html("恢复");
        }
        btn.on("click", function() {
            if (user.state == 1) {
                // 删除用户
                $.get("oper/user/delete", {id: user.id}, (rt) => {
                    if (rt.code) {
                        alert(rt.message);
                    } else {
                        let newTr = createUserTr({
                            id: user.id,
                            name: user.name,
                            phone: user.phone,
                            image: user.image,
                            state: 0
                        });
                        let oldTr = $(this).parents("tr");
                        oldTr.replaceWith(newTr);
                        alert("删除成功！");
                    }
                });
            } else {
                // 恢复用户
                $.get("oper/user/recover", {id: user.id}, (rt) => {
                    if (rt.code) {
                        alert(rt.message);
                    } else {
                        let newTr = createUserTr({
                            id: user.id,
                            name: user.name,
                            phone: user.phone,
                            image: user.image,
                            state: 1
                        });
                        let oldTr = $(this).parents("tr");
                        oldTr.replaceWith(newTr);
                        alert("恢复成功！");
                    }
                });
            }
        });
        return tr;
    }

    // 修复：移除不存在的按钮事件绑定
    // $('#add-admin-btn').on('click', function() {
    //     $('#adminModal').modal('show');
    // });
    
    // 修复：移除不存在的保存按钮事件绑定
    // $('#saveAdminBtn').on('click', function() {
    //     const adminData = {
    //         id: $('#adminId').val(),
    //         name: $('#adminName').val(),
    //         password: $('#adminPassword').val(),
    //         state: $('#adminState').val()
    //     };
    // });

});