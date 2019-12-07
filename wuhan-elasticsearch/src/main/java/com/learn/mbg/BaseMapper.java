package com.learn.mbg;

import com.learn.model.IndexSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/12/4 21:57
 */
@Mapper
public interface BaseMapper<T> extends MySqlMapper<T>, ConditionMapper<T> {
    @SelectProvider(type = CreateSql.class, method = "selectWithParamSql")
    Map<String,Object> selectWithId(Map<String, Object> params);

    @Select("SELECT * from ${table}")
    List<Map<String,Object>> select(@Param("table") String table);

    @Select("SELECT * from ${table}")
    List<IndexSource> selectIndex(@Param("table") String table);
}