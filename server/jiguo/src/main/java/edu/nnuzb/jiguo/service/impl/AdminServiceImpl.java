package edu.nnuzb.jiguo.service.impl;

import edu.nnuzb.jiguo.dao.AdminDao;
import edu.nnuzb.jiguo.entity.Admin;
import edu.nnuzb.jiguo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin login(Admin admin) {
        return adminDao.selectByAdmin(admin);
    }
    
    @Override
    public Admin getAdminByName(String name) {
        return adminDao.selectByName(name);
    }
    
    @Override
    public List<Admin> getAllAdmins() {
        return adminDao.selectAll();
    }
    
    @Override
    public int saveAdmin(Admin admin) {
        // 检查是否已存在同名管理员
        Admin existingAdmin = adminDao.selectByName(admin.getName());
        if (existingAdmin != null) {
            throw new RuntimeException("管理员名称已存在");
        }
        return adminDao.insertAdmin(admin);
    }
    
    @Override
    public int updateAdmin(Admin admin) {
        return adminDao.updateAdmin(admin);
    }
    
    @Override
    public int deleteAdmin(int id) {
        return adminDao.deleteAdmin(id);
    }

    @Override
    public int add(Admin admin) {
        if(adminDao.selectByName(admin.getName()) != null){
            throw new RuntimeException("管理员用户名已存在");
        }
        // 如果未设置状态，默认设置为1（正常）
        if (admin.getState() == null) {
            admin.setState(1);
        }
        return adminDao.insertAdmin(admin);
    }

    
    @Override
    public int selectByName(String name) {
        Admin admin = adminDao.selectByName(name);
        return admin != null ? 1 : 0;
    }

    @Override
    public int delete(int id) {
        // 使用updateState方法软删除管理员，将状态从1改为0
        return adminDao.updateState(id, 1, 0);
    }

    @Override
    public int recover(int id) {
        // 使用updateState方法恢复管理员，将状态从0改为1
        return adminDao.updateState(id, 0, 1);
    }
}