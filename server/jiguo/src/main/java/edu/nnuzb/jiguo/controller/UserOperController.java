package edu.nnuzb.jiguo.controller;

import com.github.pagehelper.PageInfo;
import edu.nnuzb.jiguo.entity.User;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("oper/user")
public class UserOperController {
    @Autowired
    private UserService userService;
    @GetMapping("search")
    public JsonData search(
            @RequestParam(name = "pno", defaultValue = "1") int pageNo, @RequestParam(name = "size", defaultValue = "5") int pageSize, @RequestParam(name = "name", defaultValue = "") String name) {
        List<User> ret = userService.searchByPage(pageNo, pageSize, name);
        if (ret.size() == 0) {
            return JsonData.fail(10001, "没有数据");
        }
        return JsonData.ok(new PageInfo<>(ret));
    }
    @GetMapping("delete")
    public JsonData delete(int id) {
        int rt = userService.delete(id);
        return rt > 0 ? JsonData.ok("OK") : JsonData.fail(10004, "删除失败");
    }
    @GetMapping("recover")
    public JsonData recover(int id) {
        int rt = userService.recover(id);
        return rt > 0 ? JsonData.ok("OK") : JsonData.fail(10005, "恢复失败");
    }
}