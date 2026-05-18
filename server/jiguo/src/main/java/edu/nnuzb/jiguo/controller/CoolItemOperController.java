package edu.nnuzb.jiguo.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import edu.nnuzb.jiguo.entity.CoolItem;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.service.CoolItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/oper/cool")
public class CoolItemOperController {
    @Autowired
    private CoolItemService coolItemService;
    
    @Value("${image.upload.path}")
    private String uploadPath;
    
    private String realUploadPath;

    @PostConstruct
    public void init() throws IOException {
        if (uploadPath.startsWith("classpath:")) {
            // 处理classpath路径
            String path = uploadPath.substring("classpath:".length());
            ClassPathResource resource = new ClassPathResource(path);
            realUploadPath = resource.getFile().getAbsolutePath();
        } else {
            // 处理普通路径
            realUploadPath = uploadPath;
        }
        
        // 确保目录存在
        File uploadDir = new File(realUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @GetMapping("search")
    public JsonData search(
            @RequestParam(name = "pno", defaultValue = "1") int pageNo,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name = "type", defaultValue = "name")String type,
            @RequestParam(name = "key", defaultValue = "")String key) {
        List<CoolItem> ret = coolItemService.searchByPage(pageNo, pageSize,type, key);
        if (ret.size() == 0) {
            return JsonData.fail(10001, "没有数据");
        }
        return JsonData.ok(new PageInfo<>(ret,5));
    }

    @PostMapping("add")
    public JsonData add(CoolItem item, @RequestParam(value = "file", required = false) MultipartFile imageFile) {
        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 生成唯一文件名
                String originalFilename = imageFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String newFilename = UUID.randomUUID().toString() + fileExtension;
                
                // 保存文件
                File saveFile = new File(realUploadPath, newFilename);
                imageFile.transferTo(saveFile);
                
                // 设置图片路径为文件名（前端会加上/image/前缀访问）
                item.setImage(newFilename);
            } catch (IOException e) {
                return JsonData.fail(10005, "图片上传失败: " + e.getMessage());
            }
        }
        
        if(StrUtil.isBlank(item.getName())){
            return JsonData.fail(501,"商品名不能为空");
        }
        if(StrUtil.isBlank(item.getTitle())){
            return JsonData.fail(502,"商品标题不能为空");
        }
        if(StrUtil.isBlank(item.getImage())){
            return JsonData.fail(503,"商品图片不能为空");
        }
        
        int ret= coolItemService.add(item);
        if(ret>0){
            return JsonData.ok(item);
        }
        return JsonData.fail(10002,"添加失败");
    }

    @PostMapping("modify")
    public JsonData modify(CoolItem item, @RequestParam(value = "file", required = false) MultipartFile imageFile) {
        if(item.getId() == null){
            return JsonData.fail(504,"商品ID不能为空");
        }
        
        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 生成唯一文件名
                String originalFilename = imageFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String newFilename = UUID.randomUUID().toString() + fileExtension;
                
                // 保存文件
                File saveFile = new File(realUploadPath, newFilename);
                imageFile.transferTo(saveFile);
                
                // 设置图片路径为文件名
                item.setImage(newFilename);
            } catch (IOException e) {
                return JsonData.fail(10005, "图片上传失败: " + e.getMessage());
            }
        }
        
        if(StrUtil.isBlank(item.getName())){
            return JsonData.fail(501,"商品名不能为空");
        }
        if(StrUtil.isBlank(item.getTitle())){
            return JsonData.fail(502,"商品标题不能为空");
        }
        if(StrUtil.isBlank(item.getImage())){
            return JsonData.fail(503,"商品图片不能为空");
        }
        
        int ret= coolItemService.modify(item);
        if(ret>0){
            return JsonData.ok(item);
        }
        return JsonData.fail(10003,"修改失败");
    }

    @PostMapping("delete/{id}")
    public JsonData delete(@PathVariable Integer id) {
        if(id == null) {
            return JsonData.fail(505, "商品ID不能为空");
        }
        int ret = coolItemService.delete(id);
        if(ret>0){
            return JsonData.ok("删除成功");
        }
        return JsonData.fail(10004, "删除失败");
    }
}