package edu.nnuzb.jiguo.entity.vo;

import edu.nnuzb.jiguo.entity.CoolItem;

public class CoolItemVo extends CoolItem {
    private Integer thumbCount;
    private Integer commentCount;
    private Boolean isThumb;
    
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
    
    public Boolean getIsThumb() {
        return isThumb;
    }
    
    public void setIsThumb(Boolean isThumb) {
        this.isThumb = isThumb;
    }
}