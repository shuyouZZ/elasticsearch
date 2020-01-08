package com.learn.mbg.mapper4;

import com.learn.mbg.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/20 13:15
 */
@Mapper
public interface ViewMapper4 extends BaseMapper {
    //select 'GW' type, caseno as  id ,'420000' qy, fwsj as time ,TITLE ,'业务编号：'||caseno||',发文名称：'||title||',发文时间：'||fwsj ||',立搞单位：'|| ligaounit ||',立搞人：'||ligaoman as content from sa.view_fwxx;
    @Select("select 'GW' type, caseno as  id ,'420000' qy, fwsj as time ,TITLE as title ,'业务编号：'||caseno||'," +
            "发文名称：'||title||',发文时间：'||fwsj ||',立搞单位：'|| ligaounit ||'," +
            "立搞人：'||ligaoman as content from ${table}")
    List<Map<String,Object>> findGw(@Param("table") String table);

    @Select("select 'GW' type, caseno as  id ,'420000' qy, fwsj as time ,TITLE as title ,'业务编号：'||caseno||'," +
            "发文名称：'||title||',发文时间：'||fwsj ||',立搞单位：'|| ligaounit ||'," +
            "立搞人：'||ligaoman as content from ${table} where time > #{updatetime}")
    List<Map<String,Object>> updateGw(@Param("table") String table, @Param("updatetime") String updatetime);

    @Select("SELECT 'KC' TYPE,BJNO AS ID, 行政区代码 qy,inputdate AS TIME,项目名称 AS title, " +
            "'报建编号：' || BJNO || '业务编号：' || BSNUM || ',项目名称：' || 项目名称 || ',行政区名称：' || 行政区名称 || '," +
            "原许可证号：' || 原许可证号 || ',批准文号：' || 批准文号 || ',录入时间：' || inputdate || '," +
            "申请人：' || 申请人 || ',矿山名称：' || 矿山名称 || ',总面积' || 总面积 ||',总储量' || 总储量 || '," +
            "区域范围' || 区域范围 AS CONTENT FROM SA.VIEW_MAP_CK_BJINFO")
    List<Map<String,Object>> ckBjinfo();

    @Select("SELECT 'KC' TYPE,BJNO AS ID, 所在行政区 qy,inputdate AS TIME,项目名称 AS title, " +
            "'报建编号：' || BJNO || '业务编号：' || BSNUM || ',申请序号' || 申请序号 || ',项目名称：' || 项目名称 || '," +
            "所在行政区名称：' || 所在行政区名称 || ',所在行政区' || 所在行政区 || ',许可证号：' || 许可证号 || '," +
            "录入时间：' || inputdate || ',申请人：' || 申请人 || ',矿山名称：' || 矿山名称 || ',开采矿种：' || 开采矿种 ||'," +
            "设计规模：' || 设计规模 || ',地质储量情况：' || 地质储量情况 ||',开采方式：' || 开采方式 ||'," +
            "坐标系统：' || 坐标系统 ||',项目类型：' || 项目类型 ||',CASETYPE：' || CASETYPE ||',许可证号：' || 许可证号 ||'," +
            "矿区面积：' || 矿区面积 ||',项目档案号：' || 项目档案号 AS CONTENT FROM SA.VIEW_MAP_CK_SQDJ")
    List<Map<String,Object>> ckSqdj();

    @Select("SELECT 'KC' TYPE,BJNO AS ID, 所在行政区 qy,inputdate AS TIME,项目名称 AS title, " +
            "'报建编号：' || BJNO || '业务编号：' || BSNUM || ',申请序号' || 申请序号 || ',项目名称：' || 项目名称 || '," +
            "所在行政区名称：' || 所在行政区名称 || ',所在行政区' || 所在行政区 || ',许可证号：' || 许可证号 || '," +
            "录入时间：' || inputdate || ',申请人：' || 申请人 || ',矿山名称：' || 矿山名称 || ',开采矿种：' || 开采矿种 ||'," +
            "设计规模：' || 设计规模 || ',地质储量情况：' || 地质储量情况 ||',开采方式：' || 开采方式 ||'," +
            "区域范围：' || 区域范围 ||',区域范围说明' || 区域范围说明 ||',坐标系统：' || 坐标系统 ||'," +
            "项目类型：' || 项目类型 ||',CASETYPE：' || CASETYPE ||',许可证号：' || 许可证号 ||',矿区面积：' || 矿区面积 ||'," +
            "项目档案号：' || 项目档案号 AS CONTENT FROM SA.VIEW_MAP_CK_HDFW")
    List<Map<String,Object>> ckHdfw();

    @Select("SELECT 'KC' TYPE,BJNO AS ID, 行政区代码 qy,inputdate AS TIME,项目名称 AS title, " +
            "'报建编号：' || BJNO || '业务编号：' || BSNUM || ',项目名称：' || 项目名称 || ',行政区名称：' || 行政区名称 || '," +
            "原许可证号：' || 原许可证号 || ',批准文号：' || 批准文号 || ',录入时间：' || inputdate || '," +
            "申请人：' || 申请人 || ',矿山名称：' || 矿山名称 || ',总面积' || 总面积 || '," +
            "区域范围' || 区域范围 AS CONTENT FROM SA.VIEW_MAP_TK_BJINFO")
    List<Map<String,Object>> tkBjinfo();

/*    @Select("SELECT * from ${table} where BJNO = #{pk}")
    Map<String,Object> findKc(@Param(value = "table") String table, @Param(value = "pk") String pk);

    @Select("SELECT * from ${table} where caseno = #{pk}")
    Map<String,Object> findGw(@Param(value = "table") String table, @Param(value = "pk") String pk);*/

}
