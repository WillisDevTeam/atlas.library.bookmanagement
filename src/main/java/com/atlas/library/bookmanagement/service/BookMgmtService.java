package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.repository.BookMgmtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookMgmtService {

    private final BookMgmtRepository bookMgmtRepository;

    public Book getLibraryBook(String bookId) {
        log.info("you are getting a book");
        return bookMgmtRepository.getOne(bookId);
    }
}
