package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.OrderDisplay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDisplayMapper {

    List<OrderDisplay> queryAllOrdersByStatus(@Param("status") Integer status);
}
