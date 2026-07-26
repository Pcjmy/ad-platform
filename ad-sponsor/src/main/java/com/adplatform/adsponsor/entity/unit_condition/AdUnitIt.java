package com.adplatform.adsponsor.entity.unit_condition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ad_unit_it")
public class AdUnitIt {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("unit_id")
    private Long unitId;

    @TableField("it_tag")
    private String itTag;

    public AdUnitIt(Long unitId, String it_tag) {
        this.unitId = unitId;
        this.itTag = it_tag;
    }
}
