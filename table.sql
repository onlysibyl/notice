-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS notice;
USE notice;

-- 创建消息类型表
CREATE TABLE IF NOT EXISTS msg_type (
    id INT PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL
);

-- 创建操作表
CREATE TABLE IF NOT EXISTS operation (
    id INT PRIMARY KEY,
    operation_name VARCHAR(255) NOT NULL
);

-- 创建组织表
CREATE TABLE IF NOT EXISTS org (
    id INT AUTO_INCREMENT PRIMARY KEY,
    org_name VARCHAR(255) NOT NULL,
    belonging_level VARCHAR(255) NOT NULL,
    belonging_name VARCHAR(255) NOT NULL
);

-- 创建班级表
CREATE TABLE IF NOT EXISTS klass (
    id INT PRIMARY KEY,
    sch_name VARCHAR(255) NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    major_name VARCHAR(255) NOT NULL,
    grade INT NOT NULL,
    klass_name VARCHAR(255) NOT NULL
);

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    username VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    u_name VARCHAR(255) NOT NULL,
    sex VARCHAR(10) NOT NULL,
    is_stu_role INT NOT NULL,
    klass_id INT,
    FOREIGN KEY (klass_id) REFERENCES klass(id)
);

-- 创建组织角色表
CREATE TABLE IF NOT EXISTS org_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    org_id INT NOT NULL,
    FOREIGN KEY (org_id) REFERENCES org(id)
);

-- 创建超级角色表
CREATE TABLE IF NOT EXISTS super_role (
    id INT PRIMARY KEY,
    is_klass INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES org_role(id)
);

-- 创建用户组织角色关联表
CREATE TABLE IF NOT EXISTS user_org_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    org_role_id INT NOT NULL,
    FOREIGN KEY (username) REFERENCES user(username),
    FOREIGN KEY (org_role_id) REFERENCES org_role(id)
);

-- 创建用户班级角色关联表
CREATE TABLE IF NOT EXISTS user_klass_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    klass_role_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (username) REFERENCES user(username)
);

-- 创建通知表
CREATE TABLE IF NOT EXISTS notice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    notice_name VARCHAR(255) NOT NULL,
    notice_type VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    org_role_id INT NOT NULL,
    klass_role_name VARCHAR(255) NOT NULL,
    begin_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    is_need_reply BOOLEAN NOT NULL,
    FOREIGN KEY (org_role_id) REFERENCES org_role(id)
);

-- 创建通知补充信息表
CREATE TABLE IF NOT EXISTS supply (
    id INT AUTO_INCREMENT PRIMARY KEY,
    notice_id INT NOT NULL,
    role_id INT NOT NULL,
    is_klass INT NOT NULL,
    begin_time DATETIME NOT NULL,
    content TEXT NOT NULL,
    FOREIGN KEY (notice_id) REFERENCES notice(id)
);

-- 创建班级通知关联表
CREATE TABLE IF NOT EXISTS klass_notice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    klass_id INT NOT NULL,
    notice_id INT NOT NULL,
    FOREIGN KEY (klass_id) REFERENCES klass(id),
    FOREIGN KEY (notice_id) REFERENCES notice(id)
);

-- 创建个人消息表
CREATE TABLE IF NOT EXISTS pers_msg (
    id INT AUTO_INCREMENT PRIMARY KEY,
    msg_name VARCHAR(255) NOT NULL,
    msg_type_id INT NOT NULL,
    is_one_send INT NOT NULL,
    send_group_id INT NOT NULL,
    send_role_id INT NOT NULL,
    send_username VARCHAR(255) NOT NULL,
    is_one_receive INT NOT NULL,
    receive_group_id INT NOT NULL,
    receive_role_id INT NOT NULL,
    receive_username VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    operation_id INT NOT NULL,
    is_done INT NOT NULL,
    FOREIGN KEY (msg_type_id) REFERENCES msg_type(id),
    FOREIGN KEY (send_username) REFERENCES user(username),
    FOREIGN KEY (receive_username) REFERENCES user(username),
    FOREIGN KEY (operation_id) REFERENCES operation(id)
);