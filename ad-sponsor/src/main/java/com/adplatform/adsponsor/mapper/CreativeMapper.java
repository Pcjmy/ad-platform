package com.adplatform.adsponsor.mapper;

import com.adplatform.adsponsor.entity.Creative;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CreativeMapper extends BaseMapper<Creative> {
    default List<Creative> findAllById(List<Long> ids) {
        return selectByIds(ids);
    }
}
