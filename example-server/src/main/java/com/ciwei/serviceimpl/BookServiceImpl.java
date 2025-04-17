package com.ciwei.serviceimpl;

import com.ciwei.Book;
import com.ciwei.BookService;
import com.ciwei.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by LzWei on 2025/4/16
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
public class BookServiceImpl implements BookService {

    static {
        System.out.println("BookServiceImpl init");
    }

    @Override
    public String book(Book book) {
        log.info("BookServiceImpl收到：{}",book.getName());
        String result = "这是调用BookServiceImpl返回的作者：" + book.getAuthor();
        log.info("返回结果：{}",result);
        return result;
    }
}
