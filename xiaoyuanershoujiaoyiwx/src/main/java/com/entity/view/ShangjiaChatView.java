package com.entity.view;

import com.annotation.ColumnInfo;
import com.entity.ShangjiaChatEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
 * 用户咨询 视图
 */
@TableName("shangjia_chat")
public class ShangjiaChatView extends ShangjiaChatEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 提问人-用户姓名 */
    @ColumnInfo(comment = "用户姓名", type = "varchar(200)")
    private String yonghuName;
    @ColumnInfo(comment = "用户头像", type = "varchar(200)")
    private String yonghuPhoto;

    /** 回答人-商家名称 */
    @ColumnInfo(comment = "商家名称", type = "varchar(200)")
    private String shangjiaName;
    @ColumnInfo(comment = "商家联系方式", type = "varchar(200)")
    private String shangjiaPhone;

    public ShangjiaChatView() {}
    public ShangjiaChatView(ShangjiaChatEntity entity) {
        try {
            BeanUtils.copyProperties(this, entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getYonghuName() { return yonghuName; }
    public void setYonghuName(String yonghuName) { this.yonghuName = yonghuName; }
    public String getYonghuPhoto() { return yonghuPhoto; }
    public void setYonghuPhoto(String yonghuPhoto) { this.yonghuPhoto = yonghuPhoto; }
    public String getShangjiaName() { return shangjiaName; }
    public void setShangjiaName(String shangjiaName) { this.shangjiaName = shangjiaName; }
    public String getShangjiaPhone() { return shangjiaPhone; }
    public void setShangjiaPhone(String shangjiaPhone) { this.shangjiaPhone = shangjiaPhone; }
}
