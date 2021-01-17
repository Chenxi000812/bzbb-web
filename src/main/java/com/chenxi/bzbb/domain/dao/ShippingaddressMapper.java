package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Shippingaddress;
import com.chenxi.bzbb.domain.model.ShippingaddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShippingaddressMapper {
    int countByExample(ShippingaddressExample example);

    int deleteByExample(ShippingaddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Shippingaddress record);

    int insertSelective(Shippingaddress record);

    List<Shippingaddress> selectByExample(ShippingaddressExample example);

    Shippingaddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Shippingaddress record, @Param("example") ShippingaddressExample example);

    int updateByExample(@Param("record") Shippingaddress record, @Param("example") ShippingaddressExample example);

    int updateByPrimaryKeySelective(Shippingaddress record);

    int updateByPrimaryKey(Shippingaddress record);
}