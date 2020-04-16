package com.learn.component;

import java.lang.annotation.*;

/**
 * @author dshuyou
 * @date 2019/12/6 11:34
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicRoutingDataSource {

    String value() default "defaultDataSource";

}
