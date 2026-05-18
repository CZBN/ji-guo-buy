package edu.nnuzb.jiguo.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import edu.nnuzb.jiguo.entity.CoolItem;
import edu.nnuzb.jiguo.entity.User;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.entity.vo.CoolItemVo;
import edu.nnuzb.jiguo.service.CoolItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coolitem")
//@CrossOrigin
public class CoolItemController {
    @Autowired
    private CoolItemService coolItemService;

    @Value("${image.upload.path:uploads/images/}")
    private String uploadPath;

    @GetMapping("search")
    public JsonData getByOrder(String order,
                               @RequestParam (name = "pno",defaultValue = "1") int pageNo,
                               @RequestParam (name = "size",defaultValue = "5")int pageSize,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer userId = null;
        if(user != null){
            userId = user.getId();
        }
        List<CoolItemVo> list = coolItemService.getByOrder(order, pageNo, pageSize, userId);
        return JsonData.ok(new PageInfo<>(list));
    }

    @GetMapping("detail/{id}")
    public JsonData detail(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer userId = user == null ? null : user.getId();
        CoolItemVo item = coolItemService.getById(id, userId);
        if (item == null) {
            return JsonData.fail(404, "商品不存在");
        }
        return JsonData.ok(item);
    }

    @GetMapping("searchByKey")
    public JsonData searchByKey(@RequestParam(name = "pno", defaultValue = "1") int pageNo,
                                @RequestParam(name = "size", defaultValue = "10") int pageSize,
                                @RequestParam(name = "type", defaultValue = "title") String type,
                                @RequestParam(name = "key", defaultValue = "") String key) {
        List<CoolItem> ret = coolItemService.searchByPage(pageNo, pageSize, type, key);
        return JsonData.ok(new PageInfo<>(ret));
    }

    @PostMapping("add")
    public JsonData add(CoolItem item, MultipartFile file) {
        if (StrUtil.isBlank(item.getName())) {
            return JsonData.fail(501, "商品名不能为空");
        }
        if (StrUtil.isBlank(item.getTitle())) {
            return JsonData.fail(502, "商品标题不能为空");
        }
        if (file == null || file.isEmpty()) {
            return JsonData.fail(503, "商品图片不能为空");
        }
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            return JsonData.fail(504, "商品图片格式错误");
        }
        if (file.getSize() > 200 * 1024) {
            return JsonData.fail(505, "商品图片尺寸超过 200K");
        }
        //文件名：采用 uuid
        //首先获取上传文件的扩展名
        String ext = file.getOriginalFilename();
        int dot = ext.lastIndexOf('.');//7 -1
        if (dot > 0) {
            ext = ext.substring(dot);
        } else {
            ext = "";
        }
        //产生 UUID 名
        String uuid = UUID.randomUUID().toString();
        //指定父路径：根据自身项目路径修改
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        //产生真实存放的文件名
        File imageFile = new File(uploadDir, uuid + ext);
        try {
            //将缓存中的文件持久化保存到真实文件中
            file.transferTo(imageFile);
            //item.setImage(imageFile.getName());
            item.setImage(uuid + ext);
            int ret = coolItemService.add(item);
            if (ret > 0) {
                return JsonData.ok(item);
            }
            return JsonData.fail(10002, "添加失败");
        } catch (IOException e) {
            return JsonData.fail(10006, e.getMessage());
        }
    }

    @PostMapping("modify")
    public JsonData modify(CoolItem item){
        int ret= coolItemService.modify(item);
        if(ret>0){
            return JsonData.ok(item);
        }
        return JsonData.fail(10003,"修改失败");
    }
}
