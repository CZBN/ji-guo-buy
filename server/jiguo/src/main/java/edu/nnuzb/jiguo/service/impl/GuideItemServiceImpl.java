package edu.nnuzb.jiguo.service.impl;

import com.github.pagehelper.PageHelper;
import edu.nnuzb.jiguo.dao.GuideItemDao;
import edu.nnuzb.jiguo.entity.GuideItem;
import edu.nnuzb.jiguo.entity.vo.GuideItemVo;
import edu.nnuzb.jiguo.service.GuideItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideItemServiceImpl implements GuideItemService {
    @Autowired
    private GuideItemDao guideItemDao;

    @Override
    public List<GuideItemVo> getByOrder(String order, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        if (!"hot".equals(order)) {
            order = "new";
        }
        return guideItemDao.selectByOrder(order);
    }
    
    @Override
    public int add(GuideItem guideItem) {
        // 实现添加导购商品逻辑
        return guideItemDao.insert(guideItem);
    }
    
    @Override
    public int update(GuideItem guideItem) {
        // 实现更新导购商品逻辑
        return guideItemDao.update(guideItem);
    }
    
    @Override
    public int delete(Integer id) {
        // 实现删除导购商品逻辑
        return guideItemDao.delete(id);
    }
    
    @Override
    public GuideItem getById(Integer id) {
        // 实现根据ID获取导购商品逻辑
        return guideItemDao.selectById(id);
    }
}
