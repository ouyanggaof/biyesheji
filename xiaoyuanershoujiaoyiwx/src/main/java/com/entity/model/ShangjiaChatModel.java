package com.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

public class ShangjiaChatModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer yonghuId;
    private Integer shangjiaId;
    private String shangjiaChatIssueText;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date issueTime;
    private String shangjiaChatReplyText;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date replyTime;
    private Integer zhuangtaiTypes;
    private Integer shangjiaChatTypes;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date insertTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
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
