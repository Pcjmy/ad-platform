package com.adplatform.adsponsor.mapper;

import com.adplatform.adsponsor.entity.AdPlan;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AdPlanMapper extends BaseMapper<AdPlan> {

    default AdPlan findByIdAndUserId(Long id, Long userId) {
        return selectOne(new LambdaQueryWrapper<AdPlan>()
                .eq(AdPlan::getId, id)
                .eq(AdPlan::getUserId, userId));
    }

    default List<AdPlan> findAllByIdAndUserId(List<Long> ids, Long userId) {
        return selectList(new LambdaQueryWrapper<AdPlan>()
                .in(AdPlan::getId, ids)
                .eq(AdPlan::getUserId, userId));
    }

    default AdPlan findByUserIdAndPlanName(Long userId, String planName) {
        return selectOne(new LambdaQueryWrapper<AdPlan>()
                .eq(AdPlan::getUserId, userId)
                .eq(AdPlan::getPlanName, planName));
    }

    default List<AdPlan> findAllByPlanStatus(Integer planStatus) {
        return selectList(new LambdaQueryWrapper<AdPlan>()
                .eq(AdPlan::getPlanStatus, planStatus));
    }
}
