package com.ciwei.serviceimpl;

import com.ciwei.Book;
import com.ciwei.BookService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by LzWei on 2025/4/16
 */
@Slf4j
public class BookServiceImpl2 implements BookService {

    static {
        log.info("BookServiceImpl2 init");
    }

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl2.class);

    @Override
    public String book(Book book) {
        logger.info("book() 方法被调用，输入参数: {}", book);

        // 业务逻辑处理
        String result = "bookServiceImpl2返回作者：" + book.getAuthor();

        logger.info("book() 方法执行完成，返回结果: {}", result);
        return result;
    }
}
