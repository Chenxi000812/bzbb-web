package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Goodspecifications;
import com.chenxi.bzbb.domain.model.GoodspecificationsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodspecificationsMapper {
    int countByExample(GoodspecificationsExample example);

    int deleteByExample(GoodspecificationsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Goodspecifications record);

    int insertSelective(Goodspecifications record);

    List<Goodspecifications> selectByExample(GoodspecificationsExample example);

    Goodspecifications selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Goodspecifications record, @Param("example") GoodspecificationsExample example);

    int updateByExample(@Param("record") Goodspecifications record, @Param("example") GoodspecificationsExample example);

    int updateByPrimaryKeySelective(Goodspecifications record);

    int updateByPrimaryKey(Goodspecifications record);
}