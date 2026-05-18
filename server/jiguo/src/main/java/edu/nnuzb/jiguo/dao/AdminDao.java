package edu.nnuzb.jiguo.dao;

import edu.nnuzb.jiguo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminDao {

    int insert(Admin record);
    Admin selectByAdmin(Admin admin);
    
    Admin selectByName(String name);
    
    List<Admin> selectAll();

    int insertAdmin(Admin admin);
    
    int updateAdmin(Admin admin);
    
    int deleteAdmin(int id);

    int updateState(@Param("id") int id, @Param("oldState") int oldState, @Param("newState") int newState);
    
    List<edu.nnuzb.jiguo.entity.User> selectListByName(String name);
}