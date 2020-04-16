package com.learn.mapper;

import com.learn.model.Comment_copy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/24 16:05
 */
@Mapper
public interface CommentsMapper {

    @Select("Select * from comments where comment_id > 254918813")
    List<Comment_copy> findAll();

    @Select("Select * from comments where comment_id > 1684000527")
    List<Comment_copy> update();

    @Insert("INSERT INTO comment_copy (comment_id ) VALUES (#{copy.comment_id})")
    void save2(@Param(value = "copy") Comment_copy copy);

    @Select("Select * from comments where comment_id > 254918813")
    List<Map<String,Object>> findAll1();

    @Select("Select * from comments where comment_id > 1684000527")
    List<Map<String,Object>> update1();

    @Select("Select * from comments where time > #{updatetime}")
    List<Map<String,Object>> updateByTime(@Param(value = "updatetime") String updatetime);


}
