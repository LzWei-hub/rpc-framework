package com.ciwei;

import com.ciwei.annotation.RpcScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by LzWei on 2025/4/16
 */
@RpcScan(basePackage = {"com.ciwei"})
public class NettyClientMain {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientMain.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = null;
        try {
            // 初始化 Spring 容器
            applicationContext = initializeApplicationContext();
            BookController bookController = applicationContext.getBean(BookController.class);
            // 调用 test 方法并捕获异常
            bookController.test();
        } catch (InterruptedException e) {
            logger.error("线程中断异常：", e);
        } catch (Exception e) {
            logger.error("发生未知异常：", e);
        } finally {
            // 确保容器关闭，释放资源
            if (applicationContext != null) {
                closeApplicationContext(applicationContext);
            }
        }
    }

    /**
     * 初始化 Spring 容器
     *
     * @return AnnotationConfigApplicationContext 实例
     */
    private static AnnotationConfigApplicationContext initializeApplicationContext() {
        return new AnnotationConfigApplicationContext(NettyClientMain.class);
    }

    /**
     * 关闭 Spring 容器
     *
     * @param applicationContext 容器实例
     */
    private static void closeApplicationContext(AnnotationConfigApplicationContext applicationContext) {
        if (applicationContext != null) {
            applicationContext.close();
        }
    }
}
