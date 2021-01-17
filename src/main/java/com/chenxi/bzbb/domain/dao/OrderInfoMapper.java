package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper {

    List<OrderInfo> selectedOrderInfosByIds(@Param("ids") List<Long> ids);

    OrderInfo selectedOrderInfoById(Long id);
}
