package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Good;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyGoodMapper {

    List<Good> queryGoodDisplays(@Param("type") Integer type, @Param("brand") Integer brand);
}
