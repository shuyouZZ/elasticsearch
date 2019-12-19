package com.learn.service;

import com.learn.common.constant.ServiceResult;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.elasticsearch.query.condition.*;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/8/30 14:48
 */
public interface ElasticsearchService {
	/**
	 * 创建默认索引
	 * @param index 索引
	 * @return ServiceResult
	 */
	ServiceResult createIndex(String index);

	/**
	 * 创建自定义索引
	 * @param index 索引
	 * @param setting 设置
	 * @param mapping 映射
	 * @return - ServiceResult
	 */
	ServiceResult createIndex(String index, String setting, String mapping);

	/**
	 * 删除索引
	 * @param index 索引
	 * @return ServiceResult
	 */
	ServiceResult deleteIndex(String index);

	/**
	 * 添加映射
	 * @param index 索引
	 * @param mapping 映射
	 * @return ServiceResult
	 */
	ServiceResult putMapping(String index, String mapping);

	/**
	 * 获取索引的映射
	 * @param index 索引
	 * @return ServiceResult
	 */
	ServiceResult getMapping(String index);

	/**
	 * 更新配置信息
	 * @param index 索引
	 * @param setting 索引数据
	 * @return ServiceResult
	 */
	ServiceResult updateSetting(String index, String setting);

	/**
	 * 获取索引的设置
	 * @param index 索引
	 * @return ServiceResult
	 */
	ServiceResult getSetting(String index);

	/**
	 * 刷新
	 * @param index 索引
	 * @return ServiceResult
	 */
	ServiceResult reflush(String index);

	/**
	 * 获取索引列表
	 * @return ServiceResult
	 */
	ServiceResult getAllIndex();

	/**
	 * 创建索引模板
	 * @return ServiceResult
	 */
	ServiceResult putIndexTemplate(String templateName, String source);

	/**
	 * 单条索引
	 * @param index 索引
	 * @param id 索引编号id
	 * @param source 索引数据
	 * @return ServiceResult
	 */
	ServiceResult index(String index, String id, Map<String, Object> source);

	/**
	 * 单条索引更新
	 * @param index 索引
	 * @param id 索引编号id
	 * @param source 索引数据
	 * @return ServiceResult
	 */
	ServiceResult update(String index, String id, Map<String, Object> source);

	/**
	 * 单条索引删除
	 * @param index 索引
	 * @param id 索引编号id
	 * @return ServiceResult
	 */
	ServiceResult delete(String index, String id);

	/**
	 * 批量索引
	 * @param index 索引
	 * @param sources 索引数据
	 * @return ServiceResult
	 */
	ServiceResult bulkIndex(String index, List<SourceEntity> sources);

	/**
	 * 批量更新
	 * @param index 索引
	 * @param sources 索引数据
	 * @return ServiceResult
	 */
	ServiceResult bulkUpdate(String index, List<SourceEntity> sources);

	/**
	 * 批量删除
	 * @param index 索引
	 * @param sources 索引数据
	 * @return ServiceResult
	 */
	ServiceResult bulkDelete(String index, List<SourceEntity> sources);

	/**
	 * 异步批量索引
	 * @param index 索引
	 * @param sources 索引数据
	 * @return ServiceResult
	 */
	ServiceResult asycBulkIndex(String index, List<SourceEntity> sources);

	/**
	 * 异步批量更新
	 * @param index 索引
	 * @param sources 索引数据
	 * @return ServiceResult
	 */
	ServiceResult asycBulkUpdate(String index, List<SourceEntity> sources);

	/**
	 * 批量删除
	 * @param index 索引
	 * @param sources 索引数据
	 * @return ServiceResult
	 */
	ServiceResult asycBulkDelete(String index, List<SourceEntity> sources);

	/**
	 * 删除索引中所有数据
	 * @param index 索引
	 * @return ServiceResut
	 */
	ServiceResult deleteData(String index);

	/**
	 * 全文查询
	 * @param index 索引
	 * @param queryType 全文查询类型
	 * @param condition 全文查询条件
	 * @return ServiceResult
	 */
	ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition);

	/**
	 * 字段查询
	 * @param index 索引
	 * @param queryType 字段查询类型
	 * @param condition 字段查询条件
	 * @return ServiceResult
	 */
	ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition);

	/**
	 * 地理空间信息查询
	 * @param index 索引
	 * @param queryType 地理查询类型
	 * @param condition 地理空间查询条件
	 * @return ServiceResult
	 */
	ServiceResult geoQuery(String index, String queryType, GeoCondition condition);

	/**
	 * 布尔查询
	 * @param index 索引
	 * @param conditions 布尔查询条件
	 * @return ServiceResult
	 */
	ServiceResult boolQuery(String index, BoolCondition conditions);

	/**
	 * QueryString查询
	 * @param index 索引
	 * @param condition queryString 查询条件
	 * @return ServiceResult
	 */
	ServiceResult queryString(String index, FullTextCondition condition);
	/**
	 * 全文查询
	 * @param index 索引
	 * @param queryType 全文查询类型
	 * @param condition 全文查询条件
	 * @param pageNum 页数
	 * @param pageSize 单页数据量
	 * @return ServiceResult
	 */
	ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition, int pageNum, int pageSize);

	/**
	 * 字段查询
	 * @param index 索引
	 * @param queryType 字段查询类型
	 * @param condition 字段查询条件
	 * @param pageNum 页数
	 * @param pageSize 单页数据量
	 * @return ServiceResult
	 */
	ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition,int pageNum, int pageSize);

	/**
	 * 地理空间信息查询
	 * @param index 索引
	 * @param queryType 地理查询类型
	 * @param condition 地理空间查询条件
	 * @param pageNum 页数
	 * @param pageSize 单页数据量
	 * @return ServiceResult
	 */
	ServiceResult geoQuery(String index, String queryType, GeoCondition condition,int pageNum, int pageSize);

	/**
	 * 布尔查询
	 * @param index 索引
	 * @param conditions 布尔查询条件
	 * @param pageNum 页数
	 * @param pageSize 单页数据量
	 * @return ServiceResult
	 */
	ServiceResult boolQuery(String index, BoolCondition conditions, int pageNum, int pageSize);

	/**
	 * 联想词（建议词）
	 * @param index 索引
	 * @param keyWord 关键词
	 * @param size 返回结果集的大小
	 * @param fields 索引字段
	 * @return ServiceResult
	 */
	ServiceResult extendWord(String index, String keyWord, int size, String[] fields);

	/**
	 * QueryString查询
	 * @param index 索引
	 * @param condition queryString 查询条件
	 * @param pageNum 页数
	 * @param pageSize 单页数据量
	 * @return ServiceResult
	 */
	ServiceResult queryString(String index, FullTextCondition condition, int pageNum, int pageSize);
}
