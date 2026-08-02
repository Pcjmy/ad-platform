package com.adplatform.adsponsor.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictRequest {

    private List<UnitDistrict> unitDistrictList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public static class UnitDistrict {

        private Long unitId;
        private String province;
        private String city;
    }
}
