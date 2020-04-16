package com.learn.mbg.mapper1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MapperTest {

    @Select("Select * from ${table} where comment_id = #{id}")
    Map<String, Object> find(@Param("table") String table, @Param("id") int id);

    @Select("Select * from ${table}")
    List<Map<String, Object>> findAll(@Param("table") String table);
}
