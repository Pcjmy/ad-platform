package com.adplatform.adsponsor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ad_creative")
public class Creative {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("type")
    private Integer type;

    @TableField("material_type")
    private Integer materialType;

    @TableField("height")
    private Integer height;

    @TableField("width")
    private Integer width;

    @TableField("size")
    private Long size;

    @TableField("duration")
    private Integer duration;

    /** 审核状态 */
    @TableField("audit_status")
    private Integer auditStatus;

    @TableField("user_id")
    private Long userId;

    @TableField("url")
    private String url;

    @TableField("createTime")
    private Date createTime;

    @TableField("updateTime")
    private Date updateTime;
}
