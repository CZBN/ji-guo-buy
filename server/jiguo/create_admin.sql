-- 创建测试管理员账户 root/123456
INSERT INTO jg_admin (name, password, state) 
SELECT 'root', MD5('123456'), 1
WHERE NOT EXISTS (
    SELECT 1 FROM jg_admin WHERE name = 'root'
);

-- 验证账户是否创建成功
SELECT * FROM jg_admin WHERE name = 'root';