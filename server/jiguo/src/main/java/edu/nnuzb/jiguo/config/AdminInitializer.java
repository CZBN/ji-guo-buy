package edu.nnuzb.jiguo.config;

import edu.nnuzb.jiguo.entity.Admin;
import edu.nnuzb.jiguo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@ConditionalOnProperty(name = "jiguo.admin.init-enabled", havingValue = "true", matchIfMissing = true)
public class AdminInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);
    
    @Autowired
    private AdminService adminService;

    @Value("${jiguo.admin.default-name:root}")
    private String defaultName;

    @Value("${jiguo.admin.default-password:123456}")
    private String defaultPassword;
    
    @Override
    public void run(String... args) {
        if (adminService.getAdminByName(defaultName) == null) {
            Admin admin = new Admin();
            admin.setName(defaultName);
            admin.setPassword(defaultPassword);
            admin.setState(1);
            
            try {
                adminService.add(admin);
                log.info("默认管理员账户 {} 已创建", defaultName);
            } catch (Exception e) {
                log.warn("默认管理员账户创建失败: {}", e.getMessage());
            }
        } else {
            log.info("默认管理员账户 {} 已存在", defaultName);
        }
    }
}
