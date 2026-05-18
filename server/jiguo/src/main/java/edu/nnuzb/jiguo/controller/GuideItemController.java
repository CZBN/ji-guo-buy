package edu.nnuzb.jiguo.controller;

import com.github.pagehelper.PageInfo;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.entity.vo.GuideItemVo;
import edu.nnuzb.jiguo.service.GuideItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guideitem")
//@CrossOrigin
public class GuideItemController {
    @Autowired
    private GuideItemService guideItemService;

    @GetMapping("search")
    public JsonData getByOrder(String order,
                               @RequestParam(name = "pno", defaultValue = "1") int pageNo,
                               @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        List<GuideItemVo> list = guideItemService.getByOrder(order, pageNo, pageSize);
        return JsonData.ok(new PageInfo<>(list));
    }

    @GetMapping("detail/{id}")
    public JsonData detail(@PathVariable Integer id) {
        Object item = guideItemService.getById(id);
        if (item == null) {
            return JsonData.fail(404, "导购商品不存在");
        }
        return JsonData.ok(item);
    }
}
