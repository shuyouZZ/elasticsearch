package com.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.common.constant.ServiceResult;
import com.learn.component.DataSourceHandler;
import com.learn.config.datasource.FirstDataSourceConfig;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.mbg.mapper1.ViewMapper1;
import com.learn.mbg.mapper4.ViewMapper4;
import com.learn.model.IndexSource;
import com.learn.service.DataService;
import com.learn.service.ElasticsearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author dshuyou
 * @date 2019/12/4 15:49
 */
@Controller
@RequestMapping("/elastic/data")
@CrossOrigin
public class DataSourceController {
    private Logger logger = LoggerFactory.getLogger(DataSourceController.class);
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private DataService dataService;

    @ApiOperation("全量索引")
    @RequestMapping(value = "/bulkindex",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "idColumn",value = "主键",required = true,dataType = "String",paramType = "query")})
    public ServiceResult bulkIndex(@RequestParam String database,
                                   @RequestParam String index,
                                   @RequestParam String table,
                                   @RequestParam String idColumn){


        List<Map<String, Object>> list = dataService.select(database,table);
        if(list == null || list.isEmpty()){
            return ServiceResult.notContent();
        }
        List<SourceEntity> bulk = new ArrayList<>();
        for (Map<String, Object> r : list){
            SourceEntity sourceEntity = new SourceEntity();
            sourceEntity.setSource(JSONObject.toJSONString(r));
            sourceEntity.setId(String.valueOf(r.get(idColumn)));
            bulk.add(sourceEntity);
        }
        return elasticsearchService.bulkIndex(index,bulk);
    }

    @ApiOperation("全量索引1")
    @RequestMapping(value = "/bulkindex1",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query")})
    public ServiceResult bulkIndex1(@RequestParam String database,
                                    @RequestParam String index,
                                    @RequestParam String table){
        List<IndexSource> list = dataService.selectIndex(database,table);
        if(list == null || list.isEmpty()){
            return ServiceResult.notContent();
        }
        List<SourceEntity> bulk = new ArrayList<>();
        for (IndexSource r : list){
            SourceEntity sourceEntity = new SourceEntity();
            sourceEntity.setSource(JSONObject.toJSONString(r));
            sourceEntity.setId(r.getId());
            bulk.add(sourceEntity);
        }
        return elasticsearchService.bulkIndex(index,bulk);
    }

    @ApiOperation("全量异步索引")
    @RequestMapping(value = "/asycindex",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "idColumn",value = "主键",required = true,dataType = "String",paramType = "query")})
    public ServiceResult asycIndex(@RequestParam String database,
                                   @RequestParam String index,
                                   @RequestParam String table,
                                   @RequestParam String idColumn){
        List<Map<String, Object>> list = dataService.select(database,table);
        if(list == null || list.isEmpty()){
            return ServiceResult.notContent();
        }
        List<SourceEntity> bulk = new ArrayList<>();
        for (Map<String, Object> r : list){
            SourceEntity sourceEntity = new SourceEntity();
            sourceEntity.setSource(JSONObject.toJSONString(r));
            sourceEntity.setId(String.valueOf(r.get(idColumn)));
            bulk.add(sourceEntity);
        }
        return elasticsearchService.asycBulkIndex(index,bulk);
    }

    @ApiOperation("点击查询")
    @RequestMapping(value = "/selectone",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult findOne(@RequestParam String database,
                                 @RequestParam String table,
                                 @RequestParam String pk,
                                 @RequestParam String id){
        Map<String,Object> res = dataService.findOne(database,table,pk,id);
        if(res == null || res.isEmpty()){
            return ServiceResult.notContent();
        }else {
            return ServiceResult.success(res);
        }
    }

}