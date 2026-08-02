package com.adplatform.adsponsor.service;

import com.adplatform.adsponsor.vo.request.AdUnitDistrictRequest;
import com.adplatform.adsponsor.vo.request.AdUnitItRequest;
import com.adplatform.adsponsor.vo.request.AdUnitKeywordRequest;
import com.adplatform.adsponsor.vo.request.AdUnitRequest;
import com.adplatform.adsponsor.vo.response.AdUnitDistrictResponse;
import com.adplatform.adsponsor.vo.response.AdUnitItResponse;
import com.adplatform.adsponsor.vo.response.AdUnitKeywordResponse;
import com.adplatform.adsponsor.vo.response.AdUnitResponse;
import com.adplatform.common.exception.AdException;

public interface IAdUnitService {

    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request)
        throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request)
        throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request)
        throws AdException;
}
