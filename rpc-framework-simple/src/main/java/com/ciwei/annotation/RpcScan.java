package com.ciwei.annotation;

import com.ciwei.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 扫描自定义注释
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface RpcScan {

    String[] basePackage();
}
