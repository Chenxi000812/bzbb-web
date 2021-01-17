package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.UserCoupon;
import com.chenxi.bzbb.domain.model.UserCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCouponMapper {
    int countByExample(UserCouponExample example);

    int deleteByExample(UserCouponExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    List<UserCoupon> selectByExample(UserCouponExample example);

    UserCoupon selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserCoupon record, @Param("example") UserCouponExample example);

    int updateByExample(@Param("record") UserCoupon record, @Param("example") UserCouponExample example);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);
}