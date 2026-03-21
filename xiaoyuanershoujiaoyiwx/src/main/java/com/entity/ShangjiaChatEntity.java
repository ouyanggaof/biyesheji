package com.entity;

import com.annotation.ColumnInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;

/**
 * 用户咨询（商家与用户一对一聊天）
 */
@TableName("shangjia_chat")
public class ShangjiaChatEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public ShangjiaChatEntity() {}
    public ShangjiaChatEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TableId(type = IdType.AUTO)
    @ColumnInfo(comment = "主键", type = "int(11)")
    @TableField(value = "id")
    private Integer id;

    /** 提问人（用户） */
    @ColumnInfo(comment = "提问人", type = "int(11)")
    @TableField(value = "yonghu_id")
    private Integer yonghuId;

    /** 回答人（商家） */
    @ColumnInfo(comment = "回答人", type = "int(11)")
    @TableField(value = "shangjia_id")
    private Integer shangjiaId;

    /** 问题 */
    @ColumnInfo(comment = "问题", type = "longtext")
    @TableField(value = "shangjia_chat_issue_text")
    private String shangjiaChatIssueText;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @ColumnInfo(comment = "问题时间", type = "timestamp")
    @TableField(value = "issue_time")
    private Date issueTime;

    /** 回复 */
    @ColumnInfo(comment = "回复", type = "longtext")
    @TableField(value = "shangjia_chat_reply_text")
    private String shangjiaChatReplyText;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @ColumnInfo(comment = "回复时间", type = "timestamp")
    @TableField(value = "reply_time")
    private Date replyTime;

    @ColumnInfo(comment = "状态", type = "int(11)")
    @TableField(value = "zhuangtai_types")
    private Integer zhuangtaiTypes;

    @ColumnInfo(comment = "数据类型", type = "int(11)")
    @TableField(value = "shangjia_chat_types")
    private Integer shangjiaChatTypes;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @ColumnInfo(comment = "提问时间", type = "timestamp")
    @TableField(value = "insert_time")
    private Date insertTime;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @ColumnInfo(comment = "创建时间", type = "timestamp")
    @TableField(value = "create_time")
    private Date createTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getYonghuId() { return yonghuId; }
    public void setYonghuId(Integer yonghuId) { this.yonghuId = yonghuId; }
    public Integer getShangjiaId() { return shangjiaId; }
    public void setShangjiaId(Integer shangjiaId) { this.shangjiaId = shangjiaId; }
    public String getShangjiaChatIssueText() { return shangjiaChatIssueText; }
    public void setShangjiaChatIssueText(String shangjiaChatIssueText) { this.shangjiaChatIssueText = shangjiaChatIssueText; }
    public Date getIssueTime() { return issueTime; }
    public void setIssueTime(Date issueTime) { this.issueTime = issueTime; }
    public String getShangjiaChatReplyText() { return shangjiaChatReplyText; }
    public void setShangjiaChatReplyText(String shangjiaChatReplyText) { this.shangjiaChatReplyText = shangjiaChatReplyText; }
    public Date getReplyTime() { return replyTime; }
    public void setReplyTime(Date replyTime) { this.replyTime = replyTime; }
    public Integer getZhuangtaiTypes() { return zhuangtaiTypes; }
    public void setZhuangtaiTypes(Integer zhuangtaiTypes) { this.zhuangtaiTypes = zhuangtaiTypes; }
    public Integer getShangjiaChatTypes() { return shangjiaChatTypes; }
    public void setShangjiaChatTypes(Integer shangjiaChatTypes) { this.shangjiaChatTypes = shangjiaChatTypes; }
    public Date getInsertTime() { return insertTime; }
    public void setInsertTime(Date insertTime) { this.insertTime = insertTime; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}
