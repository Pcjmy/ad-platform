package com.adplatform.adsponsor.entity;

import com.adplatform.adsponsor.constant.CommonStatus;
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
@TableName("ad_unit")
public class AdUnit {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("plan_id")
    private Long planId;

    @TableField("unit_name")
    private String unitName;

    @TableField("unit_status")
    private Integer unitStatus;

    /** 广告位类型（开屏，贴片，中贴...） */
    @TableField("position_type")
    private Integer positionType;

    @TableField("budget")
    private Long budget;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    public AdUnit(Long planId, String planName, Integer positionType , Long budget) {

        this.planId = planId;
        this.unitName = planName;
        this.unitStatus = CommonStatus.VALID.getStatus();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
