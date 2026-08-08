package com.adplatform.adsponsor.service;

import com.adplatform.adsponsor.vo.request.CreativeRequest;
import com.adplatform.adsponsor.vo.response.CreativeResponse;

public interface ICreativeService {

    CreativeResponse creativeCreative(CreativeRequest request);
}
