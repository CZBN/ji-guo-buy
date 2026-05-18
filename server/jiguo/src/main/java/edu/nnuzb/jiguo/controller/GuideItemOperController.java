package edu.nnuzb.jiguo.controller;

import edu.nnuzb.jiguo.entity.GuideItem;
import edu.nnuzb.jiguo.entity.po.JsonData;
import edu.nnuzb.jiguo.service.GuideItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/oper/guide")
public class GuideItemOperController {
    
    @Autowired
    private GuideItemService guideItemService;
    
    // 添加导购商品
    @PostMapping("/add")
    public JsonData add(GuideItem guideItem, MultipartFile file, HttpSession session) {
        // 检查权限
        if (session.getAttribute("admin") == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            // 处理文件上传
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID() + suffix;
                
                // 获取项目根路径
                String path = System.getProperty("user.dir");
                File dest = new File(path + "/src/main/resources/static/image/" + newFileName);
                
                // 如果目录不存在则创建
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                
                // 保存文件
                file.transferTo(dest);
                guideItem.setImage(newFileName);
            }
            
            // 设置发布日期
            guideItem.setPubDate(new Date());
            
            // 保存到数据库
            int result = guideItemService.add(guideItem);
            if (result > 0) {
                return JsonData.ok(guideItem);
            } else {
                return JsonData.fail(500, "添加失败");
            }
        } catch (IOException e) {
            return JsonData.fail(500, "文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            return JsonData.fail(500, "添加失败: " + e.getMessage());
        }
    }
    
    // 修改导购商品
    @PostMapping("/modify")
    public JsonData modify(GuideItem guideItem, MultipartFile file, HttpSession session) {
        // 检查权限
        if (session.getAttribute("admin") == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            // 处理文件上传
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID() + suffix;
                
                // 获取项目根路径
                String path = System.getProperty("user.dir");
                File dest = new File(path + "/src/main/resources/static/image/" + newFileName);
                
                // 如果目录不存在则创建
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                
                // 保存文件
                file.transferTo(dest);
                guideItem.setImage(newFileName);
            }
            
            // 更新数据库
            int result = guideItemService.update(guideItem);
            if (result > 0) {
                return JsonData.ok(guideItem);
            } else {
                return JsonData.fail(500, "修改失败");
            }
        } catch (IOException e) {
            return JsonData.fail(500, "文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            return JsonData.fail(500, "修改失败: " + e.getMessage());
        }
    }
    
    // 删除导购商品
    @PostMapping("/delete/{id}")
    public JsonData delete(@PathVariable Integer id, HttpSession session) {
        // 检查权限
        if (session.getAttribute("admin") == null) {
            return JsonData.fail(401, "请先登录");
        }
        
        try {
            // 删除数据库记录
            int result = guideItemService.delete(id);
            if (result > 0) {
                return JsonData.ok("删除成功");
            } else {
                return JsonData.fail(500, "删除失败");
            }
        } catch (Exception e) {
            return JsonData.fail(500, "删除失败: " + e.getMessage());
        }
    }
}