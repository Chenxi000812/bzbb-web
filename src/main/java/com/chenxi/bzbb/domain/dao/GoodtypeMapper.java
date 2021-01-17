package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Goodtype;
import com.chenxi.bzbb.domain.model.GoodtypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodtypeMapper {
    int countByExample(GoodtypeExample example);

    int deleteByExample(GoodtypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goodtype record);

    int insertSelective(Goodtype record);

    List<Goodtype> selectByExample(GoodtypeExample example);

    Goodtype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goodtype record, @Param("example") GoodtypeExample example);

    int updateByExample(@Param("record") Goodtype record, @Param("example") GoodtypeExample example);

    int updateByPrimaryKeySelective(Goodtype record);

    int updateByPrimaryKey(Goodtype record);
}