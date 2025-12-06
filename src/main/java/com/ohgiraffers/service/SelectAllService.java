package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.util.List;

public class SelectAllService {

  private List<BookDTO> bookList;

  // Run 에서 넘겨주는 bookList 그대로 받기
  public SelectAllService(List<BookDTO> bookList) {
      this.bookList = bookList;
  }

  // 1. 모든 도서 목록 조회
  public void selectAll() {

    if (bookList == null || bookList.isEmpty()) {
      System.out.println("조회할 도서가 없습니다.");
      return;
    }

    System.out.println("\n*** 모든 도서 목록 조회 ***");
    System.out.println("(총 " + bookList.size() + "권)\n");

    for (BookDTO book : bookList) {
      System.out.println(book);   // BookDTO.toString()이 호출됨
    }
  }
}
