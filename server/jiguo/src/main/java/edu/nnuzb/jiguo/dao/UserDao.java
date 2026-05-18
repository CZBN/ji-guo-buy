package edu.nnuzb.jiguo.dao;

import edu.nnuzb.jiguo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao extends BaseMapper<User>{

    User selectByUser(User  user);
    User selectByPhone(String phone);
    User selectByName(String name);
    
    int insert(User user);

    int insertUser(User user);
    List<User> selectListByName(String name);

    int updateState(@Param("id") int id, @Param("oldState") String oldState, @Param("newState") String newState);


}
