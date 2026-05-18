package edu.nnuzb.jiguo.service;

import edu.nnuzb.jiguo.entity.CoolItem;
import edu.nnuzb.jiguo.entity.vo.CoolItemVo;

import java.util.List;

public interface CoolItemService {
    List<CoolItemVo> getByOrder(String order,int pageNo,int pageSize,Integer userId);

    CoolItemVo getById(Integer id, Integer userId);

    List<CoolItem> searchByPage(int pageNo, int pageSize, String type, String key);

    int add(CoolItem item);

    int modify(CoolItem item);

    int delete(Integer id);
}
