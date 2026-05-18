package edu.nnuzb.jiguo.dao;

import edu.nnuzb.jiguo.entity.CoolItem;
import edu.nnuzb.jiguo.entity.vo.CoolItemVo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CoolItemDao {
    List<CoolItemVo> selectByOrder(String order);

    CoolItemVo selectById(Integer id);

    /**
     * 根据商品名或商品标题搜索
     * @param type name 表示商品名，title 表示商品标题
     * @param key 查询条件
     * @return
     */
    List<CoolItem> selectByKey(@Param("type") String type, @Param("key") String key);

    int insert(CoolItem item);

    int update(CoolItem item);

    int delete(Integer id);
}
