package com.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.common.constant.DatabaseConstant;
import com.learn.common.constant.RedisConstant;
import com.learn.common.constant.ServiceResult;
import com.learn.component.datasource.dynamic.DynamicDataSource;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.model.IndexSource;
import com.learn.service.DataService;
import com.learn.service.ElasticsearchService;
import com.learn.service.RedisService;
import com.learn.util.MD5Utils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    @Autowired
    private RedisService redisService;

    @ApiOperation("全量索引")
    @RequestMapping(value = "/bulkindex",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "database",value = "数据库",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pk",value = "主键",required = true,dataType = "String",paramType = "query")})
    public ServiceResult bulkIndex(@RequestParam String database,
                                   @RequestParam String index,
                                   @RequestParam String table,
                                   @RequestParam String pk){
        List<Map<String, Object>> list = dataService.select(database,table);
        if(list != null){
            redisService.hashPut(RedisConstant.DATABASE_TABLE,table,database);
        }
        if(list == null || list.isEmpty()){
            return ServiceResult.notContent();
        }
        List<SourceEntity> bulk = new ArrayList<>();
        for (Map<String, Object> r : list){
            SourceEntity sourceEntity = new SourceEntity();
            sourceEntity.setSource(JSONObject.toJSONString(r));
            sourceEntity.setId(String.valueOf(r.get(pk)));
            bulk.add(sourceEntity);
        }
        return elasticsearchService.bulkIndex(index,bulk);
    }

    @ApiOperation("全量索引1")
    @RequestMapping(value = "/bulkindex1",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "database",value = "数据库",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query")})
    public ServiceResult bulkIndex1(@RequestParam String database,
                                    @RequestParam String index,
                                    @RequestParam String table){
        List<IndexSource> list = dataService.selectIndex(database,table);
        if(list != null){
            redisService.hashPut(RedisConstant.DATABASE_TABLE,table,database);
        }
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
    @ApiImplicitParams({@ApiImplicitParam(name = "database",value = "数据库",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pk",value = "主键名称",required = true,dataType = "String",paramType = "query")})
    public ServiceResult asycIndex(@RequestParam String database,
                                   @RequestParam String index,
                                   @RequestParam String table,
                                   @RequestParam String pk){
        List<Map<String, Object>> list = dataService.select(database,table);
        if(list != null){
            redisService.hashPut(RedisConstant.DATABASE_TABLE,table,database);
        }
        if(list == null || list.isEmpty()){
            return ServiceResult.notContent();
        }
        List<SourceEntity> bulk = new ArrayList<>();
        for (Map<String, Object> r : list){
            SourceEntity sourceEntity = new SourceEntity();
            sourceEntity.setSource(JSONObject.toJSONString(r));
            sourceEntity.setId(String.valueOf(r.get(pk)));
            bulk.add(sourceEntity);
        }
        return elasticsearchService.asycBulkIndex(index,bulk);
    }

    @ApiOperation("点击查询")
    @RequestMapping(value = "/selectone",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "database",value = "数据库",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "数据表",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pk",value = "主键名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "String",paramType = "query")})
    public ServiceResult findOne(@RequestParam String database,
                                 @RequestParam String table,
                                 @RequestParam String pk,
                                 @RequestParam String id){
        return dataService.findOne(database,table,pk,id);
    }

    @ApiOperation("点击查询1")
    @RequestMapping(value = "/selectone1",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "table",value = "数据表",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pk",value = "主键名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "String",paramType = "query")})
    public ServiceResult findOne1(@RequestParam String table,
                                  @RequestParam String pk,
                                  @RequestParam String id){
        return dataService.findOne(table,pk,id);
    }

    @ApiOperation("获取datasource")
    @RequestMapping(value = "/getdatasource",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getDatasource(){
        return ServiceResult.success(DynamicDataSource.getDataSource());
    }

    @ApiOperation("getObjectByBloom")
    @RequestMapping(value = "/getObjectByBloom",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "database",value = "数据库",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "table",value = "数据表",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pk",value = "主键名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "String",paramType = "query")})
    public ServiceResult getObjectByBloom(@RequestParam String database,
                                          @RequestParam String table,
                                          @RequestParam String pk,
                                          @RequestParam String id){
        return ServiceResult.success(dataService.getObjectByBloom(database,table,pk,id));
    }

    @ApiOperation("getObjectByBloom1")
    @RequestMapping(value = "/getObjectByBloom1",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "table",value = "数据表",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pk",value = "主键名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "String",paramType = "query")})
    public ServiceResult getObjectByBloom1(@RequestParam String table,
                                           @RequestParam String pk,
                                           @RequestParam String id){
        return dataService.getObjectByBloom(table,pk,id);
    }
}
