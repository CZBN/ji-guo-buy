package edu.nnuzb.jiguo.service;

import edu.nnuzb.jiguo.entity.GuideItem;
import edu.nnuzb.jiguo.entity.vo.GuideItemVo;

import java.util.List;

public interface GuideItemService {
    List<GuideItemVo> getByOrder(String order,int pageNo,int pageSize);
    
    // 管理员操作相关方法
    int add(GuideItem guideItem);
    int update(GuideItem guideItem);
    int delete(Integer id);
    GuideItem getById(Integer id);
}
