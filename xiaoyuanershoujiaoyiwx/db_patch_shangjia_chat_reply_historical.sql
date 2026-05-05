-- 一次性修补历史数据：已有回复内容但 reply_time 为空、或状态为空
-- 执行前请备份数据库。新数据由后端在 /shangjiaChat/update 中自动补全回复时间与状态。

UPDATE shangjia_chat
SET reply_time = insert_time
WHERE (reply_time IS NULL OR reply_time = '1970-01-01 00:00:00')
  AND shangjia_chat_reply_text IS NOT NULL
  AND TRIM(shangjia_chat_reply_text) <> ''
  AND shangjia_chat_reply_text <> '-';

UPDATE shangjia_chat
SET zhuangtai_types = 2
WHERE zhuangtai_types IS NULL
  AND shangjia_chat_reply_text IS NOT NULL
  AND TRIM(shangjia_chat_reply_text) <> ''
  AND shangjia_chat_reply_text <> '-';

UPDATE shangjia_chat
SET zhuangtai_types = 1
WHERE zhuangtai_types IS NULL;
