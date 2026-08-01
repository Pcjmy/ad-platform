package com.adplatform.adsponsor.vo.request;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanRequest {

    private Long id;
    private Long userId;
    private String planName;
    private String startDate;
    private String endDate;

    public boolean createValidate() {
        return userId != null
                && !StringUtils.isEmpty(planName)
                && !StringUtils.isEmpty(startDate)
                && !StringUtils.isEmpty(endDate);
    }

    public boolean updateValidate() {
        return id != null && userId != null;
    }

    public boolean deleteValidate() {
        return id != null && userId != null;
    }
}
