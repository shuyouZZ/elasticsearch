package com.learn.controller;

import com.learn.common.constant.ServiceResult;
import com.learn.service.ElasticsearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshuyou
 * @date 2019/9/16 9:47
 */
@Controller
@RequestMapping("/elastic/indice")
@CrossOrigin
public class ElasticUtilController {
	private static final Logger logger = LoggerFactory.getLogger(ElasticUtilController.class);
	@Autowired
	private ElasticsearchService elasticsearchService;

	@ApiOperation("构建默认索引")
	@RequestMapping(value = "/create",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult createIndex(@RequestParam String index){
		return elasticsearchService.createIndex(index);
	}

	@ApiOperation("构建自定义索引")
	@RequestMapping(value = "/createbycustom",method = RequestMethod.POST)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "setting",value = "配置",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "mapping",value = "映射",required = true,dataType = "String",paramType = "query")})
	public ServiceResult createByCustom(@RequestParam String index,
								 @RequestParam String setting,
								 @RequestParam String mapping){
		return elasticsearchService.createIndex(index,setting,mapping);
	}

	@ApiOperation("更新映射")
	@RequestMapping(value = "/putmapping",method = RequestMethod.PUT)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "mapping",value = "映射",required = true,dataType = "String",paramType = "query")})
	public ServiceResult putMapping(@RequestParam String index,
								 @RequestParam String mapping){
		return elasticsearchService.putMapping(index,mapping);
	}

	@ApiOperation("更新配置")
	@RequestMapping(value = "/updateset",method = RequestMethod.PUT)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "setting",value = "配置",required = true,dataType = "String",paramType = "query")})
	public ServiceResult updateSet(@RequestParam String index,
								 @RequestParam String setting){
		return elasticsearchService.updateSetting(index,setting);
	}

	@ApiOperation("索引列表")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult getAllindex(){
		return elasticsearchService.getAllIndex();
	}

	@ApiOperation("查看索引配置")
	@RequestMapping(value = "/settings",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult getSettings(@RequestParam String index){
		return elasticsearchService.getSetting(index);
	}

	@ApiOperation("查看索引映射")
	@RequestMapping(value = "/mapping",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult getMapping(@RequestParam String index){
		return elasticsearchService.getMapping(index);
	}

	@ApiOperation("删除索引")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult deleteIndex(@RequestParam String index){
		return elasticsearchService.deleteIndex(index);
	}
}
