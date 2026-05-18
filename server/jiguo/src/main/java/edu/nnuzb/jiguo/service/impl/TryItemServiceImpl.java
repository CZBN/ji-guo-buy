package edu.nnuzb.jiguo.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import edu.nnuzb.jiguo.dao.TryItemDao;
import edu.nnuzb.jiguo.service.TryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.nnuzb.jiguo.entity.vo.TryItemVo;

import java.util.Date;
import java.util.List;

@Service
public class TryItemServiceImpl implements TryItemService {
    @Autowired
    private TryItemDao tryItemDao;

    @Override
    public List<TryItemVo> getBycategorystatus(String category, String status, int pageNo,int pageSize) {
        String dbCategory = "all";
        if ("tys".equals(category)) {
            dbCategory = "Exclusive Trial";
        } else if ("popular".equals(category) || "dazhong".equals(category)) {
            dbCategory = "General Trial";
        }
        
        String dbStatus = status;
        if(!("apply".equals(status)||"try".equals(status)||"end".equals(status))){
            dbStatus = "all";
        }
        
        PageHelper.startPage(pageNo,pageSize);
        List<TryItemVo> data = tryItemDao.selectByCategoryStatus(dbCategory, dbStatus);
        
        if (data != null) {
            for (TryItemVo item : data) {
                // 设置默认值
                if (item.getApplyCount() == null) {
                    item.setApplyCount(0);
                }
                if (item.getReportCount() == null) {
                    item.setReportCount(0);
                }
                
                // 根据时间判断状态
                if ("all".equals(dbStatus)) {
                    long currentTime = System.currentTimeMillis();
                    if(item.getBeginDate().getTime() > currentTime){
                        item.setStatus("apply");
                    }
                    else if(item.getEndDate().getTime() > currentTime){
                        item.setStatus("try");
                    }
                    else{
                        item.setStatus("end");
                    }
                } else {
                    item.setStatus(dbStatus);
                }

                if ("apply".equals(item.getStatus())){
                    long diff = item.getBeginDate().getTime() - System.currentTimeMillis();
                    int remainDays = (int) (diff / (1000 * 60 * 60 * 24));
                    item.setRemainDays(remainDays > 0 ? remainDays : 0);
                }
            }
        }
        
        return data;
    }
}
