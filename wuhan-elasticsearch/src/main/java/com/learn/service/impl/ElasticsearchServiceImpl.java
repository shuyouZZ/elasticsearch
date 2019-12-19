package com.learn.service.impl;

import com.learn.common.constant.RedisConstant;
import com.learn.common.constant.ServiceResult;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.Indice;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.elasticsearch.query.*;
import com.learn.elasticsearch.query.condition.*;
import com.learn.elasticsearch.query.model.DataContent;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import com.learn.elasticsearch.suggest.Suggestion;
import com.learn.service.AsycService;
import com.learn.service.ElasticsearchService;
import com.learn.util.DataUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.learn.elasticsearch.Indice.*;

/**
 * @author dshuyou
 * @date 2019/8/30 16:17
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private AsycService asycService;

	private Indice indice;
	private Document document;
	@PostConstruct
	public void init(){
		indice = Indice.getInstance(client);
		document = Document.getInstance(client);
	}

	@Override
	public ServiceResult createIndex(String indexName) {
		indexName = DataUtil.convert(indexName);
		if(DataUtil.hasSymbol(indexName)){
			logger.warn("Parameter Error {}",indexName);
			return ServiceResult.paramsError(indexName);
		}
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.notFound())){
			return result;
		}
		try {
			if(indice.create(indexName)){
				logger.info("Create Index Success {}",indexName);
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
		logger.error("Exception Error");
		return ServiceResult.exceptionFailed();
	}

	@Override
	public ServiceResult createIndex(String indexName,String setting,String mapping) {
		indexName = DataUtil.convert(indexName);
		if(DataUtil.hasSymbol(indexName)){
			logger.warn("Parameter Error {}",indexName);
			return ServiceResult.paramsError(indexName);
		}
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.notFound())){
			return result;
		}
		try {
			if(indice.create(indexName,setting) && indice.putMapping(indexName,mapping)){
				logger.info("Create Index Success {}", indexName );
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
		logger.error("Exception Error");
		return ServiceResult.exceptionFailed();
	}

	private ServiceResult isIndexExist(String... index) {
		try {
			if(indice.isExists(index)){
				logger.debug("Index {} Is Exist",index);
				return ServiceResult.isExist();
			}else {
				logger.debug("Index {} Is Exist",index);
				return ServiceResult.notFound();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult deleteIndex(String indexName) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			if(indice.deleteIndex(indexName)){
				logger.info("Delete Index Success {}", indexName);
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
		logger.error("Exception Error");
		return ServiceResult.exceptionFailed();
	}

	@Override
	public ServiceResult putMapping(String indexName, String mapping) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			if(indice.putMapping(indexName,mapping)){
				logger.info("Put Mapping Success {}",indexName);
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
		logger.error("Internal Server Error");
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult getMapping(String indexName) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		Map<String,Object> mapping;
		try {
			mapping = indice.getMapping(indexName);
			logger.info("Get Mapping Success {}",indexName);
			return ServiceResult.success(mapping);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult updateSetting(String indexName, String setting) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			indice.updateSetting(indexName,setting);
			logger.info("Update Setting Success {}",indexName);
			return ServiceResult.success(setting);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult getSetting(String indexName) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			String setting = indice.getSetting(indexName);
			logger.info("Get Setting Success {}",indexName);
			return ServiceResult.success(setting);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult reflush(String indexName) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			indice.refresh(indexName);
			logger.info("Reflush Index Success");
			return ServiceResult.success(indexName);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult getAllIndex() {
		try {
			Set<String> set = indice.getAllIndices();
			String[] indices = set.toArray(new String[0]);
			logger.info("Get Index List Success");
			return ServiceResult.success(indices);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult putIndexTemplate(String templateName, String source) {
		try {
			return ServiceResult.success(indice.putIndexTemplate(templateName,source));
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult index(String index, String id, Map<String, Object> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			if(document.index(index,id,source)){
				logger.info("Index Success {}" , index);
				return ServiceResult.success(id);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
		logger.error("Exception Error");
		return ServiceResult.exceptionFailed();
	}

	@Override
	public ServiceResult update(String index, String id, Map<String, Object> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			if(document.update(index,id,source)){
				logger.info("Updete Success {}",index);
				return ServiceResult.success(id);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
		logger.error("Exception Error");
		return ServiceResult.exceptionFailed();
	}

	@Override
	public ServiceResult delete(String index, String id) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			if(document.delete(index,id)){
				logger.info("Delete Success {}", index);
				return ServiceResult.success(id);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ServiceResult.internalServerError();
		}
		logger.error("Exception Error");
		return ServiceResult.exceptionFailed();
	}

	@Override
	public ServiceResult bulkIndex(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			//索引数据前调整副本和刷新时间，完成后再更改回来，以提升索引效率和稳定性
			indice.updateSetting(index,INIT_REPLICAS,String.valueOf(INIT_REFLUSH_INTERVAL));
			long count = document.bulkIndex(index,source);
			indice.updateSetting(index,REPLICAS,String.valueOf(REFRESH_INTERVAL)+"s");
			logger.info("Bulk Index Success {}", index);
			return ServiceResult.success(count);
		} catch (IOException e) {
			logger.error("Bulk Index Failed {}",index);
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult bulkUpdate(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			long count = document.bulkUpdate(index, source);
			logger.info("Bulk Update Success {}", index);
			return ServiceResult.success(count);
		} catch (IOException e) {
			logger.error("Internal Server Error");
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult bulkDelete(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			long count = document.bulkDelete(index, source);
			logger.info("Bulk Delete Success {}", index);
			return ServiceResult.success(count);
		} catch (IOException e) {
			logger.error("Internal Server Error");
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult asycBulkIndex(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		asycService.bulkIndex(document,indice,index,source);
		logger.info("Regist Bulk Index Service Success {}", index);
		return ServiceResult.success();
	}

	@Override
	public ServiceResult asycBulkUpdate(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		asycService.bulkUpdate(document,index,source);
		logger.info("Regist Bulk Update Service Success {}", index);
		return ServiceResult.success();
	}

	@Override
	public ServiceResult asycBulkDelete(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		asycService.bulkDelete(document,index,source);
		logger.info("Regist Bulk Detete Service Success {}",index);
		return ServiceResult.success();
	}

	@Override
	public ServiceResult deleteData(String index) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		document.deleteData(index);
		logger.info("Regist Detete All Data Service Success {}",index);
		return ServiceResult.success();
	}

	@Override
	@Cacheable(value = RedisConstant.FULLTEXT_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		FulltextQuery query = new FulltextQuery(index,client, FulltextEnum.valueOf(queryType));
		return executeAndReturn(query,condition);
	}

	@Override
	@Cacheable(value = RedisConstant.TERMS_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		TermsQuery query = new TermsQuery(index,client, TermsEnum.valueOf(queryType));
		return executeAndReturn(query,condition);
	}

	@Override
	@Cacheable(value = RedisConstant.GEO_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		GeoQuery query = new GeoQuery(index,client, GeoEnum.valueOf(queryType));
		return executeAndReturn(query,condition);
	}

	@Override
	@Cacheable(value = RedisConstant.BOOL_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult boolQuery(String index, BoolCondition conditions) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		BoolQuery query = new BoolQuery(index,client);
		return executeAndReturn(query,conditions);
	}

	@Override
	@Cacheable(value = RedisConstant.FULLTEXT_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult queryString(String index, FullTextCondition condition) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		FulltextQuery query = new FulltextQuery(index,client);
		return executeAndReturn(query,condition);
	}

	@Override
	@Cacheable(value = RedisConstant.FULLTEXT_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		FulltextQuery query = new FulltextQuery(index,client, FulltextEnum.valueOf(queryType));
		condition.setFrom(from(pageNum,pageSize));
		condition.setSize(pageSize);

		return executeAndReturn(query,condition);
	}

	@Override
	@Cacheable(value = RedisConstant.TERMS_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		TermsQuery query = new TermsQuery(index,client, TermsEnum.valueOf(queryType));
		condition.setFrom(from(pageNum,pageSize));
		condition.setSize(pageSize);

		return executeAndReturn(query,condition);
	}

	@Override
	@Cacheable(value = RedisConstant.GEO_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		GeoQuery query = new GeoQuery(index,client, GeoEnum.valueOf(queryType));
		condition.setFrom(from(pageNum,pageSize));
		condition.setSize(pageSize);

		return executeAndReturn(query,condition);
	}

	@Override
	@Cacheable(value = RedisConstant.BOOL_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult boolQuery(String index, BoolCondition conditions, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		BoolQuery query = new BoolQuery(index,client);
		conditions.setFrom(from(pageNum,pageSize));
		conditions.setSize(pageSize);

		return executeAndReturn(query,conditions);
	}

	@Override
	public ServiceResult extendWord(String index, String keyWord, int size, String[] fields) {
		if(!index.isEmpty()){
			ServiceResult result = isIndexExist(index.split(","));
			if(! result.equals(ServiceResult.isExist())){
				return result;
			}
		}
		Suggestion suggestion = new Suggestion(client);
		try {
			List<String> list = suggestion.suggest(keyWord,size,fields,index);
			logger.info("Extend Word Success {}",keyWord);
			return ServiceResult.success(list);
		} catch (IOException e) {
			logger.error("Internal Server Error");
			return ServiceResult.internalServerError();
		}
	}

	@Override
	@Cacheable(value = RedisConstant.FULLTEXT_QUERY, keyGenerator = "myKeyGenerator")
	public ServiceResult queryString(String index, FullTextCondition condition, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index.split(","));
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		FulltextEnum queryType = FulltextEnum.valueOf("queryString");
		FulltextQuery query = new FulltextQuery(index,client,queryType);
		condition.setFrom(from(pageNum,pageSize));
		condition.setSize(pageSize);

		return executeAndReturn(query,condition);
	}

	private ServiceResult executeAndReturn(BaseQuery query,BaseCondition condition){
		try {
			DataContent dataContent = query.executeQuery(condition);
			return ServiceResult.success(dataContent);
		} catch (IOException e) {
			logger.error("Internal Server Error");
			return ServiceResult.internalServerError();
		}
	}

	private int from(int pageNum, int size) {
		return (pageNum - 1) * size < 0 ? 0 : (pageNum - 1) * size;
	}
}
