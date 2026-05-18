package edu.nnuzb.jiguo.entity.vo;

import edu.nnuzb.jiguo.entity.TryItem;
import java.util.Date;

public class TryItemVo extends TryItem {
    private Integer applyCount;
    private Integer remainDays;
    private Integer reportCount;
    private String status;
    
    public Integer getApplyCount() {
        return applyCount;
    }
    
    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }
    
    public Integer getRemainDays() {
        return remainDays;
    }
    
    public void setRemainDays(Integer remainDays) {
        this.remainDays = remainDays;
    }
    
    public Integer getReportCount() {
        return reportCount;
    }
    
    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}