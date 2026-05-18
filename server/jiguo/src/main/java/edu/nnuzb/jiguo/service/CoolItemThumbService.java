package edu.nnuzb.jiguo.service;

public interface CoolItemThumbService {
    int addThumb(int itemId,int userId);
    int cancelThumb(int itemId,int userId);
}