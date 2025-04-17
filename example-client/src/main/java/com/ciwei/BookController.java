package com.ciwei;

import com.ciwei.annotation.RpcReference;
import org.springframework.stereotype.Component;

/**
 * Create by LzWei on 2025/4/16
 */
@Component
public class BookController {

    @RpcReference(version = "version2", group = "test2")
    private BookService bookService;

    public void test() throws InterruptedException{
        String book = this.bookService.book(new Book("三国演义", "罗贯中"));
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            System.out.println(bookService.book(new Book("三国演义", "罗贯中")));
        }
    }
}
