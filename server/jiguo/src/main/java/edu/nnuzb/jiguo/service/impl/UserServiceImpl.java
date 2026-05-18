package edu.nnuzb.jiguo.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import edu.nnuzb.jiguo.dao.UserDao;
import edu.nnuzb.jiguo.entity.User;
import edu.nnuzb.jiguo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
   public User login(User user) {
        return userDao.selectByUser(user);
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("phone", user.getPhone());
//        queryWrapper.eq("password", Md5Util.getMD5(user.getPassword()));
//        return userDao.selectOne(queryWrapper);
    }

    @Override
    public User register(User user) {
        User temp = userDao.selectByPhone(user.getPhone());
        if(temp != null){
            throw new RuntimeException("手机号已存在");
        }
        temp = userDao.selectByName(user.getName());
        if(temp != null){
            throw new RuntimeException("用户名已存在");
        }
        return userDao.insertUser(user)==1?user:null;
    }

    @Override
    public List<User> searchByPage(int pageNo, int pageSize, String name) {
        PageHelper.startPage(pageNo,pageSize);
        return userDao.selectListByName(name);
    }
    @Override
    public int delete(int id) {
        return userDao.updateState(id,"1","0");
    }
    @Override
    public int recover(int id) {
        return userDao.updateState(id,"0","1");
    }
}