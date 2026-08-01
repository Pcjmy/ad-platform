package com.adplatform.adsponsor.mapper;

import com.adplatform.adsponsor.entity.AdUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface AdUserMapper extends BaseMapper<AdUser> {

    default AdUser findByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<AdUser>()
                .eq(AdUser::getUsername, username));
    }

    default AdUser findById(Long id) {
        return selectOne(new LambdaQueryWrapper<AdUser>()
                .eq(AdUser::getId, id));
    }
}
