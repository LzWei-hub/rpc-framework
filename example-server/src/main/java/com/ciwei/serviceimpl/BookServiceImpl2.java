package com.ciwei.serviceimpl;

import com.ciwei.Book;
import com.ciwei.BookService;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by LzWei on 2025/4/16
 */
@Slf4j
public class BookServiceImpl2 implements BookService {

    static {
        System.out.println("BookServiceImpl2 init");
    }

    @Override
    public String book(Book book) {
        log.info("bookServiceImpl2收到：",book.getName());
        String result = "bookServiceImpl2返回作者：" + book.getAuthor();
        log.info("bookServiceImpl2返回：",result);
        return result;
    }
}
