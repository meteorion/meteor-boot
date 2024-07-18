DROP DATABASE IF EXISTS `meteor-cloud-v2-pay`;
# --
CREATE DATABASE  `meteor-cloud-v2-pay` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `meteor-cloud-v2-pay`;

CREATE TABLE `pay_channel_config`
(
    `channel_config_id` BIGINT AUTO_INCREMENT COMMENT '配置ID',
    `pay_channel_id`    BIGINT COMMENT '通道ID',
    `channel_type`      VARCHAR(3) COMMENT '支付通道类型',
    `status`            INT COMMENT '通道状态',
    `cost_rate`         DECIMAL(10, 2) COMMENT '成本费率（我方）',
    `cost_fee`          INT COMMENT '成本手续费（我方）',
    `max_rate`          DECIMAL(10, 2) COMMENT '最大费率(通道)',
    `min_rate`          DECIMAL(10, 2) COMMENT '最低费率(通道)',
    `max_fee`           INT COMMENT '最大手续费(通道)',
    `min_fee`           INT COMMENT '最低手续费(通道)',
    `daily_order_limit` INT COMMENT '日订单限额',
    `daily_limit`       INT COMMENT '日限额',
    `single_min_limit`  INT COMMENT '单笔最低金额',
    `single_max_limit`  INT COMMENT '单笔最大限额',
    `start_time`        DATETIME COMMENT '开始时间',
    `end_time`          DATETIME COMMENT '截止时间',
    `create_time`       DATETIME COMMENT '创建时间',
    `creator`           VARCHAR(20) COMMENT '创建人',
    `update_time`       DATETIME COMMENT '更新时间',
    `updater`           VARCHAR(20) COMMENT '更新人',
    PRIMARY KEY (`channel_config_id`) -- 设置主键
) COMMENT ='支付通道配置表';

--
CREATE TABLE `pay_channel`
(
    `pay_channel_id` BIGINT AUTO_INCREMENT COMMENT '支付通道ID',
    `name`           VARCHAR(255) COMMENT '通道名称',
    `code`           VARCHAR(50) COMMENT '通道代号', -- 假设代号较短，不超过50个字符
    `status`         INT COMMENT '通道状态',
    `daily_limit`    INT COMMENT '日限额',
    `month_limit`    INT COMMENT '月限额',
    `create_time`    DATETIME COMMENT '创建时间',
    `creator`        VARCHAR(20) COMMENT '创建人',
    `update_time`    DATETIME COMMENT '更新时间',
    `updater`        VARCHAR(20) COMMENT '更新人',
    PRIMARY KEY (`pay_channel_id`)                   -- 设置主键
) COMMENT ='支付通道基本信息表';

--
CREATE TABLE `pay_channel_client_config`
(
    `channel_config_id` BIGINT NOT NULL COMMENT '主键',
    `pay_channel_id`    BIGINT COMMENT '支付通道ID',
    `channel_type`      VARCHAR(255) COMMENT '支付通道类型',
    `service_url`       VARCHAR(512) COMMENT '服务地址', -- 假设URL长度可能较长
    `app_id`            VARCHAR(100) COMMENT '运用ID',   -- 假设应用ID长度适中
    `agent_id`          VARCHAR(50) COMMENT '代理编号',
    `public_key`        TEXT COMMENT '公钥',             -- 公钥可能很长，使用TEXT类型
    `secret_key`        TEXT COMMENT '私钥',             -- 私钥可能很长，使用TEXT类型
    `sign_key`          VARCHAR(255) COMMENT '签名key',
    `metedata`          JSON COMMENT '扩展参数',         -- 使用JSON类型存储复杂结构的扩展参数
    PRIMARY KEY (`channel_config_id`)                    -- 设置主键
) COMMENT ='支付客户端配置表';
