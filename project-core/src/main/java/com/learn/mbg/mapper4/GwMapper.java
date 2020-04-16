package com.learn.mbg.mapper4;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/27 15:08
 */
@Mapper
public interface GwMapper {

    @Select("SELECT * from ${table}")
    List<Map<String,Object>> findAll(@Param("table")String table);

    @Select("SELECT * from ${table} whert caseno = #{id}")
    List<Map<String,Object>> findOne(@Param("table")String table,@Param("id")String id);
}
