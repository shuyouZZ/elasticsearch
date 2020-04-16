package com.learn.component;

import java.lang.annotation.*;

/**
 * @author dshuyou
 * @date  2019/10/22 9:22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented //是否将该注解生成在javadoc文档中：配置即为true
public @interface HotWord {
}

