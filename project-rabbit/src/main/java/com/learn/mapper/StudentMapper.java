package com.learn.mapper;

import com.learn.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * @author dshuyou
 * @date 2019/11/1 11:13
 */
@Mapper
public interface StudentMapper {

    @Select("Select * from Student where sid > 03")
    List<Student> findAll();

    @Select("Select * from Student where sid > 03")
    List<Map<String,Object>> findAll1();
}
