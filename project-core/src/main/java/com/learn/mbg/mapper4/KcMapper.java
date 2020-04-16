package com.learn.mbg.mapper4;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/27 15:04
 */
@Mapper
public interface KcMapper {

    @Select("SELECT * from ${table}")
    List<Map<String,Object>> findAll(@Param("table")String table);

    @Select("SELECT * from ${table} whert BJNO = #{id}")
    List<Map<String,Object>> findKc(@Param("table")String table,@Param("id")String id);

    @Select("SELECT * from ${table} whert caseno = #{id}")
    List<Map<String,Object>> findGw(@Param("table")String table,@Param("id")String id);
}
