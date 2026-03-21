-- 取物类型字典数据补丁：购买商品时“取物类型”下拉无选项，是因为 dictionary 表里没有 dic_code='quwu_types' 的数据。
-- 在库 xiaoyuanershoujiaoyiwx 下执行本脚本即可出现下拉选项。执行前请先备份。

USE xiaoyuanershoujiaoyiwx;

-- 若已存在 quwu_types 的字典项可先删除再插入，避免重复（可选）：
-- DELETE FROM dictionary WHERE dic_code = 'quwu_types';

-- 插入取物类型选项（code_index 为订单里保存的值，index_name 为下拉显示文字）
INSERT INTO dictionary (dic_code, dic_name, code_index, index_name, super_id, beizhu, create_time) VALUES
('quwu_types', '取物类型', 1, '自取', 0, NULL, NOW()),
('quwu_types', '取物类型', 2, '配送/快递', 0, NULL, NOW()),
('quwu_types', '取物类型', 3, '当面交易', 0, NULL, NOW());
