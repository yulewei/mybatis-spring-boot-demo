package com.example.service;

import com.example.dto.BookSearch;
import com.example.entity.Book;
import com.example.entity.criteria.BookExample;
import com.example.mapper.BookMapper;
import com.example.util.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yulewei on 2017/11/22
 */
@Service("bookService")
public class BookService {

    @Resource
    private BookService bookService;
    @Resource
    private BookMapper bookMapper;

    public PageInfo<Book> searchByPage(BookSearch bookSearch) {
        PageHelper.startPage(bookSearch.getPage(), bookSearch.getLimit());
        BookExample example = new BookExample();
        List<Book> list = bookMapper.selectByExample(example);
        return PageInfo.of(list);
    }

    public List<Book> findAll() {
        BookExample example = new BookExample();
        return bookMapper.selectByExample(example);
    }

    @Transactional(readOnly = true)
    public List<Book> findByIsbn(String isbn) {
        BookExample example = new BookExample();
        example.createCriteria().andIsbnEqualTo(isbn);
        return bookMapper.selectByExample(example);
    }

    @Transactional
    public Integer insert(Book book) {
        bookMapper.insertSelective(book);

        try {
            book.setTitle("222");
            bookService.updateBook(book);
        } catch (Exception e) {
            System.out.println("异常");
        }
        return book.getId();
    }

    public Integer getBook1(Book book) {
        bookMapper.insertSelective(book);
        return book.getId();
    }

    @Transactional
    public Integer getBook2(Book book) {
        bookMapper.insertSelective(book);
        return book.getId();
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer updateBook(Book book) {
        bookMapper.updateByPrimaryKeySelective(book);
        return book.getId();
//        throw new RuntimeException("RuntimeException");
    }
}
