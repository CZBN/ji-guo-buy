package edu.nnuzb.jiguo.service;

import edu.nnuzb.jiguo.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin login(Admin admin);
    
    Admin getAdminByName(String name);

    List<Admin> getAllAdmins();
    
    int saveAdmin(Admin admin);
    
    int updateAdmin(Admin admin);
    
    int deleteAdmin(int id);

    int add(Admin admin);
    
    int delete(int id);
    
    int recover(int id);
    
    int selectByName(String name);
}