package com.ciwei;

import com.ciwei.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by LzWei on 2025/4/16
 */
@RpcScan(basePackage = {"com.ciwei"})
public class NettyClientMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        BookController bookController = applicationContext.getBean(BookController.class);
        try {
            bookController.test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
