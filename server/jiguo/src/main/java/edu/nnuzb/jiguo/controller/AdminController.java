package edu.nnuzb.jiguo.controller;

import edu.nnuzb.jiguo.entity.Admin;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("login")
    public JsonData login(@RequestBody Admin admin, HttpSession session) {
        Admin temp = adminService.login(admin);
        if (temp == null) {
            return JsonData.fail(500, "账号或密码错误");
        }
        // 只存储必要的信息到session中，避免存储过多数据
        Admin sessionAdmin = new Admin();
        sessionAdmin.setId(temp.getId());
        sessionAdmin.setName(temp.getName());
        sessionAdmin.setState(temp.getState());
        session.setAttribute("admin", sessionAdmin);
        return JsonData.ok(sessionAdmin);
    }
    
    @PostMapping("login2")
    public JsonData login2(String name, String password, HttpSession session) {
        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(password);
        Admin temp = adminService.login(admin);
        if (temp == null) {
            return JsonData.fail(500, "账号或密码错误");
        }
        // 只存储必要的信息到session中，避免存储过多数据
        Admin sessionAdmin = new Admin();
        sessionAdmin.setId(temp.getId());
        sessionAdmin.setName(temp.getName());
        sessionAdmin.setState(temp.getState());
        session.setAttribute("admin", sessionAdmin);
        return JsonData.ok(sessionAdmin);
    }

    @GetMapping("logoff")
    public JsonData logoff(HttpSession session) {
        session.removeAttribute("admin");
        // 不立即invalidate session，让容器自动处理
        return JsonData.ok("退出成功！");
    }

    @GetMapping("info")
    public JsonData info(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return JsonData.fail(401, "请先登录");
        }
        return JsonData.ok(admin);
    }
    
    @GetMapping("list")
    public JsonData list(HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        if (currentAdmin == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            List<Admin> admins = adminService.getAllAdmins();
            return JsonData.ok(admins);
        } catch (Exception e) {
            return JsonData.fail(500, "获取管理员列表失败: " + e.getMessage());
        }
    }
    
    @PostMapping("save")
    public JsonData save(@RequestBody Admin admin, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        if (currentAdmin == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            int result = adminService.saveAdmin(admin);
            return JsonData.ok(result);
        } catch (Exception e) {
            return JsonData.fail(500, e.getMessage());
        }
    }
    
    @PostMapping("update")
    public JsonData update(@RequestBody Admin admin, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        if (currentAdmin == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            int result = adminService.updateAdmin(admin);
            return JsonData.ok(result);
        } catch (Exception e) {
            return JsonData.fail(500, e.getMessage());
        }
    }
    
    @PostMapping("delete/{id}")
    public JsonData delete(@PathVariable int id, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        if (currentAdmin == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            int result = adminService.deleteAdmin(id);
            return JsonData.ok(result);
        } catch (Exception e) {
            return JsonData.fail(500, e.getMessage());
        }
    }
}