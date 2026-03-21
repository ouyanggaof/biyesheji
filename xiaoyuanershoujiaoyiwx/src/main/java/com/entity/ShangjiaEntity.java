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
import com.utils.DateUtil;

/**
 * 商家
 */
@TableName("shangjia")
public class ShangjiaEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public ShangjiaEntity() {}
    public ShangjiaEntity(T t) {
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

    @ColumnInfo(comment = "账户", type = "varchar(200)")
    @TableField(value = "username")
    private String username;

    @ColumnInfo(comment = "密码", type = "varchar(200)")
    @TableField(value = "password")
    private String password;

    @ColumnInfo(comment = "商家名称", type = "varchar(200)")
    @TableField(value = "shangjia_name")
    private String shangjiaName;

    @ColumnInfo(comment = "联系方式", type = "varchar(200)")
    @TableField(value = "shangjia_phone")
    private String shangjiaPhone;

    @ColumnInfo(comment = "邮箱", type = "varchar(200)")
    @TableField(value = "shangjia_email")
    private String shangjiaEmail;

    @ColumnInfo(comment = "营业执照展示", type = "varchar(200)")
    @TableField(value = "shangjia_photo")
    private String shangjiaPhoto;

    @ColumnInfo(comment = "商家类型", type = "int(11)")
    @TableField(value = "shangjia_types")
    private Integer shangjiaTypes;

    @ColumnInfo(comment = "现有余额", type = "decimal(10,2)")
    @TableField(value = "new_money")
    private Double newMoney;

    @ColumnInfo(comment = "商家介绍", type = "longtext")
    @TableField(value = "shangjia_content")
    private String shangjiaContent;

    @ColumnInfo(comment = "逻辑删除", type = "int(11)")
    @TableField(value = "shangjia_delete")
    private Integer shangjiaDelete;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @ColumnInfo(comment = "创建时间", type = "timestamp")
    @TableField(value = "create_time")
    private Date createTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getShangjiaName() { return shangjiaName; }
    public void setShangjiaName(String shangjiaName) { this.shangjiaName = shangjiaName; }
    public String getShangjiaPhone() { return shangjiaPhone; }
    public void setShangjiaPhone(String shangjiaPhone) { this.shangjiaPhone = shangjiaPhone; }
    public String getShangjiaEmail() { return shangjiaEmail; }
    public void setShangjiaEmail(String shangjiaEmail) { this.shangjiaEmail = shangjiaEmail; }
    public String getShangjiaPhoto() { return shangjiaPhoto; }
    public void setShangjiaPhoto(String shangjiaPhoto) { this.shangjiaPhoto = shangjiaPhoto; }
    public Integer getShangjiaTypes() { return shangjiaTypes; }
    public void setShangjiaTypes(Integer shangjiaTypes) { this.shangjiaTypes = shangjiaTypes; }
    public Double getNewMoney() { return newMoney; }
    public void setNewMoney(Double newMoney) { this.newMoney = newMoney; }
    public String getShangjiaContent() { return shangjiaContent; }
    public void setShangjiaContent(String shangjiaContent) { this.shangjiaContent = shangjiaContent; }
    public Integer getShangjiaDelete() { return shangjiaDelete; }
    public void setShangjiaDelete(Integer shangjiaDelete) { this.shangjiaDelete = shangjiaDelete; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}
