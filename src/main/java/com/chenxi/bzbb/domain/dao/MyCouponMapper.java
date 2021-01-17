package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCouponMapper {
    List<Coupon> queryUserCoupons(@Param("uid") Long uid, @Param("status") Integer status);
}
