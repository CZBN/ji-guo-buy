package edu.nnuzb.jiguo.controller;
import edu.nnuzb.jiguo.entity.User;
import edu.nnuzb.jiguo.service.CoolItemThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import edu.nnuzb.jiguo.entity.po.JsonData;

@RestController
@RequestMapping("coolthumb")
public class CoolItemThumbController {
    @Autowired
    private CoolItemThumbService coolItemThumbService;
    @GetMapping("add")
    public JsonData addThumb(int itemId, HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return JsonData.fail(500,"请先登录");
        }
        try {
            return JsonData.ok(coolItemThumbService.addThumb(itemId,
                    user.getId()));
        }
        catch (Exception ex){
            return JsonData.fail(600,ex.getMessage());
        }
    }
    @GetMapping("cancel")
    public JsonData cancelThumb(int itemId, HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return JsonData.fail(500,"请先登录");
        }
        try {
            return JsonData.ok(coolItemThumbService.cancelThumb(itemId,
                    user.getId()));
        }
        catch (Exception ex){
            return JsonData.fail(600,ex.getMessage());
        }
    }
}