package edu.nnuzb.jiguo.service.impl;
import edu.nnuzb.jiguo.service.CoolItemThumbService;
import edu.nnuzb.jiguo.dao.CoolItemThumbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CoolItemThumbServiceImpl implements CoolItemThumbService {
    @Autowired
    private CoolItemThumbDao coolItemThumbDao;
    @Override
    public int addThumb(int itemId, int userId) {
        int rt=coolItemThumbDao.selectByItemIdAndUserId(itemId,userId);
        if(rt==0) {
            coolItemThumbDao.insertCoolItemThumb(itemId,userId);
            // 返回当前总点赞数
            return coolItemThumbDao.selectByItemIdAndUserId(itemId, userId);
        }
        throw new RuntimeException("已经点过赞了");
    }
    @Override
    public int cancelThumb(int itemId, int userId) {
        int rt=coolItemThumbDao.selectByItemIdAndUserId(itemId,userId);
        if(rt==1) {
            coolItemThumbDao.deleteCoolItemThumb(itemId,userId);
            // 返回当前总点赞数
            return coolItemThumbDao.selectByItemIdAndUserId(itemId, userId);
        }
        throw new RuntimeException("尚未点过赞");
    }
    
}