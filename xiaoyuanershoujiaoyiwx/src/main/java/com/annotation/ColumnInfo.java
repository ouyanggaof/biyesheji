package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//作用：标在 实体类的字段 上（@Target(ElementType.FIELD)），用 comment 和 type 描述该列的 中文说明 和 数据库类型
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnInfo {
    String comment();
    String type();
}
