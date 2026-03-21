-- 数据库缺列补丁：若表结构与代码不一致，在库 xiaoyuanershoujiaoyiwx 下执行本脚本
-- 执行前请先备份。若列已存在会报错，可忽略或先查 information_schema 再决定是否执行。

USE xiaoyuanershoujiaoyiwx;

-- 1. 商品表 shangpin：缺少 yonghu_id 时添加（关联发布用户）
ALTER TABLE shangpin ADD COLUMN yonghu_id INT(11) NULL COMMENT '发布用户id' AFTER id;
-- 若有唯一/外键需要可再补： ALTER TABLE shangpin ADD INDEX idx_yonghu_id (yonghu_id);

-- 2. 商品订单表 shangpin_order：缺少 quwu_types 时添加（取物方式）
ALTER TABLE shangpin_order ADD COLUMN quwu_types INT(11) NULL COMMENT '取物方式' AFTER buy_number;
