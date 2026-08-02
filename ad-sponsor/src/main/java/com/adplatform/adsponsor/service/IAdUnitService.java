package com.adplatform.adsponsor.service;

import com.adplatform.adsponsor.vo.request.AdUnitRequest;
import com.adplatform.adsponsor.vo.response.AdUnitResponse;
import com.adplatform.common.exception.AdException;

public interface IAdUnitService {

    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;
}
