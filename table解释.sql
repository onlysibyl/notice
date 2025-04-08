-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS notice;
USE notice;

-- 创建用户表
-- 存储系统中所有用户的基本信息
CREATE TABLE IF NOT EXISTS user (
    -- 用户的唯一标识，通常是学号或工号等
    username VARCHAR(255) NOT NULL PRIMARY KEY COMMENT '用户的唯一标识',
    -- 用户登录使用的密码
    password VARCHAR(255) NOT NULL COMMENT '用户登录密码',
    -- 用户的真实姓名
    u_name VARCHAR(255) NOT NULL COMMENT '用户真实姓名',
    -- 用户的性别
    sex VARCHAR(10) NOT NULL COMMENT '用户性别',
    -- 标识用户是否为学生角色，1 表示是，0 表示不是
    is_stu_role INT NOT NULL COMMENT '是否为学生角色，1 是，0 否',
    -- 用户所属班级的 ID
    klass_id INT COMMENT '用户所属班级的 ID'
);

-- 创建用户组织角色关联表
-- 记录用户与组织角色之间的关联关系
CREATE TABLE IF NOT EXISTS user_org_role (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 关联的用户的用户名
    username VARCHAR(255) NOT NULL COMMENT '关联的用户的用户名',
    -- 关联的组织角色的 ID
    org_role_id INT NOT NULL COMMENT '关联的组织角色的 ID'
);

-- 创建用户班级角色关联表
-- 记录用户与班级角色之间的关联关系
CREATE TABLE IF NOT EXISTS user_klass_role (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 关联的用户的用户名
    username VARCHAR(255) NOT NULL COMMENT '关联的用户的用户名',
    -- 关联的班级角色的名称
    klass_role_name VARCHAR(255) NOT NULL COMMENT '关联的班级角色的名称'
);

-- 创建组织表
-- 存储系统中的各种组织信息
CREATE TABLE IF NOT EXISTS org (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 组织的名称
    org_name VARCHAR(255) NOT NULL COMMENT '组织名称',
    -- 组织所属的层级
    belonging_level VARCHAR(255) NOT NULL COMMENT '组织所属层级',
    -- 组织所属的上级名称
    belonging_name VARCHAR(255) NOT NULL COMMENT '组织所属上级名称'
);

-- 创建组织角色表
-- 存储组织中的各种角色信息
CREATE TABLE IF NOT EXISTS org_role (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 角色的名称
    role_name VARCHAR(255) NOT NULL COMMENT '角色名称',
    -- 关联的组织的 ID
    org_id INT NOT NULL COMMENT '关联的组织的 ID'
);

-- 创建班级表
-- 存储学校中的各个班级信息
CREATE TABLE IF NOT EXISTS klass (
    -- 班级的唯一标识
    id INT PRIMARY KEY COMMENT '班级唯一标识',
    -- 班级所属学校的名称
    sch_name VARCHAR(255) NOT NULL COMMENT '班级所属学校名称',
    -- 班级所属系部的名称
    dept_name VARCHAR(255) NOT NULL COMMENT '班级所属系部名称',
    -- 班级所属专业的名称
    major_name VARCHAR(255) NOT NULL COMMENT '班级所属专业名称',
    -- 班级的年级
    grade INT NOT NULL COMMENT '班级年级',
    -- 班级的名称
    klass_name VARCHAR(255) NOT NULL COMMENT '班级名称'
);

-- 创建通知表
-- 存储系统中发布的各种通知信息
CREATE TABLE IF NOT EXISTS notice (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 通知的名称
    notice_name VARCHAR(255) NOT NULL COMMENT '通知名称',
    -- 通知的类型
    notice_type VARCHAR(255) NOT NULL COMMENT '通知类型',
    -- 通知的具体内容
    content TEXT NOT NULL COMMENT '通知具体内容',
    -- 发布通知的组织角色的 ID
    org_role_id INT NOT NULL COMMENT '发布通知的组织角色的 ID',
    -- 发布通知的班级角色的名称
    klass_role_name VARCHAR(255) NOT NULL COMMENT '发布通知的班级角色的名称',
    -- 通知的开始时间
    begin_time DATETIME NOT NULL COMMENT '通知开始时间',
    -- 通知的结束时间
    end_time DATETIME NOT NULL COMMENT '通知结束时间',
    -- 通知是否需要回复
    is_need_reply BOOLEAN NOT NULL COMMENT '通知是否需要回复'
);

-- 创建通知补充信息表
-- 存储通知的补充信息
CREATE TABLE IF NOT EXISTS supply (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 关联的通知的 ID
    notice_id INT NOT NULL COMMENT '关联的通知的 ID',
    -- 关联的角色的 ID
    role_id INT NOT NULL COMMENT '关联的角色的 ID',
    -- 是否为班级相关，1 表示是，0 表示否
    is_klass INT NOT NULL COMMENT '是否为班级相关，1 是，0 否',
    -- 补充信息的开始时间
    begin_time DATETIME NOT NULL COMMENT '补充信息开始时间',
    -- 补充信息的具体内容
    content TEXT NOT NULL COMMENT '补充信息具体内容'
);

-- 创建班级通知关联表
-- 记录班级与通知之间的关联关系
CREATE TABLE IF NOT EXISTS klass_notice (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 关联的班级的 ID
    klass_id INT NOT NULL COMMENT '关联的班级的 ID',
    -- 关联的通知的 ID
    notice_id INT NOT NULL COMMENT '关联的通知的 ID'
);

-- 创建个人消息表
-- 存储用户之间的个人消息信息
CREATE TABLE IF NOT EXISTS pers_msg (
    -- 自增的唯一标识
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增唯一标识',
    -- 消息的名称
    msg_name VARCHAR(255) NOT NULL COMMENT '消息名称',
    -- 消息的类型 ID
    msg_type_id INT NOT NULL COMMENT '消息类型 ID',
    -- 是否为一对一发送，1 表示是，0 表示否
    is_one_send INT NOT NULL COMMENT '是否为一对一发送，1 是，0 否',
    -- 发送方所在组的 ID
    send_group_id INT NOT NULL COMMENT '发送方所在组的 ID',
    -- 发送方的角色 ID
    send_role_id INT NOT NULL COMMENT '发送方的角色 ID',
    -- 发送方的用户名
    send_username VARCHAR(255) NOT NULL COMMENT '发送方的用户名',
    -- 是否为一对一接收，1 表示是，0 表示否
    is_one_receive INT NOT NULL COMMENT '是否为一对一接收，1 是，0 否',
    -- 接收方所在组的 ID
    receive_group_id INT NOT NULL COMMENT '接收方所在组的 ID',
    -- 接收方的角色 ID
    receive_role_id INT NOT NULL COMMENT '接收方的角色 ID',
    -- 接收方的用户名
    receive_username VARCHAR(255) NOT NULL COMMENT '接收方的用户名',
    -- 消息的具体内容
    content TEXT NOT NULL COMMENT '消息具体内容',
    -- 操作的 ID
    operation_id INT NOT NULL COMMENT '操作 ID',
    -- 消息是否已处理，1 表示是，0 表示否
    is_done INT NOT NULL COMMENT '消息是否已处理，1 是，0 否'
);

-- 创建消息类型表
-- 存储系统中消息的各种类型信息
CREATE TABLE IF NOT EXISTS msg_type (
    -- 消息类型的唯一标识
    id INT PRIMARY KEY COMMENT '消息类型唯一标识',
    -- 消息类型的名称
    type_name VARCHAR(255) NOT NULL COMMENT '消息类型名称'
);

-- 创建操作表
-- 存储系统中各种操作的信息
CREATE TABLE IF NOT EXISTS operation (
    -- 操作的唯一标识
    id INT PRIMARY KEY COMMENT '操作唯一标识',
    -- 操作的名称
    operation_name VARCHAR(255) NOT NULL COMMENT '操作名称'
);

-- 创建超级角色表
-- 存储超级角色的相关信息
CREATE TABLE IF NOT EXISTS super_role (
    -- 超级角色的唯一标识
    id INT PRIMARY KEY COMMENT '超级角色唯一标识',
    -- 是否为班级相关，1 表示是，0 表示否
    is_klass INT NOT NULL COMMENT '是否为班级相关，1 是，0 否',
    -- 关联的角色的 ID
    role_id INT NOT NULL COMMENT '关联的角色的 ID'
);