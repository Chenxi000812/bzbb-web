package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Iplimit;
import com.chenxi.bzbb.domain.model.IplimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IplimitMapper {
    int countByExample(IplimitExample example);

    int deleteByExample(IplimitExample example);

    int deleteByPrimaryKey(String ip);

    int insert(Iplimit record);

    int insertSelective(Iplimit record);

    List<Iplimit> selectByExample(IplimitExample example);

    Iplimit selectByPrimaryKey(String ip);

    int updateByExampleSelective(@Param("record") Iplimit record, @Param("example") IplimitExample example);

    int updateByExample(@Param("record") Iplimit record, @Param("example") IplimitExample example);

    int updateByPrimaryKeySelective(Iplimit record);

    int updateByPrimaryKey(Iplimit record);
}