package com.ciwei.annotation;

import java.lang.annotation.*;

/**
 * RPC 引用注解，自动装配服务实现类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {
    /**
     * 服务版本，默认值为空字符串
     */
    String version() default "";

    /**
     * 服务分组，默认值为空字符串
     */
    String group() default "";

}
