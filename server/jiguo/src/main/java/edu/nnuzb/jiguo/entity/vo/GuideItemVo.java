package edu.nnuzb.jiguo.entity.vo;

import edu.nnuzb.jiguo.entity.GuideItem;

public class GuideItemVo extends GuideItem {
    private Integer thumbCount;
    private Integer commentCount;
    
    public Integer getThumbCount() {
        return thumbCount;
    }
    
    public void setThumbCount(Integer thumbCount) {
        this.thumbCount = thumbCount;
    }
    
    public Integer getCommentCount() {
        return commentCount;
    }
    
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}