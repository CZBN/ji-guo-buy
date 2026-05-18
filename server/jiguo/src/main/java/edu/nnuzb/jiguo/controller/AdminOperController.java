package edu.nnuzb.jiguo.controller;

import edu.nnuzb.jiguo.entity.Admin;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oper/admin")
public class AdminOperController {
    
    @Autowired
    private AdminService adminService;
    
    @GetMapping("/list")
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
    
    @PostMapping("/add")
    public JsonData add(@RequestBody Admin admin, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        if (currentAdmin == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            // 修复：使用正确的service变量名
            // 设置默认状态为1（正常）
            admin.setState(1);
            int result = adminService.add(admin);
            if (result > 0) {
                // 重新查询数据库获取完整信息
                Admin newAdmin = adminService.getAdminByName(admin.getName());
                return JsonData.ok(newAdmin);
            }
            return JsonData.fail(10002, "添加失败");
        } catch (Exception e) {
            return JsonData.fail(10003, e.getMessage());
        }
    }
    
    @PostMapping("/delete/{id}")
    public JsonData delete(@PathVariable int id, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        if (currentAdmin == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            int result = adminService.delete(id);
            return result > 0 ? JsonData.ok("删除成功") : JsonData.fail(10004, "删除失败");
        } catch (Exception e) {
            return JsonData.fail(500, "删除失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/recover/{id}")
    public JsonData recover(@PathVariable int id, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        if (currentAdmin == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            int result = adminService.recover(id);
            return result > 0 ? JsonData.ok("恢复成功") : JsonData.fail(10005, "恢复失败");
        } catch (Exception e) {
            return JsonData.fail(500, "恢复失败: " + e.getMessage());
        }
    }
}