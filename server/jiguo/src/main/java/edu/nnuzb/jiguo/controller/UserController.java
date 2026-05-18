package edu.nnuzb.jiguo.controller;

import cn.hutool.core.util.StrUtil;
import edu.nnuzb.jiguo.entity.User;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping("test")
    public JsonData test(HttpSession session){
        return JsonData.ok("ok");
    }
    @RequestMapping("/user/login")
    public JsonData login(User user, HttpSession session){
        if(StrUtil.isBlank(user.getPhone())){
            return JsonData.fail(501,"手机号不能为空");
        }
        if(StrUtil.isBlank(user.getPassword())){
            return JsonData.fail(502,"密码不能为空");
        }
        if(!user.getPhone().matches("1[3589][0-9]{9}")){
            return JsonData.fail(503,"手机号格式错误");
        }
        if(!user.getPassword().matches(".{6,16}")){
            return JsonData.fail(504,"密码格式错误");
        }
        User temp = userService.login(user);
        if (temp == null){
            return JsonData.fail(500,"手机号或密码错误");
        }
        temp.setPassword(null);
        session.setAttribute("user",temp);
        //session.setMaxInactiveInterval(60*60*24*7);
        return JsonData.ok(temp);
    }

    @PostMapping("reg")
    public JsonData register(User user, String confirm){
        if(StrUtil.isBlank(user.getPhone())){
            return JsonData.fail(501,"手机号不能为空");
        }
        if(StrUtil.isBlank(user.getPassword())){
            return JsonData.fail(502,"密码不能为空");
        }
        if(!user.getPhone().matches("1[3589][0-9]{9}")){
            return JsonData.fail(503,"手机号格式错误");
        }
        if(!user.getPassword().matches(".{6,16}")){
            return JsonData.fail(504,"密码格式错误");
        }
        if(!user.getPassword().equals(confirm)){
            return JsonData.fail(509,"密码不一致");
        }
        if(StrUtil.isBlank(user.getName())){
            return JsonData.fail(505,"用户名不能为空");
        }
        if(!user.getName().matches("[a-zA-Z\u4e00-\u9fa5].{1,11}")){
            return JsonData.fail(506,"用户名格式错误");
        }
        // 设置默认头像
        if(StrUtil.isBlank(user.getImage())){
            user.setImage("tx.jpg");
        }
        try{
            User temp = userService.register(user);
            if(temp == null){
                return JsonData.fail(508,"注册失败");
            }
            return JsonData.ok(temp);
        }
        catch (Exception e){
            return JsonData.fail(507,e.getMessage());
        }
    }
}
