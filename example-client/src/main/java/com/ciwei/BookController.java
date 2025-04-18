package com.ciwei;

import com.ciwei.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Create by LzWei on 2025/4/16
 */
@Component
@Slf4j
public class BookController {

    @RpcReference(version = "version2", group = "test2")
    private BookService bookService;

    public void test() {
        try {
            // 初始化 Book 对象
            Book inputBook = new Book("三国演义", "罗贯中");

            log.info("客户端准备调用 bookService.book() 方法，输入参数: {}", inputBook);

            // 调用 bookService.book() 并缓存结果
            String cachedBookResult = this.bookService.book(inputBook);

            log.info("客户端收到初始结果: {}", cachedBookResult);

            // 模拟延迟（如果必须保留 Thread.sleep，需确保其必要性）
            Thread.sleep(1000);

            // 循环输出结果，避免重复调用
            for (int i = 0; i < 10; i++) {
                log.info("客户端循环输出结果: {}", cachedBookResult);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            log.error("Thread was interrupted", e);
        } catch (Exception e) {
            log.error("Error occurred while processing book service", e);
        }
    }
}
