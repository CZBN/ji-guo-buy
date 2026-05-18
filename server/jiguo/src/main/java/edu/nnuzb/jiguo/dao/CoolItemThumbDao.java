package edu.nnuzb.jiguo.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CoolItemThumbDao {
    int insertCoolItemThumb(int itemId,int userId);
    int deleteCoolItemThumb(int itemId,int userId);
    int selectByItemIdAndUserId(int itemId,int userId);
}