package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.util.List;

public class SelectAllService {

    private List<BookDTO> bookList;

    public SelectAllService(List<BookDTO> bookList) {
        this.bookList = bookList;
    }

    // 1. 모든 도서 목럭 조회
    public void selectAll() {
        for (BookDTO book : bookList) {
            System.out.println(book);
        }
    }

}
