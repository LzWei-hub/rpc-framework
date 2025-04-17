package com.ciwei.annotation;

import java.lang.annotation.*;

/**
 * RPC 服务注释，标记在服务实现类上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {

    /**
     * 服务版本，默认值为空字符串
     */
    String version() default "";
    /**
     * 服务组，默认值为空字符串
     */
    String group() default "";
}
