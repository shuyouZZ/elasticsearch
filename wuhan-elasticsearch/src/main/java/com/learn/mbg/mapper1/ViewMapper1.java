package com.learn.mbg.mapper1;

import com.learn.mbg.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/20 13:15
 */
@Mapper
public interface ViewMapper1 extends BaseMapper {

    @MapKey("comment_id")
    @Select("SELECT * from ${table}")
    List<Map<String,Map<String,Object>>> comments(@Param(value = "table") String table,@Param(value = "pk") String pk);

    @Select("SELECT * from ${table} where comment_id = #{pk}")
    Map<String,Object> findOne(@Param(value = "table") String table, @Param(value = "pk") String pk);

    @Select("SELECT count(#{pk}) from ${table}")
    int count(@Param(value = "table") String table,@Param(value = "pk") String pk);

    @Select("SELECT * from ${table}")
    @Options(fetchSize = Integer.MIN_VALUE)
    List<Map<String,Object>> findByFetch(@Param(value = "table") String table);

    @Select("SELECT * from ${table} where comment_id >= (select comment_id from ${table} Order By comment_id limit #{currPage}, 1) limit #{size};")
    List<Map<String,Object>> findByPage(@Param(value = "table") String table,@Param("currPage") int currPage,@Param("size")int size);

    @Insert("<script>"  +
            "INSERT INTO comment_copy(comment_id,music_id,liked_count,time,user_id,nickname,user_img) "
            + "VALUES <foreach collection=\"list\" item=\"comment\" index=\"index\" separator=\",\">" +
            "(#{comment.comment_id},#{comment.music_id},#{comment.liked_count},#{comment.time},#{comment.user_id},#{comment.nickname,jdbcType=VARCHAR},#{comment.user_img,jdbcType=VARCHAR})" +
            " </foreach>"
            + "</script>")
    int add(@Param("list") List<Map<String, Object>> list);

    @Insert("<script>"  +
            "INSERT INTO comment_copy(comment_id,music_id,liked_count,time,user_id) "
            + "VALUES <foreach collection=\"list\" item=\"comment\" index=\"index\" separator=\",\">" +
            "(#{comment.comment_id},#{comment.music_id},#{comment.liked_count},#{comment.time},#{comment.user_id})" +
            " </foreach>"
            + "</script>")
    int add1(@Param("list") List<Map<String, Object>> list);

}
