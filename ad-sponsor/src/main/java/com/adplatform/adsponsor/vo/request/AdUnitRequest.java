package com.adplatform.adsponsor.vo.request;

import com.alibaba.druid.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    public boolean createValidate() {
        return planId != null && !StringUtils.isEmpty(unitName)
                && positionType != null && budget != null;
    }
}
