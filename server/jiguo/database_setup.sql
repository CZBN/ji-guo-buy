CREATE DATABASE IF NOT EXISTS jiguo
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE jiguo;

CREATE TABLE IF NOT EXISTS jg_admin (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  state INT NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  phone VARCHAR(11) NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  image VARCHAR(255) DEFAULT 'tx.jpg',
  state INT NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_cool_item (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  title VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) DEFAULT 0,
  image VARCHAR(255) DEFAULT NULL,
  pub_date DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_cool_item_thumb (
  id INT PRIMARY KEY AUTO_INCREMENT,
  item_id INT NOT NULL,
  user_id INT NOT NULL,
  thumb_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_cool_thumb (item_id, user_id),
  CONSTRAINT fk_thumb_item FOREIGN KEY (item_id) REFERENCES jg_cool_item(id) ON DELETE CASCADE,
  CONSTRAINT fk_thumb_user FOREIGN KEY (user_id) REFERENCES jg_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_cool_item_comment (
  id INT PRIMARY KEY AUTO_INCREMENT,
  item_id INT NOT NULL,
  user_id INT DEFAULT NULL,
  content VARCHAR(500) DEFAULT NULL,
  comment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_comment_item FOREIGN KEY (item_id) REFERENCES jg_cool_item(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_guide_item (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  image VARCHAR(255) DEFAULT NULL,
  title VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) DEFAULT 0,
  pub_date DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_try_item (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  image VARCHAR(255) DEFAULT NULL,
  quantity INT DEFAULT 0,
  price DECIMAL(10,2) DEFAULT 0,
  category VARCHAR(50) NOT NULL,
  begin_date DATE NOT NULL,
  end_date DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_try_apply (
  id INT PRIMARY KEY AUTO_INCREMENT,
  item_id INT NOT NULL,
  user_id INT NOT NULL,
  apply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_try_apply_item FOREIGN KEY (item_id) REFERENCES jg_try_item(id) ON DELETE CASCADE,
  CONSTRAINT fk_try_apply_user FOREIGN KEY (user_id) REFERENCES jg_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jg_try_report (
  id INT PRIMARY KEY AUTO_INCREMENT,
  item_id INT NOT NULL,
  user_id INT NOT NULL,
  title VARCHAR(255) DEFAULT NULL,
  content TEXT,
  report_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_try_report_item FOREIGN KEY (item_id) REFERENCES jg_try_item(id) ON DELETE CASCADE,
  CONSTRAINT fk_try_report_user FOREIGN KEY (user_id) REFERENCES jg_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO jg_admin (name, password, state)
SELECT 'root', MD5('123456'), 1
WHERE NOT EXISTS (SELECT 1 FROM jg_admin WHERE name = 'root');

INSERT INTO jg_user (phone, name, password, image, state)
SELECT '13800138000', '测试用户', MD5('123456'), 'tx.jpg', 1
WHERE NOT EXISTS (SELECT 1 FROM jg_user WHERE phone = '13800138000');

INSERT INTO jg_cool_item (id, name, title, price, image, pub_date) VALUES
(1, '智能手表', '多功能运动健康监测，支持消息提醒与长续航', 599.00, 'k1.jpg', NOW()),
(2, '无线耳机', '主动降噪蓝牙耳机，通勤和学习都适合', 299.00, 'k2.jpg', NOW()),
(3, '便携音箱', '小体积大音量，户外露营也能稳定播放', 199.00, 'k3.jpg', NOW()),
(4, '运动相机', '防抖高清拍摄，记录旅行与运动瞬间', 899.00, 'k4.jpg', NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name), title = VALUES(title), price = VALUES(price), image = VALUES(image);

INSERT INTO jg_guide_item (id, name, title, price, image, pub_date) VALUES
(1, '智能家居', '入门级智能家居设备怎么选，这几款更适合宿舍和家庭', 199.00, 'g1.jpg', NOW()),
(2, '影音娱乐', '无线耳机选购指南：降噪、续航、佩戴感都要看', 399.00, 'g3.jpg', NOW()),
(3, '效率办公', '平板与键盘套装能否替代轻办公电脑', 1299.00, 'g4.jpg', NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name), title = VALUES(title), price = VALUES(price), image = VALUES(image);

INSERT INTO jg_try_item (id, title, image, quantity, price, category, begin_date, end_date) VALUES
(1, '智能手环免费试用', 'tiyanImg.jpg', 50, 299.00, 'General Trial', DATE_ADD(CURDATE(), INTERVAL 3 DAY), DATE_ADD(CURDATE(), INTERVAL 15 DAY)),
(2, '降噪耳机体验师招募', 'earbuds.jpg', 20, 599.00, 'Exclusive Trial', DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 8 DAY)),
(3, '智能手表深度试用', 'watch.jpg', 10, 999.00, 'Exclusive Trial', DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY))
ON DUPLICATE KEY UPDATE title = VALUES(title), image = VALUES(image), quantity = VALUES(quantity), price = VALUES(price), category = VALUES(category), begin_date = VALUES(begin_date), end_date = VALUES(end_date);

INSERT IGNORE INTO jg_cool_item_thumb (item_id, user_id) VALUES (1, 1), (2, 1), (3, 1);
INSERT IGNORE INTO jg_cool_item_comment (item_id, user_id, content) VALUES
(1, 1, '外观不错，续航表现也比较稳定。'),
(2, 1, '降噪效果适合通勤使用。');
INSERT IGNORE INTO jg_try_apply (item_id, user_id) VALUES (1, 1), (2, 1);
INSERT IGNORE INTO jg_try_report (item_id, user_id, title, content) VALUES
(2, 1, '降噪耳机体验报告', '佩戴舒适，连接稳定，适合日常学习和通勤。');
