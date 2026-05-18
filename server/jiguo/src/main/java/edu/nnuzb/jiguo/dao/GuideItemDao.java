package edu.nnuzb.jiguo.dao;

import edu.nnuzb.jiguo.entity.GuideItem;
import edu.nnuzb.jiguo.entity.vo.GuideItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuideItemDao {
    List<GuideItemVo> selectByOrder(@Param("order") String order);
    
    // 管理员操作相关方法
    int insert(GuideItem guideItem);
    int update(GuideItem guideItem);
    int delete(Integer id);
    GuideItem selectById(Integer id);
}
