package edu.nnuzb.jiguo.service;
import edu.nnuzb.jiguo.entity.User;

import java.util.List;

public interface UserService {
    User login( User user);

    User register(User user);

    List<User> searchByPage(int pageNo, int pageSize,String name);

    int delete(int id);
    int recover(int id);
}
