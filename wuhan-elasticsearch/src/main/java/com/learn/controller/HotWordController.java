package com.learn.controller;

import com.learn.common.constant.ServiceResult;
import com.learn.component.HotWord;
import com.learn.service.HotWordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author dshuyou
 * @date 2019/10/21 17:50
 */
@Controller
@RequestMapping("/hotword")
@CrossOrigin
public class HotWordController {
	@Autowired
	@Qualifier("hotWordServiceImpl")
	private HotWordService hotWordService;

	@ApiOperation("导入热词")
	@RequestMapping(value = "/set",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult setHotWord(@RequestParam(defaultValue = "hotword") String key,
								   @RequestParam String value,
								   @RequestParam(defaultValue = "1") long score){
		Boolean result = hotWordService.set(key,value,score);
		return result.equals(true) ?
				ServiceResult.success(key) : new ServiceResult(200,"this hotword is exist",key);
	}

	@ApiOperation("增加热词频率")
	@RequestMapping(value = "/increment",method = RequestMethod.GET)
	@ResponseBody
	@HotWord
	public ServiceResult increment(@RequestParam(defaultValue = "hotword") String key,
									 @RequestParam String value,
									 @RequestParam(defaultValue = "1") long delta){
		double result = hotWordService.increment(key,value,delta);
		return ServiceResult.success(result);
	}


	@ApiOperation("热词列表")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult getHotWord(@RequestParam(defaultValue = "hotword") String key,
									 @RequestParam(defaultValue = "0") long start,
									 @RequestParam(defaultValue = "10") long end){
		Set<String> set = hotWordService.getHotWord(key,start,end);
		return ServiceResult.success(set);
	}
}
