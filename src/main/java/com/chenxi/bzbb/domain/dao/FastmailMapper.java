package com.chenxi.bzbb.domain.dao;

import com.chenxi.bzbb.domain.model.Fastmail;
import com.chenxi.bzbb.domain.model.FastmailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FastmailMapper {
    int countByExample(FastmailExample example);

    int deleteByExample(FastmailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Fastmail record);

    int insertSelective(Fastmail record);

    List<Fastmail> selectByExample(FastmailExample example);

    Fastmail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Fastmail record, @Param("example") FastmailExample example);

    int updateByExample(@Param("record") Fastmail record, @Param("example") FastmailExample example);

    int updateByPrimaryKeySelective(Fastmail record);

    int updateByPrimaryKey(Fastmail record);
}