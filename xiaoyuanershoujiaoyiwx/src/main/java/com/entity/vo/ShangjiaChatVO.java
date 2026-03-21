package com.entity.vo;

import com.entity.ShangjiaChatEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

@TableName("shangjia_chat")
public class ShangjiaChatVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Integer id;
    @TableField(value = "yonghu_id")
    private Integer yonghuId;
    @TableField(value = "shangjia_id")
    private Integer shangjiaId;
    @TableField(value = "shangjia_chat_issue_text")
    private String shangjiaChatIssueText;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @TableField(value = "issue_time")
    private Date issueTime;
    @TableField(value = "shangjia_chat_reply_text")
    private String shangjiaChatReplyText;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @TableField(value = "reply_time")
    private Date replyTime;
    @TableField(value = "zhuangtai_types")
    private Integer zhuangtaiTypes;
    @TableField(value = "shangjia_chat_types")
    private Integer shangjiaChatTypes;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @TableField(value = "insert_time")
    private Date insertTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @TableField(value = "create_time")
    private Date createTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getYonghuId() { return yonghuId; }
    public void setYonghuId(Integer yonghuId) { this.yonghuId = yonghuId; }
    public Integer getShangjiaId() { return shangjiaId; }
    public void setShangjiaId(Integer shangjiaId) { this.shangjiaId = shangjiaId; }
    public String getShangjiaChatIssueText() { return shangjiaChatIssueText; }
    public void setShangjiaChatIssueText(String s) { this.shangjiaChatIssueText = s; }
    public Date getIssueTime() { return issueTime; }
    public void setIssueTime(Date d) { this.issueTime = d; }
    public String getShangjiaChatReplyText() { return shangjiaChatReplyText; }
    public void setShangjiaChatReplyText(String s) { this.shangjiaChatReplyText = s; }
    public Date getReplyTime() { return replyTime; }
    public void setReplyTime(Date d) { this.replyTime = d; }
    public Integer getZhuangtaiTypes() { return zhuangtaiTypes; }
    public void setZhuangtaiTypes(Integer i) { this.zhuangtaiTypes = i; }
    public Integer getShangjiaChatTypes() { return shangjiaChatTypes; }
    public void setShangjiaChatTypes(Integer i) { this.shangjiaChatTypes = i; }
    public Date getInsertTime() { return insertTime; }
    public void setInsertTime(Date d) { this.insertTime = d; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date d) { this.createTime = d; }
}
