package edu.nnuzb.jiguo.service;

import edu.nnuzb.jiguo.entity.vo.TryItemVo;

import java.util.List;

public interface TryItemService {
    List<TryItemVo> getBycategorystatus(String category, String status, int pageNo,int pageSize);
}
