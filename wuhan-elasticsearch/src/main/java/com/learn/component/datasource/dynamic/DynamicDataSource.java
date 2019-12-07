package com.learn.component.datasource.dynamic;

import com.alibaba.druid.pool.DruidDataSource;
import com.learn.component.datasource.PropertiesUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dshuyou
 * @date 2019/12/6 11:47
 */
public class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware {

    private ApplicationContext applicationContext ;


    private static final ThreadLocal<String> dataSourceKey = ThreadLocal.withInitial(() -> "defaultDataSource");

    public static Map<Object, Object> dataSourcesMap = new ConcurrentHashMap<>(10);

    static {
        dataSourcesMap.put("defaultDataSource", SpringUtils.getBean("defaultDataSource"));
    }

    public static String getDataSource() {
        return DynamicDataSource.dataSourceKey.get();
    }

    public static void clear() {
        DynamicDataSource.dataSourceKey.remove();
    }

    public static void setDataSource(String dataSource) {
        DynamicDataSource.dataSourceKey.set(dataSource);
        DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringUtils.getBean("dataSource");
        dynamicDataSource.afterPropertiesSet();
    }

    /**
     * 连接数据源前,调用该方法
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //1.获取手动设置的数据源参数DataSourceBean
        String dataSource = DynamicDataSource.getDataSource();
        if(dataSource == null) {
            return null;
        }
        try {
            //2.获取AbstractRoutingDataSource的targetDataSources属性,该属性存放数据源属性
            Map<Object, Object> targetSourceMap = getTargetSource();
            synchronized(this) {

                //3.判断targetDataSources中是否已经存在要设置的数据源bean，存在的话,则直接返回beanName
                if(targetSourceMap == null || !targetSourceMap.keySet().contains(dataSource)) {
					//不存在，则先在spring容器中创建该数据源bean，将创建后的bean,放入到targetDataSources Map中，并通知spring有bean更新
                    if(targetSourceMap == null){targetSourceMap = new HashMap<>();}
                    DruidDataSource druidDataSource = PropertiesUtils.initDynamicDatasource(dataSource);
                    targetSourceMap.put(dataSource, druidDataSource);
					/*
					 * 通知spring有bean更新
					 * 主要更新AbstractRoutingDataSource的resolvedDefaultDataSource(Map)属性,
					 * 更新完以后,AbstractRoutingDataSource的determineTargetDataSource()中,才能找到数据源
					 * 代码如下：
					 * Object lookupKey = determineCurrentLookupKey();
					   DataSource dataSource = this.resolvedDataSources.get(lookupKey);
					 */
                    super.setTargetDataSources(targetSourceMap);
                    super.afterPropertiesSet();
                }
            }
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据数据源信息在spring中创建bean,并返回
     * @param dataSource 数据源信息
     * @return
     * @throws IllegalAccessException
     */
    public Object createDataSource(String dataSource) throws IllegalAccessException {
        //1.将applicationContext转化为ConfigurableApplicationContext
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        //2.获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory beanFactory =  (DefaultListableBeanFactory) context.getBeanFactory();
        /*
         * 3.本文用的是DruidDataSource,所有在这里我们获取的是该bean的BeanDefinitionBuilder,
         * 通过BeanDefinitionBuilder来创建bean定义
         */
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
        /**
         * 4.获取DataSourceBean里的属性和对应值,并将其交给BeanDefinitionBuilder创建bean的定义
         */
        DataSourceBean dataSourceBean = PropertiesUtils.praseProperties(dataSource);

        Map<String, Object> propertyKeyValues = getPropertyKeyValues(DataSourceBean.class, dataSourceBean);
        for(Map.Entry<String,Object> entry : propertyKeyValues.entrySet()) {
            beanDefinitionBuilder.addPropertyValue(entry.getKey(), entry.getValue());
        }
        //5.bean定义创建好以后,将其交给beanFactory注册成bean对象，由spring容器管理
        beanFactory.registerBeanDefinition(dataSourceBean.getBeanName(), beanDefinitionBuilder.getBeanDefinition());
        //6.最后获取步骤5生成的bean,并将其返回
        return context.getBean(dataSourceBean.getBeanName());
    }
    //获取类属性和对应的值,放入Map中
    @SuppressWarnings("unused")
    private <T> Map<String, Object> getPropertyKeyValues(Class<T> clazz, Object object) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        Map<String,Object> map = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }
        map.remove("beanName");
        return map;
    }
    //通过反射获取AbstractRoutingDataSource的targetDataSources属性
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getTargetSource() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = AbstractRoutingDataSource.class.getDeclaredField("targetDataSources");
        field.setAccessible(true);
        return (Map<Object, Object>) field.get(this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
