package edu.nnuzb.jiguo.service.impl;

import com.github.pagehelper.PageHelper;
import edu.nnuzb.jiguo.dao.CoolItemDao;
import edu.nnuzb.jiguo.dao.CoolItemThumbDao;
import edu.nnuzb.jiguo.entity.CoolItem;
import edu.nnuzb.jiguo.entity.vo.CoolItemVo;
import edu.nnuzb.jiguo.service.CoolItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CoolItemServiceImpl implements CoolItemService {
    @Autowired
    private CoolItemDao coolItemDao;
    @Autowired
    private CoolItemThumbDao coolItemThumbDao;

    @Override
    public List<CoolItemVo> getByOrder(String order,int pageNo,int pageSize,Integer userId) {
        if (!"hot".equals(order)) {
            order = "new";
        }
        PageHelper.startPage(pageNo,pageSize);
        List<CoolItemVo> data = coolItemDao.selectByOrder(order);
        if (userId!= null){
            for (CoolItemVo item : data) {
                item.setIsThumb(coolItemThumbDao.selectByItemIdAndUserId(item.getId(),userId)>0);
            }
        }
        return data;
    }

    @Override
    public CoolItemVo getById(Integer id, Integer userId) {
        CoolItemVo item = coolItemDao.selectById(id);
        if (item != null && userId != null) {
            item.setIsThumb(coolItemThumbDao.selectByItemIdAndUserId(item.getId(), userId) > 0);
        }
        return item;
    }

    @Override
    public List<CoolItem> searchByPage(int pageNo, int pageSize, String type, String key)
    {
        if(! "name".equals(type)){
            type="title";
        }
        PageHelper.startPage(pageNo,pageSize);
        return coolItemDao.selectByKey(type, key);
    }

    @Override
    public int add(CoolItem item) {
        return coolItemDao.insert(item);
    }

    @Override
    public int modify(CoolItem item) {
        return coolItemDao.update(item);
    }

    @Override
    public int delete(Integer id) {
        return coolItemDao.delete(id);
    }
}
