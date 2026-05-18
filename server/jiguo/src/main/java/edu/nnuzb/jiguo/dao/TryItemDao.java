package edu.nnuzb.jiguo.dao;

import edu.nnuzb.jiguo.entity.vo.TryItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TryItemDao {
    List<TryItemVo> selectByCategoryStatus(@Param("category") String category, @Param("status") String status);
}
