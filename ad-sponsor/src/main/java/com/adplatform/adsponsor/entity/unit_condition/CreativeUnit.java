package com.adplatform.adsponsor.entity.unit_condition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("creative_unit")
public class CreativeUnit {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("creative_id")
    private Long creativeId;

    @TableField("unit_id")
    private Long unitId;

    public CreativeUnit(Long creativeId, Long unitId) {
        this.creativeId = creativeId;
        this.unitId = unitId;
    }
}
