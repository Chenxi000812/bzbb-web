package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Goodbrand;
import com.chenxi.bzbb.domain.model.GoodbrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodbrandMapper {
    int countByExample(GoodbrandExample example);

    int deleteByExample(GoodbrandExample example);

    int insert(Goodbrand record);

    int insertSelective(Goodbrand record);

    List<Goodbrand> selectByExample(GoodbrandExample example);

    int updateByExampleSelective(@Param("record") Goodbrand record, @Param("example") GoodbrandExample example);

    int updateByExample(@Param("record") Goodbrand record, @Param("example") GoodbrandExample example);
}