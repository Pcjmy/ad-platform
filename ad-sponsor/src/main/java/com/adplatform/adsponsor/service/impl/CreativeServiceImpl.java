package com.adplatform.adsponsor.service.impl;

import com.adplatform.adsponsor.entity.Creative;
import com.adplatform.adsponsor.mapper.CreativeMapper;
import com.adplatform.adsponsor.service.ICreativeService;
import com.adplatform.adsponsor.vo.request.CreativeRequest;
import com.adplatform.adsponsor.vo.response.CreativeResponse;
import org.springframework.stereotype.Service;

@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeMapper creativeMapper;

    public CreativeServiceImpl(CreativeMapper creativeMapper) {
        this.creativeMapper = creativeMapper;
    }

    @Override
    public CreativeResponse creativeCreative(CreativeRequest request) {
        Creative creative = request.convertToEntity();
        creativeMapper.insert(creative);

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
