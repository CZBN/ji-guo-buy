package edu.nnuzb.jiguo.controller;

import com.github.pagehelper.PageInfo;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.entity.vo.TryItemVo;
import edu.nnuzb.jiguo.service.TryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/try")
public class TryItemController {
    @Autowired
    private TryItemService tryItemService;

    @GetMapping("search")
    public JsonData search(String category, String status,
                           @RequestParam(name = "pno", defaultValue = "1")int pageNo,
                           @RequestParam(name = "size", defaultValue = "10")int pageSize) {
        // 参数校验
        if (category == null || category.isEmpty()) {
            category = "all";
        }
        
        if (status == null || status.isEmpty()) {
            status = "all";
        }
        
        List<TryItemVo> data = tryItemService.getBycategorystatus(
                category, status, pageNo, pageSize);
        if(data==null || data.size()==0){
            return JsonData.ok(new PageInfo<>(data)); // 即使没有数据也要返回PageInfo结构
        }
        return JsonData.ok(new PageInfo<>(data));
    }
}