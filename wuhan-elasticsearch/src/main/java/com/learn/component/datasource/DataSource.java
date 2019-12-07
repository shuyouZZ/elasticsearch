package com.learn.component.datasource;

import java.lang.annotation.*;

/**
 * @author dshuyou
 * @date 2019/12/4 16:54
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    /**
     * 数据源 dsKey
     */
    DsKey dsKey() default DsKey.DATASOURCE;

}